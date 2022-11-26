package weblogic.store;

import weblogic.logging.Loggable;

public final class PersistentStoreRuntimeException extends RuntimeException {
   public static final long serialVersionUID = 8693566772284952008L;

   public PersistentStoreRuntimeException(Loggable message) {
      super(message.getMessage());
   }

   public PersistentStoreRuntimeException(Loggable message, Throwable cause) {
      super(message.getMessage(), cause);
   }

   public PersistentStoreRuntimeException(Throwable cause) {
      super(cause);
   }
}
