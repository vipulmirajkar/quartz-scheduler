package com.example.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.classes.SchedulerHelper;
import com.example.demo.payload.ScheduleEmailRequest;

@SpringBootApplication
public class SpringQuartzApplication implements Job {
	private static final Logger logger = LoggerFactory.getLogger(SpringApplication.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDateTime dateTime = LocalDateTime.of(2019, 1, 16, 13, 50);
		ZoneId zone = ZoneId.of("Asia/Kolkata");

		ScheduleEmailRequest scheduleEmailRequest = new ScheduleEmailRequest();
		scheduleEmailRequest.setEmail("vipul.mirajkar95@gmail.com");
		scheduleEmailRequest.setSubject("Happy Birthday");
		scheduleEmailRequest.setBody("Wish you many many happy returns of the day!");
		scheduleEmailRequest.setDateTime(dateTime);
		logger.info("dateTime: " + dateTime);
		scheduleEmailRequest.setTimeZone(zone);
		logger.info("zone: " + zone);
		//checking commit

		System.out.println(scheduleEmailRequest);
		SchedulerHelper helper = new SchedulerHelper();
		helper.scheduleEmail(scheduleEmailRequest);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringQuartzApplication.class, args);

		String exp = "0 33 13 * * ?";

		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		scheduler.start();

		JobDetail job = JobBuilder.newJob(SpringQuartzApplication.class).build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule(exp))
				.build();
		scheduler.scheduleJob(job, trigger);
	}
}