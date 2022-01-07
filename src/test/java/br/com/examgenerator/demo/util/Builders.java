package br.com.examgenerator.demo.util;

import br.com.examgenerator.demo.persistence.model.Course;
import br.com.examgenerator.demo.persistence.model.Professor;

public class Builders {

    public static Course mockCourse() {
         return Course.Builder.newCourse()
                 .id(1L)
                 .name("Java")
                 .professor(mockProfessor())
                 .build();
    }

    public static Professor mockProfessor() {
        return Professor.Builder.newProfessor()
                .id(1L)
                .name("Renato")
                .email("renato@algumacoisa.com")
                .build();
    }
}
