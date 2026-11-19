package edu.oopj.q1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Student profile model with score and collection invariants. */
public class Student {
    private final String studentId;
    private String name;
    private String email;
    private String courseName;
    private double programmingScore;
    private double mathematicsScore;
    private final List<String> technicalSkills = new ArrayList<>();

    public Student(String studentId, String name, String email, String courseName,
                   double programmingScore, double mathematicsScore, List<String> technicalSkills) {
        this.studentId = required(studentId, "studentId");
        setName(name);
        setEmail(email);
        setCourseName(courseName);
        setProgrammingScore(programmingScore);
        setMathematicsScore(mathematicsScore);
        setTechnicalSkills(technicalSkills);
    }

    public Student(String studentId, String name, String email, String courseName) {
        this(studentId, name, email, courseName, 0, 0, List.of());
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = required(name, "name"); }
    public String getEmail() { return email; }
    public void setEmail(String email) {
        String value = required(email, "email");
        if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) throw new IllegalArgumentException("Invalid email address");
        this.email = value;
    }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = required(courseName, "courseName"); }
    public double getProgrammingScore() { return programmingScore; }
    public void setProgrammingScore(double score) { this.programmingScore = validScore(score); }
    public double getMathematicsScore() { return mathematicsScore; }
    public void setMathematicsScore(double score) { this.mathematicsScore = validScore(score); }
    public List<String> getTechnicalSkills() { return Collections.unmodifiableList(technicalSkills); }
    public void setTechnicalSkills(List<String> skills) {
        technicalSkills.clear();
        if (skills != null) skills.stream().filter(Objects::nonNull).map(String::trim).filter(s -> !s.isEmpty())
                .distinct().forEach(technicalSkills::add);
    }
    public double getAverageScore() { return (programmingScore + mathematicsScore) / 2.0; }
    private static double validScore(double score) {
        if (!Double.isFinite(score) || score < 0 || score > 100) throw new IllegalArgumentException("Score must be between 0 and 100");
        return score;
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " is required");
        return value.trim();
    }
    @Override public boolean equals(Object other) { return other instanceof Student s && studentId.equalsIgnoreCase(s.studentId); }
    @Override public int hashCode() { return studentId.toLowerCase().hashCode(); }
    @Override public String toString() {
        return "Student{id='" + studentId + "', name='" + name + "', email='" + email + "', course='" + courseName
                + "', programming=" + programmingScore + ", mathematics=" + mathematicsScore + ", average="
                + String.format("%.2f", getAverageScore()) + ", skills=" + technicalSkills + "}";
    }
}
