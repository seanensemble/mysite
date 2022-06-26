package com.mysite.core.services.impl;

import com.mysite.core.services.CronService;
import com.mysite.core.services.TagService;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = CronService.class,immediate = true)
public class CronServiceImpl implements CronService {

    private Runnable job;
    private static final Logger LOG = LoggerFactory.getLogger(TagService.class);

    @Reference
    private Scheduler scheduler;

    public CronServiceImpl() {
//        System.out.println("2 Inside constructor of CronServiceImpl...");
        job = new Runnable() {
            public void run() {
                System.out.println("2 Executing the job");
                LOG.info("2 Executing the job");
            }
        };
    }


    @Activate
    public void activate(ComponentContext componentContext){
//        System.out.println("\n ==============non-OSGI CronService ACTIVATE================");
//        job.run();
//        this.scheduler.schedule(this.job,  scheduler.NOW(3, 5));
//        String cronExpression = " 0/5 * * * * ? *";
//        this.scheduler.schedule(this.job,  scheduler.EXPR(cronExpression));
    }


}
