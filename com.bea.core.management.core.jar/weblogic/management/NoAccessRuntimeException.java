package weblogic.management;

import weblogic.utils.NestedRuntimeException;

public final class NoAccessRuntimeException extends NestedRuntimeException {
   private static final long serialVersionUID = -2938124329563694886L;
   private transient String details;

   public NoAccessRuntimeException(String message, Throwable t) {
      super(message, t);
      this.details = null;
      this.details = message;
   }

   public NoAccessRuntimeException(Throwable t) {
      this("", t);
   }

   public NoAccessRuntimeException(String message) {
      this(message, (Throwable)null);
   }

   public NoAccessRuntimeException(String user, String perm, String target) {
      this(user, perm, target, (Throwable)null);
   }

   public NoAccessRuntimeException(String user, String perm, String target, Throwable t) {
      this("User " + user + " does not have " + perm + " permission on " + target, t);
   }

   public String getMessage() {
      return this.details == null ? super.getMessage() : this.details;
   }
}
