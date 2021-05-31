package com.gl.procamp.config;

public enum Env {
    PROD ("PROD"),
    UAT ("UAT");

    private final String envName;

    Env(String envName) {
        this.envName = envName;
    }

    @Override
    public String toString() {
        return this.envName;
    }
}
