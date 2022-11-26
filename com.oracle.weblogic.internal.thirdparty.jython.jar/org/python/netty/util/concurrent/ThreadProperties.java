package org.python.netty.util.concurrent;

public interface ThreadProperties {
   Thread.State state();

   int priority();

   boolean isInterrupted();

   boolean isDaemon();

   String name();

   long id();

   StackTraceElement[] stackTrace();

   boolean isAlive();
}
