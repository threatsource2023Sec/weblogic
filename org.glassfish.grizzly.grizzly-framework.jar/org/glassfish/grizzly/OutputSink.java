package org.glassfish.grizzly;

public interface OutputSink {
   void notifyCanWrite(WriteHandler var1);

   /** @deprecated */
   @Deprecated
   void notifyCanWrite(WriteHandler var1, int var2);

   boolean canWrite();

   /** @deprecated */
   @Deprecated
   boolean canWrite(int var1);
}
