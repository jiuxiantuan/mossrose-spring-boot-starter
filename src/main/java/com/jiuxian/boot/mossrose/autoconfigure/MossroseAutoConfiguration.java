package com.jiuxian.boot.mossrose.autoconfigure;

import com.jiuxian.mossrose.MossroseProcess;
import com.jiuxian.mossrose.config.MossroseConfig;
import com.jiuxian.mossrose.spring.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MossroseProperties.class)
@EnableConfigurationProperties(MossroseProperties.class)
public class MossroseAutoConfiguration {

    @Autowired
    private MossroseProperties mossroseProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public MossroseConfig.Cluster mossroseConfig() {
        MossroseConfig.Cluster cluster = new MossroseConfig.Cluster();
        if(mossroseProperties.getName() != null) {
            cluster.setName(mossroseProperties.getName());
        }
        if(mossroseProperties.getDiscoveryZk() != null) {
            cluster.setDiscoveryZk(mossroseProperties.getDiscoveryZk());
        }

        return cluster;
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}
