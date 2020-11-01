FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/*.jar coinapi-app.jar
ENTRYPOINT ["java","-jar","/coinapi-app.jar"]
