package com.gl.procamp.helpers.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AuthResponse {
    public String expires;
    public String token;
}
