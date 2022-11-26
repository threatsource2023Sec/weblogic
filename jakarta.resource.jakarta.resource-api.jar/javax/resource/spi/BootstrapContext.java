package javax.resource.spi;

import java.util.Timer;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;

public interface BootstrapContext {
   WorkManager getWorkManager();

   XATerminator getXATerminator();

   Timer createTimer() throws UnavailableException;

   boolean isContextSupported(Class var1);

   TransactionSynchronizationRegistry getTransactionSynchronizationRegistry();
}
