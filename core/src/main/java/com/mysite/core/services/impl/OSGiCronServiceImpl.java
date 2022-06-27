package com.mysite.core.services.impl;

import com.mysite.core.services.TagService;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = OSGiCronServiceImpl.class,immediate = true)
@Designate(ocd = OSGiCronServiceImpl.ServiceConfig.class )
public class OSGiCronServiceImpl implements Runnable {

    @ObjectClassDefinition(name="Sean_OSGi_CRON",
            description = "OSGi config with CRON and helloworld")
    public @interface ServiceConfig {
        @AttributeDefinition(
                name = "Service Name",
                description = "Enter service name.",
                type = AttributeType.STRING)
        public String cronServiceName() default "Sean OSGi CRON Impl";


        @AttributeDefinition(
                name = "CRON expression",
                description = "CRON for OSGi config"
        )
        public String scheduler_expression() default  "0 0/5 * * * ? *";

        @AttributeDefinition(
                name = "CRON message",
                description = "message to put into log file"
        )
        public String cron_message() default  "hello world";

    }


    private String cronServiceName;

    private Runnable job;
    private static final Logger LOG = LoggerFactory.getLogger(OSGiCronServiceImpl.class);

    private String cronExpression;
    @Reference
    private Scheduler scheduler;

    private ScheduleOptions scheduleOptions;

    private String cronMessage;


    public void run() {
        System.out.println("printing out message: " + cronMessage);
        LOG.info("**** Scheduler run : {}", cronMessage);
    }

    @Activate
    public void activate(ServiceConfig serviceConfig){

        System.out.println("\n ==============4 OSGiCronService ACTIVATE================");


        cronMessage = serviceConfig.cron_message();

        cronExpression = serviceConfig.scheduler_expression();

        scheduleOptions = scheduler.EXPR(cronExpression);
        scheduleOptions.name("paramedname");

        this.scheduler.schedule(this,  scheduleOptions);
    }

    @Modified
    public void modified(ServiceConfig serviceConfig){
        // Called whenever you modified an OSGi config property, like "cronServiceName" above.
        System.out.println("\n ==============CronService MODIFIED================");
        this.scheduler.unschedule("paramedname");

        cronMessage = serviceConfig.cron_message();

        cronExpression = serviceConfig.scheduler_expression();

        scheduleOptions = scheduler.EXPR(cronExpression);
        scheduleOptions.name("paramedname");

        this.scheduler.schedule(this,  scheduleOptions);
    }


    @Deactivate
    public void deactivate(ServiceConfig serviceConfig){
        System.out.println("\n ==============OSGiCronService DEACTIVATE================");
        this.scheduler.unschedule("paramedname");
    }



}
