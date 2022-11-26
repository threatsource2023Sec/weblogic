package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface ReplicatedStoreMBean extends PersistentStoreMBean {
   String getDirectory();

   void setDirectory(String var1) throws InvalidAttributeValueException;

   String getAddress();

   void setAddress(String var1);

   int getPort();

   void setPort(int var1);

   int getLocalIndex();

   void setLocalIndex(int var1);

   int getRegionSize();

   void setRegionSize(int var1);

   int getBlockSize();

   void setBlockSize(int var1);

   int getIoBufferSize();

   void setIoBufferSize(int var1);

   long getBusyWaitMicroSeconds();

   void setBusyWaitMicroSeconds(long var1);

   long getSleepWaitMilliSeconds();

   void setSleepWaitMilliSeconds(long var1);

   int getMinReplicaCount();

   void setMinReplicaCount(int var1);

   int getMaxReplicaCount();

   void setMaxReplicaCount(int var1);

   int getMaximumMessageSizePercent();

   void setMaximumMessageSizePercent(int var1);

   int getSpaceLoggingStartPercent();

   void setSpaceLoggingStartPercent(int var1);

   int getSpaceLoggingDeltaPercent();

   void setSpaceLoggingDeltaPercent(int var1);

   int getSpaceOverloadYellowPercent();

   void setSpaceOverloadYellowPercent(int var1);

   int getSpaceOverloadRedPercent();

   void setSpaceOverloadRedPercent(int var1);
}
