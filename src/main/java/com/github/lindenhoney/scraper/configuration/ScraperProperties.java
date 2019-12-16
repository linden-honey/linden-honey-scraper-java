package com.github.lindenhoney.scraper.configuration;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

@Getter
@Setter
public class ScraperProperties {

    @NotBlank
    private String baseUrl;

    @Valid
    private final RetryProperties retry = new RetryProperties();

    @Getter
    @Setter
    public static class RetryProperties {
        private int maxRetries;
        private Duration firstBackoff;
        private Duration maxBackoff;
    }
}
