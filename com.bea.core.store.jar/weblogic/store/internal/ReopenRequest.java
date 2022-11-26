package weblogic.store.internal;

import java.util.HashMap;
import weblogic.store.PersistentStoreException;
import weblogic.store.io.PersistentStoreIO;

class ReopenRequest extends StoreRequest {
   private final HashMap config;

   ReopenRequest(PersistentStoreConnectionImpl conn, HashMap config) {
      super((PersistentHandleImpl)null, conn, 0);
      this.config = config;
   }

   void run(PersistentStoreIO ios) throws PersistentStoreException {
      ios.flush();
      ios.close();
      ios.open(this.config);
      PersistentStoreImpl store = this.connection.getStoreImpl();
      store.setConfigInternal(this.config);
   }

   boolean requiresFlush() {
      return false;
   }

   void coalesce(StoreRequest other) {
   }

   int getType() {
      return 6;
   }
}
