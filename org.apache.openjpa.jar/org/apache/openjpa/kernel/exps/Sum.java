package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.kernel.Filters;
import serp.util.Numbers;

class Sum extends AggregateVal {
   public Sum(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      Class wrap = Filters.wrap(c);
      return wrap != Integer.class && wrap != Short.class && wrap != Byte.class ? c : Long.TYPE;
   }

   protected Object operate(Collection os, Class c) {
      if (os.isEmpty()) {
         return null;
      } else {
         Class type = this.getType(c);
         Object sum = Filters.convert(Numbers.valueOf(0), type);
         Iterator itr = os.iterator();

         while(itr.hasNext()) {
            Object cur = itr.next();
            if (cur != null) {
               sum = Filters.add(sum, type, cur, c);
            }
         }

         return sum;
      }
   }
}
