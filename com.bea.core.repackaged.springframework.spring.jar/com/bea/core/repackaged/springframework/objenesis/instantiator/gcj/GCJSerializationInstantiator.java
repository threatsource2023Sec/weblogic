package com.bea.core.repackaged.springframework.objenesis.instantiator.gcj;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.SerializationInstantiatorHelper;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;

@Instantiator(Typology.SERIALIZATION)
public class GCJSerializationInstantiator extends GCJInstantiatorBase {
   private final Class superType;

   public GCJSerializationInstantiator(Class type) {
      super(type);
      this.superType = SerializationInstantiatorHelper.getNonSerializableSuperClass(type);
   }

   public Object newInstance() {
      try {
         return this.type.cast(newObjectMethod.invoke(dummyStream, this.type, this.superType));
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }
}
