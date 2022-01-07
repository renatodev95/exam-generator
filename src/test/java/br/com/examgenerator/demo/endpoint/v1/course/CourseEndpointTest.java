package br.com.examgenerator.demo.endpoint.v1.course;

import br.com.examgenerator.demo.persistence.model.Course;
import br.com.examgenerator.demo.persistence.repository.CourseRepository;
import br.com.examgenerator.demo.persistence.repository.ProfessorRepository;
import br.com.examgenerator.demo.util.Builders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CourseEndpointTest {

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpEntity<Void> professorHeader;
    private HttpEntity<Void> wrongHeader;
    private Course course = Builders.mockCourse();

    @Before
    public void configProfessorHeader() {
        String body = "{\"username\":\"renato\",\"password\":\"sicoob\"}";
        HttpHeaders headers = testRestTemplate.postForEntity("/login", body, String.class).getHeaders();
        professorHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configWrongHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "1111");
        wrongHeader = new HttpEntity<>(headers);
    }


    //Todas as vezes que chamarmos um metodo do nosso repositorio esse metodo será executado primeiro.
    //Aqui definimos o que será retornado quando chamarmos cada metodo do repositorio.
    @Before
    public void setup() {
        BDDMockito.when(courseRepository.findCourseById(course.getId())).thenReturn(course);
        BDDMockito.when(courseRepository.listCourses("")).thenReturn(Collections.singletonList(course));
        BDDMockito.when(courseRepository.listCourses("Java")).thenReturn(Collections.singletonList(course));
    }

    @Test
    public void getCourseByIdWhenTokenIsWrongShouldReturn403() throws Exception {
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/1", GET, wrongHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void listCoursesWhenTokenIsWrongShouldReturn403() throws Exception {
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/list?name=", GET, wrongHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void listAllCoursesWhenNameDoesNotExistsShouldReturn404() throws Exception {
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/list?name=nope", GET, professorHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void listAllCoursesWhenNameExistsShouldReturn200() throws Exception {
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/list?name=Java", GET, professorHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getCourseByIdWithoutIdShouldReturn400() throws Exception {
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/", GET, professorHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void getCourseByIdWhenCourseIdDoesNotExistsShoudReturn404() throws Exception {
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/-1", GET, professorHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void getCourseByIdWhenCourseExistsShouldReturn200() throws Exception {
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/1", GET, professorHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteCourseWhenIdExistsShouldReturn200() throws Exception {
        Long id = 1L;
        BDDMockito.doNothing().when(courseRepository).deleteById(id);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/{id}", GET, professorHeader, String.class, id);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteCourseWhenIdDoesNotExistsShoudReturn404() throws Exception {
        Long id = -1L;
        BDDMockito.doNothing().when(courseRepository).deleteById(id);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/{id}", GET, professorHeader, String.class, id);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void createCourseWhenNameIsNullShouldReturn400() throws Exception {
        Course course = courseRepository.findCourseById(1L);
        course.setName(null);
        assertThat(createCourse(course).getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void createCourseWhenEverythingIsRightShouldReturn200() throws Exception {
        Course course = courseRepository.findCourseById(1L);
        course.setId(null);
        assertThat(createCourse(course).getStatusCodeValue()).isEqualTo(200);
    }

    private ResponseEntity<String> createCourse(Course course) {
        BDDMockito.when(courseRepository.save(course)).thenReturn(course);
        return testRestTemplate.exchange("/v1/professor/course", POST, new HttpEntity<>(course, professorHeader.getHeaders()), String.class);
    }
}