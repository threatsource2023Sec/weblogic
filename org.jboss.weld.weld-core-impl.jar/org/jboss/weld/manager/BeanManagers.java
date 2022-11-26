package org.jboss.weld.manager;

import java.util.Comparator;

public final class BeanManagers {
   public static final Comparator ID_COMPARATOR = (m1, m2) -> {
      return m1.getId().compareTo(m2.getId());
   };

   private BeanManagers() {
   }
}
