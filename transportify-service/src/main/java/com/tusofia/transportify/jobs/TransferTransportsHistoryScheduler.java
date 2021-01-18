package com.tusofia.transportify.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.annotation.PostConstruct;

@Slf4j
public class TransferTransportsHistoryScheduler extends BaseJobScheduler {
  private static final String TRANSFER_TRANSPORTS_HISTORY_JOB = "TRANSFER_TRANSPORTS_HISTORY_JOB";

  public TransferTransportsHistoryScheduler(Scheduler scheduler) {
    super(scheduler);
  }
//* * * * * ? *
  @Override
  @PostConstruct
  public void start() throws SchedulerException {
    log.info("Scheduling Transfer Transports History Job");
    scheduleJob(TRANSFER_TRANSPORTS_HISTORY_JOB, TransferTransportsHistoryJob.class,
            CronScheduleBuilder.cronSchedule("0 0/1 8-17 * * ?"));
  }
}
