FROM openjdk:16
VOLUME tmp
ARG JAR_FILE
COPY ${JAR_FILE} /jar/app.jar
ENTRYPOINT ['java','-jar','/jar/app.jar']