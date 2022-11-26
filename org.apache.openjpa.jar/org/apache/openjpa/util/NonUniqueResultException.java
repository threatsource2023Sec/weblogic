package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class NonUniqueResultException extends InvalidStateException {
   public NonUniqueResultException(Localizer.Message msg) {
      super(msg);
   }

   public NonUniqueResultException(Localizer.Message msg, Object failed) {
      super(msg);
      this.setFailedObject(failed);
   }

   public int getSubtype() {
      return 6;
   }
}
