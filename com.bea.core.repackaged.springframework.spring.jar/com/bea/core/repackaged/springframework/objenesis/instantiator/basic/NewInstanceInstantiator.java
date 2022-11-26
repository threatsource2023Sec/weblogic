package com.bea.core.repackaged.springframework.objenesis.instantiator.basic;

import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import com.bea.core.repackaged.springframework.objenesis.instantiator.util.ClassUtils;

@Instantiator(Typology.NOT_COMPLIANT)
public class NewInstanceInstantiator implements ObjectInstantiator {
   private final Class type;

   public NewInstanceInstantiator(Class type) {
      this.type = type;
   }

   public Object newInstance() {
      return ClassUtils.newInstance(this.type);
   }
}
