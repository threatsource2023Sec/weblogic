package org.apache.xml.security.c14n.implementations;

import java.util.ArrayList;
import java.util.List;

class SymbMap implements Cloneable {
   int free = 23;
   NameSpaceSymbEntry[] entries;
   String[] keys;

   SymbMap() {
      this.entries = new NameSpaceSymbEntry[this.free];
      this.keys = new String[this.free];
   }

   void put(String key, NameSpaceSymbEntry value) {
      int index = this.index(key);
      Object oldKey = this.keys[index];
      this.keys[index] = key;
      this.entries[index] = value;
      if ((oldKey == null || !oldKey.equals(key)) && --this.free == 0) {
         this.free = this.entries.length;
         int newCapacity = this.free << 2;
         this.rehash(newCapacity);
      }

   }

   List entrySet() {
      List a = new ArrayList();

      for(int i = 0; i < this.entries.length; ++i) {
         if (this.entries[i] != null && !"".equals(this.entries[i].uri)) {
            a.add(this.entries[i]);
         }
      }

      return a;
   }

   protected int index(Object obj) {
      Object[] set = this.keys;
      int length = set.length;
      int index = (obj.hashCode() & Integer.MAX_VALUE) % length;
      Object cur = set[index];
      if (cur != null && !cur.equals(obj)) {
         --length;

         do {
            int var10000;
            if (index == length) {
               var10000 = 0;
            } else {
               ++index;
               var10000 = index;
            }

            index = var10000;
            cur = set[index];
         } while(cur != null && !cur.equals(obj));

         return index;
      } else {
         return index;
      }
   }

   protected void rehash(int newCapacity) {
      int oldCapacity = this.keys.length;
      String[] oldKeys = this.keys;
      NameSpaceSymbEntry[] oldVals = this.entries;
      this.keys = new String[newCapacity];
      this.entries = new NameSpaceSymbEntry[newCapacity];
      int i = oldCapacity;

      while(i-- > 0) {
         if (oldKeys[i] != null) {
            String o = oldKeys[i];
            int index = this.index(o);
            this.keys[index] = o;
            this.entries[index] = oldVals[i];
         }
      }

   }

   NameSpaceSymbEntry get(String key) {
      return this.entries[this.index(key)];
   }

   protected Object clone() {
      try {
         SymbMap copy = (SymbMap)super.clone();
         copy.entries = new NameSpaceSymbEntry[this.entries.length];
         System.arraycopy(this.entries, 0, copy.entries, 0, this.entries.length);
         copy.keys = new String[this.keys.length];
         System.arraycopy(this.keys, 0, copy.keys, 0, this.keys.length);
         return copy;
      } catch (CloneNotSupportedException var2) {
         var2.printStackTrace();
         return null;
      }
   }
}
