package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class NoTransactionException extends InvalidStateException {
   public NoTransactionException(Localizer.Message msg) {
      super(msg);
   }

   public int getSubtype() {
      return 3;
   }
}
