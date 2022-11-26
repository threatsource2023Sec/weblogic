package org.apache.openjpa.datacache;

import java.util.BitSet;
import org.apache.openjpa.kernel.AbstractPCData;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCDataImpl;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;

public class DataCachePCDataImpl extends PCDataImpl implements DataCachePCData {
   private final long _exp;

   public DataCachePCDataImpl(Object oid, ClassMetaData meta) {
      super(oid, meta);
      int timeout = meta.getDataCacheTimeout();
      if (timeout > 0) {
         this._exp = System.currentTimeMillis() + (long)timeout;
      } else {
         this._exp = -1L;
      }

   }

   public boolean isTimedOut() {
      return this._exp != -1L && this._exp < System.currentTimeMillis();
   }

   public synchronized Object getData(int index) {
      return super.getData(index);
   }

   public synchronized void setData(int index, Object val) {
      super.setData(index, val);
   }

   public synchronized void clearData(int index) {
      super.clearData(index);
   }

   public synchronized Object getImplData() {
      return super.getImplData();
   }

   public synchronized void setImplData(Object val) {
      super.setImplData(val);
   }

   public synchronized Object getImplData(int index) {
      return super.getImplData(index);
   }

   public synchronized void setImplData(int index, Object val) {
      super.setImplData(index, val);
   }

   public synchronized Object getIntermediate(int index) {
      return super.getIntermediate(index);
   }

   public synchronized void setIntermediate(int index, Object val) {
      super.setIntermediate(index, val);
   }

   public synchronized boolean isLoaded(int index) {
      return super.isLoaded(index);
   }

   public synchronized void setLoaded(int index, boolean loaded) {
      super.setLoaded(index, loaded);
   }

   public synchronized Object getVersion() {
      return super.getVersion();
   }

   public synchronized void setVersion(Object version) {
      super.setVersion(version);
   }

   public synchronized void store(OpenJPAStateManager sm) {
      super.store(sm);
   }

   public synchronized void store(OpenJPAStateManager sm, BitSet fields) {
      super.store(sm, fields);
   }

   protected void storeField(OpenJPAStateManager sm, FieldMetaData fmd) {
      if (fmd.getManagement() == 3) {
         int index = fmd.getIndex();
         if (fmd.getOrders().length > 0) {
            if (sm.getPCState() == PCState.PNEW) {
               return;
            }

            if (sm.getPCState() == PCState.PDIRTY) {
               this.clearData(index);
               return;
            }
         }

         super.storeField(sm, fmd);
         if (sm.getPCState() == PCState.PDIRTY && fmd.isUsedInOrderBy()) {
            this.clearInverseRelationCache(sm, fmd);
         }

      }
   }

   protected void clearInverseRelationCache(OpenJPAStateManager sm, FieldMetaData fmd) {
      ClassMetaData cmd = sm.getMetaData();
      FieldMetaData[] fields = cmd.getFields();

      for(int i = 0; i < fields.length; ++i) {
         FieldMetaData[] inverses = fields[i].getInverseMetaDatas();
         if (inverses.length != 0) {
            for(int j = 0; j < inverses.length; ++j) {
               if (inverses[j].getOrderDeclaration().indexOf(fmd.getName()) != -1) {
                  DataCache cache = sm.getMetaData().getDataCache();
                  Object oid = sm.getContext().getObjectId(sm.fetch(i));
                  DataCachePCData data = cache == null ? null : cache.get(oid);
                  if (data != null && data instanceof DataCachePCDataImpl) {
                     ((DataCachePCDataImpl)data).clearData(inverses[j].getIndex());
                  }
               }
            }
         }
      }

   }

   protected Object toData(FieldMetaData fmd, Object val, StoreContext ctx) {
      return !fmd.isLRS() && !fmd.isStream() ? super.toData(fmd, val, ctx) : NULL;
   }

   protected Object toNestedData(ValueMetaData vmd, Object val, StoreContext ctx) {
      if (val == null) {
         return null;
      } else {
         switch (vmd.getDeclaredTypeCode()) {
            case 11:
            case 12:
            case 13:
               return NULL;
            default:
               return super.toNestedData(vmd, val, ctx);
         }
      }
   }

   public AbstractPCData newEmbeddedPCData(OpenJPAStateManager sm) {
      return new DataCachePCDataImpl(sm.getId(), sm.getMetaData());
   }
}
