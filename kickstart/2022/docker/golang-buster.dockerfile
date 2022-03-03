FROM debian:buster-slim
RUN apt-get update -y && apt-get upgarde -y && apt-get install -y golang