package weblogic.jms.client;

import weblogic.jms.extensions.WLJMSContext;

public interface JMSContextInternal extends WLJMSContext {
   boolean getUserTransactionsEnabled();

   boolean isXAServerEnabled();
}
