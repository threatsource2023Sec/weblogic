package org.glassfish.tyrus.core;

import java.lang.reflect.Method;

public abstract class ComponentProvider {
   public abstract boolean isApplicable(Class var1);

   public abstract Object create(Class var1);

   public Method getInvocableMethod(Method method) {
      return method;
   }

   public abstract boolean destroy(Object var1);
}
