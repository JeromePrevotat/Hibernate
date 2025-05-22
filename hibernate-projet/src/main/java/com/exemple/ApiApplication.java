package com.exemple;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.core.util.JacksonFeature;

public class ApiApplication extends ResourceConfig{
    public ApiApplication(){
        packages("com.exemple");
        register(JacksonFeature.class);
    }
}
