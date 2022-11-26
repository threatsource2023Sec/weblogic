package com.sun.faces.util.copier;

public class NewInstanceCopier implements Copier {
   public Object copy(Object object) {
      try {
         return object.getClass().newInstance();
      } catch (IllegalAccessException | InstantiationException var3) {
         throw new IllegalStateException(var3);
      }
   }
}
