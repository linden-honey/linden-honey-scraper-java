package com.github.lindenhoney.scraper.service;

import com.github.lindenhoney.scraper.domain.Song;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface Scraper {
    Flux<Song> fetchSongs();
}
