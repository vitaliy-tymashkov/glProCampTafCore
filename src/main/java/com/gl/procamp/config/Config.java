package com.gl.procamp.config;

import lombok.Getter;
import lombok.Setter;

public class Config {
    private static Config config;

    @Getter @Setter
    private String authType;
    @Getter @Setter
    private String expiry;
    @Getter @Setter
    private String loginFrom;
    @Getter @Setter
    private String baseUrl;
    @Getter @Setter
    private String loginUrl;
    @Getter @Setter
    private String loginUrlApi;

    private Config() {
    }

    public static Config getInstance(){
        if (config == null) {
            config = new Config();
        }
        return config;
    }
}
