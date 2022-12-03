FROM openjdk:11
EXPOSE 5001

# Specify JAR location
ARG JAR_FILE=target/skillprofile.query-1.0.0.jar
# Copy the JAR
COPY ${JAR_FILE} skillprofile_query.jar
ENTRYPOINT ["java","-jar","/skillprofile_query.jar"]
