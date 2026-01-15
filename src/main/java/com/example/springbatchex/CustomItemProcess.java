package com.example.springbatchex;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;

import lombok.NonNull;

public class CustomItemProcess implements ItemProcessor<Customer,Customer2> {
	private ModelMapper modelMapper = new ModelMapper();

	@Nullable
	@Override
	public Customer2 process(@NonNull Customer item) throws Exception {
		Customer2 customer2 = modelMapper.map(item, Customer2.class);
		return customer2;
	}
}
