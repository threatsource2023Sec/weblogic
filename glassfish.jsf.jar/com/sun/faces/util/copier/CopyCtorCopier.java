package com.sun.faces.util.copier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CopyCtorCopier implements Copier {
   public Object copy(Object object) {
      try {
         Constructor copyConstructor = object.getClass().getConstructor(object.getClass());
         return copyConstructor.newInstance(object);
      } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var3) {
         throw new IllegalStateException(var3);
      }
   }
}
