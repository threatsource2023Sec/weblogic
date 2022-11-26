package weblogic.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class InterceptingInvocationHandlerImpl implements InvocationHandler {
   private final Object delegate;
   private final InvocationHandlerInterceptor iceptor;

   InterceptingInvocationHandlerImpl(Object delegate, InvocationHandlerInterceptor iceptor) {
      this.delegate = delegate;
      this.iceptor = iceptor;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      this.iceptor.preInvoke(method, args);
      Object result = null;

      try {
         result = method.invoke(this.delegate, args);
      } catch (InvocationTargetException var6) {
         throw var6.getCause();
      }

      return this.iceptor.postInvoke(method, args, result);
   }
}
