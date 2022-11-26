package weblogic.application.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.CustomModuleContext;
import weblogic.application.MergedDescriptorModule;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ConfigurationSupportBean;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public final class DefaultModule implements Module, UpdateListener, MergedDescriptorModule {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final String parentModuleId;
   private final String descriptorUri;
   private final String parentModuleUri;
   private ModuleType parentModuleType;
   private ApplicationContextInternal applicationContext;
   private DescriptorBean descriptorBean;
   private String descriptorNamespace;
   private String appModuleName;
   private final ConfigModuleCallbackHandler callbackHandler;
   private final boolean failOnNonDynamicChanges;
   private final boolean mergeLibraryDescriptors;
   private String bindingJarUri;
   private ConfigDescriptorManager configDescManager;
   private boolean descriptorParsed;
   private boolean useBindingCache;
   private boolean ignoreMissingDescriptors;

   public DefaultModule(CustomModuleContext customModuleContext, CustomModuleBean customModuleBean, ConfigModuleCallbackHandler callbackHandler, String descriptorNamespace, boolean failOnNonDynamicChanges, boolean mergeLibraryDescriptors) {
      this(customModuleContext.getParentModuleUri(), customModuleContext.getParentModuleId(), customModuleBean.getUri(), customModuleContext.getModuleProviderBean().getBindingJarUri(), callbackHandler, failOnNonDynamicChanges, mergeLibraryDescriptors);
      if (this.parentModuleUri != null) {
         this.parentModuleType = ModuleType.WAR;
      } else {
         this.parentModuleType = ModuleType.EAR;
      }

      ConfigurationSupportBean configSupport = customModuleBean.getConfigurationSupport();
      if (configSupport == null) {
         CustomModuleLogger.logNoConfigSupport(this.appModuleName, this.descriptorUri);
      } else {
         if (!this.descriptorUri.equals(configSupport.getBaseUri())) {
            CustomModuleLogger.logConfigSupportUriMismatch(this.appModuleName, this.descriptorUri, configSupport.getBaseUri());
         }

         if (descriptorNamespace != null) {
            this.descriptorNamespace = descriptorNamespace;
         } else {
            this.descriptorNamespace = configSupport.getBaseNamespace();
         }
      }

      this.ignoreMissingDescriptors = false;
   }

   public DefaultModule(String descriptorUri) {
      this((String)null, (String)null, ModuleType.EAR, descriptorUri);
   }

   public DefaultModule(String parentModuleUri, String parentModuleId, ModuleType parentModuleType, String descriptorUri) {
      this(parentModuleUri, parentModuleId, descriptorUri, (String)null, (ConfigModuleCallbackHandler)null, false, true);
      this.descriptorNamespace = null;
      this.parentModuleType = parentModuleType;
      this.ignoreMissingDescriptors = true;
   }

   private DefaultModule(String parentModuleUri, String parentModuleId, String descriptorUri, String bindingJarUri, ConfigModuleCallbackHandler callbackHandler, boolean failOnNonDynamicChanges, boolean mergeLibraryDescriptors) {
      this.descriptorParsed = false;
      this.useBindingCache = true;
      this.parentModuleUri = parentModuleUri;
      this.parentModuleId = parentModuleId;
      this.descriptorUri = descriptorUri;
      this.failOnNonDynamicChanges = failOnNonDynamicChanges;
      this.mergeLibraryDescriptors = mergeLibraryDescriptors;
      this.callbackHandler = callbackHandler;
      this.appModuleName = ApplicationAccess.getApplicationAccess().getCurrentApplicationName();
      if (parentModuleUri != null) {
         if (this.appModuleName == null) {
            this.appModuleName = parentModuleUri;
         } else {
            this.appModuleName = this.appModuleName + "/" + parentModuleUri;
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + descriptorUri);
      }

      this.bindingJarUri = bindingJarUri;
   }

   public String getId() {
      return this.descriptorUri;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_CONFIG;
   }

   public DescriptorBean[] getDescriptors() {
      if (!this.descriptorParsed) {
         try {
            this.descriptorBean = this.parseDescriptorBean();
         } catch (ModuleException var2) {
         }
      }

      return this.descriptorBean == null ? new DescriptorBean[0] : new DescriptorBean[]{this.descriptorBean};
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[0];
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.applicationContext = (ApplicationContextInternal)appCtx;
      this.appModuleName = this.applicationContext.getApplicationId();
      if (this.parentModuleUri != null) {
         this.appModuleName = this.appModuleName + "/" + this.parentModuleUri;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

      reg.addUpdateListener(this);
      this.configDescManager = new ConfigDescriptorManager(this.appModuleName);
      this.configDescManager.initBindingInfo(parent, this.bindingJarUri, this.useBindingCache);
      return parent;
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

      this.init(appCtx, gcl, reg);
   }

   public void prepare() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

      CustomModuleLogger.logPrepareDeploy(this.appModuleName, this.descriptorUri);
      this.descriptorBean = this.parseDescriptorBean();
      this.descriptorParsed = true;
      if (this.callbackHandler != null) {
         this.callbackHandler.prepare(this.descriptorUri, this.descriptorNamespace, this.descriptorBean);
      }

   }

   public void activate() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

      if (this.callbackHandler != null) {
         this.callbackHandler.activate(this.descriptorUri, this.descriptorNamespace, this.descriptorBean);
      }

   }

   public void start() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

   }

   public void deactivate() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

      if (this.callbackHandler != null) {
         this.callbackHandler.deactivate(this.descriptorUri, this.descriptorNamespace, this.descriptorBean);
      }

   }

   public void unprepare() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

      if (this.callbackHandler != null) {
         this.callbackHandler.unprepare(this.descriptorUri, this.descriptorNamespace, this.descriptorBean);
      }

      this.descriptorBean = null;
   }

   public void destroy(UpdateListener.Registration reg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

      reg.removeUpdateListener(this);
      this.applicationContext = null;
      this.descriptorBean = null;
      this.configDescManager.destroy();
   }

   public boolean acceptURI(String updateUri) {
      return this.parentModuleType == ModuleType.WAR ? updateUri.equals(this.parentModuleUri + "/" + this.descriptorUri) : updateUri.equals(this.descriptorUri);
   }

   public void prepareUpdate(String uri) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(uri);
      }

      CustomModuleLogger.logPrepareUpdate(this.appModuleName, uri);
      DescriptorBean proposedBean = this.parseDescriptorBean();
      this.descriptorParsed = true;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("prepareUpdate: " + proposedBean);
      }

      try {
         this.descriptorBean.getDescriptor().prepareUpdate(proposedBean.getDescriptor(), this.failOnNonDynamicChanges);
      } catch (DescriptorUpdateRejectedException var4) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(var4.toString());
         }

         throw new ModuleException("Prepare failed for update to " + this.descriptorUri + " for app module " + this.appModuleName, var4);
      }
   }

   public void activateUpdate(String uri) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(uri);
      }

      try {
         this.descriptorBean.getDescriptor().activateUpdate();
      } catch (DescriptorUpdateFailedException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(var3.toString());
         }

         throw new ModuleException("Activate failed for update to " + this.descriptorUri + " for app module " + this.appModuleName, var3);
      }
   }

   public void rollbackUpdate(String uri) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(uri);
      }

      this.descriptorBean.getDescriptor().rollbackUpdate();
   }

   public void remove() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.appModuleName + "/" + this.descriptorUri);
      }

   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
   }

   public void forceProductionToAdmin() {
   }

   public Map getDescriptorMappings() {
      if (!this.mergeLibraryDescriptors) {
         return null;
      } else {
         DescriptorBean[] beans = this.getDescriptors();
         if (beans != null && beans.length != 0) {
            Map m = new HashMap(1);
            m.put(this.descriptorUri, beans[0]);
            return m;
         } else {
            return null;
         }
      }
   }

   public void handleMergedFinder(ClassFinder finder) {
   }

   private DescriptorBean parseDescriptorBean() throws ModuleException {
      try {
         File configDir = null;
         if (this.applicationContext.getAppDeploymentMBean() != null && this.applicationContext.getAppDeploymentMBean().getLocalPlanDir() != null) {
            configDir = new File(this.applicationContext.getAppDeploymentMBean().getLocalPlanDir());
         }

         return this.configDescManager.parseMergedDescriptorBean(this.applicationContext.getApplicationFileManager(), this.applicationContext.getApplicationFileName(), configDir, this.applicationContext.findDeploymentPlan(), this.descriptorUri, this.applicationContext.getLibraryProvider(this.parentModuleId), this.parentModuleType, this.parentModuleUri, this.mergeLibraryDescriptors, this.ignoreMissingDescriptors);
      } catch (IOException var2) {
         throw new ModuleException("Error reading descriptor: " + this.descriptorUri + " for app module " + this.appModuleName, var2);
      } catch (XMLStreamException var3) {
         throw new ModuleException("Error reading descriptor: " + this.descriptorUri + " for app module " + this.appModuleName, var3);
      }
   }
}
