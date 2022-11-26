package weblogic.store.internal;

import java.nio.ByteBuffer;
import weblogic.store.PersistentStoreException;
import weblogic.store.io.PersistentStoreIO;

final class UpdateRequest extends StoreRequest {
   private ByteBuffer[] data;

   UpdateRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, ByteBuffer[] data, int flags) {
      this(handle, connection, data, flags, -1L, -1L);
   }

   UpdateRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, ByteBuffer[] data, int flags, long flushGroup, long liveSequence) {
      super(handle, connection, flags, flushGroup, liveSequence);
      this.data = data;
   }

   ByteBuffer[] getData() {
      return this.data;
   }

   void run(PersistentStoreIO ios) throws PersistentStoreException {
      ios.update(this.handle.getStoreHandle(), this.typeCode, this.data, this.flags);
      this.connection.getStatisticsImpl().incrementUpdateCount();
   }

   protected boolean requiresFlush() {
      return true;
   }

   void coalesce(StoreRequest other) {
      switch (other.getType()) {
         case 1:
            throw new AssertionError("Attempt to create a record that already exists");
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
            this.finishIO();
            return;
         case 5:
         case 6:
         case 7:
         case 8:
            return;
         default:
            throw new AssertionError("Unknown type: " + other.getType());
      }
   }

   public int getType() {
      return 3;
   }
}
