package com.github.lindenhoney.scraper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "linden-honey")
public class ApplicationProperties {

    @Valid
    private final Scrapers scrapers = new Scrapers();

    @Getter
    @Setter
    public static class Scrapers {

        private boolean enabled;

        @Valid
        private final Scraper grob = new Scraper();

        @Getter
        @Setter
        public static class Scraper {

            private boolean enabled;

            @NotNull
            private String baseUrl;

            @Valid
            private final Retry retry = new Retry();

            @Getter
            @Setter
            public static class Retry {
                private int maxRetries;
                private Duration firstBackoff;
                private Duration maxBackoff;
            }
        }
    }
}
