# Multi-stage build
FROM gradle:8.6-jdk17 AS build
WORKDIR /app
COPY gradle.properties settings.gradle build.gradle ./
COPY todo-web/build.gradle ./todo-web/
COPY todo-web/src ./todo-web/src/
RUN gradle :todo-web:build -x test

FROM registry.access.redhat.com/ubi8/openjdk-17:1.18
ENV LANGUAGE='en_US:en'
COPY --from=build /app/todo-web/build/quarkus-app/lib/ /deployments/lib/
COPY --from=build /app/todo-web/build/quarkus-app/*.jar /deployments/
COPY --from=build /app/todo-web/build/quarkus-app/app/ /deployments/app/
COPY --from=build /app/todo-web/build/quarkus-app/quarkus/ /deployments/quarkus/
EXPOSE 8081
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"
ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
