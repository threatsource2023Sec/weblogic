package weblogic.metadata.management;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.internal.DeploymentPlanDescriptorLoader;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.WebBean;
import weblogic.j2ee.descriptor.wl.AnnotationOverridesBean;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.ModuleProviderBean;
import weblogic.logging.NonCatalogLogger;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class AnnotationOverridesModule implements Module, UpdateListener {
   private static final String ROOT_ELEMENT_NAME = "annotation-overrides";
   private static final String PLAN_UPDATE_URI = "META-INF/annotation-overrides.xml";
   private static final String PLAN_DIR_NAME = "plan";
   private static final String PLAN_FILENAME = "Plan.xml";
   private ModuleProviderBean _provider;
   private GenericClassLoader _classloader;
   private Map _descriptorMap = new HashMap();
   private String _appName;
   private CustomModuleBean _customModuleBean;
   private DescriptorManager _descriptorManager;
   private ApplicationContextInternal _applicationContext;
   private static NonCatalogLogger _logger = new NonCatalogLogger("AnnotationOverridesModule");
   private long _overrideVersion = 0L;
   private Map _pendingUpdateDescriptors;
   private Map _webUriMapping;

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[0];
   }

   public void adminToProduction() {
   }

   public void forceProductionToAdmin() throws ModuleException {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier arg0) throws ModuleException {
   }

   public AnnotationOverridesModule(ModuleProviderBean provider, CustomModuleBean bean) {
      this._provider = provider;
      this._customModuleBean = bean;
      this._descriptorManager = new DescriptorManager();
      this._pendingUpdateDescriptors = new HashMap();
   }

   public String getId() {
      return this._customModuleBean.getUri();
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_CONFIG;
   }

   public DescriptorBean[] getDescriptors() {
      return new DescriptorBean[0];
   }

   private String getLogPrefix() {
      return "[ _appName: " + this._appName + " ][ uri: " + this._customModuleBean.getUri() + " ] ";
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this._applicationContext = (ApplicationContextInternal)appCtx;
      this._appName = this._applicationContext.getApplicationId();
      reg.addUpdateListener(this);
      this._classloader = parent;
      this._webUriMapping = new HashMap();
      ApplicationBean app = this._applicationContext.getApplicationDD();
      if (app != null) {
         ModuleBean[] var5 = app.getModules();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ModuleBean moduleBean = var5[var7];
            WebBean webBean = moduleBean.getWeb();
            if (webBean != null) {
               String ctxRoot = webBean.getContextRoot();
               String uri = webBean.getWebUri();
               this._webUriMapping.put(uri, ctxRoot);
            }
         }
      }

      return parent;
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.init(appCtx, gcl, reg);
   }

   private Map getAnnotationOverrideDescriptors() throws ModuleException {
      Map overrideDescriptors = new HashMap();

      try {
         DeploymentPlanBean deploymentPlanBean = this.getDeploymentPlanBean();
         if (deploymentPlanBean == null) {
            _logger.debug(this.getLogPrefix() + ":No Deployment Plan");
         } else {
            String configRoot = deploymentPlanBean.getConfigRoot();
            _logger.info("ConfigRoot is " + configRoot);
            StringBuilder builder = new StringBuilder();
            ModuleOverrideBean[] moduleOverrides = deploymentPlanBean.getModuleOverrides();

            for(int i = 0; i < moduleOverrides.length; ++i) {
               ModuleOverrideBean moduleOverride = moduleOverrides[i];
               String moduleName = moduleOverride.getModuleName();
               String moduleType = moduleOverride.getModuleType();
               boolean isRoot = deploymentPlanBean.rootModule(moduleName);
               ModuleDescriptorBean[] moduleDescriptors = moduleOverride.getModuleDescriptors();

               for(int j = 0; j < moduleDescriptors.length; ++j) {
                  ModuleDescriptorBean moduleDescriptor = moduleDescriptors[j];
                  String descriptorUri = moduleDescriptor.getUri();
                  String rootElement = moduleDescriptor.getRootElement();
                  if (rootElement.equals("annotation-overrides")) {
                     String pathToDescriptor = null;
                     String moduleUri = moduleName;
                     builder.delete(0, builder.length());
                     builder.append(configRoot);
                     if (isRoot) {
                        moduleUri = "__WLS ROOT MODULE URI__";
                        builder.append(File.separatorChar);
                        builder.append(descriptorUri);
                     } else {
                        builder.append(File.separatorChar);
                        builder.append(moduleName);
                        builder.append(File.separatorChar);
                        builder.append(descriptorUri);
                     }

                     pathToDescriptor = builder.toString();
                     FileInputStream fis = null;

                     try {
                        File descriptorFile = new File(pathToDescriptor);
                        if (descriptorFile.exists()) {
                           if (!descriptorFile.canRead()) {
                              _logger.debug(this.getLogPrefix() + ": unable to load anootation override for " + moduleUri + " due to file permission");
                           } else {
                              fis = new FileInputStream(descriptorFile);
                              DescriptorBean overrides = this._descriptorManager.createDescriptor(fis).getRootBean();
                              overrideDescriptors.put(moduleUri, overrides);
                              if (this._webUriMapping.containsKey(moduleUri)) {
                                 String alternativeUri = (String)this._webUriMapping.get(moduleUri);
                                 overrideDescriptors.put(alternativeUri, overrides);
                              }
                           }
                        }
                     } catch (Exception var26) {
                        throw new ModuleException(var26);
                     } finally {
                        if (fis != null) {
                           fis.close();
                        }

                     }
                  }
               }
            }
         }

         return overrideDescriptors;
      } catch (Exception var28) {
         throw new ModuleException(var28);
      }
   }

   public void prepare() throws ModuleException {
      _logger.info(this.getLogPrefix() + ":prepare()");
      this._descriptorMap = this.getAnnotationOverrideDescriptors();
   }

   private DeploymentPlanBean getDeploymentPlanBean() throws FileNotFoundException, IOException, XMLStreamException {
      AppDeploymentMBean deployable = this._applicationContext.getAppDeploymentMBean();

      assert deployable != null;

      DeploymentPlanBean bean = deployable.getDeploymentPlanDescriptor();
      if (bean == null) {
         String configDir = this.getConfigDir(deployable);
         if (configDir == null) {
            return null;
         }

         File planFile = new File(configDir + File.separator + "Plan.xml");
         if (planFile.exists() && planFile.canRead()) {
            bean = (new DeploymentPlanDescriptorLoader(planFile)).getDeploymentPlanBean();
            bean.setConfigRoot(configDir);
         }
      }

      return bean;
   }

   private String getConfigDir(AppDeploymentMBean deployable) {
      String configDir = null;
      String srcPath = deployable.getSourcePath();
      File applicationRootFile = new File(srcPath);
      boolean isExploded = applicationRootFile.isDirectory();
      File parent;
      if (isExploded) {
         parent = applicationRootFile.getParentFile();
         if (parent != null) {
            configDir = parent.getAbsolutePath();
         }
      } else {
         parent = applicationRootFile.getParentFile();
         if (parent != null) {
            File p = parent.getParentFile();
            if (p != null) {
               configDir = p.getAbsolutePath();
            }
         }
      }

      return configDir != null ? configDir + File.separator + "plan" : null;
   }

   public void activate() throws ModuleException {
      _logger.info(this.getLogPrefix() + ":activate()");
      Map parmMap = this._applicationContext.getApplicationParameters();
      if (parmMap == Collections.EMPTY_MAP || parmMap == null) {
         this._applicationContext.setApplicationParameters(new HashMap());
      }

      this._applicationContext.getApplicationParameters().put("weblogic.metadata.management.AnnotationOverrideDescriptors", this._descriptorMap);
      this._applicationContext.getApplicationParameters().put("weblogic.metadata.management.AnnotationOverrideDescriptorsVersionID", this._overrideVersion);
   }

   public boolean acceptURI(String uri) {
      return uri != null && uri.endsWith("META-INF/annotation-overrides.xml");
   }

   public void prepareUpdate(String uri) throws ModuleException {
      _logger.info(this.getLogPrefix() + ":prepareUpdate()");
      List changeList = new LinkedList();
      this._pendingUpdateDescriptors.put(uri, changeList);
      String moduleName = getModuleNameFromURI(uri);
      if (moduleName != null && moduleName.length() > 0) {
         Map proposed = this.getAnnotationOverrideDescriptors();
         DescriptorBean proposedBean = (DescriptorBean)proposed.get(moduleName);
         DescriptorBean currentBean = (DescriptorBean)this._descriptorMap.get(moduleName);
         if (proposedBean != null && currentBean != null) {
            try {
               AnnotationOverridesBean proposedOverrides = (AnnotationOverridesBean)proposedBean;
               AnnotationOverridesBean currentOverrides = (AnnotationOverridesBean)currentBean;
               DescriptorDiff diffs = currentBean.getDescriptor().computeDiff(proposedBean.getDescriptor());
               if (diffs.size() > 0) {
                  _logger.info(this.getLogPrefix() + ":[moduleName:" + moduleName + "] had diffs, # of diffs = " + diffs.size());
                  proposedOverrides.setUpdateCount(currentOverrides.getUpdateCount() + 1L);
                  currentBean.getDescriptor().prepareUpdate(proposedBean.getDescriptor());
                  changeList.add(currentBean);
               }

            } catch (DescriptorUpdateRejectedException var10) {
               _logger.error(this.getLogPrefix() + ":prepareUpdate()", var10);
               this._pendingUpdateDescriptors.remove(uri);
               throw new ModuleException("Failed on module:" + moduleName, var10);
            }
         }
      } else {
         throw new ModuleException(this.getLogPrefix() + " Unexpected uri format: " + uri);
      }
   }

   public void activateUpdate(String uri) throws ModuleException {
      _logger.info(this.getLogPrefix() + ":activateUpdate()");
      boolean updateFailed = false;
      List changeList = (List)this._pendingUpdateDescriptors.remove(uri);
      Iterator var4 = changeList.iterator();

      while(var4.hasNext()) {
         DescriptorBean bean = (DescriptorBean)var4.next();

         try {
            bean.getDescriptor().activateUpdate();
         } catch (DescriptorUpdateFailedException var7) {
            _logger.error(this.getLogPrefix() + ":activateUpdate()" + var7);
            updateFailed = true;
         }
      }

      if (!updateFailed) {
         this._applicationContext.getApplicationParameters().put("weblogic.metadata.management.AnnotationOverrideDescriptorsVersionID", ++this._overrideVersion);
      }

   }

   public void start() throws ModuleException {
   }

   public void deactivate() throws ModuleException {
   }

   public void unprepare() throws ModuleException {
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      reg.removeUpdateListener(this);
   }

   public void rollbackUpdate(String uri) {
   }

   public void remove() throws ModuleException {
   }

   private static String getModuleNameFromURI(String uri) {
      if (uri != null && uri.length() > 0) {
         if (uri.equals("META-INF/annotation-overrides.xml")) {
            return "__WLS ROOT MODULE URI__";
         }

         int index = uri.indexOf("META-INF/annotation-overrides.xml");
         if (index > 0) {
            return uri.substring(0, index - 1);
         }
      }

      return null;
   }

   public static void main(String[] args) throws Exception {
      DescriptorManager descriptorManager = new DescriptorManager();
      FileInputStream fis = null;
      FileOutputStream fos = null;

      try {
         fis = new FileInputStream("D:/Development/oamApp/plan/OAMPgFlowWeb/META-INF/annotation-overrides.xml");
         Descriptor desc = descriptorManager.createDescriptor(fis);
         AnnotationOverridesBean overrides = (AnnotationOverridesBean)desc.getRootBean();
         fos = new FileOutputStream("D:/temp/anno-ovr.xml");
         descriptorManager.writeDescriptorAsXML(desc, new BufferedOutputStream(fos));
      } catch (Exception var9) {
         throw var9;
      } finally {
         if (fis != null) {
            fis.close();
         }

         if (fos != null) {
            fos.flush();
         }

      }

   }
}
