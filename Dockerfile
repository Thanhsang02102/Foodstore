
FROM maven:3.9-eclipse-temurin-21 AS build

COPY src /thanhsang/spring-mvc/src
COPY pom.xml /thanhsang/spring-mvc

WORKDIR /thanhsang/spring-mvc


RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine



COPY --from=build /thanhsang/spring-mvc/target/*.war /thanhsang/spring-mvc/app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/thanhsang/spring-mvc/app.war"]