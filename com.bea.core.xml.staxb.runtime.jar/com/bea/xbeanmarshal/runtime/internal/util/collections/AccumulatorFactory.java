package com.bea.xbeanmarshal.runtime.internal.util.collections;

public final class AccumulatorFactory {
   public static Accumulator createAccumulator(Class component_type) {
      return createAccumulator(component_type, 16);
   }

   public static Accumulator createAccumulator(Class component_type, int initial_capacity) {
      return new ArrayListBasedObjectAccumulator(component_type, initial_capacity, false);
   }

   public static Accumulator createAccumulator(Class container_type, Class component_type, int initial_capacity) {
      if (container_type.isArray()) {
         assert container_type.getComponentType().isAssignableFrom(component_type);

         return createAccumulator(component_type, initial_capacity);
      } else {
         throw new AssertionError("unsupported container type: " + container_type);
      }
   }

   public static Accumulator createAccumulator(Class container_type, Class component_type) {
      return createAccumulator(container_type, component_type, 16);
   }
}
