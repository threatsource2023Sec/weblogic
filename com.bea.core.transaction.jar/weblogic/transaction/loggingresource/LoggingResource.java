package weblogic.transaction.loggingresource;

import javax.transaction.xa.Xid;
import weblogic.transaction.nonxa.NonXAResource;

public interface LoggingResource extends NonXAResource {
   void writeXARecord(Xid var1, byte[] var2) throws LoggingResourceException;

   byte[] getXARecord(Xid var1) throws LoggingResourceException;

   void deleteXARecord(Xid var1);

   byte[][] recoverXARecords() throws LoggingResourceException;

   String getName();
}
