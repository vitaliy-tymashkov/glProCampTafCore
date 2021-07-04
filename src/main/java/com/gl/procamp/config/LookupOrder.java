package com.gl.procamp.config;

import static com.gl.procamp.config.LookupEntity.DEFAULT_VALUES;
import static com.gl.procamp.config.LookupEntity.ENV;
import static com.gl.procamp.config.LookupEntity.FILE;
import static com.gl.procamp.config.LookupEntity.YAML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum LookupOrder {
    ENV_FILE_DEFAULT (new ArrayList<> (Arrays.asList(ENV, FILE, DEFAULT_VALUES))),
    FILE_DEFAULT (new ArrayList<> (Arrays.asList(FILE, DEFAULT_VALUES))),
    FILE_ENV_DEFAULT (new ArrayList<> (Arrays.asList(FILE, ENV, DEFAULT_VALUES))),
    DEFAULT_ENV_FILE (new ArrayList<> (Arrays.asList(DEFAULT_VALUES, ENV, FILE))),
    DEFAULT_ONLY (new ArrayList<> (Collections.singletonList(DEFAULT_VALUES))),
    YAML_ONLY (new ArrayList<> (Collections.singletonList(YAML))),
    ENV_YAML_FILE_DEFAULT (new ArrayList<> (Arrays.asList(ENV, YAML, FILE, DEFAULT_VALUES)));

    private List<LookupEntity> lookupOrder;

    LookupOrder(List<LookupEntity> lookupEntities){
        this.lookupOrder = lookupEntities;
    }

    public List<LookupEntity> getLookupOrder(){
        return lookupOrder;
    }
}
