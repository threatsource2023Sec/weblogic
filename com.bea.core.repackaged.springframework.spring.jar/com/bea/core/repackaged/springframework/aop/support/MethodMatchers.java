package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.IntroductionAwareMethodMatcher;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.Method;

public abstract class MethodMatchers {
   public static MethodMatcher union(MethodMatcher mm1, MethodMatcher mm2) {
      return (MethodMatcher)(!(mm1 instanceof IntroductionAwareMethodMatcher) && !(mm2 instanceof IntroductionAwareMethodMatcher) ? new UnionMethodMatcher(mm1, mm2) : new UnionIntroductionAwareMethodMatcher(mm1, mm2));
   }

   static MethodMatcher union(MethodMatcher mm1, ClassFilter cf1, MethodMatcher mm2, ClassFilter cf2) {
      return (MethodMatcher)(!(mm1 instanceof IntroductionAwareMethodMatcher) && !(mm2 instanceof IntroductionAwareMethodMatcher) ? new ClassFilterAwareUnionMethodMatcher(mm1, cf1, mm2, cf2) : new ClassFilterAwareUnionIntroductionAwareMethodMatcher(mm1, cf1, mm2, cf2));
   }

   public static MethodMatcher intersection(MethodMatcher mm1, MethodMatcher mm2) {
      return (MethodMatcher)(!(mm1 instanceof IntroductionAwareMethodMatcher) && !(mm2 instanceof IntroductionAwareMethodMatcher) ? new IntersectionMethodMatcher(mm1, mm2) : new IntersectionIntroductionAwareMethodMatcher(mm1, mm2));
   }

   public static boolean matches(MethodMatcher mm, Method method, Class targetClass, boolean hasIntroductions) {
      Assert.notNull(mm, (String)"MethodMatcher must not be null");
      return mm instanceof IntroductionAwareMethodMatcher ? ((IntroductionAwareMethodMatcher)mm).matches(method, targetClass, hasIntroductions) : mm.matches(method, targetClass);
   }

   private static class IntersectionIntroductionAwareMethodMatcher extends IntersectionMethodMatcher implements IntroductionAwareMethodMatcher {
      public IntersectionIntroductionAwareMethodMatcher(MethodMatcher mm1, MethodMatcher mm2) {
         super(mm1, mm2);
      }

      public boolean matches(Method method, Class targetClass, boolean hasIntroductions) {
         return MethodMatchers.matches(this.mm1, method, targetClass, hasIntroductions) && MethodMatchers.matches(this.mm2, method, targetClass, hasIntroductions);
      }
   }

   private static class IntersectionMethodMatcher implements MethodMatcher, Serializable {
      protected final MethodMatcher mm1;
      protected final MethodMatcher mm2;

      public IntersectionMethodMatcher(MethodMatcher mm1, MethodMatcher mm2) {
         Assert.notNull(mm1, (String)"First MethodMatcher must not be null");
         Assert.notNull(mm2, (String)"Second MethodMatcher must not be null");
         this.mm1 = mm1;
         this.mm2 = mm2;
      }

      public boolean matches(Method method, Class targetClass) {
         return this.mm1.matches(method, targetClass) && this.mm2.matches(method, targetClass);
      }

      public boolean isRuntime() {
         return this.mm1.isRuntime() || this.mm2.isRuntime();
      }

      public boolean matches(Method method, Class targetClass, Object... args) {
         boolean aMatches = this.mm1.isRuntime() ? this.mm1.matches(method, targetClass, args) : this.mm1.matches(method, targetClass);
         boolean bMatches = this.mm2.isRuntime() ? this.mm2.matches(method, targetClass, args) : this.mm2.matches(method, targetClass);
         return aMatches && bMatches;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof IntersectionMethodMatcher)) {
            return false;
         } else {
            IntersectionMethodMatcher that = (IntersectionMethodMatcher)other;
            return this.mm1.equals(that.mm1) && this.mm2.equals(that.mm2);
         }
      }

      public int hashCode() {
         return 37 * this.mm1.hashCode() + this.mm2.hashCode();
      }

      public String toString() {
         return this.getClass().getName() + ": " + this.mm1 + ", " + this.mm2;
      }
   }

   private static class ClassFilterAwareUnionIntroductionAwareMethodMatcher extends ClassFilterAwareUnionMethodMatcher implements IntroductionAwareMethodMatcher {
      public ClassFilterAwareUnionIntroductionAwareMethodMatcher(MethodMatcher mm1, ClassFilter cf1, MethodMatcher mm2, ClassFilter cf2) {
         super(mm1, cf1, mm2, cf2);
      }

      public boolean matches(Method method, Class targetClass, boolean hasIntroductions) {
         return this.matchesClass1(targetClass) && MethodMatchers.matches(this.mm1, method, targetClass, hasIntroductions) || this.matchesClass2(targetClass) && MethodMatchers.matches(this.mm2, method, targetClass, hasIntroductions);
      }
   }

   private static class ClassFilterAwareUnionMethodMatcher extends UnionMethodMatcher {
      private final ClassFilter cf1;
      private final ClassFilter cf2;

      public ClassFilterAwareUnionMethodMatcher(MethodMatcher mm1, ClassFilter cf1, MethodMatcher mm2, ClassFilter cf2) {
         super(mm1, mm2);
         this.cf1 = cf1;
         this.cf2 = cf2;
      }

      protected boolean matchesClass1(Class targetClass) {
         return this.cf1.matches(targetClass);
      }

      protected boolean matchesClass2(Class targetClass) {
         return this.cf2.matches(targetClass);
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!super.equals(other)) {
            return false;
         } else {
            ClassFilter otherCf1 = ClassFilter.TRUE;
            ClassFilter otherCf2 = ClassFilter.TRUE;
            if (other instanceof ClassFilterAwareUnionMethodMatcher) {
               ClassFilterAwareUnionMethodMatcher cfa = (ClassFilterAwareUnionMethodMatcher)other;
               otherCf1 = cfa.cf1;
               otherCf2 = cfa.cf2;
            }

            return this.cf1.equals(otherCf1) && this.cf2.equals(otherCf2);
         }
      }

      public int hashCode() {
         return super.hashCode();
      }

      public String toString() {
         return this.getClass().getName() + ": " + this.cf1 + ", " + this.mm1 + ", " + this.cf2 + ", " + this.mm2;
      }
   }

   private static class UnionIntroductionAwareMethodMatcher extends UnionMethodMatcher implements IntroductionAwareMethodMatcher {
      public UnionIntroductionAwareMethodMatcher(MethodMatcher mm1, MethodMatcher mm2) {
         super(mm1, mm2);
      }

      public boolean matches(Method method, Class targetClass, boolean hasIntroductions) {
         return this.matchesClass1(targetClass) && MethodMatchers.matches(this.mm1, method, targetClass, hasIntroductions) || this.matchesClass2(targetClass) && MethodMatchers.matches(this.mm2, method, targetClass, hasIntroductions);
      }
   }

   private static class UnionMethodMatcher implements MethodMatcher, Serializable {
      protected final MethodMatcher mm1;
      protected final MethodMatcher mm2;

      public UnionMethodMatcher(MethodMatcher mm1, MethodMatcher mm2) {
         Assert.notNull(mm1, (String)"First MethodMatcher must not be null");
         Assert.notNull(mm2, (String)"Second MethodMatcher must not be null");
         this.mm1 = mm1;
         this.mm2 = mm2;
      }

      public boolean matches(Method method, Class targetClass) {
         return this.matchesClass1(targetClass) && this.mm1.matches(method, targetClass) || this.matchesClass2(targetClass) && this.mm2.matches(method, targetClass);
      }

      protected boolean matchesClass1(Class targetClass) {
         return true;
      }

      protected boolean matchesClass2(Class targetClass) {
         return true;
      }

      public boolean isRuntime() {
         return this.mm1.isRuntime() || this.mm2.isRuntime();
      }

      public boolean matches(Method method, Class targetClass, Object... args) {
         return this.mm1.matches(method, targetClass, args) || this.mm2.matches(method, targetClass, args);
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof UnionMethodMatcher)) {
            return false;
         } else {
            UnionMethodMatcher that = (UnionMethodMatcher)other;
            return this.mm1.equals(that.mm1) && this.mm2.equals(that.mm2);
         }
      }

      public int hashCode() {
         return 37 * this.mm1.hashCode() + this.mm2.hashCode();
      }

      public String toString() {
         return this.getClass().getName() + ": " + this.mm1 + ", " + this.mm2;
      }
   }
}
