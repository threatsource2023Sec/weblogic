package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class StoreException extends OpenJPAException {
   public static final int LOCK = 1;
   public static final int OBJECT_NOT_FOUND = 2;
   public static final int OPTIMISTIC = 3;
   public static final int REFERENTIAL_INTEGRITY = 4;
   public static final int OBJECT_EXISTS = 5;

   public StoreException(String msg) {
      super(msg);
   }

   public StoreException(Localizer.Message msg) {
      super(msg.getMessage());
   }

   public StoreException(Throwable cause) {
      super(cause);
   }

   public int getType() {
      return 2;
   }
}
