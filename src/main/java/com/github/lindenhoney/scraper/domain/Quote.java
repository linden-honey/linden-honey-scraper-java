package com.github.lindenhoney.scraper.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Quote {

    @NotBlank
    private final String phrase;
}
