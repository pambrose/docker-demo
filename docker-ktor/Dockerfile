FROM alpine
MAINTAINER Paul Ambrose "pambrose@mac.com"
RUN apk add openjdk17-jre

# Define the user to use in this instance to prevent using root that even in a container, can be a security risk.
ENV APPLICATION_USER ktor-demo

# Then add the user, create the /app folder and give permissions to our user.
RUN adduser --disabled-password --gecos '' $APPLICATION_USER
RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

# Mark this container to use the specified $APPLICATION_USER
USER $APPLICATION_USER

COPY ./build/libs/ktor-demo.jar /app/ktor-demo.jar
COPY src/main/resources /app/src/main/resources

# Make /app the working directory
WORKDIR /app

EXPOSE 8080

CMD []
# Launch java to execute the jar with defaults intended for containers.
ENTRYPOINT ["java", "-server", "-XX:+UseContainerSupport", "-Xmx2048m", "-Dlogback.configurationFile=/app/src/main/resources/logback-ktor-demo.xml", "-jar", "/app/ktor-demo.jar"]
