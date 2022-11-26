package weblogic.cache.locks;

import java.util.Comparator;

public class LockKeyComparator implements Comparator {
   public int compare(Object o1, Object o2) {
      int h1 = o1 == null ? 0 : o1.hashCode();
      int h2 = o2 == null ? 0 : o2.hashCode();
      return h1 - h2;
   }
}
