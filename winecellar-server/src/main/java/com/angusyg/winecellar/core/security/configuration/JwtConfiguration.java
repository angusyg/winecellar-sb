package com.angusyg.winecellar.core.security.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("security.jwt")
public class JwtConfiguration {
    private String uri;
    private String header;
    private String prefix;
    private int expiration;
    private String secret;
}

