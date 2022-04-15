## ПЛАН по автоматизации тестирования сервиса приобретения Туров

### Валидные данные:
1) Номер карты: числовое значение из 16 цирф, не равное нулям, не допускается использование буквенных и символьных выражений
   (значение карты APPROVED: 4444 4444 4444 4441, значение карты DECLINED: 4444 4444 4444 4442;)
2) Месяц - числовое значение из двух цифр (от 01 до 12), в случае указания в поле "Год" текущего год, поле "Месяц" должно содержать числовое значение, большее или равное текущему месяцу.
3) Год - числовое значение из двух цифр, равное текущему году, либо трем последующим.
4) Персональные данные человека - имя и фамилия владельца карты, состоящие из двух или более латинских символов, но менее 71, разделенные между собой пробелом (значения имени или фамиии могут содержать дефис);
5) CVC/CVV - числовое значение из трех цифр (не равное 000).

### Перечень сценариев:

* Операция по введению в форму отправки (и обычную, и кредитную) валидных данных
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Ввести валидные данные плательщика
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция выполнена успешно, появилось уведомление об одобрении банком, в базе данных появилась запись со статусом APPROVED


* Операция по приобретению тура при отправке пустой формы
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Не заполнять поля формы данными плательщика
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле каждого поля появилось уведомлении о необходимости заполнения/неверном формате, в базу данных новая запись не внесена


* Операция по приобретению тура при вводе в форму (и обычную, и кредитную) не валидных данных карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Ввести не валидный номер карты 4444 4444 4444 4442, все остальные значения ввести валидные
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция выполнена, появилось уведомление об отказе банком, в базе данных появилась запись со статусом DECLINED


* Отправка формы с пустым полем номера карты 
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Оставить пустым поле ввода номера карты, остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода номера карты появилось уведомлении о необходимости заполнения, в базу данных новая запись не внесена


* Отправка формы с невалидным значением в поле заполнения номера карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Ввести невалидное значение номера карты (количеством символов более или менее 16 цифр / буквенное значение / символьное значение/ нулевое значение), остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода номера карты появилось уведомлении о неверном формате, в базу данных новая запись не внесена


* Отправка формы с пустым полем месяца (срока действия) карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Оставить пустым поле ввода месяца срока действия карты, остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода месяца срока действия карты появилось уведомлении о необходимости заполнения, в базу данных новая запись не внесена


* Отправка формы с невалидным значением в поле Месяца срока действия карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Ввести невалидное значение месяца срока действия карты (количеством символов более или менее 2 цифр / числовое значение свыше 13/ в случае указания в поле "Год" текущего года установить в поле "Месяц" числовое значение, меньшее текущего месяца/ буквенное значение / символьное значение / нулевое значение), остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода месяца срока действия карты появилось уведомлении о неверном формате, в базу данных новая запись не внесена


* Отправка формы с пустым полем года (срока действия) карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Оставить пустым поле ввода года срока действия карты, остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода года срока действия карты появилось уведомлении о необходимости заполнения, в базу данных новая запись не внесена


* Отправка формы с невалидным значением в поле года срока действия карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Ввести невалидное значение года срока действия карты (количеством символов более или менее 2 цифр, не равное текущему году, либо трем последующим / буквенное значение / символьное значение / нулевое значение), остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода года срока действия карты появилось уведомлении о неверном формате, в базу данных новая запись не внесена


* Отправка формы с пустым полем имени владельца карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Оставить пустым поле ввода имени владельца карты, остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода имени владельца карты появилось уведомлении о необходимости заполнения, в базу данных новая запись не внесена


* Отправка формы с невалидным значением в поле имени владельца карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Ввести невалидное значение имени владельца карты ( количеством латинских символов более 71 или менее 2 / валидным количеством латинских букв, не разделенных между собой пробелом / валидным количеством символов кириллицы/ валидным количеством латинских символов с использованием цифровых и символьных выражений ), остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода имени владельца карты появилось уведомлении о неверном формате, в базу данных новая запись не внесена


* Отправка формы с пустым полем ввода CVC/CVV карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Оставить пустым поле ввода CVC/CVV карты, остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода CVC/CVV карты появилось уведомлении о необходимости заполнения, в базу данных новая запись не внесена


* Отправка формы с невалидным значением в поле ввода CVC/CVV карты
1. Зайти на страницу покупки
2. Нажать кнопку "Купить"/ "Купить в кредит"
3. Ввести невалидное значение CVC/CVV карты ( количеством цифровых символов, не равным 3 / количеством цифровых символов, равным 3, но состоящим из нулей / валидным количеством символов кириллицы/ валидным количеством латинских символов / валидным количеством символьных выражений т.е. спецсимволов ), остальные поля заполнить валидными значениями
4. Нажать кнопку "Продолжить"

Ожидаемый результат: Операция не выполнена, возле поля ввода CVC/CVV карты появилось уведомлении о неверном формате, в базу данных новая запись не внесена



### Перечень используемых инструментов
* Java 11 — Универсальный язык реализации проекта, поддерживает работу различных операционных систем, легок в использовании, распространен, за счет чего большая часть сотрудников без труда может взаимодействовать с кодом и вносить правки.
* IntelliJ IDEA — интегрированная среда разработки программного обеспечения. Мощный инструмент для работы с Java. Имеет множество гибких настроек и большое число плагинов.
* Gradle —  система автоматической сборки проектов, из преимуществ упрощенное подключение зависимостей, более лаконичные коды, возможность генерации и просмотра репортов.
* JUnit5 — удобный, более совершенный фреймворк для написания и запуска авто-тестов, содержит большое количество аннотаций и assert’ов.
* Selenide — фреймворк для автоматизированного тестирования веб-приложений на основе Selenium WebDriver, удобнее предшественника за счет автоматического подключения веб-драйвера, уменьшает время написания тестов.
* Faker - библиотека, генерирующая рандомные данные, необходимые для теста, не идеальный инструмент, но значительно помогает при выявлении проблем, в отличие от внесения "хардкода".
* Lombok - библиотека для уменьшения количества шаблонного кода, облегчения написания кода и минимизации затрачиваемого времени
* Docker - программное обеспечение, используемое для автоматизации развёртывания и управления приложениями в средах с поддержкой контейнеризации. 
* Allure - удобный фреймворк для создания достаточных, понятных и лаконичных отчётов по автотестам.
* Postman - инструмент для отправки запросов к API с понятным интерфейсом.
* Git и GitHub - удобная система контроля версий, с возможностью одновременной параллельной разработки, хранения кода, откатов версий и пр.
* DBeaver - бесплатный универсальный инструмент для работы с различными базами данных.
 

### Перечень и описание возможных рисков при автоматизации
* Отсутствует понятная и конкретная тестовая документация
* Отсутствует заказчик, и как следствие - нет возможности задать вопросы при их возникновении
* Увеличение риска допущения багов на продакшн за счет тестирования на "эмуляторе"
* Тестирование c двумя БД (MySQL и PostgreSQL) - дополнительные сложности настройки контейнеров Docker.
* Наличие основной работы с ненормированным графиком- как следствие возможны дополнительные затраты времени, и непредвиденные обстоятельства.

### Интервальная оценка с учётом рисков
* Разработка плана тестирования - 4 часа;
* Настройка тестового окружения - 10 часов;
* Написание автотестов - 96 часов;
* Подготовка отчета по итогам автоматизированного тестирования - 12 часов;
* Подготовка отчета по итогам автоматизации - 12 часов.
### Итого: 134 часа.

### План сдачи работ
* 15.04.21 - разработка плана тестирования;
* 22.04.21 - разработка и прогон автотестов;
* 24.04.21 - подготовка отчёта по итогам автоматизированного тестирования;
* 25.04.21 - подготовка отчета по итогам автоматизации.