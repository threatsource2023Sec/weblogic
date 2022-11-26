package org.jboss.weld.util.collections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Arrays2 {
   public static final Object[] EMPTY_ARRAY = new Object[0];
   public static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];
   public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
   public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
   public static final String[] EMPTY_STRING_ARRAY = new String[0];

   private Arrays2() {
   }

   public static boolean containsAll(Object[] array, Object... values) {
      return Arrays.asList(array).containsAll(Arrays.asList(values));
   }

   public static boolean unorderedEquals(Object[] array, Object... values) {
      return containsAll(array, values) && array.length == values.length;
   }

   @SafeVarargs
   public static Set asSet(Object... array) {
      Set result = new HashSet(array.length);
      Collections.addAll(result, array);
      return result;
   }

   public static boolean contains(Object[] array, Object value) {
      Object[] var2 = array;
      int var3 = array.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object element = var2[var4];
         if (element.equals(value)) {
            return true;
         }
      }

      return false;
   }
}
