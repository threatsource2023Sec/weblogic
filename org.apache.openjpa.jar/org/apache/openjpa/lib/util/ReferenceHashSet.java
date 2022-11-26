package org.apache.openjpa.lib.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.collections.set.MapBackedSet;

public class ReferenceHashSet implements Set, Serializable {
   public static final int HARD = 0;
   public static final int SOFT = 1;
   public static final int WEAK = 2;
   private static final Object DUMMY_VAL = new Serializable() {
      public String toString() {
         return ReferenceHashSet.class.getName() + ".DUMMY_VAL";
      }
   };
   private final Set _set;

   public ReferenceHashSet(int refType) {
      if (refType == 0) {
         this._set = new HashSet();
      } else {
         int mapRefType = refType == 2 ? 2 : 1;
         this._set = MapBackedSet.decorate(new org.apache.commons.collections.map.ReferenceMap(mapRefType, 0), DUMMY_VAL);
      }

   }

   public boolean add(Object obj) {
      return this._set.add(obj);
   }

   public boolean addAll(Collection coll) {
      return this._set.addAll(coll);
   }

   public void clear() {
      this._set.clear();
   }

   public boolean contains(Object obj) {
      return this._set.contains(obj);
   }

   public boolean containsAll(Collection coll) {
      return this._set.containsAll(coll);
   }

   public boolean isEmpty() {
      return this._set.isEmpty();
   }

   public Iterator iterator() {
      return this._set.iterator();
   }

   public boolean remove(Object obj) {
      return this._set.remove(obj);
   }

   public boolean removeAll(Collection coll) {
      return this._set.removeAll(coll);
   }

   public boolean retainAll(Collection coll) {
      return this._set.retainAll(coll);
   }

   public int size() {
      return this._set.size();
   }

   public Object[] toArray() {
      return this._set.toArray();
   }

   public Object[] toArray(Object[] arr) {
      return this._set.toArray(arr);
   }

   public int hashCode() {
      return this._set.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         if (obj instanceof ReferenceHashSet) {
            obj = ((ReferenceHashSet)obj)._set;
         }

         return this._set.equals(obj);
      }
   }
}
