package org.apache.openjpa.abstractstore;

import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FetchConfigurationImpl;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.ImplHelper;

public abstract class AbstractStoreManager implements StoreManager {
   protected StoreContext ctx;

   public final void setContext(StoreContext ctx) {
      this.ctx = ctx;
      this.open();
   }

   public StoreContext getContext() {
      return this.ctx;
   }

   protected void open() {
   }

   public void beginOptimistic() {
   }

   public void rollbackOptimistic() {
   }

   public void begin() {
   }

   public void commit() {
   }

   public void rollback() {
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object edata) {
      return true;
   }

   public abstract boolean initialize(OpenJPAStateManager var1, PCState var2, FetchConfiguration var3, Object var4);

   public abstract boolean load(OpenJPAStateManager var1, BitSet var2, FetchConfiguration var3, int var4, Object var5);

   public Collection loadAll(Collection sms, PCState state, int load, FetchConfiguration fetch, Object edata) {
      return ImplHelper.loadAll(sms, this, state, load, fetch, edata);
   }

   public Collection flush(Collection sms) {
      Collection pNew = new LinkedList();
      Collection pNewUpdated = new LinkedList();
      Collection pNewFlushedDeleted = new LinkedList();
      Collection pDirty = new LinkedList();
      Collection pDeleted = new LinkedList();
      Iterator itr = sms.iterator();

      while(true) {
         while(itr.hasNext()) {
            OpenJPAStateManager sm = (OpenJPAStateManager)itr.next();
            if (sm.getPCState() == PCState.PNEW && !sm.isFlushed()) {
               pNew.add(sm);
            } else if (sm.getPCState() == PCState.PNEW && sm.isFlushed()) {
               pNewUpdated.add(sm);
            } else if (sm.getPCState() == PCState.PNEWFLUSHEDDELETED) {
               pNewFlushedDeleted.add(sm);
            } else if (sm.getPCState() == PCState.PDIRTY) {
               pDirty.add(sm);
            } else if (sm.getPCState() == PCState.PDELETED) {
               pDeleted.add(sm);
            }
         }

         if (pNew.isEmpty() && pNewUpdated.isEmpty() && pNewFlushedDeleted.isEmpty() && pDirty.isEmpty() && pDeleted.isEmpty()) {
            return Collections.EMPTY_LIST;
         }

         return this.flush(pNew, pNewUpdated, pNewFlushedDeleted, pDirty, pDeleted);
      }
   }

   public void beforeStateChange(OpenJPAStateManager sm, PCState fromState, PCState toState) {
   }

   public boolean assignObjectId(OpenJPAStateManager sm, boolean preFlush) {
      ClassMetaData meta = sm.getMetaData();
      if (meta.getIdentityType() == 2) {
         return ApplicationIds.assign(sm, this, preFlush);
      } else {
         Object val = ImplHelper.generateIdentityValue(this.ctx, meta, 6);
         return this.assignDataStoreId(sm, val);
      }
   }

   protected boolean assignDataStoreId(OpenJPAStateManager sm, Object val) {
      ClassMetaData meta = sm.getMetaData();
      if (val == null && meta.getIdentityStrategy() != 1) {
         return false;
      } else {
         if (val == null) {
            val = this.getDataStoreIdSequence(meta).next(this.ctx, meta);
         }

         sm.setObjectId(this.newDataStoreId(val, meta));
         return true;
      }
   }

   public boolean assignField(OpenJPAStateManager sm, int field, boolean preFlush) {
      FieldMetaData fmd = sm.getMetaData().getField(field);
      Object val = ImplHelper.generateFieldValue(this.ctx, fmd);
      if (val == null) {
         return false;
      } else {
         sm.store(field, val);
         return true;
      }
   }

   public Class getManagedType(Object oid) {
      return oid instanceof Id ? ((Id)oid).getType() : null;
   }

   public Class getDataStoreIdType(ClassMetaData meta) {
      return Id.class;
   }

   public Object copyDataStoreId(Object oid, ClassMetaData meta) {
      Id id = (Id)oid;
      return new Id(meta.getDescribedType(), id.getId(), id.hasSubclasses());
   }

   public Object newDataStoreId(Object val, ClassMetaData meta) {
      while(meta.getPCSuperclass() != null) {
         meta = meta.getPCSuperclassMetaData();
      }

      return Id.newInstance(meta.getDescribedType(), val);
   }

   public void retainConnection() {
   }

   public void releaseConnection() {
   }

   public Object getClientConnection() {
      return null;
   }

   public abstract ResultObjectProvider executeExtent(ClassMetaData var1, boolean var2, FetchConfiguration var3);

   public StoreQuery newQuery(String language) {
      return null;
   }

   public FetchConfiguration newFetchConfiguration() {
      return new FetchConfigurationImpl();
   }

   public int compareVersion(OpenJPAStateManager state, Object v1, Object v2) {
      if (v1 != null && v2 != null) {
         int compare = ((Comparable)v1).compareTo((Comparable)v2);
         if (compare < 0) {
            return 2;
         } else {
            return compare == 0 ? 3 : 1;
         }
      } else {
         return 4;
      }
   }

   public Seq getDataStoreIdSequence(ClassMetaData forClass) {
      return this.ctx.getConfiguration().getSequenceInstance();
   }

   public Seq getValueSequence(FieldMetaData forField) {
      return null;
   }

   public boolean cancelAll() {
      return false;
   }

   public void close() {
   }

   protected abstract Collection flush(Collection var1, Collection var2, Collection var3, Collection var4, Collection var5);

   protected OpenJPAConfiguration newConfiguration() {
      return new OpenJPAConfigurationImpl();
   }

   protected Collection getUnsupportedOptions() {
      Collection c = new HashSet();
      c.add("openjpa.option.Optimistic");
      c.add("openjpa.option.DatastoreIdentity");
      c.add("openjpa.option.IncrementalFlush");
      c.add("openjpa.option.AutoassignValue");
      c.add("openjpa.option.IncrementValue");
      c.add("openjpa.option.DataStoreConnection");
      return c;
   }

   protected String getPlatform() {
      return this.getClass().getName();
   }
}
