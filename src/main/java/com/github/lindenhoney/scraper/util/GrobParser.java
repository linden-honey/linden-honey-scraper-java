package com.github.lindenhoney.scraper.util;

import com.github.lindenhoney.scraper.domain.Quote;
import com.github.lindenhoney.scraper.domain.Song;
import com.github.lindenhoney.scraper.domain.SongPreview;
import com.github.lindenhoney.scraper.domain.Verse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class GrobParser {

    protected static Optional<Quote> parseQuote(String html) {
        return Optional.ofNullable(html)
                .filter(StringUtils::isNotBlank)
                .map(Jsoup::parseBodyFragment)
                .map(Element::text)
                .map(text -> text.replaceAll("\\s+", StringUtils.SPACE))
                .map(StringUtils::trimToNull)
                .map(Quote::new);
    }

    protected static Optional<Verse> parseVerse(String html) {
        return Optional.ofNullable(html)
                .filter(StringUtils::isNotBlank)
                .map(it -> Arrays.stream(html.split("<br>"))
                        .map(GrobParser::parseQuote)
                        .flatMap(Optional::stream)
                        .collect(Collectors.toList()))
                .map(Verse::new);
    }

    protected static Stream<Verse> parseLyrics(String html) {
        return Optional.ofNullable(html)
                .filter(StringUtils::isNotBlank)
                .stream()
                .flatMap(it -> Arrays.stream(it.split("(?:<br>\\s*){2,}"))
                        .map(GrobParser::parseVerse)
                        .flatMap(Optional::stream));
    }

    public static Optional<Song> parseSong(String html) {
        return Optional.ofNullable(html)
                .filter(StringUtils::isNotBlank)
                .map(Jsoup::parse)
                .map(document -> {
                    final String title = Optional.ofNullable(document.selectFirst("h2"))
                            .map(Element::text)
                            .map(StringUtils::trimToNull)
                            .orElse(null);
                    final String author = Optional.ofNullable(document.selectFirst("p:has(strong:contains(Автор))"))
                            .map(el -> StringUtils.substringAfterLast(el.text(), ": "))
                            .map(StringUtils::trimToNull)
                            .orElse(null);
                    final String album = Optional.ofNullable(document.selectFirst("p:has(strong:contains(Альбом))"))
                            .map(el -> StringUtils.substringAfterLast(el.text(), ": "))
                            .map(StringUtils::trimToNull)
                            .orElse(null);
                    final String lyricsHtml = document.selectFirst("p:last-of-type").html();
                    final List<Verse> verses = parseLyrics(lyricsHtml).collect(Collectors.toList());
                    return new Song(title, author, album, verses);
                });
    }

    public static Stream<SongPreview> parsePreviews(String html) {
        return Optional.ofNullable(html)
                .filter(StringUtils::isNotBlank)
                .map(Jsoup::parse)
                .stream()
                .flatMap(document -> document.select("#abc_list a")
                        .stream()
                        .map(link -> {
                            final Long id = Optional.ofNullable(link.attr("href"))
                                    .filter(StringUtils::isNotBlank)
                                    .map(path -> StringUtils.substringAfterLast(path, "/"))
                                    .map(path -> StringUtils.substringBeforeLast(path, "."))
                                    .map(NumberUtils::toLong)
                                    .filter(it -> !it.equals(0L))
                                    .orElse(null);
                            final String title = link.text();
                            return new SongPreview(id, title);
                        }));
    }
}
