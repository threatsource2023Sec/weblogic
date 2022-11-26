package weblogic.management.mbeanservers.edit.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationMBean;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationModuleMBean;
import weblogic.management.mbeanservers.edit.DescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.internal.ModuleBeanInfo;
import weblogic.management.runtime.SessionHelperRuntimeMBean;

public class AppDeploymentConfigurationMBeanImpl extends ServiceImpl implements AppDeploymentConfigurationMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private static String planName = "plan.xml";
   public String objectName = null;
   private DomainAccess domainAccess;
   private AppInfo appInfo;
   private DomainMBean domain;
   private WLSModelMBeanContext context;
   private WeblogicApplicationBean weblogicApplication;
   private ApplicationBean application;
   private ApplicationDescriptorMBeanImpl applicationDescriptor = null;
   private DescriptorMBean[] descriptors = null;
   private SessionHelperRuntimeMBean helper = null;
   private List connectorBeans = null;
   private List ejbJarBeans = null;
   private List webAppBeans = null;
   private List garBeans = null;
   private List moduleBeans = null;
   private List modules = null;
   private List registeredBeans = new ArrayList();

   AppDeploymentConfigurationMBeanImpl(DomainAccess domainAccess, AppInfo appInfo, DomainMBean domain, WLSModelMBeanContext context, SessionHelperRuntimeMBean helper) {
      super("AppDeploymentConfiguration", AppDeploymentConfigurationMBean.class.getName(), (Service)null, (String)null);
      this.objectName = "com.bea:Name=" + appInfo.getName() + ",Type=" + AppDeploymentConfigurationMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

      this.domainAccess = domainAccess;
      this.appInfo = appInfo;
      this.domain = domain;
      this.context = context;
      this.helper = helper;
      this.registerBeans();
   }

   public String getName() {
      return this.appInfo.getName();
   }

   public String getType() {
      return this.appInfo.getType();
   }

   public DescriptorMBean[] getDescriptors() {
      return this.descriptors;
   }

   public AppDeploymentConfigurationModuleMBean[] getModules() {
      return (AppDeploymentConfigurationModuleMBean[])this.modules.toArray(new AppDeploymentConfigurationModuleMBean[0]);
   }

   public void saveChanges() {
      this.helper.savePlan();
   }

   public void activateChanges() throws IOException {
      this.helper.activateChanges();
   }

   public void undoUnsavedChanges() {
      try {
         this.helper.undoUnsavedChanges();
         this.reregisterBeans();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public void undoUnactivatedChanges() {
      try {
         this.helper.undoUnactivatedChanges();
         this.reregisterBeans();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public static void copyFile(File from, File to) throws IOException {
      byte[] buf = new byte[1024];
      FileInputStream fis = new FileInputStream(from);
      BufferedInputStream bis = new BufferedInputStream(fis);
      FileOutputStream fos = new FileOutputStream(to);
      BufferedOutputStream bos = new BufferedOutputStream(fos);

      int r;
      while((r = bis.read(buf)) > 0) {
         bos.write(buf, 0, r);
      }

      bis.close();
      bos.close();
   }

   Iterator getChanges() {
      return this.helper.getChanges();
   }

   Iterator getUnactivatedChanges() {
      return this.helper.getUnactivatedChanges();
   }

   boolean isModified() {
      return this.helper.isModified();
   }

   void updateApplication() {
      try {
         String planPath = this.appInfo.getPlanPath();
         File pendingDir = new File(DomainDir.getPendingDeploymentsDir(this.appInfo.getName()));
         if (pendingDir.exists() && planPath != null) {
            copyFile(new File(DomainDir.getPendingDeploymentsDir(this.appInfo.getName()) + File.separator + planName), new File(planPath));
         }
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }

      this.helper.updateApplication();
   }

   private void reregisterBeans() {
      try {
         this.unregisterBeans();
         this.registerBeans();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   private void registerBeans() {
      try {
         if (this.modules == null) {
            this.modules = new ArrayList();
         }

         if (ModuleType.EAR.toString().equals(this.getType())) {
            this.weblogicApplication = this.helper.getWeblogicApplicationBean();
            this.registerBean(this.weblogicApplication);
            this.application = this.helper.getApplicationBean();
            this.registerBean(this.application);
            this.applicationDescriptor = new ApplicationDescriptorMBeanImpl(this.appInfo, this.domain, (Service)null, (String)null, this.weblogicApplication, this.application);
            this.registerBean(this.applicationDescriptor, new ObjectName(this.applicationDescriptor.objectName));
            this.moduleBeans = this.helper.getModuleBeans();
            if (this.descriptors == null) {
               this.descriptors = new DescriptorMBean[this.moduleBeans.size() + 1];
            }

            this.descriptors[0] = this.applicationDescriptor;
            this.registerConnectors();
            this.registerEjbJars();
            this.registerGars();
            this.registerWebApps();

            for(int n = 0; n < this.moduleBeans.size(); ++n) {
               ModuleBeanInfo moduleInfo = (ModuleBeanInfo)this.moduleBeans.get(n);
               if ("jms".equals(((ModuleBeanInfo)this.moduleBeans.get(n)).getType())) {
                  JMSDescriptorMBeanImpl jmsDesc = new JMSDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.moduleBeans.get(n));
                  this.descriptors[n + 1] = jmsDesc;
                  this.registerBean(jmsDesc, new ObjectName(jmsDesc.objectName));
                  this.registerBean(jmsDesc.getJMSDescriptor());
               } else if ("jdbc".equals(((ModuleBeanInfo)this.moduleBeans.get(n)).getType())) {
                  DatasourceDescriptorMBeanImpl jdbcDesc = new DatasourceDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.moduleBeans.get(n));
                  this.descriptors[n + 1] = jdbcDesc;
                  this.registerBean(jdbcDesc, new ObjectName(jdbcDesc.objectName));
                  this.registerBean(jdbcDesc.getDatasourceDescriptor());
               } else if ("diagnostics".equals(((ModuleBeanInfo)this.moduleBeans.get(n)).getType())) {
                  WLDFDescriptorMBeanImpl wldfDesc = new WLDFDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.moduleBeans.get(n));
                  this.descriptors[n + 1] = wldfDesc;
                  this.registerBean(wldfDesc.getWLDFDescriptor());
                  this.registerBean(wldfDesc, new ObjectName(wldfDesc.objectName));
               }
            }
         } else if (ModuleType.WAR.toString().equals(this.getType())) {
            this.registerWebApps();
         } else if (ModuleType.EJB.toString().equals(this.getType())) {
            this.registerEjbJars();
         } else if (ModuleType.RAR.toString().equals(this.getType())) {
            this.registerConnectors();
         } else if ("gar".equals(this.getType())) {
            this.registerGars();
         } else {
            AppDeploymentConfigurationModuleMBeanImpl module;
            if ("jdbc".equals(this.getType())) {
               this.moduleBeans = this.helper.getModuleBeans();
               this.registerBean(((ModuleBeanInfo)this.moduleBeans.get(0)).getStdDesc());
               if (((ModuleBeanInfo)this.moduleBeans.get(0)).getConfigDesc() != null) {
                  this.registerBean(((ModuleBeanInfo)this.moduleBeans.get(0)).getConfigDesc());
               }

               DatasourceDescriptorMBeanImpl jdbcDesc = new DatasourceDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.moduleBeans.get(0));
               if (this.descriptors == null) {
                  this.descriptors = new DescriptorMBean[1];
               }

               this.descriptors[0] = jdbcDesc;
               this.registerBean(jdbcDesc, new ObjectName(jdbcDesc.objectName));
               this.registerBean(jdbcDesc.getDatasourceDescriptor());
               module = new AppDeploymentConfigurationModuleMBeanImpl(this.appInfo, this.context, (ModuleBeanInfo)this.moduleBeans.get(0), new DescriptorMBean[]{jdbcDesc});
               this.registerBean(module, new ObjectName(module.objectName));
               this.modules.add(module);
            } else if ("jms".equals(this.getType())) {
               this.moduleBeans = this.helper.getModuleBeans();
               this.registerBean(((ModuleBeanInfo)this.moduleBeans.get(0)).getStdDesc());
               if (((ModuleBeanInfo)this.moduleBeans.get(0)).getConfigDesc() != null) {
                  this.registerBean(((ModuleBeanInfo)this.moduleBeans.get(0)).getConfigDesc());
               }

               JMSDescriptorMBeanImpl jmsDesc = new JMSDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.moduleBeans.get(0));
               if (this.descriptors == null) {
                  this.descriptors = new DescriptorMBean[1];
               }

               this.descriptors[0] = jmsDesc;
               this.registerBean(jmsDesc, new ObjectName(jmsDesc.objectName));
               this.registerBean(jmsDesc.getJMSDescriptor());
               module = new AppDeploymentConfigurationModuleMBeanImpl(this.appInfo, this.context, (ModuleBeanInfo)this.moduleBeans.get(0), new DescriptorMBean[]{jmsDesc});
               this.registerBean(module, new ObjectName(module.objectName));
               this.modules.add(module);
            }
         }

      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   void unregisterBeans() {
      try {
         for(int n = 0; n < this.registeredBeans.size(); ++n) {
            WLSModelMBeanFactory.unregisterWLSModelMBean(this.registeredBeans.get(n), this.context);
         }

         this.registeredBeans.clear();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   private DescriptorMBean[] getDescs(AppInfo appInfo, WLSModelMBeanContext context, DescriptorMBean d, ModuleBeanInfo mbi) {
      DescriptorMBean[] descs = new DescriptorMBean[mbi.getModules().size() + 1];

      try {
         descs[0] = d;

         for(int mod = 0; mod < mbi.getModules().size(); ++mod) {
            ModuleBeanInfo moduleInfo = (ModuleBeanInfo)mbi.getModules().get(mod);
            this.registerBean(moduleInfo.getStdDesc());
            if (moduleInfo.getConfigDesc() != null) {
               this.registerBean(moduleInfo.getConfigDesc());
            }

            if ("webservice".equals(moduleInfo.getType())) {
               WebservicesDescriptorMBeanImpl webservicesDesc = new WebservicesDescriptorMBeanImpl(appInfo, moduleInfo);
               this.registerBean(webservicesDesc, new ObjectName(webservicesDesc.objectName));
               descs[mod + 1] = webservicesDesc;
            } else if ("persistence".equals(moduleInfo.getType())) {
               PersistenceDescriptorMBeanImpl persistenceDesc = new PersistenceDescriptorMBeanImpl(appInfo, moduleInfo);
               this.registerBean(persistenceDesc, new ObjectName(persistenceDesc.objectName));
               descs[mod + 1] = persistenceDesc;
            }
         }

         return descs;
      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   private void registerModules(WLSModelMBeanContext context, ModuleBeanInfo mbi) {
      try {
         for(int mod = 0; mod < mbi.getModules().size(); ++mod) {
            ModuleBeanInfo moduleInfo = (ModuleBeanInfo)mbi.getModules().get(mod);
            WLSModelMBeanFactory.registerWLSModelMBean(moduleInfo.getStdDesc(), context);
            if (moduleInfo.getConfigDesc() != null) {
               WLSModelMBeanFactory.registerWLSModelMBean(moduleInfo.getConfigDesc(), context);
            }
         }

      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   private void registerBean(Object instance) {
      try {
         WLSModelMBeanFactory.registerWLSModelMBean(instance, this.context);
         this.registeredBeans.add(instance);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   private void registerBean(Object instance, ObjectName objectName) {
      try {
         if (!this.context.getMBeanServer().isRegistered(objectName)) {
            WLSModelMBeanFactory.registerWLSModelMBean(instance, objectName, this.context);
            this.registeredBeans.add(instance);
         }

      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   private void registerConnectors() {
      try {
         this.connectorBeans = this.helper.getConnectorBeans();
         if (this.connectorBeans != null) {
            for(int n = 0; n < this.connectorBeans.size(); ++n) {
               this.registerBean(((ModuleBeanInfo)this.connectorBeans.get(n)).getConfigDesc());
               this.registerBean(((ModuleBeanInfo)this.connectorBeans.get(n)).getStdDesc());
               ConnectorDescriptorMBeanImpl connectorDesc = new ConnectorDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.connectorBeans.get(n));
               this.registerBean(connectorDesc, new ObjectName(connectorDesc.objectName));
               AppDeploymentConfigurationModuleMBeanImpl module = new AppDeploymentConfigurationModuleMBeanImpl(this.appInfo, this.context, (ModuleBeanInfo)this.connectorBeans.get(n), new DescriptorMBean[]{connectorDesc});
               this.registerBean(module, new ObjectName(module.objectName));
               this.modules.add(module);
            }
         }

      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   private void registerEjbJars() {
      try {
         this.ejbJarBeans = this.helper.getEjbJarBeans();
         if (this.ejbJarBeans != null) {
            for(int n = 0; n < this.ejbJarBeans.size(); ++n) {
               this.registerBean(((ModuleBeanInfo)this.ejbJarBeans.get(n)).getConfigDesc());
               this.registerBean(((ModuleBeanInfo)this.ejbJarBeans.get(n)).getStdDesc());
               EJBDescriptorMBeanImpl EJBDesc = new EJBDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.ejbJarBeans.get(n));
               this.registerBean(EJBDesc, new ObjectName(EJBDesc.objectName));
               DescriptorMBean[] descs = this.getDescs(this.appInfo, this.context, EJBDesc, (ModuleBeanInfo)this.ejbJarBeans.get(n));

               for(int mod = 0; mod < ((ModuleBeanInfo)this.ejbJarBeans.get(n)).getModules().size(); ++mod) {
                  this.registerModules(this.context, (ModuleBeanInfo)((ModuleBeanInfo)this.ejbJarBeans.get(n)).getModules().get(mod));
               }

               AppDeploymentConfigurationModuleMBeanImpl module = new AppDeploymentConfigurationModuleMBeanImpl(this.appInfo, this.context, (ModuleBeanInfo)this.ejbJarBeans.get(n), descs);
               this.registerBean(module, new ObjectName(module.objectName));
               this.modules.add(module);
            }
         }

      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   private void registerWebApps() {
      try {
         this.webAppBeans = this.helper.getWebAppBeans();
         if (this.webAppBeans != null) {
            for(int n = 0; n < this.webAppBeans.size(); ++n) {
               this.registerBean(((ModuleBeanInfo)this.webAppBeans.get(n)).getConfigDesc());
               this.registerBean(((ModuleBeanInfo)this.webAppBeans.get(n)).getStdDesc());
               WebAppDescriptorMBeanImpl webAppDesc = new WebAppDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.webAppBeans.get(n));
               DescriptorMBean[] descs = this.getDescs(this.appInfo, this.context, webAppDesc, (ModuleBeanInfo)this.webAppBeans.get(n));
               this.registerBean(webAppDesc, new ObjectName(webAppDesc.objectName));

               for(int mod = 0; mod < ((ModuleBeanInfo)this.webAppBeans.get(n)).getModules().size(); ++mod) {
                  this.registerModules(this.context, (ModuleBeanInfo)((ModuleBeanInfo)this.webAppBeans.get(n)).getModules().get(mod));
               }

               AppDeploymentConfigurationModuleMBeanImpl module = new AppDeploymentConfigurationModuleMBeanImpl(this.appInfo, this.context, (ModuleBeanInfo)this.webAppBeans.get(n), descs);
               this.registerBean(module, new ObjectName(module.objectName));
               this.modules.add(module);
            }
         }

      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   private void registerGars() {
      try {
         this.garBeans = this.helper.getGarBeans();
         if (this.garBeans != null) {
            for(int n = 0; n < this.garBeans.size(); ++n) {
               this.registerBean(((ModuleBeanInfo)this.garBeans.get(n)).getStdDesc());
               GarDescriptorMBeanImpl garDesc = new GarDescriptorMBeanImpl(this.appInfo, (ModuleBeanInfo)this.garBeans.get(n));
               this.registerBean(garDesc, new ObjectName(garDesc.objectName));
               AppDeploymentConfigurationModuleMBeanImpl module = new AppDeploymentConfigurationModuleMBeanImpl(this.appInfo, this.context, (ModuleBeanInfo)this.garBeans.get(n), new DescriptorMBean[]{garDesc});
               this.registerBean(module, new ObjectName(module.objectName));
               this.modules.add(module);
            }
         }

      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }
}
