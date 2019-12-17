package com.github.lindenhoney.scraper.service;

import com.github.lindenhoney.scraper.domain.Song;
import reactor.core.publisher.Flux;

public interface Scraper {
    Flux<Song> fetchSongs();
}
