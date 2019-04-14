FROM java:8
MAINTAINER tiagoquin

COPY MockMock.jar /opt/app/MockMock.jar

EXPOSE 2525 8080

CMD ["java", "-jar", "/opt/app/MockMock.jar", "-p", "2525", "-h", "8080"]
