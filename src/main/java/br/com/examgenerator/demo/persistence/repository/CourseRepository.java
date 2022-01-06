package br.com.examgenerator.demo.persistence.repository;

import br.com.examgenerator.demo.persistence.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Inserindo o Professor nas querys para impedir que um professor possa acessar dados de outros professores

    @Query("select c from Course c where c.id = ?1 and c.professor = ?#{principal.professor}")
    Course findCourseById(Long id);

    @Query("select c from Course c where c = ?1 and c.professor = ?#{principal.professor}")
    Course findOne(Course course);

    @Query("select c from Course c where c.name like %?1% and c.professor = ?#{principal.professor}")
    List<Course> listCourses(String name);

    @Query("delete from Course c where c.id = ?1 and c.professor = ?#{principal.professor}")
    @Modifying @Transactional
    void delete(Long id);

    @Query("delete from Course c where c = ?1 and c.professor = ?#{principal.professor}")
    @Modifying @Transactional
    void delete(Course course);
}
