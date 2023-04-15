FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY . /app

RUN chmod -R +x /app

RUN ./mvnw install -DskipTests

ENTRYPOINT ["/bin/bash", "/app/entrypoint.sh"]
#CMD ["java", "-jar", "target/resume-builder-0.0.1.jar"]
