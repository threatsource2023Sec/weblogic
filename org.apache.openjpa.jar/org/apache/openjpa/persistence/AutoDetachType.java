package org.apache.openjpa.persistence;

import java.util.EnumSet;
import java.util.Iterator;

public enum AutoDetachType {
   CLOSE(2),
   COMMIT(4),
   NON_TRANSACTIONAL_READ(8),
   ROLLBACK(16);

   private final int autoDetachConstant;

   private AutoDetachType(int value) {
      this.autoDetachConstant = value;
   }

   public static EnumSet toEnumSet(int autoDetach) {
      EnumSet types = EnumSet.noneOf(AutoDetachType.class);
      if ((autoDetach & 2) != 0) {
         types.add(CLOSE);
      }

      if ((autoDetach & 4) != 0) {
         types.add(COMMIT);
      }

      if ((autoDetach & 8) != 0) {
         types.add(NON_TRANSACTIONAL_READ);
      }

      if ((autoDetach & 16) != 0) {
         types.add(ROLLBACK);
      }

      return types;
   }

   public static int fromEnumSet(EnumSet types) {
      int autoDetach = 0;

      AutoDetachType type;
      for(Iterator i$ = types.iterator(); i$.hasNext(); autoDetach |= type.autoDetachConstant) {
         type = (AutoDetachType)i$.next();
      }

      return autoDetach;
   }
}
