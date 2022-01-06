package br.com.examgenerator.demo.endpoint.v1.course;

import br.com.examgenerator.demo.persistence.model.Course;
import br.com.examgenerator.demo.persistence.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/professor/course")
public class CourseEndpoint {

    @Autowired
    private final CourseRepository courseRepository;

    public CourseEndpoint(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCrouseById(@PathVariable Long id) {
        Course course = courseRepository.findByIdAndProfessor(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
