package com.mysite.core.services.impl;

import com.mysite.core.services.OSGiCronService;
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

@Component(service = OSGiCronService.class,immediate = true)
@Designate(ocd = OSGiCronServiceImpl.ServiceConfig.class )
public class OSGiCronServiceImpl implements OSGiCronService {

    @ObjectClassDefinition(name="Sean_OSGi_CRON",
            description = "OSGi config with CRON and helloworld")
    public @interface ServiceConfig {
        @AttributeDefinition(
                name = "Service Name",
                description = "Enter service name.",
                type = AttributeType.STRING)
        public String cronServiceName() default "Sean OSGi CRON Impl";
    }


    private String cronServiceName;

    private Runnable job;
    private static final Logger LOG = LoggerFactory.getLogger(TagService.class);

    @Reference
    private Scheduler scheduler;


    @Activate
    public void activate(ServiceConfig serviceConfig){
        System.out.println("\n ==============2 OSGiCronService ACTIVATE================");
        cronServiceName=serviceConfig.cronServiceName();

        job = new Runnable() {
            public void run() {
                String toPrint = String.format("OSGiCronService Executing the job %s", cronServiceName);

                System.out.println(toPrint);
                LOG.info(toPrint);
            }
        };

        this.scheduler.schedule(this.job,  scheduler.NOW(3, 5));
//        String cronExpression = " 0/5 * * * * ? *";
//        this.scheduler.schedule(this.job,  scheduler.EXPR(cronExpression));
    }

    @Deactivate
    public void deactivate(ComponentContext componentContext){
        System.out.println("\n ==============OSGiCronService DEACTIVATE================");
//        this.scheduler.unschedule();
    }


    @Override
    public String getCronServiceName() {
        return cronServiceName;
    }

}
