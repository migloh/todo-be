FROM eclipse-temurin
ARG JAR_FILE=build/libs/todo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY .env .env
ENTRYPOINT ["java", "-jar", "app.jar"]