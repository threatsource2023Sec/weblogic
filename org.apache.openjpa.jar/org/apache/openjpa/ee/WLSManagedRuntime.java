package org.apache.openjpa.ee;

import java.lang.reflect.Method;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

public class WLSManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime {
   private final Method _txHelperMeth;
   private final Method _txManagerMeth;

   public WLSManagedRuntime() throws ClassNotFoundException, NoSuchMethodException {
      Class txHelper = Class.forName("weblogic.transaction.TransactionHelper");
      this._txHelperMeth = txHelper.getMethod("getTransactionHelper", (Class[])null);
      this._txManagerMeth = txHelper.getMethod("getTransactionManager", (Class[])null);
   }

   public TransactionManager getTransactionManager() throws Exception {
      Object o = this._txHelperMeth.invoke((Object)null, (Object[])null);
      return (TransactionManager)this._txManagerMeth.invoke(o, (Object[])null);
   }

   public void setRollbackOnly(Throwable cause) throws Exception {
      Transaction transaction = this.getTransactionManager().getTransaction();

      try {
         transaction.getClass().getMethod("setRollbackOnly", Throwable.class).invoke(transaction, cause);
      } catch (Throwable var4) {
         transaction.setRollbackOnly();
      }

   }

   public Throwable getRollbackCause() throws Exception {
      Transaction transaction = this.getTransactionManager().getTransaction();

      try {
         return (Throwable)transaction.getClass().getMethod("getRollbackReason").invoke(transaction);
      } catch (Throwable var3) {
         return null;
      }
   }
}
