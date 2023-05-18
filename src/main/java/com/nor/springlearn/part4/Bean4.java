package com.nor.springlearn.part4;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "java")
public class Bean4 {
    private String version;

    private String home;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "Bean4{" +
                "version='" + version + '\'' +
                ", home='" + home + '\'' +
                '}';
    }
}
