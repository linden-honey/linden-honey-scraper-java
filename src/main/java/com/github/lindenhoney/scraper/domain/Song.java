package com.github.lindenhoney.scraper.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"verses"})
@ToString(exclude = {"verses"})
public class Song {

    @NotBlank
    private String title;

    private String author;

    private String album;

    @Valid
    @NotNull
    private List<Verse> verses = new ArrayList<>();

    public Song(@NotBlank String title, String author, String album, @Valid @NotNull List<Verse> verses) {
        this.title = title;
        this.author = author;
        this.album = album;
        this.verses = verses;
    }
}
