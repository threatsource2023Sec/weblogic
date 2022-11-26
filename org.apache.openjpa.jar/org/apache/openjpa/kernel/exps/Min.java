package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Iterator;

class Min extends AggregateVal {
   public Min(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      return c;
   }

   protected Object operate(Collection os, Class c) {
      Comparable min = null;
      Iterator itr = os.iterator();

      while(true) {
         Comparable cur;
         do {
            do {
               if (!itr.hasNext()) {
                  return min;
               }

               cur = (Comparable)itr.next();
            } while(cur == null);
         } while(min != null && min.compareTo(cur) <= 0);

         min = cur;
      }
   }
}
