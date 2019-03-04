# syntax = docker/dockerfile:experimental

# ===============================
# [ Builder image ]
# ===============================
FROM openjdk:11-jdk-slim AS BUILDER

ARG WORK_DIR=/linden-honey

COPY . $WORK_DIR
WORKDIR $WORK_DIR

RUN ./gradlew build -x test

# ===============================
# [ Production image ]
# ===============================
FROM openjdk:11-jre-slim

LABEL name="linden-honey-scraper" \
      maintainer="aliaksandr.babai@gmail.com"

ARG WORK_DIR=/linden-honey

ENV SERVER_PORT=8080 \
    JAVA_OPTS=""

WORKDIR $WORK_DIR
COPY --from=BUILDER $WORK_DIR/build/libs/*.jar .

EXPOSE $SERVER_PORT
ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar *.jar"]
