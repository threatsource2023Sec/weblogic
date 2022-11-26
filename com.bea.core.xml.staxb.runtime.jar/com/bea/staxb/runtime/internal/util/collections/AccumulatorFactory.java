package com.bea.staxb.runtime.internal.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

public final class AccumulatorFactory {
   public static Accumulator createAccumulator(Class component_type) {
      return createAccumulator(component_type, 16);
   }

   public static Accumulator createAccumulator(Class component_type, int initial_capacity) {
      if (component_type.isPrimitive()) {
         return createPrimtiveAccumulator(component_type, initial_capacity);
      } else {
         return (Accumulator)(String.class.equals(component_type) ? new StringList(initial_capacity) : new ArrayListBasedObjectAccumulator(component_type, initial_capacity, false));
      }
   }

   private static Accumulator createPrimtiveAccumulator(Class component_type, int initial_capacity) {
      if (initial_capacity < 0) {
         initial_capacity = 16;
      }

      if (Integer.TYPE.equals(component_type)) {
         return new IntList(initial_capacity);
      } else if (Short.TYPE.equals(component_type)) {
         return new ShortList(initial_capacity);
      } else if (Long.TYPE.equals(component_type)) {
         return new LongList(initial_capacity);
      } else if (Float.TYPE.equals(component_type)) {
         return new FloatList(initial_capacity);
      } else if (Double.TYPE.equals(component_type)) {
         return new DoubleList(initial_capacity);
      } else if (Byte.TYPE.equals(component_type)) {
         return new ByteList(initial_capacity);
      } else if (Boolean.TYPE.equals(component_type)) {
         return new BooleanList(initial_capacity);
      } else if (Character.TYPE.equals(component_type)) {
         return new CharList(initial_capacity);
      } else {
         throw new AssertionError("unknown primitive type: " + component_type);
      }
   }

   public static Accumulator createAccumulator(Class container_type, Class component_type, int initial_capacity) {
      if (container_type.isArray()) {
         assert container_type.getComponentType().isAssignableFrom(component_type);

         return createAccumulator(component_type, initial_capacity);
      } else if (Collection.class.isAssignableFrom(container_type)) {
         return createCollectionAccumulator(container_type, component_type, initial_capacity);
      } else {
         throw new AssertionError("unsupported container type: " + container_type);
      }
   }

   public static Accumulator createAccumulator(Class container_type, Class component_type) {
      return createAccumulator(container_type, component_type, 16);
   }

   private static Accumulator createCollectionAccumulator(Class container_type, Class component_type, int initial_capacity) {
      assert !container_type.isArray();

      if (!Collection.class.equals(container_type) && !ArrayList.class.equals(container_type) && !List.class.equals(container_type)) {
         if (!Set.class.equals(container_type) && !HashSet.class.equals(container_type)) {
            if (!SortedSet.class.equals(container_type) && !TreeSet.class.equals(container_type)) {
               if (Vector.class.equals(container_type)) {
                  return new VectorBasedObjectAccumulator(component_type, initial_capacity);
               } else if (Stack.class.equals(container_type)) {
                  return new StackBasedObjectAccumulator(component_type, initial_capacity);
               } else {
                  return (Accumulator)(LinkedList.class.equals(container_type) ? new LinkedListBasedObjectAccumulator(component_type, initial_capacity) : new GenericCollectionObjectAccumulator(container_type, component_type));
               }
            } else {
               return new TreeSetBasedObjectAccumulator(component_type, initial_capacity);
            }
         } else {
            return new HashSetBasedObjectAccumulator(component_type, initial_capacity);
         }
      } else {
         return new ArrayListBasedObjectAccumulator(component_type, initial_capacity);
      }
   }

   public static Object createMultiDimSoapArrayAccumulator(Class container_type, Class component_type, int[] dims) {
      return new MultiDimSoapArrayAccumulator(container_type, component_type, dims);
   }
}
