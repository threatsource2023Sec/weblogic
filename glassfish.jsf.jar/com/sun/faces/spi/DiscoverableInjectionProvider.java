package com.sun.faces.spi;

import com.sun.faces.util.Util;

public abstract class DiscoverableInjectionProvider implements InjectionProvider {
   public static boolean isInjectionFeatureAvailable(String delegateClass) {
      try {
         Util.loadClass(delegateClass, (Object)null);
         return true;
      } catch (Exception var2) {
         return false;
      }
   }
}
