package com.jiuxian.boot.mossrose.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mossrose")
public class MossroseProperties {

    private String name;
    private Integer port;
    private String loadBalancingMode;
    private String discoveryZk;
    private Boolean runOnMaster;
    private Boolean enableUi;
    private Integer uiPort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLoadBalancingMode() {
        return loadBalancingMode;
    }

    public void setLoadBalancingMode(String loadBalancingMode) {
        this.loadBalancingMode = loadBalancingMode;
    }

    public String getDiscoveryZk() {
        return discoveryZk;
    }

    public void setDiscoveryZk(String discoveryZk) {
        this.discoveryZk = discoveryZk;
    }

    public Boolean getRunOnMaster() {
        return runOnMaster;
    }

    public void setRunOnMaster(Boolean runOnMaster) {
        this.runOnMaster = runOnMaster;
    }

    public Boolean getEnableUi() {
        return enableUi;
    }

    public void setEnableUi(Boolean enableUi) {
        this.enableUi = enableUi;
    }

    public Integer getUiPort() {
        return uiPort;
    }

    public void setUiPort(Integer uiPort) {
        this.uiPort = uiPort;
    }
}
