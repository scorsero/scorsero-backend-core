FROM openjdk:8-jdk-alpine

ADD . /scorsero_backend
WORKDIR /scorsero_backend
RUN apk update && apk add build-base postgresql-dev
RUN ./gradlew build

#CMD java -jar ./build/libs/scorsero-backend-core-0.0.1-SNAPSHOT.jar