package com.gofar.batch_demo.listener;

import com.gofar.batch_demo.utils.BatchStats;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StatsListener implements JobExecutionListener {

    private final BatchStats batchStats;


    public StatsListener(BatchStats batchStats) {
        this.batchStats = batchStats;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        this.batchStats.end();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        this.batchStats.start();
    }
}
