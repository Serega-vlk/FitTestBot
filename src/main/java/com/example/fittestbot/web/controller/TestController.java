package com.example.fittestbot.web.controller;

import com.example.fittestbot.repository.TestRepository;
import com.example.fittestbot.service.TestService;
import com.example.fittestbot.web.dto.TestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
  private TestService registrationService;
  @PostMapping
  public ResponseEntity<String> register(@RequestBody TestDto test, @RequestHeader("id") String id) throws Throwable {
    return ResponseEntity.ok(registrationService.register(test, id)
        .getOrElseThrow(Function.identity()));
  }

  @DeleteMapping
  public ResponseEntity<String> delete(@RequestBody String test, @RequestHeader("id") String id) throws Throwable {
    return ResponseEntity.ok(registrationService.delete(test, id)
        .getOrElseThrow(Function.identity()));
  }
}
