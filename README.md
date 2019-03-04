# Linden Honey Scraper Spring

> Lyrics scraper service powered by Spring Boot

[![java version][java-image]][java-url]
[![build status][travis-image]][travis-url]
[![release][release-image]][release-url]
[![license][license-image]][license-url]

[java-image]: https://img.shields.io/badge/java-%3E%3D11-brightgreen.svg?style=flat-square
[java-url]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[release-image]: https://img.shields.io/github/release/linden-honey/linden-honey-scraper-spring.svg?style=flat-square
[release-url]: https://github.com/linden-honey/linden-honey-scraper-spring/releases
[travis-image]: https://img.shields.io/travis/linden-honey/linden-honey-scraper-spring/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/linden-honey/linden-honey-scraper-spring
[license-image]: https://img.shields.io/github/license/mashape/apistatus.svg?style=flat-square
[license-url]: https://github.com/linden-honey/linden-honey-scraper-spring/blob/master/LICENSE

REST API for the lyrics of __Yegor Letov__ and his post-punk/psychedelic rock band __Grazhdanskaya Oborona (Civil Defense)__

## Technologies

* [Java](https://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Gradle](https://gradle.org/)
* [Spring](https://spring.io/)
* [PostgreSQL](https://www.postgresql.org/)

## Usage

### Local

Run application:
```
./gradlew bootRun
```

Run tests (required clean pre-configured database):
```
./gradlew test
```

Build application artifact (with tests - required clean pre-configured database):
```
./gradlew build
```

Build application artifacts (without tests):
```
./gradlew build -x test
```

### Docker

Bootstrap project using docker-compose:
```
docker-compose up
```

Stop and remove containers, networks, images, and volumes:
```
docker-compose down
```

## Application instance

[https://linden-honey-scraper-spring.herokuapp.com](https://linden-honey-scraper-spring.herokuapp.com)
