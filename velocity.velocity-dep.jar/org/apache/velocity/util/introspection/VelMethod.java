package org.apache.velocity.util.introspection;

public interface VelMethod {
   Object invoke(Object var1, Object[] var2) throws Exception;

   boolean isCacheable();

   String getMethodName();

   Class getReturnType();
}
