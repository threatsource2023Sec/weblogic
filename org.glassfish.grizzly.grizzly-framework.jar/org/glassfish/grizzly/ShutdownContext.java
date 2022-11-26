package org.glassfish.grizzly;

public interface ShutdownContext {
   Transport getTransport();

   void ready();
}
