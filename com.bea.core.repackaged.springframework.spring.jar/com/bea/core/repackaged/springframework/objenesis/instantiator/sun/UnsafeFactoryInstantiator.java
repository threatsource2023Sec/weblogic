package com.bea.core.repackaged.springframework.objenesis.instantiator.sun;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import com.bea.core.repackaged.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

@Instantiator(Typology.STANDARD)
public class UnsafeFactoryInstantiator implements ObjectInstantiator {
   private final Unsafe unsafe = UnsafeUtils.getUnsafe();
   private final Class type;

   public UnsafeFactoryInstantiator(Class type) {
      this.type = type;
   }

   public Object newInstance() {
      try {
         return this.type.cast(this.unsafe.allocateInstance(this.type));
      } catch (InstantiationException var2) {
         throw new ObjenesisException(var2);
      }
   }
}
