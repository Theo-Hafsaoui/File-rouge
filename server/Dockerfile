FROM maven:3.9 as build-app
COPY src /server/src
COPY pom.xml /server/pom.xml
WORKDIR /server
RUN mvn clean package


FROM payara/micro:6.2023.11-jdk17
COPY --from=build-app /server/target/server.war $DEPLOY_DIR
