package com.example.springbatchex;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ChunkConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("batchJob")
			.incrementer(new RunIdIncrementer())
			.start(step1())
			.next(step2())
			.build();
	}

	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
			.<String,String>chunk(2)
			.reader(new ListItemReader<>(Arrays.asList("item1", "item2","item3","item4", "item5","item6")))
			.processor(new ItemProcessor<String, String>() {

				@Nullable
				@Override
				public String process(@Nonnull String item) throws Exception {
					Thread.sleep(300);
					System.out.println(item);
					return "my_" + item;
				}
			})
			.writer(new ItemWriter<String>() {
				@Override
				public void write(List<? extends String> items) throws Exception {
					Thread.sleep(300);
					System.out.println(items);
				}
			})
			.build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
			.tasklet((contribution, chunkContext) -> {
				System.out.println("step2 has executed");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

}
