**Rest api для управления карточками студентов**

- Написан на основе Spring MVC.
- Сервер Tomcat 8.5.40.
- В качестве авторизации используется кастомный токен - запросы без хедера KEY:"pass" отклоняются.
- При необходимости его можно поменять в application.properties.
- База данных PostgreSQL, обращение через репозиторий.
Две таблицы - информация о студентах и предметы с id студента. 
- Используется Lombok и логирование Slf4j.
- Добавлены интеграционные тесты.
- Реализован GET метод по пути /api/v1/students
GET и DELETE /api/v1/students/1 для получения и удаления карточки студента по id.
PUT и POST /api/v1/students для сохранения и изменения карточки. Вариант json для POST/PUT (в post нет id)
{"id":4,"fullName":"test","birthday":"1993-12-20","group":2,"course":223,"subjects":["ECONOMY","HISTORY"]}
