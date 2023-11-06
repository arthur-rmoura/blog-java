FROM openjdk:8

# Adding files
RUN mkdir /opt/appl-poi/
COPY ./artefatos/appl-0.0.4.jar /
COPY ./application.properties /opt/appl-poi/
COPY ./application-dev.properties /opt/appl-poi/

EXPOSE 8080

CMD ["java", "-jar", "appl-0.0.4.jar"]