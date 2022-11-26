package com.bea.staxb.runtime.internal.util.collections;

import java.util.Collection;
import java.util.HashSet;

public final class HashSetBasedObjectAccumulator extends ObjectAccumulator {
   public HashSetBasedObjectAccumulator(Class component_type, int initial_capacity) {
      super(component_type, initial_capacity, true);
   }

   protected Collection createNewStore(int capacity) {
      return new HashSet();
   }

   public HashSet getHashSetStore() {
      assert this.store instanceof HashSet;

      return (HashSet)this.store;
   }

   public void set(int index, Object value) {
      throw new UnsupportedOperationException("no indexed access");
   }
}
