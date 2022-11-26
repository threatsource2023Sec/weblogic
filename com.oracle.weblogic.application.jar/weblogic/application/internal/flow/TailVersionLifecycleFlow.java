package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationException;
import weblogic.application.internal.ApplicationVersionLifecycleNotifier;
import weblogic.management.DeploymentException;

public class TailVersionLifecycleFlow extends BaseFlow {
   private boolean assertUndeployableCheckRequired = true;

   public TailVersionLifecycleFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void activate() throws DeploymentException {
      super.activate();
      this.appCtx.setIsActive(true);

      try {
         ApplicationVersionLifecycleNotifier.sendLifecycleEventNotification(this.appCtx.getApplicationId(), ApplicationVersionLifecycleNotifier.ApplicationLifecycleAction.POST_DEPLOY);
         this.assertUndeployableCheckRequired = false;
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

   public void assertUndeployable() throws DeploymentException {
      try {
         ApplicationVersionLifecycleNotifier.sendLifecycleEventNotification(this.appCtx.getApplicationId(), ApplicationVersionLifecycleNotifier.ApplicationLifecycleAction.PRE_UNDEPLOY);
         this.assertUndeployableCheckRequired = true;
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

   public void remove() throws DeploymentException {
      if (!this.assertUndeployableCheckRequired) {
         throw new DeploymentException(new IllegalStateException("It is illegal to remove without first checking that deployment was not vetoed with the assertUndeployable method."));
      } else {
         this.assertUndeployableCheckRequired = false;
         super.remove();
      }
   }

   private void logUnexpectedException(Throwable e) {
      System.err.println("FAILURE: An unexpected exception has occured.");
      e.printStackTrace();
   }
}
