package org.apache.openjpa.xmlstore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.event.OrphanedKeyAction;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.Proxy;
import org.apache.openjpa.util.UnsupportedException;
import serp.util.Numbers;

public final class ObjectData implements Cloneable {
   private Object _oid;
   private Object[] _data;
   private Long _version;
   private ClassMetaData _meta;

   public ObjectData(Object oid, ClassMetaData meta) {
      this._oid = oid;
      this._meta = meta;
      this._data = new Object[meta.getFields().length];
   }

   public Object getId() {
      return this._oid;
   }

   public Object getField(int num) {
      return this._data[num];
   }

   public void setField(int num, Object val) {
      this._data[num] = val;
   }

   public void setVersion(Long version) {
      this._version = version;
   }

   public Long getVersion() {
      return this._version;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void load(OpenJPAStateManager sm, FetchConfiguration fetch) {
      if (sm.getVersion() == null) {
         sm.setVersion(this._version);
      }

      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (!sm.getLoaded().get(i) && fetch.requiresFetch(fmds[i]) != 0) {
            sm.store(i, toLoadable(sm, fmds[i], this._data[i], fetch));
         }
      }

   }

   public void load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch) {
      if (sm.getVersion() == null) {
         sm.setVersion(this._version);
      }

      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fields.get(i)) {
            sm.store(i, toLoadable(sm, fmds[i], this._data[i], fetch));
         }
      }

   }

   private static Object toLoadable(OpenJPAStateManager sm, FieldMetaData fmd, Object val, FetchConfiguration fetch) {
      if (val == null) {
         return null;
      } else {
         Collection c;
         switch (fmd.getTypeCode()) {
            case 11:
               c = (Collection)val;
               Object a = Array.newInstance(fmd.getElement().getType(), c.size());
               int idx = 0;

               for(Iterator itr = c.iterator(); itr.hasNext(); ++idx) {
                  Array.set(a, idx, toNestedLoadable(sm, fmd.getElement(), itr.next(), fetch));
               }

               return a;
            case 12:
               c = (Collection)val;
               Collection c2 = (Collection)sm.newFieldProxy(fmd.getIndex());
               Iterator itr = c.iterator();

               while(itr.hasNext()) {
                  c2.add(toNestedLoadable(sm, fmd.getElement(), itr.next(), fetch));
               }

               return c2;
            case 13:
               Map m = (Map)val;
               Map m2 = (Map)sm.newFieldProxy(fmd.getIndex());
               Iterator itr = m.entrySet().iterator();

               while(itr.hasNext()) {
                  Map.Entry e = (Map.Entry)itr.next();
                  m2.put(toNestedLoadable(sm, fmd.getKey(), e.getKey(), fetch), toNestedLoadable(sm, fmd.getElement(), e.getValue(), fetch));
               }

               return m2;
            default:
               return toNestedLoadable(sm, fmd, val, fetch);
         }
      }
   }

   private static Object toNestedLoadable(OpenJPAStateManager sm, ValueMetaData vmd, Object val, FetchConfiguration fetch) {
      if (val == null) {
         return null;
      } else {
         switch (vmd.getTypeCode()) {
            case 14:
               return ((Date)val).clone();
            case 15:
            case 27:
               StoreContext ctx = sm.getContext();
               Object pc = ctx.find(val, fetch, (BitSet)null, (Object)null, 0);
               if (pc != null) {
                  return pc;
               }

               OrphanedKeyAction action = ctx.getConfiguration().getOrphanedKeyActionInstance();
               return action.orphan(val, sm, vmd);
            default:
               return val;
         }
      }
   }

   public void store(OpenJPAStateManager sm) {
      this._version = (Long)sm.getVersion();
      if (this._version == null) {
         this._version = Numbers.valueOf(0L);
      }

      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (sm.getDirty().get(i)) {
            int var10000 = fmds[i].getManagement();
            FieldMetaData var10001 = fmds[i];
            if (var10000 == 3) {
               this._data[i] = toStorable(fmds[i], sm.fetch(i), sm.getContext());
            }
         }
      }

   }

   private static Object toStorable(FieldMetaData fmd, Object val, StoreContext ctx) {
      if (val == null) {
         return null;
      } else {
         switch (fmd.getTypeCode()) {
            case 11:
               Collection c = new ArrayList();
               int i = 0;

               for(int len = Array.getLength(val); i < len; ++i) {
                  c.add(toNestedStorable(fmd.getElement(), Array.get(val, i), ctx));
               }

               return c;
            case 12:
               Collection c = (Collection)val;
               Collection c2 = new ArrayList();
               Iterator itr = c.iterator();

               while(itr.hasNext()) {
                  c2.add(toNestedStorable(fmd.getElement(), itr.next(), ctx));
               }

               return c2;
            case 13:
               Map m = (Map)val;
               Map m2 = new HashMap();
               Iterator itr = m.entrySet().iterator();

               while(itr.hasNext()) {
                  Map.Entry e = (Map.Entry)itr.next();
                  m2.put(toNestedStorable(fmd.getKey(), e.getKey(), ctx), toNestedStorable(fmd.getElement(), e.getValue(), ctx));
               }

               return m2;
            default:
               return toNestedStorable(fmd, val, ctx);
         }
      }
   }

   private static Object toNestedStorable(ValueMetaData vmd, Object val, StoreContext ctx) {
      if (val == null) {
         return null;
      } else {
         switch (vmd.getTypeCode()) {
            case 11:
            case 12:
            case 13:
               throw new UnsupportedException("This store does not support nested containers (e.g. collections of collections).");
            case 14:
               if (val instanceof Proxy) {
                  return ((Proxy)val).copy(val);
               }

               return ((Date)val).clone();
            case 15:
            case 27:
               return ctx.getObjectId(val);
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               return val;
         }
      }
   }

   public Object clone() {
      ObjectData data = new ObjectData(this._oid, this._meta);
      data.setVersion(this._version);
      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         Object val = this._data[i];
         if (val == null) {
            data.setField(i, (Object)null);
         } else {
            switch (fmds[i].getTypeCode()) {
               case 11:
               case 12:
                  data.setField(i, new ArrayList((Collection)val));
                  break;
               case 13:
                  data.setField(i, new HashMap((Map)val));
                  break;
               default:
                  data.setField(i, val);
            }
         }
      }

      return data;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("Class: (" + this._meta.getDescribedType().getName() + ")\n");
      buf.append("Object Id: (" + this._oid + ")\n");
      buf.append("Version: (" + this._version + ")\n");
      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         buf.append("  Field: (" + i + ")\n");
         buf.append("  Name: (" + fmds[i].getName() + ")\n");
         buf.append("  Value: (" + this._data[i] + ")\n");
      }

      return buf.toString();
   }
}
