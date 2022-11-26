package weblogic.diagnostics.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.FatalModuleException;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorLoader;
import weblogic.diagnostics.l18n.DiagnosticsServicesTextTextFormatter;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class WLDFModule implements Module, UpdateListener, WLDFModuleStates {
   private static final DiagnosticsServicesTextTextFormatter txtFormatter = DiagnosticsServicesTextTextFormatter.getInstance();
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticsModule");
   private String moduleURI = null;
   private WLDFResourceBean wldfResource = null;
   private WLDFSubModule[] subModules;
   private int moduleState = 0;
   private WLDFResourceBean proposedWLDFResource;
   private DescriptorDiff proposedUpdates = null;
   private ApplicationContextInternal appCtxInternal;
   private String partitionName = "";
   private SituationalConfigManager situationalConfigManager;
   public static final Object UPDATE_SYNC = new Object();

   WLDFModule(String uri) {
      this.moduleURI = uri;
      this.situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);
   }

   WLDFModule() {
   }

   public String getId() {
      return this.moduleURI;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_WLDF;
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[0];
   }

   public DescriptorBean[] getDescriptors() {
      return this.wldfResource == null ? new DescriptorBean[0] : new DescriptorBean[]{(DescriptorBean)this.wldfResource};
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.initUsingLoader(appCtx, parent, reg);
      return parent;
   }

   public void init(WLDFResourceBean descriptor) throws ModuleException {
      if (this.moduleState >= 1) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Module " + this.getModuleName() + " already initialized, returning");
         }

      } else {
         this.wldfResource = descriptor;
         this.initModuleInternal((ApplicationContext)null, (UpdateListener.Registration)null, true);
      }
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      if (this.moduleState >= 1) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Module " + this.getModuleName() + " already initialized, returning");
         }

      } else {
         this.initDescriptor(appCtx, reg);
      }
   }

   public void prepare() throws ModuleException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Inside prepare() of " + this.getModuleName() + " with moduleState = " + this.moduleState + " module=" + this);
      }

      if (this.moduleState < 1) {
         throw new ModuleException(txtFormatter.getModulePrepareCalledBeforeInitializedText(this.getModuleName(), this.stateToString(1), this.stateToString(this.moduleState)));
      } else if (this.moduleState >= 2) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Module " + this.getModuleName() + " already prepared");
         }

      } else {
         try {
            for(int i = 0; i < this.subModules.length; ++i) {
               this.subModules[i].prepare();
            }
         } catch (ModuleException var2) {
            if (var2 instanceof FatalModuleException) {
               throw var2;
            }

            throw new FatalModuleException(var2);
         }

         this.moduleState = 2;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("WLDFModule PREPARED successfully");
         }

      }
   }

   public void activate() throws ModuleException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Inside activate() " + this.getModuleName() + " with moduleState = " + this.moduleState + " module=" + this);
      }

      if (this.moduleState < 2) {
         throw new ModuleException(txtFormatter.getModuleActivateCalledBeforePreparedText(this.getModuleName(), this.stateToString(3), this.stateToString(this.moduleState)));
      } else if (this.moduleState == 3) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Module " + this.getModuleName() + " already activated");
         }

      } else {
         synchronized(UPDATE_SYNC) {
            try {
               for(int i = 0; i < this.subModules.length; ++i) {
                  this.subModules[i].activate();
               }
            } catch (ModuleException var4) {
               if (var4 instanceof FatalModuleException) {
                  throw var4;
               }

               throw new FatalModuleException(var4);
            }
         }

         this.moduleState = 3;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("WLDFModule ACTIVATED successfully");
         }

      }
   }

   public void start() throws ModuleException {
   }

   public void deactivate() throws ModuleException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Inside deactivate() of " + this.getModuleName() + " with moduleState = " + this.moduleState + " module=" + this);
      }

      if (this.moduleState <= 2) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Module " + this.getModuleName() + " already deactivated");
         }

      } else {
         synchronized(UPDATE_SYNC) {
            for(int i = 0; i < this.subModules.length; ++i) {
               this.subModules[i].deactivate();
            }

            this.moduleState = 2;
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("WLDFModule deactivated successfully");
            }

         }
      }
   }

   public void unprepare() throws ModuleException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Inside unprepare() of " + this.getModuleName() + " with moduleState = " + this.moduleState + " module=" + this);
      }

      if (this.moduleState <= 1) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Module " + this.getModuleName() + " already unprepared");
         }

      } else {
         for(int i = 0; i < this.subModules.length; ++i) {
            this.subModules[i].unprepare();
         }

         this.moduleState = 1;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("WLDFModule unprepared successfully");
         }

      }
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Inside destroy() of " + this.getModuleName() + " with moduleState = " + this.moduleState + " module=" + this);
      }

      if (this.moduleState <= 0) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Module " + this.getModuleName() + " already in initial state");
         }

      } else {
         for(int i = 0; i < this.subModules.length; ++i) {
            this.subModules[i].destroy();
         }

         this.moduleState = 0;
         if (reg != null) {
            reg.removeUpdateListener(this);
         }

      }
   }

   private String getModuleName() {
      return this.wldfResource != null ? this.wldfResource.getName() : this.moduleURI;
   }

   public void remove() throws ModuleException {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
   }

   public void forceProductionToAdmin() throws ModuleException {
   }

   public boolean acceptURI(String uri) {
      return this.moduleURI == null ? false : this.moduleURI.equals(uri);
   }

   public void prepareUpdate(String uri) throws ModuleException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLDF Module " + this.getModuleName() + " during prepareUpdate() callback.  State is " + this.moduleState + " module=" + this);
      }

      try {
         if (this.appCtxInternal.getSystemResourceMBean() != null) {
            uri = DomainDir.getPathRelativeConfigDir(uri);
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Preparing update from URI = " + this.getModuleName());
         }

         this.proposedWLDFResource = this.loadDescriptor(this.appCtxInternal, uri, true);
         Descriptor original = ((DescriptorBean)this.wldfResource).getDescriptor();
         Descriptor proposed = ((DescriptorBean)this.proposedWLDFResource).getDescriptor();
         this.proposedUpdates = original.computeDiff(proposed);
      } catch (Exception var4) {
         throw new WLDFModuleException(var4);
      }

      for(int i = 0; i < this.subModules.length; ++i) {
         this.subModules[i].prepareUpdate(this.proposedWLDFResource, this.proposedUpdates);
      }

      this.moduleState = 4;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Update prepared for URI = " + this.getModuleName());
      }

   }

   public void activateUpdate(String uri) throws ModuleException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLDF Module " + this.getModuleName() + " during activateUpdate() callback.  State is " + this.moduleState + " module=" + this);
      }

      synchronized(UPDATE_SYNC) {
         int i = 0;

         while(true) {
            if (i >= this.subModules.length) {
               break;
            }

            this.subModules[i].activateUpdate(this.proposedWLDFResource, this.proposedUpdates);
            ++i;
         }
      }

      this.moduleState = 3;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Activated update from URI = " + uri);
      }

   }

   public void rollbackUpdate(String uri) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLDF Module " + this.getModuleName() + " during rollbackUpdate() callback.  State is " + this.moduleState + " module=" + this);
      }

      synchronized(UPDATE_SYNC) {
         int i = 0;

         while(true) {
            if (i >= this.subModules.length) {
               break;
            }

            this.subModules[i].rollbackUpdate(this.proposedWLDFResource, this.proposedUpdates);
            ++i;
         }
      }

      this.moduleState = 3;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Rolled back update from URI = " + uri);
      }

   }

   public boolean isActivated() {
      return this.moduleState == 3;
   }

   public boolean isPrepared() {
      return this.moduleState >= 2;
   }

   public boolean isInitialized() {
      return this.moduleState >= 1;
   }

   public boolean isNew() {
      return this.moduleState == 0;
   }

   private void initDescriptor(ApplicationContext appCtx, UpdateListener.Registration reg) throws ModuleException {
      try {
         ApplicationContextInternal appCtxInternal = (ApplicationContextInternal)appCtx;
         this.appCtxInternal = appCtxInternal;
         WLDFSystemResourceMBean wldfSystemRes = (WLDFSystemResourceMBean)appCtxInternal.getSystemResourceMBean();
         if (wldfSystemRes != null) {
            this.wldfResource = wldfSystemRes.getWLDFResource();
         } else {
            this.wldfResource = this.loadDescriptor(appCtxInternal, this.moduleURI, false);
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Initialized WLDFResource");
         }

         this.initModuleInternal(appCtx, reg, wldfSystemRes != null);
      } catch (Exception var5) {
         throw new ModuleException("Error creating WLDF descriptor from " + this.moduleURI, var5);
      }
   }

   private void initModuleInternal(ApplicationContext appCtx, UpdateListener.Registration reg, boolean isSystemResource) throws WLDFModuleException {
      if (appCtx != null) {
         this.partitionName = ApplicationVersionUtils.getPartitionName(appCtx.getApplicationId());
         if ("DOMAIN".equals(this.partitionName)) {
            this.partitionName = "";
         }
      }

      if (isSystemResource) {
         if (this.partitionName != null && !this.partitionName.isEmpty()) {
            this.subModules = SubModuleRegistry.getPartitionScopedWLDFSubModules();
         } else {
            this.subModules = SubModuleRegistry.getWLDFSubModules();
         }
      } else {
         this.subModules = SubModuleRegistry.getAppScopedWLDFSubModules();
      }

      for(int i = 0; i < this.subModules.length; ++i) {
         this.subModules[i].init(this.partitionName, appCtx, this.wldfResource);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Initialized WLDF Submodules");
      }

      if (reg != null) {
         reg.addUpdateListener(this);
      }

      this.moduleState = 1;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Initialized WLDF Module " + this.wldfResource.getName() + " successfully");
      }

   }

   private WLDFResourceBean loadDescriptor(ApplicationContextInternal appCtxInternal, String uri, boolean doUpdate) throws IOException, XMLStreamException {
      InputStream fis = null;
      VirtualJarFile vjf = null;

      WLDFResourceBean var13;
      try {
         if (appCtxInternal.getSystemResourceMBean() != null) {
            DescriptorManager dm = new DescriptorManager();
            fis = this.situationalConfigManager.getSituationalConfigInputStream(appCtxInternal.getSystemResourceMBean().getSourcePath());
            if (fis == null) {
               fis = new FileInputStream(uri);
            }

            WLDFDescriptorLoader loader = new WLDFDescriptorLoader((InputStream)fis, dm, new ArrayList());
            WLDFResourceBean var31 = (WLDFResourceBean)loader.loadDescriptorBean();
            return var31;
         }

         File configDir = null;
         DeploymentPlanBean plan = null;
         String appName = (new File(appCtxInternal.getAppDeploymentMBean().getAbsoluteSourcePath())).getName();
         vjf = appCtxInternal.getApplicationFileManager().getVirtualJarFile();
         AppDeploymentMBean dmb = appCtxInternal.getAppDeploymentMBean();
         plan = this.getDeploymentPlan(appCtxInternal, doUpdate);
         if (dmb.getPlanDir() != null) {
            configDir = new File(dmb.getLocalPlanDir());
         }

         File externalDD = this.getExternalDiagnosticDescriptorFile(plan);
         WLDFDescriptorLoader myDescriptorLoader;
         if (externalDD != null) {
            myDescriptorLoader = new WLDFDescriptorLoader(externalDD, configDir, plan, appName, uri);
         } else {
            myDescriptorLoader = new WLDFDescriptorLoader(vjf, configDir, plan, appName, uri);
         }

         WLDFResourceBean ret = this.getWLDFResourceBean(myDescriptorLoader);
         if (ret == null) {
            var13 = (WLDFResourceBean)(new DescriptorManager()).createDescriptorRoot(WLDFResourceBean.class).getRootBean();
            return var13;
         }

         var13 = ret;
      } finally {
         try {
            if (fis != null) {
               ((InputStream)fis).close();
            }
         } catch (IOException var27) {
         }

         try {
            if (vjf != null) {
               vjf.close();
            }
         } catch (IOException var26) {
         }

      }

      return var13;
   }

   private DeploymentPlanBean getDeploymentPlan(ApplicationContextInternal appCtxInternal, boolean doUpdate) {
      String appName = appCtxInternal.getApplicationId();
      AppDeploymentMBean dmb = null;
      if (doUpdate) {
         DomainMBean proposedDomain = appCtxInternal.getProposedDomain();
         if (proposedDomain != null) {
            dmb = proposedDomain.lookupAppDeployment(appName);
         }
      }

      if (dmb == null) {
         dmb = appCtxInternal.getAppDeploymentMBean();
      }

      return dmb.getDeploymentPlanDescriptor();
   }

   private WLDFResourceBean getWLDFResourceBean(WLDFDescriptorLoader myDescriptorLoader) throws IOException {
      try {
         return (WLDFResourceBean)myDescriptorLoader.loadDescriptorBean();
      } catch (IOException var4) {
         throw var4;
      } catch (Exception var5) {
         IOException e1 = new IOException(var5.getMessage());
         e1.initCause(var5);
         throw e1;
      }
   }

   private File getExternalDiagnosticDescriptorFile(DeploymentPlanBean plan) {
      if (plan == null) {
         return null;
      } else {
         File externalDD = null;
         ModuleOverrideBean mob = plan.findModuleOverride(this.appCtxInternal.getApplicationFileName());
         if (mob != null) {
            ModuleDescriptorBean[] moduleDesBeans = mob.getModuleDescriptors();

            for(int i = 0; i < moduleDesBeans.length; ++i) {
               if (moduleDesBeans[i].isExternal() && moduleDesBeans[i].getRootElement().equals("wldf-resource")) {
                  externalDD = new File(plan.getConfigRoot(), moduleDesBeans[i].getUri());
                  if (externalDD.isFile() && externalDD.exists()) {
                     break;
                  }

                  externalDD = null;
               }
            }
         }

         return externalDD;
      }
   }

   private String stateToString(int state) {
      switch (state) {
         case 0:
            return "NEW";
         case 1:
            return "INITIALIZED";
         case 2:
            return "PREPARED";
         case 3:
            return "ACTIVATED";
         case 4:
            return "UPDATE_PENDING";
         default:
            return "UNKNOWN";
      }
   }
}
