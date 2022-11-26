package org.hibernate.validator.internal.util.classhierarchy;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;

public class ClassHierarchyHelper {
   private ClassHierarchyHelper() {
   }

   public static List getHierarchy(Class clazz, Filter... filters) {
      Contracts.assertNotNull(clazz);
      List classes = CollectionHelper.newArrayList();
      List allFilters = CollectionHelper.newArrayList();
      allFilters.addAll(Arrays.asList(filters));
      allFilters.add(Filters.excludeProxies());
      getHierarchy(clazz, classes, allFilters);
      return classes;
   }

   private static void getHierarchy(Class clazz, List classes, Iterable filters) {
      for(Class current = clazz; current != null; current = current.getSuperclass()) {
         if (classes.contains(current)) {
            return;
         }

         if (acceptedByAllFilters(current, filters)) {
            classes.add(current);
         }

         Class[] var4 = current.getInterfaces();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class currentInterface = var4[var6];
            getHierarchy(currentInterface, classes, filters);
         }
      }

   }

   private static boolean acceptedByAllFilters(Class clazz, Iterable filters) {
      Iterator var2 = filters.iterator();

      Filter classFilter;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         classFilter = (Filter)var2.next();
      } while(classFilter.accepts(clazz));

      return false;
   }

   public static Set getDirectlyImplementedInterfaces(Class clazz) {
      Contracts.assertNotNull(clazz);
      Set classes = CollectionHelper.newHashSet();
      getImplementedInterfaces(clazz, classes);
      return classes;
   }

   private static void getImplementedInterfaces(Class clazz, Set classes) {
      Class[] var2 = clazz.getInterfaces();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class currentInterface = var2[var4];
         classes.add(currentInterface);
         getImplementedInterfaces(currentInterface, classes);
      }

   }
}
