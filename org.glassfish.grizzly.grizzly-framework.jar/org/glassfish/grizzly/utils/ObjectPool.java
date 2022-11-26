package org.glassfish.grizzly.utils;

public interface ObjectPool {
   PoolableObject poll();

   void offer(PoolableObject var1);

   void clear();
}
