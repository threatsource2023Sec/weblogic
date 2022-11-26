package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class UnsupportedException extends OpenJPAException {
   public UnsupportedException() {
      this.setFatal(true);
   }

   public UnsupportedException(String msg) {
      super(msg);
      this.setFatal(true);
   }

   public UnsupportedException(Localizer.Message msg) {
      super(msg);
      this.setFatal(true);
   }

   public UnsupportedException(Localizer.Message msg, Throwable cause) {
      super(msg, cause);
      this.setFatal(true);
   }

   public int getType() {
      return 3;
   }
}
