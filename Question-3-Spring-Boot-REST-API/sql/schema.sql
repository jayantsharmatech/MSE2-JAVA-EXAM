CREATE DATABASE IF NOT EXISTS student_skill_db CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE student_skill_db;
CREATE TABLE IF NOT EXISTS students (
  student_id VARCHAR(40) PRIMARY KEY,
  student_name VARCHAR(120) NOT NULL,
  email VARCHAR(254) NOT NULL UNIQUE,
  course_name VARCHAR(120) NOT NULL,
  programming_score DECIMAL(5,2) NOT NULL CHECK (programming_score BETWEEN 0 AND 100),
  mathematics_score DECIMAL(5,2) NOT NULL CHECK (mathematics_score BETWEEN 0 AND 100)
);
CREATE TABLE IF NOT EXISTS student_technical_skills (
  student_id VARCHAR(40) NOT NULL,
  skill VARCHAR(100) NOT NULL,
  PRIMARY KEY (student_id, skill),
  CONSTRAINT fk_skill_student FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);
