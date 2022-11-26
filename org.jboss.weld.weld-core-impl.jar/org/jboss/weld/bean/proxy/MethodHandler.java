package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;

public interface MethodHandler {
   Object invoke(Object var1, Method var2, Method var3, Object[] var4) throws Throwable;
}
