FROM openjdk:11-jre-slim

ARG WORK_DIR=/app
WORKDIR $WORK_DIR

ENV SERVER_PORT=80 \
    JAVA_OPTS=""

COPY build/libs/*.jar .

EXPOSE $SERVER_PORT
CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar *.jar
