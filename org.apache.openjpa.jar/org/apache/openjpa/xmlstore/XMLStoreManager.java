package org.apache.openjpa.xmlstore;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.openjpa.abstractstore.AbstractStoreManager;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.lib.rop.ListResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.OptimisticException;
import org.apache.openjpa.util.StoreException;
import serp.util.Numbers;

public class XMLStoreManager extends AbstractStoreManager {
   private XMLConfiguration _conf;
   private XMLStore _store;
   private Collection _updates;
   private Collection _deletes;

   protected Collection getUnsupportedOptions() {
      Collection c = super.getUnsupportedOptions();
      c.remove("openjpa.option.DatastoreIdentity");
      c.remove("openjpa.option.Optimistic");
      c.add("openjpa.option.EmbeddedRelation");
      c.add("openjpa.option.EmbeddedCollectionRelation");
      c.add("openjpa.option.EmbeddedMapRelation");
      return c;
   }

   protected OpenJPAConfiguration newConfiguration() {
      return new XMLConfiguration();
   }

   protected void open() {
      this._conf = (XMLConfiguration)this.ctx.getConfiguration();
      this._store = this._conf.getStore();
   }

   public boolean exists(OpenJPAStateManager sm, Object context) {
      return this._store.getData(sm.getMetaData(), sm.getObjectId()) != null;
   }

   private static void incrementVersion(OpenJPAStateManager sm) {
      long version = 0L;
      if (sm.getVersion() != null) {
         version = (Long)sm.getVersion() + 1L;
      }

      sm.setNextVersion(Numbers.valueOf(version));
   }

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object context) {
      ObjectData data;
      if (context != null) {
         data = (ObjectData)context;
      } else {
         data = this._store.getData(sm.getMetaData(), sm.getObjectId());
      }

      if (data == null) {
         return false;
      } else {
         sm.initialize(data.getMetaData().getDescribedType(), state);
         data.load(sm, fetch);
         return true;
      }
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object context) {
      ObjectData data;
      if (context != null) {
         data = (ObjectData)context;
      } else {
         data = this._store.getData(sm.getMetaData(), sm.getObjectId());
      }

      if (data == null) {
         return false;
      } else {
         data.load(sm, fields, fetch);
         return true;
      }
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object context) {
      if (sm.getVersion() == null) {
         return false;
      } else {
         ObjectData data;
         if (context != null) {
            data = (ObjectData)context;
         } else {
            data = this._store.getData(sm.getMetaData(), sm.getObjectId());
         }

         if (data == null) {
            return false;
         } else if (sm.getVersion().equals(data.getVersion())) {
            return true;
         } else {
            sm.setVersion(data.getVersion());
            return false;
         }
      }
   }

   public void begin() {
      this._store.beginTransaction();
   }

   public void commit() {
      try {
         this._store.endTransaction(this._updates, this._deletes);
      } finally {
         this._updates = null;
         this._deletes = null;
      }

   }

   public void rollback() {
      this._updates = null;
      this._deletes = null;
      this._store.endTransaction((Collection)null, (Collection)null);
   }

   protected Collection flush(Collection pNew, Collection pNewUpdated, Collection pNewFlushedDeleted, Collection pDirty, Collection pDeleted) {
      Collection exceps = new LinkedList();
      this._updates = new ArrayList(pNew.size() + pDirty.size());
      this._deletes = new ArrayList(pDeleted.size());
      Iterator itr = pNew.iterator();

      OpenJPAStateManager sm;
      while(itr.hasNext()) {
         sm = (OpenJPAStateManager)itr.next();
         Object oid = sm.getObjectId();
         ObjectData data = this._store.getData(sm.getMetaData(), oid);
         if (data != null) {
            throw (new StoreException("Attempt to insert new object " + sm.getManagedInstance() + "with the same oid as an existing instance: " + oid)).setFatal(true);
         }

         data = new ObjectData(oid, sm.getMetaData());
         incrementVersion(sm);
         data.store(sm);
         this._updates.add(data);
      }

      itr = pDirty.iterator();

      while(true) {
         ObjectData data;
         while(itr.hasNext()) {
            sm = (OpenJPAStateManager)itr.next();
            data = this._store.getData(sm.getMetaData(), sm.getObjectId());
            if (data != null && data.getVersion().equals(sm.getVersion())) {
               incrementVersion(sm);
               data = (ObjectData)data.clone();
               data.store(sm);
               this._updates.add(data);
            } else {
               exceps.add(new OptimisticException(sm.getManagedInstance()));
            }
         }

         itr = pDeleted.iterator();

         while(itr.hasNext()) {
            sm = (OpenJPAStateManager)itr.next();
            data = this._store.getData(sm.getMetaData(), sm.getObjectId());
            if (data != null) {
               this._deletes.add(data);
            }
         }

         return exceps;
      }
   }

   public ResultObjectProvider executeExtent(ClassMetaData meta, boolean subclasses, FetchConfiguration fetch) {
      ObjectData[] datas = this._store.getData(meta);
      Class candidate = meta.getDescribedType();
      List pcs = new ArrayList(datas.length);

      for(int i = 0; i < datas.length; ++i) {
         Class c = datas[i].getMetaData().getDescribedType();
         if (c == candidate || subclasses && candidate.isAssignableFrom(c)) {
            pcs.add(this.ctx.find(datas[i].getId(), fetch, (BitSet)null, datas[i], 0));
         }
      }

      return new ListResultObjectProvider(pcs);
   }
}
