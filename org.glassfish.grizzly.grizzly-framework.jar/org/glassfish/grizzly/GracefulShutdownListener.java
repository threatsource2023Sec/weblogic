package org.glassfish.grizzly;

public interface GracefulShutdownListener {
   void shutdownRequested(ShutdownContext var1);

   void shutdownForced();
}
