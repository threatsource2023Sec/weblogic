package org.jboss.weld.interceptor.util.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import org.jboss.weld.bean.proxy.MethodHandler;

public abstract class TargetInstanceProxyMethodHandler implements MethodHandler, Serializable {
   private final Object targetInstance;
   private final Class targetClass;

   public TargetInstanceProxyMethodHandler(Object targetInstance, Class targetClass) {
      this.targetInstance = targetInstance;
      this.targetClass = targetClass;
   }

   public final Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      if (thisMethod.getDeclaringClass().equals(TargetInstanceProxy.class)) {
         if (thisMethod.getName().equals("getTargetInstance")) {
            return this.getTargetInstance();
         } else {
            return thisMethod.getName().equals("getTargetClass") ? this.getTargetClass() : null;
         }
      } else {
         return this.doInvoke(self, thisMethod, proceed, args);
      }
   }

   protected abstract Object doInvoke(Object var1, Method var2, Method var3, Object[] var4) throws Throwable;

   public Object getTargetInstance() {
      return this.targetInstance;
   }

   public Class getTargetClass() {
      return this.targetClass;
   }
}
