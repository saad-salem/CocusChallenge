FROM openjdk:11-jdk
ENV PORT 8080
EXPOSE 8080
COPY target/challenge-0.0.1-SNAPSHOT.jar cocus-challenge-1.0.0.jar
ENTRYPOINT ["java","-jar","/cocus-challenge-1.0.0.jar"]