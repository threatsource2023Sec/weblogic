package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class UserException extends OpenJPAException {
   public static final int METADATA = 1;
   public static final int INVALID_STATE = 2;
   public static final int NO_TRANSACTION = 3;
   public static final int CALLBACK = 4;
   public static final int NO_RESULT = 5;
   public static final int NON_UNIQUE_RESULT = 6;

   public UserException() {
   }

   public UserException(String msg) {
      super(msg);
   }

   public UserException(Localizer.Message msg) {
      super(msg);
   }

   public UserException(Throwable cause) {
      super(cause);
   }

   public UserException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public UserException(Localizer.Message msg, Throwable cause) {
      super(msg, cause);
   }

   public int getType() {
      return 4;
   }
}
