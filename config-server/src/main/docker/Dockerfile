FROM java:8
VOLUME /tmp
ADD bell-sam-config-server-0.0.9-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
RUN mkdir -p /root/.ssh
RUN chmod 700 /root/.ssh
COPY id_rsa.bell-sam-config /root/.ssh/id_rsa.bell-sam-config
RUN chmod 600 /root/.ssh/id_rsa.bell-sam-config
RUN bash -c 'echo -e "Host bell-sam-config.github.com\n\tHostName github.com\n\tUser git\n\tIdentityFile /root/.ssh/id_rsa.bell-sam-config\n\tIdentitiesOnly yes\n\tStrictHostKeyChecking no\n" >> /root/.ssh/config >> /root/.ssh/config'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
