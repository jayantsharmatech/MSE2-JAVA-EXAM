package edu.oopj.q3;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="students", uniqueConstraints=@UniqueConstraint(name="uk_student_email", columnNames="email"))
public class Student {
    @Id @Column(name="student_id", length=40, nullable=false, updatable=false)
    private String id;
    @Column(name="student_name", nullable=false, length=120) private String name;
    @Column(nullable=false, unique=true, length=254) private String email;
    @Column(name="course_name", nullable=false, length=120) private String courseName;
    @Column(name="programming_score", nullable=false) private double programmingScore;
    @Column(name="mathematics_score", nullable=false) private double mathematicsScore;
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="student_technical_skills", joinColumns=@JoinColumn(name="student_id"))
    @Column(name="skill", nullable=false, length=100)
    private Set<String> technicalSkills=new LinkedHashSet<>();

    protected Student() { }
    public Student(String id,String name,String email,String courseName,double programmingScore,double mathematicsScore,Set<String> skills){
        this.id=id;this.name=name;this.email=email;this.courseName=courseName;this.programmingScore=programmingScore;this.mathematicsScore=mathematicsScore;
        setTechnicalSkills(skills);
    }
    public String getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getCourseName(){return courseName;}
    public double getProgrammingScore(){return programmingScore;} public double getMathematicsScore(){return mathematicsScore;}
    public Set<String> getTechnicalSkills(){return Set.copyOf(technicalSkills);}
    public void updateScoresAndSkills(double programming,double mathematics,Set<String> skills){programmingScore=programming;mathematicsScore=mathematics;setTechnicalSkills(skills);}
    private void setTechnicalSkills(Set<String> skills){technicalSkills=new LinkedHashSet<>();if(skills!=null)skills.stream().filter(s->s!=null&&!s.isBlank()).map(String::trim).forEach(technicalSkills::add);}
    @Transient public double getAverageScore(){return (programmingScore+mathematicsScore)/2.0;}
    @Transient public String getPerformanceCategory(){double a=getAverageScore();if(a>=85)return "Excellent";if(a>=70)return "Good";if(a>=50)return "Satisfactory";return "Needs Improvement";}
}
