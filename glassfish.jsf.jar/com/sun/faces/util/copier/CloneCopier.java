package com.sun.faces.util.copier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CloneCopier implements Copier {
   private static final String ERROR_CANT_CLONE = "Can not clone object of type %s since it doesn't implement Cloneable";

   public Object copy(Object object) {
      if (!(object instanceof Cloneable)) {
         throw new IllegalStateException(String.format("Can not clone object of type %s since it doesn't implement Cloneable", object.getClass()));
      } else {
         try {
            Method cloneMethod = this.getMethod(object, "clone");
            if (!cloneMethod.isAccessible()) {
               cloneMethod.setAccessible(true);
            }

            return cloneMethod.invoke(object);
         } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException var3) {
            throw new IllegalStateException(var3);
         }
      }
   }

   private Method getMethod(Object object, String name) {
      for(Class c = object.getClass(); c != null; c = c.getSuperclass()) {
         Method[] var4 = c.getDeclaredMethods();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (method.getName().equals(name)) {
               return method;
            }
         }
      }

      return null;
   }
}
