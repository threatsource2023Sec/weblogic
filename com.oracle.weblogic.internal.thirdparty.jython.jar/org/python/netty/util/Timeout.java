package org.python.netty.util;

public interface Timeout {
   Timer timer();

   TimerTask task();

   boolean isExpired();

   boolean isCancelled();

   boolean cancel();
}
