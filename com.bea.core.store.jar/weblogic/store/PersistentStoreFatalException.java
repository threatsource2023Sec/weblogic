package weblogic.store;

import weblogic.logging.Loggable;

public final class PersistentStoreFatalException extends PersistentStoreException {
   public static final long serialVersionUID = -9113884236167962484L;

   public PersistentStoreFatalException(PersistentStoreException pse) {
      super(pse.getMessage(), pse.getCause());
   }

   public PersistentStoreFatalException(Loggable message) {
      super(message);
   }

   public PersistentStoreFatalException(Loggable message, Throwable cause) {
      super(message, cause);
   }
}
