package com.tusofia.transportify.jobs;

import lombok.AllArgsConstructor;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

@AllArgsConstructor
public abstract class BaseJobScheduler {

  private final Scheduler scheduler;

  /**
   * Schedule a job if it doesn't exist, otherwise change its schedule.
   *
   * @param jobIdentity unique name identifying job
   * @param jobClass    jobClass class of the job class
   * @param jobSchedule job schedule (see {@link CronScheduleBuilder})
   */
  protected final void scheduleJob(String jobIdentity, Class<? extends Job> jobClass, ScheduleBuilder<?> jobSchedule)
          throws SchedulerException {
    JobKey jobKey = new JobKey(jobIdentity, jobClass.getName());
    TriggerKey triggerKey = new TriggerKey(jobIdentity, null);
    Trigger trigger =
            TriggerBuilder.newTrigger().forJob(jobKey).withIdentity(triggerKey).withSchedule(jobSchedule).build();
    if (scheduler.checkExists(jobKey)) {
      if (scheduler.checkExists(triggerKey)) {
        scheduler.rescheduleJob(scheduler.getTriggersOfJob(jobKey).get(0).getKey(), trigger);
      } else {
        scheduler.scheduleJob(trigger);
      }
    } else {
      JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();
      scheduler.scheduleJob(jobDetail, trigger);
    }
  }

  public abstract void start() throws SchedulerException;
}
