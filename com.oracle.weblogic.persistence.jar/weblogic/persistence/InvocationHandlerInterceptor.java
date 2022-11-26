package weblogic.persistence;

import java.lang.reflect.Method;

public interface InvocationHandlerInterceptor {
   void preInvoke(Method var1, Object[] var2) throws Throwable;

   Object postInvoke(Method var1, Object[] var2, Object var3) throws Throwable;
}
