package weblogic.management.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.internal.ConfigLogger;
import weblogic.management.internal.ManagementTextTextFormatter;

@Service
@ContractsProvided({DomainMBeanValidator.class})
public final class JMSLegalHelper implements DomainMBeanValidator {
   private static final int DIABLO_VERSION = 9;
   public static final Set messageKeys = new HashSet();

   private static boolean targetMatchStoreTarget(DeploymentMBean bean, DeploymentMBean storeBean) {
      TargetMBean[] t1 = bean.getTargets();
      TargetMBean[] t2 = storeBean.getTargets();
      if (t1 != null && t1.length != 0 && t1[0] != null) {
         if (t2 != null && t2.length != 0 && t2[0] != null) {
            return t1[0].getName().equals(t2[0].getName());
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   static boolean isAssociatedMBeanScopeLegal(WebLogicMBean bean, WebLogicMBean associatedBean) {
      WebLogicMBean beanScope = bean.getParent();
      WebLogicMBean associatedBeanScope = associatedBean.getParent();
      if (!(beanScope instanceof DomainMBean) && !(beanScope instanceof ResourceGroupTemplateMBean)) {
         throw new AssertionError("The scope of the given MBean cannot be null and it must be an instance of either a DomainMBean or a ResourceGroupTemplateMBean");
      } else if (!(associatedBeanScope instanceof DomainMBean) && !(associatedBeanScope instanceof ResourceGroupTemplateMBean)) {
         throw new AssertionError("The scope of the associated MBean cannot be null and it must be an instance of either a DomainMBean or a ResourceGroupTemplateMBean");
      } else if (!(beanScope instanceof DomainMBean) && !(associatedBeanScope instanceof DomainMBean)) {
         if (beanScope instanceof ResourceGroupMBean && associatedBeanScope instanceof ResourceGroupMBean) {
            return beanScope.equals(associatedBeanScope);
         } else if (beanScope instanceof ResourceGroupTemplateMBean && !(beanScope instanceof ResourceGroupMBean)) {
            return beanScope.equals(associatedBeanScope);
         } else {
            ResourceGroupTemplateMBean beanRGT = ((ResourceGroupMBean)beanScope).getResourceGroupTemplate();
            return beanRGT != null && beanRGT.equals(associatedBeanScope);
         }
      } else {
         return beanScope instanceof DomainMBean && associatedBeanScope instanceof DomainMBean;
      }
   }

   private static boolean usesMigratableTarget(DeploymentMBean bean) {
      TargetMBean[] t = bean.getTargets();
      if (t != null && t.length != 0) {
         for(int inc = 0; inc < t.length; ++inc) {
            if (t[inc] instanceof MigratableTargetMBean) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean usesDynamicClusterTarget(DeploymentMBean bean) {
      TargetMBean[] t = bean.getTargets();
      if (t != null && t.length != 0) {
         if (bean.getTargets()[0] instanceof ClusterMBean) {
            ClusterMBean clusterMbean = (ClusterMBean)((ClusterMBean)bean.getTargets()[0]);
            return clusterMbean.getDynamicServers().getMaximumDynamicServerCount() > 0;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static int getConfigMajorVersion(DomainMBean domain) {
      if (domain == null) {
         return 9;
      } else {
         String version = domain.getConfigurationVersion();
         if (version != null && version.length() != 0) {
            int dot = version.indexOf(46);
            String major;
            if (dot <= 0) {
               major = version;
            } else {
               major = version.substring(0, dot);
            }

            try {
               return Integer.parseInt(major);
            } catch (NumberFormatException var5) {
               return 9;
            }
         } else {
            return 9;
         }
      }
   }

   public static void validateJMSServer(JMSServerMBean bean) throws IllegalArgumentException {
      if (bean.getParent() instanceof DomainMBean && getConfigMajorVersion((DomainMBean)bean.getParent()) >= 9 && bean.getStore() == null && bean.getPersistentStore() == null && bean.getStoreEnabled() && usesMigratableTarget(bean)) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSServerMigratableStore(bean.getName()));
      } else if (bean.getPersistentStore() != null && bean.getStoreEnabled() && !targetMatchStoreTarget(bean, bean.getPersistentStore())) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getStoreTargetMismatch(bean.getName()));
      } else {
         String rgGroupInfo = getRGGroupInfo(bean);
         String referringRGTMsg = getReferedRGTMsg(bean);
         if (bean.getPersistentStore() == null && bean.getStoreEnabled() && !(bean.getParent() instanceof DomainMBean)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getDefaultStoreInRGScopeError(bean.getName(), rgGroupInfo));
         } else if (bean.getPersistentStore() != null && bean.getStoreEnabled() && !isAssociatedMBeanScopeLegal(bean, bean.getPersistentStore())) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getServerScopeMismatch(bean.getName(), bean.getPersistentStore().getName(), rgGroupInfo, referringRGTMsg));
         } else if (bean.isHostingTemporaryDestinations() || bean.getTemporaryTemplateResource() == null && bean.getTemporaryTemplateName() == null) {
            if (bean.isHostingTemporaryDestinations()) {
               String resourceName = bean.getTemporaryTemplateResource();
               String templateName = bean.getTemporaryTemplateName();
               if (resourceName != null && templateName == null) {
                  throw new IllegalArgumentException("A temporary template resource was specified (" + resourceName + ") but no temporary template name was given");
               }

               if (templateName != null && resourceName == null) {
                  throw new IllegalArgumentException("A temporary template name was specified (" + templateName + ") but no temporary template resource was given");
               }

               if (templateName != null) {
                  DomainMBean domain = getDomain(bean);
                  JMSSystemResourceMBean resource = null;
                  WebLogicMBean parent = bean.getParent();
                  if (parent instanceof DomainMBean) {
                     resource = ((DomainMBean)parent).lookupJMSSystemResource(resourceName);
                  } else if (parent instanceof ResourceGroupMBean) {
                     ResourceGroupMBean rgBean = (ResourceGroupMBean)parent;
                     resource = rgBean.lookupJMSSystemResource(resourceName);
                     if (resource == null) {
                        ResourceGroupTemplateMBean associatedRGTBean = rgBean.getResourceGroupTemplate();
                        if (associatedRGTBean != null) {
                           resource = domain.lookupResourceGroupTemplate(associatedRGTBean.getName()).lookupJMSSystemResource(resourceName);
                        }
                     }
                  } else {
                     if (!(parent instanceof ResourceGroupTemplateMBean)) {
                        throw new IllegalArgumentException(ManagementConfigValidatorsTextFormatter.getInstance().getCheckValidParentForJmsServer(parent.getType(), bean.getName()));
                     }

                     resource = domain.lookupResourceGroupTemplate(parent.getName()).lookupJMSSystemResource(resourceName);
                  }

                  if (resource == null) {
                     throw new IllegalArgumentException(ConfigLogger.logNoTemporaryTemplateLoggable(bean.getName(), resourceName, templateName).getMessage());
                  }

                  JMSBean resourceBean = resource.getJMSResource();
                  if (resourceBean == null) {
                     return;
                  }

                  TemplateBean domainTemporaryTemplate = resourceBean.lookupTemplate(templateName);
                  if (domainTemporaryTemplate == null) {
                     throw new IllegalArgumentException("The JMSServer " + bean.getName() + " has a temporary resource of " + resourceName + " and a temporary template name of " + templateName + " but not template of that name can be found in the given resource");
                  }
               }
            }

         } else {
            throw new IllegalArgumentException("The JMSServer " + bean.getName() + " does not allow temporary destinations to be hosted, but  has either the temporary-template-resource or temporary-template-name defined");
         }
      }
   }

   public static void validateSAFAgentServiceTypeInRG(SAFAgentMBean bean, String type) throws IllegalArgumentException {
      if ("Receiving-only".equals(type) && (bean.getParent() instanceof ResourceGroupTemplateMBean || bean.getParent() instanceof ResourceGroupMBean)) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getReceivingSAFAgentInvlidForRG(bean.getName()));
      }
   }

   public static void validateSAFAgent(SAFAgentMBean bean) throws IllegalArgumentException {
      if (bean.getStore() == null && usesMigratableTarget(bean)) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSServerMigratableStore(bean.getName()));
      } else if (bean.getStore() != null && bean.getStore().getDistributionPolicy().equalsIgnoreCase("Singleton")) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSAFAgentWithStoreNotDistributed(bean.getName()));
      } else {
         String rgGroupInfo = getRGGroupInfo(bean);
         String referringRGTMsg = getReferedRGTMsg(bean);
         if (bean.getStore() == null && !(bean.getParent() instanceof DomainMBean)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getDefaultStoreInRGScopeError(bean.getName(), rgGroupInfo));
         } else if (bean.getStore() != null && !isAssociatedMBeanScopeLegal(bean, bean.getStore())) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getServerScopeMismatch(bean.getName(), bean.getStore().getName(), rgGroupInfo, referringRGTMsg));
         } else if (bean.getStore() != null && !targetMatchStoreTarget(bean, bean.getStore())) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getStoreTargetMismatch(bean.getName()));
         } else {
            if ("Receiving-only".equals(bean.getServiceType())) {
               if (bean.getTargets().length == 1 && bean.getTargets()[0] instanceof MigratableTargetMBean) {
                  throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getReceivingAgentInvlid4MT(bean.getName()));
               }

               if (bean.getParent() instanceof ResourceGroupTemplateMBean || bean.getParent() instanceof ResourceGroupMBean) {
                  throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getReceivingSAFAgentInvlidForRG(bean.getName()));
               }
            }

         }
      }
   }

   public static void validateServerBytesValues(JMSServerMBean bean) throws IllegalArgumentException {
      if (bean.getBytesMaximum() >= 0L && bean.getBytesThresholdHigh() > bean.getBytesMaximum()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSBytesMaxOverThreshold(bean.getName()));
      } else if (bean.getBytesThresholdHigh() >= 0L && bean.getBytesThresholdHigh() < bean.getBytesThresholdLow()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSBytesThresholdsReversed(bean.getName()));
      }
   }

   public static void validateServerBytesValues(SAFAgentMBean bean) throws IllegalArgumentException {
      if (bean.getBytesMaximum() >= 0L && bean.getBytesThresholdHigh() > bean.getBytesMaximum()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSBytesMaxOverThreshold(bean.getName()));
      } else if (bean.getBytesThresholdHigh() >= 0L && bean.getBytesThresholdHigh() < bean.getBytesThresholdLow()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSBytesThresholdsReversed(bean.getName()));
      }
   }

   public static void validateServerMessagesValues(JMSServerMBean bean) throws IllegalArgumentException {
      if (bean.getMessagesMaximum() >= 0L && bean.getMessagesThresholdHigh() > bean.getMessagesMaximum()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSMessagesMaxOverThreshold(bean.getName()));
      } else if (bean.getMessagesThresholdHigh() >= 0L && bean.getMessagesThresholdHigh() < bean.getMessagesThresholdLow()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSMessagesThresholdsReversed(bean.getName()));
      }
   }

   public static void validateServerMessagesValues(SAFAgentMBean bean) throws IllegalArgumentException {
      if (bean.getMessagesMaximum() >= 0L && bean.getMessagesThresholdHigh() > bean.getMessagesMaximum()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSMessagesMaxOverThreshold(bean.getName()));
      } else if (bean.getMessagesThresholdHigh() >= 0L && bean.getMessagesThresholdHigh() < bean.getMessagesThresholdLow()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSMessagesThresholdsReversed(bean.getName()));
      }
   }

   public static void validateSingleServerTargets(DeploymentMBean mbean, TargetMBean target) throws IllegalArgumentException {
      if (target != null) {
         if (!(target instanceof ServerMBean) && !(target instanceof ClusterMBean) && !(target instanceof MigratableTargetMBean)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSingleTargetRequired(mbean.getName(), mbean.getType(), "[" + target.getName() + "]"));
         }
      }
   }

   public static void validateSingleServerTargets(DeploymentMBean mbean, TargetMBean[] targets) throws IllegalArgumentException {
      if (targets != null && targets.length != 0) {
         if (targets.length > 1) {
            StringBuffer buf = new StringBuffer("[");

            for(int i = 0; i < targets.length; ++i) {
               TargetMBean target = targets[i];
               buf.append(target.getName());
               if (i < targets.length - 1) {
                  buf.append(",");
               }
            }

            buf.append("]");
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSingleTargetRequired(mbean.getName(), mbean.getType(), buf.toString()));
         } else {
            validateSingleServerTargets(mbean, targets[0]);
         }
      }
   }

   public static void validateJMSServerTargets(DomainMBean domainBean) throws IllegalArgumentException {
      JMSServerMBean[] jmsServers = domainBean.getJMSServers();
      JMSServerMBean[] var2 = jmsServers;
      int var3 = jmsServers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JMSServerMBean jmsServer = var2[var4];
         TargetMBean[] targets = jmsServer.getTargets();
         validateSingleServerTargets(jmsServer, (TargetMBean[])targets);
      }

   }

   public static void validateStoreTargets(DomainMBean domainBean) throws IllegalArgumentException {
      PersistentStoreMBean[] stores = domainBean.getFileStores();
      FileStoreMBean[] var2 = stores;
      int var3 = stores.length;

      int inc;
      TargetMBean[] targets;
      for(inc = 0; inc < var3; ++inc) {
         PersistentStoreMBean store = var2[inc];
         targets = store.getTargets();
         validateSingleServerTargets(store, (TargetMBean[])targets);
      }

      PersistentStoreMBean[] stores = domainBean.getJDBCStores();
      JDBCStoreMBean[] var12 = stores;
      var3 = stores.length;

      for(inc = 0; inc < var3; ++inc) {
         PersistentStoreMBean store = var12[inc];
         targets = store.getTargets();
         validateSingleServerTargets(store, (TargetMBean[])targets);
      }

      PersistentStoreMBean[] stores = domainBean.getReplicatedStores();
      ReplicatedStoreMBean[] var13 = stores;
      var3 = stores.length;

      for(inc = 0; inc < var3; ++inc) {
         PersistentStoreMBean store = var13[inc];
         targets = store.getTargets();
         validateSingleServerTargets(store, (TargetMBean[])targets);
      }

      ArrayList jmsServerStores = new ArrayList();
      JMSServerMBean[] jmsServers = domainBean.getJMSServers();

      for(inc = 0; inc < jmsServers.length; ++inc) {
         JMSServerMBean jmsServer = jmsServers[inc];
         PersistentStoreMBean store = jmsServer.getPersistentStore();
         if (store != null) {
            if (!targetMatchStoreTarget(jmsServer, store)) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getStoreTargetMismatch(jmsServer.getName()));
            }

            jmsServerStores.add(store);
         }
      }

      ArrayList safAgentStores = new ArrayList();
      SAFAgentMBean[] safAgents = domainBean.getSAFAgents();

      for(int inc = 0; inc < safAgents.length; ++inc) {
         SAFAgentMBean agent = safAgents[inc];
         PersistentStoreMBean store = agent.getStore();
         if (store != null) {
            if (!targetMatchStoreTarget(agent, store)) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getStoreTargetMismatch(agent.getName()));
            }

            safAgentStores.add(store);
         }
      }

      PathServiceMBean[] paths = domainBean.getPathServices();

      for(int inc = 0; inc < paths.length; ++inc) {
         PathServiceMBean pathService = paths[inc];
         PersistentStoreMBean store = pathService.getPersistentStore();
         if (store != null && !targetMatchStoreTarget(pathService, store)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getPathServiceStoreTargetMismatch(pathService.getName()));
         }

         if (isClustered(pathService) && (jmsServerStores.contains(store) || safAgentStores.contains(store))) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getPathServiceStoreCannotBeShared(pathService.getName(), store.getName()));
         }
      }

   }

   public static boolean isClustered(DeploymentMBean mbean) {
      TargetMBean[] targets = mbean.getTargets();
      if (targets != null && targets.length != 0 && targets[0] != null) {
         return targets[0] instanceof ClusterMBean;
      } else {
         return false;
      }
   }

   public static void validateStoreParams(DomainMBean domainBean) throws IllegalArgumentException {
      FileStoreMBean[] fileStores = domainBean.getFileStores();

      for(int inc = 0; inc < fileStores.length; ++inc) {
         FileStoreMBean fileStore = fileStores[inc];
         TargetMBean[] targets = fileStore.getTargets();
         if (targets.length != 0) {
            TargetMBean target = fileStore.getTargets()[0];
            if (target != null && target instanceof MigratableTargetMBean && (fileStore.getDirectory() == null || fileStore.getDirectory().trim().equals(""))) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getMigratableFileStoreDirectoryMissing(fileStore.getName(), target.getName()));
            }

            validateHAPolicies(fileStore, target);
         }

         validateStoreConfig(fileStore);
      }

      ReplicatedStoreMBean[] replicatedStores = domainBean.getReplicatedStores();

      for(int inc = 0; inc < replicatedStores.length; ++inc) {
         ReplicatedStoreMBean replicatedStore = replicatedStores[inc];
         TargetMBean[] targets = replicatedStore.getTargets();
         if (targets.length != 0) {
            TargetMBean target = replicatedStore.getTargets()[0];
            if (target != null && target instanceof MigratableTargetMBean && (replicatedStore.getDirectory() == null || replicatedStore.getDirectory().trim().equals(""))) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getMigratableFileStoreDirectoryMissing(replicatedStore.getName(), target.getName()));
            }

            validateHAPolicies(replicatedStore, target);
         }

         validateReplicatedStoreConfig(replicatedStore);
      }

      JDBCStoreMBean[] jdbcStores = domainBean.getJDBCStores();

      for(int inc = 0; inc < jdbcStores.length; ++inc) {
         JDBCStoreMBean jdbcStore = jdbcStores[inc];
         TargetMBean[] targets = jdbcStore.getTargets();
         if (targets.length != 0) {
            validateHAPolicies(jdbcStore, targets[0]);
         }
      }

      ServerMBean[] servers = domainBean.getServers();
      ServerMBean[] var18 = servers;
      int var21 = servers.length;

      int var7;
      for(var7 = 0; var7 < var21; ++var7) {
         ServerMBean server = var18[var7];
         validateStoreConfig(server.getDefaultFileStore());
         validateDiagnosticStoreConfig(server.getServerDiagnosticConfig());
      }

      JMSServerMBean[] jmsServers = domainBean.getJMSServers();
      JMSServerMBean[] var22 = jmsServers;
      var7 = jmsServers.length;

      int var24;
      for(var24 = 0; var24 < var7; ++var24) {
         JMSServerMBean jms = var22[var24];
         validatePagingConfig(jms);
      }

      ResourceGroupTemplateMBean[] var23 = domainBean.getResourceGroupTemplates();
      var7 = var23.length;

      for(var24 = 0; var24 < var7; ++var24) {
         ResourceGroupTemplateMBean rgtBean = var23[var24];
         validatePagingConfigInRG(rgtBean.getJMSServers());
      }

   }

   public static void validateHAPolicies(DynamicDeploymentMBean ddmbean, TargetMBean target) throws IllegalArgumentException {
      if (target instanceof ClusterMBean) {
         if (!ddmbean.getMigrationPolicy().equals("Off")) {
            String basisType = ((ClusterMBean)target).getMigrationBasis();
            if ("database".equals(basisType) && ((ClusterMBean)target).getDataSourceForAutomaticMigration() == null) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotEnableAutoMigrationWithoutLeasing(ddmbean.getType(), ddmbean.getName(), ddmbean.getMigrationPolicy()));
            }

            ServerMBean[] servers = ((ClusterMBean)target).getServers();
            if ("consensus".equals(basisType)) {
               for(int j = 0; j < servers.length; ++j) {
                  MachineMBean machine = servers[j].getMachine();
                  if (machine == null) {
                     throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotEnableAutoMigrationWithoutLeasing(ddmbean.getType(), ddmbean.getName(), ddmbean.getMigrationPolicy()));
                  }

                  if (machine.getNodeManager() == null) {
                     throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotEnableAutoMigrationWithoutLeasing(ddmbean.getType(), ddmbean.getName(), ddmbean.getMigrationPolicy()));
                  }
               }
            }
         }

         if (ddmbean.getDistributionPolicy().equals("Singleton") && !ddmbean.getMigrationPolicy().equals("On-Failure") && !ddmbean.getMigrationPolicy().equals("Always")) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getInvalidStoreHAPolicies(ddmbean.getType(), ddmbean.getName(), ddmbean.getDistributionPolicy(), ddmbean.getMigrationPolicy()));
         }

         if (ddmbean instanceof MessagingBridgeMBean && ddmbean.getMigrationPolicy().equals("Always")) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getAlwaysMigrationPolicyIsNotAllowedForBridges(ddmbean.getName()));
         }
      }

   }

   private static void validateHAPoliciesForCluster(DynamicDeploymentMBean ddmbean, ClusterMBean target) throws IllegalArgumentException {
      if (!ddmbean.getMigrationPolicy().equals("Off")) {
         String basisType = target.getMigrationBasis();
         if ("database".equals(basisType)) {
            JDBCSystemResourceMBean dataSource = target.getDataSourceForAutomaticMigration();
            if (dataSource == null) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotEnableAutoMigrationWithoutLeasing(ddmbean.getType(), ddmbean.getName(), ddmbean.getMigrationPolicy()));
            }
         }

         ServerMBean[] servers = target.getServers();
         if ("consensus".equals(basisType)) {
            for(int j = 0; j < servers.length; ++j) {
               MachineMBean machine = servers[j].getMachine();
               if (machine == null) {
                  throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotEnableAutoMigrationWithoutLeasing(ddmbean.getType(), ddmbean.getName(), ddmbean.getMigrationPolicy()));
               }

               if (machine.getNodeManager() == null) {
                  throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotEnableAutoMigrationWithoutLeasing(ddmbean.getType(), ddmbean.getName(), ddmbean.getMigrationPolicy()));
               }
            }
         }
      }

   }

   private static void invalidNumberRange(Object val, String name, Object fakeDef, Object realMin, Object realMax) {
      throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getInvalidNumberRange(String.valueOf(val), name, String.valueOf(fakeDef), String.valueOf(realMin), String.valueOf(realMax)));
   }

   private static void validateStoreTuning(int minWin, int maxWin, int ioSize, long initSize, int blockSize) {
      if (minWin != -1 && minWin < 65536) {
         invalidNumberRange(minWin, "MinWindowBufferSize", -1, 65536, 1073741824);
      }

      if (maxWin != -1 && (maxWin < 65536 || maxWin < minWin)) {
         Object minWinObj = minWin == -1 ? 65536 : "MinWindowBufferSize";
         invalidNumberRange(maxWin, "MaxWindowBufferSize", -1, minWinObj, 1073741824);
      }

      if (ioSize != -1 && ioSize < 1048576) {
         invalidNumberRange(ioSize, "IoBufferSize", -1, 1048576, 67108864);
      }

      if (initSize != 0L && initSize < 1048576L) {
         invalidNumberRange(initSize, "InitialSize", 0, 1048576, 4503599627370496L);
      }

      if (blockSize != -1 && blockSize < 512) {
         invalidNumberRange(blockSize, "BlockSize", -1, 512, 8192);
      }

   }

   private static void validateDiagnosticStoreConfig(WLDFServerDiagnosticMBean bean) throws IllegalArgumentException {
      int minWin = bean.getDiagnosticStoreMinWindowBufferSize();
      int maxWin = bean.getDiagnosticStoreMaxWindowBufferSize();
      int ioSize = bean.getDiagnosticStoreIoBufferSize();
      int blockSize = bean.getDiagnosticStoreBlockSize();
      validateStoreTuning(minWin, maxWin, ioSize, 0L, blockSize);
   }

   private static void validatePagingConfig(JMSServerMBean bean) throws IllegalArgumentException {
      int minWin = bean.getPagingMinWindowBufferSize();
      int maxWin = bean.getPagingMaxWindowBufferSize();
      int ioSize = bean.getPagingIoBufferSize();
      int blockSize = bean.getPagingBlockSize();
      validateStoreTuning(minWin, maxWin, ioSize, 0L, blockSize);
   }

   private static void validatePagingConfigInRG(JMSServerMBean[] jmsServers) throws IllegalArgumentException {
      JMSServerMBean[] var1 = jmsServers;
      int var2 = jmsServers.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         JMSServerMBean aJmsServer = var1[var3];

         try {
            validatePagingConfig(aJmsServer);
         } catch (IllegalArgumentException var6) {
            throw new IllegalArgumentException(var6.getMessage());
         }
      }

   }

   private static void validateStoreConfig(GenericFileStoreMBean bean) throws IllegalArgumentException {
      int minWin = bean.getMinWindowBufferSize();
      int maxWin = bean.getMaxWindowBufferSize();
      int ioSize = bean.getIoBufferSize();
      long initSize = bean.getInitialSize();
      int blockSize = bean.getBlockSize();
      validateStoreTuning(minWin, maxWin, ioSize, initSize, blockSize);
   }

   private static void validateReplicatedStoreConfig(ReplicatedStoreMBean bean) throws IllegalArgumentException {
   }

   public static void validateJDBCPrefix(String value) throws IllegalArgumentException {
      if (value != null && value.length() != 0) {
         if (value.equalsIgnoreCase("jmsmsg") || value.equalsIgnoreCase("jmsstore") || value.equalsIgnoreCase("jmsstate")) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getIllegalJMSJDBCPrefix());
         }
      }
   }

   public static void validateRetryBaseAndMax(SAFAgentMBean bean) {
      if (bean.getDefaultRetryDelayMultiplier() > 1.0 && bean.getDefaultRetryDelayBase() > bean.getDefaultRetryDelayMaximum()) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getIllegalRetryDelayBaseAndMax(bean.getName(), bean.getDefaultRetryDelayBase(), bean.getDefaultRetryDelayMaximum()));
      }
   }

   public static void validateSAFAgentTargets(TargetMBean[] targets) throws IllegalArgumentException {
      if (targets != null && targets.length != 0) {
         for(int i = 0; i < targets.length; ++i) {
            TargetMBean target = targets[i];
            if (!(target instanceof ServerMBean) && !(target instanceof ClusterMBean) && !(target instanceof MigratableTargetMBean)) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getIllegalSAFAgentTargets(target.getType()));
            }

            if (target instanceof MigratableTargetMBean && targets.length > 1) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getIllegalSAFAgentMigratableTargets());
            }
         }

      }
   }

   public static void validateAcknowledgeIntervalValue(long value) throws IllegalArgumentException {
      if (value <= 0L && value != -1L) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getAcknowledgeIntervalNotValid(value));
      }
   }

   public static void validateSpaceUsageSettings(ReplicatedStoreMBean bean) throws IllegalArgumentException {
      try {
         validateSpaceSettings(bean.getName(), bean.getSpaceLoggingStartPercent(), bean.getSpaceOverloadYellowPercent(), bean.getSpaceOverloadRedPercent());
      } catch (Exception var2) {
         throw new IllegalArgumentException(var2.getMessage());
      }
   }

   public static void validateSpaceSettings(String name, int start, int yellow, int red) throws Exception {
      if (start >= 0 && (start > yellow || start > red)) {
         throw new Exception(ManagementTextTextFormatter.getInstance().getJMSSpaceOverloadLoggingPercentReversed(name));
      } else if (yellow >= 0 && (yellow < start || yellow > red)) {
         throw new Exception(ManagementTextTextFormatter.getInstance().getJMSSpaceOverloadLoggingPercentReversed(name));
      } else if (red >= 0 && (red < start || red < yellow)) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJMSSpaceOverloadLoggingPercentReversed(name));
      }
   }

   public static void validatePathServiceStores(DomainMBean domainBean) throws IllegalArgumentException {
      PathServiceMBean[] paths = domainBean.getPathServices();

      for(int inc = 0; inc < paths.length; ++inc) {
         PathServiceMBean pathService = paths[inc];
         TargetMBean[] targets = pathService.getTargets();
         if (targets.length != 0) {
            TargetMBean target = pathService.getTargets()[0];
            if (target != null && target instanceof ClusterMBean) {
               PersistentStoreMBean store = pathService.getPersistentStore();
               if (store != null) {
                  if (!store.getDistributionPolicy().equals("Singleton")) {
                     throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getPathServiceStoreInvalidDistributionPolicy(pathService.getName()));
                  }

                  if (!store.getMigrationPolicy().equals("Always")) {
                     throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getPathServiceStoreInvalidMigrationPolicy(pathService.getName()));
                  }
               }
            }
         }
      }

   }

   public void validate(DomainMBean domain) {
      validateStoreTargets(domain);
      validateStoreParams(domain);
      validatePathServiceStores(domain);
      validateJMSServerTargets(domain);
   }

   public static void validateJDBCStore(JDBCStoreMBean storeMBean) throws IllegalArgumentException {
      WebLogicMBean parent = storeMBean.getParent();
      WebLogicMBean checkBean = storeMBean;
      validateCustomStoreMigrationPolicy(storeMBean);
      if (!(parent instanceof DomainMBean) && !(parent instanceof ResourceGroupTemplateMBean)) {
         checkBean = parent;
      }

      WebLogicMBean dataSourceBean = storeMBean.getDataSource();
      if (dataSourceBean != null) {
         String rgGroupInfo = getRGGroupInfo((WebLogicMBean)checkBean);
         String referringRGTMsg = getReferedRGTMsg((WebLogicMBean)checkBean);
         if (!isAssociatedMBeanScopeLegal((WebLogicMBean)checkBean, dataSourceBean)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getJDBCStoreScopeMismatch(storeMBean.getName(), dataSourceBean.getName(), rgGroupInfo, referringRGTMsg));
         }
      }
   }

   public static void validatePathService(PathServiceMBean pathService) {
      String rgGroupInfo = getRGGroupInfo(pathService);
      String referringRGTMsg = getReferedRGTMsg(pathService);
      PersistentStoreMBean store = pathService.getPersistentStore();
      if (store == null && !(pathService.getParent() instanceof DomainMBean)) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getDefaultStoreInPathServiceInRGError(pathService.getName(), rgGroupInfo));
      } else {
         if (store != null && pathService.getParent() instanceof ResourceGroupTemplateMBean) {
            if (!store.getDistributionPolicy().equals("Singleton")) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getPathServiceStoreInvalidDistributionPolicyInRG(pathService.getName(), rgGroupInfo));
            }

            if (!store.getMigrationPolicy().equals("Always")) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getPathServiceStoreInvalidMigrationPolicyInRG(pathService.getName(), rgGroupInfo));
            }
         }

         if (store != null && !isAssociatedMBeanScopeLegal(pathService, store)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getPathServiceScopeMismatch(pathService.getName(), store.getName(), rgGroupInfo, referringRGTMsg));
         }
      }
   }

   public static DomainMBean getDomain(WebLogicMBean basic) throws IllegalArgumentException {
      DescriptorBean db = (DescriptorBean)basic;
      DescriptorBean parent = db.getDescriptor().getRootBean();
      if (!(parent instanceof DomainMBean)) {
         throw new IllegalArgumentException("could not get DomainMbean from " + basic.getName() + ".  My root has type " + parent.getClass().getName());
      } else {
         return (DomainMBean)parent;
      }
   }

   public static void validateSubDeploymentTargets(SubDeploymentMBean subDeploymentMBean) {
      TargetMBean[] targets = subDeploymentMBean.getTargets();
      boolean isSAFAgentPresent = false;
      boolean isJMSServerPresent = false;
      WebLogicMBean subDScopeBean = null;
      if (targets != null && targets.length != 0) {
         if (subDeploymentMBean.getParent().getParent() instanceof AppDeploymentMBean) {
            subDScopeBean = subDeploymentMBean.getParent().getParent();
         } else {
            subDScopeBean = subDeploymentMBean.getParent();
         }

         if (subDeploymentMBean.getParent().getParent() instanceof ResourceGroupTemplateMBean) {
            if (targets.length > 1) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSubdepInRGTargetRuleError(subDeploymentMBean.getName(), subDeploymentMBean.getParent().getName()));
            }

            if (targets[0] instanceof ServerMBean || targets[0] instanceof ClusterMBean) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSubdepInRGTargetRuleError(subDeploymentMBean.getName(), subDeploymentMBean.getParent().getName()));
            }

            if (!(targets[0] instanceof JMSServerMBean) && !(targets[0] instanceof SAFAgentMBean)) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSubdepInRGTargetRuleError(subDeploymentMBean.getName(), subDeploymentMBean.getParent().getName()));
            }
         }

         if (subDScopeBean.getParent() instanceof DomainMBean && targets.length > 1) {
            TargetMBean[] var5 = targets;
            int var6 = targets.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               TargetMBean target = var5[var7];
               if (target instanceof JMSServerMBean && ((JMSServerMBean)target).getPersistentStore() != null && ((JMSServerMBean)target).getPersistentStore().getDistributionPolicy().equalsIgnoreCase("Singleton")) {
                  throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSubdepJMSServerDPValidation(subDeploymentMBean.getName(), subDeploymentMBean.getParent().getName()));
               }
            }
         }

         if (!isAssociatedMBeanScopeLegal(subDScopeBean, targets[0])) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSubDepToTargetScopeMismatch(subDeploymentMBean.getName(), subDeploymentMBean.getParent().getName(), targets[0].getName()));
         }
      }
   }

   public static void validatePersistentStore(FileStoreMBean bean) {
      validateCustomStoreMigrationPolicy(bean);
   }

   public static void validateWorkerCount(JDBCStoreMBean bean, int workerCount) {
      String connectionCachingPolicy = bean.getConnectionCachingPolicy();
      if (workerCount > 1 && connectionCachingPolicy.equalsIgnoreCase("NONE")) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getConnectionCachingWorkerCountValidation(bean.getName(), "NONE", "1"));
      }
   }

   public static void validateConnectionCachePolicy(JDBCStoreMBean bean, String connectionCachingPolicy) {
      int workerCount = bean.getWorkerCount();
      if (workerCount > 1 && connectionCachingPolicy.equalsIgnoreCase("NONE")) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getConnectionCachingWorkerCountValidation(bean.getName(), "NONE", "1"));
      }
   }

   private static void validateCustomStoreMigrationPolicy(PersistentStoreMBean bean) {
      String distributionPolicy = bean.getDistributionPolicy();
      String migrationPolicy = bean.getMigrationPolicy();
      if (distributionPolicy.equals("Singleton") && !migrationPolicy.equals("On-Failure") && !migrationPolicy.equals("Always")) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCustomStoreMigrationPolicyUnsupported(bean.getName(), "On-Failure", "Always"));
      }
   }

   private static void validateStoresInRG(ResourceGroupMBean resourceGroup, PersistentStoreMBean[] stores) {
      TargetMBean[] rgTargets = resourceGroup.findEffectiveTargets();
      PersistentStoreMBean[] var3 = stores;
      int var4 = stores.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PersistentStoreMBean aRGFileStore = var3[var5];
         TargetMBean vtTarget = getOneTargetFromVT(rgTargets);
         if (vtTarget instanceof ClusterMBean) {
            validateHAPoliciesForCluster(aRGFileStore, (ClusterMBean)vtTarget);
         }

         if (aRGFileStore instanceof FileStoreMBean) {
            try {
               validateStoreConfig((FileStoreMBean)aRGFileStore);
            } catch (IllegalArgumentException var9) {
               throw new IllegalArgumentException(var9.getMessage());
            }
         }
      }

   }

   private static void validateBridgesInRG(ResourceGroupMBean resourceGroup, MessagingBridgeMBean[] bridges) {
      TargetMBean[] rgTargets = resourceGroup.findEffectiveTargets();
      MessagingBridgeMBean[] var3 = bridges;
      int var4 = bridges.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MessagingBridgeMBean bridgeMBean = var3[var5];
         TargetMBean[] var7 = rgTargets;
         int var8 = rgTargets.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            TargetMBean aRGTarget = var7[var9];
            if (aRGTarget instanceof VirtualTargetMBean) {
               TargetMBean[] vtTargets = ((VirtualTargetMBean)aRGTarget).getTargets();
               if (vtTargets != null && vtTargets.length != 0) {
                  validateHAPolicies(bridgeMBean, vtTargets[0]);
               }
            }
         }
      }

   }

   private static TargetMBean getOneTargetFromVT(TargetMBean[] rgTargets) {
      TargetMBean[] var1 = rgTargets;
      int var2 = rgTargets.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         TargetMBean aRGTarget = var1[var3];
         if (!(aRGTarget instanceof VirtualTargetMBean)) {
            return null;
         }

         TargetMBean[] vtTargets = ((VirtualTargetMBean)aRGTarget).getTargets();
         if (vtTargets.length > 1) {
            throw new AssertionError("A virtual target used to target a JMS persistent store, can only target a single server or a cluster.");
         }

         if (vtTargets.length == 1) {
            return vtTargets[0];
         }
      }

      return null;
   }

   private static String getRGGroupInfo(WebLogicMBean bean) {
      String rgGroupInfo = "";
      if (bean != null) {
         if (bean.getParent() instanceof DomainMBean) {
            rgGroupInfo = "\" Domain Scope \"";
         } else {
            rgGroupInfo = " Resource Group Template \"" + bean.getParent().getName() + "\"";
            if (bean.getParent() instanceof ResourceGroupMBean) {
               rgGroupInfo = " Resource Group \"" + bean.getParent().getName() + "\"";
            }
         }
      }

      return rgGroupInfo;
   }

   private static String getReferedRGTMsg(WebLogicMBean bean) {
      String referringRGTMsg = "";
      if (bean != null && bean.getParent() instanceof ResourceGroupMBean) {
         ResourceGroupTemplateMBean referredBeanbyRG = ((ResourceGroupMBean)bean.getParent()).getResourceGroupTemplate();
         if (referredBeanbyRG != null) {
            referringRGTMsg = " or under Resource Group Template \"" + referredBeanbyRG.getName() + "\"";
         }
      }

      return referringRGTMsg;
   }
}
