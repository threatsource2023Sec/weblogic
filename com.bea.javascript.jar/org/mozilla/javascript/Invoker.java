package org.mozilla.javascript;

import java.lang.reflect.Method;

public abstract class Invoker {
   public Invoker createInvoker(Method var1, Class[] var2) {
      return null;
   }

   public abstract Object invoke(Object var1, Object[] var2);
}
