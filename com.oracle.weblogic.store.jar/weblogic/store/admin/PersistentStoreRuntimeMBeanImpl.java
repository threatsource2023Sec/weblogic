package weblogic.store.admin;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PersistentStoreConnectionRuntimeMBean;
import weblogic.management.runtime.PersistentStoreRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.RuntimeUpdater;
import weblogic.store.StoreStatistics;
import weblogic.store.common.PartitionNameUtils;
import weblogic.store.common.StoreDebug;
import weblogic.store.internal.PersistentStoreImpl;
import weblogic.work.WorkManagerFactory;

public class PersistentStoreRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PersistentStoreRuntimeMBean, RuntimeUpdater {
   private final PersistentStore store;
   private final StoreStatistics statistics;
   private final ArrayList connections = new ArrayList();
   private static final boolean IS_HEALTH_CRITICAL = false;
   private static final String HEALTH_CONFIG_TYPE = "PersistentStoreRuntime";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String HEALTH_NAME_PREFIX = "PersistentStore.";
   private Object healthStatusLock = new Object();
   private int healthStatus = 0;
   private String healthStatusMsg = null;

   public PersistentStoreRuntimeMBeanImpl(PersistentStore store) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionName(store.getName()));
      this.store = store;
      this.statistics = store.getStatistics();
      ServerRuntimeMBean configuredName;
      if (this.parent instanceof PartitionRuntimeMBean) {
         ((PartitionRuntimeMBean)this.parent).addPersistentStoreRuntime(this);
      } else {
         configuredName = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         configuredName.addPersistentStoreRuntime(this);
      }

      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         configuredName = null;

         String configuredName;
         try {
            configuredName = (String)store.getConfigValue("StoreConfigName");
         } catch (PersistentStoreException var4) {
            if (StoreDebug.storeAdmin.isDebugEnabled()) {
               StoreDebug.storeAdmin.debug("PersistentStoreRuntimeMBeanImpl(): " + var4.getMessage() + ": Using " + store.getShortName() + " for configured name");
            }

            configuredName = store.getShortName();
         }

         StoreDebug.storeAdmin.debug("PersistentStoreRuntimeMBeanImpl(): name: " + store.getName() + " short name: " + store.getShortName() + " configured name: " + configuredName + " stripped name:" + PartitionNameUtils.stripDecoratedPartitionName(store.getName()));
         StoreDebug.storeAdmin.debug("Registering store " + this.getName() + " with the HealhMonitorService as " + "PersistentStore." + store.getName());
      }

      HealthMonitorService.register("PersistentStore." + store.getName(), this, false);
   }

   public void unregister() throws ManagementException {
      synchronized(this.connections) {
         Iterator i = this.connections.iterator();

         while(true) {
            if (!i.hasNext()) {
               break;
            }

            PersistentStoreConnectionRuntimeMBeanImpl c = (PersistentStoreConnectionRuntimeMBeanImpl)i.next();
            c.unregister();
         }
      }

      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Unregistering store " + this.getName() + " from the HealhMonitorService as " + "PersistentStore." + this.store.getName());
      }

      HealthMonitorService.unregister("PersistentStore." + this.store.getName());
      if (this.parent instanceof PartitionRuntimeMBean) {
         ((PartitionRuntimeMBean)this.parent).removePersistentStoreRuntime(this);
      } else {
         ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         serverRuntime.removePersistentStoreRuntime(this);
      }

      super.unregister();
   }

   public long getAllocatedWindowBufferBytes() {
      return this.statistics.getMappedBufferBytes();
   }

   public long getAllocatedIoBufferBytes() {
      return this.statistics.getIOBufferBytes();
   }

   public long getCreateCount() {
      return this.statistics.getCreateCount();
   }

   public long getReadCount() {
      return this.statistics.getReadCount();
   }

   public long getUpdateCount() {
      return this.statistics.getUpdateCount();
   }

   public long getDeleteCount() {
      return this.statistics.getDeleteCount();
   }

   public long getObjectCount() {
      return this.statistics.getObjectCount();
   }

   public long getPhysicalWriteCount() {
      return this.statistics.getPhysicalWriteCount();
   }

   public PersistentStoreConnectionRuntimeMBean[] getConnections() {
      synchronized(this.connections) {
         PersistentStoreConnectionRuntimeMBean[] ret = new PersistentStoreConnectionRuntimeMBean[this.connections.size()];
         this.connections.toArray(ret);
         return ret;
      }
   }

   public void addConnection(PersistentStoreConnectionRuntimeMBean c) {
      synchronized(this.connections) {
         this.connections.add(c);
      }
   }

   public void removeConnection(PersistentStoreConnection conn) throws ManagementException {
      String name = conn.getName();
      synchronized(this.connections) {
         Iterator i = this.connections.iterator();

         while(i.hasNext()) {
            PersistentStoreConnectionRuntimeMBeanImpl c = (PersistentStoreConnectionRuntimeMBeanImpl)i.next();
            if (c.getName().equals(name)) {
               i.remove();
               c.unregister();
            }
         }

      }
   }

   public HealthState getHealthState() {
      synchronized(this.store) {
         HealthState healthState;
         synchronized(this.healthStatusLock) {
            PersistentStoreException fatalException = this.store.getFatalException();
            Symptom symptom;
            if (fatalException != null) {
               symptom = new Symptom(SymptomType.STORE_ERROR, Severity.HIGH, this.getName(), fatalException.toString());
               healthState = new HealthState(3, symptom);
            } else {
               symptom = null;
               if (this.healthStatus == 1) {
                  symptom = new Symptom(SymptomType.STORE_ERROR, Severity.HIGH, this.getName(), this.healthStatusMsg);
               }

               healthState = new HealthState(this.healthStatus, symptom);
            }

            healthState.setSubsystemName("PersistentStore." + this.getName());
            healthState.setCritical(false);
            healthState.setMBeanName(this.getName());
            healthState.setMBeanType("PersistentStoreRuntime");
         }

         return healthState;
      }
   }

   public void setHealthFailed(PersistentStoreException e) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("HealthFailed: Scheduling HealthSetter: e: " + e.getMessage());
      }

      synchronized(this.healthStatusLock) {
         this.healthStatus = 3;
      }

      WorkManagerFactory.getInstance().getSystem().schedule(new HealthSetter(e));
   }

   public void setHealthWarn(String warningMsg) {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("Setting health status to HEALTH_WARN: store " + this.store.getName());
      }

      synchronized(this.healthStatusLock) {
         this.healthStatus = 1;
         this.healthStatusMsg = warningMsg;
      }
   }

   private final class HealthSetter implements Runnable {
      private PersistentStoreException exception;

      HealthSetter(PersistentStoreException e) {
         this.exception = e;
         ((PersistentStoreImpl)PersistentStoreRuntimeMBeanImpl.this.store).setHealthSetterComplete(false);
      }

      public void run() {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("HealthSetter PersistentStoreRuntimeMBean: name: " + PersistentStoreRuntimeMBeanImpl.this.store.getName() + " stripped name:" + PartitionNameUtils.stripDecoratedPartitionName(PersistentStoreRuntimeMBeanImpl.this.store.getName()) + " short name: " + PersistentStoreRuntimeMBeanImpl.this.store.getShortName() + " isSpecialStore: " + ((PersistentStoreImpl)PersistentStoreRuntimeMBeanImpl.this.store).isSpecialStore());
         }

         try {
            if (((PersistentStoreImpl)PersistentStoreRuntimeMBeanImpl.this.store).isSpecialStore()) {
               if (StoreDebug.storeAdmin.isDebugEnabled()) {
                  StoreDebug.storeAdmin.debug("Calling HealthMonitorService.subsystemFailedNonFatal: PersistentStore." + PersistentStoreRuntimeMBeanImpl.this.store.getName() + " exception: " + this.exception.toString());
               }

               HealthMonitorService.subsystemFailedNonFatal("PersistentStore." + PersistentStoreRuntimeMBeanImpl.this.store.getName(), this.exception.toString());
            } else {
               if (StoreDebug.storeAdmin.isDebugEnabled()) {
                  StoreDebug.storeAdmin.debug("Calling HealthMonitorService.subsystemFailed: PersistentStore." + PersistentStoreRuntimeMBeanImpl.this.store.getName() + " exception: " + this.exception.toString());
               }

               HealthMonitorService.subsystemFailed("PersistentStore." + PersistentStoreRuntimeMBeanImpl.this.store.getName(), this.exception.toString());
            }
         } finally {
            ((PersistentStoreImpl)PersistentStoreRuntimeMBeanImpl.this.store).setHealthSetterComplete(true);
         }

      }
   }
}
