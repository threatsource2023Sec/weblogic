package com.bea.adaptive.harvester.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ListSet extends ArrayList implements Set {
   private static final long serialVersionUID = 1L;

   public ListSet() {
   }

   public ListSet(int size) {
      super(size);
   }

   public boolean add(Object item) {
      if (!this.contains(item)) {
         super.add(item);
         return true;
      } else {
         return false;
      }
   }

   public boolean addNoCheck(Object item) {
      super.add(item);
      return true;
   }

   public void add(int index, Object item) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c) {
      boolean mods = false;

      Object item;
      for(Iterator it = c.iterator(); it.hasNext(); mods |= this.add(item)) {
         item = it.next();
      }

      return mods;
   }

   public boolean addAll(int i, Collection c) {
      throw new UnsupportedOperationException();
   }

   public Object set(int i, Object item) {
      throw new UnsupportedOperationException();
   }
}
