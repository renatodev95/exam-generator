package br.com.examgenerator.demo.endpoint.v1.course;

import br.com.examgenerator.demo.persistence.repository.CourseRepository;
import br.com.examgenerator.demo.util.EndpointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/professor/course")
public class CourseEndpoint {

    @Autowired
    private final CourseRepository courseRepository;

    private final EndpointUtil endpointUtil;

    public CourseEndpoint(CourseRepository courseRepository, EndpointUtil endpointUtil) {
        this.courseRepository = courseRepository;
        this.endpointUtil = endpointUtil;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return endpointUtil.returnObjectOrNotFound(courseRepository.findCourseById(id));
    }
}
