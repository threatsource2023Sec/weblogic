package com.bea.core.repackaged.springframework.objenesis.instantiator.basic;

import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;

@Instantiator(Typology.NOT_COMPLIANT)
public class AccessibleInstantiator extends ConstructorInstantiator {
   public AccessibleInstantiator(Class type) {
      super(type);
      if (this.constructor != null) {
         this.constructor.setAccessible(true);
      }

   }
}
