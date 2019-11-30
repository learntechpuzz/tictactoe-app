FROM node:latest AS tictactoe-ui
WORKDIR /app/tictactoe-ui
COPY web .
RUN npm install
RUN npm run build

FROM maven:latest AS tictactoe-service
WORKDIR /app/tictactoe-service
COPY . .
RUN mvn clean package

FROM java:8-jdk-alpine
WORKDIR /static
COPY --from=tictactoe-ui /app/tictactoe-ui/build/ .
VOLUME ["/app/tictactoe"]
WORKDIR /app
COPY --from=tictactoe-service /app/tictactoe-service/target/tictactoe-service-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "/app/tictactoe-service-0.0.1-SNAPSHOT.jar"]
