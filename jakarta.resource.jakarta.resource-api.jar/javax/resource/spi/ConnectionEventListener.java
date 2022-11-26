package javax.resource.spi;

import java.util.EventListener;

public interface ConnectionEventListener extends EventListener {
   void connectionClosed(ConnectionEvent var1);

   void localTransactionStarted(ConnectionEvent var1);

   void localTransactionCommitted(ConnectionEvent var1);

   void localTransactionRolledback(ConnectionEvent var1);

   void connectionErrorOccurred(ConnectionEvent var1);
}
