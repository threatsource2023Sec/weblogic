package org.apache.openjpa.ee;

import java.lang.reflect.Method;
import javax.transaction.TransactionManager;

public class SunOneManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime {
   private Method _switchMeth;
   private Method _txManagerMeth;

   public SunOneManagedRuntime() throws ClassNotFoundException, NoSuchMethodException {
      Class swtch = Class.forName("com.sun.enterprise.Switch");
      this._switchMeth = swtch.getMethod("getSwitch", (Class[])null);
      this._txManagerMeth = swtch.getMethod("getTransactionManager", (Class[])null);
   }

   public TransactionManager getTransactionManager() throws Exception {
      Object sw = this._switchMeth.invoke((Object)null, (Object[])null);
      return (TransactionManager)this._txManagerMeth.invoke(sw, (Object[])null);
   }

   public void setRollbackOnly(Throwable cause) throws Exception {
      this.getTransactionManager().getTransaction().setRollbackOnly();
   }

   public Throwable getRollbackCause() throws Exception {
      return null;
   }
}
