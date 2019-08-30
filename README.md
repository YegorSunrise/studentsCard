### Rest api для управления карточками студентов
#### Стек:
 - Spring MVC
 - Spring Security
 - Hibernate
 - PostgreSQL
 - Tomcat
 - junit
 - mockito
 - Lombok

Работа с api:
  Авторизация через заголовок KEY, значение которого задается в application.properties

Получение списка карточек студентов:
GET /api/v1/students

Получение карточки студента по id
GET /api/v1/students/1

Удаление карточки студента
DELETE /api/v1/students/1

Изменение карточки студента
PUT /api/v1/students/1
`{"fullName":"John"}`

Создание студента
POST /api/v1/students
`{"fullName":"Peter","birthday":"1993-12-20","group":2,"course":223,"subjects":["ECONOMY","HISTORY"]}`
