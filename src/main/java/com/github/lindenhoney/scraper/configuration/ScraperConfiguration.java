package com.github.lindenhoney.scraper.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ScraperProperties.class)
public class ScraperConfiguration {
}
