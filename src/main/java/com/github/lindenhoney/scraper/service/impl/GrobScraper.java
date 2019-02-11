package com.github.lindenhoney.scraper.service.impl;

import com.github.lindenhoney.scraper.config.ApplicationProperties;
import com.github.lindenhoney.scraper.domain.Song;
import com.github.lindenhoney.scraper.domain.SongPreview;
import com.github.lindenhoney.scraper.util.GrobParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import javax.validation.Validator;
import java.net.ConnectException;
import java.nio.charset.Charset;

@Slf4j
@ConditionalOnProperty(
        prefix = "linden-honey.scrapers",
        value = {"enabled", "grob.enabled"},
        havingValue = "true"
)
@Service
public class GrobScraper extends AbstractScraper {

    private static final String SOURCE_CHARSET = "windows-1251";

    public GrobScraper(ApplicationProperties properties, Validator validator) {
        super(properties.getScrapers().getGrob(), validator);
    }

    @Override
    public Flux<Song> fetchSongs() {
        return fetchPreviews()
                .map(SongPreview::getId)
                .flatMapSequential(this::fetchSong);
    }

    private Flux<SongPreview> fetchPreviews() {
        return client.get()
                .uri("texts")
                .exchange()
                .flatMap(response -> response.bodyToMono(byte[].class))
                .map(bytes -> new String(bytes, Charset.forName(SOURCE_CHARSET)))
                .flatMapMany(html -> Flux.fromStream(GrobParser.parsePreviews(html)))
                .filter(this::validate);
    }

    private Mono<Song> fetchSong(Long id) {
        log.trace("Fetching the song with id {}", id);
        return client.get()
                .uri(builder -> builder
                        .path("text_print.php")
                        .queryParam("area", "go_texts")
                        .queryParam("id", id)
                        .build())
                .exchange()
                .retryWhen(Retry
                        .anyOf(ConnectException.class)
                        .retryMax(properties.getRetry().getMaxRetries())
                        .exponentialBackoff(properties.getRetry().getFirstBackoff(), properties.getRetry().getMaxBackoff())
                        .doOnRetry(context -> log.trace("Performing retry of fetching the song with id {} (attempt {})", id, context.iteration())))
                .flatMap(response -> response.bodyToMono(byte[].class))
                .map(bytes -> new String(bytes, Charset.forName(SOURCE_CHARSET)))
                .flatMap(html -> Mono.justOrEmpty(GrobParser.parseSong(html)))
                .filter(this::validate);
    }
}
