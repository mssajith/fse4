FROM openjdk:11
EXPOSE 5000

# Specify JAR location
ARG JAR_FILE=target/skillprofile.cmd-1.0.0.jar
# Copy the JAR
COPY ${JAR_FILE} skillprofile_cmd.jar
ENTRYPOINT ["java","-jar","/skillprofile_cmd.jar"]
