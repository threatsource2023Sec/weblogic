package com.bea.common.security.service;

import java.util.Set;

public interface PrincipalValidationService {
   boolean validate(Set var1);

   boolean validate(Set var1, String var2);

   void sign(Set var1);
}
