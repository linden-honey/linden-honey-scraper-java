package com.github.lindenhoney.scraper.domain;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class Quote {

    @NotBlank
    private final String phrase;
}
