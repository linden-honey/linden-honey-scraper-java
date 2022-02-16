package com.github.lindenhoney.scraper.util;

import com.github.lindenhoney.scraper.domain.Preview;
import com.github.lindenhoney.scraper.domain.Quote;
import com.github.lindenhoney.scraper.domain.Verse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTest {

    @Test
    @Tag("quote")
    @DisplayName("Should return empty optional of quote")
    void parseEmptyQuoteTest() {
        assertThat(Parser.parseQuote(null)).isNotPresent();
        assertThat(Parser.parseQuote("")).isNotPresent();
        assertThat(Parser.parseQuote(" ")).isNotPresent();
    }

    @Test
    @Tag("quote")
    @DisplayName("Should return quote object with a phrase string")
    void parseQuoteTest() {
        final String html = "Some phrase";
        assertThat(Parser.parseQuote(html))
                .isPresent()
                .get()
                .extracting(Quote::getPhrase)
                .isEqualTo("Some phrase");
    }

    @Test
    @Tag("quote")
    @DisplayName("Should replace all trailing spaces in a phrase")
    void parseQuoteWithTrailingSpacesTest() {
        final String html = "    Some text    with    trailing spaces  ";
        assertThat(Parser.parseQuote(html))
                .isPresent()
                .get()
                .extracting(Quote::getPhrase)
                .isEqualTo("Some text with trailing spaces");
    }

    @Test
    @Tag("quote")
    @DisplayName("Should convert all html formatting tags to regular text")
    void parseQuoteWithHtmlFormattingTagsTest() {
        final String html = "<strong>Some</strong> text<br> with html<br> <i>formatting</i> <b>tags</b>";
        assertThat(Parser.parseQuote(html))
                .isPresent()
                .get()
                .extracting(Quote::getPhrase)
                .isEqualTo("Some text with html formatting tags");
    }

    @Test
    @Tag("verse")
    @DisplayName("Should return empty optional of verse")
    void parseEmptyVerseTest() {
        assertThat(Parser.parseVerse(null)).isNotPresent();
        assertThat(Parser.parseVerse("")).isNotPresent();
        assertThat(Parser.parseVerse(" ")).isNotPresent();
    }

    @Test
    @Tag("verse")
    @DisplayName("Should return verse object with a quotes array")
    void parseVerseTest() {
        final String html = "Some phrase";
        assertThat(Parser.parseVerse(html))
                .isPresent()
                .map(Verse::getQuotes)
                .get()
                .asList()
                .extracting("phrase")
                .containsOnly("Some phrase");
    }

    @Test
    @Tag("verse")
    @DisplayName("Should parse all phrases into a quotes array")
    void parseVerseWithSeveralQuotesTest() {
        final String html = ""
                + "Some phrase 1"
                + "<br>"
                + "Some phrase 2"
                + "<br>"
                + "Some phrase 3";
        assertThat(Parser.parseVerse(html))
                .isPresent()
                .map(Verse::getQuotes)
                .get()
                .asList()
                .extracting("phrase")
                .containsOnly(
                        "Some phrase 1",
                        "Some phrase 2",
                        "Some phrase 3"
                );
    }

    @Test
    @Tag("lyrics")
    @DisplayName("Should return empty stream of verses")
    void parseEmptyLyricsTest() {
        assertThat(Parser.parseLyrics(null)).isEmpty();
        assertThat(Parser.parseLyrics("")).isEmpty();
        assertThat(Parser.parseLyrics(" ")).isEmpty();
    }

    @Test
    @Tag("lyrics")
    @DisplayName("Should return array with verses objects")
    void parseLyricsTest() {
        final String html = ""
                + "Some phrase 1"
                + "<br><br><br>"
                + "Some phrase 2"
                + "<br>"
                + "Some phrase 3"
                + "<br> <br>"
                + "Some phrase 4";
        assertThat(Parser.parseLyrics(html))
                .isNotEmpty()
                .flatExtracting("quotes")
                .extracting("phrase")
                .containsOnly(
                        "Some phrase 1",
                        "Some phrase 2",
                        "Some phrase 3",
                        "Some phrase 4"
                );
    }


    @Test
    @Tag("song")
    @DisplayName("Should return empty optional of song")
    void parseEmptySongTest() {
        assertThat(Parser.parseSong(null)).isNotPresent();
        assertThat(Parser.parseSong("")).isNotPresent();
        assertThat(Parser.parseSong(" ")).isNotPresent();
    }

    @Test
    @Tag("song")
    @DisplayName("Should return song object with all filled props")
    void parseSongTest() {
        final String html = ""
                + "<h2>Всё идёт по плану</h2>"
                + "<p><strong>Автор:</strong> Е.Летов</p>"
                + "<p><strong>Альбом:</strong> Всё идёт по плану</p>"
                + "<p>Some phrase 1<br>Some phrase 2</p>";
        assertThat(Parser.parseSong(html))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("title", "Всё идёт по плану")
                .hasFieldOrPropertyWithValue("author", "Е.Летов")
                .hasFieldOrPropertyWithValue("album", "Всё идёт по плану")
                .hasFieldOrPropertyWithValue("verses", Collections.singletonList(new Verse(
                        Arrays.asList(
                                new Quote("Some phrase 1"),
                                new Quote("Some phrase 2")
                        )
                )));
    }

    @Test
    @Tag("song")
    @DisplayName("Should return song object without title")
    void parseSongWithoutTitleTest() {
        final String html = ""
                + "<p><strong>Автор:</strong> Е.Летов</p>"
                + "<p><strong>Альбом:</strong> Всё идёт по плану</p>"
                + "<p></p>";
        assertThat(Parser.parseSong(html))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("title", null)
                .hasFieldOrPropertyWithValue("author", "Е.Летов")
                .hasFieldOrPropertyWithValue("album", "Всё идёт по плану")
                .hasFieldOrPropertyWithValue("verses", Collections.emptyList());
    }

    @Test
    @Tag("song")
    @DisplayName("Should return song object without author")
    void parseSongWithoutAuthorTest() {
        final String html = ""
                + "<h2>Всё идёт по плану</h2>"
                + "<p><strong>Альбом:</strong> Всё идёт по плану</p>"
                + "<p></p>";
        assertThat(Parser.parseSong(html))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("title", "Всё идёт по плану")
                .hasFieldOrPropertyWithValue("author", null)
                .hasFieldOrPropertyWithValue("album", "Всё идёт по плану")
                .hasFieldOrPropertyWithValue("verses", Collections.emptyList());
    }

    @Test
    @Tag("song")
    @DisplayName("Should return song object without album")
    void parseSongWithoutAlbumTest() {
        final String html = ""
                + "<h2>Всё идёт по плану</h2>"
                + "<p><strong>Автор:</strong> Е.Летов</p>"
                + "<p></p>";
        assertThat(Parser.parseSong(html))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("title", "Всё идёт по плану")
                .hasFieldOrPropertyWithValue("author", "Е.Летов")
                .hasFieldOrPropertyWithValue("album", null)
                .hasFieldOrPropertyWithValue("verses", Collections.emptyList());
    }

    @Test
    @Tag("preview")
    @DisplayName("Should return array with preview objects")
    void parsePreviews() {
        final String html = ""
                + "<ul id=\"abc_list\">"
                + "<li><a href=\"/texts/1056899068.html\">Всё идёт по плану</a></li>"
                + "<li><a href=\"\">Some title</a></li>"
                + "<li><a href=\"\"></a></li>"
                + "<li><a href=\"/texts/1056901056.html\">Всё как у людей</a></li>"
                + "</ul>";
        assertThat(Parser.parsePreviews(html))
                .isNotEmpty()
                .containsExactly(
                        new Preview("1056899068", "Всё идёт по плану"),
                        new Preview("1056901056", "Всё как у людей")
                );
    }

    @Test
    @Tag("preview")
    @DisplayName("Should return empty array")
    void parseEmptyPreviews() {
        assertThat(Parser.parsePreviews(StringUtils.EMPTY))
                .isEmpty();
    }
}
