FROM maven:3-jdk-8-slim
COPY . usr/madfat/atm
WORKDIR usr/madfat/atm
CMD ["mvn","spring-boot:run"]