package weblogic.application;

public abstract class ApplicationVersionLifecycleListener {
   public void preDeploy(ApplicationVersionLifecycleEvent evt) throws ApplicationException {
   }

   public void postDeploy(ApplicationVersionLifecycleEvent evt) {
   }

   public void preUndeploy(ApplicationVersionLifecycleEvent evt) throws ApplicationException {
   }

   public void postDelete(ApplicationVersionLifecycleEvent evt) {
   }
}
