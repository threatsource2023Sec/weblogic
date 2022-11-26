package weblogic.store;

public interface StoreStatistics extends OperationStatistics {
   long getPhysicalWriteCount();

   long getPhysicalReadCount();

   long getMappedBufferBytes();

   long getIOBufferBytes();

   int getDeviceUsedPercent();

   int getLocalUsedPercent();

   int getMaximumWriteSize();

   boolean isWarningOverloaded();

   boolean isDeviceErrorOverloaded();

   boolean isPotentiallyOverloaded(long var1);
}
