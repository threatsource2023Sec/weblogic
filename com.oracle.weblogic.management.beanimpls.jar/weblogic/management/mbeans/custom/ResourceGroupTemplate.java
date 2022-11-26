package weblogic.management.mbeans.custom;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.internal.DescriptorBeanClassName;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DiscreteResourceProcessor;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.ForeignJNDIProviderMBean;
import weblogic.management.configuration.GeneralResourceProcessor;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.configuration.ManagedExecutorServiceMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceMBean;
import weblogic.management.configuration.ManagedThreadFactoryMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.AttributeAggregator;
import weblogic.management.utils.AppDeploymentHelper;

public class ResourceGroupTemplate extends ConfigurationMBeanCustomizer {
   public static final String RESOURCE_GROUP_TEMPLATES_NAME = "resource-group-templates";
   private final ResourceGroupTemplateMBean rgt;
   private final ConfigurationMBeanCustomized base;
   private String uploadDir;
   private static final String DESTROYER = "destroyer";

   public ResourceGroupTemplate(ConfigurationMBeanCustomized base) {
      super(base);
      this.base = base;
      this.rgt = (ResourceGroupTemplateMBean)base;
   }

   public DeploymentMBean[] getDeployments() {
      return (DeploymentMBean[])((DeploymentMBean[])ResourceGroupTemplate.DEPLOYMENTAGGREGATOR.instance.getAll(this.getMbean()));
   }

   static BasicDeploymentMBean[] getBasicDeployments(ResourceGroupTemplateMBean rgt) {
      BasicDeploymentMBean[] apps = AppDeploymentHelper.getAppsAndLibs(rgt, false);
      BasicDeploymentMBean[] sysRes = rgt.getSystemResources();
      if (apps != null && apps.length != 0) {
         if (sysRes != null && sysRes.length != 0) {
            BasicDeploymentMBean[] basicDeps = new BasicDeploymentMBean[apps.length + sysRes.length];
            System.arraycopy(sysRes, 0, basicDeps, 0, sysRes.length);
            System.arraycopy(apps, 0, basicDeps, sysRes.length, apps.length);
            return basicDeps;
         } else {
            return apps;
         }
      } else {
         return sysRes;
      }
   }

   public BasicDeploymentMBean[] getBasicDeployments() {
      return getBasicDeployments((ResourceGroupTemplateMBean)this.getMbean());
   }

   public SystemResourceMBean[] getSystemResources() {
      return (SystemResourceMBean[])((SystemResourceMBean[])ResourceGroupTemplate.SYSTEMRESOURCEAGGREGATOR.instance.getAll(this.getMbean()));
   }

   public void processResources(GeneralResourceProcessor proc) throws InvalidAttributeValueException, ManagementException {
      proc.start();
      AppDeploymentMBean[] var2 = this.rgt.getAppDeployments();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         AppDeploymentMBean appDeployment = var2[var4];
         proc.processResource(appDeployment);
      }

      LibraryMBean[] var6 = this.rgt.getLibraries();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         LibraryMBean library = var6[var4];
         proc.processResource(library);
      }

      JMSServerMBean[] var7 = this.rgt.getJMSServers();
      var3 = var7.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JMSServerMBean jmsServer = var7[var4];
         proc.processResource(jmsServer);
      }

      JMSBridgeDestinationMBean[] var8 = this.rgt.getJMSBridgeDestinations();
      var3 = var8.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JMSBridgeDestinationMBean bridgeDest = var8[var4];
         proc.processResource(bridgeDest);
      }

      MessagingBridgeMBean[] var9 = this.rgt.getMessagingBridges();
      var3 = var9.length;

      for(var4 = 0; var4 < var3; ++var4) {
         MessagingBridgeMBean messagingBridge = var9[var4];
         proc.processResource(messagingBridge);
      }

      PathServiceMBean[] var10 = this.rgt.getPathServices();
      var3 = var10.length;

      for(var4 = 0; var4 < var3; ++var4) {
         PathServiceMBean pathService = var10[var4];
         proc.processResource(pathService);
      }

      MailSessionMBean[] var11 = this.rgt.getMailSessions();
      var3 = var11.length;

      for(var4 = 0; var4 < var3; ++var4) {
         MailSessionMBean mailSession = var11[var4];
         proc.processResource(mailSession);
      }

      FileStoreMBean[] var12 = this.rgt.getFileStores();
      var3 = var12.length;

      for(var4 = 0; var4 < var3; ++var4) {
         FileStoreMBean fileStore = var12[var4];
         proc.processResource(fileStore);
      }

      JDBCStoreMBean[] var13 = this.rgt.getJDBCStores();
      var3 = var13.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JDBCStoreMBean jdbcStore = var13[var4];
         proc.processResource(jdbcStore);
      }

      SAFAgentMBean[] var14 = this.rgt.getSAFAgents();
      var3 = var14.length;

      for(var4 = 0; var4 < var3; ++var4) {
         SAFAgentMBean safAgent = var14[var4];
         proc.processResource(safAgent);
      }

      JMSSystemResourceMBean[] var16 = this.rgt.getJMSSystemResources();
      var3 = var16.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JMSSystemResourceMBean jmsSystemResource = var16[var4];
         proc.processResource(jmsSystemResource);
      }

      JDBCSystemResourceMBean[] var18 = this.rgt.getJDBCSystemResources();
      var3 = var18.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JDBCSystemResourceMBean jdbcSystemResource = var18[var4];
         proc.processResource(jdbcSystemResource);
      }

      CoherenceClusterSystemResourceMBean[] var20 = this.rgt.getCoherenceClusterSystemResources();
      var3 = var20.length;

      for(var4 = 0; var4 < var3; ++var4) {
         CoherenceClusterSystemResourceMBean coherenceClusterSystemResource = var20[var4];
         proc.processResource(coherenceClusterSystemResource);
      }

      OsgiFrameworkMBean[] var22 = this.rgt.getOsgiFrameworks();
      var3 = var22.length;

      for(var4 = 0; var4 < var3; ++var4) {
         OsgiFrameworkMBean osgiFrameworkBean = var22[var4];
         proc.processResource(osgiFrameworkBean);
      }

      WLDFSystemResourceMBean[] var24 = this.rgt.getWLDFSystemResources();
      var3 = var24.length;

      for(var4 = 0; var4 < var3; ++var4) {
         WLDFSystemResourceMBean wldfSystemResource = var24[var4];
         proc.processResource(wldfSystemResource);
      }

      ManagedExecutorServiceMBean[] var26 = this.rgt.getManagedExecutorServices();
      var3 = var26.length;

      for(var4 = 0; var4 < var3; ++var4) {
         ManagedExecutorServiceMBean bean = var26[var4];
         proc.processResource(bean);
      }

      ManagedScheduledExecutorServiceMBean[] var28 = this.rgt.getManagedScheduledExecutorServices();
      var3 = var28.length;

      for(var4 = 0; var4 < var3; ++var4) {
         ManagedScheduledExecutorServiceMBean bean = var28[var4];
         proc.processResource(bean);
      }

      ManagedThreadFactoryMBean[] var30 = this.rgt.getManagedThreadFactories();
      var3 = var30.length;

      for(var4 = 0; var4 < var3; ++var4) {
         ManagedThreadFactoryMBean bean = var30[var4];
         proc.processResource(bean);
      }

      proc.end();
   }

   public void processResources(DiscreteResourceProcessor proc) throws ManagementException, InvalidAttributeValueException {
      proc.start();
      AppDeploymentMBean[] var2 = this.rgt.getAppDeployments();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         AppDeploymentMBean appDeployment = var2[var4];
         proc.processAppDeployment(appDeployment);
      }

      LibraryMBean[] var6 = this.rgt.getLibraries();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         LibraryMBean library = var6[var4];
         proc.processLibrary(library);
      }

      JMSServerMBean[] var7 = this.rgt.getJMSServers();
      var3 = var7.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JMSServerMBean jmsServer = var7[var4];
         proc.processJMSServer(jmsServer);
      }

      JMSBridgeDestinationMBean[] var8 = this.rgt.getJMSBridgeDestinations();
      var3 = var8.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JMSBridgeDestinationMBean bridgeDest = var8[var4];
         proc.processJMSBridgeDestination(bridgeDest);
      }

      MessagingBridgeMBean[] var9 = this.rgt.getMessagingBridges();
      var3 = var9.length;

      for(var4 = 0; var4 < var3; ++var4) {
         MessagingBridgeMBean messagingBridge = var9[var4];
         proc.processMessagingBridge(messagingBridge);
      }

      PathServiceMBean[] var10 = this.rgt.getPathServices();
      var3 = var10.length;

      for(var4 = 0; var4 < var3; ++var4) {
         PathServiceMBean pathService = var10[var4];
         proc.processPathService(pathService);
      }

      MailSessionMBean[] var11 = this.rgt.getMailSessions();
      var3 = var11.length;

      for(var4 = 0; var4 < var3; ++var4) {
         MailSessionMBean mailSession = var11[var4];
         proc.processMailSession(mailSession);
      }

      FileStoreMBean[] var12 = this.rgt.getFileStores();
      var3 = var12.length;

      for(var4 = 0; var4 < var3; ++var4) {
         FileStoreMBean fileStore = var12[var4];
         proc.processFileStore(fileStore);
      }

      JDBCStoreMBean[] var13 = this.rgt.getJDBCStores();
      var3 = var13.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JDBCStoreMBean jdbcStore = var13[var4];
         proc.processJDBCStore(jdbcStore);
      }

      SAFAgentMBean[] var14 = this.rgt.getSAFAgents();
      var3 = var14.length;

      for(var4 = 0; var4 < var3; ++var4) {
         SAFAgentMBean safAgent = var14[var4];
         proc.processSAFAgent(safAgent);
      }

      JMSSystemResourceMBean[] var16 = this.rgt.getJMSSystemResources();
      var3 = var16.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JMSSystemResourceMBean jmsSystemResource = var16[var4];
         proc.processJMSSystemResource(jmsSystemResource);
      }

      JDBCSystemResourceMBean[] var18 = this.rgt.getJDBCSystemResources();
      var3 = var18.length;

      for(var4 = 0; var4 < var3; ++var4) {
         JDBCSystemResourceMBean jdbcSystemResource = var18[var4];
         proc.processJDBCSystemResource(jdbcSystemResource);
      }

      CoherenceClusterSystemResourceMBean[] var20 = this.rgt.getCoherenceClusterSystemResources();
      var3 = var20.length;

      for(var4 = 0; var4 < var3; ++var4) {
         CoherenceClusterSystemResourceMBean coherenceClusterSystemResource = var20[var4];
         proc.processCoherenceClusterSystemResource(coherenceClusterSystemResource);
      }

      OsgiFrameworkMBean[] var22 = this.rgt.getOsgiFrameworks();
      var3 = var22.length;

      for(var4 = 0; var4 < var3; ++var4) {
         OsgiFrameworkMBean osgiFrameworkBean = var22[var4];
         proc.processOsgiFramework(osgiFrameworkBean);
      }

      WLDFSystemResourceMBean[] var24 = this.rgt.getWLDFSystemResources();
      var3 = var24.length;

      for(var4 = 0; var4 < var3; ++var4) {
         WLDFSystemResourceMBean wldfSystemResource = var24[var4];
         proc.processWLDFSystemResource(wldfSystemResource);
      }

      ManagedExecutorServiceMBean[] var26 = this.rgt.getManagedExecutorServices();
      var3 = var26.length;

      for(var4 = 0; var4 < var3; ++var4) {
         ManagedExecutorServiceMBean bean = var26[var4];
         proc.processManagedExecutorService(bean);
      }

      ManagedScheduledExecutorServiceMBean[] var28 = this.rgt.getManagedScheduledExecutorServices();
      var3 = var28.length;

      for(var4 = 0; var4 < var3; ++var4) {
         ManagedScheduledExecutorServiceMBean bean = var28[var4];
         proc.processManagedScheduledExecutorService(bean);
      }

      ManagedThreadFactoryMBean[] var30 = this.rgt.getManagedThreadFactories();
      var3 = var30.length;

      for(var4 = 0; var4 < var3; ++var4) {
         ManagedThreadFactoryMBean bean = var30[var4];
         proc.processManagedThreadFactory(bean);
      }

      ForeignJNDIProviderMBean[] var32 = this.rgt.getForeignJNDIProviders();
      var3 = var32.length;

      for(var4 = 0; var4 < var3; ++var4) {
         ForeignJNDIProviderMBean bean = var32[var4];
         proc.processForeignJNDIProvider(bean);
      }

      proc.end();
   }

   public String getUploadDirectoryName() {
      String template_name = this.getMbean().getName();
      Object parent = this.getMbean().getParent();
      if (parent instanceof DomainMBean) {
         String server_name = ((DomainMBean)parent).getAdminServerName();
         if (this.uploadDir == null && server_name != null && template_name != null) {
            String templateUploadPath = "upload" + File.separator + "resource-group-templates" + File.separator + template_name + File.separator;
            this.uploadDir = DomainDir.getPathRelativeServerDirNonCanonical(server_name, templateUploadPath);
         }
      }

      return this.uploadDir;
   }

   public void setUploadDirectoryName(String uploadDir) {
      this.uploadDir = uploadDir;
   }

   public void _preDestroy() {
      destroySystemResources(this.rgt);
   }

   protected static void destroySystemResources(ResourceGroupTemplateMBean rgt) {
      SystemResourceMBean[] systemResources = rgt.getSystemResources();
      SystemResourceMBean[] var2 = systemResources;
      int var3 = systemResources.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         SystemResourceMBean sRMB = var2[var4];

         try {
            BeanInfo bean = ManagementService.getBeanInfoAccess().getBeanInfoForInstance(sRMB, true, (String)null);
            String interfaceName = DescriptorBeanClassName.toInterface(bean.getBeanDescriptor().getBeanClass().getName());
            PropertyDescriptor[] var8 = ManagementService.getBeanInfoAccess().getBeanInfoForInstance(rgt, true, (String)null).getPropertyDescriptors();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               PropertyDescriptor p = var8[var10];
               if (p.getPropertyType().isArray() && p.getPropertyType().getComponentType().getName().equals(interfaceName)) {
                  String destroyer = (String)p.getValue("destroyer");
                  Method m = rgt.getClass().getMethod(destroyer, Class.forName(interfaceName));
                  m.invoke(rgt, sRMB);
               }
            }
         } catch (InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException var14) {
            throw new RuntimeException(var14);
         }
      }

   }

   private static class SYSTEMRESOURCEAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator(ResourceGroupTemplateMBean.class, SystemResourceMBean.class, "getSystemResources");
   }

   private static class DEPLOYMENTAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator(ResourceGroupTemplateMBean.class, DeploymentMBean.class, "getDeployments");
   }
}
