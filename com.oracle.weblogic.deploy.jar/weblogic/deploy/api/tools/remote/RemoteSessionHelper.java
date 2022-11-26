package weblogic.deploy.api.tools.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.WebLogicJ2eeApplicationObject;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.shared.WebLogicModuleTypeUtil;
import weblogic.deploy.api.spi.WebLogicDConfigBeanRoot;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.deploy.api.spi.config.DescriptorSupportManager;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationModuleMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.ModuleBeanInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RemoteSessionHelper extends SessionHelper {
   private WeblogicApplicationBean weblogicApplication = null;
   private ApplicationBean application = null;
   private AppInfo appInfo = null;
   AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private RuntimeAccess runtimeAccess = null;
   private DomainMBean domain = null;
   private WebLogicDeploymentManager dm = null;
   private String plan = null;
   private WLSModelMBeanContext context = null;
   private ModuleType moduleType = null;
   private RemoteSessionHelper orig = null;
   private RemoteSessionHelper saved = null;
   private ArrayList webAppBeans = null;
   private ArrayList ejbJarBeans = null;
   private ArrayList connectorBeans = null;
   private ArrayList garBeans = null;
   private ArrayList moduleBeans = null;

   public RemoteSessionHelper(WebLogicDeploymentManager dm, AppInfo appInfo, String plan, WLSModelMBeanContext context) throws ConfigurationException, IOException, InvalidModuleException, ManagementException {
      super(dm);
      this.dm = dm;
      this.appInfo = appInfo;
      this.plan = plan;
      this.context = context;
      context.setRecurse(true);
      this.runtimeAccess = ManagementService.getRuntimeAccess(this.kernelId);
      this.domain = this.runtimeAccess.getDomain();
      File sourceFile = new File(appInfo.getSourcePath());
      this.setApplication(sourceFile);
      if (plan == null) {
         plan = appInfo.getPlanPath();
      }

      ModuleType planFile;
      if (appInfo.getType() == null) {
         planFile = WebLogicModuleTypeUtil.getFileModuleType(sourceFile);
         if (planFile != null) {
            appInfo.setType(planFile.toString());
         }
      }

      if (appInfo.isEnableScaffolding()) {
         this.enableBeanScaffolding();
      }

      planFile = null;
      if (plan != null) {
         File planFile = new File(plan);
         this.setPlan(planFile);
      }
   }

   void initConfig(RemoteSessionHelper orig) throws ConfigurationException, IOException, InvalidModuleException {
      this.orig = orig;
      this.saved = orig;
      if (this.plan == null) {
         super.initializeConfiguration(new File(this.appInfo.getSourcePath()), (File)null);
      } else {
         super.initializeConfiguration(new File(this.appInfo.getSourcePath()), new File(this.plan));
      }

      this.initDescriptors();
   }

   private WebLogicDDBeanRoot getDDBeanRoot(DeployableObject deployableObject) {
      DeployableObject rootDeployableObject = deployableObject;
      if (deployableObject == null) {
         rootDeployableObject = this.getDeployableObject();
      }

      return (WebLogicDDBeanRoot)((DeployableObject)rootDeployableObject).getDDBeanRoot();
   }

   private WebLogicDConfigBeanRoot getDConfigBeanRoot(DeployableObject deployableObject) throws ConfigurationException {
      WebLogicDDBeanRoot standardDD = this.getDDBeanRoot(deployableObject);
      WebLogicDConfigBeanRoot dcbr = (WebLogicDConfigBeanRoot)this.getConfiguration().getDConfigBeanRoot(standardDD);
      return dcbr;
   }

   public WeblogicApplicationBean getWeblogicApplicationBean() {
      this.setContext((AbstractDescriptorBean)this.weblogicApplication, "WeblogicApplicationDescriptor");
      return this.weblogicApplication;
   }

   public ApplicationBean getApplicationBean() {
      this.setContext((AbstractDescriptorBean)this.application, "ApplicationDescriptor");
      return this.application;
   }

   public List getWebAppBeans() {
      return this.webAppBeans;
   }

   public List getEjbJarBeans() {
      return this.ejbJarBeans;
   }

   public List getGarBeans() {
      return this.garBeans;
   }

   public List getConnectorBeans() {
      return this.connectorBeans;
   }

   public List getModuleBeans() {
      return this.moduleBeans;
   }

   public void savePlan() throws IllegalStateException, ConfigurationException, FileNotFoundException {
      String pendingDir = DomainDir.getPendingDeploymentsDir(this.appInfo.getName());
      File pendingPlanFile = new File(pendingDir + File.separator + "plan.xml");
      this.setPlan(pendingPlanFile);
      super.savePlan();
   }

   public void undoUnsavedChanges() {
      FileInputStream strm = null;

      try {
         if (this.saved != null && this.saved != this.orig) {
            strm = new FileInputStream(this.saved.getPlan());
            this.saved.getConfiguration().restore(strm);
            this.setPlan(this.saved.getPlan());
            this.close();
            super.initializeConfiguration(new File(this.appInfo.getSourcePath()), this.saved.getPlan());
            this.initDescriptors();
         } else {
            if (this.orig.getPlan() != null && this.orig.getPlan().exists() && this.orig.getPlan().length() > 0L) {
               strm = new FileInputStream(this.orig.getPlan());
               this.orig.getConfiguration().restore(strm);
            }

            this.setPlan(this.orig.getPlan());
            this.close();
            super.initializeConfiguration(new File(this.appInfo.getSourcePath()), this.orig.getPlan());
            this.initDescriptors();
         }
      } catch (Exception var10) {
         throw new RuntimeException(var10);
      } finally {
         if (strm != null) {
            try {
               strm.close();
            } catch (Exception var9) {
            }
         }

      }

   }

   public void undoUnactivatedChanges() {
      FileInputStream strm = null;

      try {
         this.saved = this.orig;
         if (this.orig.getPlan() != null && this.orig.getPlan().exists()) {
            strm = new FileInputStream(this.orig.getPlan());
            this.orig.getConfiguration().restore(strm);
         }

         this.setPlan(this.orig.getPlan());
         this.close();
         super.initializeConfiguration(new File(this.appInfo.getSourcePath()), this.orig.getPlan());
         this.initDescriptors();
      } catch (Exception var10) {
         throw new RuntimeException(var10);
      } finally {
         if (strm != null) {
            try {
               strm.close();
            } catch (Exception var9) {
            }
         }

      }

   }

   public void activateChanges() {
      try {
         String planPath = this.appInfo.getPlanPath();
         if (this.appInfo.getDeployedPlanPath() != null) {
            planPath = this.appInfo.getDeployedPlanPath();
         }

         if (planPath != null) {
            this.setPlan(new File(planPath));
            super.savePlan();
         }

         this.saved = null;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   Iterator getChanges() {
      ArrayList combinedDiffs = new ArrayList();
      Iterator it = null;
      DescriptorDiff descDiff = null;
      AbstractDescriptorBean savedStdBean = null;
      AbstractDescriptorBean stdBean = null;
      AbstractDescriptorBean savedConfigBean = null;
      AbstractDescriptorBean configBean = null;

      try {
         if (WebLogicModuleType.EAR.getValue() == this.moduleType.getValue()) {
            this.doDiff((AbstractDescriptorBean)this.saved.getWeblogicApplicationBean(), (AbstractDescriptorBean)this.weblogicApplication, combinedDiffs);
            this.doDiff((AbstractDescriptorBean)this.saved.getApplicationBean(), (AbstractDescriptorBean)this.application, combinedDiffs);
            this.diffModules(this.saved.getModuleBeans(), this.getModuleBeans(), combinedDiffs);
            this.diffModules(this.saved.getWebAppBeans(), this.getWebAppBeans(), combinedDiffs);
            this.diffModules(this.saved.getEjbJarBeans(), this.getEjbJarBeans(), combinedDiffs);
            this.diffModules(this.saved.getConnectorBeans(), this.getConnectorBeans(), combinedDiffs);
            this.diffModules(this.saved.getGarBeans(), this.getGarBeans(), combinedDiffs);
         } else if (WebLogicModuleType.WAR.getValue() == this.moduleType.getValue()) {
            if (this.saved.webAppBeans.size() == 1) {
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.saved.webAppBeans.get(0)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.webAppBeans.get(0)).getConfigDesc(), combinedDiffs);
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.saved.webAppBeans.get(0)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.webAppBeans.get(0)).getStdDesc(), combinedDiffs);
            }
         } else if (WebLogicModuleType.EJB.getValue() == this.moduleType.getValue()) {
            if (this.saved.ejbJarBeans.size() == 1) {
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.saved.ejbJarBeans.get(0)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.ejbJarBeans.get(0)).getConfigDesc(), combinedDiffs);
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.saved.ejbJarBeans.get(0)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.ejbJarBeans.get(0)).getStdDesc(), combinedDiffs);
            }
         } else if (WebLogicModuleType.CAR.getValue() == this.moduleType.getValue()) {
            if (this.saved.connectorBeans.size() == 1) {
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.saved.connectorBeans.get(0)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.connectorBeans.get(0)).getConfigDesc(), combinedDiffs);
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.saved.connectorBeans.get(0)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.connectorBeans.get(0)).getStdDesc(), combinedDiffs);
            }
         } else if (WebLogicModuleType.GAR.getValue() == this.moduleType.getValue() && this.saved.garBeans.size() == 1) {
            this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.saved.garBeans.get(0)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.garBeans.get(0)).getStdDesc(), combinedDiffs);
         }
      } catch (Throwable var9) {
      }

      return combinedDiffs.iterator();
   }

   Iterator getUnactivatedChanges() {
      ArrayList combinedDiffs = new ArrayList();
      Iterator it = null;
      DescriptorDiff descDiff = null;
      AbstractDescriptorBean origStdBean = null;
      AbstractDescriptorBean stdBean = null;
      AbstractDescriptorBean origConfigBean = null;
      AbstractDescriptorBean configBean = null;

      try {
         if (WebLogicModuleType.EAR.getValue() == this.moduleType.getValue()) {
            this.doDiff((AbstractDescriptorBean)this.orig.getWeblogicApplicationBean(), (AbstractDescriptorBean)this.weblogicApplication, combinedDiffs);
            this.doDiff((AbstractDescriptorBean)this.orig.getApplicationBean(), (AbstractDescriptorBean)this.application, combinedDiffs);
            this.diffModules(this.orig.getModuleBeans(), this.getModuleBeans(), combinedDiffs);
            this.diffModules(this.orig.getWebAppBeans(), this.getWebAppBeans(), combinedDiffs);
            this.diffModules(this.orig.getEjbJarBeans(), this.getEjbJarBeans(), combinedDiffs);
            this.diffModules(this.orig.getConnectorBeans(), this.getConnectorBeans(), combinedDiffs);
            this.diffModules(this.orig.getGarBeans(), this.getGarBeans(), combinedDiffs);
         } else if (WebLogicModuleType.WAR.getValue() == this.moduleType.getValue()) {
            if (this.orig.webAppBeans.size() == 1) {
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.orig.webAppBeans.get(0)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.webAppBeans.get(0)).getConfigDesc(), combinedDiffs);
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.orig.webAppBeans.get(0)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.webAppBeans.get(0)).getStdDesc(), combinedDiffs);
            }
         } else if (WebLogicModuleType.EJB.getValue() == this.moduleType.getValue()) {
            if (this.orig.ejbJarBeans.size() == 1) {
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.orig.ejbJarBeans.get(0)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.ejbJarBeans.get(0)).getConfigDesc(), combinedDiffs);
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.orig.ejbJarBeans.get(0)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.ejbJarBeans.get(0)).getStdDesc(), combinedDiffs);
            }
         } else if (WebLogicModuleType.CAR.getValue() == this.moduleType.getValue()) {
            if (this.orig.connectorBeans.size() == 1) {
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.orig.connectorBeans.get(0)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.connectorBeans.get(0)).getConfigDesc(), combinedDiffs);
               this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.orig.connectorBeans.get(0)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.connectorBeans.get(0)).getStdDesc(), combinedDiffs);
            }
         } else if (WebLogicModuleType.GAR.getValue() == this.moduleType.getValue() && this.orig.garBeans.size() == 1) {
            this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)this.orig.garBeans.get(0)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)this.garBeans.get(0)).getConfigDesc(), combinedDiffs);
         }
      } catch (Throwable var9) {
      }

      return combinedDiffs.iterator();
   }

   boolean isModified() {
      try {
         if (WebLogicModuleType.EAR.getValue() == this.moduleType.getValue()) {
            if (((AbstractDescriptorBean)this.weblogicApplication).getDescriptor().isModified()) {
               return true;
            }

            if (((AbstractDescriptorBean)this.application).getDescriptor().isModified()) {
               return true;
            }

            if (this.isModified(this.getModuleBeans())) {
               return true;
            }

            if (this.isModified(this.getWebAppBeans())) {
               return true;
            }

            if (this.isModified(this.getEjbJarBeans())) {
               return true;
            }

            if (this.isModified(this.getConnectorBeans())) {
               return true;
            }

            if (this.isModified(this.getGarBeans())) {
               return true;
            }
         } else if (WebLogicModuleType.WAR.getValue() == this.moduleType.getValue()) {
            if (this.webAppBeans.size() == 1 && this.isModified((ModuleBeanInfo)this.webAppBeans.get(0))) {
               return true;
            }
         } else if (WebLogicModuleType.EJB.getValue() == this.moduleType.getValue()) {
            if (this.ejbJarBeans.size() == 1 && this.isModified((ModuleBeanInfo)this.ejbJarBeans.get(0))) {
               return true;
            }
         } else if (WebLogicModuleType.CAR.getValue() == this.moduleType.getValue()) {
            if (this.connectorBeans.size() == 1 && this.isModified((ModuleBeanInfo)this.connectorBeans.get(0))) {
               return true;
            }
         } else if (WebLogicModuleType.GAR.getValue() == this.moduleType.getValue() && this.garBeans.size() == 1 && this.isModified((ModuleBeanInfo)this.garBeans.get(0))) {
            return true;
         }

         return false;
      } catch (Throwable var2) {
         return false;
      }
   }

   private boolean isModified(List modules) {
      if (modules != null) {
         for(int n = 0; n < modules.size(); ++n) {
            if (this.isModified((ModuleBeanInfo)modules.get(n))) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isModified(ModuleBeanInfo beanInfo) {
      if (beanInfo != null) {
         DescriptorBean descriptorBean = beanInfo.getConfigDesc();
         if (descriptorBean != null && descriptorBean.getDescriptor().isModified()) {
            return true;
         }

         descriptorBean = beanInfo.getStdDesc();
         if (descriptorBean != null && descriptorBean.getDescriptor().isModified()) {
            return true;
         }
      }

      return false;
   }

   void setSaved(RemoteSessionHelper saved) {
      this.saved = saved;
   }

   void setOrig(RemoteSessionHelper orig) {
      this.orig = orig;
   }

   void updateApplication() {
   }

   private boolean hasNonDynamicChange() {
      Iterator it = this.getUnactivatedChanges();
      if (it.hasNext()) {
         BeanUpdateEvent event = (BeanUpdateEvent)it.next();
         BeanUpdateEvent.PropertyUpdate[] var3 = event.getUpdateList();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BeanUpdateEvent.PropertyUpdate update = var3[var5];
            if (!update.isDynamic()) {
               return true;
            }
         }
      }

      return false;
   }

   private void setContext(AbstractDescriptorBean bean, String extension) {
      DescriptorImpl descriptor = (DescriptorImpl)bean.getDescriptor();
      Map ctx = descriptor.getContext();
      if (this.domain.lookupAppDeployment(this.appInfo.getName()) != null) {
         ctx.put("DescriptorConfigExtension", this.domain.lookupAppDeployment(this.appInfo.getName()));
      } else {
         ctx.put("DescriptorConfigExtension", this.domain);
      }

      ctx.put("DescriptorConfigExtensionAttribute", extension);
      ctx.put("ApplicationName", this.appInfo.getName());
   }

   private void setContext(AbstractDescriptorBean bean, String extension, String moduleName, String moduleType) {
      this.setContext(bean, extension);
      DescriptorImpl descriptor = (DescriptorImpl)bean.getDescriptor();
      Map ctx = descriptor.getContext();
      ctx.put("ModuleName", moduleName);
      ctx.put("ModuleType", moduleType);
   }

   private void initDescriptors() throws ConfigurationException, IOException {
      DeployableObject j2eeDeployableObject = this.getDeployableObject();
      this.webAppBeans = new ArrayList();
      this.ejbJarBeans = new ArrayList();
      this.connectorBeans = new ArrayList();
      this.garBeans = new ArrayList();
      this.moduleType = j2eeDeployableObject.getType();
      String mt = this.moduleType.toString();
      WebLogicDDBeanRoot ddRoot;
      WebLogicDConfigBeanRoot configBeanRoot;
      if (ModuleType.EAR.getValue() == this.moduleType.getValue()) {
         WebLogicJ2eeApplicationObject wjao = (WebLogicJ2eeApplicationObject)j2eeDeployableObject;
         ddRoot = this.getDDBeanRoot(j2eeDeployableObject);
         this.application = (ApplicationBean)ddRoot.getDescriptorBean();
         configBeanRoot = this.getDConfigBeanRoot(j2eeDeployableObject);
         this.weblogicApplication = (WeblogicApplicationBean)configBeanRoot.getDescriptorBean();
         WeblogicModuleBean[] modules = this.weblogicApplication.getModules();
         this.moduleBeans = new ArrayList();

         WebLogicDConfigBeanRoot dcb;
         ModuleBeanInfo beanInfo;
         try {
            if (modules != null) {
               for(int n = 0; n < modules.length; ++n) {
                  WebLogicDDBeanRoot ddb = (WebLogicDDBeanRoot)wjao.getDDBeanRoot(modules[n].getPath());
                  dcb = (WebLogicDConfigBeanRoot)configBeanRoot.getDConfigBean(wjao.getDDBeanRoot(modules[n].getPath()));
                  String moduleType = null;
                  if ("JMS".equals(modules[n].getType())) {
                     moduleType = "jms";
                     this.setContext((AbstractDescriptorBean)ddb.getDescriptorBean(), "JMSDescriptor", modules[n].getName(), moduleType);
                  } else if ("JDBC".equals(modules[n].getType())) {
                     moduleType = "jdbc";
                     this.setContext((AbstractDescriptorBean)ddb.getDescriptorBean(), "DatasourceDescriptor", modules[n].getName(), moduleType);
                  } else if ("GAR".equals(modules[n].getType())) {
                     this.addGar(modules[n].getName(), ddb);
                  } else if ("Interception".equals(modules[n].getType())) {
                     moduleType = "interception";
                  } else if ("wldf".equals(modules[n].getType())) {
                     moduleType = "diagnostics";
                  }

                  if (!"GAR".equals(modules[n].getType())) {
                     beanInfo = new ModuleBeanInfo(modules[n].getName(), moduleType, ddb.getDescriptorBean(), dcb.getDescriptorBean());
                     this.moduleBeans.add(beanInfo);
                  }
               }
            }
         } catch (Exception var14) {
            throw new RuntimeException(var14);
         }

         DConfigBean[] secondaryBeans = configBeanRoot.getSecondaryDescriptors();
         if (secondaryBeans != null) {
            for(int n = 0; n < secondaryBeans.length; ++n) {
               dcb = (WebLogicDConfigBeanRoot)secondaryBeans[n];
               AbstractDescriptorBean descBean = (AbstractDescriptorBean)dcb.getDescriptorBean();
               if (descBean instanceof WLDFResourceBean) {
                  this.setContext(descBean, "WLDFDescriptor", "diagnostics", "diagnostics");
                  beanInfo = new ModuleBeanInfo("diagnostics", "diagnostics", (DescriptorBean)null, descBean);
                  this.moduleBeans.add(beanInfo);
               }
            }
         }

         DeployableObject[] subModules = wjao.getDeployableObjects();
         String[] moduleUris = wjao.getModuleUris();

         for(int l = 0; l < subModules.length; ++l) {
            WebLogicDDBeanRoot ddRoot = this.getDDBeanRoot(subModules[l]);
            this.getDConfigBeanRoot(subModules[l]);
            ModuleType type = ddRoot.getType();
            if (ModuleType.WAR.getValue() == type.getValue()) {
               this.getWebApps(moduleUris[l], subModules[l]);
            } else if (ModuleType.EJB.getValue() == type.getValue()) {
               this.getEjbJars(moduleUris[l], subModules[l]);
            } else if (ModuleType.RAR.getValue() == type.getValue()) {
               this.getConnectors(moduleUris[l], subModules[l]);
            }
         }
      } else if (ModuleType.WAR.getValue() == this.moduleType.getValue()) {
         this.getWebApps(this.appInfo.getName(), j2eeDeployableObject);
      } else if (ModuleType.EJB.getValue() == this.moduleType.getValue()) {
         this.getEjbJars(this.appInfo.getName(), j2eeDeployableObject);
      } else if (ModuleType.RAR.getValue() == this.moduleType.getValue()) {
         this.getConnectors(this.appInfo.getName(), j2eeDeployableObject);
      } else if ("gar".equals(mt)) {
         this.addGar(this.appInfo.getName(), this.getDDBeanRoot(j2eeDeployableObject));
      } else {
         String moduleType;
         ModuleBeanInfo beanInfo;
         if ("jdbc".equals(mt)) {
            moduleType = "jdbc";
            ddRoot = this.getDDBeanRoot(j2eeDeployableObject);
            configBeanRoot = this.getDConfigBeanRoot(j2eeDeployableObject);
            this.setContext((AbstractDescriptorBean)ddRoot.getDescriptorBean(), "DatasourceDescriptor", this.appInfo.getName(), moduleType);
            beanInfo = new ModuleBeanInfo(this.appInfo.getName(), moduleType, ddRoot.getDescriptorBean(), configBeanRoot == null ? null : configBeanRoot.getDescriptorBean());
            this.moduleBeans = new ArrayList();
            this.moduleBeans.add(beanInfo);
         } else if ("jms".equals(mt)) {
            moduleType = "jms";
            ddRoot = this.getDDBeanRoot(j2eeDeployableObject);
            configBeanRoot = this.getDConfigBeanRoot(j2eeDeployableObject);
            this.setContext((AbstractDescriptorBean)ddRoot.getDescriptorBean(), "JMSDescriptor", this.appInfo.getName(), moduleType);
            beanInfo = new ModuleBeanInfo(this.appInfo.getName(), moduleType, ddRoot.getDescriptorBean(), configBeanRoot == null ? null : configBeanRoot.getDescriptorBean());
            this.moduleBeans = new ArrayList();
            this.moduleBeans.add(beanInfo);
         }
      }

   }

   private ModuleBeanInfo getPersistence(WebLogicDeployableObject dobj, String uri) {
      try {
         if (dobj.hasDDBean(uri)) {
            WebLogicDDBeanRoot ddRoot = (WebLogicDDBeanRoot)dobj.getDDBeanRoot(uri);
            WebLogicDConfigBeanRoot wlsDDRoot = this.getDConfigBeanRoot(dobj);
            DConfigBean configDesc = null;
            WebLogicDConfigBeanRoot configRoot = null;
            DescriptorSupport[] descSupts = DescriptorSupportManager.getForBaseURI(uri);
            if (descSupts != null && descSupts.length > 0 && descSupts[0] != null) {
               configDesc = wlsDDRoot.getDConfigBean(ddRoot, descSupts[0]);
               if (configDesc instanceof WebLogicDConfigBeanRoot) {
                  configRoot = (WebLogicDConfigBeanRoot)configDesc;
               }
            }

            ModuleBeanInfo module = new ModuleBeanInfo(dobj.getArchive().getPath(), "persistence", ddRoot.getDescriptorBean(), configRoot == null ? null : configRoot.getDescriptorBean());
            this.setContext((AbstractDescriptorBean)module.getStdDesc(), "PersistencesDescriptor", module.getName(), module.getType());
            if (module.getConfigDesc() != null) {
               this.setContext((AbstractDescriptorBean)module.getConfigDesc(), "WeblogicPersistencesDescriptor", module.getName(), module.getType());
            }

            return module;
         } else {
            return null;
         }
      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   private ModuleBeanInfo getWebServices(WebLogicDeployableObject dobj, String uri) {
      try {
         if (dobj.hasDDBean(uri)) {
            WebLogicDDBeanRoot ddRoot = (WebLogicDDBeanRoot)dobj.getDDBeanRoot(uri);
            WebLogicDConfigBeanRoot wlsDDRoot = this.getDConfigBeanRoot(dobj);
            DConfigBean configDesc = null;
            WebLogicDConfigBeanRoot configRoot = null;
            DescriptorSupport[] descSupts = DescriptorSupportManager.getForBaseURI(uri);
            if (descSupts != null && descSupts[0] != null) {
               configDesc = wlsDDRoot.getDConfigBean(ddRoot, descSupts[0]);
               if (configDesc instanceof WebLogicDConfigBeanRoot) {
                  configRoot = (WebLogicDConfigBeanRoot)configDesc;
               }
            }

            ModuleBeanInfo module = new ModuleBeanInfo(dobj.getArchive().getPath(), "webservice", ddRoot.getDescriptorBean(), configRoot == null ? null : configRoot.getDescriptorBean());
            this.setContext((AbstractDescriptorBean)module.getStdDesc(), "WebservicesDescriptor", module.getName(), module.getType());
            this.setContext((AbstractDescriptorBean)module.getConfigDesc(), "WeblogicWebservicesDescriptor", module.getName(), module.getType());
            return module;
         } else {
            return null;
         }
      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   private void getWebApps(String name, DeployableObject deployableObject) throws ConfigurationException, IOException {
      WebLogicDDBeanRoot ddRoot = this.getDDBeanRoot(deployableObject);
      WebLogicDConfigBeanRoot configBeanRoot = this.getDConfigBeanRoot(deployableObject);
      this.setContext((AbstractDescriptorBean)ddRoot.getDescriptorBean(), "WebAppDescriptor", name, ModuleType.WAR.toString());
      this.setContext((AbstractDescriptorBean)configBeanRoot.getDescriptorBean(), "WeblogicWebAppDescriptor", name, ModuleType.WAR.toString());
      ModuleBeanInfo beanInfo = new ModuleBeanInfo(name, AppDeploymentConfigurationModuleMBean.WEB_APP_TYPE, ddRoot.getDescriptorBean(), configBeanRoot.getDescriptorBean());
      this.webAppBeans.add(beanInfo);
      this.addWebServices(beanInfo, (WebLogicDeployableObject)deployableObject);
      this.addPersistence(beanInfo, (WebLogicDeployableObject)deployableObject);
   }

   private void getEjbJars(String name, DeployableObject deployableObject) throws ConfigurationException, IOException {
      WebLogicDDBeanRoot ddRoot = this.getDDBeanRoot(deployableObject);
      WebLogicDConfigBeanRoot configBeanRoot = this.getDConfigBeanRoot(deployableObject);
      this.setContext((AbstractDescriptorBean)ddRoot.getDescriptorBean(), "EJBDescriptor", name, ModuleType.EJB.toString());
      this.setContext((AbstractDescriptorBean)configBeanRoot.getDescriptorBean(), "WeblogicEJBDescriptor", name, ModuleType.EJB.toString());
      ModuleBeanInfo beanInfo = new ModuleBeanInfo(name, AppDeploymentConfigurationModuleMBean.EJB_TYPE, ddRoot.getDescriptorBean(), configBeanRoot.getDescriptorBean());
      this.ejbJarBeans.add(beanInfo);
      this.addWebServices(beanInfo, (WebLogicDeployableObject)deployableObject);
      this.addPersistence(beanInfo, (WebLogicDeployableObject)deployableObject);
   }

   private void addGar(String name, WebLogicDDBeanRoot ddb) throws ConfigurationException, IOException {
      this.setContext((AbstractDescriptorBean)ddb.getDescriptorBean(), "GarDescriptor", name, "gar");
      ModuleBeanInfo beanInfo = new ModuleBeanInfo(name, "gar", ddb.getDescriptorBean(), (DescriptorBean)null);
      this.garBeans.add(beanInfo);
   }

   private void addPersistence(ModuleBeanInfo beanInfo, WebLogicDeployableObject deployableObject) {
      ModuleBeanInfo persistenceInfo = this.getPersistence(deployableObject, "META-INF/persistence.xml");
      if (persistenceInfo != null) {
         beanInfo.addModule(persistenceInfo);
      }

      persistenceInfo = this.getPersistence(deployableObject, "WEB-INF/classes/META-INF/persistence.xml");
      if (persistenceInfo != null) {
         beanInfo.addModule(persistenceInfo);
      }

   }

   private void addWebServices(ModuleBeanInfo beanInfo, WebLogicDeployableObject deployableObject) {
      ModuleBeanInfo webserviceInfo = this.getWebServices(deployableObject, "WEB-INF/webservices.xml");
      if (webserviceInfo != null) {
         beanInfo.addModule(webserviceInfo);
      }

      webserviceInfo = this.getWebServices(deployableObject, "WEB-INF/web-services.xml");
      if (webserviceInfo != null) {
         beanInfo.addModule(webserviceInfo);
      }

      webserviceInfo = this.getWebServices(deployableObject, "META-INF/webservices.xml");
      if (webserviceInfo != null) {
         beanInfo.addModule(webserviceInfo);
      }

      webserviceInfo = this.getWebServices(deployableObject, "META-INF/web-services.xml");
      if (webserviceInfo != null) {
         beanInfo.addModule(webserviceInfo);
      }

   }

   private void getConnectors(String name, DeployableObject deployableObject) throws ConfigurationException, IOException {
      WebLogicDDBeanRoot ddRoot = this.getDDBeanRoot(deployableObject);
      WebLogicDConfigBeanRoot configBeanRoot = this.getDConfigBeanRoot(deployableObject);
      this.setContext((AbstractDescriptorBean)ddRoot.getDescriptorBean(), "ConnectorDescriptor", name, ModuleType.RAR.toString());
      this.setContext((AbstractDescriptorBean)configBeanRoot.getDescriptorBean(), "WeblogicConnectorDescriptor", name, ModuleType.RAR.toString());
      ModuleBeanInfo beanInfo = new ModuleBeanInfo(name, AppDeploymentConfigurationModuleMBean.CONNECTOR_TYPE, ddRoot.getDescriptorBean(), configBeanRoot.getDescriptorBean());
      this.connectorBeans.add(beanInfo);
   }

   private void doDiff(AbstractDescriptorBean origBean, AbstractDescriptorBean bean, ArrayList combinedDiffs) {
      DescriptorDiff descDiff = null;
      Iterator it = null;
      if (origBean != null && bean != null) {
         descDiff = origBean.getDescriptor().computeDiff(bean.getDescriptor());
         if (descDiff != null) {
            it = descDiff.iterator();
            if (it != null) {
               while(it.hasNext()) {
                  combinedDiffs.add(it.next());
               }
            }
         }
      }

   }

   private void diffModules(List origModules, List modules, ArrayList combinedDiffs) {
      if (origModules != null && modules != null) {
         for(int n = 0; n < modules.size(); ++n) {
            this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)origModules.get(n)).getConfigDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)modules.get(n)).getConfigDesc(), combinedDiffs);
            this.doDiff((AbstractDescriptorBean)((ModuleBeanInfo)origModules.get(n)).getStdDesc(), (AbstractDescriptorBean)((ModuleBeanInfo)modules.get(n)).getStdDesc(), combinedDiffs);
         }
      }

   }

   public static String getPendingPlanPath(String appName) {
      String pendingDir = DomainDir.getPendingDeploymentsDir(appName);
      File pendingPlanFile = new File(pendingDir + File.separator + "plan.xml");
      return pendingPlanFile.exists() ? pendingPlanFile.getAbsolutePath() : null;
   }

   private RemoteSessionHelper(WebLogicDeploymentManager dm, AppInfo appInfo, String plan) throws Exception {
      super(dm);
      this.dm = dm;
      this.appInfo = appInfo;
      this.plan = plan;
   }

   public static void main(String[] args) throws Exception {
      System.out.println("RemoteSessionHelper");
      if (args.length == 8) {
         if (args[3].length() == 0) {
            args[3] = null;
         }

         boolean enableScaffolding = false;
         if ("true".equals(args[5])) {
            enableScaffolding = true;
         }

         AppInfo info = new AppInfo(args[0], args[1], args[2], args[3], args[4], enableScaffolding, args[6], args[7]);
         RemoteSessionHelper helper = new RemoteSessionHelper(getDisconnectedDeploymentManager(), info, args[2]);
         helper.initConfig((RemoteSessionHelper)null);
         helper.printModuleBeans(helper.getModuleBeans());
         List modBeans = helper.getWebAppBeans();
         System.out.println("webapp beans:  " + modBeans);
         int n;
         if (modBeans != null) {
            System.out.println("webapp beans size:  " + modBeans.size());

            for(n = 0; n < modBeans.size(); ++n) {
               System.out.println("webapp bean:  " + modBeans.get(n));
               helper.printModuleBeans(((ModuleBeanInfo)modBeans.get(n)).getModules());
            }
         }

         modBeans = helper.getConnectorBeans();
         System.out.println("connector beans:  " + modBeans);
         if (modBeans != null) {
            System.out.println("connector beans size:  " + modBeans.size());

            for(n = 0; n < modBeans.size(); ++n) {
               System.out.println("connector bean:  " + modBeans.get(n));
               helper.printModuleBean((ModuleBeanInfo)modBeans.get(n));
            }
         }
      }

   }

   private void printModuleBeans(List mbeans) {
      System.out.println("module beans:  " + mbeans);
      if (mbeans != null) {
         System.out.println("module beans size:  " + mbeans.size());

         for(int n = 0; n < mbeans.size(); ++n) {
            this.printModuleBean((ModuleBeanInfo)mbeans.get(n));
         }
      }

   }

   private void printModuleBean(ModuleBeanInfo mbean) {
      if (mbean != null) {
         System.out.println("module bean:  " + mbean);
         System.out.println("name:  " + mbean.getName());
         System.out.println("type:  " + mbean.getType());
         System.out.println("stdDesc:  " + mbean.getStdDesc());
         System.out.println("configDesc:  " + mbean.getConfigDesc());
      }

   }
}
