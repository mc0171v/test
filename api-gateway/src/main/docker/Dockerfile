FROM java:8
VOLUME /tmp
ADD bell-sam-api-gateway-0.0.8.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
