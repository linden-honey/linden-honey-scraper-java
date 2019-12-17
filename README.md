# Linden Honey Scraper Spring

> Lyrics scraper service powered by Spring Boot

[![java version][java-image]][java-url]
[![build status][ci-image]][ci-url]
[![release][release-image]][release-url]
[![license][license-image]][license-url]

[java-image]: https://img.shields.io/badge/java-%3E%3D11-brightgreen.svg?style=flat-square
[java-url]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[release-image]: https://img.shields.io/github/release/linden-honey/linden-honey-scraper-spring.svg?style=flat-square
[release-url]: https://github.com/linden-honey/linden-honey-scraper-spring/releases
[ci-image]: https://img.shields.io/travis/linden-honey/linden-honey-scraper-spring/master.svg?style=flat-square
[ci-url]: https://travis-ci.org/linden-honey/linden-honey-scraper-spring
[license-image]: https://img.shields.io/github/license/mashape/apistatus.svg?style=flat-square
[license-url]: https://github.com/linden-honey/linden-honey-scraper-spring/blob/master/LICENSE

## Technologies

- [Java](https://openjdk.java.net/)
- [Gradle](https://gradle.org/)
- [Spring](https://spring.io/)

## Usage

### Local

Build application artifacts:
```
./gradlew build
```

Build application artifacts (without tests):
```
./gradlew build -x test
```

Run tests:
```
./gradlew test
```

Run application:
```
./gradlew bootRun
```

### Docker

Require already built application artifacts

Bootstrap full project using docker-compose:
```
docker-compose up
```

Bootstrap project excluding some services using docker-compose:
```
docker-compose up  --scale [SERVICE=0...]
```

Stop and remove containers, networks, images:
```
docker-compose down
```

## Application instance

[https://linden-honey-scraper-spring.herokuapp.com](https://linden-honey-scraper-spring.herokuapp.com)
