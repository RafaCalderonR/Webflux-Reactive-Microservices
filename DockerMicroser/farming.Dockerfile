FROM openjdk:11
VOLUME /tmp
ADD ./jars/microservice-farming-0.0.1-SNAPSHOT.jar service-farming.jar
ENTRYPOINT ["java" , "-jar", "/service-farming.jar"]