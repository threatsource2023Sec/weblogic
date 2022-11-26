package weblogic.store.gxa.internal;

import java.util.HashMap;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.gxa.GXid;

abstract class GXAAbstractTransaction implements GXATransaction {
   private static final String[] STATUS_STRINGS = new String[]{"Initializing", "Active", "Preparing", "Prepared", "Rollback", "RollbackOnly", "Commit"};
   private boolean commitFailed = false;
   private final Object commitFailedLock = new Object();
   private int status;
   protected GXAResourceImpl gxaResource;
   protected int operationCount;
   protected PersistentStoreTransaction persistentStoreTransaction;
   protected GXAOperationWrapperImpl firstOperation;
   protected GXAOperationWrapperImpl lastOperation;
   protected boolean storeTxAvailable;
   protected HashMap propertyMaps;
   static final int CBONEPHASEPREPARE = 1;
   static final int CBTWOPHASEPREPARE = 2;
   static final int CBONEPHASECOMMIT = 3;
   static final int CBTWOPHASECOMMIT = 4;
   static final int CBROLLBACK = 5;

   protected GXAAbstractTransaction(GXAResourceImpl gxaResource) {
      this.gxaResource = gxaResource;
   }

   public abstract GXid getGXid();

   abstract GXidImpl getGXidImpl();

   public synchronized PersistentStoreTransaction getStoreTransaction() {
      if (!this.storeTxAvailable) {
         throw new IllegalStateException("Programming error.");
      } else {
         if (this.persistentStoreTransaction == null) {
            this.persistentStoreTransaction = this.gxaResource.getPersistentStore().begin();
         }

         return this.persistentStoreTransaction;
      }
   }

   protected synchronized boolean hasStoreWork() {
      return this.persistentStoreTransaction != null;
   }

   boolean hasStoreIOWork() {
      PersistentStoreTransaction pstx = null;
      synchronized(this) {
         pstx = this.persistentStoreTransaction;
      }

      return pstx != null && pstx.hasPendingWork();
   }

   public GXAResource getGXAResource() {
      return this.gxaResource;
   }

   public int getStatus() {
      return this.status;
   }

   public String getStatusAsString() {
      int i = this.status;
      return i >= 0 && i < STATUS_STRINGS.length ? STATUS_STRINGS[this.status] : "Unexpected status " + i + " contact customer support";
   }

   void setStatus(int status) {
      this.status = status;
   }

   public synchronized GXAOperation[] getGXAOperations() {
      GXAOperation[] ret = new GXAOperation[this.operationCount];
      int i = 0;

      for(GXAOperationWrapperImpl opW = this.firstOperation; opW != null; opW = opW.getNext()) {
         ret[i++] = opW.getOperation();
      }

      return ret;
   }

   synchronized void addOperation(GXAOperationWrapperImpl operation) {
      ++this.operationCount;
      if (this.lastOperation == null) {
         this.firstOperation = operation;
         this.lastOperation = operation;
      } else {
         this.lastOperation.setNext(operation);
         operation.setPrev(this.lastOperation);
         this.lastOperation = operation;
      }
   }

   synchronized void removeOperation(GXAOperationWrapperImpl operation) {
      --this.operationCount;
      if (operation.getPrev() == null) {
         this.firstOperation = operation.getNext();
      } else {
         operation.getPrev().setNext(operation.getNext());
      }

      if (operation.getNext() == null) {
         this.lastOperation = operation.getPrev();
      } else {
         operation.getNext().setPrev(operation.getPrev());
         operation.setNext((GXAOperationWrapperImpl)null);
      }

      operation.setPrev((GXAOperationWrapperImpl)null);
   }

   protected synchronized GXAOperationWrapperImpl getFirstOperation() {
      return this.firstOperation;
   }

   private HashMap getPropertiesForTable(String tableName) {
      if (this.propertyMaps == null) {
         this.propertyMaps = new HashMap();
      }

      HashMap props = (HashMap)this.propertyMaps.get(tableName);
      if (props == null) {
         props = new HashMap();
         this.propertyMaps.put(tableName, props);
      }

      return props;
   }

   public synchronized Object putProperty(String tableName, String key, Object val) {
      return this.getPropertiesForTable(tableName).put(key, val);
   }

   public synchronized Object getProperty(String tableName, String key) {
      return this.getPropertiesForTable(tableName).get(key);
   }

   public synchronized Object removeProperty(String tableName, String key) {
      return this.getPropertiesForTable(tableName).remove(key);
   }

   protected synchronized boolean loopOperationCallbacks(int pass, int type) {
      boolean ok = true;
      synchronized(this.commitFailedLock) {
         if (this.commitFailed) {
            return false;
         }

         if (type == 32) {
            this.commitFailed = true;
         }
      }

      for(GXAOperationWrapperImpl operation = this.getFirstOperation(); operation != null; operation = operation.getNext()) {
         switch (type) {
            case 1:
               ok &= operation.onPrepare(pass, true);
               break;
            case 2:
               ok &= operation.onPrepare(pass, false);
               break;
            case 3:
            case 4:
               operation.onCommit(pass);
               break;
            case 5:
               operation.onRollback(pass);
               break;
            default:
               throw new IllegalStateException("Programming error.");
         }
      }

      return ok;
   }
}
