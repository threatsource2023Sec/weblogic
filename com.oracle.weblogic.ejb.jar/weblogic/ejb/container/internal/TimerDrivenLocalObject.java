package weblogic.ejb.container.internal;

import java.security.AccessController;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;

public class TimerDrivenLocalObject extends BaseLocalObject {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected InvocationWrapper preInvoke(Object pk, MethodDescriptor md, ContextHandler ch) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debug("[TimerDrivenLocalObject] preInvoke with md: " + md + " on: " + this);
      }

      SecurityHelper.pushAnonymousSubject(KERNEL_ID);

      try {
         InvocationWrapper wrap = InvocationWrapper.newInstance(md);
         wrap.setPrimaryKey(pk);
         super.__WL_preInvoke(wrap, ch);
         wrap.setContextClassLoader(this.beanInfo.getModuleClassLoader());
         return wrap;
      } catch (Throwable var5) {
         SecurityHelper.popRunAsSubject(KERNEL_ID);
         throw var5;
      }
   }

   public void postInvoke(InvocationWrapper wrap, Throwable ee) throws Exception {
      try {
         Throwable th = ee;

         try {
            this.postInvoke1(0, wrap, ee);
         } catch (Throwable var8) {
            th = var8;
         }

         this.__WL_postInvokeCleanup(wrap, th);
      } finally {
         wrap.resetContextClassLoader();
         SecurityHelper.popRunAsSubject(KERNEL_ID);
      }

   }

   private static void debug(String s) {
      debugLogger.debug("[TimerDrivenLocalObject] " + s);
   }
}
