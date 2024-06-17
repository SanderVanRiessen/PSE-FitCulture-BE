FROM maven:3-jdk-8-alpine as builder

WORKDIR /usr/src/app

COPY . /usr/src/app
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:8-jre-alpine

COPY --from=builder /usr/src/app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]
