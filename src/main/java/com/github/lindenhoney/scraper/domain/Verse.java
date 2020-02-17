package com.github.lindenhoney.scraper.domain;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"quotes"})
@ToString(exclude = {"quotes"})
public class Verse {

    @Valid
    @NotEmpty
    @Builder.Default
    private final List<Quote> quotes = new ArrayList<>();
}
