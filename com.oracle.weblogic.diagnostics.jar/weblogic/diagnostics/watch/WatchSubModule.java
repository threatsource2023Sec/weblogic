package weblogic.diagnostics.watch;

import weblogic.application.ApplicationContext;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.l18n.DiagnosticsServicesTextTextFormatter;
import weblogic.diagnostics.module.WLDFBaseSubModule;
import weblogic.diagnostics.module.WLDFModuleException;
import weblogic.diagnostics.module.WLDFSubModule;
import weblogic.management.ManagementException;
import weblogic.management.ManagementRuntimeException;

public final class WatchSubModule extends WLDFBaseSubModule {
   private static final DiagnosticsServicesTextTextFormatter txtFormatter = DiagnosticsServicesTextTextFormatter.getInstance();
   private WatchManager watchManager = null;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private WatchConfiguration savedConfig;
   private WatchConfiguration proposedConfig;

   private WatchSubModule() {
   }

   public static final WLDFSubModule createInstance() {
      return new WatchSubModule();
   }

   public void init(String partition, ApplicationContext appCtx, WLDFResourceBean wldfResource) {
      try {
         super.init(partition, appCtx, wldfResource);
         this.watchManager = WatchManagerFactory.getFactoryInstance(this.getPartitionName()).createWatchManager(wldfResource);
      } catch (ManagementException var5) {
         throw new ManagementRuntimeException(var5);
      }
   }

   public void prepare() throws WLDFModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.prepare");
      }

   }

   public synchronized void activate() throws WLDFModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.activate");
      }

      if (this.watchManager == null) {
         throw new WLDFModuleException(txtFormatter.getWatchModuleWatchManagerNotConfiguredText());
      } else {
         this.watchManager.activate();
      }
   }

   public synchronized void deactivate() throws WLDFModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.deactivate");
      }

      if (this.watchManager != null) {
         this.watchManager.deactivate();
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("WatchManager var is null!");
      }

   }

   public void unprepare() throws WLDFModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.unprepare");
      }

   }

   public synchronized void destroy() throws WLDFModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.destroy");
      }

      try {
         if (this.watchManager != null) {
            this.watchManager.destroy();
            this.watchManager = null;
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WatchManager var is null!");
         }

      } catch (ManagementException var2) {
         throw new WLDFModuleException(var2);
      }
   }

   public void prepareUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.prepareUpdate");
      }

      this.proposedConfig = new WatchConfiguration(this.getPartitionName(), proposedBean);
   }

   public void activateUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.activateUpdate");
      }

      if (this.watchManager == null) {
         throw new WLDFModuleException(txtFormatter.getWatchModuleWatchManagerNotConfiguredText());
      } else {
         this.watchManager.deactivate();
         if (this.proposedConfig == null) {
            this.proposedConfig = new WatchConfiguration(this.getPartitionName(), proposedBean);
         }

         this.savedConfig = this.watchManager.getWatchConfiguration();
         this.watchManager.setWatchConfiguration(this.proposedConfig);
         this.watchManager.activate();
         this.proposedConfig = null;
         this.savedConfig = null;
      }
   }

   public void rollbackUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("In WatchSubModule.rollbackUpdate");
      }

      try {
         if (this.savedConfig != null) {
            this.watchManager.deactivate();
            this.watchManager.setWatchConfiguration(this.savedConfig);
            this.watchManager.activate();
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Unable to roll back update, previous configuration not found");
         }
      } catch (WLDFModuleException var7) {
         throw new RuntimeException(var7);
      } finally {
         this.proposedConfig = null;
         this.savedConfig = null;
      }

   }
}
