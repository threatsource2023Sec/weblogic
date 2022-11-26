package com.oracle.pitchfork.intercept;

import java.lang.reflect.Method;

public class PointcutMatch {
   private final Class pointcutClass;
   private final Method pointcutMethod;

   public PointcutMatch(Class paramPointcutClass, Method paramPointcutMethod) {
      this.pointcutClass = paramPointcutClass;
      this.pointcutMethod = paramPointcutMethod;
   }

   public int hashCode() {
      int retVal = this.pointcutClass == null ? 0 : this.pointcutClass.hashCode();
      retVal ^= this.pointcutMethod == null ? 0 : this.pointcutMethod.hashCode();
      return retVal;
   }

   private static boolean safeEquals(Object a, Object b) {
      if (a == b) {
         return true;
      } else if (a == null) {
         return false;
      } else {
         return b == null ? false : a.equals(b);
      }
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof PointcutMatch)) {
         return false;
      } else {
         PointcutMatch pm = (PointcutMatch)o;
         return !safeEquals(this.pointcutClass, pm.pointcutClass) ? false : safeEquals(this.pointcutMethod, pm.pointcutMethod);
      }
   }

   public String toString() {
      String className = this.pointcutClass == null ? "null" : this.pointcutClass.getSimpleName();
      String methodName = this.pointcutMethod == null ? "null" : this.pointcutMethod.getName();
      return "PointcutMatch(" + System.identityHashCode(this) + "," + className + "," + methodName + ")";
   }
}
