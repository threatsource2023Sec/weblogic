package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class NoResultException extends InvalidStateException {
   public NoResultException(Localizer.Message msg) {
      super(msg);
   }

   public NoResultException(Localizer.Message msg, Object failed) {
      super(msg);
      this.setFailedObject(failed);
   }

   public int getSubtype() {
      return 5;
   }
}
