package com.example.springbatchex;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer2 {

	@Id
	private long id;
	private String firstName;
	private String lastName;
	private String birthdate;
}
