package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

/** @deprecated */
public class StringContains implements FilterListener {
   public static final String TAG = "stringContains";

   public String getTag() {
      return "stringContains";
   }

   public boolean expectsArguments() {
      return true;
   }

   public boolean expectsTarget() {
      return true;
   }

   public Object evaluate(Object target, Class targetClass, Object[] args, Class[] argClasses, Object candidate, StoreContext ctx) {
      if (target != null && args[0] != null) {
         return target.toString().indexOf(args[0].toString()) != -1 ? Boolean.TRUE : Boolean.FALSE;
      } else {
         return Boolean.FALSE;
      }
   }

   public Class getType(Class targetClass, Class[] argClasses) {
      return Boolean.TYPE;
   }
}
