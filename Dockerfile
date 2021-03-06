FROM gradle:jdk11 AS BUILD
WORKDIR /home/gradle/src
COPY . /home/gradle/src
RUN gradle bootJar

FROM openjdk:11-jre-slim
WORKDIR /code
COPY --from=BUILD /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENV TZ="Europe/Berlin"
COPY wait-for-it.sh wait-for-it.sh
RUN ["chmod", "+x", "wait-for-it.sh"]

ENTRYPOINT ["./wait-for-it.sh", "database:3306", "--timeout=0", "--", "java", "-jar", "-Dspring.profiles.active=${FORUM_PRODUCTION_PROFILE}", "app.jar"]
