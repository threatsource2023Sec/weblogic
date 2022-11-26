package weblogic.jdbc.common.internal;

import java.util.Comparator;

class LastUseComparator implements Comparator {
   public int compare(ConnectionEnv o1, ConnectionEnv o2) {
      if (o1.lastSuccessfulConnectionUse == o2.lastSuccessfulConnectionUse) {
         return 0;
      } else {
         return o1.lastSuccessfulConnectionUse < o2.lastSuccessfulConnectionUse ? -1 : 1;
      }
   }
}
