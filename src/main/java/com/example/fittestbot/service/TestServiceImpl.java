package com.example.fittestbot.service;

import com.example.fittestbot.entity.AnswerEntity;
import com.example.fittestbot.entity.QuestionEntity;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.exception.AccessDeniedException;
import com.example.fittestbot.model.Role;
import com.example.fittestbot.repository.AnswerRepository;
import com.example.fittestbot.repository.MarkRepository;
import com.example.fittestbot.repository.QuestionRepository;
import com.example.fittestbot.repository.TestRepository;
import com.example.fittestbot.repository.UserRepository;
import com.example.fittestbot.web.dto.TestDto;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
  private TestRepository testRepository;
  private UserRepository userRepository;
  private QuestionRepository questionRepository;
  private AnswerRepository answerRepository;
  private MarkRepository markRepository;
  @Override
  @Transactional
  public Either<Throwable, String> register(TestDto test, String id) {
    return Try.of(() -> userRepository.findById(Long.parseLong(id))
            .orElseThrow(NotFoundException::new))
        .filter(user -> user.getRole() == Role.ADMIN,
            AccessDeniedException::new)
        .map(user -> { save(test);
          return "Successfully created";})
        .toEither();
  }

  @Override
  @Transactional
  public Either<Throwable, String> delete(String name, String id) {
    return Try.of(() -> userRepository.findById(Long.parseLong(id))
            .orElseThrow(NotFoundException::new))
        .filter(user -> user.getRole() == Role.ADMIN,
            AccessDeniedException::new)
        .map(user -> { delete(name);
          return "Successfully deleted";})
        .toEither();
  }

  private void save(TestDto testDto){
    Test test = testRepository.save(Test.builder()
        .name(testDto.getName())
        .total(testDto.getTotal())
        .build());
    testDto.getQuestions().forEach(node -> {
      QuestionEntity entity = questionRepository.save(QuestionEntity.builder()
          .value(node.getQuestion().getValue())
          .text(node.getQuestion().getText())
          .test(test)
          .build());
      node.getAnswers().forEach(answer -> answerRepository.save(AnswerEntity.builder()
          .text(answer.getText())
          .isCorrect(answer.getIsCorrect())
          .question(entity)
          .build()));
    });
  }

  private void delete(String name){
    Test test = testRepository.findByName(name).orElseThrow(NotFoundException::new);
    markRepository.deleteByTest(test);
    testRepository.delete(test);
  }
}
