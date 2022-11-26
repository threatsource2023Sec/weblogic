package weblogic.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.KernelStatus;
import weblogic.store.common.StoreDebug;
import weblogic.store.internal.PersistentStoreImpl;

public class PersistentStoreManager {
   private static final PersistentStoreManager singleton = new PersistentStoreManager();
   private static final String GLOBAL_ID = "0";
   private Map ejbTimerStoresByPartition = new HashMap();
   private final Map stores = new HashMap();
   private final Map storesByLogicalName = new HashMap();
   private PersistentStore defaultStore;
   private Lock defaultStoreLock = new ReentrantLock(true);

   private PersistentStoreManager() {
   }

   public static PersistentStoreManager getManager() {
      return singleton;
   }

   public PersistentStore getStore(String name) {
      Map storeMap = this.getStoreMapForPartition();
      synchronized(storeMap) {
         PersistentStore store = (PersistentStore)storeMap.get(name);
         return store;
      }
   }

   public PersistentStore getStoreByLogicalName(String name) {
      Map storeMap = this.getStoreLogicalNameMapForPartition();
      synchronized(storeMap) {
         PersistentStore store = (PersistentStore)storeMap.get(name);
         return store;
      }
   }

   public PersistentStore getDefaultStore() {
      if (KernelStatus.isServer()) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (cic != null && !cic.isGlobalRuntime() && StoreDebug.defaultStoreVerbose.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Default store retrieved by partition ").append(cic.getApplicationName());
            sb.append(" use of the default store is only supported for the global runtime.");
            StoreDebug.defaultStoreVerbose.debug(sb.toString());
         }
      }

      PersistentStore store;
      try {
         this.defaultStoreLock.lock();
         store = this.defaultStore;
      } finally {
         this.defaultStoreLock.unlock();
      }

      return store;
   }

   public void setDefaultStore(PersistentStore store) {
      try {
         this.defaultStoreLock.lock();
         this.defaultStore = store;
      } finally {
         this.defaultStoreLock.unlock();
      }

   }

   public PersistentStore getEjbTimerStore() {
      PersistentStore ejbTimerStore = null;
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("Retrieving EjbTimerStore for partition " + cic.getPartitionName() + " (" + cic.getPartitionId() + ").");
      }

      if (cic.isGlobalRuntime()) {
         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("Returing the default store.");
         }

         ejbTimerStore = this.getDefaultStore();
      } else {
         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("Returing an EjbTimerStore.");
         }

         synchronized(this.ejbTimerStoresByPartition) {
            ejbTimerStore = (PersistentStore)this.ejbTimerStoresByPartition.get(cic.getPartitionId());
         }
      }

      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("EjbTimerStore retrieval for partition " + cic.getPartitionName() + " (" + cic.getPartitionId() + ") done.");
      }

      return ejbTimerStore;
   }

   public void removeAllEjbTimerStores() {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("Removing all EjbTimerStores.");
      }

      synchronized(this.ejbTimerStoresByPartition) {
         Iterator var5 = this.ejbTimerStoresByPartition.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry ejbTimerStoreEntry = (Map.Entry)var5.next();
            PersistentStore ejbTimerStore = (PersistentStore)ejbTimerStoreEntry.getValue();
            String partitionId = (String)ejbTimerStoreEntry.getKey();
            Map storeMap = this.getStoreMapForPartition(partitionId);
            if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
               StoreDebug.ejbTimerStore.debug("Removing EjbTimerStore from PersistentStore map");
            }

            synchronized(storeMap) {
               storeMap.remove(ejbTimerStore.getName());
            }

            if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
               StoreDebug.ejbTimerStore.debug("EjbTimerStore removed from PersistentStore map");
            }

            try {
               ejbTimerStore.close();
            } catch (Exception var11) {
               if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
                  StoreDebug.ejbTimerStore.debug("Exception while closing EjbTimerStore for partition " + partitionId, var11);
               }
            }
         }

         this.ejbTimerStoresByPartition.clear();
      }

      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("All EjbTimerStores have been removed.");
      }

   }

   public void addEjbTimerStore(String partitionId, String serverName, PersistentStore ejbTimerStore) {
      if (ejbTimerStore != null && partitionId != null && !partitionId.equals("0")) {
         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("Adding EjbTimerStore for server " + serverName + " for partitionId " + partitionId + "");
         }

         synchronized(this.ejbTimerStoresByPartition) {
            if (!this.ejbTimerStoresByPartition.containsKey(partitionId)) {
               this.ejbTimerStoresByPartition.put(partitionId, ejbTimerStore);
            }
         }

         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("EjbTimerStore added for server " + serverName + " for partitionId " + partitionId + "");
         }
      }

   }

   public void removeEjbTimerStore(String partitionId, String serverName) {
      if (partitionId != null && !partitionId.equals("0")) {
         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("Removing EjbTimerStore for server " + serverName + " for partitionId " + partitionId + "");
         }

         synchronized(this.ejbTimerStoresByPartition) {
            this.ejbTimerStoresByPartition.remove(partitionId);
         }

         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("EjbTimerStore removed for server " + serverName + " for partitionId " + partitionId + "");
         }
      }

   }

   public void addStore(String name, PersistentStore store) {
      Map storeMap = this.getStoreMapForPartition();
      synchronized(storeMap) {
         storeMap.put(name, store);
      }
   }

   public void removeStore(String name) {
      Map storeMap = this.getStoreMapForPartition();
      synchronized(storeMap) {
         storeMap.remove(name);
      }
   }

   public void addStoreByLogicalName(String name, PersistentStore store) {
      Map storeMap = this.getStoreLogicalNameMapForPartition();
      synchronized(storeMap) {
         storeMap.put(name, store);
      }
   }

   public void removeStoreByLogicalName(String name) {
      Map storeMap = this.getStoreLogicalNameMapForPartition();
      synchronized(storeMap) {
         storeMap.remove(name);
      }
   }

   public boolean storeExists(String name) {
      Map storeMap = this.getStoreMapForPartition();
      synchronized(storeMap) {
         boolean exists = storeMap.containsKey(name);
         return exists;
      }
   }

   public boolean storeExistsByLogicalName(String name) {
      Map storeLogicalNameMap = this.getStoreLogicalNameMapForPartition();
      synchronized(storeLogicalNameMap) {
         boolean exists = storeLogicalNameMap.containsKey(name);
         return exists;
      }
   }

   public Iterator getAllStores() {
      ArrayList storesCopy = new ArrayList();

      try {
         this.defaultStoreLock.lock();
         if (this.defaultStore != null) {
            storesCopy.add(this.defaultStore);
         }
      } finally {
         this.defaultStoreLock.unlock();
      }

      synchronized(this.stores) {
         Iterator var3 = this.stores.values().iterator();

         while(var3.hasNext()) {
            Map map = (Map)var3.next();
            storesCopy.addAll(map.values());
         }

         return Collections.unmodifiableList(storesCopy).iterator();
      }
   }

   public void closeFileStore(String name) throws PersistentStoreException {
      PersistentStore deadStore;
      synchronized(this.stores) {
         Map storeMap = this.getStoreMapForPartition();
         deadStore = (PersistentStore)storeMap.remove(name);
      }

      if (deadStore != null) {
         deadStore.close();
      }

   }

   public void dump(XMLStreamWriter xsw) throws XMLStreamException {
      xsw.writeStartElement("PersistentStores");
      xsw.writeStartElement("DefaultStore");
      ((PersistentStoreImpl)this.defaultStore).dump(xsw);
      xsw.writeEndElement();
      Iterator allStores = this.getAllStores();

      while(allStores.hasNext()) {
         PersistentStoreImpl store = (PersistentStoreImpl)allStores.next();
         store.dump(xsw);
      }

      xsw.writeEndElement();
   }

   private Map getStoreLogicalNameMapForPartition() {
      return this.getStoreLogicalNameMapForPartition(this.getPartitionId());
   }

   private Map getStoreLogicalNameMapForPartition(String partitionId) {
      synchronized(this.storesByLogicalName) {
         Map logicalStoreMap = (Map)this.storesByLogicalName.get(partitionId);
         if (logicalStoreMap == null) {
            logicalStoreMap = new HashMap();
            this.storesByLogicalName.put(partitionId, logicalStoreMap);
         }

         return (Map)logicalStoreMap;
      }
   }

   private Map getStoreMapForPartition() {
      return this.getStoreMapForPartition(this.getPartitionId());
   }

   private Map getStoreMapForPartition(String partitionId) {
      synchronized(this.stores) {
         Map storeMap = (Map)this.stores.get(partitionId);
         if (storeMap == null) {
            storeMap = new HashMap();
            this.stores.put(partitionId, storeMap);
         }

         return (Map)storeMap;
      }
   }

   private String getPartitionId() {
      String partitionId = null;

      try {
         if (KernelStatus.isServer()) {
            ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
            if (cic != null) {
               partitionId = cic.getPartitionId();
            }
         }
      } catch (Exception var3) {
         StoreDebug.persistentStoreManager.debug(var3.getMessage(), var3);
      }

      return partitionId;
   }
}
