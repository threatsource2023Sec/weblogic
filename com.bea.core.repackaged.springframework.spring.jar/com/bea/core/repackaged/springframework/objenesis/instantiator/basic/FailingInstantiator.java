package com.bea.core.repackaged.springframework.objenesis.instantiator.basic;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;

@Instantiator(Typology.NOT_COMPLIANT)
public class FailingInstantiator implements ObjectInstantiator {
   public FailingInstantiator(Class type) {
   }

   public Object newInstance() {
      throw new ObjenesisException("Always failing");
   }
}
