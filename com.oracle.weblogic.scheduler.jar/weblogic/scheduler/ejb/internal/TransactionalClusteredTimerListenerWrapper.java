package weblogic.scheduler.ejb.internal;

import weblogic.scheduler.TransactionalTimerListener;
import weblogic.scheduler.ejb.ClusteredTimerListener;

public final class TransactionalClusteredTimerListenerWrapper extends ClusteredTimerListenerWrapper implements TransactionalTimerListener {
   public TransactionalClusteredTimerListenerWrapper(String annotation, ClusteredTimerListener clusteredTimerListener, String dispatchPolicy) {
      super(annotation, clusteredTimerListener, dispatchPolicy);
   }

   public TransactionalClusteredTimerListenerWrapper() {
   }

   public int getTransactionTimeoutSeconds() {
      return this.getEJBTimerListener().getTransactionTimeoutSeconds();
   }

   public String toString() {
      return this.getEJBTimerListener() == null ? super.toString() : "ApplicationName:" + this.getApplicationName() + " ModuleName:" + this.getModuleName() + " EJB:" + this.getEJBTimerListener().getEjbName() + " CallbackMethodSignature:" + this.getEJBTimerListener().getCallbackMethodSignature();
   }
}
