package com.jiuxian.boot.mossrose.autoconfigure;

import com.jiuxian.mossrose.MossroseProcess;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class MossroseApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        final ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        final MossroseProcess process = applicationContext.getBean(MossroseProcess.class);
        if(process != null) {
            process.run();
        }
    }

}
