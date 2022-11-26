package com.bea.core.repackaged.springframework.objenesis.strategy;

import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.android.Android10Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.android.Android17Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.android.Android18Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.basic.AccessibleInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.basic.ObjectInputStreamInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.gcj.GCJInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.perc.PercInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.sun.SunReflectionFactoryInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.sun.UnsafeFactoryInstantiator;
import java.io.Serializable;

public class StdInstantiatorStrategy extends BaseInstantiatorStrategy {
   public ObjectInstantiator newInstantiatorOf(Class type) {
      if (!PlatformDescription.isThisJVM("Java HotSpot") && !PlatformDescription.isThisJVM("OpenJDK")) {
         if (PlatformDescription.isThisJVM("Dalvik")) {
            if (PlatformDescription.isAndroidOpenJDK()) {
               return new UnsafeFactoryInstantiator(type);
            } else if (PlatformDescription.ANDROID_VERSION <= 10) {
               return new Android10Instantiator(type);
            } else {
               return (ObjectInstantiator)(PlatformDescription.ANDROID_VERSION <= 17 ? new Android17Instantiator(type) : new Android18Instantiator(type));
            }
         } else if (PlatformDescription.isThisJVM("GNU libgcj")) {
            return new GCJInstantiator(type);
         } else {
            return (ObjectInstantiator)(PlatformDescription.isThisJVM("PERC") ? new PercInstantiator(type) : new UnsafeFactoryInstantiator(type));
         }
      } else if (PlatformDescription.isGoogleAppEngine() && PlatformDescription.SPECIFICATION_VERSION.equals("1.7")) {
         return (ObjectInstantiator)(Serializable.class.isAssignableFrom(type) ? new ObjectInputStreamInstantiator(type) : new AccessibleInstantiator(type));
      } else {
         return new SunReflectionFactoryInstantiator(type);
      }
   }
}
