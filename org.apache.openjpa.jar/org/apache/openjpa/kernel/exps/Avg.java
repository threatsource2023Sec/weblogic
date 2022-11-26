package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.kernel.Filters;
import serp.util.Numbers;

class Avg extends AggregateVal {
   public Avg(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      return c;
   }

   protected Object operate(Collection os, Class c) {
      if (os.isEmpty()) {
         return null;
      } else {
         Object sum = Filters.convert(Numbers.valueOf(0), c);
         int size = 0;
         Iterator itr = os.iterator();

         while(itr.hasNext()) {
            Object cur = itr.next();
            if (cur != null) {
               sum = Filters.add(sum, c, cur, c);
               ++size;
            }
         }

         if (size == 0) {
            return null;
         } else {
            return Filters.divide(sum, c, Numbers.valueOf(size), Integer.TYPE);
         }
      }
   }
}
