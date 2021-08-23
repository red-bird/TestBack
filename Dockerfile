FROM gradle:7.2.0-jdk16 as build

USER root
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --stacktrace

FROM openjdk:17-jdk-alpine

RUN mkdir -p  /usr/src/app/
WORKDIR /usr/src/app/
COPY --from=build /home/gradle/src/build/libs/RtBack-0.0.1.jar /usr/src/app
COPY ./mockup /usr/src/app/mockup
ENTRYPOINT ["java", "-jar", "RtBack-0.0.1.jar"]