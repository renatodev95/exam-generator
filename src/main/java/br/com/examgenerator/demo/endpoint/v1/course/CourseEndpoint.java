package br.com.examgenerator.demo.endpoint.v1.course;

import br.com.examgenerator.demo.persistence.model.Course;
import br.com.examgenerator.demo.persistence.repository.CourseRepository;
import br.com.examgenerator.demo.util.EndpointUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("v1/professor/course")
public class CourseEndpoint {

    private final CourseRepository courseRepository;
    private final EndpointUtil endpointUtil;
    private final CourseService courseService;

    public CourseEndpoint(CourseRepository courseRepository,
                          EndpointUtil endpointUtil,
                          CourseService courseService) {
        this.courseRepository = courseRepository;
        this.endpointUtil = endpointUtil;
        this.courseService = courseService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return endpointUtil.returnObjectOrNotFound(courseRepository.findCourseById(id));
    }

    @GetMapping(path = "list")
    public ResponseEntity<?> listCourses(@RequestParam(value = "name", defaultValue = "") String name) {
        return endpointUtil.returnObjectOrNotFound(courseRepository.listCourses(name));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        courseService.throwResourceNotFoundIfCourseDoesNotExist(courseRepository.findCourseById(id));
        courseRepository.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Course course) {
        courseService.throwResourceNotFoundIfCourseDoesNotExist(courseRepository.findOne(course));
        courseRepository.save(course);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
