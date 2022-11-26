package com.bea.core.repackaged.springframework.util.comparator;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Comparator;

public class InstanceComparator implements Comparator {
   private final Class[] instanceOrder;

   public InstanceComparator(Class... instanceOrder) {
      Assert.notNull(instanceOrder, (String)"'instanceOrder' array must not be null");
      this.instanceOrder = instanceOrder;
   }

   public int compare(Object o1, Object o2) {
      int i1 = this.getOrder(o1);
      int i2 = this.getOrder(o2);
      return i1 < i2 ? -1 : (i1 == i2 ? 0 : 1);
   }

   private int getOrder(@Nullable Object object) {
      if (object != null) {
         for(int i = 0; i < this.instanceOrder.length; ++i) {
            if (this.instanceOrder[i].isInstance(object)) {
               return i;
            }
         }
      }

      return this.instanceOrder.length;
   }
}
