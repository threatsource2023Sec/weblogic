package com.bea.staxb.runtime.internal.util.collections;

import java.util.Collection;
import java.util.LinkedList;

public final class LinkedListBasedObjectAccumulator extends ObjectAccumulator {
   public LinkedListBasedObjectAccumulator(Class component_type, int initial_capacity) {
      super(component_type, initial_capacity, true);
   }

   protected Collection createNewStore(int capacity) {
      return new LinkedList();
   }

   public LinkedList getLinkedListStore() {
      assert this.store instanceof LinkedList;

      return (LinkedList)this.store;
   }

   public void set(int index, Object value) {
      LinkedList l = (LinkedList)this.store;
      l.set(index, value);
      this.lastArray = null;
   }
}
