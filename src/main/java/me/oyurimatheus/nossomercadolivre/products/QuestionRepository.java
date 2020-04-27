package me.oyurimatheus.nossomercadolivre.products;

import org.springframework.data.repository.Repository;

import java.util.List;

interface QuestionRepository extends Repository<Question, Long> {

    Question save(Question question);

    List<Question> findByProduct(Product product);
}
