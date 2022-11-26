package org.glassfish.tyrus.core.uri.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMultivaluedMap implements MultivaluedMap {
   protected final Map store;

   public AbstractMultivaluedMap(Map store) {
      if (store == null) {
         throw new NullPointerException("Underlying store must not be 'null'.");
      } else {
         this.store = store;
      }
   }

   public final void putSingle(Object key, Object value) {
      List values = this.getValues(key);
      values.clear();
      if (value != null) {
         values.add(value);
      } else {
         this.addNull(values);
      }

   }

   protected void addNull(List values) {
   }

   protected void addFirstNull(List values) {
   }

   public final void add(Object key, Object value) {
      List values = this.getValues(key);
      if (value != null) {
         values.add(value);
      } else {
         this.addNull(values);
      }

   }

   public final void addAll(Object key, Object... newValues) {
      if (newValues == null) {
         throw new NullPointerException("Supplied array of values must not be null.");
      } else if (newValues.length != 0) {
         List values = this.getValues(key);
         Object[] var4 = newValues;
         int var5 = newValues.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Object value = var4[var6];
            if (value != null) {
               values.add(value);
            } else {
               this.addNull(values);
            }
         }

      }
   }

   public final void addAll(Object key, List valueList) {
      if (valueList == null) {
         throw new NullPointerException("Supplied list of values must not be null.");
      } else if (!valueList.isEmpty()) {
         List values = this.getValues(key);
         Iterator var4 = valueList.iterator();

         while(var4.hasNext()) {
            Object value = var4.next();
            if (value != null) {
               values.add(value);
            } else {
               this.addNull(values);
            }
         }

      }
   }

   public final Object getFirst(Object key) {
      List values = (List)this.store.get(key);
      return values != null && values.size() > 0 ? values.get(0) : null;
   }

   public final void addFirst(Object key, Object value) {
      List values = this.getValues(key);
      if (value != null) {
         values.add(0, value);
      } else {
         this.addFirstNull(values);
      }

   }

   protected final List getValues(Object key) {
      List l = (List)this.store.get(key);
      if (l == null) {
         l = new LinkedList();
         this.store.put(key, l);
      }

      return (List)l;
   }

   public String toString() {
      return this.store.toString();
   }

   public int hashCode() {
      return this.store.hashCode();
   }

   public boolean equals(Object o) {
      return this.store.equals(o);
   }

   public Collection values() {
      return this.store.values();
   }

   public int size() {
      return this.store.size();
   }

   public List remove(Object key) {
      return (List)this.store.remove(key);
   }

   public void putAll(Map m) {
      this.store.putAll(m);
   }

   public List put(Object key, List value) {
      return (List)this.store.put(key, value);
   }

   public Set keySet() {
      return this.store.keySet();
   }

   public boolean isEmpty() {
      return this.store.isEmpty();
   }

   public List get(Object key) {
      return (List)this.store.get(key);
   }

   public Set entrySet() {
      return this.store.entrySet();
   }

   public boolean containsValue(Object value) {
      return this.store.containsValue(value);
   }

   public boolean containsKey(Object key) {
      return this.store.containsKey(key);
   }

   public void clear() {
      this.store.clear();
   }

   public boolean equalsIgnoreValueOrder(MultivaluedMap omap) {
      if (this == omap) {
         return true;
      } else if (!this.keySet().equals(omap.keySet())) {
         return false;
      } else {
         Iterator var2 = this.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry e = (Map.Entry)var2.next();
            List olist = (List)omap.get(e.getKey());
            if (((List)e.getValue()).size() != olist.size()) {
               return false;
            }

            Iterator var5 = ((List)e.getValue()).iterator();

            while(var5.hasNext()) {
               Object v = var5.next();
               if (!olist.contains(v)) {
                  return false;
               }
            }
         }

         return true;
      }
   }
}
