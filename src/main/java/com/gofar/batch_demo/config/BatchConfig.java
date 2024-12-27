package com.gofar.batch_demo.config;

import com.gofar.batch_demo.listener.StatsListener;
import com.gofar.batch_demo.model.DataRow;
import com.gofar.batch_demo.model.ProcessedData;
import com.gofar.batch_demo.utils.BatchStats;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public BatchStats batchStats() {
        return new BatchStats();
    }

    @Bean
    public StatsListener statsListener(BatchStats batchStats) {
        return new StatsListener(batchStats);
    }

    @Bean
    public ExcelItemReader itemReader() {
        return new ExcelItemReader(new ClassPathResource("data.xlsx"));
    }


    @Bean
    public Job importJob(JobRepository jobRepository, Step step, StatsListener statsListener) {
        return new JobBuilder("importJob", jobRepository)
                .listener(statsListener)
                .flow(step)
                .end().build();
    }

    @Bean
    public Step step(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ExcelItemReader itemReader,
            DataProcessor itemProcessor,
            CustomItemWriter itemWriter
    ) {
        return new StepBuilder("step", jobRepository)
                .<DataRow, ProcessedData>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

}
