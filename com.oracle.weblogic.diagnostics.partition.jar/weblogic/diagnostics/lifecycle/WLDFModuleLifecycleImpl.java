package weblogic.diagnostics.lifecycle;

import weblogic.application.ApplicationFactoryManager;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.diagnostics.module.WLDFAppDeploymentExtensionFactory;
import weblogic.diagnostics.module.WLDFToolsExtensionFactory;

public class WLDFModuleLifecycleImpl implements DiagnosticComponentLifecycle {
   private static WLDFModuleLifecycleImpl INSTANCE = new WLDFModuleLifecycleImpl();

   public static final DiagnosticComponentLifecycle getInstance() {
      return INSTANCE;
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public int getStatus() {
      return 0;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      ApplicationFactoryManager.getApplicationFactoryManager().addAppDeploymentExtensionFactory(new WLDFAppDeploymentExtensionFactory());
      ToolsFactoryManager.addExtensionFactory(new WLDFToolsExtensionFactory());
   }
}
