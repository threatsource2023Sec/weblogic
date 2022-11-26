package weblogic.store.gxa;

import weblogic.logging.Loggable;
import weblogic.store.PersistentStoreException;

public class GXAException extends PersistentStoreException {
   public GXAException(Loggable message) {
      super(message);
   }

   public GXAException(Loggable message, Throwable cause) {
      super(message, cause);
   }

   public GXAException(Throwable cause) {
      super(cause);
   }

   /** @deprecated */
   @Deprecated
   public GXAException(String text) {
      super(text);
   }

   /** @deprecated */
   @Deprecated
   public GXAException(String text, Throwable cause) {
      super(text, cause);
   }
}
