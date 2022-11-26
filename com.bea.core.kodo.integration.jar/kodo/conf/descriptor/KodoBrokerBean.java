package kodo.conf.descriptor;

public interface KodoBrokerBean extends BrokerImplBean {
   boolean getLargeTransaction();

   void setLargeTransaction(boolean var1);

   int getAutoClear();

   void setAutoClear(int var1);

   int getDetachState();

   void setDetachState(int var1);

   boolean getNontransactionalRead();

   void setNontransactionalRead(boolean var1);

   boolean getRetainState();

   void setRetainState(boolean var1);

   boolean getEvictFromDataCache();

   void setEvictFromDataCache(boolean var1);

   boolean getDetachedNew();

   void setDetachedNew(boolean var1);

   boolean getOptimistic();

   void setOptimistic(boolean var1);

   boolean getNontransactionalWrite();

   void setNontransactionalWrite(boolean var1);

   boolean getSyncWithManagedTransactions();

   void setSyncWithManagedTransactions(boolean var1);

   boolean getMultithreaded();

   void setMultithreaded(boolean var1);

   boolean getPopulateDataCache();

   void setPopulateDataCache(boolean var1);

   boolean getIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   int getAutoDetach();

   void setAutoDetach(int var1);

   int getRestoreState();

   void setRestoreState(int var1);

   boolean getOrderDirtyObjects();

   void setOrderDirtyObjects(boolean var1);
}
