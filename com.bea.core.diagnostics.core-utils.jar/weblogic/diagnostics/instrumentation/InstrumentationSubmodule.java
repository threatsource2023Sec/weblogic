package weblogic.diagnostics.instrumentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.descriptor.WLDFInstrumentationBean;
import weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.module.WLDFBaseSubModule;
import weblogic.diagnostics.module.WLDFModuleException;
import weblogic.diagnostics.module.WLDFSubModule;
import weblogic.j2ee.descriptor.wl.ClassLoadingBean;
import weblogic.j2ee.descriptor.wl.ShareableBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.runtime.RuntimeMBean;

public final class InstrumentationSubmodule extends WLDFBaseSubModule {
   private ApplicationContextInternal appCtx;
   private WLDFResourceBean wldfResource;
   private boolean isSystemResource;
   private static HashMap proposedUpdateMap = new HashMap(1);

   public static final WLDFSubModule createInstance() {
      return new InstrumentationSubmodule();
   }

   private InstrumentationSubmodule() {
   }

   private String getName() {
      return !this.isSystemResource && this.appCtx != null ? this.appCtx.getApplicationId() : "_WL_INTERNAL_SERVER_SCOPE";
   }

   public void init(String partition, ApplicationContext appCtx, WLDFResourceBean wldfResource) throws WLDFModuleException {
      try {
         super.init(partition, appCtx, wldfResource);
         this.appCtx = (ApplicationContextInternal)appCtx;
         this.wldfResource = wldfResource;
         this.isSystemResource = this.appCtx == null || this.appCtx.getSystemResourceMBean() != null;
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule INIT for " + this.getName() + ", bean: " + wldfResource.getName());
         }

         if (this.isSystemResource) {
            this.findOrCreateServerInstrumentationScope();
         }
      } catch (ClassCastException var5) {
         throw new WLDFModuleException("Unexpected application context type", var5);
      }
   }

   private InstrumentationScope findOrCreateServerInstrumentationScope() throws WLDFModuleException {
      InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
      String appName = this.getName();
      InstrumentationScope scope = manager.findInstrumentationScope(appName);
      if (scope == null) {
         this.createInstrumentationScope(appName, (ApplicationRuntimeMBeanImpl)null);
      } else {
         scope.addDescriptor(this.wldfResource.getInstrumentation());
         scope.registerRuntime((RuntimeMBean)null);
      }

      return scope;
   }

   public void prepare() throws WLDFModuleException {
      String appName = this.getName();
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule PREPARE for " + appName + ", bean: " + this.wldfResource.getName());
      }

      InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
      if (this.isSystemResource) {
         InstrumentationScope serverScope = manager.findInstrumentationScope(appName);
         serverScope.initializeScope();
         if (!serverScope.isEnabled() && !this.isEmptyInstrumentationScope(serverScope)) {
            DiagnosticsLogger.logWarnInstrumentationScopeDisabled(appName);
         }

      } else if (!manager.isEnabled()) {
         if (!this.isEmptyInstrumentationDescriptor()) {
            DiagnosticsLogger.logWarnInstrumentationManagerDisabled(appName);
         }

      } else {
         WeblogicApplicationBean appBean;
         if (this.appCtx != null) {
            String partitionName = this.appCtx.getPartitionName();
            if (!partitionName.equals("DOMAIN")) {
               appBean = this.appCtx.getWLApplicationDD();
               ClassLoadingBean loadingBean = appBean != null ? appBean.getClassLoading() : null;
               ShareableBean[] sBeans = loadingBean != null ? loadingBean.getShareables() : null;
               if (sBeans != null && sBeans.length > 0) {
                  DiagnosticsLogger.logDisableInstrumentationWithShareableClassloader(ApplicationVersionUtils.getNonPartitionName(appName), partitionName);
                  return;
               }
            }
         }

         ApplicationRuntimeMBeanImpl appRuntime = this.appCtx == null ? null : this.appCtx.getRuntime();
         appBean = null;
         InstrumentationScope scope = manager.findInstrumentationScope(appName);
         if (scope != null) {
            throw new WLDFModuleException("Instrumentation scope " + appName + " already exists");
         } else {
            this.createApplicationInstrumentationScope(appName, appRuntime);
         }
      }
   }

   private boolean isEmptyInstrumentationDescriptor() {
      WLDFInstrumentationBean instrumentationBean = this.wldfResource != null ? this.wldfResource.getInstrumentation() : null;
      if (instrumentationBean != null) {
         if (instrumentationBean.isEnabled()) {
            return false;
         }

         WLDFInstrumentationMonitorBean[] mons = instrumentationBean.getWLDFInstrumentationMonitors();
         if (mons != null && mons.length > 0) {
            return false;
         }
      }

      return true;
   }

   private boolean isEmptyInstrumentationScope(InstrumentationScope scope) {
      if (scope != null) {
         DiagnosticMonitorControl[] mons = scope.getMonitorControlsInScope();
         return mons != null ? mons.length == 0 : true;
      } else {
         return true;
      }
   }

   private void createApplicationInstrumentationScope(String appName, ApplicationRuntimeMBeanImpl appRuntime) throws WLDFModuleException {
      try {
         InstrumentationScope appScope = this.createInstrumentationScope(appName, appRuntime);
         appScope.initializeScope();
         if (!appScope.isEnabled() && !this.isEmptyInstrumentationScope(appScope)) {
            DiagnosticsLogger.logWarnInstrumentationScopeDisabled(appName);
         }

      } catch (Exception var4) {
         throw new WLDFModuleException(var4);
      }
   }

   private InstrumentationScope createInstrumentationScope(String appName, ApplicationRuntimeMBeanImpl appRuntime) throws WLDFModuleException {
      try {
         InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
         WLDFInstrumentationBean instrumentationBean = this.wldfResource != null ? this.wldfResource.getInstrumentation() : null;
         InstrumentationScope scope = manager.createInstrumentationScope(appName, instrumentationBean);
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule createInstrumentationScope for " + appName + " created scope=" + scope);
         }

         if (scope != null) {
            DiagnosticsLogger.logInformInstrumentationScopeCreation(appName);
            scope.registerRuntime(appRuntime);
         }

         return scope;
      } catch (Exception var6) {
         throw new WLDFModuleException("Failed to create instrumentation module for application " + appName, var6);
      }
   }

   public void activate() throws WLDFModuleException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule ACTIVATE for " + this.getName() + ", bean: " + this.wldfResource.getName());
      }

   }

   public void deactivate() throws WLDFModuleException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule DEACTIVATE for " + this.getName() + ", bean: " + this.wldfResource.getName());
      }

      this.deactivateScope();
   }

   public void unprepare() throws WLDFModuleException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule UNPREPARE for " + this.getName() + ", bean: " + this.wldfResource.getName());
      }

      this.deactivateScope();
   }

   private void deactivateScope() {
      String appName = this.getName();
      InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
      InstrumentationScope scope = manager.findInstrumentationScope(appName);
      if (scope != null) {
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule deactivating scope=" + scope.getName() + " for " + appName + " found ");
         }

         scope.deactivateDescriptor(this.wldfResource.getInstrumentation());
         if (!scope.isEnabled()) {
            scope.unregisterRuntime();
            manager.deleteInstrumentationScope(scope);
         }
      }

   }

   public void destroy() throws WLDFModuleException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule DESTROY for " + this.getName() + ", bean: " + this.wldfResource.getName());
      }

      String appName = this.getName();
      InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
      InstrumentationScope scope = manager.findInstrumentationScope(appName);
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule unprepare for " + appName + " found scope=" + scope);
      }

      if (scope != null && scope.isEnabled()) {
         scope.initializeScope();
      }

   }

   public void prepareUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule PREPAREUPDATE for " + this.getName() + ", proposed bean: " + proposedBean.getName());
      }

      if (this.isSystemResource) {
         String appName = this.getName();
         InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
         InstrumentationScope scope = manager.findInstrumentationScope(appName);
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule PREPAREUPDATE for " + appName + " found scope=" + scope);
         }

         if (scope != null) {
            synchronized(proposedUpdateMap) {
               List proposedUpdateList = (List)proposedUpdateMap.get(appName);
               if (proposedUpdateList == null) {
                  List proposedUpdateList = new ArrayList(1);
                  proposedUpdateList.add(proposedBean);
                  proposedUpdateMap.put(appName, proposedUpdateList);
               } else {
                  proposedUpdateList.add(proposedBean);
               }
            }
         }
      }

   }

   public void activateUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule ACTIVATEUPDATE for " + this.getName() + ", proposed bean: " + proposedBean.getName());
      }

      String appName = this.getName();
      InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
      InstrumentationScope scope = manager.findInstrumentationScope(appName);
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule ACTIVATEUPDATE for " + appName + " found scope=" + scope);
      }

      if (scope != null) {
         if (!this.isSystemResource) {
            WLDFInstrumentationBean instrumentationBean = proposedBean.getInstrumentation();
            InstrumentationScope proposedScope = InstrumentationScope.createInstrumentationScope(appName, instrumentationBean);
            scope.merge(proposedScope);
         } else {
            this.updateServerScope(appName, scope);
         }

         if (!scope.isEnabled() && !this.isEmptyInstrumentationScope(scope)) {
            DiagnosticsLogger.logWarnInstrumentationScopeDisabled(appName);
         }
      }

   }

   public void rollbackUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("InstrumentationSubmodule ROLLBACKUPDATE for " + this.getName() + ", proposed bean: " + proposedBean.getName());
      }

      proposedUpdateMap.remove(this.getName());
   }

   private void updateServerScope(String appName, InstrumentationScope scope) {
      List proposedBeans = (List)proposedUpdateMap.remove(appName);
      if (proposedBeans != null) {
         Iterator var4 = proposedBeans.iterator();

         while(var4.hasNext()) {
            WLDFResourceBean wldfBean = (WLDFResourceBean)var4.next();
            scope.addDescriptor(wldfBean.getInstrumentation());
         }

         scope.initializeScope();
      }

   }
}
