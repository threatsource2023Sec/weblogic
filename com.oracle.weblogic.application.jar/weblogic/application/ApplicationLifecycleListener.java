package weblogic.application;

public abstract class ApplicationLifecycleListener {
   public void preStart(ApplicationLifecycleEvent evt) throws ApplicationException {
   }

   public void postStart(ApplicationLifecycleEvent evt) throws ApplicationException {
   }

   public void preStop(ApplicationLifecycleEvent evt) throws ApplicationException {
   }

   public void postStop(ApplicationLifecycleEvent evt) throws ApplicationException {
   }
}
