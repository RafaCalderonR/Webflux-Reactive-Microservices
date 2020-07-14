FROM openjdk:11
VOLUME /tmp
ADD ./jars/microservice-factory-0.0.1-SNAPSHOT.jar service-factory.jar
ENTRYPOINT ["java" , "-jar", "/service-factory.jar"]