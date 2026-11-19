package edu.oopj.q3;

import java.util.Set;

public record StudentResponse(String id,String name,String email,String courseName,double programmingScore,
                              double mathematicsScore,double averageScore,String performanceCategory,
                              String strongerSubject,Set<String> technicalSkills) {
    static StudentResponse from(Student s){
        String stronger=Double.compare(s.getProgrammingScore(),s.getMathematicsScore())==0?"Tie":
                (s.getProgrammingScore()>s.getMathematicsScore()?"Programming":"Mathematics");
        return new StudentResponse(s.getId(),s.getName(),s.getEmail(),s.getCourseName(),s.getProgrammingScore(),s.getMathematicsScore(),
                s.getAverageScore(),s.getPerformanceCategory(),stronger,s.getTechnicalSkills());
    }
}
