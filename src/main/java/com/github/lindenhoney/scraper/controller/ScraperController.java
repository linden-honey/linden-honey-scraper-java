package com.github.lindenhoney.scraper.controller;

import com.github.lindenhoney.scraper.domain.Song;
import com.github.lindenhoney.scraper.service.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

@ConditionalOnBean(Scraper.class)
@RestController
@RequestMapping("/scrapers")
@RequiredArgsConstructor
public class ScraperController {

    private final List<Scraper> scrapers;

    protected Optional<Scraper> resolveScraper(String id) {
        return scrapers.stream()
                .filter(scraper -> Objects.equals(id, scraper.getId()))
                .findFirst();
    }

    @GetMapping(
            value = "{id}/songs",
            produces = {
                    APPLICATION_JSON_UTF8_VALUE,
                    APPLICATION_STREAM_JSON_VALUE
            }
    )

    public Flux<Song> fetchSongs(@PathVariable("id") String id) {
    return resolveScraper(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scraper not found"))
            .fetchSongs();
}
}
