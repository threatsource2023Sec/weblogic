package com.bea.staxb.runtime.internal.util.collections;

import java.util.Collection;

public final class GenericCollectionObjectAccumulator implements Accumulator {
   private final Class componentType;
   private final Collection container;

   public GenericCollectionObjectAccumulator(Class container_type, Class component_type) {
      assert Collection.class.isAssignableFrom(container_type);

      this.componentType = component_type;

      try {
         this.container = (Collection)container_type.newInstance();
      } catch (InstantiationException var4) {
         throw (IllegalArgumentException)(new IllegalArgumentException()).initCause(var4);
      } catch (IllegalAccessException var5) {
         throw (IllegalArgumentException)(new IllegalArgumentException()).initCause(var5);
      }
   }

   public void append(Object elem) {
      this.container.add(elem);
   }

   public void appendDefault() {
      this.append((Object)null);
   }

   public void set(int index, Object value) {
      throw new UnsupportedOperationException("no indexed access");
   }

   public int size() {
      return this.container.size();
   }

   public Object getFinalArray() {
      return this.container;
   }
}
