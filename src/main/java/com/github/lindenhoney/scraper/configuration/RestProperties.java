package com.github.lindenhoney.scraper.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties("application.rest")
@Data
public class RestProperties {
    @NotBlank
    private String basePath;
}
