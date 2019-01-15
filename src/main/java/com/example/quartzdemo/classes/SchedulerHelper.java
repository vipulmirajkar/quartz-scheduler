package com.example.quartzdemo.classes;

import java.time.ZonedDateTime;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.quartzdemo.controller.EmailJobSchedulerController;
import com.example.quartzdemo.payload.ScheduleEmailRequest;
import com.example.quartzdemo.payload.ScheduleEmailResponse;

public class SchedulerHelper {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerHelper.class);

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
}