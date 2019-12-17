FROM openjdk:11-jre-slim

LABEL name="linden-honey-scraper" \
      maintainer="aliaksandr.babai@gmail.com"

ARG WORK_DIR=/linden-honey
WORKDIR $WORK_DIR

ENV SERVER_PORT=80 \
    JAVA_OPTS=""

COPY build/libs/*.jar .

EXPOSE $SERVER_PORT
CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar *.jar
