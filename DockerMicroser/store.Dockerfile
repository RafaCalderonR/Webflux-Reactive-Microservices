FROM openjdk:11
VOLUME /tmp
ADD ./jars/microservice-store-0.0.1-SNAPSHOT.jar service-store.jar
ENTRYPOINT ["java" , "-jar", "/service-store.jar"]