FROM alpine:latest
WORKDIR /app

RUN apk update
# util tools (just in case)
RUN apk add vim
RUN apk add curl
# pre requisites for the project to work
RUN apk add openjdk11-jdk
RUN apk add maven

# copy the code to build it during the image creation
RUN mkdir -p codebase/src/.mvn
COPY src/ codebase/src/
COPY .mvn/ codebase/src/.mvn
COPY mvnw mvnw.cmd pom.xml codebase/

WORKDIR /app/codebase
RUN mkdir logs

# build & package
RUN mvn clean install compile package

EXPOSE 3030
# run the application
CMD ["java", "-jar", "target/vetapi-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
