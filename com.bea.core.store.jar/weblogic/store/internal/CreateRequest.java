package weblogic.store.internal;

import java.nio.ByteBuffer;
import weblogic.store.PersistentStoreException;
import weblogic.store.io.PersistentStoreIO;

final class CreateRequest extends StoreRequest {
   private ByteBuffer[] data;

   CreateRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, ByteBuffer[] data, int flags) {
      this(handle, connection, data, flags, -1L, -1L);
   }

   CreateRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, ByteBuffer[] data, int flags, long flushGroup, long liveSequence) {
      super(handle, connection, flags, flushGroup, liveSequence);
      this.data = data;
   }

   void run(PersistentStoreIO ios) throws PersistentStoreException {
      if (this.getCrashTestException() != null) {
         if (this.getCrashTestException() instanceof PersistentStoreException) {
            ios.setTestException(this.getCrashTestException());
         } else {
            this.setError(this.getCrashTestException());
         }
      }

      ios.create(this.handle.getStoreHandle(), this.typeCode, this.data, this.flags);
      this.connection.getStatisticsImpl().incrementCreateCount();
   }

   final synchronized void setCrashTestException(PersistentStoreException arg) {
      this.crashTestException = arg;
   }

   boolean requiresFlush() {
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
            ReadRequest read = (ReadRequest)other;
            ByteBuffer[] bodyCopy = null;
            if (this.data != null) {
               bodyCopy = new ByteBuffer[this.data.length];

               for(int i = 0; i < bodyCopy.length; ++i) {
                  bodyCopy[i] = this.data[i].duplicate();
               }
            }

            read.override(bodyCopy);
            read.finishIO();
            return;
         case 3:
            UpdateRequest update = (UpdateRequest)other;
            this.data = update.getData();
            update.finishIO();
            return;
         case 4:
            DeleteRequest delete = (DeleteRequest)other;
            delete.finishIO();
            if (!this.isIOFinished()) {
               this.connection.getStoreImpl().releaseHandle(this.typeCode, this.handle);
               this.finishIO();
            }

            return;
         default:
            throw new AssertionError("Unknown type: " + other.getType());
      }
   }

   int getType() {
      return 1;
   }
}
