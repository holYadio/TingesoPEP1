FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} monolitico-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/monolitico-0.0.1-SNAPSHOT.jar"]