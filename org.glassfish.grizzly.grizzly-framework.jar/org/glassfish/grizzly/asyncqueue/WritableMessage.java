package org.glassfish.grizzly.asyncqueue;

public interface WritableMessage {
   boolean hasRemaining();

   int remaining();

   boolean release();

   boolean isExternal();
}
