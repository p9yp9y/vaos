mvn package -DskipTests
java -jar target/dependency/jetty-runner.jar target/*.war