package weblogic.store.xa.internal;

import weblogic.store.ObjectHandler;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.RuntimeHandler;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.internal.GXAResourceImpl;
import weblogic.store.internal.PersistentStoreImpl;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.store.xa.map.PersistentMapXAImpl;

public class PersistentStoreXAImpl extends PersistentStoreImpl implements PersistentStoreXA, Runnable {
   public static final int ONE_DAY_MILLIS = 86400000;
   private GXAResourceImpl gxaResource;
   private final Object gxaLock;
   private String overrideResourceName;

   public PersistentStoreXAImpl(String storeName, PersistentStoreIO ios) throws PersistentStoreException {
      this(storeName, ios, (String)null);
   }

   public PersistentStoreXAImpl(String storeName, PersistentStoreIO ios, String overrideResourceName) throws PersistentStoreException {
      this(storeName, ios, overrideResourceName, (RuntimeHandler)null);
   }

   public PersistentStoreXAImpl(String storeName, PersistentStoreIO ios, String overrideResourceName, RuntimeHandler runtimeHandler) throws PersistentStoreException {
      super(storeName, ios, runtimeHandler);
      this.gxaLock = new Object();
      this.overrideResourceName = overrideResourceName;
   }

   public PersistentMap createPersistentMapXA(String name) throws PersistentStoreException {
      synchronized(this.maps) {
         PersistentMap pMap = (PersistentMap)this.maps.get(name);
         if (pMap == null) {
            PersistentStoreConnection keys = this.createConnectionInternal(name, (byte)1);
            PersistentStoreConnection values = this.createConnectionInternal(name + ".values", (byte)1);
            pMap = new PersistentMapXAImpl(keys, values);
            this.maps.put(name, pMap);
         }

         return (PersistentMap)pMap;
      }
   }

   public PersistentMap createPersistentMapXA(String name, ObjectHandler handler) throws PersistentStoreException {
      synchronized(this.maps) {
         PersistentMap pMap = (PersistentMap)this.maps.get(name);
         if (pMap == null) {
            PersistentStoreConnection keys = this.createConnectionInternal(name, (byte)1);
            PersistentStoreConnection values = this.createConnectionInternal(name + ".values", (byte)1);
            pMap = new PersistentMapXAImpl(keys, values, handler);
            this.maps.put(name, pMap);
         }

         return (PersistentMap)pMap;
      }
   }

   public GXAResource getGXAResource() throws PersistentStoreException {
      synchronized(this.gxaLock) {
         if (this.gxaResource != null) {
            return this.gxaResource;
         }

         String domainName = this.getDomainName();
         this.gxaResource = new GXAResourceImpl(domainName, this, this.overrideResourceName);
         this.gxaResource.setAbandonTimeoutMillis(this.getJTAAbandonTimeoutMillis());
         this.gxaResource.open();
      }

      return this.gxaResource;
   }

   protected void closeSub() throws PersistentStoreException {
      synchronized(this.gxaLock) {
         GXAResourceImpl rm = this.gxaResource;
         if (rm != null) {
            rm.shutdown();
         }

      }
   }

   private String getDomainName() {
      RuntimeHandler runtimeHandler = this.getRuntimeHandler();
      return runtimeHandler == null ? "" : runtimeHandler.getDomainName();
   }

   private long getJTAAbandonTimeoutMillis() {
      RuntimeHandler runtimeHandler = this.getRuntimeHandler();
      return runtimeHandler == null ? 86400000L : runtimeHandler.getJTAAbandonTimeoutMillis();
   }
}
