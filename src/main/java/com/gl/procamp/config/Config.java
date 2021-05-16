package com.gl.procamp.config;

import lombok.Getter;
import lombok.Setter;

public class Config {
    private static Config config;

    @Getter @Setter
    private String baseUrl;
    @Getter @Setter
    private String authBearerToken;
    @Getter @Setter
    private String loginUrl;

    private Config() {
    }

    public static Config getInstance(){
        if (config == null) {
            config = new Config();
        }
        return config;
    }
}
