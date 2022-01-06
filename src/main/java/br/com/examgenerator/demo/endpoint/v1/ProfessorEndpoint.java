package br.com.examgenerator.demo.endpoint.v1;

import br.com.examgenerator.demo.persistence.model.Professor;
import br.com.examgenerator.demo.persistence.repository.ProfessorRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/professor")
public class ProfessorEndpoint {

    private final ProfessorRepository repository;

    public ProfessorEndpoint(ProfessorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find professor by his ID", notes = "We have to make this method better", response = Professor.class)
    public ResponseEntity<?> getProfessorByEmail(@PathVariable Long id) {
        Professor professor = repository.findById(id).orElse(null);
        return new ResponseEntity<>(professor, HttpStatus.OK);
    }
}
