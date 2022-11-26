package weblogic.store.internal;

import java.util.ArrayList;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentStoreException;
import weblogic.store.io.IORecord;
import weblogic.store.io.PersistentStoreIO;

class CursorRequest extends StoreRequest {
   private final int batchSize;
   private final ObjectHandler handler;
   private PersistentStoreIO.Cursor ioCursor;
   private ArrayList retList;

   CursorRequest(PersistentStoreConnectionImpl conn, int batchSize, PersistentStoreIO.Cursor curs, ObjectHandler handler, int flags) {
      super((PersistentHandleImpl)null, conn, flags);
      this.batchSize = batchSize;
      this.handler = handler;
      this.ioCursor = curs;
   }

   void run(PersistentStoreIO ios) throws PersistentStoreException {
      if (this.ioCursor == null) {
         this.ioCursor = ios.createCursor(this.typeCode, this.flags);
      }

      ArrayList rl = new ArrayList(this.batchSize);

      for(int i = 0; i < this.batchSize; ++i) {
         IORecord iorec = this.ioCursor.next();
         if (iorec == null) {
            rl.add((Object)null);
            break;
         }

         PersistentStoreRecordImpl psr = new PersistentStoreRecordImpl(iorec, this.handler, this.connection, true);
         rl.add(psr);
         this.connection.getStatisticsImpl().incrementReadCount();
         this.connection.getStoreImpl().getStatisticsImpl().incrementPhysicalReadCount();
      }

      this.retList = rl;
   }

   Object getResult() {
      return this.retList;
   }

   boolean requiresFlush() {
      return false;
   }

   void coalesce(StoreRequest other) {
   }

   int getType() {
      return 7;
   }

   PersistentStoreIO.Cursor getCursor() {
      return this.ioCursor;
   }
}
