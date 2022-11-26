package org.apache.openjpa.kernel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.openjpa.event.OrphanedKeyAction;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.Proxy;

public abstract class AbstractPCData implements PCData {
   public static final Object NULL = new Object();
   private static final Object[] EMPTY_ARRAY = new Object[0];

   public abstract BitSet getLoaded();

   public abstract AbstractPCData newEmbeddedPCData(OpenJPAStateManager var1);

   public boolean isLoaded(int field) {
      return this.getLoaded().get(field);
   }

   protected Object toField(OpenJPAStateManager sm, FieldMetaData fmd, Object data, FetchConfiguration fetch, Object context) {
      if (data == null) {
         return null;
      } else {
         int idx;
         switch (fmd.getDeclaredTypeCode()) {
            case 11:
               idx = Array.getLength(data);
               Object a = Array.newInstance(fmd.getElement().getDeclaredType(), idx);
               if (idx == 0) {
                  return a;
               }

               if (this.isImmutableType(fmd.getElement())) {
                  System.arraycopy(data, 0, a, 0, idx);
               } else {
                  for(int i = 0; i < idx; ++i) {
                     Array.set(a, i, this.toNestedField(sm, fmd.getElement(), Array.get(data, i), fetch, context));
                  }
               }

               return a;
            case 12:
               ProxyDataList c = (ProxyDataList)data;
               Collection c2 = (Collection)sm.newFieldProxy(fmd.getIndex());
               c2 = this.toNestedFields(sm, fmd.getElement(), (Collection)data, fetch, context);
               if (c2 instanceof Proxy) {
                  ChangeTracker ct = ((Proxy)c2).getChangeTracker();
                  if (ct != null) {
                     ct.setNextSequence(c.nextSequence);
                  }
               }

               return c2;
            case 13:
               Map m = (Map)data;
               Map m2 = (Map)sm.newFieldProxy(fmd.getIndex());
               Collection keys = new ArrayList(m.size());
               Collection values = new ArrayList(m.size());
               Iterator mi = m.entrySet().iterator();

               while(mi.hasNext()) {
                  Map.Entry e = (Map.Entry)mi.next();
                  keys.add(e.getKey());
                  values.add(e.getValue());
               }

               Object[] keyArray = keys.toArray();
               Object[] valueArray = this.toNestedFields(sm, fmd.getElement(), values, fetch, context).toArray();

               for(idx = 0; idx < keyArray.length; ++idx) {
                  m2.put(keyArray[idx], valueArray[idx]);
               }

               return m2;
            default:
               return this.toNestedField(sm, fmd, data, fetch, context);
         }
      }
   }

   protected Object toNestedField(OpenJPAStateManager sm, ValueMetaData vmd, Object data, FetchConfiguration fetch, Object context) {
      if (data == null) {
         return null;
      } else {
         switch (vmd.getDeclaredTypeCode()) {
            case 14:
               return ((Date)data).clone();
            case 15:
               if (vmd.isEmbedded()) {
                  return this.toEmbeddedField(sm, vmd, data, fetch, context);
               }
            case 27:
               Object ret = this.toRelationField(sm, vmd, data, fetch, context);
               if (ret != null) {
                  return ret;
               }

               OrphanedKeyAction action = sm.getContext().getConfiguration().getOrphanedKeyActionInstance();
               return action.orphan(data, sm, vmd);
            case 26:
               return (Locale)data;
            default:
               return data;
         }
      }
   }

   protected Collection toNestedFields(OpenJPAStateManager sm, ValueMetaData vmd, Collection data, FetchConfiguration fetch, Object context) {
      if (data == null) {
         return null;
      } else {
         Collection ret = new ArrayList(data.size());
         Iterator itr;
         switch (vmd.getDeclaredTypeCode()) {
            case 14:
               itr = data.iterator();

               while(itr.hasNext()) {
                  ret.add(((Date)itr.next()).clone());
               }

               return ret;
            case 15:
               if (vmd.isEmbedded()) {
                  itr = data.iterator();

                  while(itr.hasNext()) {
                     ret.add(this.toEmbeddedField(sm, vmd, itr.next(), fetch, context));
                  }
               }
            case 27:
               Object[] r = this.toRelationFields(sm, data, fetch);
               if (r != null) {
                  for(int i = 0; i < r.length; ++i) {
                     if (r[i] != null) {
                        ret.add(r[i]);
                     } else {
                        ret.add(sm.getContext().getConfiguration().getOrphanedKeyActionInstance().orphan(data, sm, vmd));
                     }
                  }

                  return ret;
               }
               break;
            case 26:
               itr = data.iterator();

               while(itr.hasNext()) {
                  ret.add((Locale)itr.next());
               }

               return ret;
         }

         return data;
      }
   }

   protected Object toRelationField(OpenJPAStateManager sm, ValueMetaData vmd, Object data, FetchConfiguration fetch, Object context) {
      return sm.getContext().find(data, fetch, (BitSet)null, (Object)null, 0);
   }

   protected Object[] toRelationFields(OpenJPAStateManager sm, Object data, FetchConfiguration fetch) {
      return sm.getContext().findAll((Collection)data, fetch, (BitSet)null, (Object)null, 0);
   }

   protected Object toEmbeddedField(OpenJPAStateManager sm, ValueMetaData vmd, Object data, FetchConfiguration fetch, Object context) {
      AbstractPCData pcdata = (AbstractPCData)data;
      OpenJPAStateManager embedded = sm.getContext().embed((Object)null, pcdata.getId(), sm, vmd);
      pcdata.load(embedded, (BitSet)pcdata.getLoaded().clone(), fetch, context);
      return embedded.getManagedInstance();
   }

   protected Object toData(FieldMetaData fmd, Object val, StoreContext ctx) {
      if (val == null) {
         return null;
      } else {
         switch (fmd.getDeclaredTypeCode()) {
            case 11:
               int length = Array.getLength(val);
               if (length == 0) {
                  return EMPTY_ARRAY;
               }

               Object a;
               if (this.isImmutableType(fmd.getElement())) {
                  a = Array.newInstance(fmd.getElement().getDeclaredType(), length);
                  System.arraycopy(val, 0, a, 0, length);
               } else {
                  Object[] data = new Object[length];

                  for(int i = 0; i < length; ++i) {
                     data[i] = this.toNestedData(fmd.getElement(), Array.get(val, i), ctx);
                  }

                  a = data;
               }

               return a;
            case 12:
               Collection c = (Collection)val;
               if (c.isEmpty()) {
                  return AbstractPCData.ProxyDataList.EMPTY_LIST;
               } else {
                  ProxyDataList c2 = null;

                  for(Iterator ci = c.iterator(); ci.hasNext(); c2.add(val)) {
                     val = this.toNestedData(fmd.getElement(), ci.next(), ctx);
                     if (val == NULL) {
                        return NULL;
                     }

                     if (c2 == null) {
                        int size = c.size();
                        c2 = new ProxyDataList(size);
                        if (c instanceof Proxy) {
                           ChangeTracker ct = ((Proxy)c).getChangeTracker();
                           if (ct != null) {
                              c2.nextSequence = ct.getNextSequence();
                           }
                        } else {
                           c2.nextSequence = size;
                        }
                     }
                  }

                  return c2;
               }
            case 13:
               Map m = (Map)val;
               if (m.isEmpty()) {
                  return Collections.EMPTY_MAP;
               } else {
                  Map m2 = null;

                  Object val2;
                  for(Iterator mi = m.entrySet().iterator(); mi.hasNext(); m2.put(val, val2)) {
                     Map.Entry e = (Map.Entry)mi.next();
                     val = this.toNestedData(fmd.getKey(), e.getKey(), ctx);
                     if (val == NULL) {
                        return NULL;
                     }

                     val2 = this.toNestedData(fmd.getElement(), e.getValue(), ctx);
                     if (val2 == NULL) {
                        return NULL;
                     }

                     if (m2 == null) {
                        m2 = new HashMap(m.size());
                     }
                  }

                  return m2;
               }
            default:
               return this.toNestedData(fmd, val, ctx);
         }
      }
   }

   private boolean isImmutableType(ValueMetaData element) {
      switch (element.getDeclaredTypeCode()) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 10:
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
            return true;
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return false;
      }
   }

   protected Object toNestedData(ValueMetaData vmd, Object val, StoreContext ctx) {
      if (val == null) {
         return null;
      } else {
         switch (vmd.getDeclaredTypeCode()) {
            case 8:
               if (val instanceof Proxy) {
                  return ((Proxy)val).copy(val);
               }

               return val;
            case 14:
               if (val instanceof Proxy) {
                  return ((Proxy)val).copy(val);
               }

               return ((Date)val).clone();
            case 15:
               if (vmd.isEmbedded()) {
                  return this.toEmbeddedData(val, ctx);
               }
            case 27:
               return this.toRelationData(val, ctx);
            case 26:
               return (Locale)val;
            default:
               return val;
         }
      }
   }

   protected Object toRelationData(Object val, StoreContext ctx) {
      return ctx.getObjectId(val);
   }

   protected Object toEmbeddedData(Object val, StoreContext ctx) {
      if (ctx == null) {
         return NULL;
      } else {
         OpenJPAStateManager sm = ctx.getStateManager(val);
         if (sm == null) {
            return NULL;
         } else {
            ctx.retrieve(val, false, (OpCallbacks)null);
            PCData pcdata = this.newEmbeddedPCData(sm);
            pcdata.store(sm);
            return pcdata;
         }
      }
   }

   private static class ProxyDataList extends ArrayList {
      public static final ProxyDataList EMPTY_LIST = new ProxyDataList(0);
      public int nextSequence = 0;

      public ProxyDataList(int size) {
         super(size);
      }
   }
}
