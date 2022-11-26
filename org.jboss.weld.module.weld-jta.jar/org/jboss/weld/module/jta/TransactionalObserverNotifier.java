package org.jboss.weld.module.jta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.transaction.RollbackException;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.event.ObserverNotifier;
import org.jboss.weld.module.ObserverNotifierFactory;
import org.jboss.weld.resolution.TypeSafeObserverResolver;
import org.jboss.weld.transaction.spi.TransactionServices;

class TransactionalObserverNotifier extends ObserverNotifier {
   static final ObserverNotifierFactory FACTORY = new ObserverNotifierFactory() {
      public ObserverNotifier create(String contextId, TypeSafeObserverResolver resolver, ServiceRegistry services, boolean strict) {
         return new TransactionalObserverNotifier(contextId, resolver, services, strict);
      }
   };
   private final TransactionServices transactionServices;
   private final String contextId;

   TransactionalObserverNotifier(String contextId, TypeSafeObserverResolver resolver, ServiceRegistry services, boolean strict) {
      super(contextId, resolver, services, strict);
      this.contextId = contextId;
      this.transactionServices = (TransactionServices)services.get(TransactionServices.class);
   }

   private void deferNotification(Object event, EventMetadata metadata, ObserverMethod observer, List notifications) {
      TransactionPhase transactionPhase = observer.getTransactionPhase();
      boolean before = transactionPhase.equals(TransactionPhase.BEFORE_COMPLETION);
      Status status = Status.valueOf(transactionPhase);
      notifications.add(new DeferredEventNotification(this.contextId, event, metadata, observer, this.currentEventMetadata, status, before));
   }

   protected void notifyTransactionObservers(List observers, Object event, EventMetadata metadata, ObserverNotifier.ObserverExceptionHandler handler) {
      if (!observers.isEmpty()) {
         if (this.transactionServices != null && this.transactionServices.isTransactionActive()) {
            List notifications = new ArrayList();
            Iterator var6 = observers.iterator();

            while(var6.hasNext()) {
               ObserverMethod observer = (ObserverMethod)var6.next();
               this.deferNotification(event, metadata, observer, notifications);
            }

            try {
               this.transactionServices.registerSynchronization(new TransactionNotificationSynchronization(notifications));
            } catch (Exception var8) {
               if (!(var8.getCause() instanceof RollbackException) && !(var8.getCause() instanceof IllegalStateException)) {
                  throw var8;
               }

               List filteredObservers = (List)observers.stream().filter((observerMethod) -> {
                  return !observerMethod.getTransactionPhase().equals(TransactionPhase.AFTER_SUCCESS);
               }).sorted((o1, o2) -> {
                  return o2.getTransactionPhase().toString().compareTo(o1.getTransactionPhase().toString());
               }).collect(Collectors.toList());
               this.notifySyncObservers(filteredObservers, event, metadata, handler);
            }
         } else {
            this.notifySyncObservers(observers, event, metadata, handler);
         }

      }
   }
}
