package weblogic.store.internal;

import weblogic.store.PersistentStoreException;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.file.ReplicatedStoreIO;

final class PollRequest extends StoreRequest {
   void run(PersistentStoreIO ios) throws PersistentStoreException {
      if (ios instanceof ReplicatedStoreIO) {
         ((ReplicatedStoreIO)ios).poll();
      }

   }

   final synchronized void setCrashTestException(PersistentStoreException arg) {
      this.crashTestException = arg;
   }

   boolean requiresFlush() {
      return false;
   }

   void coalesce(StoreRequest other) {
   }

   int getType() {
      return 8;
   }
}
