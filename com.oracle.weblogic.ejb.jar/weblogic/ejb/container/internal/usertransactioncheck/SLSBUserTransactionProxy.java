package weblogic.ejb.container.internal.usertransactioncheck;

import javax.transaction.UserTransaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.logging.Loggable;

public class SLSBUserTransactionProxy extends BaseUserTransactionProxy {
   private final WLEnterpriseBean bean;

   public SLSBUserTransactionProxy(UserTransaction delegate, WLEnterpriseBean bean) {
      super(delegate);
      this.bean = bean;
   }

   protected void checkAllowedInvoke() {
      int state = this.bean.__WL_getMethodState();
      Loggable log;
      if (state == 4) {
         log = EJBLogger.logSLSBIllegalInvokeUserTransactionMethodInEjbCreateOrPostConstructLoggable();
         throw new IllegalStateException(log.getMessage());
      } else if (state == 16) {
         log = EJBLogger.logSLSBIllegalInvokeUserTransactionMethodInEjbRemoveOrPreDestroyLoggable();
         throw new IllegalStateException(log.getMessage());
      }
   }
}
