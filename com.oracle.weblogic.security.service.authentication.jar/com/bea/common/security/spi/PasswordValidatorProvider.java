package com.bea.common.security.spi;

import com.bea.common.security.service.ValidationFailedException;
import weblogic.security.spi.SecurityProvider;

public interface PasswordValidatorProvider extends SecurityProvider {
   void validate(String var1, String var2) throws ValidationFailedException;
}
