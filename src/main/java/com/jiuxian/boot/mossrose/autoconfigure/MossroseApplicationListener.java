package com.jiuxian.boot.mossrose.autoconfigure;

import com.jiuxian.mossrose.MossroseProcess;
import com.jiuxian.mossrose.config.MossroseConfig;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MossroseApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        final ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        final MossroseConfig.Cluster cluster = applicationContext.getBean(MossroseConfig.Cluster.class);
        if(cluster != null) {
            MossroseConfig config = new MossroseConfig();
            config.setCluster(cluster);

            List<MossroseConfig.JobMeta> jobs = new ArrayList<>();
            final Map<String, Object> jobBeans = applicationContext.getBeansWithAnnotation(Job.class);
            if(jobBeans == null || jobBeans.isEmpty()) {
                throw new IllegalStateException("No job bean.");
            }
            for (Map.Entry<String, Object> entry : jobBeans.entrySet()) {
                MossroseConfig.JobMeta job = new MossroseConfig.JobMeta();
                job.setJobBeanName(entry.getKey());

                final Job jobAnnotation = entry.getValue().getClass().getAnnotation(Job.class);
                job.setId(jobAnnotation.id());
                job.setGroup(jobAnnotation.group());
                job.setCron(jobAnnotation.cron());
                job.setDescription(jobAnnotation.description());

                jobs.add(job);
            }
            config.setJobs(jobs);

            MossroseProcess process = new MossroseProcess(config);
            process.run();
        }
    }

}
