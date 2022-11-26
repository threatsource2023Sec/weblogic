package weblogic.cluster.migration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.UpdateException;

public class MigratableGroupConfigManager implements ConfigurationProcessor {
   private final RuntimeAccess runtimeAccess;
   private static DebugLogger dynLogger = DebugLogger.getDebugLogger("DebugDynamicSingletonServices");
   private final Map configs = new HashMap();

   protected MigratableGroupConfigManager(RuntimeAccess runtimeAccess) {
      this.runtimeAccess = runtimeAccess;
   }

   protected DomainMBean getLocalDomain() {
      return this.runtimeAccess.getDomain();
   }

   protected String getLocalServerName() {
      return this.runtimeAccess.getServer().getName();
   }

   protected boolean isAdminServer() {
      return this.runtimeAccess.isAdminServer();
   }

   protected MigratableTargetMBean findOrCreateMigratableTargetMBean(String partitionName, MigratableGroupConfig config) throws MigrationException, IllegalArgumentException {
      try {
         return this.findOrCreateMigratableTargetMBean(partitionName, config, this.getLocalDomain(), this.isAdminServer(), this.getLocalServerName());
      } catch (InvalidAttributeValueException var4) {
         throw new MigrationException(var4);
      } catch (DistributedManagementException var5) {
         throw new MigrationException(var5);
      }
   }

   static void validateConfig(String partitionName, MigratableGroupConfig config, DomainMBean domain, boolean isAdminServer, String localServer) throws IllegalArgumentException {
      String configName = config.getName();
      if (configName != null && !configName.trim().isEmpty()) {
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, configName);
         MigratableTargetMBean migTarget = domain.lookupMigratableTarget(targetName);
         if (migTarget != null && !migTarget.isDynamicallyCreated()) {
            throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " is invalid: duplicate name with a statistically configured MigratableTargetMBean");
         } else {
            String upsName;
            if (config.isManualService()) {
               if (config.getUserPreferredServer() == null) {
                  throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " is invalid: UserPreferredServer must not be null");
               }

               upsName = config.getUserPreferredServer().getName();
               if (domain.lookupServer(upsName) == null) {
                  throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " is invalid: UserPreferredServer " + upsName + " does not exist in domain");
               }
            } else if (config.getUserPreferredServer() == null) {
               return;
            }

            upsName = config.getUserPreferredServer().getName();
            ClusterMBean upsCluster = config.getUserPreferredServer().getCluster();
            if (upsCluster == null) {
               throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " is invalid: UserPreferredServer " + upsName + " must in a cluster");
            } else {
               String upsClusterName = upsCluster.getName();
               if (domain.lookupCluster(upsClusterName) == null) {
                  throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " is invalid: UserPreferredServer " + upsName + " must in a valid cluster; its cluster " + upsClusterName + " does not exist in domain");
               } else {
                  ServerMBean localServerMBean = domain.lookupServer(localServer);
                  if (localServerMBean == null) {
                     throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " is invalid: localServer " + localServer + " does not exist in domain");
                  } else if (localServerMBean.getCluster() == null) {
                     throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " can not register on local server " + localServerMBean.getName() + "; it must register on server in a cluster");
                  } else if (!localServerMBean.getCluster().getName().equals(upsClusterName)) {
                     throw new IllegalArgumentException("MigratableGroupConfig " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " can not register on local server " + localServerMBean.getName() + " which in cluster " + localServerMBean.getCluster().getName() + "; its UserPreferredServer " + upsName + " belongs to different cluster " + upsClusterName);
                  }
               }
            }
         }
      } else {
         throw new IllegalArgumentException("MigratableGroupConfig " + config + PartitionAwareObject.getMessageInPartition(partitionName) + " is invalid: must have a valid name");
      }
   }

   private synchronized MigratableTargetMBean findOrCreateMigratableTargetMBean(String partitionName, MigratableGroupConfig config, DomainMBean domain, boolean isAdminServer, String localServer) throws InvalidAttributeValueException, DistributedManagementException, IllegalArgumentException {
      String configName = config.getName();
      String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, configName);
      MigratableTargetMBean migTarget = domain.lookupMigratableTarget(targetName);
      if (migTarget != null) {
         if (dynLogger.isDebugEnabled()) {
            p("findOrCreateMigratableTargetMBean: processing " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + ": find existing MT: " + migTarget);
         }

         if ("manual".equals(migTarget.getMigrationPolicy()) && !config.isManualService()) {
            throw new IllegalArgumentException("Service " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " already has been registered as manual service; can not register as automatic service again.");
         } else if (!"manual".equals(migTarget.getMigrationPolicy()) && config.isManualService()) {
            throw new IllegalArgumentException("Service " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " already has been registered as automatic service; can not register as manual service again.");
         } else {
            return migTarget;
         }
      } else {
         validateConfig(partitionName, config, domain, isAdminServer, localServer);
         migTarget = domain.createMigratableTarget(targetName);
         if (dynLogger.isDebugEnabled()) {
            p("findOrCreateMigratableTargetMBean: processing " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + ": created new MT: " + migTarget);
         }

         try {
            ((AbstractDescriptorBean)migTarget)._setTransient(true);
            migTarget.setCluster(domain.lookupServer(localServer).getCluster());
            migTarget.setNotes("This is a system generated migratable target based on MigratableGroupConfig. Do not delete manually.");
            if (config.isManualService()) {
               String upsServerName = config.getUserPreferredServer().getName();
               ServerMBean upsServer = domain.lookupServer(upsServerName);
               migTarget.setUserPreferredServer(upsServer);
               migTarget.setMigrationPolicy("manual");
               migTarget.setRestartOnFailure(config.getRestartOnFailure());
               migTarget.setSecondsBetweenRestarts(config.getSecondsBetweenRestarts());
               migTarget.setNumberOfRestartAttempts(config.getNumberOfRestartAttempts());
               migTarget.setCritical(config.isCritical());
            } else {
               migTarget.setMigrationPolicy("exactly-once");
            }

            if (config.getPreScript() != null) {
               migTarget.setPreScript(config.getPreScript());
            }

            if (config.getPostScript() != null) {
               migTarget.setPostScript(config.getPostScript());
            }

            migTarget.setPostScriptFailureFatal(config.isPostScriptFailureFatal());
            migTarget.setNonLocalPostAllowed(config.isNonLocalPostAllowed());
         } catch (Throwable var11) {
            domain.destroyMigratableTarget(migTarget);
            if (dynLogger.isDebugEnabled()) {
               p("findOrCreateMigratableTargetMBean: processing " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + ": destroyed MT: " + migTarget + "; due to " + var11);
            }

            throw var11;
         }

         this.configs.put(targetName, config);
         return migTarget;
      }
   }

   protected synchronized MigratableGroupConfig findMigratableGroupConfig(String partitionName, String configName) {
      return (MigratableGroupConfig)this.configs.get(PartitionAwareObject.qualifyPartitionInfo(partitionName, configName));
   }

   protected synchronized MigratableGroupConfig findMigratableGroupConfig(String targetName) {
      return (MigratableGroupConfig)this.configs.get(targetName);
   }

   public synchronized void updateConfiguration(DomainMBean root) throws UpdateException {
      Iterator var2 = this.configs.keySet().iterator();

      while(var2.hasNext()) {
         String targetName = (String)var2.next();
         String partitionName = PartitionAwareObject.extractPartitionName(targetName);
         MigratableGroupConfig config = (MigratableGroupConfig)this.configs.get(targetName);

         try {
            if (dynLogger.isDebugEnabled()) {
               p("updateConfiguration: processing " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName));
            }

            this.findOrCreateMigratableTargetMBean(partitionName, config, root, this.isAdminServer(), this.getLocalServerName());
         } catch (Throwable var7) {
            var7.printStackTrace();
            throw new UpdateException(var7);
         }
      }

   }

   protected synchronized void unregisterMigratableGroupConfig(String partitionName, String configName) throws MigrationException {
      String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, configName);
      if (this.configs.remove(targetName) == null) {
         if (dynLogger.isDebugEnabled()) {
            p("unregisterMigratableGroupConfig: unknown config: " + configName + PartitionAwareObject.getMessageInPartition(partitionName));
         }

      } else {
         if (dynLogger.isDebugEnabled()) {
            p("unregisterMigratableGroupConfig: removed config from map: " + configName + PartitionAwareObject.getMessageInPartition(partitionName));
         }

         MigratableTargetMBean mtmbean = this.getLocalDomain().lookupMigratableTarget(targetName);
         if (mtmbean != null) {
            this.getLocalDomain().destroyMigratableTarget(mtmbean);
            if (dynLogger.isDebugEnabled()) {
               p("unregisterMigratableGroupConfig: removed MigratableTargetMBean from domain: " + configName + PartitionAwareObject.getMessageInPartition(partitionName));
            }

         }
      }
   }

   private static void p(Object msg) {
      dynLogger.debug("[MigratableGroupConfigManager] " + msg);
   }
}
