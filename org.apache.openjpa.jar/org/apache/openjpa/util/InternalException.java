package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class InternalException extends OpenJPAException {
   public InternalException() {
      this.setFatal(true);
   }

   public InternalException(String msg) {
      super(msg);
      this.setFatal(true);
   }

   public InternalException(Localizer.Message msg) {
      super(msg);
      this.setFatal(true);
   }

   public InternalException(Throwable cause) {
      super(cause);
      this.setFatal(true);
   }

   public InternalException(String msg, Throwable cause) {
      super(msg, cause);
      this.setFatal(true);
   }

   public InternalException(Localizer.Message msg, Throwable cause) {
      super(msg, cause);
      this.setFatal(true);
   }

   public int getType() {
      return 1;
   }
}
