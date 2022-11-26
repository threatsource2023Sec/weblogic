package com.bea.staxb.runtime.internal.util.collections;

import java.util.Collection;
import java.util.Vector;

public final class VectorBasedObjectAccumulator extends ObjectAccumulator {
   public VectorBasedObjectAccumulator(Class component_type, int initial_capacity) {
      super(component_type, initial_capacity, true);
   }

   protected Collection createNewStore(int capacity) {
      return new Vector();
   }

   public Vector getVectorStore() {
      return (Vector)this.store;
   }

   public void set(int index, Object value) {
      Vector v = (Vector)this.store;
      v.set(index, value);
      this.lastArray = null;
   }
}
