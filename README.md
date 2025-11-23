Сервис для управления назначением ревьюверов на Pull Request'ы. Позволяет создавать команды, управлять пользователями, создавать PR и автоматически назначать ревьюверов из команды автора.

## Стек

- **Java 23**
- **Spring Boot 3.5.7**
- **PostgreSQL 18**
- **Maven**
- **Docker & Docker Compose**
- **MapStruct** 
- **Lombok** 
- **H2** 

## Структура проекта

```
AssignmentService/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/andrey/assignmentservice/
│   │   │       ├── controller/      # REST контроллеры
│   │   │       ├── service/         # Бизнес-логика
│   │   │       ├── repository/      # Репозитории JPA
│   │   │       ├── model/           # Сущности БД
│   │   │       ├── dto/             # DTO
│   │   │       └── exception/       # Обработка исключений
│   │   └── resources/
│   │       ├── application.yml      # Основная конфигурация
│   │       ├── application-dev.yml  # Конфигурация для разработки
│   │       ├── application-test.yml # Конфигурация для тестов
│   │       └── openapi.yml          # OpenAPI спецификация
│   └── test/
│       └── java/
│           └── com/andrey/assignmentservice/
│               └── controller/api/  # Интеграционные тесты
├── docker-compose.yml               # Docker Compose конфигурация
├── Dockerfile                       # Docker образ приложения
├── pom.xml                          # Maven зависимости
└── check_style.xml                  # Конфигурация Checkstyle
```

##  Пояснение к доп. заданию

##  Эндпоинт статистики

### GET `/stat/reviews`

Возвращает статистику по ревью Pull Request'ов.

**Пример:**
```json
{
  "total_pull_requests": 10,
  "open_pull_requests": 7,
  "merged_pull_requests": 3,
  "assignments_per_user": {
    "u1": 5,
    "u2": 3,
    "u3": 2
  },
  "reviews_per_pull_request": {
    "pr-1001": 2,
    "pr-1002": 1,
    "pr-1003": 2
  }
}
```

**Описание полей:**
- `total_pull_requests` - общее количество Pull Request'ов
- `open_pull_requests` - количество открытых PR
- `merged_pull_requests` - количество merged PR
- `assignments_per_user` - ключ - ID пользователя, значение - количество назначений на ревью
- `reviews_per_pull_request` - ключ - ID PR, значение - количество назначенных ревьюверов

### Список интеграционных тестов

#### 1. `PullRequestRestControllerIT`
Тестирует функциональность работы с Pull Request'ами:
- Создание PR с автоматическим назначением ревьюверов
- Проверка корректности назначения ревьюверов из команды автора

#### 2. `UserRestControllerIT`
Тестирует функциональность работы с пользователями:
- Получение списка PR для конкретного пользователя
- Обновление статуса активности пользователя

#### 3. `TeamRestControllerIT`
Тестирует функциональность работы с командами:
- Создание команды с участниками
- Проверка корректности сохранения связей между командой и пользователями

### Запуск тестов

```bash
# Запуск всех тестов
mvn test
```

##  Конфигурация Checkstyle

Проект использует **Checkstyle** для проверки качества кода. Конфигурация находится в файле `check_style.xml`.

Проверка запускается автоматически на фазе `validate` при сборке проекта.

## Инструкция по запуску проекта

### Запуск через Docker Compose

1. **Клонируйте репозиторий**:
```bash
git clone <repository-url>
cd AssignmentService
```

2. **Запустите проект**:
```bash
docker-compose up
```

Эта команда:
- Соберет Docker образ приложения
- Запустит контейнер с PostgreSQL
- Запустит контейнер с бекендом на порту 8080

Также, учтено, что при сборке проекта через докер, приложение должно проходить все необходимые тесты.
