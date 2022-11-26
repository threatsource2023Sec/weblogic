package com.bea.core.repackaged.springframework.objenesis.instantiator.sun;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.SerializationInstantiatorHelper;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.io.NotSerializableException;
import java.lang.reflect.Constructor;

@Instantiator(Typology.SERIALIZATION)
public class SunReflectionFactorySerializationInstantiator implements ObjectInstantiator {
   private final Constructor mungedConstructor;

   public SunReflectionFactorySerializationInstantiator(Class type) {
      Class nonSerializableAncestor = SerializationInstantiatorHelper.getNonSerializableSuperClass(type);

      Constructor nonSerializableAncestorConstructor;
      try {
         nonSerializableAncestorConstructor = nonSerializableAncestor.getDeclaredConstructor((Class[])null);
      } catch (NoSuchMethodException var5) {
         throw new ObjenesisException(new NotSerializableException(type + " has no suitable superclass constructor"));
      }

      this.mungedConstructor = SunReflectionFactoryHelper.newConstructorForSerialization(type, nonSerializableAncestorConstructor);
      this.mungedConstructor.setAccessible(true);
   }

   public Object newInstance() {
      try {
         return this.mungedConstructor.newInstance((Object[])null);
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }
}
