package br.com.examgenerator.demo.endpoint.v1.course;

import br.com.examgenerator.demo.exception.ResourceNotFoundException;
import br.com.examgenerator.demo.persistence.model.Course;
import br.com.examgenerator.demo.persistence.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CourseService implements Serializable {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void throwResourceNotFoundIfCourseDoesNotExist(Course course) {
        if (course == null || course.getId() == null || courseRepository.findCourseById(course.getId()) == null) {
            throw new ResourceNotFoundException("Course not found");
        }
    }
}
