package org.apache.openjpa.kernel;

import java.util.BitSet;
import java.util.Collection;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

public abstract class DelegatingStoreManager implements StoreManager {
   private final StoreManager _store;
   private final DelegatingStoreManager _del;

   public DelegatingStoreManager(StoreManager store) {
      this._store = store;
      if (store instanceof DelegatingStoreManager) {
         this._del = (DelegatingStoreManager)this._store;
      } else {
         this._del = null;
      }

   }

   public StoreManager getDelegate() {
      return this._store;
   }

   public StoreManager getInnermostDelegate() {
      return this._del == null ? this._store : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingStoreManager) {
            other = ((DelegatingStoreManager)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   public void setContext(StoreContext ctx) {
      this._store.setContext(ctx);
   }

   public void beginOptimistic() {
      this._store.beginOptimistic();
   }

   public void rollbackOptimistic() {
      this._store.rollbackOptimistic();
   }

   public void begin() {
      this._store.begin();
   }

   public void commit() {
      this._store.commit();
   }

   public void rollback() {
      this._store.rollback();
   }

   public boolean exists(OpenJPAStateManager sm, Object context) {
      return this._store.exists(sm, context);
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object context) {
      return this._store.syncVersion(sm, context);
   }

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object context) {
      return this._store.initialize(sm, state, fetch, context);
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object context) {
      return this._store.load(sm, fields, fetch, lockLevel, context);
   }

   public Collection loadAll(Collection sms, PCState state, int load, FetchConfiguration fetch, Object context) {
      return this._store.loadAll(sms, state, load, fetch, context);
   }

   public void beforeStateChange(OpenJPAStateManager sm, PCState fromState, PCState toState) {
      this._store.beforeStateChange(sm, fromState, toState);
   }

   public Collection flush(Collection sms) {
      return this._store.flush(sms);
   }

   public boolean assignObjectId(OpenJPAStateManager sm, boolean preFlush) {
      return this._store.assignObjectId(sm, preFlush);
   }

   public boolean assignField(OpenJPAStateManager sm, int field, boolean preFlush) {
      return this._store.assignField(sm, field, preFlush);
   }

   public Class getManagedType(Object oid) {
      return this._store.getManagedType(oid);
   }

   public Class getDataStoreIdType(ClassMetaData meta) {
      return this._store.getDataStoreIdType(meta);
   }

   public Object copyDataStoreId(Object oid, ClassMetaData meta) {
      return this._store.copyDataStoreId(oid, meta);
   }

   public Object newDataStoreId(Object oidVal, ClassMetaData meta) {
      return this._store.newDataStoreId(oidVal, meta);
   }

   public Object getClientConnection() {
      return this._store.getClientConnection();
   }

   public void retainConnection() {
      this._store.retainConnection();
   }

   public void releaseConnection() {
      this._store.releaseConnection();
   }

   public ResultObjectProvider executeExtent(ClassMetaData meta, boolean subclasses, FetchConfiguration fetch) {
      return this._store.executeExtent(meta, subclasses, fetch);
   }

   public StoreQuery newQuery(String language) {
      return this._store.newQuery(language);
   }

   public FetchConfiguration newFetchConfiguration() {
      return this._store.newFetchConfiguration();
   }

   public void close() {
      this._store.close();
   }

   public int compareVersion(OpenJPAStateManager state, Object v1, Object v2) {
      return this._store.compareVersion(state, v1, v2);
   }

   public Seq getDataStoreIdSequence(ClassMetaData forClass) {
      return this._store.getDataStoreIdSequence(forClass);
   }

   public Seq getValueSequence(FieldMetaData fmd) {
      return this._store.getValueSequence(fmd);
   }

   public boolean cancelAll() {
      return this._store.cancelAll();
   }
}
