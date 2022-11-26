package org.glassfish.grizzly;

public interface WriteHandler {
   void onWritePossible() throws Exception;

   void onError(Throwable var1);
}
