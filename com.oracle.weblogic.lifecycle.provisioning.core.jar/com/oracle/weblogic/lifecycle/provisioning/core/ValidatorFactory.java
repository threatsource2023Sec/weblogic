package com.oracle.weblogic.lifecycle.provisioning.core;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.Validator;
import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class ValidatorFactory implements Factory {
   private final javax.validation.ValidatorFactory vf;

   @Inject
   public ValidatorFactory(javax.validation.ValidatorFactory vf) {
      Objects.requireNonNull(vf);
      this.vf = vf;
   }

   @Singleton
   public Validator provide() {
      try {
         return (Validator)(new InitialContext()).lookup("java:comp/Validator");
      } catch (NamingException var2) {
         return this.vf.getValidator();
      }
   }

   public void dispose(Validator validator) {
   }
}
