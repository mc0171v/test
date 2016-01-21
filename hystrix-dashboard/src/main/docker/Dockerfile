FROM java:8
VOLUME /tmp
ADD bell-sam-hystrix-dashboard-0.0.4.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
