package br.com.examgenerator.demo.persistence.repository;

import br.com.examgenerator.demo.persistence.model.Professor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {

    Professor findByEmail(String email);
}
