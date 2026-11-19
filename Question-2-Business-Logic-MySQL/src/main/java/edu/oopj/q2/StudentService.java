package edu.oopj.q2;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/** Collection-backed business operations; MySQL remains the durable source of truth. */
final class StudentService {
    private final StudentRepository repository;
    private final Map<String,Student> students=new LinkedHashMap<>();
    StudentService(StudentRepository repository) throws SQLException { this.repository=repository; refresh(); }
    private void refresh() throws SQLException { students.clear(); for(Student s:repository.findAll()) students.put(key(s.id()),s); }
    void add(Student s) throws SQLException {
        if(students.containsKey(key(s.id()))) throw new IllegalArgumentException("Duplicate student ID: "+s.id());
        if(students.values().stream().anyMatch(x->x.email().equalsIgnoreCase(s.email()))) throw new IllegalArgumentException("Email already exists");
        repository.save(s); students.put(key(s.id()),s);
    }
    List<Student> all(){return List.copyOf(students.values());}
    Optional<Student> byId(String id){return Optional.ofNullable(students.get(key(id)));}
    List<Student> search(String query){String q=query.toLowerCase(Locale.ROOT);return students.values().stream()
            .filter(s->s.id().toLowerCase(Locale.ROOT).contains(q)||s.email().toLowerCase(Locale.ROOT).contains(q)||s.courseName().toLowerCase(Locale.ROOT).contains(q)).toList();}
    List<Student> meritList(){return students.values().stream().sorted(Comparator.comparingDouble(Student::average).reversed().thenComparing(Student::name)).toList();}
    Map<String,List<String>> sharedSkills(){
        Map<String,List<String>> owners=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for(Student s:students.values())for(String skill:s.technicalSkills())owners.computeIfAbsent(skill,k->new ArrayList<>()).add(s.name()+" ("+s.id()+")");
        return owners.entrySet().stream().filter(e->e.getValue().size()>1).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,TreeMap::new));
    }
    List<Student> needsProgrammingImprovement(){return students.values().stream().filter(s->s.programmingScore()<50&&s.mathematicsScore()>=75).toList();}
    void updateScoresSkills(String id,double programming,double mathematics,List<String> skills)throws SQLException{
        Student old=byId(id).orElseThrow(()->new IllegalArgumentException("Student not found: "+id));
        Student updated=new Student(old.id(),old.name(),old.email(),old.courseName(),programming,mathematics,skills);
        repository.update(updated);students.put(key(id),updated);
    }
    boolean delete(String id)throws SQLException{boolean removed=repository.delete(id);students.remove(key(id));return removed;}
    private static String key(String id){return id.trim().toLowerCase(Locale.ROOT);}
}
