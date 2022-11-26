package com.bea.cache.tx;

import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import weblogic.cache.tx.LocalTransactionManager;

public class LocalUserTransaction implements UserTransaction {
   public void begin() throws NotSupportedException {
      LocalTransactionManager.getInstance().begin();
   }

   public void commit() throws RollbackException {
      LocalTransactionManager.getInstance().commit();
   }

   public void rollback() {
      LocalTransactionManager.getInstance().rollback();
   }

   public void setRollbackOnly() {
      LocalTransactionManager.getInstance().setRollbackOnly();
   }

   public int getStatus() {
      return LocalTransactionManager.getInstance().getStatus();
   }

   public void setTransactionTimeout(int timeout) throws SystemException {
      LocalTransactionManager.getInstance().setTransactionTimeout(timeout);
   }

   public TransactionManager getTransactionManager() {
      return LocalTransactionManager.getInstance();
   }
}
