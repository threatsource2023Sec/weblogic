package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationUtils;
import java.util.List;
import javax.transaction.Synchronization;

public class JtaAfterCompletionSynchronization implements Synchronization {
   private final List synchronizations;

   public JtaAfterCompletionSynchronization(List synchronizations) {
      this.synchronizations = synchronizations;
   }

   public void beforeCompletion() {
   }

   public void afterCompletion(int status) {
      switch (status) {
         case 3:
            try {
               TransactionSynchronizationUtils.invokeAfterCommit(this.synchronizations);
               break;
            } finally {
               TransactionSynchronizationUtils.invokeAfterCompletion(this.synchronizations, 0);
            }
         case 4:
            TransactionSynchronizationUtils.invokeAfterCompletion(this.synchronizations, 1);
            break;
         default:
            TransactionSynchronizationUtils.invokeAfterCompletion(this.synchronizations, 2);
      }

   }
}
