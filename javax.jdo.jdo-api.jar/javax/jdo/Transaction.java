package javax.jdo;

import javax.transaction.Synchronization;

public interface Transaction {
   void begin();

   void commit();

   void rollback();

   boolean isActive();

   boolean getRollbackOnly();

   void setRollbackOnly();

   void setNontransactionalRead(boolean var1);

   boolean getNontransactionalRead();

   void setNontransactionalWrite(boolean var1);

   boolean getNontransactionalWrite();

   void setRetainValues(boolean var1);

   boolean getRetainValues();

   void setRestoreValues(boolean var1);

   boolean getRestoreValues();

   void setOptimistic(boolean var1);

   boolean getOptimistic();

   String getIsolationLevel();

   void setIsolationLevel(String var1);

   void setSynchronization(Synchronization var1);

   Synchronization getSynchronization();

   PersistenceManager getPersistenceManager();

   void setSerializeRead(Boolean var1);

   Boolean getSerializeRead();
}
