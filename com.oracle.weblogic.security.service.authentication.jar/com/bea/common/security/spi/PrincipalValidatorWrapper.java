package com.bea.common.security.spi;

import weblogic.security.spi.PrincipalValidator;

public interface PrincipalValidatorWrapper extends PrincipalValidator {
   String getPrincipalValidatorType();
}
