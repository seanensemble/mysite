package com.mysite.core.services;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Activities {
    String getRandomActivity();
}