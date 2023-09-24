package com.manuel.backend.usersapp.backendusersapp.enums;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityEnums {
    public static final String BEARER = "Bearer ";
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLE_USER = "ROLE_USER";
}
