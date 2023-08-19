FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./mvnw -B dependency:go-offline

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/xyz-1.0.0.jar .
COPY docker-startup.sh .
RUN chmod +x docker-startup.sh
EXPOSE 8080
CMD ["./docker-startup.sh"]
