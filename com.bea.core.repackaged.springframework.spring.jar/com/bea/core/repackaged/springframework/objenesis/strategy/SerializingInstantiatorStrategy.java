package com.bea.core.repackaged.springframework.objenesis.strategy;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.android.AndroidSerializationInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.basic.ObjectInputStreamInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.basic.ObjectStreamClassInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.gcj.GCJSerializationInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.perc.PercSerializationInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.sun.SunReflectionFactorySerializationInstantiator;
import java.io.NotSerializableException;
import java.io.Serializable;

public class SerializingInstantiatorStrategy extends BaseInstantiatorStrategy {
   public ObjectInstantiator newInstantiatorOf(Class type) {
      if (!Serializable.class.isAssignableFrom(type)) {
         throw new ObjenesisException(new NotSerializableException(type + " not serializable"));
      } else if (!PlatformDescription.JVM_NAME.startsWith("Java HotSpot") && !PlatformDescription.isThisJVM("OpenJDK")) {
         if (PlatformDescription.JVM_NAME.startsWith("Dalvik")) {
            return (ObjectInstantiator)(PlatformDescription.isAndroidOpenJDK() ? new ObjectStreamClassInstantiator(type) : new AndroidSerializationInstantiator(type));
         } else if (PlatformDescription.JVM_NAME.startsWith("GNU libgcj")) {
            return new GCJSerializationInstantiator(type);
         } else {
            return (ObjectInstantiator)(PlatformDescription.JVM_NAME.startsWith("PERC") ? new PercSerializationInstantiator(type) : new SunReflectionFactorySerializationInstantiator(type));
         }
      } else {
         return (ObjectInstantiator)(PlatformDescription.isGoogleAppEngine() && PlatformDescription.SPECIFICATION_VERSION.equals("1.7") ? new ObjectInputStreamInstantiator(type) : new SunReflectionFactorySerializationInstantiator(type));
      }
   }
}
