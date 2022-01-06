package br.com.examgenerator.demo.persistence.repository;

import br.com.examgenerator.demo.persistence.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Inserindo o Professor na query para impedir que um professor possa acessar dados de outros professores
    @Query("select c from Course c where c.id = ?1 and c.professor = ?#{principal.professor}")
    Course findCourseById(Long id);
}
