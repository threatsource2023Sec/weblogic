package weblogic.persist;

import java.io.IOException;
import javax.transaction.TransactionRolledbackException;

public interface TxQueueFile extends TxFile {
   void put(Object var1) throws IOException;

   Object get() throws TransactionRolledbackException;

   Object getW() throws DeadlockException, TransactionRolledbackException;

   Object getW(long var1) throws QueueTimeoutException, TransactionRolledbackException;
}
