package edu.oopj.q3;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class StudentService {
    private final StudentRepository repository;
    public StudentService(StudentRepository repository){this.repository=repository;}
    public StudentResponse add(CreateStudentRequest request){
        if(repository.existsById(request.id()))throw new DuplicateStudentException("Student ID already exists: "+request.id());
        if(repository.findByEmailIgnoreCase(request.email()).isPresent())throw new DuplicateStudentException("Email is already registered");
        try{return StudentResponse.from(repository.save(new Student(request.id().trim(),request.name().trim(),request.email().trim(),request.courseName().trim(),
                request.programmingScore(),request.mathematicsScore(),request.technicalSkills())));}
        catch(DataIntegrityViolationException e){throw new DuplicateStudentException("Student ID or email is already registered");}
    }
    @Transactional(readOnly=true) public List<StudentResponse> all(){return repository.findAll().stream().map(StudentResponse::from).toList();}
    @Transactional(readOnly=true) public StudentResponse byId(String id){return StudentResponse.from(get(id));}
    public StudentResponse update(String id,UpdateScoresSkillsRequest request){Student s=get(id);s.updateScoresAndSkills(request.programmingScore(),request.mathematicsScore(),request.technicalSkills());return StudentResponse.from(repository.save(s));}
    public void delete(String id){repository.delete(get(id));}
    @Transactional(readOnly=true) public List<StudentResponse> meritList(){return repository.findAll().stream()
            .sorted(Comparator.comparingDouble(Student::getAverageScore).reversed().thenComparing(Student::getName,String.CASE_INSENSITIVE_ORDER))
            .map(StudentResponse::from).toList();}
    @Transactional(readOnly=true) public List<StudentResponse> search(String query){String q=query.toLowerCase(Locale.ROOT);
        return repository.findAll().stream().filter(s->s.getId().toLowerCase(Locale.ROOT).contains(q)||s.getEmail().toLowerCase(Locale.ROOT).contains(q)||s.getCourseName().toLowerCase(Locale.ROOT).contains(q))
                .map(StudentResponse::from).toList();}
    private Student get(String id){return repository.findById(id).orElseThrow(()->new StudentNotFoundException("Student not found: "+id));}
}
