package com.example.fittestbot.service;

import com.example.fittestbot.web.dto.Marks;
import com.example.fittestbot.web.dto.Test;
import com.example.fittestbot.web.dto.TestDto;
import io.vavr.control.Either;

import java.util.List;

public interface TestService {
  Either<Throwable, String> register(TestDto test, String id);

  Either<Throwable, String> delete(String name, String id);

  Either<Throwable, Marks> getAllMarks(String name, String id);

  Either<Throwable, List<Test>> getAllTests(String id);
}
