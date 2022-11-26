package org.apache.openjpa.lib.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.Reference;
import org.apache.commons.collections.map.AbstractHashedMap;
import org.apache.commons.collections.map.AbstractReferenceMap;

public class ReferenceHashMap extends org.apache.commons.collections.map.ReferenceMap implements ReferenceMap, SizedMap {
   private int _maxSize = Integer.MAX_VALUE;

   public ReferenceHashMap(int keyType, int valueType) {
      super(toReferenceConstant(keyType), toReferenceConstant(valueType));
   }

   public ReferenceHashMap(int keyType, int valueType, int capacity, float loadFactor) {
      super(toReferenceConstant(keyType), toReferenceConstant(valueType), capacity, loadFactor);
   }

   private static int toReferenceConstant(int type) {
      switch (type) {
         case 0:
            return 0;
         case 2:
            return 1;
         default:
            return 2;
      }
   }

   public int getMaxSize() {
      return this._maxSize;
   }

   public void setMaxSize(int maxSize) {
      this._maxSize = maxSize < 0 ? Integer.MAX_VALUE : maxSize;
      if (this._maxSize != Integer.MAX_VALUE) {
         this.removeOverflow(this._maxSize);
      }

   }

   public boolean isFull() {
      return this.size() >= this._maxSize;
   }

   public void overflowRemoved(Object key, Object value) {
   }

   public void valueExpired(Object key) {
   }

   public void keyExpired(Object value) {
   }

   public void removeExpired() {
      this.purge();
   }

   private void removeOverflow(int maxSize) {
      while(this.size() > maxSize) {
         Object key = this.keySet().iterator().next();
         this.overflowRemoved(key, this.remove(key));
      }

   }

   protected void addMapping(int hashIndex, int hashCode, Object key, Object value) {
      if (this._maxSize != Integer.MAX_VALUE) {
         this.removeOverflow(this._maxSize - 1);
      }

      super.addMapping(hashIndex, hashCode, key, value);
   }

   protected AbstractHashedMap.HashEntry createEntry(AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value) {
      return new AccessibleEntry(this, next, hashCode, key, value);
   }

   protected void purge(Reference ref) {
      int index = this.hashIndex(ref.hashCode(), this.data.length);
      AccessibleEntry entry = (AccessibleEntry)this.data[index];
      AccessibleEntry prev = null;
      Object key = null;

      Object value;
      for(value = null; entry != null; entry = entry.nextEntry()) {
         if (this.purge(entry, ref)) {
            if (isHard(this.keyType)) {
               key = entry.key();
            } else if (isHard(this.valueType)) {
               value = entry.value();
            }

            if (prev == null) {
               this.data[index] = entry.nextEntry();
            } else {
               prev.setNextEntry(entry.nextEntry());
            }

            --this.size;
            break;
         }

         prev = entry;
      }

      if (key != null) {
         this.valueExpired(key);
      } else if (value != null) {
         this.keyExpired(value);
      }

   }

   private boolean purge(AccessibleEntry entry, Reference ref) {
      boolean match = !isHard(this.keyType) && entry.key() == ref || !isHard(this.valueType) && entry.value() == ref;
      if (match) {
         if (!isHard(this.keyType)) {
            ((Reference)entry.key()).clear();
         }

         if (!isHard(this.valueType)) {
            ((Reference)entry.value()).clear();
         } else if (this.purgeValues) {
            entry.nullValue();
         }
      }

      return match;
   }

   private static boolean isHard(int type) {
      return type == 0;
   }

   protected void doWriteObject(ObjectOutputStream out) throws IOException {
      out.writeInt(this._maxSize);
      super.doWriteObject(out);
   }

   protected void doReadObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
      this._maxSize = in.readInt();
      super.doReadObject(in);
   }

   private static class AccessibleEntry extends AbstractReferenceMap.ReferenceEntry {
      public AccessibleEntry(AbstractReferenceMap map, AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value) {
         super(map, next, hashCode, key, value);
      }

      public Object key() {
         return this.key;
      }

      public Object value() {
         return this.value;
      }

      public void nullValue() {
         this.value = null;
      }

      public AccessibleEntry nextEntry() {
         return (AccessibleEntry)this.next;
      }

      public void setNextEntry(AccessibleEntry next) {
         this.next = next;
      }
   }
}
