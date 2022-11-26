package weblogic.management;

import weblogic.utils.NestedException;

/** @deprecated */
@Deprecated
public final class NoAccessException extends NestedException {
   private static final long serialVersionUID = 6933385751528674427L;

   public NoAccessException(String message, Throwable t) {
      super(message, t);
   }

   public NoAccessException(Throwable t) {
      this("", t);
   }

   public NoAccessException(String message) {
      this(message, (Throwable)null);
   }

   public NoAccessException(String user, String perm, String target) {
      this(user, perm, target, (Throwable)null);
   }

   public NoAccessException(String user, String perm, String target, Throwable t) {
      this("User " + user + " does not have " + perm + " permission  on " + target, t);
   }
}
