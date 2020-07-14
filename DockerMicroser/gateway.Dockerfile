FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ADD ./jars/microservice-zuul-0.0.1-SNAPSHOT.jar service-gateway.jar
ENTRYPOINT ["java", "-jar", "/service-gateway.jar"]

