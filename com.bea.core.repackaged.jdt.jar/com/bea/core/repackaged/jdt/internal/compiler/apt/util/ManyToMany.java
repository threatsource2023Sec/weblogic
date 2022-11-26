package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ManyToMany {
   private final Map _forward = new HashMap();
   private final Map _reverse = new HashMap();
   private boolean _dirty = false;

   public synchronized boolean clear() {
      boolean hadContent = !this._forward.isEmpty() || !this._reverse.isEmpty();
      this._reverse.clear();
      this._forward.clear();
      this._dirty |= hadContent;
      return hadContent;
   }

   public synchronized void clearDirtyBit() {
      this._dirty = false;
   }

   public synchronized boolean containsKey(Object key) {
      return this._forward.containsKey(key);
   }

   public synchronized boolean containsKeyValuePair(Object key, Object value) {
      Set values = (Set)this._forward.get(key);
      return values == null ? false : values.contains(value);
   }

   public synchronized boolean containsValue(Object value) {
      return this._reverse.containsKey(value);
   }

   public synchronized Set getKeys(Object value) {
      Set keys = (Set)this._reverse.get(value);
      return (Set)(keys == null ? Collections.emptySet() : new HashSet(keys));
   }

   public synchronized Set getValues(Object key) {
      Set values = (Set)this._forward.get(key);
      return (Set)(values == null ? Collections.emptySet() : new HashSet(values));
   }

   public synchronized Set getKeySet() {
      Set keys = new HashSet(this._forward.keySet());
      return keys;
   }

   public synchronized Set getValueSet() {
      Set values = new HashSet(this._reverse.keySet());
      return values;
   }

   public synchronized boolean isDirty() {
      return this._dirty;
   }

   public synchronized boolean keyHasOtherValues(Object key, Object value) {
      Set values = (Set)this._forward.get(key);
      if (values == null) {
         return false;
      } else {
         int size = values.size();
         if (size == 0) {
            return false;
         } else if (size > 1) {
            return true;
         } else {
            return !values.contains(value);
         }
      }
   }

   public synchronized boolean put(Object key, Object value) {
      Set values = (Set)this._forward.get(key);
      if (values == null) {
         values = new HashSet();
         this._forward.put(key, values);
      }

      boolean added = ((Set)values).add(value);
      this._dirty |= added;
      Set keys = (Set)this._reverse.get(value);
      if (keys == null) {
         keys = new HashSet();
         this._reverse.put(value, keys);
      }

      ((Set)keys).add(key);

      assert this.checkIntegrity();

      return added;
   }

   public synchronized boolean remove(Object key, Object value) {
      Set values = (Set)this._forward.get(key);
      if (values == null) {
         assert this.checkIntegrity();

         return false;
      } else {
         boolean removed = values.remove(value);
         if (values.isEmpty()) {
            this._forward.remove(key);
         }

         if (removed) {
            this._dirty = true;
            Set keys = (Set)this._reverse.get(value);
            keys.remove(key);
            if (keys.isEmpty()) {
               this._reverse.remove(value);
            }
         }

         assert this.checkIntegrity();

         return removed;
      }
   }

   public synchronized boolean removeKey(Object key) {
      Set values = (Set)this._forward.get(key);
      if (values == null) {
         assert this.checkIntegrity();

         return false;
      } else {
         Iterator var4 = values.iterator();

         while(var4.hasNext()) {
            Object value = (Object)var4.next();
            Set keys = (Set)this._reverse.get(value);
            if (keys != null) {
               keys.remove(key);
               if (keys.isEmpty()) {
                  this._reverse.remove(value);
               }
            }
         }

         this._forward.remove(key);
         this._dirty = true;

         assert this.checkIntegrity();

         return true;
      }
   }

   public synchronized boolean removeValue(Object value) {
      Set keys = (Set)this._reverse.get(value);
      if (keys == null) {
         assert this.checkIntegrity();

         return false;
      } else {
         Iterator var4 = keys.iterator();

         while(var4.hasNext()) {
            Object key = (Object)var4.next();
            Set values = (Set)this._forward.get(key);
            if (values != null) {
               values.remove(value);
               if (values.isEmpty()) {
                  this._forward.remove(key);
               }
            }
         }

         this._reverse.remove(value);
         this._dirty = true;

         assert this.checkIntegrity();

         return true;
      }
   }

   public synchronized boolean valueHasOtherKeys(Object value, Object key) {
      Set keys = (Set)this._reverse.get(value);
      if (keys == null) {
         return false;
      } else {
         int size = keys.size();
         if (size == 0) {
            return false;
         } else if (size > 1) {
            return true;
         } else {
            return !keys.contains(key);
         }
      }
   }

   private boolean checkIntegrity() {
      Iterator var2 = this._forward.entrySet().iterator();

      Map.Entry entry;
      Set keys;
      Object key;
      Iterator var5;
      Set values;
      while(var2.hasNext()) {
         entry = (Map.Entry)var2.next();
         keys = (Set)entry.getValue();
         if (keys.isEmpty()) {
            throw new IllegalStateException("Integrity compromised: forward map contains an empty set");
         }

         var5 = keys.iterator();

         while(var5.hasNext()) {
            key = (Object)var5.next();
            values = (Set)this._reverse.get(key);
            if (values == null || !values.contains(entry.getKey())) {
               throw new IllegalStateException("Integrity compromised: forward map contains an entry missing from reverse map: " + key);
            }
         }
      }

      var2 = this._reverse.entrySet().iterator();

      while(var2.hasNext()) {
         entry = (Map.Entry)var2.next();
         keys = (Set)entry.getValue();
         if (keys.isEmpty()) {
            throw new IllegalStateException("Integrity compromised: reverse map contains an empty set");
         }

         var5 = keys.iterator();

         while(var5.hasNext()) {
            key = (Object)var5.next();
            values = (Set)this._forward.get(key);
            if (values == null || !values.contains(entry.getKey())) {
               throw new IllegalStateException("Integrity compromised: reverse map contains an entry missing from forward map: " + key);
            }
         }
      }

      return true;
   }
}
