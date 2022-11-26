package com.oracle.weblogic.lifecycle.provisioning.core;

import javax.inject.Singleton;
import javax.validation.Validation;
import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class ValidatorFactoryFactory implements Factory {
   private volatile javax.validation.ValidatorFactory vf;

   @Singleton
   public javax.validation.ValidatorFactory provide() {
      synchronized(this) {
         if (this.vf == null) {
            this.vf = Validation.buildDefaultValidatorFactory();
         }
      }

      return this.vf;
   }

   public void dispose(javax.validation.ValidatorFactory validatorFactory) {
      if (validatorFactory != null) {
         validatorFactory.close();
      }

      this.vf = null;
   }
}
