package br.com.examgenerator.demo.persistence.repository;

import br.com.examgenerator.demo.persistence.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);
}
