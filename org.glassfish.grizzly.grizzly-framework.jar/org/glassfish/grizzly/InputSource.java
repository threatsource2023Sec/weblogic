package org.glassfish.grizzly;

public interface InputSource {
   void notifyAvailable(ReadHandler var1);

   void notifyAvailable(ReadHandler var1, int var2);

   boolean isFinished();

   int readyData();

   boolean isReady();
}
