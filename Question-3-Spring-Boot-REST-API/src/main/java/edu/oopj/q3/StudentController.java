package edu.oopj.q3;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service){this.service=service;}
    @PostMapping public ResponseEntity<StudentResponse> add(@Valid @RequestBody CreateStudentRequest request){StudentResponse created=service.add(request);return ResponseEntity.created(URI.create("/api/students/"+created.id())).body(created);}
    @GetMapping public List<StudentResponse> all(){return service.all();}
    @GetMapping("/merit-list") public List<StudentResponse> meritList(){return service.meritList();}
    @GetMapping("/search") public List<StudentResponse> search(@RequestParam String query){return service.search(query);}
    @GetMapping("/{id}") public StudentResponse byId(@PathVariable String id){return service.byId(id);}
    @PutMapping("/{id}/scores-skills") public StudentResponse update(@PathVariable String id,@Valid @RequestBody UpdateScoresSkillsRequest request){return service.update(id,request);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable String id){service.delete(id);return ResponseEntity.noContent().build();}
    @ExceptionHandler(StudentNotFoundException.class) public ResponseEntity<Map<String,String>> notFound(RuntimeException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));}
    @ExceptionHandler(DuplicateStudentException.class) public ResponseEntity<Map<String,String>> duplicate(RuntimeException e){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));}
}
