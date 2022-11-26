package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;

public interface BeanInstance {
   Object getInstance();

   Class getInstanceType();

   Object invoke(Object var1, Method var2, Object... var3) throws Throwable;
}
