package org.hibernate.validator.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class CollectionHelper {
   private CollectionHelper() {
   }

   public static HashMap newHashMap() {
      return new HashMap();
   }

   public static HashMap newHashMap(int size) {
      return new HashMap(getInitialCapacityFromExpectedSize(size));
   }

   public static HashMap newHashMap(Map map) {
      return new HashMap(map);
   }

   public static ConcurrentHashMap newConcurrentHashMap() {
      return new ConcurrentHashMap();
   }

   public static HashSet newHashSet() {
      return new HashSet();
   }

   public static HashSet newHashSet(int size) {
      return new HashSet(getInitialCapacityFromExpectedSize(size));
   }

   public static HashSet newHashSet(Collection c) {
      return new HashSet(c);
   }

   public static HashSet newHashSet(Iterable iterable) {
      HashSet set = newHashSet();
      Iterator var2 = iterable.iterator();

      while(var2.hasNext()) {
         Object t = var2.next();
         set.add(t);
      }

      return set;
   }

   public static ArrayList newArrayList() {
      return new ArrayList();
   }

   public static ArrayList newArrayList(int size) {
      return new ArrayList(size);
   }

   @SafeVarargs
   public static ArrayList newArrayList(Iterable... iterables) {
      ArrayList resultList = newArrayList();
      Iterable[] var2 = iterables;
      int var3 = iterables.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Iterable oneIterable = var2[var4];
         Iterator var6 = oneIterable.iterator();

         while(var6.hasNext()) {
            Object oneElement = var6.next();
            resultList.add(oneElement);
         }
      }

      return resultList;
   }

   @SafeVarargs
   public static Set asSet(Object... ts) {
      return new HashSet(Arrays.asList(ts));
   }

   public static List toImmutableList(List list) {
      switch (list.size()) {
         case 0:
            return Collections.emptyList();
         case 1:
            return Collections.singletonList(list.get(0));
         default:
            return Collections.unmodifiableList(list);
      }
   }

   public static Set toImmutableSet(Set set) {
      switch (set.size()) {
         case 0:
            return Collections.emptySet();
         case 1:
            return Collections.singleton(set.iterator().next());
         default:
            return Collections.unmodifiableSet(set);
      }
   }

   public static Map toImmutableMap(Map map) {
      switch (map.size()) {
         case 0:
            return Collections.emptyMap();
         case 1:
            Map.Entry entry = (Map.Entry)map.entrySet().iterator().next();
            return Collections.singletonMap(entry.getKey(), entry.getValue());
         default:
            return Collections.unmodifiableMap(map);
      }
   }

   private static int getInitialCapacityFromExpectedSize(int expectedSize) {
      return expectedSize < 3 ? expectedSize + 1 : (int)((float)expectedSize / 0.75F + 1.0F);
   }

   public static Iterator iteratorFromArray(Object object) {
      return new ArrayIterator(accessorFromArray(object), object);
   }

   public static Iterable iterableFromArray(Object object) {
      return new ArrayIterable(accessorFromArray(object), object);
   }

   private static ArrayAccessor accessorFromArray(Object object) {
      ArrayAccessor accessor;
      if (Object.class.isAssignableFrom(object.getClass().getComponentType())) {
         accessor = CollectionHelper.ArrayAccessor.OBJECT;
      } else if (object.getClass() == boolean[].class) {
         accessor = CollectionHelper.ArrayAccessor.BOOLEAN;
      } else if (object.getClass() == int[].class) {
         accessor = CollectionHelper.ArrayAccessor.INT;
      } else if (object.getClass() == long[].class) {
         accessor = CollectionHelper.ArrayAccessor.LONG;
      } else if (object.getClass() == double[].class) {
         accessor = CollectionHelper.ArrayAccessor.DOUBLE;
      } else if (object.getClass() == float[].class) {
         accessor = CollectionHelper.ArrayAccessor.FLOAT;
      } else if (object.getClass() == byte[].class) {
         accessor = CollectionHelper.ArrayAccessor.BYTE;
      } else if (object.getClass() == short[].class) {
         accessor = CollectionHelper.ArrayAccessor.SHORT;
      } else {
         if (object.getClass() != char[].class) {
            throw new IllegalArgumentException("Provided object " + object + " is not a supported array type");
         }

         accessor = CollectionHelper.ArrayAccessor.CHAR;
      }

      return accessor;
   }

   private interface ArrayAccessor {
      ArrayAccessor OBJECT = new ArrayAccessor() {
         public int size(Object[] array) {
            return array.length;
         }

         public Object get(Object[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor BOOLEAN = new ArrayAccessor() {
         public int size(boolean[] array) {
            return array.length;
         }

         public Boolean get(boolean[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor INT = new ArrayAccessor() {
         public int size(int[] array) {
            return array.length;
         }

         public Integer get(int[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor LONG = new ArrayAccessor() {
         public int size(long[] array) {
            return array.length;
         }

         public Long get(long[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor DOUBLE = new ArrayAccessor() {
         public int size(double[] array) {
            return array.length;
         }

         public Double get(double[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor FLOAT = new ArrayAccessor() {
         public int size(float[] array) {
            return array.length;
         }

         public Float get(float[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor BYTE = new ArrayAccessor() {
         public int size(byte[] array) {
            return array.length;
         }

         public Byte get(byte[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor SHORT = new ArrayAccessor() {
         public int size(short[] array) {
            return array.length;
         }

         public Short get(short[] array, int index) {
            return array[index];
         }
      };
      ArrayAccessor CHAR = new ArrayAccessor() {
         public int size(char[] array) {
            return array.length;
         }

         public Character get(char[] array, int index) {
            return array[index];
         }
      };

      int size(Object var1);

      Object get(Object var1, int var2);
   }

   private static class ArrayIterator implements Iterator {
      private final ArrayAccessor accessor;
      private final Object values;
      private int current = 0;

      public ArrayIterator(ArrayAccessor accessor, Object values) {
         this.accessor = accessor;
         this.values = values;
      }

      public boolean hasNext() {
         return this.current < this.accessor.size(this.values);
      }

      public Object next() {
         Object result = this.accessor.get(this.values, this.current);
         ++this.current;
         return result;
      }
   }

   private static class ArrayIterable implements Iterable {
      private final ArrayAccessor accessor;
      private final Object values;

      public ArrayIterable(ArrayAccessor accessor, Object values) {
         this.accessor = accessor;
         this.values = values;
      }

      public final Iterator iterator() {
         return new ArrayIterator(this.accessor, this.values);
      }
   }
}
