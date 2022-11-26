package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Iterator;
import serp.util.Numbers;

class Count extends AggregateVal {
   public Count(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      return Long.TYPE;
   }

   protected Object operate(Collection os, Class c) {
      long count = 0L;
      Iterator itr = os.iterator();

      while(itr.hasNext()) {
         if (itr.next() != null) {
            ++count;
         }
      }

      return Numbers.valueOf(count);
   }
}
