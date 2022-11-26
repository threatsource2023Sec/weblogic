package weblogic.management.runtime;

import weblogic.utils.NestedException;

public final class MigrationException extends NestedException {
   public MigrationException(String message, Throwable t) {
      super(message, t);
   }

   public MigrationException(Throwable t) {
      this("", t);
   }

   public MigrationException(String message) {
      this(message, (Throwable)null);
   }
}
