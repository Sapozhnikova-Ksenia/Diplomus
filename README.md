1. `git clone https://github.com/Sapozhnikova-Ksenia/Diplomus`

2. `docker-compose up -d`

3. для MySQL: `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar`

   для Postgres: `"java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar`

4. для MySQL: `./gradlew clean test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass`

   для Postgres: `./gradlew clean test -Durl=jdbc:postgresql://localhost:5432/app -Duser=app -Dpassword=pass`

5. ./gradlew allureServe
