package org.glassfish.tyrus.core;

import java.lang.reflect.Method;

public class DefaultComponentProvider extends ComponentProvider {
   public boolean isApplicable(Class c) {
      return true;
   }

   public Object create(Class toLoad) {
      try {
         return ReflectionHelper.getInstance(toLoad);
      } catch (Exception var3) {
         return null;
      }
   }

   public boolean destroy(Object o) {
      return false;
   }

   public Method getInvocableMethod(Method method) {
      return method;
   }
}
