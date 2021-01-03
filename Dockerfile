FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar sample.jar
ENTRYPOINT ["java","-jar","/sample.jar"]