package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Iterator;

class Max extends AggregateVal {
   public Max(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      return c;
   }

   protected Object operate(Collection os, Class c) {
      Comparable max = null;
      Iterator itr = os.iterator();

      while(true) {
         Comparable cur;
         do {
            do {
               if (!itr.hasNext()) {
                  return max;
               }

               cur = (Comparable)itr.next();
            } while(cur == null);
         } while(max != null && max.compareTo(cur) >= 0);

         max = cur;
      }
   }
}
