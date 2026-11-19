package edu.oopj.q3;

import jakarta.validation.constraints.*;
import java.util.Set;

public record CreateStudentRequest(
    @NotBlank @Size(max=40) String id,
    @NotBlank @Size(max=120) String name,
    @NotBlank @Email @Size(max=254) String email,
    @NotBlank @Size(max=120) String courseName,
    @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double programmingScore,
    @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double mathematicsScore,
    Set<@NotBlank @Size(max=100) String> technicalSkills
) { }
