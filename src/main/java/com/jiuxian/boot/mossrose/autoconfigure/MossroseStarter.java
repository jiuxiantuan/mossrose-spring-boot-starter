package com.jiuxian.boot.mossrose.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Map;

public class MossroseStarter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void init() {
        final Map<String, Object> jobBeans = applicationContext.getBeansWithAnnotation(Job.class);
    }
}
