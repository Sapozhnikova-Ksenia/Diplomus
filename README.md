# Дипломный проект по профессии «Тестировщик»

## Документация
- [План автоматизации тестирования](https://github.com/Sapozhnikova-Ksenia/Diplomus/blob/master/docs/plan.md);
- [Отчёт по итогам тестирования](https://github.com/Sapozhnikova-Ksenia/Diplomus/blob/master/docs/Report.md);
- [Отчёт по итогам автоматизации тестирования](https://github.com/Sapozhnikova-Ksenia/Diplomus/blob/master/docs/Summary.md).

## Инструкция по запуску
1. Клонировать удаленный репозиторий в `Git Bash` с помощью команды `git clone https://github.com/Sapozhnikova-Ksenia/Diplomus`


2. Открыть проект с помощью `IntelliJ IDEA`


3. В`IntelliJ IDEA` открыть терминал, и запустить команду `docker-compose up -d` 


4. Запустить приложение, введя команду в терминале `IntelliJ IDEA`, в зависимости от выбранной базы данных
* при MySQL запустить команду `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar`
* при Postgres запустить команду `"java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar`


5. Открыть в браузере страницу [http://localhost:8080/](http://localhost:8080/) для проверки запуска приложения


6. Запустить имеющиеся тесты, введя команду в терминале `IntelliJ IDEA`, в зависимости от выбранной базы данных
* для MySQL запустить команду `./gradlew clean test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass`
* для Postgres запустить команду `./gradlew clean test -Durl=jdbc:postgresql://localhost:5432/app -Duser=app -Dpassword=pass`


7. Дождаться окончания прохождения всех тестов


8. Открыть новую вкладку терминала `IntelliJ IDEA` и выполнить команду `./gradlew allureServe` для создания документов отчетности
