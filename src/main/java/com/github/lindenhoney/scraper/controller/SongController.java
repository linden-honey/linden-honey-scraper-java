package com.github.lindenhoney.scraper.controller;

import com.github.lindenhoney.scraper.domain.Preview;
import com.github.lindenhoney.scraper.domain.Song;
import com.github.lindenhoney.scraper.service.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final Scraper scraper;

    @GetMapping(
            path = "/songs",
            produces = APPLICATION_JSON_VALUE
    )
    public Flux<Song> getSongs() {
        return scraper.fetchSongs();
    }

    @GetMapping(
            path = "/songs/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    public Mono<Song> getSong(@PathVariable("id") String id) {
        return scraper.fetchSong(id);
    }

    @GetMapping(
            path = "/songs",
            produces = APPLICATION_JSON_VALUE,
            params = "projection=preview"
    )
    public Flux<Preview> getPreviews(@RequestParam(name = "projection", required = false) String projection) {
        return scraper.fetchPreviews();
    }
}
