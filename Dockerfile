#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#EXPOSE 8080
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#RUN addgroup -S spring && adduser -S spring -G spring
#RUN mkdir -p /app/
#RUN mkdir -p /app/logs/
#ADD target/blog.jar /app/app.jar
#ENV DATABASE_URL=jdbc:mysql://localhost:3306/blog
#ENV DATABASE_USERNAME=root
#ENV DATABASE_PASSWORD=upiver88
#ENTRYPOINT ["java","-jar","-Ddb.url=${DATABASE_URL}","-Ddb.username=${DATABASE_USERNAME}", "-Ddb.password=${DATABASE_PASSWORD}", "/app.jar"]


FROM tomcat:9.0-alpine
ADD target/blog.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
