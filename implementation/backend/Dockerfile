FROM maven:3-amazoncorretto-17
COPY ./ ./
RUN ["mvn", "install", "-Dmaven.test.skip=true"]
CMD java -jar target/spiritanimal-0.0.1-SNAPSHOT.jar
