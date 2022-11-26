package weblogic.diagnostics.module;

import java.util.HashMap;
import weblogic.application.ModuleException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.l18n.DiagnosticsServicesTextTextFormatter;
import weblogic.management.configuration.WLDFSystemResourceMBean;

public class WLDFModuleFactory {
   private static final DiagnosticsServicesTextTextFormatter txtFormatter = DiagnosticsServicesTextTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsModule");
   private static WLDFModuleFactory singleton = new WLDFModuleFactory();
   private HashMap activeModulesMap = new HashMap(4);

   WLDFModuleFactory() {
   }

   public static WLDFModuleFactory getInstance() {
      return singleton;
   }

   public synchronized WLDFModule findOrCreateModule(WLDFResourceBean bean) {
      String moduleName = bean.getName();
      WLDFModule module = this.findModuleByName(moduleName);
      if (module == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Create WLDFModule for WLDFResource " + bean.getName());
         }

         module = new WLDFModule();
         this.activeModulesMap.put(moduleName, module);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("findOrCreateModule for " + bean.getName() + " " + module);
      }

      return module;
   }

   public WLDFModule findModule(WLDFResourceBean bean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Find WLDFModule for WLDFResource " + bean.getName());
      }

      return this.findModuleByName(bean.getName());
   }

   public WLDFModule removeModule(WLDFResourceBean bean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Removing WLDFModule for WLDFResource " + bean.getName());
      }

      return this.removeModule(bean.getName());
   }

   public synchronized WLDFModule findModuleByName(String moduleName) {
      WLDFModule module = (WLDFModule)this.activeModulesMap.get(moduleName);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Found module for " + moduleName + ": " + module);
      }

      return module;
   }

   synchronized WLDFModule createModule(WLDFSystemResourceMBean mbean) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("creating WLDFModule for SystemResource " + mbean.getName());
      }

      String uri = mbean.getDescriptorFileName();
      String moduleName = mbean.getName();
      WLDFModule existingModule = this.findModule(mbean);
      if (existingModule != null) {
         throw new WLDFModuleException(txtFormatter.getModuleInstanceAlreadyExistsText(mbean.getName()));
      } else {
         WLDFModule newModule = new WLDFModule(uri);
         this.activeModulesMap.put(moduleName, newModule);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Created module for " + mbean.getName() + " " + newModule);
         }

         return newModule;
      }
   }

   WLDFModule removeModule(WLDFSystemResourceMBean mbean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Removing WLDFModule for SystemResource " + mbean.getName());
      }

      return this.removeModule(mbean.getName());
   }

   private synchronized WLDFModule findModule(WLDFSystemResourceMBean mbean) {
      return this.findModuleByName(mbean.getName());
   }

   private synchronized WLDFModule removeModule(String moduleName) {
      WLDFModule module = (WLDFModule)this.activeModulesMap.remove(moduleName);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Removed module: " + module);
      }

      return module;
   }
}
