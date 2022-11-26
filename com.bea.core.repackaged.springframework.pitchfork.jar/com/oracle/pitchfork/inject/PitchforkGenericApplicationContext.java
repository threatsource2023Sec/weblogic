package com.oracle.pitchfork.inject;

import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;

public class PitchforkGenericApplicationContext extends GenericApplicationContext {
   public PitchforkGenericApplicationContext(ApplicationContext parent) {
      super(parent);
   }

   public void assertBeanFactoryActive() {
   }
}
