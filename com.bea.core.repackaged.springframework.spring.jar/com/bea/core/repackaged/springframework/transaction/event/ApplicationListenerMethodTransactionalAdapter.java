package com.bea.core.repackaged.springframework.transaction.event;

import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.event.ApplicationListenerMethodAdapter;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronization;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationAdapter;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationManager;
import java.lang.reflect.Method;

class ApplicationListenerMethodTransactionalAdapter extends ApplicationListenerMethodAdapter {
   private final TransactionalEventListener annotation;

   public ApplicationListenerMethodTransactionalAdapter(String beanName, Class targetClass, Method method) {
      super(beanName, targetClass, method);
      TransactionalEventListener ann = (TransactionalEventListener)AnnotatedElementUtils.findMergedAnnotation(method, TransactionalEventListener.class);
      if (ann == null) {
         throw new IllegalStateException("No TransactionalEventListener annotation found on method: " + method);
      } else {
         this.annotation = ann;
      }
   }

   public void onApplicationEvent(ApplicationEvent event) {
      if (TransactionSynchronizationManager.isSynchronizationActive()) {
         TransactionSynchronization transactionSynchronization = this.createTransactionSynchronization(event);
         TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
      } else if (this.annotation.fallbackExecution()) {
         if (this.annotation.phase() == TransactionPhase.AFTER_ROLLBACK && this.logger.isWarnEnabled()) {
            this.logger.warn("Processing " + event + " as a fallback execution on AFTER_ROLLBACK phase");
         }

         this.processEvent(event);
      } else if (this.logger.isDebugEnabled()) {
         this.logger.debug("No transaction is active - skipping " + event);
      }

   }

   private TransactionSynchronization createTransactionSynchronization(ApplicationEvent event) {
      return new TransactionSynchronizationEventAdapter(this, event, this.annotation.phase());
   }

   private static class TransactionSynchronizationEventAdapter extends TransactionSynchronizationAdapter {
      private final ApplicationListenerMethodAdapter listener;
      private final ApplicationEvent event;
      private final TransactionPhase phase;

      public TransactionSynchronizationEventAdapter(ApplicationListenerMethodAdapter listener, ApplicationEvent event, TransactionPhase phase) {
         this.listener = listener;
         this.event = event;
         this.phase = phase;
      }

      public int getOrder() {
         return this.listener.getOrder();
      }

      public void beforeCommit(boolean readOnly) {
         if (this.phase == TransactionPhase.BEFORE_COMMIT) {
            this.processEvent();
         }

      }

      public void afterCompletion(int status) {
         if (this.phase == TransactionPhase.AFTER_COMMIT && status == 0) {
            this.processEvent();
         } else if (this.phase == TransactionPhase.AFTER_ROLLBACK && status == 1) {
            this.processEvent();
         } else if (this.phase == TransactionPhase.AFTER_COMPLETION) {
            this.processEvent();
         }

      }

      protected void processEvent() {
         this.listener.processEvent(this.event);
      }
   }
}
