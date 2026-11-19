package edu.oopj.q3;

import jakarta.validation.constraints.*;
import java.util.Set;

public record UpdateScoresSkillsRequest(
    @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double programmingScore,
    @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double mathematicsScore,
    Set<@NotBlank @Size(max=100) String> technicalSkills
) { }
