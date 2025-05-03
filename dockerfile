FROM eclipse-temurin:17
LABEL maintainer="coisa@jaum.dev"
WORKDIR /app
COPY target/urlshortener-0.0.1-SNAPSHOT.jar /app/encurtador.jar
ENTRYPOINT ["java", "-jar", "encurtador.jar"]