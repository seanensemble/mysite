package com.mysite.core.models;

import java.util.List;

public interface OSGiConfigDemo {

    public String getServiceName();

    public String getCronServiceName();
    public int getServiceCount();
    public boolean isLiveData();
    public String[] getCountries() ;
    public String getRunModes();

}
