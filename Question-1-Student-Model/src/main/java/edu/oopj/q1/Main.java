package edu.oopj.q1;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("S1001", "Asha Rao", "asha@example.com", "BCA", 88, 76,
                List.of("Java", "SQL", "Spring Boot"));
        System.out.println(student);
        System.out.printf("Average score: %.2f%n", student.getAverageScore());
    }
}
