# OOPJ MSE-2: Student Profile and Skill Development System

This workspace contains one independent folder per programming question from the attached paper.

## Projects

| Folder | Question | What it contains |
|---|---|---|
| `Question-1-Student-Model` | Q1(a) | Encapsulated `Student` class and a small runnable demonstration. |
| `Question-2-Business-Logic-MySQL` | Q2(a) | Java collections business logic, MySQL JDBC persistence, schema and interactive console. |
| `Question-3-Spring-Boot-REST-API` | Q3(a) | Spring Boot REST API with MySQL persistence, validation and CRUD/merit endpoints. |

The PDF's final deliverables also ask for three AI prompts with brief reflections. A starter set is provided in `AI-Prompts-and-Reflections.md`; personalize the reflections to describe your own use and learning.

## Requirements

- JDK 17 or later
- Maven 3.6.3 or later
- MySQL 8.0 or later for Questions 2 and 3
- VS Code with the Extension Pack for Java (and Spring Boot Extension Pack for Q3, optional)

Open this whole folder in VS Code. Each question folder is a separate Maven project (Q1 can also be compiled directly).

## MySQL setup for Questions 2 and 3

1. Start MySQL.
2. Run the SQL file in the project's `sql/schema.sql` using MySQL Workbench or `mysql -u root -p < sql/schema.sql` from that project folder.
3. Set connection environment variables before launching:
   - `DB_URL` (Q2 defaults to `jdbc:mysql://localhost:3306/student_skill_q2_db`; Q3 defaults to `jdbc:mysql://localhost:3306/student_skill_db`)
   - `DB_USERNAME` (defaults to `root`)
   - `DB_PASSWORD` (set this to your MySQL password)
4. From the project folder run `mvn spring-boot:run` for Q3 or `mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java` for Q2.

For a quick Q2 in-memory demonstration without a running MySQL server, run `mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.args="--demo"`. The normal Q2 command uses MySQL and persists students there.

## API quick start (Q3)

After database setup and `mvn spring-boot:run`, the service listens on `http://localhost:8080`.

```bash
curl -X POST http://localhost:8080/api/students \
  -H 'Content-Type: application/json' \
  -d '{"id":"S1001","name":"Asha Rao","email":"asha@example.com","courseName":"BCA","programmingScore":88,"mathematicsScore":76,"technicalSkills":["Java","SQL"]}'

curl http://localhost:8080/api/students
curl http://localhost:8080/api/students/merit-list
curl -X PUT http://localhost:8080/api/students/S1001/scores-skills \
  -H 'Content-Type: application/json' \
  -d '{"programmingScore":91,"mathematicsScore":80,"technicalSkills":["Java","SQL","Spring"]}'
curl -X DELETE http://localhost:8080/api/students/S1001
```

API errors return readable JSON messages. Student IDs and email addresses are unique; both scores must be between 0 and 100.
