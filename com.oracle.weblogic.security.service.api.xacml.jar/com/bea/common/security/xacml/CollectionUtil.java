package com.bea.common.security.xacml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {
   public static boolean equals(Collection one, Collection two) {
      if (one == two) {
         return true;
      } else if (one != null && two != null && one.size() == two.size()) {
         Collection temp = new ArrayList(one);
         Iterator it = two.iterator();

         do {
            if (!it.hasNext()) {
               return temp.isEmpty();
            }
         } while(temp.remove(it.next()));

         return false;
      } else {
         return false;
      }
   }

   public static boolean equalsWithSequence(Collection one, Collection two) {
      if (one == two) {
         return true;
      } else if (one != null && two != null && one.size() == two.size()) {
         Iterator ite1 = one.iterator();
         Iterator ite2 = two.iterator();

         do {
            if (!ite1.hasNext()) {
               return true;
            }
         } while(ite1.next().equals(ite2.next()));

         return false;
      } else {
         return false;
      }
   }
}
