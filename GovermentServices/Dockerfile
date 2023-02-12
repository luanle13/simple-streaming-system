FROM openjdk:11
RUN apt-get update
RUN apt-get install -y curl
COPY target/gss.jar application.jar
CMD ["java", "-cp", "application.jar", "com.simplestreamingsystem.goverment.Main"]