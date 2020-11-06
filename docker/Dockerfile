FROM openjdk:8-jdk-alpine
ADD ./build/libs/*.jar usr/src/app.jar
WORKDIR usr/src
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
