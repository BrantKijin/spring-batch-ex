package com.example.springbatchex;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

public class JobResultListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {

	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		String jobName = jobExecution.getJobInstance().getJobName();
		long instanceId = jobExecution.getJobInstance().getInstanceId();
		BatchStatus status = jobExecution.getStatus();
		ExitStatus exitStatus = jobExecution.getExitStatus();
		Date createTime = jobExecution.getCreateTime();
		Date startTime = jobExecution.getStartTime();
		ExecutionContext executionContext = jobExecution.getExecutionContext();
		Map<String, JobParameter> parameters =
			jobExecution.getJobParameters().getParameters();

		Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
		for(StepExecution stepExecution : stepExecutions){
			System.out.println("");
			System.out.println("================================= Step name : " + stepExecution.getStepName() + " =====================================================");
			System.out.println("stepExecution.getStatus() : " + stepExecution.getStatus());
			System.out.println("stepExecution.getExitStatus() : " + stepExecution.getExitStatus());
			System.out.println("stepExecution.getCommitCount() : " + stepExecution.getCommitCount());
			System.out.println("stepExecution.getRollbackCount() : " + stepExecution.getRollbackCount());
			System.out.println("stepExecution.getReadCount() : " + stepExecution.getReadCount());
			System.out.println("stepExecution.getReadSkipCount() : " + stepExecution.getReadSkipCount());
			System.out.println("stepExecution.getStartTime() : " + stepExecution.getStartTime());
			System.out.println("stepExecution.getEndTime() : " + stepExecution.getEndTime());
			System.out.println("stepExecution.getWriteCount() : " + stepExecution.getWriteCount());
			System.out.println("stepExecution.getWriteSkipCount() : " + stepExecution.getWriteSkipCount());
			System.out.println("stepExecution.getFilterCount() : " + stepExecution.getFilterCount());
			System.out.println("stepExecution.getProcessSkipCount() : " + stepExecution.getProcessSkipCount());
			System.out.println("stepExecution.getSkipCount() : " + stepExecution.getSkipCount());
			System.out.println("stepExecution.getExecutionContext() : " + stepExecution.getExecutionContext());
		}
	}
}
