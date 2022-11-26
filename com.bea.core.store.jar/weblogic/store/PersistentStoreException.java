package weblogic.store;

import weblogic.logging.Loggable;

public class PersistentStoreException extends Exception {
   public static final long serialVersionUID = -3782999997773519896L;

   public PersistentStoreException(Loggable message) {
      super(message.getMessage());
   }

   public PersistentStoreException(Loggable message, Throwable cause) {
      super(message.getMessage(), cause);
   }

   public PersistentStoreException(Throwable cause) {
      super(cause);
   }

   public PersistentStoreException(String text) {
      super(text);
   }

   public PersistentStoreException(String text, Throwable cause) {
      super(text, cause);
   }

   public final void log() {
      StoreLogger.logPersistentStoreException(this.getMessage());
   }
}
