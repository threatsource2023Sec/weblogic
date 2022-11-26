package com.bea.common.security.service;

public interface PasswordValidationService {
   void validate(String var1, String var2) throws ValidationFailedException;
}
