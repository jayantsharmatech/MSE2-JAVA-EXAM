CREATE DATABASE IF NOT EXISTS student_skill_q2_db CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE student_skill_q2_db;
CREATE TABLE IF NOT EXISTS students (
  student_id VARCHAR(40) PRIMARY KEY,
  student_name VARCHAR(120) NOT NULL,
  email VARCHAR(254) NOT NULL UNIQUE,
  course_name VARCHAR(120) NOT NULL,
  programming_score DECIMAL(5,2) NOT NULL CHECK (programming_score BETWEEN 0 AND 100),
  mathematics_score DECIMAL(5,2) NOT NULL CHECK (mathematics_score BETWEEN 0 AND 100),
  technical_skills TEXT NOT NULL
);
