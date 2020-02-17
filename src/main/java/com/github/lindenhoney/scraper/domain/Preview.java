package com.github.lindenhoney.scraper.domain;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class Preview {

    @NotBlank
    private final String id;

    @NotBlank
    private final String title;
}
