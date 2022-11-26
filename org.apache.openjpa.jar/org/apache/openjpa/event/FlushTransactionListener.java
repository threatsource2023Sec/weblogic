package org.apache.openjpa.event;

public interface FlushTransactionListener {
   void beforeFlush(TransactionEvent var1);

   void afterFlush(TransactionEvent var1);
}
