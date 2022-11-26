package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class GeneralException extends OpenJPAException {
   public GeneralException() {
   }

   public GeneralException(String msg) {
      super(msg);
   }

   public GeneralException(Localizer.Message msg) {
      super(msg);
   }

   public GeneralException(Throwable cause) {
      super(cause);
   }

   public GeneralException(Localizer.Message msg, Throwable cause) {
      super(msg, cause);
   }

   public GeneralException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public int getType() {
      return 0;
   }
}
