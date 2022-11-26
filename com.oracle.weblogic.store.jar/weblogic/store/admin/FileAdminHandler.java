package weblogic.store.admin;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.KernelStatus;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.GenericFileStoreMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.PersistentStoreException;
import weblogic.store.RuntimeHandler;
import weblogic.store.StoreLogger;
import weblogic.store.StoreWritePolicy;
import weblogic.store.admin.util.PartitionFileSystemUtils;
import weblogic.store.admin.util.PersistentStoreUtils;
import weblogic.store.common.PartitionNameUtils;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.file.StoreDir;
import weblogic.store.xa.PersistentStoreManagerXA;

public class FileAdminHandler extends AdminHandler {
   private GenericBeanListener listener;
   private String directoryName;
   private boolean autoCreateDirectory;
   private static final HashMap changeableAttributes = new HashMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void prepare(GenericManagedDeployment deployment) throws DeploymentException {
      super.prepare(deployment);
      FileStoreMBean fStoreBean = (FileStoreMBean)deployment.getMBean();
      this.autoCreateDirectory = true;
      boolean isProduction = ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled();
      String createDirProp;
      if (isProduction) {
         createDirProp = System.getProperty("weblogic.store.file.AutoCreateDirectory", "true").trim();
         if ("false".equalsIgnoreCase(createDirProp)) {
            this.autoCreateDirectory = false;
         }
      }

      createDirProp = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      this.directoryName = PersistentStoreUtils.computeDirectory(deployment.getName(), createDirProp, fStoreBean);
      this.prepareCommon(fStoreBean);
   }

   public void prepareDefaultStore(ServerMBean serverBean, boolean autoCreateDirs) throws DeploymentException {
      this.prepareDefaultStore(serverBean, autoCreateDirs, true);
   }

   public void prepareDefaultStore(ServerMBean serverBean, boolean autoCreateDirs, boolean registerListener) throws DeploymentException {
      this.defaultStore = true;
      this.name = "_WLS_" + serverBean.getName();
      this.autoCreateDirectory = autoCreateDirs;
      GenericFileStoreMBean fsBean = serverBean.getDefaultFileStore();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      this.directoryName = PartitionFileSystemUtils.locateDefaultStoreDirectory(cic, fsBean.getDirectory(), serverBean.getName(), "default");
      this.prepareCommon(fsBean, registerListener, true);
   }

   public void prepareEjbTimerStore(ServerMBean serverBean, String ejbTimerStoreName) throws DeploymentException {
      this.autoCreateDirectory = true;
      this.name = ejbTimerStoreName;
      GenericFileStoreMBean fsBean = serverBean.getDefaultFileStore();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      this.directoryName = PartitionFileSystemUtils.locateEjbTimerStoreDirectory(cic, fsBean.getDirectory(), this.name);
      this.prepareCommon(fsBean, false, false);
   }

   public void unprepareEjbTimerStore(ServerMBean serverBean) {
      this.unprepare((GenericManagedDeployment)null);
   }

   private void prepareCommon(GenericFileStoreMBean fsBean) throws DeploymentException {
      this.prepareCommon(fsBean, true, true);
   }

   private void prepareCommon(GenericFileStoreMBean fsBean, boolean registerListener, boolean createListener) throws DeploymentException {
      this.config = new HashMap();

      try {
         String serverName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         String domainName = null;
         if (KernelStatus.isInitialized() && KernelStatus.isServer()) {
            domainName = ManagementService.getRuntimeAccess(kernelId).getDomainName();
            this.config.put("DomainName", domainName);
         }

         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         this.config.put("StoreConfigName", this.getConfiguredName());
         this.config.put("BlockSize", fsBean.getBlockSize());
         this.config.put("CacheDirectory", PartitionFileSystemUtils.locateCacheDirectory(cic, domainName, fsBean.getCacheDirectory()));
         this.config.put("InitialSize", fsBean.getInitialSize());
         this.config.put("IoBufferSize", fsBean.getIoBufferSize());
         this.config.put("MaxFileSize", fsBean.getMaxFileSize());
         this.config.put("MaxWindowBufferSize", fsBean.getMaxWindowBufferSize());
         this.config.put("MinWindowBufferSize", fsBean.getMinWindowBufferSize());
         this.config.put("SynchronousWritePolicy", StoreWritePolicy.getPolicy(fsBean.getSynchronousWritePolicy()));
         this.config.put("FileLockingEnabled", fsBean.isFileLockingEnabled());
      } catch (PersistentStoreException var8) {
         StoreLogger.logStoreDeploymentFailed(PartitionNameUtils.stripDecoratedPartitionName(this.name), var8.toString(), var8);
         throw new DeploymentException(var8);
      }

      try {
         StoreDir.createDirectory(new File(this.directoryName), this.autoCreateDirectory);
      } catch (IOException var7) {
         StoreLogger.logStoreDeploymentFailed(PartitionNameUtils.stripDecoratedPartitionName(this.name), var7.toString(), var7);
         throw new DeploymentException(var7);
      }

      if (createListener) {
         this.listener = new GenericBeanListener(fsBean, this, changeableAttributes, (Map)null, registerListener);
      }

   }

   public void activate(GenericManagedDeployment deployment) throws DeploymentException {
      try {
         RuntimeHandler runtimeHandler = null;
         if (KernelStatus.isServer()) {
            runtimeHandler = new RuntimeHandlerImpl();
         }

         this.store = PersistentStoreManagerXA.makeXAStore(this.name, this.directoryName, this.overrideResourceName, this.autoCreateDirectory, runtimeHandler);
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

   public void createMigratedDefaultStore(ServerMBean serverBean, boolean autoCreateDirs) throws DeploymentException {
      this.prepareDefaultStore(serverBean, autoCreateDirs, false);
      this.defaultStore = false;
      this.activate((GenericManagedDeployment)null);
   }

   public void unprepare(GenericManagedDeployment deployment) {
      if (this.listener != null) {
         this.listener.close();
         this.listener = null;
      }

      super.unprepare(deployment);
   }

   public void setSynchronousWritePolicy(String newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's write policy to " + newVal);
      }

      try {
         this.store.setConfigValue("SynchronousWritePolicy", StoreWritePolicy.getPolicy(newVal));
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setMinWindowBufferSize(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's MinWindowBufferSize to " + newVal);
      }

      try {
         this.store.setConfigValue("MinWindowBufferSize", newVal);
      } catch (PersistentStoreException var3) {
         var3.log();
      }

   }

   public void setMaxWindowBufferSize(int newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's MaxWindowBufferSize to " + newVal);
      }

      try {
         this.store.setConfigValue("MaxWindowBufferSize", newVal);
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

   public void setMaxFileSize(long newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's MaxFileSize to " + newVal);
      }

      try {
         this.store.setConfigValue("MaxFileSize", newVal);
      } catch (PersistentStoreException var4) {
         var4.log();
      }

   }

   public void setFileLockingEnabled(boolean newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's FileLockingEnabled to " + newVal);
      }

      try {
         this.store.setConfigValue("FileLockingEnabled", newVal);
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

   public void setInitialSize(long newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's InitialSize to " + newVal);
      }

      try {
         this.store.setConfigValue("InitialSize", newVal);
      } catch (PersistentStoreException var4) {
         var4.log();
      }

   }

   public void setCacheDirectory(String newVal) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Changing the store's CacheDirectory to " + newVal);
      }

      try {
         String domainName = ManagementService.getRuntimeAccess(kernelId).getDomainName();
         String serverName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         this.store.setConfigValue("CacheDirectory", PartitionFileSystemUtils.locateCacheDirectory(cic, domainName, newVal));
      } catch (PersistentStoreException var5) {
         var5.log();
      }

   }

   static {
      changeableAttributes.put("SynchronousWritePolicy", String.class);
      changeableAttributes.put("CacheDirectory", String.class);
      changeableAttributes.put("MinWindowBufferSize", Integer.TYPE);
      changeableAttributes.put("MaxWindowBufferSize", Integer.TYPE);
      changeableAttributes.put("IoBufferSize", Integer.TYPE);
      changeableAttributes.put("MaxFileSize", Long.TYPE);
      changeableAttributes.put("BlockSize", Integer.TYPE);
      changeableAttributes.put("InitialSize", Long.TYPE);
      changeableAttributes.put("FileLockingEnabled", Boolean.TYPE);
   }
}
