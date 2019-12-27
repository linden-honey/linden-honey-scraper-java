package com.github.lindenhoney.scraper.service;

import com.github.lindenhoney.scraper.configuration.ScraperProperties;
import com.github.lindenhoney.scraper.domain.Preview;
import com.github.lindenhoney.scraper.domain.Song;
import com.github.lindenhoney.scraper.util.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import javax.validation.Validator;
import java.net.ConnectException;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class DefaultScraper extends AbstractScraper {

    private static final Charset SOURCE_CHARSET = Charset.forName("cp1251");

    public DefaultScraper(ScraperProperties properties, Validator validator) {
        super(properties, validator);
    }

    @Override
    public Flux<Song> fetchSongs() {
        log.debug("Songs fetching started");
        return fetchPreviews()
                .map(Preview::getId)
                .flatMapSequential(this::fetchSong)
                .doOnError(throwable -> log.error("Unexpected error happened during songs fetching", throwable))
                .doOnComplete(() -> log.debug("Songs fetching successfully finished"));
    }

    @Override
    public Flux<Preview> fetchPreviews() {
        log.debug("Previews fetching started");
        return client.get()
                .uri("texts")
                .exchange()
                .flatMap(response -> response.bodyToMono(byte[].class))
                .map(bytes -> new String(bytes, SOURCE_CHARSET))
                .flatMapMany(html -> Flux.fromStream(Parser.parsePreviews(html)))
                .filter(this::validate)
                .doOnComplete(() -> log.debug("Previews fetching successfully finished"));
    }

    @Override
    public Mono<Song> fetchSong(String id) {
        log.debug("Fetching song with id {}", id);
        return client
                .get()
                .uri(builder -> builder
                        .path("text_print.php")
                        .queryParam("area", "go_texts")
                        .queryParam("id", id)
                        .build())
                .exchange()
                .retryWhen(Retry
                        .anyOf(ConnectException.class)
                        .retryMax(properties.getRetry().getMaxRetries())
                        .exponentialBackoffWithJitter(properties.getRetry().getFirstBackoff(), properties.getRetry().getMaxBackoff())
                        .doOnRetry(context -> {
                            log.debug("Fetching attempt({}) for song with id {} is failed.", context.iteration() - 1, id);
                            log.debug("Retry is caused by: \"{}\"", context.exception().getMessage());
                        }))
                .flatMap(response -> response.bodyToMono(byte[].class))
                .map(bytes -> new String(bytes, SOURCE_CHARSET))
                .flatMap(html -> Mono.justOrEmpty(Parser.parseSong(html)))
                .filter(this::validate)
                .doOnSuccess(song -> log.debug(
                        "Successfully fetched song with id {} and title \"{}\"",
                        id,
                        new String(song.getTitle().getBytes(UTF_8), Charset.defaultCharset())
                ));
    }
}
