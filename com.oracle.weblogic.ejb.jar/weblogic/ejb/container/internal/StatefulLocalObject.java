package weblogic.ejb.container.internal;

import weblogic.ejb.container.interfaces.LocalHandle30;
import weblogic.ejb.container.manager.StatefulSessionManager;

public class StatefulLocalObject extends BaseLocalObject {
   protected boolean __WL_postInvokeTxRetry(InvocationWrapper wrap, Throwable ee) throws Exception {
      boolean retry = super.__WL_postInvokeTxRetry(wrap, ee);
      if (!retry && wrap.getMethodDescriptor().isRemoveMethod()) {
         MethodDescriptor md = wrap.getMethodDescriptor();
         if (ee == null || !md.isRetainIfException() && this.deploymentInfo.getExceptionInfo(md.getMethod(), ee).isAppException()) {
            ((StatefulSessionManager)this.getBeanManager()).removeForRemoveAnnotation(wrap);
         }
      }

      return retry;
   }

   public final LocalHandle30 getLocalHandle30Object(Object businessLocalObject, Object pk) {
      return new LocalHandle30Impl(this, businessLocalObject, pk);
   }
}
