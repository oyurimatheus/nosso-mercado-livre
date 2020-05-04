package me.oyurimatheus.nossomercadolivre.products;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface QuestionRepository extends Repository<Question, Long> {

    Question save(Question question);

    List<Question> findByProduct(Product product);

    Optional<Question> findById(Long id);
}
