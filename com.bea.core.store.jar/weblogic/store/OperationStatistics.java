package weblogic.store;

public interface OperationStatistics {
   long getCreateCount();

   long getReadCount();

   long getUpdateCount();

   long getDeleteCount();

   long getObjectCount();
}
