FROM gradle:7.2.0-jdk17 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --chown=gradle:gradle . /home/gradle/src
USER root
COPY . .
RUN chown -R gradle /home/gradle/src
RUN gradle clean build -x test

FROM adoptopenjdk/openjdk15
ENV ARTIFACT_NAME=parking-1.0-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
ENV PORT=${PORT:-8080}
ENV JDBC_USER=${JDBC_USER:-postgres}
ENV JDBC_PASSWORD=${JDBC_PASSWORD:-password}
ENV JDBC_URL=${JDBC_URL:-jdbc:postgresql://localhost:5432/parking}
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE $PORT
ENTRYPOINT exec java $JAVA_OPTS -jar $ARTIFACT_NAME
