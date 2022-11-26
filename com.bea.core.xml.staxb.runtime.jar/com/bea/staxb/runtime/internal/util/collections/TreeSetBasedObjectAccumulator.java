package com.bea.staxb.runtime.internal.util.collections;

import java.util.Collection;
import java.util.TreeSet;

public final class TreeSetBasedObjectAccumulator extends ObjectAccumulator {
   public TreeSetBasedObjectAccumulator(Class component_type, int initial_capacity) {
      super(component_type, initial_capacity, true);
   }

   protected Collection createNewStore(int capacity) {
      return new TreeSet();
   }

   public TreeSet getTreeSetStore() {
      assert this.store instanceof TreeSet;

      return (TreeSet)this.store;
   }

   public void set(int index, Object value) {
      throw new UnsupportedOperationException("no indexed access");
   }
}
