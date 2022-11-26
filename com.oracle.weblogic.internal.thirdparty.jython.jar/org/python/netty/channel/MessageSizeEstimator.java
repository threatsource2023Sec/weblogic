package org.python.netty.channel;

public interface MessageSizeEstimator {
   Handle newHandle();

   public interface Handle {
      int size(Object var1);
   }
}
