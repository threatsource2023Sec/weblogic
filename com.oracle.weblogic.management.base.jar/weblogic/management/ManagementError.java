package weblogic.management;

import weblogic.utils.NestedError;

public final class ManagementError extends NestedError {
   private static final long serialVersionUID = 7024545900215683004L;

   public ManagementError(String message, Throwable t) {
      super(message, t);
   }

   public ManagementError(Throwable t) {
      this("", t);
   }

   public ManagementError(String message) {
      this(message, (Throwable)null);
   }
}
