package com.example.springbatchex;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

public class CustomItemWriter implements ItemStreamWriter<String> {
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("");

	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("");
	}

	@Override
	public void close() throws ItemStreamException {
		System.out.println("");
	}

	@Override
	public void write(List<? extends String> list) throws Exception {
		list.forEach(System.out::println);
	}
}
