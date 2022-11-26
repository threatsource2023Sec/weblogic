package com.bea.staxb.runtime.internal.util.collections;

import java.util.Collection;
import java.util.Stack;

public final class StackBasedObjectAccumulator extends ObjectAccumulator {
   public StackBasedObjectAccumulator(Class component_type, int initial_capacity) {
      super(component_type, initial_capacity, true);
   }

   protected Collection createNewStore(int capacity) {
      return new Stack();
   }

   public Stack getStackStore() {
      assert this.store instanceof Stack;

      return (Stack)this.store;
   }

   public void set(int index, Object value) {
      Stack s = (Stack)this.store;
      s.set(index, value);
      this.lastArray = null;
   }
}
