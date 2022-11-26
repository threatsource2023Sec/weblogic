package org.jboss.weld.module.jta;

import java.lang.annotation.Annotation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.weld.Container;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.event.CurrentEventMetadata;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.util.Observers;

class DeferredEventNotification implements Runnable {
   protected final ObserverMethod observer;
   private final Object event;
   protected final EventMetadata metadata;
   private final CurrentEventMetadata currentEventMetadata;
   private final String contextId;
   private final Status status;
   private final boolean before;

   public DeferredEventNotification(String contextId, Object event, EventMetadata metadata, ObserverMethod observer, CurrentEventMetadata currentEventMetadata, Status status, boolean before) {
      this.contextId = contextId;
      this.observer = observer;
      this.event = event;
      this.metadata = metadata;
      this.currentEventMetadata = currentEventMetadata;
      this.status = status;
      this.before = before;
   }

   public void run() {
      try {
         EventLogger.LOG.asyncFire(this.metadata, this.observer);
         (new RunInRequest(this.contextId) {
            protected void execute() {
               ThreadLocalStack.ThreadLocalStackReference stack = DeferredEventNotification.this.currentEventMetadata.pushIfNotNull(DeferredEventNotification.this.metadata);

               try {
                  Observers.notify(DeferredEventNotification.this.observer, DeferredEventNotification.this.event, DeferredEventNotification.this.metadata);
               } finally {
                  stack.pop();
               }

            }
         }).run();
      } catch (Exception var2) {
         EventLogger.LOG.asyncObserverFailure(this.observer, this.metadata, var2.getCause() != null ? var2.getCause() : var2);
         EventLogger.LOG.catchingDebug(var2);
      }

   }

   public Status getStatus() {
      return this.status;
   }

   public boolean isBefore() {
      return this.before;
   }

   public String toString() {
      return "Deferred event [" + this.event + "] for [" + this.observer + "]";
   }

   private abstract static class RunInRequest {
      private final String contextId;

      public RunInRequest(String contextId) {
         this.contextId = contextId;
      }

      protected abstract void execute();

      public void run() {
         if (this.isRequestContextActive()) {
            this.execute();
         } else {
            RequestContext requestContext = (RequestContext)Container.instance(this.contextId).deploymentManager().instance().select(RequestContext.class, new Annotation[]{UnboundLiteral.INSTANCE}).get();

            try {
               requestContext.activate();
               this.execute();
            } finally {
               requestContext.invalidate();
               requestContext.deactivate();
            }
         }

      }

      private boolean isRequestContextActive() {
         return Container.instance(this.contextId).deploymentManager().isContextActive(RequestScoped.class);
      }
   }
}
