package com.sun.faces.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CollectionsUtils {
   private CollectionsUtils() {
   }

   @SafeVarargs
   public static Set asSet(Object... a) {
      return new HashSet(Arrays.asList(a));
   }

   public static Object[] ar(Object... ts) {
      return ts;
   }

   public static Object[] ar() {
      return null;
   }

   public static ConstMap map() {
      return new ConstMap();
   }

   public static class ConstMap extends HashMap {
      public ConstMap() {
         super(50, 1.0F);
      }

      public ConstMap add(Object key, Object value) {
         this.put(key, value);
         return this;
      }

      public Map fix() {
         return Collections.unmodifiableMap(this);
      }
   }
}
