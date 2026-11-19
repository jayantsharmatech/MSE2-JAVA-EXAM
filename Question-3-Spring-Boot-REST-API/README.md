# Question 3: Spring Boot REST API

Implements the six requested endpoints with a layered controller/service/JPA repository and MySQL persistence. Validation rejects missing fields, invalid emails, overlong strings and scores outside 0-100. Duplicate IDs/emails return HTTP 409; missing IDs return 404.

## Run

1. Run `mysql -u root -p < sql/schema.sql` from this folder.
2. Set `DB_USERNAME` and `DB_PASSWORD` (optionally `DB_URL`).
3. Run `mvn spring-boot:run` (Java 17+).

The schema creates both the student and technical skill tables. Hibernate uses `ddl-auto=update` to make first startup easier; the supplied schema also documents the expected structure.

## Endpoints

- `POST /api/students` - add a student; returns 201.
- `GET /api/students` - retrieve all.
- `GET /api/students/{id}` - find by ID.
- `PUT /api/students/{id}/scores-skills` - update scores and skill set.
- `DELETE /api/students/{id}` - delete; returns 204.
- `GET /api/students/merit-list` - descending average score.
- `GET /api/students/search?query=...` - find by ID, email or course.

Example create body: `{"id":"S1001","name":"Asha Rao","email":"asha@example.com","courseName":"BCA","programmingScore":88,"mathematicsScore":76,"technicalSkills":["Java","SQL"]}`
