FROM java:8
EXPOSE 30000

VOLUME /tmp
ADD target/glen-eureka-0.0.1-SNAPSHOT.jar /glen-eureka-0.0.1-SNAPSHOT.jar
RUN bash -c 'touch /glen-eureka-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","/glen-eureka-0.0.1-SNAPSHOT.jar"]
