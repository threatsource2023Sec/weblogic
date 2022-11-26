package com.bea.common.security.spi;

import weblogic.security.spi.PrincipalValidator;

public interface PrincipalValidationProvider {
   PrincipalValidator getPrincipalValidator();
}
