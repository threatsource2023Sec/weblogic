package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationException;
import weblogic.application.internal.ApplicationVersionLifecycleNotifier;
import weblogic.management.DeploymentException;

public class HeadVersionLifecycleFlow extends BaseFlow {
   public HeadVersionLifecycleFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void deactivate() throws DeploymentException {
      super.deactivate();
      this.appCtx.setIsActive(false);
   }

   public void remove() throws DeploymentException {
      super.remove();

      try {
         ApplicationVersionLifecycleNotifier.sendLifecycleEventNotification(this.appCtx.getApplicationId(), ApplicationVersionLifecycleNotifier.ApplicationLifecycleAction.POST_DELETE);
      } catch (DeploymentException var3) {
         Throwable cause = var3.getCause();
         if (cause == null || !(cause instanceof ApplicationException)) {
            this.logUnexpectedException(var3);
         }

         throw var3;
      } catch (RuntimeException var4) {
         this.logUnexpectedException(var4);
         throw var4;
      } catch (Error var5) {
         this.logUnexpectedException(var5);
         throw var5;
      }
   }

   private void logUnexpectedException(Throwable e) {
      System.err.println("FAILURE: An unexpected exception has occured.");
      e.printStackTrace();
   }
}
