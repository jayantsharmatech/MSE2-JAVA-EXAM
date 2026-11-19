package edu.oopj.q3;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,String> {
    Optional<Student> findByEmailIgnoreCase(String email);
    List<Student> findByCourseNameContainingIgnoreCase(String courseName);
}
