package weblogic.diagnostics.runtimecontrol.internal;

import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.l18n.DiagnosticsServicesTextTextFormatter;
import weblogic.diagnostics.module.WLDFModule;
import weblogic.diagnostics.module.WLDFModuleFactory;
import weblogic.diagnostics.runtimecontrol.RuntimeProfileDriver;
import weblogic.management.ManagementException;

public class RuntimeProfileDriverImpl extends RuntimeProfileDriver {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticRuntimeControlDriver");
   private static RuntimeProfileDriverImpl singleton;
   private final WLDFModuleFactory moduleFactory = WLDFModuleFactory.getInstance();
   private WLDFResourceRegistrationHandler wldfResourceRegistrationHandler;

   RuntimeProfileDriverImpl() {
   }

   public static RuntimeProfileDriver getSingleton() {
      if (singleton == null) {
         singleton = new RuntimeProfileDriverImpl();
      }

      return singleton;
   }

   public void setWLDFResourceRegistrationHandler(WLDFResourceRegistrationHandler handler) {
      if (this.wldfResourceRegistrationHandler == null) {
         this.wldfResourceRegistrationHandler = handler;
      } else {
         throw new IllegalStateException("Internal error: Registration handler already initialized.");
      }
   }

   public void deploy(WLDFResourceBean bean) throws ManagementException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("deploying WLDF profile: " + bean.getName());
      }

      this.findOrCreateModule(bean);
   }

   public void enable(WLDFResourceBean bean, boolean enable) throws ManagementException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("enabling WLDF profile " + bean.getName() + " set to " + enable);
      }

      WLDFModule module;
      if (enable) {
         module = this.findOrCreateModule(bean);
         module.activate();
         if (this.wldfResourceRegistrationHandler != null) {
            this.wldfResourceRegistrationHandler.registerWLDFResource(bean.getName(), bean);
         }
      } else {
         module = this.moduleFactory.findModule(bean);
         if (module != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Module " + bean.getName() + " not active, skipping disable call");
            }

            if (this.wldfResourceRegistrationHandler != null) {
               this.wldfResourceRegistrationHandler.unregisterWLDFResource(bean.getName(), bean);
            }

            module.deactivate();
         }
      }

   }

   public void undeploy(WLDFResourceBean bean) throws ManagementException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("undeploying WLDF profile: " + bean.getName());
      }

      WLDFModule module = this.moduleFactory.removeModule(bean);
      if (module != null) {
         if (module.isActivated()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("deactivating and unpreparing WLDF profile: " + bean.getName());
            }

            module.deactivate();
            module.unprepare();
         }

         module.destroy((UpdateListener.Registration)null);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("undeploy: no module instance found for resource " + bean.getName());
      }

   }

   public boolean isEnabled(WLDFResourceBean bean) {
      return this.isEnabled(bean.getName());
   }

   public boolean isEnabled(String beanName) {
      WLDFModule module = this.moduleFactory.findModuleByName(beanName);
      return module != null ? module.isActivated() : false;
   }

   private WLDFModule findOrCreateModule(WLDFResourceBean bean) throws ManagementException, ModuleException {
      WLDFModule module = this.moduleFactory.findOrCreateModule(bean);
      if (module == null) {
         throw new ManagementException(DiagnosticsServicesTextTextFormatter.getInstance().getUnableToCreateRuntimeControlText(bean.getName()));
      } else {
         if (module.isNew()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("New module, initializing and preparing WLDF profile: " + bean.getName());
            }

            module.init(bean);
            module.prepare();
         }

         return module;
      }
   }
}
