package com.bea.staxb.runtime.internal.util.collections;

import java.util.ArrayList;
import java.util.Collection;

public final class ArrayListBasedObjectAccumulator extends ObjectAccumulator {
   public ArrayListBasedObjectAccumulator(Class component_type, int initial_capacity, boolean return_collection) {
      super(component_type, initial_capacity, return_collection);
   }

   public ArrayListBasedObjectAccumulator(Class component_type, int initial_capacity) {
      this(component_type, initial_capacity, true);
   }

   protected Collection createNewStore(int capacity) {
      return new ArrayList(capacity);
   }

   public ArrayList getArrayListStore() {
      assert this.store instanceof ArrayList;

      return (ArrayList)this.store;
   }

   public void set(int index, Object value) {
      ArrayList l = (ArrayList)this.store;
      l.set(index, value);
      this.lastArray = null;
   }
}
