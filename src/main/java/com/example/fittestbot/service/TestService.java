package com.example.fittestbot.service;

import com.example.fittestbot.web.dto.TestDto;
import io.vavr.control.Either;

public interface TestService {
  Either<Throwable, String> register(TestDto test, String id);

  Either<Throwable, String> delete(String name, String id);
}
