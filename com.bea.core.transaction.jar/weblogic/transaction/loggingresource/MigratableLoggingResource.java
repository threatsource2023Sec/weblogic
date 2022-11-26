package weblogic.transaction.loggingresource;

import javax.transaction.xa.Xid;

public interface MigratableLoggingResource extends LoggingResource {
   void migratableActivate(String var1);

   void migratableDeactivate(String var1);

   byte[][] recoverXARecords(String var1) throws LoggingResourceException;

   void deleteXARecord(Xid var1, String var2);
}
