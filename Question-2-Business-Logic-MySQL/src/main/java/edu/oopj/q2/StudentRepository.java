package edu.oopj.q2;

import java.sql.*;
import java.util.*;

/** MySQL persistence using prepared statements. */
final class StudentRepository {
    private final String url=env("DB_URL","jdbc:mysql://localhost:3306/student_skill_q2_db");
    private final String user=env("DB_USERNAME","root");
    private final String password=env("DB_PASSWORD","");
    private Connection connect() throws SQLException { return DriverManager.getConnection(url,user,password); }
    List<Student> findAll() throws SQLException {
        String sql="SELECT * FROM students ORDER BY student_name";
        try(Connection c=connect(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
            List<Student> rows=new ArrayList<>(); while(r.next()) rows.add(read(r)); return rows;
        }
    }
    Optional<Student> findById(String id) throws SQLException {
        try(Connection c=connect(); PreparedStatement p=c.prepareStatement("SELECT * FROM students WHERE student_id=?")){
            p.setString(1,id); try(ResultSet r=p.executeQuery()){return r.next()?Optional.of(read(r)):Optional.empty();}
        }
    }
    void save(Student s) throws SQLException {
        String sql="INSERT INTO students(student_id,student_name,email,course_name,programming_score,mathematics_score,technical_skills) VALUES(?,?,?,?,?,?,?)";
        try(Connection c=connect(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,s.id());p.setString(2,s.name());p.setString(3,s.email());p.setString(4,s.courseName());
            p.setDouble(5,s.programmingScore());p.setDouble(6,s.mathematicsScore());p.setString(7,String.join("|",s.technicalSkills()));p.executeUpdate();
        }
    }
    void update(Student s) throws SQLException {
        String sql="UPDATE students SET student_name=?,email=?,course_name=?,programming_score=?,mathematics_score=?,technical_skills=? WHERE student_id=?";
        try(Connection c=connect(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,s.name());p.setString(2,s.email());p.setString(3,s.courseName());p.setDouble(4,s.programmingScore());
            p.setDouble(5,s.mathematicsScore());p.setString(6,String.join("|",s.technicalSkills()));p.setString(7,s.id());p.executeUpdate();
        }
    }
    boolean delete(String id) throws SQLException {
        try(Connection c=connect();PreparedStatement p=c.prepareStatement("DELETE FROM students WHERE student_id=?")){p.setString(1,id);return p.executeUpdate()>0;}
    }
    private static Student read(ResultSet r)throws SQLException{
        String raw=r.getString("technical_skills"); List<String> skills=raw==null||raw.isBlank()?List.of():Arrays.asList(raw.split("\\|"));
        return new Student(r.getString("student_id"),r.getString("student_name"),r.getString("email"),r.getString("course_name"),
                r.getDouble("programming_score"),r.getDouble("mathematics_score"),skills);
    }
    private static String env(String key,String fallback){String v=System.getenv(key);return v==null?fallback:v;}
}
