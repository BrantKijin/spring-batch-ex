package com.example.springbatchex;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ItemReader_ItemProcessor_ItemWriter_Configuration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job(){
		return jobBuilderFactory.get("batchJob")
			.start(step1())
		//	.next(step2())
			.build();
	}
	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
			.<String,String>chunk(3)
			.reader(itemReader())
			.processor(itemProcessor())
			.writer(itemWriter())
			.build();
	}

	@Bean
	public ItemReader itemReader(){
		return new CustomItemReader(Arrays.asList(new Customer("user1"),new Customer("user2"),new Customer("user3")));
	}
	@Bean
	public ItemProcessor itemProcessor(){
		return new CustomItemProcessor();
	}

	@Bean
	public ItemWriter itemWriter(){
		return new CustomItemWriter();
	}




}
