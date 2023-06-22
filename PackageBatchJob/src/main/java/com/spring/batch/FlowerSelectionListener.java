package com.spring.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class FlowerSelectionListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {

		System.out.println("Executing before logic");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		String flowerType=stepExecution.getJobParameters().getString("type");
		return flowerType.equalsIgnoreCase("roses")?new ExitStatus("TRIM_REQD"):new ExitStatus("NO_TRIM_REQ");
	}

}
