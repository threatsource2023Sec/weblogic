package weblogic.cache.tx;

import java.lang.reflect.Method;
import javax.transaction.TransactionManager;

public class WLSJTAIntegration extends AbstractTransactionManagerJTAIntegration {
   private final Object _txHelper;
   private final Method _txManagerMeth;

   public WLSJTAIntegration() throws Exception {
      Class txHelperCls = Class.forName("weblogic.transaction.TransactionHelper");
      Method txHelperMeth = txHelperCls.getMethod("getTransactionHelper", (Class[])null);
      this._txHelper = txHelperMeth.invoke((Object)null, (Object[])null);
      this._txManagerMeth = txHelperCls.getMethod("getTransactionManager", (Class[])null);
   }

   protected TransactionManager getTransactionManager() throws Exception {
      return (TransactionManager)this._txManagerMeth.invoke(this._txHelper, (Object[])null);
   }
}
