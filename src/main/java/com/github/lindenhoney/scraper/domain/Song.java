package com.github.lindenhoney.scraper.domain;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
