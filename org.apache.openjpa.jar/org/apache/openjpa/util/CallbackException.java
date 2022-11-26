package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class CallbackException extends UserException {
   public CallbackException(Localizer.Message msg) {
      super(msg);
   }

   public CallbackException(Throwable cause) {
      super(cause);
   }

   public int getSubtype() {
      return 4;
   }
}
