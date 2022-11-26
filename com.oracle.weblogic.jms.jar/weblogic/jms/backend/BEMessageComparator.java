package weblogic.jms.backend;

import java.util.Comparator;
import java.util.List;
import weblogic.jms.common.MessageImpl;

public final class BEMessageComparator implements Comparator {
   private BEDestinationKey[] keys;

   BEMessageComparator(List keyList) {
      this.keys = new BEDestinationKey[keyList.size()];
      keyList.toArray(this.keys);
   }

   boolean isDefault() {
      return this.keys.length == 1 && this.keys[0].isDefault();
   }

   public int compare(Object o1, Object o2) {
      MessageImpl m1 = (MessageImpl)o1;
      MessageImpl m2 = (MessageImpl)o2;

      for(int inc = 0; inc < this.keys.length; ++inc) {
         if (this.keys[inc] != null) {
            long ret = this.keys[inc].compareKey(m1, m2, false);
            if (ret < 0L) {
               return -1;
            }

            if (ret > 0L) {
               return 1;
            }
         }
      }

      return 0;
   }
}
