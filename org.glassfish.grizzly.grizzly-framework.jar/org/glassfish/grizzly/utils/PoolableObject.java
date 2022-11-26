package org.glassfish.grizzly.utils;

public interface PoolableObject {
   void prepare();

   void release();
}
