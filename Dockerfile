FROM amazoncorretto:21-alpine

WORKDIR /movies

COPY target/smart-search-0.0.1-SNAPSHOT.jar /movies/movies-docker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/movies/movies-docker.jar"]