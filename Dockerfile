FROM gradle:7.2.0-jdk17 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --chown=gradle:gradle . /home/gradle/src
USER root
COPY . .
RUN chown -R gradle /home/gradle/src
RUN gradle clean build

FROM adoptopenjdk/openjdk15
ENV ARTIFACT_NAME=parking-1.0-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
ENV PARKING_API_PORT=${PARKING_API_PORT:-8080}
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE $PARKING_API_PORT
ENTRYPOINT exec java $JAVA_OPTS -jar $ARTIFACT_NAME
