package com.github.lindenhoney.scraper.domain;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class Preview {

    @NotNull
    private final String id;

    @NotBlank
    private final String title;
}
