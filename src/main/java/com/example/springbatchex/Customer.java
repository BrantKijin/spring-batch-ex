package com.example.springbatchex;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

	private final long id;
	private final String firstname;
	private final String lastname;
	private final Date birthdate;
}
