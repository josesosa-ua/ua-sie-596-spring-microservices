FROM openjdk:21-slim

LABEL maintainer="Jose Sosa <josesosa@arizona.edu>"

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

ENV JAVA_OPTS="--add-opens java.base/java.nio.charset=ALL-UNNAMED"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]
