FROM openjdk:17-jdk-slim
LABEL authors="sasha"

WORKDIR /app

ENTRYPOINT ["top", "-b"]