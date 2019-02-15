package com.github.lindenhoney.scraper.service.impl;

import com.github.lindenhoney.scraper.config.ApplicationProperties;
import com.github.lindenhoney.scraper.config.ApplicationProperties.ScraperProperties;
import com.github.lindenhoney.scraper.service.Scraper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractScraper implements Scraper {

    protected final ScraperProperties properties;
    protected final Validator validator;
    protected final WebClient client;

    protected AbstractScraper(ApplicationProperties properties, Validator validator) {
        this.properties = getScraperProperties(this.getId(), properties);
        this.validator = validator;
        this.client = WebClient.create(this.properties.getBaseUrl());
    }

    protected <T> boolean validate(T bean) {
        final Set<ConstraintViolation<T>> violations = validator.validate(bean);
        final boolean isValid = violations.isEmpty();
        if (!isValid) {
            final List<String> messages = violations.stream()
                    .map(v -> String.format("%s %s, but have value \"%s\"", v.getPropertyPath(), v.getMessage(), v.getInvalidValue()))
                    .collect(Collectors.toList());
            log.warn("{} validation failed: {}", bean, messages);
        }
        return isValid;
    }

    protected static ScraperProperties getScraperProperties(String id, ApplicationProperties properties) {
        return Optional.of(properties)
                .map(ApplicationProperties::getScrapers)
                .map(scrapers -> scrapers.get(id))
                .orElseThrow(() -> new NoSuchElementException(String.format("Scraper properties with id='%s' not found", id)));
    }
}
