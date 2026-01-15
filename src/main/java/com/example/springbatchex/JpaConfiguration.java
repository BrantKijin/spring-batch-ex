package com.example.springbatchex;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class JpaConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource;
	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("batchJob")
			.incrementer(new RunIdIncrementer())
			.start(step1())
			.build();
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
			.<Customer, Customer2>chunk(10)
			.reader(customItemReader())
			.processor(customItemProcess())
			.writer(customItemWriter())
			.build();
	}

	@Bean
	public JpaItemWriter<Customer2> customItemWriter() {
		return new JpaItemWriterBuilder<Customer2>()
			.entityManagerFactory(entityManagerFactory)
			.usePersist(true)
			.build();
	}

	@Bean
	public ItemProcessor<? super Customer, ? extends Customer2> customItemProcess() {
		return new CustomItemProcess();
	}

	@Bean
	public JdbcPagingItemReader<Customer> customItemReader() {
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new CustomerRowMapper());

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		queryProvider.setWhereClause("where firstname like :firstName");

		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider);

		HashMap<String, Object> params = new HashMap<>();
		params.put("firstName", "C%");

		reader.setParameterValues(params);


		return reader;
	}
}
