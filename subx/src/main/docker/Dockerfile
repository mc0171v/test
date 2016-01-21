FROM java:8
VOLUME /tmp
ADD bell-sam-subx-0.0.9.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
