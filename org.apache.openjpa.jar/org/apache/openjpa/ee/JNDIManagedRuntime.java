package org.apache.openjpa.ee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.TransactionManager;

public class JNDIManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime {
   private String _tmLoc = "java:/TransactionManager";
   private TransactionManager _tm = null;

   public String getTransactionManagerName() {
      return this._tmLoc;
   }

   public void setTransactionManagerName(String name) {
      this._tmLoc = name;
      this._tm = null;
   }

   public TransactionManager getTransactionManager() throws Exception {
      if (this._tm == null) {
         Context ctx = new InitialContext();

         try {
            this._tm = (TransactionManager)ctx.lookup(this._tmLoc);
         } finally {
            ctx.close();
         }
      }

      return this._tm;
   }

   public void setRollbackOnly(Throwable cause) throws Exception {
      this.getTransactionManager().getTransaction().setRollbackOnly();
   }

   public Throwable getRollbackCause() throws Exception {
      return null;
   }
}
