package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;

public interface ResourceTransactionDefinition extends TransactionDefinition {
   boolean isLocalResource();
}
