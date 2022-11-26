package com.bea.core.repackaged.springframework.objenesis.instantiator.gcj;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.lang.reflect.InvocationTargetException;

@Instantiator(Typology.STANDARD)
public class GCJInstantiator extends GCJInstantiatorBase {
   public GCJInstantiator(Class type) {
      super(type);
   }

   public Object newInstance() {
      try {
         return this.type.cast(newObjectMethod.invoke(dummyStream, this.type, Object.class));
      } catch (IllegalAccessException | InvocationTargetException | RuntimeException var2) {
         throw new ObjenesisException(var2);
      }
   }
}
