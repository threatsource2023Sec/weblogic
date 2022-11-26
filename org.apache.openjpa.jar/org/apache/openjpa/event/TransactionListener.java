package org.apache.openjpa.event;

public interface TransactionListener extends BeginTransactionListener, FlushTransactionListener, EndTransactionListener {
}
