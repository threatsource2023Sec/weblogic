package org.python.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Generic {
   public static final int CHM_INITIAL_CAPACITY = 16;
   public static final float CHM_LOAD_FACTOR = 0.75F;
   public static final int CHM_CONCURRENCY_LEVEL = 2;

   public static List list() {
      return new ArrayList();
   }

   public static List list(int capacity) {
      return new ArrayList(capacity);
   }

   @SafeVarargs
   public static List list(Object... contents) {
      List l = new ArrayList(contents.length);
      Object[] var2 = contents;
      int var3 = contents.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object t = var2[var4];
         l.add(t);
      }

      return l;
   }

   public static Map map() {
      return new HashMap();
   }

   public static Map identityHashMap() {
      return new IdentityHashMap();
   }

   public static Map identityHashMap(int capacity) {
      return new IdentityHashMap(capacity);
   }

   public static ConcurrentMap concurrentMap() {
      return new ConcurrentHashMap(16, 0.75F, 2);
   }

   public static Set set() {
      return new HashSet();
   }

   public static Set linkedHashSet() {
      return new LinkedHashSet();
   }

   public static Set linkedHashSet(int capacity) {
      return new LinkedHashSet(capacity);
   }

   @SafeVarargs
   public static Set set(Object... contents) {
      Set s = new HashSet(contents.length);
      Object[] var2 = contents;
      int var3 = contents.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object u = var2[var4];
         s.add(u);
      }

      return s;
   }

   public static Set concurrentSet() {
      return Collections.newSetFromMap(concurrentMap());
   }
}
