package com.example.springbatchex;

import java.util.List;

import javax.annotation.Nullable;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class CustomItemStreamReader implements ItemStreamReader<String> {

	private final List<String> items;
	private int index = -1;
	private boolean restart = false;

	public CustomItemStreamReader(List<String> items) {
		this.items = items;
		this.index = 0;

	}

	@Nullable
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String item = null;

		if (this.index < this.items.size()) {
			item = this.items.get(this.index);
			index++;
		}

		if (this.index == 6 && !restart) {
			throw new RuntimeException("Restarting the stream");
		}

		return item;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (executionContext.containsKey("index")) {
			index = executionContext.getInt("index");
			this.restart = true;
		} else {
			index = 0;
			executionContext.put("index", index);
		}

	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.put("index", index);
	}

	@Override
	public void close() throws ItemStreamException {
		System.out.println("Closing the stream");
	}
}
