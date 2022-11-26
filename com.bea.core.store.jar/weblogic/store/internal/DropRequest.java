package weblogic.store.internal;

import weblogic.store.PersistentStoreException;
import weblogic.store.io.PersistentStoreIO;

class DropRequest extends StoreRequest {
   private int currDeleteCount = 1;
   private PersistentStoreConnectionImpl sysConn;
   private PersistentHandleImpl sysConnPH;

   DropRequest(PersistentStoreConnectionImpl conn, PersistentStoreConnectionImpl sysConn, PersistentHandleImpl sysConnPH) {
      super((PersistentHandleImpl)null, conn, 0);
      this.sysConn = sysConn;
      this.sysConnPH = sysConnPH;
   }

   void run(PersistentStoreIO ios) throws PersistentStoreException {
      if (this.currDeleteCount > 0) {
         this.currDeleteCount = ios.drop(this.connection.getTypeCode());
         if (this.currDeleteCount > 0) {
            OperationStatisticsImpl stats = this.connection.getStatisticsImpl();
            stats.incrementDeleteCount((long)this.currDeleteCount);
            this.connection.getStoreImpl().scheduleSelf(this);
         } else {
            ios.delete(this.sysConnPH.getStoreHandle(), this.sysConn.getTypeCode(), this.flags);
         }

      }
   }

   boolean requiresFlush() {
      return true;
   }

   void coalesce(StoreRequest other) {
   }

   int getType() {
      return 5;
   }
}
