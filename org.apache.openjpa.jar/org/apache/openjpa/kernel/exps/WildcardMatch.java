package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.SimpleRegex;

/** @deprecated */
public class WildcardMatch implements FilterListener {
   public static final String TAG = "wildcardMatch";

   public String getTag() {
      return "wildcardMatch";
   }

   public boolean expectsArguments() {
      return true;
   }

   public boolean expectsTarget() {
      return true;
   }

   public Object evaluate(Object target, Class targetClass, Object[] args, Class[] argClasses, Object candidate, StoreContext ctx) {
      if (target != null && args[0] != null) {
         String wild = args[0].toString().replace('?', '.');

         int i;
         for(int st = 0; (i = wild.indexOf("*", st)) != -1; st = i + 3) {
            wild = wild.substring(0, i) + "." + wild.substring(i);
         }

         SimpleRegex re = new SimpleRegex(wild, false);
         return re.matches(target.toString()) ? Boolean.TRUE : Boolean.FALSE;
      } else {
         return Boolean.FALSE;
      }
   }

   public Class getType(Class targetClass, Class[] argClasses) {
      return Boolean.TYPE;
   }
}
