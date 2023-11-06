FROM openjdk:17-oracle

WORKDIR /app
COPY target/contacts-1.0.0-SNAPSHOT.jar app.jar

CMD ["java","-jar","app.jar"]