package weblogic.diagnostics.lifecycle;

import weblogic.application.ApplicationFactoryManager;
import weblogic.diagnostics.module.WLDFDeploymentFactory;

public class ManagerLifecycleImpl implements DiagnosticComponentLifecycle {
   private static ManagerLifecycleImpl singleton = new ManagerLifecycleImpl();

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addDeploymentFactory(WLDFDeploymentFactory.getInstance());
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }
}
