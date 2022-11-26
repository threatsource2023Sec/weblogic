package org.apache.openjpa.kernel;

import java.util.BitSet;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

public class PCDataImpl extends AbstractPCData {
   private final Object _oid;
   private final Class _type;
   private final Object[] _data;
   private final BitSet _loaded;
   private Object _version = null;
   private Object _impl = null;
   private Object[] _fieldImpl = null;

   public PCDataImpl(Object oid, ClassMetaData meta) {
      this._oid = oid;
      this._type = meta.getDescribedType();
      int len = meta.getFields().length;
      this._data = new Object[len];
      this._loaded = new BitSet(len);
   }

   public Object getId() {
      return this._oid;
   }

   public Class getType() {
      return this._type;
   }

   public BitSet getLoaded() {
      return this._loaded;
   }

   public Object getData(int index) {
      return this._loaded.get(index) ? this._data[index] : null;
   }

   public void setData(int index, Object val) {
      this._loaded.set(index);
      this._data[index] = val;
   }

   public void clearData(int index) {
      this._loaded.clear(index);
      this._data[index] = null;
   }

   public Object getImplData() {
      return this._impl;
   }

   public void setImplData(Object val) {
      this._impl = val;
   }

   public Object getImplData(int index) {
      return this._fieldImpl != null ? this._fieldImpl[index] : null;
   }

   public void setImplData(int index, Object val) {
      if (val != null) {
         if (this._fieldImpl == null) {
            this._fieldImpl = new Object[this._data.length];
         }

         this._fieldImpl[index] = val;
      } else if (this._fieldImpl != null) {
         this._fieldImpl[index] = null;
      }

   }

   public Object getIntermediate(int index) {
      return !this._loaded.get(index) ? this._data[index] : null;
   }

   public void setIntermediate(int index, Object val) {
      this._loaded.clear(index);
      this._data[index] = val;
   }

   public boolean isLoaded(int index) {
      return this._loaded.get(index);
   }

   public void setLoaded(int index, boolean loaded) {
      if (loaded) {
         this._loaded.set(index);
      } else {
         this._loaded.clear(index);
      }

   }

   public Object getVersion() {
      return this._version;
   }

   public void setVersion(Object version) {
      this._version = version;
   }

   public void load(OpenJPAStateManager sm, FetchConfiguration fetch, Object context) {
      this.loadVersion(sm);
      this.loadImplData(sm);
      FieldMetaData[] fmds = sm.getMetaData().getFields();
      ((StateManagerImpl)sm).setLoading(true);

      for(int i = 0; i < fmds.length; ++i) {
         if (!this.isLoaded(i)) {
            this.loadIntermediate(sm, fmds[i]);
         } else if (!sm.getLoaded().get(i) && fetch.requiresFetch(fmds[i]) != 0) {
            this.loadField(sm, fmds[i], fetch, context);
         }
      }

   }

   public void load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, Object context) {
      this.loadVersion(sm);
      this.loadImplData(sm);
      int len = fields == null ? 0 : fields.length();

      for(int i = 0; i < len; ++i) {
         if (fields.get(i)) {
            FieldMetaData fmd = sm.getMetaData().getField(i);
            if (!this.isLoaded(i)) {
               this.loadIntermediate(sm, fmd);
            } else {
               this.loadField(sm, fmd, fetch, context);
               this.loadImplData(sm, fmd);
               fields.clear(i);
            }
         }
      }

   }

   protected void loadVersion(OpenJPAStateManager sm) {
      if (sm.getVersion() == null) {
         sm.setVersion(this.getVersion());
      }

   }

   protected void loadImplData(OpenJPAStateManager sm) {
      Object impl = this.getImplData();
      if (sm.getImplData() == null && impl != null) {
         sm.setImplData(impl, true);
      }

   }

   protected void loadField(OpenJPAStateManager sm, FieldMetaData fmd, FetchConfiguration fetch, Object context) {
      int index = fmd.getIndex();
      Object val = this.toField(sm, fmd, this.getData(index), fetch, context);
      sm.storeField(index, val);
   }

   protected void loadImplData(OpenJPAStateManager sm, FieldMetaData fmd) {
      int index = fmd.getIndex();
      Object impl = this.getImplData(index);
      if (impl != null) {
         sm.setImplData(index, impl);
      }

   }

   protected void loadIntermediate(OpenJPAStateManager sm, FieldMetaData fmd) {
      int index = fmd.getIndex();
      Object inter = this.getIntermediate(index);
      if (inter != null && !sm.getLoaded().get(index)) {
         sm.setIntermediate(index, inter);
      }

   }

   public void store(OpenJPAStateManager sm) {
      this.storeVersion(sm);
      this.storeImplData(sm);
      FieldMetaData[] fmds = sm.getMetaData().getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (sm.getLoaded().get(i)) {
            this.storeField(sm, fmds[i]);
            this.storeImplData(sm, fmds[i], this.isLoaded(i));
         } else if (!this.isLoaded(i)) {
            this.storeIntermediate(sm, fmds[i]);
         }
      }

   }

   public void store(OpenJPAStateManager sm, BitSet fields) {
      this.storeVersion(sm);
      this.storeImplData(sm);
      FieldMetaData[] fmds = sm.getMetaData().getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fields != null && fields.get(i)) {
            this.storeField(sm, fmds[i]);
            this.storeImplData(sm, fmds[i], this.isLoaded(i));
         } else if (!this.isLoaded(i)) {
            this.storeIntermediate(sm, fmds[i]);
         }
      }

   }

   protected void storeVersion(OpenJPAStateManager sm) {
      this.setVersion(sm.getVersion());
   }

   protected void storeImplData(OpenJPAStateManager sm) {
      if (sm.isImplDataCacheable()) {
         this.setImplData(sm.getImplData());
      }

   }

   protected void storeField(OpenJPAStateManager sm, FieldMetaData fmd) {
      if (fmd.getManagement() == 3) {
         int index = fmd.getIndex();
         Object val = this.toData(fmd, sm.fetchField(index, false), sm.getContext());
         if (val != NULL) {
            this.setData(index, val);
         } else {
            this.clearData(index);
         }

      }
   }

   protected void storeIntermediate(OpenJPAStateManager sm, FieldMetaData fmd) {
      int index = fmd.getIndex();
      Object val = sm.getIntermediate(index);
      if (val != null) {
         this.setIntermediate(index, val);
      }

   }

   protected void storeImplData(OpenJPAStateManager sm, FieldMetaData fmd, boolean fieldLoaded) {
      int index = fmd.getIndex();
      if (fieldLoaded) {
         Object impl = sm.getImplData(index);
         if (impl != null && sm.isImplDataCacheable(index)) {
            this.setImplData(index, impl);
         }
      } else {
         this.setImplData(index, (Object)null);
      }

   }

   public AbstractPCData newEmbeddedPCData(OpenJPAStateManager sm) {
      return new PCDataImpl(sm.getId(), sm.getMetaData());
   }
}
