package com.github.lindenhoney.scraper.service;

import com.github.lindenhoney.scraper.domain.Preview;
import com.github.lindenhoney.scraper.domain.Song;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Scraper {

    Flux<Song> fetchSongs();

    Mono<Song> fetchSong(String id);

    Flux<Preview> fetchPreviews();

}
