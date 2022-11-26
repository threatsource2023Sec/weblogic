package weblogic.management.mbeans.custom;

import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.management.RuntimeOperationsException;
import weblogic.descriptor.Descriptor;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJMSConnectionFactoryMBean;
import weblogic.management.configuration.ForeignJMSDestinationMBean;
import weblogic.management.configuration.JMSDistributedQueueMemberMBean;
import weblogic.management.configuration.JMSDistributedTopicMemberMBean;
import weblogic.management.configuration.JMSQueueMBean;
import weblogic.management.configuration.JMSSessionPoolMBean;
import weblogic.management.configuration.JMSTopicMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PartitionTemplateMBean;
import weblogic.management.configuration.PartitionWorkManagerMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ResourceManagementMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.AttributeAggregator;
import weblogic.management.provider.internal.DescriptorHelper;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;

public final class Domain extends ConfigurationMBeanCustomizer {
   static String ERR = "WebLogic Server Multitenancy is no longer supported";
   private boolean debug = false;
   private String path = null;
   static int instanceCounter = 0;
   static Map instanceMap = new HashMap();
   private static Map configurationServices = new HashMap();
   private boolean clusterConstraintsEnabled = false;
   private boolean productionModeEnabled = false;

   public Domain(ConfigurationMBeanCustomized base) {
      super(base);
      String enforceClusterConstraints = System.getProperty("weblogic.ClusterConstraintsEnabled");
      if (enforceClusterConstraints != null) {
         enforceClusterConstraints = enforceClusterConstraints.toLowerCase(Locale.US);
         if (enforceClusterConstraints.equals("true")) {
            this.setClusterConstraintsEnabled(true);
         } else {
            this.setClusterConstraintsEnabled(false);
         }
      }

      if (this.debug) {
         ++instanceCounter;
         instanceMap.put(this.toString(), Thread.currentThread().getStackTrace());
         System.out.println("Constructed DomainMBean customizer current count " + instanceCounter);
         Thread.dumpStack();
      }

   }

   public String getRootDirectory() {
      try {
         if (this.path == null) {
            this.path = DomainDir.getRootDir();
         }
      } catch (Exception var2) {
         ManagementLogger.logExceptionInCustomizer(var2);
      }

      return this.path;
   }

   public boolean isClusterConstraintsEnabled() {
      return this.clusterConstraintsEnabled;
   }

   public void setClusterConstraintsEnabled(boolean enableClusterConstraints) {
      this.clusterConstraintsEnabled = enableClusterConstraints;
   }

   public boolean isProductionModeEnabled() {
      return this.productionModeEnabled;
   }

   public void setProductionModeEnabled(boolean productionModeEnabled) {
      this.productionModeEnabled = productionModeEnabled;
      Descriptor rootDescriptor = ((DomainMBean)this.getMbean()).getDescriptor();
      DescriptorHelper.setDescriptorTreeProductionMode(rootDescriptor, productionModeEnabled);
      DescriptorHelper.setDescriptorManagerProductionModeIfNeeded(rootDescriptor, productionModeEnabled);
   }

   public void discoverManagedServers() {
   }

   public boolean discoverManagedServer(String serverName) {
      return false;
   }

   public Object[] getDisconnectedManagedServers() {
      return new Object[0];
   }

   public HashMap start() {
      if (this.isConfig()) {
         return null;
      } else {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         DomainAccess runtime = ManagementService.getDomainAccess(kernelId);
         ServerMBean[] servers = ((DomainMBean)this.getMbean()).getServers();
         HashMap taskMBeans = new HashMap();

         try {
            for(int i = 0; i < servers.length; ++i) {
               ServerMBean server = servers[i];
               taskMBeans.put(server.getName(), runtime.lookupServerLifecycleRuntime(server.getName()).start());

               try {
                  Thread.currentThread();
                  Thread.sleep(1000L);
               } catch (Exception var8) {
               }
            }

            return taskMBeans;
         } catch (ServerLifecycleException var9) {
            RuntimeException re = new RuntimeException(var9);
            throw new RuntimeOperationsException(re);
         }
      }
   }

   public HashMap kill() {
      if (this.isConfig()) {
         return null;
      } else {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         DomainAccess runtime = ManagementService.getDomainAccess(kernelId);
         ServerMBean[] servers = ((DomainMBean)this.getMbean()).getServers();
         HashMap taskMBeans = new HashMap();

         try {
            for(int i = 0; i < servers.length; ++i) {
               ServerMBean server = servers[i];
               taskMBeans.put(server.getName(), runtime.lookupServerLifecycleRuntime(server.getName()).forceShutdown());

               try {
                  Thread.currentThread();
                  Thread.sleep(1000L);
               } catch (Exception var8) {
               }
            }

            return taskMBeans;
         } catch (ServerLifecycleException var9) {
            RuntimeException re = new RuntimeException(var9);
            throw new RuntimeOperationsException(re);
         }
      }
   }

   public DeploymentMBean[] getDeployments() {
      return (DeploymentMBean[])((DeploymentMBean[])Domain.DEPLOYMENTAGGREGATOR.instance.getAll(this.getMbean()));
   }

   public BasicDeploymentMBean[] getBasicDeployments() {
      DomainMBean domain = (DomainMBean)this.getMbean();
      BasicDeploymentMBean[] apps = AppDeploymentHelper.getAppsAndLibs(domain);
      BasicDeploymentMBean[] sysRes = domain.getSystemResources();
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

   public SystemResourceMBean[] getSystemResources() {
      return (SystemResourceMBean[])((SystemResourceMBean[])Domain.SYSTEMRESOURCEAGGREGATOR.instance.getAll(this.getMbean()));
   }

   public SystemResourceMBean lookupSystemResource(String name) {
      return (SystemResourceMBean)Domain.SYSTEMRESOURCEAGGREGATOR.instance.lookup(this.getMbean(), name);
   }

   public TargetMBean[] getTargets() {
      return (TargetMBean[])((TargetMBean[])Domain.TARGETAGGREGATOR.instance.getAll(this.getMbean()));
   }

   public TargetMBean lookupTarget(String name) {
      return (TargetMBean)Domain.TARGETAGGREGATOR.instance.lookup(this.getMbean(), name);
   }

   public JMSSessionPoolMBean createJMSSessionPool(String name, JMSSessionPoolMBean toClone) {
      JMSSessionPoolMBean newBean = (JMSSessionPoolMBean)this.getMbean().createChildCopyIncludingObsolete("JMSSessionPool", toClone);
      return newBean;
   }

   public ForeignJMSDestinationMBean createForeignJMSDestination(String name, ForeignJMSDestinationMBean toClone) {
      ForeignJMSDestinationMBean newBean = (ForeignJMSDestinationMBean)this.getMbean().createChildCopyIncludingObsolete("ForeignJMSDestination", toClone);
      return newBean;
   }

   public ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String name, ForeignJMSConnectionFactoryMBean toClone) {
      ForeignJMSConnectionFactoryMBean newBean = (ForeignJMSConnectionFactoryMBean)this.getMbean().createChildCopyIncludingObsolete("ForeignJMSConnectionFactory", toClone);
      return newBean;
   }

   public JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String name, JMSDistributedQueueMemberMBean toClone) {
      JMSDistributedQueueMemberMBean newBean = (JMSDistributedQueueMemberMBean)this.getMbean().createChildCopy("JMSDistributedQueueMember", toClone);
      return newBean;
   }

   public JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String name, JMSDistributedTopicMemberMBean toClone) {
      JMSDistributedTopicMemberMBean newBean = (JMSDistributedTopicMemberMBean)this.getMbean().createChildCopy("JMSDistributedTopicMember", toClone);
      return newBean;
   }

   public JMSTopicMBean createJMSTopic(String name, JMSTopicMBean toClone) {
      JMSTopicMBean newBean = (JMSTopicMBean)this.getMbean().createChildCopyIncludingObsolete("JMSTopic", toClone);
      return newBean;
   }

   public JMSQueueMBean createJMSQueue(String name, JMSQueueMBean toClone) {
      JMSQueueMBean newBean = (JMSQueueMBean)this.getMbean().createChildCopyIncludingObsolete("JMSQueue", toClone);
      return newBean;
   }

   public WLDFSystemResourceMBean createWLDFSystemResourceFromBuiltin(String name, String builtInSystemResource) {
      DomainMBean domain = (DomainMBean)this.getMbean();
      return (new WLDFConfigProcessor()).createWLDFSystemResource(domain, name, builtInSystemResource);
   }

   public boolean arePartitionsPresent() {
      return false;
   }

   public ConfigurationMBean[] findConfigBeansWithTags(String configBeanType, String... tags) {
      return this.findConfigBeansWithTags(configBeanType, true, tags);
   }

   public ConfigurationMBean[] findConfigBeansWithTags(String configBeanType, boolean matchAll, String... tags) {
      this.validateTagQueryInput(tags);
      HashMap mbeanMap = new HashMap();
      HashMap taggedMbeanMap = ConfigurationMBeanCustomizer.getTaggedMBeanMap();
      if (taggedMbeanMap != null && !taggedMbeanMap.isEmpty()) {
         Collection coll = taggedMbeanMap.values();
         Iterator var7 = coll.iterator();

         while(true) {
            WebLogicMBean wlMbean;
            String type;
            do {
               do {
                  if (!var7.hasNext()) {
                     return (ConfigurationMBean[])mbeanMap.values().toArray(new ConfigurationMBean[0]);
                  }

                  wlMbean = (WebLogicMBean)var7.next();
               } while(!(wlMbean instanceof ConfigurationMBean));

               type = wlMbean.getType();
            } while(configBeanType != null && !configBeanType.isEmpty() && !configBeanType.equals(type));

            mbeanMap = this.findMBean(mbeanMap, (ConfigurationMBean)wlMbean, tags, matchAll);
         }
      } else {
         return (ConfigurationMBean[])mbeanMap.values().toArray(new ConfigurationMBean[0]);
      }
   }

   private HashMap findMBean(HashMap mbeanMap, ConfigurationMBean mbean, String[] inputTagArray, boolean matchAll) {
      HashSet mbeanTagSet = new HashSet();
      String[] mbeanTagArray = mbean.getTags();
      if (mbeanTagArray != null && mbeanTagArray.length != 0) {
         String[] var7 = mbeanTagArray;
         int var8 = mbeanTagArray.length;

         int var9;
         for(var9 = 0; var9 < var8; ++var9) {
            String mta = var7[var9];
            mbeanTagSet.add(mta.toLowerCase().trim());
         }

         String key = mbean.getName() + ":" + mbean.getType() + ":" + mbean.getParent();
         if (inputTagArray.length == 1 && mbeanTagSet.contains(inputTagArray[0])) {
            mbeanMap.put(key, mbean);
            return mbeanMap;
         } else {
            int var17;
            if (matchAll) {
               if (mbeanTagArray.length < inputTagArray.length) {
                  return mbeanMap;
               }

               boolean hasAllTags = true;
               String[] var16 = inputTagArray;
               var17 = inputTagArray.length;

               for(int var11 = 0; var11 < var17; ++var11) {
                  String ita = var16[var11];
                  if (!mbeanTagSet.contains(ita.toLowerCase().trim())) {
                     hasAllTags = false;
                     break;
                  }
               }

               if (hasAllTags) {
                  mbeanMap.put(key, mbean);
               }
            } else {
               String[] var15 = inputTagArray;
               var9 = inputTagArray.length;

               for(var17 = 0; var17 < var9; ++var17) {
                  String ita = var15[var17];
                  if (mbeanTagSet.contains(ita)) {
                     mbeanMap.put(key, mbean);
                     break;
                  }
               }
            }

            return mbeanMap;
         }
      } else {
         return mbeanMap;
      }
   }

   private void validateTagQueryInput(String... tags) throws IllegalArgumentException {
      if (tags != null && tags.length != 0) {
         String[] var2 = tags;
         int var3 = tags.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String tag = var2[var4];
            if (tag == null || tag.isEmpty()) {
               throw new IllegalArgumentException("Invalid tag: tag must contain a valid string");
            }
         }

      } else {
         throw new IllegalArgumentException("Tag may not be null or empty string");
      }
   }

   public String[] listTags(String configType) {
      HashSet tagsResultSet = new HashSet();
      HashMap taggedMbeanMap = ConfigurationMBeanCustomizer.getTaggedMBeanMap();
      if (taggedMbeanMap != null && !taggedMbeanMap.isEmpty()) {
         Collection coll = taggedMbeanMap.values();
         Iterator var5 = coll.iterator();

         while(var5.hasNext()) {
            WebLogicMBean wlMbean = (WebLogicMBean)var5.next();
            if (wlMbean instanceof ConfigurationMBean) {
               String type = wlMbean.getType();
               String[] tags = ((ConfigurationMBean)wlMbean).getTags();
               if (tags.length > 0) {
                  if (configType == null | configType.isEmpty()) {
                     tagsResultSet.addAll(Arrays.asList(tags));
                  } else if (configType.equals(type)) {
                     tagsResultSet.addAll(Arrays.asList(tags));
                  }
               }
            }
         }
      }

      return (String[])tagsResultSet.toArray(new String[0]);
   }

   public VirtualTargetMBean[] findAllVirtualTargets() {
      return new VirtualTargetMBean[0];
   }

   public VirtualTargetMBean lookupInAllVirtualTargets(String name) {
      return null;
   }

   public TargetMBean lookupInAllTargets(String name) {
      TargetMBean[] var2 = ((DomainMBean)this.getMbean()).findAllTargets();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean t = var2[var4];
         if (t.getName().equals(name)) {
            return t;
         }
      }

      return null;
   }

   public TargetMBean[] findAllTargets() {
      Set findAllEffectiveTarget = new HashSet();
      TargetMBean[] targets = ((DomainMBean)this.getMbean()).getTargets();
      if (targets != null && targets.length > 0) {
         findAllEffectiveTarget.addAll(Arrays.asList(targets));
      }

      return (TargetMBean[])findAllEffectiveTarget.toArray(new TargetMBean[findAllEffectiveTarget.size()]);
   }

   public VirtualTargetMBean[] getVirtualTargets() {
      return new VirtualTargetMBean[0];
   }

   public VirtualTargetMBean createVirtualTarget(String name) {
      throw new UnsupportedOperationException("createVirtualTarget: " + ERR);
   }

   public void destroyVirtualTarget(VirtualTargetMBean target) {
      throw new UnsupportedOperationException("destroyVirtualTarget: " + ERR);
   }

   public VirtualTargetMBean lookupVirtualTarget(String name) {
      return null;
   }

   public ResourceManagementMBean getResourceManagement() {
      return null;
   }

   public PartitionMBean[] getPartitions() {
      return new PartitionMBean[0];
   }

   public PartitionMBean lookupPartition(String name) {
      return null;
   }

   public PartitionMBean findPartitionByID(String id) {
      return null;
   }

   public PartitionMBean createPartition(String name) {
      throw new UnsupportedOperationException("createPartition: " + ERR);
   }

   public PartitionMBean createPartition(String name, String partitionID) {
      throw new UnsupportedOperationException("createPartition: " + ERR);
   }

   public void destroyPartition(PartitionMBean partition) {
      throw new UnsupportedOperationException("destroyPartition: " + ERR);
   }

   public String getPartitionUriSpace() {
      return "";
   }

   public ResourceGroupMBean[] getResourceGroups() {
      return new ResourceGroupMBean[0];
   }

   public ResourceGroupMBean createResourceGroup(String name) {
      throw new UnsupportedOperationException("createResourceGroup: " + ERR);
   }

   public ResourceGroupMBean lookupResourceGroup(String name) {
      return null;
   }

   public void destroyResourceGroup(ResourceGroupMBean resourceGroup) {
      throw new UnsupportedOperationException("destroyResourceGroup: " + ERR);
   }

   public ResourceGroupTemplateMBean[] getResourceGroupTemplates() {
      return new ResourceGroupTemplateMBean[0];
   }

   public ResourceGroupTemplateMBean lookupResourceGroupTemplate(String name) {
      return null;
   }

   public ResourceGroupTemplateMBean createResourceGroupTemplate(String name) {
      throw new UnsupportedOperationException("createResourceGroupTemplat: " + ERR);
   }

   public void destroyResourceGroupTemplate(ResourceGroupTemplateMBean template) {
      throw new UnsupportedOperationException("destroyResourceGroupTemplate: " + ERR);
   }

   public PartitionTemplateMBean[] getPartitionTemplates() {
      return new PartitionTemplateMBean[0];
   }

   public PartitionTemplateMBean lookupPartitionTemplate(String name) {
      return null;
   }

   public PartitionTemplateMBean createPartitionTemplate(String name) {
      throw new UnsupportedOperationException("createPartitionTemplate: " + ERR);
   }

   public void destroyPartitionTemplate(PartitionTemplateMBean template) {
      throw new UnsupportedOperationException("destroyPartitionTemplate: " + ERR);
   }

   public PartitionMBean createPartitionFromTemplate(String name, PartitionTemplateMBean template) {
      throw new UnsupportedOperationException("createPartitionFromTemplate: " + ERR);
   }

   public PartitionWorkManagerMBean[] getPartitionWorkManagers() {
      return new PartitionWorkManagerMBean[0];
   }

   public PartitionWorkManagerMBean createPartitionWorkManager(String name) {
      throw new UnsupportedOperationException("createPartitionWorkManager: " + ERR);
   }

   public void destroyPartitionWorkManager(PartitionWorkManagerMBean bean) {
      throw new UnsupportedOperationException("destroyPartitionWorkManager: " + ERR);
   }

   public PartitionWorkManagerMBean lookupPartitionWorkManager(String name) {
      return null;
   }

   private static class TARGETAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator("weblogic.management.configuration.DomainMBean", TargetMBean.class);
   }

   private static class SYSTEMRESOURCEAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator(DomainMBean.class, SystemResourceMBean.class, "getSystemResources");
   }

   private static class DEPLOYMENTAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator(DomainMBean.class, DeploymentMBean.class, "getDeployments");
   }
}
