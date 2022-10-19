package com.example.register.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Schema(description = "Applications")
@Entity
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appId;

    @Column (name = "URL", nullable = false)
    private String url;

    @Version
    private Long version;


    protected App() {}

    public App(Long appId, String url) {
        this.appId = appId;
        this.url = url;
    }

    public Long getAppId() {
        return appId;
    }

    public String getUrl() {
        return url;
    }

    public Long getVersion() {
        return version;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
