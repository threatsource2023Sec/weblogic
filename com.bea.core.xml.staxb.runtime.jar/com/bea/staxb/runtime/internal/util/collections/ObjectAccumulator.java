package com.bea.staxb.runtime.internal.util.collections;

import java.lang.reflect.Array;
import java.util.Collection;

public abstract class ObjectAccumulator implements Accumulator {
   private final Class componentType;
   private final boolean returnCollectionForArray;
   protected final Collection store;
   protected Object[] lastArray;

   public ObjectAccumulator(Class component_type, int initial_capacity, boolean return_collection) {
      this.componentType = component_type;
      this.returnCollectionForArray = return_collection;
      this.store = this.createNewStore(initial_capacity);

      assert this.store != null;

   }

   public ObjectAccumulator(Class component_type, int initial_capacity) {
      this(component_type, initial_capacity, false);
   }

   protected abstract Collection createNewStore(int var1);

   public ObjectAccumulator(Class component_type) {
      this(component_type, 16);
   }

   public void append(Object o) {
      assert this.checkInstance(o);

      this.lastArray = null;
      this.store.add(o);
   }

   public final void appendDefault() {
      this.append((Object)null);
   }

   public int size() {
      return this.store.size();
   }

   private boolean checkInstance(Object o) {
      if (o == null) {
         return true;
      } else if (this.componentType.isPrimitive()) {
         return true;
      } else if (!this.componentType.isInstance(o)) {
         String msg = "Invalid type: " + o.getClass().getName() + " expecting: " + this.componentType.getName();
         throw new IllegalArgumentException(msg);
      } else {
         return true;
      }
   }

   public final Object getFinalArray() {
      if (this.returnCollectionForArray) {
         return this.store;
      } else if (this.lastArray != null) {
         return this.lastArray;
      } else {
         Object[] out = (Object[])((Object[])Array.newInstance(this.componentType, this.store.size()));
         Object[] retval = this.store.toArray(out);
         this.lastArray = retval;
         return retval;
      }
   }

   public final Collection getStore() {
      return this.store;
   }
}
