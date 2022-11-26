package weblogic.store.internal;

import weblogic.store.PersistentStoreException;
import weblogic.store.StoreLogger;
import weblogic.store.io.PersistentStoreIO;

final class DeleteRequest extends StoreRequest {
   DeleteRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, int flags) {
      this(handle, connection, flags, -1L, -1L);
   }

   DeleteRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, int flags, long flushGroup, long liveSequence) {
      super(handle, connection, flags, flushGroup, liveSequence);
      if (handle.getStoreHandle() == -1) {
         throw new AssertionError("Attempting to delete record that has already been deleted");
      }
   }

   void run(PersistentStoreIO ios) throws PersistentStoreException {
      ios.delete(this.handle.getStoreHandle(), this.typeCode, this.flags);
      this.connection.getStatisticsImpl().incrementDeleteCount();
   }

   protected boolean requiresFlush() {
      return true;
   }

   void coalesce(StoreRequest other) {
      switch (other.getType()) {
         case 1:
         case 5:
         case 6:
         case 7:
         case 8:
            return;
         case 2:
         case 3:
         default:
            other.finishIO();
            other.setError(new PersistentStoreException(StoreLogger.logStoreRecordNotFoundLoggable((long)this.handle.getStoreHandle())));
            return;
         case 4:
            other.finishIO();
      }
   }

   public int getType() {
      return 4;
   }
}
