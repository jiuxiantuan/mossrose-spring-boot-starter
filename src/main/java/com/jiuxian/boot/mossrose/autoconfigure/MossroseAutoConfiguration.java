package com.jiuxian.boot.mossrose.autoconfigure;

import com.jiuxian.mossrose.MossroseProcess;
import com.jiuxian.mossrose.config.MossroseConfig;
import com.jiuxian.mossrose.spring.SpringContextHolder;
import com.jiuxian.mossrose.ui.RestMossroseUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@ConditionalOnClass(MossroseProperties.class)
@EnableConfigurationProperties(MossroseProperties.class)
public class MossroseAutoConfiguration {

    @Autowired
    private MossroseProperties mossroseProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public MossroseProcess mossroseProcess() {
        MossroseConfig config = new MossroseConfig();

        MossroseConfig.Cluster cluster = new MossroseConfig.Cluster();
        if (mossroseProperties.getName() != null) {
            cluster.setName(mossroseProperties.getName());
        }
        if (mossroseProperties.getDiscoveryZk() != null) {
            cluster.setDiscoveryZk(mossroseProperties.getDiscoveryZk());
        }
        if (mossroseProperties.getRunOnMaster() != null) {
            cluster.setRunOnMaster(mossroseProperties.getRunOnMaster());
        }
        if (mossroseProperties.getPort() != null) {
            cluster.setPort(mossroseProperties.getPort());
        }
        if (mossroseProperties.getLoadBalancingMode() != null) {
            cluster.setLoadBalancingMode(MossroseConfig.Cluster.LoadBalancingMode.valueOf(mossroseProperties.getLoadBalancingMode()));
        }

        config.setCluster(cluster);

        List<MossroseConfig.JobMeta> jobs = new ArrayList<>();
        final Map<String, Object> jobBeans = applicationContext.getBeansWithAnnotation(Job.class);
        if (jobBeans == null || jobBeans.isEmpty()) {
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

        return new MossroseProcess(config);
    }

    @Bean
    @ConditionalOnProperty(prefix = "mossrose", name = "enable-ui", havingValue = "true")
    public RestMossroseUI mossroseUI(MossroseProcess mossroseProcess) {
        final Integer uiPort = mossroseProperties.getUiPort();
        int port = uiPort != null ? uiPort : 7758;
        return new RestMossroseUI(mossroseProcess, port);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}
