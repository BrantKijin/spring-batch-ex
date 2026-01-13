package com.example.springbatchex;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ChunkProviderChunkProcessorConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job(){
		return jobBuilderFactory.get("batchJob")
			.start(step1())
			.next(step2())
			.build();
	}

	@Bean
	@JobScope
	public Step step1(){
		return stepBuilderFactory.get("step1")
			.<String,String>chunk(2)
			.reader(new ListItemReader<>(Arrays.asList("Item1","Item2","Item3")))
			.processor(new ItemProcessor<String, String>() {
				@Nullable
				@Override
				public String process(@Nonnull String s) throws Exception {
					return "my_"+ s;
				}
			})
			.writer(items -> items.forEach(System.out::println))
			.build();
	}


	@Bean
	public Step step2(){
		return stepBuilderFactory.get("Step2")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("Step2 has executed");
				return RepeatStatus.FINISHED;
			}).build();
	}

}
