В сервисе используются:

    jooq
    bootstrap
    spring-boot

- Никакой валидации кроме стандартной на email не предусмотрел. 
- Сделал все поля ui обязательными.
- Для id контакта использовал UUID

---
**Локальный запуск:**

    1. cd database_docker
    2. ./docker-compose up
    3. cd ..
    4. mvn clean package
    5. docker-compose up

К базе данных можно подключиться:

    url = jdbc:postgresql://localhost:6432/contacts
    username = postgres
    password = postgres
    schema = contacts_schema

Сервис будет доступен на **localhost:16008**

---

[docker-compose.yaml](docker-compose.yaml)

    environment:

      DATASOURCE_URL: jdbc:postgresql://database:5432/contacts - адресс базы данных
      POSTGRES_PASSWORD: postgres
      POSTGRES_USER: postgres
      POSTGRES_DB: contacts

      CREATE_RANDOM_CONTACTS: "true" - инициализация бд рандомными данными
      LOGGING_LEVEL: DEBUG - уровень логирования
      COUNT_GENERATED_CONTACT - количетсво генерируемых контактов

