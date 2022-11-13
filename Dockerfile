FROM openjdk:8-jdk-alpine
ADD target/TransferMoneyService-0.0.1-SNAPSHOT.jar TransferMoneyService.jar
EXPOSE 5500
ENTRYPOINT ["java","-jar","/TransferMoneyService.jar"]