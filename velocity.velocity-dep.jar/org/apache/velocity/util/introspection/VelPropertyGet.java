package org.apache.velocity.util.introspection;

public interface VelPropertyGet {
   Object invoke(Object var1) throws Exception;

   boolean isCacheable();

   String getMethodName();
}
