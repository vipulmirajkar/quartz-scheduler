package com.example.quartzdemo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quartzdemo.controller.EmailJobSchedulerController;
import com.example.quartzdemo.payload.ScheduleEmailRequest;
import com.example.quartzdemo.payload.ScheduleEmailResponse;

@SpringBootApplication
public class QuartzDemoApplication implements Job {
	private static final Logger logger = LoggerFactory.getLogger(QuartzDemoApplication.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDateTime dateTime = LocalDateTime.of(2019, 1, 15, 15, 49);
		ZoneId zone = ZoneId.systemDefault();

		ScheduleEmailRequest scheduleEmailRequest = new ScheduleEmailRequest();
		scheduleEmailRequest.setEmail("vipul.mirajkar95@gmail.com");
		scheduleEmailRequest.setSubject("Happy Birthday");
		scheduleEmailRequest.setBody("Wish you many many happy returns of the day!");
		scheduleEmailRequest.setDateTime(dateTime);
		System.out.println("LocalDate: " + dateTime);
		scheduleEmailRequest.setTimeZone(zone);
		System.out.println("Zone: " + zone);

		System.out.println(scheduleEmailRequest);
		scheduleEmail(scheduleEmailRequest);
	}

	public ScheduleEmailResponse scheduleEmail(ScheduleEmailRequest scheduleEmailRequest) {
		try {
			SchedulerFactory factory = new StdSchedulerFactory();
			Scheduler scheduler = factory.getScheduler();

			ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequest.getDateTime(),
					scheduleEmailRequest.getTimeZone());
			System.out.println(scheduleEmailRequest.getDateTime() + " " + scheduleEmailRequest.getTimeZone());
			System.out.println("scheduleEmail: " + dateTime);
			if (dateTime.isBefore(ZonedDateTime.now())) {
				ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
						"dateTime must be after current time");
				return scheduleEmailResponse;
			}

			EmailJobSchedulerController helper = new EmailJobSchedulerController(); // caller object

			JobDetail jobDetail = helper.buildJobDetail(scheduleEmailRequest);
			Trigger trigger = helper.buildJobTrigger(jobDetail, dateTime);
			System.out.println("Trigger Name: " + trigger.getCalendarName());
			scheduler.scheduleJob(jobDetail, trigger);

			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true, jobDetail.getKey().getName(),
					jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");

			return scheduleEmailResponse;
		} catch (SchedulerException ex) {
			logger.error("Error scheduling email", ex.getMessage());
			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
					"Error scheduling email. Please try later!");
			return scheduleEmailResponse;
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(QuartzDemoApplication.class, args);

		String exp = "0 13 15 * * ?";

		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		scheduler.start();

		JobDetail job = JobBuilder.newJob(QuartzDemoApplication.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(CronScheduleBuilder.cronSchedule(exp))
				.build();
		scheduler.scheduleJob(job, trigger);
	}
}