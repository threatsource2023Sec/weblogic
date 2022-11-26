package org.glassfish.grizzly;

public interface ReadHandler {
   void onDataAvailable() throws Exception;

   void onError(Throwable var1);

   void onAllDataRead() throws Exception;
}
