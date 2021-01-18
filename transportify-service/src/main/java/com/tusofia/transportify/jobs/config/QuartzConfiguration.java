package com.tusofia.transportify.jobs.config;

import com.tusofia.transportify.jobs.TransferTransportsHistoryScheduler;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "quartz", name = "enabled", matchIfMissing = true)
public class QuartzConfiguration {
  @Bean
  public TransferTransportsHistoryScheduler configureAmplitudeScheduler(@Autowired Scheduler scheduler) {
    return new TransferTransportsHistoryScheduler(scheduler);
  }
}
