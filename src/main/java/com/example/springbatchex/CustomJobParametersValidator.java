package com.example.springbatchex;

import javax.annotation.Nullable;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class CustomJobParametersValidator implements JobParametersValidator {
	@Override
	public void validate(@Nullable JobParameters jobParameters) throws JobParametersInvalidException {

		if(!StringUtils.hasText(jobParameters.getString("name"))){
			throw new JobParametersInvalidException("name is required");
		}
	}
}
