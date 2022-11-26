package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class InvalidStateException extends UserException {
   public InvalidStateException(Localizer.Message msg) {
      super(msg);
   }

   public InvalidStateException(Localizer.Message msg, Object failed) {
      super(msg);
      this.setFailedObject(failed);
   }

   public int getSubtype() {
      return 2;
   }
}
