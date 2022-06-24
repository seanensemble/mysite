package com.mysite.core.models.impl;

import com.mysite.core.models.OSGiConfigDemo;
import com.mysite.core.services.OSGiConfig;
import com.mysite.core.services.OSGiCronService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = OSGiConfigDemo.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OSGiConfigDemoImpl implements OSGiConfigDemo{

    /*--------Start Tutorial #31--------*/
    @OSGiService
    OSGiConfig oSGiConfig;

    @OSGiService
    OSGiCronService oSGiCronService;

    @Override
    public String getCronServiceName() {
        System.out.println("*****getCronServiceName inside OSGiConfigDemoImpl*****");
        String cronServiceName = oSGiCronService.getCronServiceName();

        System.out.println(cronServiceName);

        return cronServiceName;
    }

    @Override
    public String getServiceName() {
        System.out.println("*****getServiceName inside OSGiConfigDemoImpl*****");
        return oSGiConfig.getServiceName();
    }

    @Override
    public int getServiceCount() {
        return oSGiConfig.getServiceCount();
    }

    @Override
    public boolean isLiveData() {
        return oSGiConfig.isLiveData();
    }

    @Override
    public String[] getCountries() {
        return oSGiConfig.getCountries();
    }

    @Override
    public String getRunModes() {
        return oSGiConfig.getRunModes();
    }
    /*--------End Tutorial #31--------*/


}
