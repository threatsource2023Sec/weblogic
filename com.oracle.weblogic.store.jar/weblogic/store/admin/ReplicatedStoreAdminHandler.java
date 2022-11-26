package weblogic.store.admin;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import weblogic.kernel.KernelStatus;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.ReplicatedStoreMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.PersistentStoreException;
import weblogic.store.RuntimeHandler;
import weblogic.store.StoreLogger;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.file.ReplicatedStoreIO;
import weblogic.store.io.file.StoreDir;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.store.xa.internal.PersistentStoreXAImpl;

public class ReplicatedStoreAdminHandler extends AdminHandler {
   public static final int DEFAULT_BUSY_WAIT_MICRO_SECONDS = 100;
   public static final int DEFAULT_SLEEP_WAIT_MILLI_SECONDS = 1000;
   public static final String DEFAULT_CONFIG_FILE_NAME = "rs_daemons.cfg";
   private GenericBeanListener listener;
   private String directoryName;
   private static final HashMap changeableAttributes = new HashMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static volatile boolean alreadyLoggedDeprecated = false;

   public void prepare(GenericManagedDeployment deployment) throws DeploymentException {
      super.prepare(deployment);
      ReplicatedStoreMBean replicatedStoreBean = (ReplicatedStoreMBean)deployment.getMBean();
      if (!alreadyLoggedDeprecated) {
         StoreLogger.logReplicatedStoreDeprecated();
         alreadyLoggedDeprecated = true;
      }

      this.config = new HashMap();
      String serverName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      this.directoryName = replicatedStoreBean.getDirectory();
      if (this.directoryName != null && this.directoryName.length() != 0) {
         File storeDir = new File(this.directoryName);
         if (!storeDir.isAbsolute() && !storeDir.getPath().startsWith(File.separator)) {
            throw new DeploymentException("Directory must be an absolute path");
         } else {
            String configFileName = this.directoryName + File.separator + "rs_daemons.cfg";
            if (!(new File(configFileName)).exists()) {
               throw new DeploymentException("Daemon config file rs_daemons.cfg does not exist in global directory: [" + this.directoryName + "]");
            } else {
               this.config.put("StoreConfigName", this.getConfiguredName());
               this.config.put("ConfigFileName", configFileName);
               this.config.put("Address", replicatedStoreBean.getAddress());
               this.config.put("Port", replicatedStoreBean.getPort());
               this.config.put("LocalIndex", replicatedStoreBean.getLocalIndex());
               this.config.put("RegionSize", replicatedStoreBean.getRegionSize());
               this.config.put("BlockSize", replicatedStoreBean.getBlockSize());
               this.config.put("IoBufferSize", replicatedStoreBean.getIoBufferSize());
               this.config.put("BusyWaitMicroSeconds", replicatedStoreBean.getBusyWaitMicroSeconds() < 0L ? 100L : replicatedStoreBean.getBusyWaitMicroSeconds());
               this.config.put("SleepWaitMilliSeconds", replicatedStoreBean.getSleepWaitMilliSeconds() < 0L ? 1000L : replicatedStoreBean.getSleepWaitMilliSeconds());
               this.config.put("MinReplicaCount", replicatedStoreBean.getMinReplicaCount());
               this.config.put("MaxReplicaCount", replicatedStoreBean.getMaxReplicaCount());
               this.config.put("MaximumMessageSizePercent", replicatedStoreBean.getMaximumMessageSizePercent());
               this.config.put("SpaceLoggingStartPercent", replicatedStoreBean.getSpaceLoggingStartPercent());
               this.config.put("SpaceLoggingDeltaPercent", replicatedStoreBean.getSpaceLoggingDeltaPercent());
               this.config.put("SpaceOverloadYellowPercent", replicatedStoreBean.getSpaceOverloadYellowPercent());
               this.config.put("SpaceOverloadRedPercent", replicatedStoreBean.getSpaceOverloadRedPercent());
               if (KernelStatus.isInitialized() && KernelStatus.isServer()) {
                  this.config.put("DomainName", ManagementService.getRuntimeAccess(kernelId).getDomainName());
               }

               try {
                  StoreDir.createDirectory(new File(this.directoryName), false);
               } catch (IOException var6) {
                  StoreLogger.logStoreDeploymentFailed(this.name, var6.toString(), var6);
                  throw new DeploymentException(var6);
               }

               this.listener = new GenericBeanListener(replicatedStoreBean, this, changeableAttributes, (Map)null, true);
            }
         }
      } else {
         throw new DeploymentException("Directory cannot be null or empty");
      }
   }

   public void activate(GenericManagedDeployment deployment) throws DeploymentException {
      try {
         RuntimeHandler runtimeHandler = null;
         if (KernelStatus.isServer()) {
            runtimeHandler = new RuntimeHandlerImpl();
         }

         this.store = makeStore(this.name, this.directoryName, this.overrideResourceName, false, runtimeHandler);
      } catch (PersistentStoreException var3) {
         StoreLogger.logStoreDeploymentFailed(this.name, var3.toString(), var3);
         throw new DeploymentException(var3);
      }

      DeploymentMBean bean = null;
      if (deployment != null) {
         bean = deployment.getMBean();
      }

      if (bean != null) {
         if (this.listener != null) {
            this.listener.close();
         }

         this.listener = new GenericBeanListener(bean, this, changeableAttributes);
      }

      super.activate(deployment);
   }

   public void unprepare(GenericManagedDeployment deployment) {
      if (this.listener != null) {
         this.listener.close();
         this.listener = null;
      }

      super.unprepare(deployment);
   }

   public static PersistentStoreXA makeStore(String name, String directoryName, String overrideResourceName, boolean autoCreateDirectory, RuntimeHandler runtimeHandler) throws PersistentStoreException {
      PersistentStoreIO io = makeStoreIO(name, directoryName, false);
      return new PersistentStoreXAImpl(name, io, overrideResourceName, runtimeHandler);
   }

   public static ReplicatedStoreIO makeStoreIO(String name, String dirName, boolean autoCreateDirs) throws PersistentStoreException {
      if (name != null && dirName != null) {
         return new ReplicatedStoreIO(name, dirName, false);
      } else {
         throw new PersistentStoreException("Name and directory name may not be null");
      }
   }

   public void setAddress(String newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's Address to " + newVal);
      }

      try {
         this.store.setConfigValue("Address", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setPort(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's Port to " + newVal);
      }

      try {
         this.store.setConfigValue("Port", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setLocalIndex(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's LocalIndex to " + newVal);
      }

      try {
         this.store.setConfigValue("LocalIndex", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setRegionSize(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's RegionSize to " + newVal);
      }

      try {
         this.store.setConfigValue("RegionSize", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setBlockSize(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's BlockSize to " + newVal);
      }

      try {
         this.store.setConfigValue("BlockSize", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setIoBufferSize(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's IOBufferSize to " + newVal);
      }

      try {
         this.store.setConfigValue("IoBufferSize", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setBusyWaitMicroSeconds(long newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's BusyWaitMicroSeconds to " + newVal);
      }

      try {
         if (newVal < 0L) {
            this.store.setConfigValue("BusyWaitMicroSeconds", 100);
         } else {
            this.store.setConfigValue("BusyWaitMicroSeconds", newVal);
         }
      } catch (PersistentStoreException var4) {
         var4.log();
      }

   }

   public void setSleepWaitMilliSeconds(long newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's SleepWaitMilliSeconds to " + newVal);
      }

      try {
         if (newVal < 0L) {
            this.store.setConfigValue("SleepWaitMilliSeconds", 1000);
         } else {
            this.store.setConfigValue("SleepWaitMilliSeconds", newVal);
         }
      } catch (PersistentStoreException var4) {
         var4.log();
      }

   }

   public void setMinReplicaCount(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's MinReplicaCount to " + newVal);
      }

      try {
         this.store.setConfigValue("MinReplicaCount", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setMaxReplicaCount(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's MaxReplicaCount to " + newVal);
      }

      try {
         this.store.setConfigValue("MaxReplicaCount", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setMaximumMessageSizePercent(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's MaximumMessageSizePercent to " + newVal);
      }

      try {
         this.store.setConfigValue("MaximumMessageSizePercent", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setSpaceLoggingStartPercent(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's SpaceLoggingStartPercent to " + newVal);
      }

      try {
         this.store.setConfigValue("SpaceLoggingStartPercent", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setSpaceLoggingDeltaPercent(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's SpaceLoggingDeltaPercent to " + newVal);
      }

      try {
         this.store.setConfigValue("SpaceLoggingDeltaPercent", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setSpaceOverloadYellowPercent(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's SpaceOverloadYellowPercent to " + newVal);
      }

      try {
         this.store.setConfigValue("SpaceOverloadYellowPercent", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setSpaceOverloadRedPercent(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's SpaceOverloadRedPercent to " + newVal);
      }

      try {
         this.store.setConfigValue("SpaceOverloadRedPercent", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   static {
      changeableAttributes.put("Address", String.class);
      changeableAttributes.put("Port", Integer.TYPE);
      changeableAttributes.put("LocalIndex", Integer.TYPE);
      changeableAttributes.put("RegionSize", Integer.TYPE);
      changeableAttributes.put("BlockSize", Integer.TYPE);
      changeableAttributes.put("IoBufferSize", Integer.TYPE);
      changeableAttributes.put("BusyWaitMicroSeconds", Long.TYPE);
      changeableAttributes.put("SleepWaitMilliSeconds", Long.TYPE);
      changeableAttributes.put("MinReplicaCount", Integer.TYPE);
      changeableAttributes.put("MaxReplicaCount", Integer.TYPE);
      changeableAttributes.put("MaximumMessageSizePercent", Integer.TYPE);
      changeableAttributes.put("SpaceLoggingStartPercent", Integer.TYPE);
      changeableAttributes.put("SpaceLoggingDeltaPercent", Integer.TYPE);
      changeableAttributes.put("SpaceOverloadYellowPercent", Integer.TYPE);
      changeableAttributes.put("SpaceOverloadRedPercent", Integer.TYPE);
   }
}
