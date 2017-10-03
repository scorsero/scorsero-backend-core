FROM openjdk:8-jdk-alpine

ADD . /scorsero_backend
WORKDIR /scorsero_backend
RUN ./gradlew clean
RUN apk update && apk add build-base postgresql-dev

#CMD java -jar ./build/libs/scorsero-backend-core-0.0.1-SNAPSHOT.jar