package weblogic.jms.backend;

import java.util.ArrayList;
import java.util.Comparator;
import weblogic.messaging.kernel.MessageElement;

public class JMSMessageCursorComparator implements Comparator {
   private BECursorDestinationKey[] keys;

   public JMSMessageCursorComparator(ArrayList keyList) {
      this.keys = new BECursorDestinationKey[keyList.size()];
      keyList.toArray(this.keys);
   }

   public int compare(Object o1, Object o2) {
      MessageElement me1 = (MessageElement)o1;
      MessageElement me2 = (MessageElement)o2;

      for(int inc = 0; inc < this.keys.length; ++inc) {
         if (this.keys[inc] != null) {
            long ret = this.keys[inc].compareKey(me1, me2, false);
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
