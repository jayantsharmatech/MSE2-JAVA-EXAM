package edu.oopj.q2;

import java.util.*;

public record Student(String id, String name, String email, String courseName,
                      double programmingScore, double mathematicsScore, List<String> technicalSkills) {
    public Student {
        id = required(id, "id"); name = required(name, "name"); email = required(email, "email");
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) throw new IllegalArgumentException("Invalid email address");
        courseName = required(courseName, "courseName");
        score(programmingScore); score(mathematicsScore);
        technicalSkills = technicalSkills == null ? List.of() : technicalSkills.stream().filter(Objects::nonNull)
                .map(String::trim).filter(s -> !s.isEmpty()).distinct().toList();
    }
    public double average() { return (programmingScore + mathematicsScore) / 2.0; }
    public String category() {
        double a=average();
        if(a>=85) return "Excellent"; if(a>=70) return "Good"; if(a>=50) return "Satisfactory";
        return "Needs Improvement";
    }
    public String strongerSubject() {
        int c=Double.compare(programmingScore, mathematicsScore);
        return c==0 ? "Tie" : (c>0 ? "Programming" : "Mathematics");
    }
    static double score(double v) { if(!Double.isFinite(v)||v<0||v>100) throw new IllegalArgumentException("Scores must be between 0 and 100"); return v; }
    private static String required(String v,String f){ if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" is required"); return v.trim(); }
}
