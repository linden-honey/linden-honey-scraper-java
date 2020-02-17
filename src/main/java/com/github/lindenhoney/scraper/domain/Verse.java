package com.github.lindenhoney.scraper.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Value
@EqualsAndHashCode(exclude = {"quotes"})
@ToString(exclude = {"quotes"})
public class Verse {

    @Valid
    @NotEmpty
    @Builder.Default
    private final List<Quote> quotes = new ArrayList<>();
}
