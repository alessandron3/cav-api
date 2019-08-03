FROM openjdk:8-jdk-alpine

EXPOSE 8081

VOLUME /application
COPY **/cav-api*.jar /application/app.jar
CMD ["/bin/sh"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/application/app.jar"]
