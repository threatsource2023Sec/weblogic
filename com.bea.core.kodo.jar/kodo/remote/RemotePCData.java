package kodo.remote;

import org.apache.openjpa.kernel.PCData;

public interface RemotePCData extends PCData {
   boolean isNewInstance();

   void setNewInstance(boolean var1);

   boolean isRemoteFlush();

   void setRemoteFlush(boolean var1);

   int getLockLevel();
}
