package com.github.lindenhoney.scraper.service;

import com.github.lindenhoney.scraper.domain.Song;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;

public interface Scraper {

    Flux<Song> fetchSongs();

    default String getId() {
        return StringUtils.substringBeforeLast(this.getClass().getSimpleName().toLowerCase(), "scraper");
    }
}
