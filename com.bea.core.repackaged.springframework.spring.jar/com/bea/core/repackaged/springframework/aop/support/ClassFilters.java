package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.util.Arrays;

public abstract class ClassFilters {
   public static ClassFilter union(ClassFilter cf1, ClassFilter cf2) {
      Assert.notNull(cf1, (String)"First ClassFilter must not be null");
      Assert.notNull(cf2, (String)"Second ClassFilter must not be null");
      return new UnionClassFilter(new ClassFilter[]{cf1, cf2});
   }

   public static ClassFilter union(ClassFilter[] classFilters) {
      Assert.notEmpty((Object[])classFilters, (String)"ClassFilter array must not be empty");
      return new UnionClassFilter(classFilters);
   }

   public static ClassFilter intersection(ClassFilter cf1, ClassFilter cf2) {
      Assert.notNull(cf1, (String)"First ClassFilter must not be null");
      Assert.notNull(cf2, (String)"Second ClassFilter must not be null");
      return new IntersectionClassFilter(new ClassFilter[]{cf1, cf2});
   }

   public static ClassFilter intersection(ClassFilter[] classFilters) {
      Assert.notEmpty((Object[])classFilters, (String)"ClassFilter array must not be empty");
      return new IntersectionClassFilter(classFilters);
   }

   private static class IntersectionClassFilter implements ClassFilter, Serializable {
      private final ClassFilter[] filters;

      IntersectionClassFilter(ClassFilter[] filters) {
         this.filters = filters;
      }

      public boolean matches(Class clazz) {
         ClassFilter[] var2 = this.filters;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ClassFilter filter = var2[var4];
            if (!filter.matches(clazz)) {
               return false;
            }
         }

         return true;
      }

      public boolean equals(Object other) {
         return this == other || other instanceof IntersectionClassFilter && ObjectUtils.nullSafeEquals(this.filters, ((IntersectionClassFilter)other).filters);
      }

      public int hashCode() {
         return ObjectUtils.nullSafeHashCode((Object[])this.filters);
      }

      public String toString() {
         return this.getClass().getName() + ": " + Arrays.toString(this.filters);
      }
   }

   private static class UnionClassFilter implements ClassFilter, Serializable {
      private final ClassFilter[] filters;

      UnionClassFilter(ClassFilter[] filters) {
         this.filters = filters;
      }

      public boolean matches(Class clazz) {
         ClassFilter[] var2 = this.filters;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ClassFilter filter = var2[var4];
            if (filter.matches(clazz)) {
               return true;
            }
         }

         return false;
      }

      public boolean equals(Object other) {
         return this == other || other instanceof UnionClassFilter && ObjectUtils.nullSafeEquals(this.filters, ((UnionClassFilter)other).filters);
      }

      public int hashCode() {
         return ObjectUtils.nullSafeHashCode((Object[])this.filters);
      }

      public String toString() {
         return this.getClass().getName() + ": " + Arrays.toString(this.filters);
      }
   }
}
