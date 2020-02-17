package com.github.lindenhoney.scraper.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Value
@EqualsAndHashCode(exclude = {"verses"})
@ToString(exclude = {"verses"})
public class Song {

    @NotBlank
    private final String title;

    private final String author;

    private final String album;

    @Valid
    @NotEmpty
    @Builder.Default
    private final List<Verse> verses = new ArrayList<>();
}
