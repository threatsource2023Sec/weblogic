package weblogic.store.admin;

import java.util.HashMap;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.utils.GenericAdminHandler;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.StoreLogger;
import weblogic.store.common.StoreDebug;
import weblogic.store.internal.PersistentStoreImpl;
import weblogic.store.xa.PersistentStoreXA;

public abstract class AdminHandler implements GenericAdminHandler {
   protected PersistentStoreXA store;
   protected HashMap config;
   protected String name;
   protected String configuredName;
   protected String logicalName;
   protected String overrideResourceName;
   protected String instanceName;
   protected MigratableTargetMBean migratableTarget;
   protected boolean handlerOpened;
   protected boolean storeOpened;
   protected boolean defaultStore;
   protected boolean isMigratable = false;
   protected boolean isRP;
   private GenericBeanListener listener;
   private static final HashMap changeableAttributes = new HashMap();

   public void prepare(GenericManagedDeployment deployment) throws DeploymentException {
      DeploymentMBean mBean = deployment.getMBean();
      this.name = deployment.getName();
      this.configuredName = mBean.getName();
      this.defaultStore = false;
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("AdminHandler.prepare: mBean: " + mBean.toString());
      }

      PersistentStoreMBean psBean = (PersistentStoreMBean)mBean;
      this.logicalName = psBean.getLogicalName();
      if (deployment.isClustered()) {
         this.instanceName = deployment.getInstanceName();
      }

      this.overrideResourceName = psBean.getXAResourceName();
      if (isEmptyString(this.overrideResourceName)) {
         this.overrideResourceName = null;
      }

      this.isRP = psBean.getRestartInPlace();
      this.isMigratable = GenericDeploymentManager.isMigratable(psBean);
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("AdminHandler: prepare: store is migratable: " + this.isMigratable);
      }

      this.listener = new GenericBeanListener(mBean, this, changeableAttributes);
      if (this.name.startsWith("_WLS_")) {
         throw new DeploymentException("Invalid store name: " + this.name);
      } else {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Preparing PersistentStore \"" + this.name + "\" from MBean \"" + mBean + "\"");
         }

         if (PersistentStoreManager.getManager().storeExists(this.name)) {
            throw new DeploymentException("The persistent store " + this.name + " already exists");
         }
      }
   }

   String getName() {
      return this.name;
   }

   String getConfiguredName() {
      return this.configuredName;
   }

   String getOverrideResourceName() {
      return this.overrideResourceName;
   }

   public PersistentStoreXA getStore() {
      return this.store;
   }

   public void activate(GenericManagedDeployment deployment) throws DeploymentException {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("AdminHandler.activate: Opening the persistent store " + this.name);
      }

      if (this.store instanceof PersistentStoreImpl) {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("AdminHandler: activate: store configuraiton properties: isMigratable: " + this.isMigratable + ", isRPEnabled: " + this.isRP + ", defaultStore: " + this.defaultStore);
         }

         this.config.put("IsMigratable", this.isMigratable);
         this.config.put("IsRPEnabled", this.isRP);
         this.config.put("IsDefaultStore", this.defaultStore);
      }

      try {
         this.store.open(this.config);
      } catch (PersistentStoreException var6) {
         while(!((PersistentStoreImpl)this.store).isHealthSetterComplete()) {
            try {
               Thread.sleep(10L);
            } catch (InterruptedException var5) {
            }
         }

         try {
            this.store.unregisterStoreMBean();
         } catch (PersistentStoreException var4) {
         }

         StoreLogger.logStoreDeploymentFailed(this.name, var6.toString(), var6);
         throw new DeploymentException(var6);
      }

      PersistentStoreManager manager = PersistentStoreManager.getManager();
      if (this.defaultStore) {
         if (manager.getDefaultStore() != null) {
            throw new AssertionError("Multiple default stores configured");
         }

         manager.setDefaultStore(this.store);
      } else {
         manager.addStore(this.name, this.store);
         if (this.logicalName != null) {
            manager.addStoreByLogicalName(this.logicalName, this.store);
         }
      }

   }

   public void deactivate(GenericManagedDeployment deployment) throws UndeploymentException {
      PersistentStoreManager manager = PersistentStoreManager.getManager();
      if (this.defaultStore) {
         manager.setDefaultStore((PersistentStore)null);
      } else {
         manager.removeStore(this.name);
         if (this.logicalName != null) {
            manager.removeStoreByLogicalName(this.logicalName);
         }
      }

      try {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Closing the persistent store " + this.name);
         }

         this.store.close();
      } catch (PersistentStoreException var4) {
         StoreLogger.logStoreShutdownFailed(this.name, var4.toString(), var4);
         throw new UndeploymentException(var4);
      }
   }

   public void unprepare(GenericManagedDeployment deployment) {
      if (this.listener != null) {
         this.listener.close();
         this.listener = null;
      }

   }

   public void setLogicalName(String newVal) {
      PersistentStoreManager manager = PersistentStoreManager.getManager();
      if (this.logicalName != null) {
         manager.removeStoreByLogicalName(this.logicalName);
      }

      this.logicalName = newVal;
      if (this.logicalName != null && this.store != null) {
         manager.addStoreByLogicalName(this.logicalName, this.store);
      }

   }

   protected static boolean isEmptyString(String str) {
      return str == null || str.length() == 0;
   }

   protected static boolean isEmptyBytes(byte[] bytes) {
      return bytes == null || bytes.length == 0;
   }

   static {
      changeableAttributes.put("LogicalName", String.class);
   }
}
