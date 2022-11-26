package weblogic.store.internal;

import java.nio.ByteBuffer;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentStoreException;
import weblogic.store.io.IORecord;
import weblogic.store.io.PersistentStoreIO;

final class ReadRequest extends StoreRequest {
   private final ObjectHandler handler;
   private Object result;

   ReadRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, ObjectHandler handler) {
      super(handle, connection, 0);
      this.handler = handler;
   }

   ReadRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, ObjectHandler handler, boolean readFailureFatal) {
      super(handle, connection, 0, -1L, -1L, readFailureFatal);
      this.handler = handler;
   }

   void run(PersistentStoreIO ios) throws PersistentStoreException {
      IORecord iorec = ios.read(this.handle.getStoreHandle(), this.typeCode);
      ByteBuffer[] body = new ByteBuffer[]{iorec.getData()};
      this.result = new PersistentStoreRecordImpl(this.handle, body, this.handler, this.connection, false);
      this.connection.getStatisticsImpl().incrementReadCount();
      this.connection.getStoreImpl().getStatisticsImpl().incrementPhysicalReadCount();
   }

   protected boolean requiresFlush() {
      return false;
   }

   void override(ByteBuffer[] body) {
      try {
         this.result = new PersistentStoreRecordImpl(this.handle, body, this.handler, this.connection, true);
      } catch (PersistentStoreException var3) {
         this.result = var3;
      }

   }

   Object getResult() {
      return this.result;
   }

   void coalesce(StoreRequest other) {
   }

   int getType() {
      return 2;
   }
}
