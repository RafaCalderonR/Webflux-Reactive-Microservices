FROM openjdk:11
VOLUME /tmp
ADD ./jars/microservice-assistance-0.0.1-SNAPSHOT.jar service-assistance.jar
ENTRYPOINT ["java" , "-jar", "/service-assistance.jar"]