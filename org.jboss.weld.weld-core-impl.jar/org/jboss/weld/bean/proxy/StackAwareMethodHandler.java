package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;

public interface StackAwareMethodHandler extends MethodHandler {
   Object invoke(InterceptionDecorationContext.Stack var1, Object var2, Method var3, Method var4, Object[] var5) throws Throwable;
}
