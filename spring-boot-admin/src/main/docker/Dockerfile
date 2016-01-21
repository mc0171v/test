FROM java:8
VOLUME /tmp
ADD bell-sam-spring-boot-admin-0.0.6-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
