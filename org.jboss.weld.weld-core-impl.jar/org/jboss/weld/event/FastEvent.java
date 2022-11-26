package org.jboss.weld.event;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Observers;

public class FastEvent {
   protected final ResolvedObservers resolvedObserverMethods;

   public static FastEvent of(Class type, BeanManagerImpl manager, Annotation... qualifiers) {
      return of(type, manager, manager.getAccessibleLenientObserverNotifier(), qualifiers);
   }

   public static FastEvent of(Class type, BeanManagerImpl manager, ObserverNotifier notifier, Annotation... qualifiers) {
      ResolvedObservers resolvedObserverMethods = notifier.resolveObserverMethods(type, (Annotation[])qualifiers);
      if (resolvedObserverMethods.isMetadataRequired()) {
         EventMetadata metadata = new EventMetadataImpl(type, (InjectionPoint)null, qualifiers);
         CurrentEventMetadata metadataService = (CurrentEventMetadata)manager.getServices().get(CurrentEventMetadata.class);
         return new FastEventWithMetadataPropagation(resolvedObserverMethods, metadata, metadataService);
      } else {
         return new FastEvent(resolvedObserverMethods);
      }
   }

   private FastEvent(ResolvedObservers resolvedObserverMethods) {
      this.resolvedObserverMethods = resolvedObserverMethods;
   }

   public void fire(Object event) {
      Iterator var2 = this.resolvedObserverMethods.getImmediateSyncObservers().iterator();

      while(var2.hasNext()) {
         ObserverMethod observer = (ObserverMethod)var2.next();
         observer.notify(event);
      }

   }

   // $FF: synthetic method
   FastEvent(ResolvedObservers x0, Object x1) {
      this(x0);
   }

   private static class FastEventWithMetadataPropagation extends FastEvent {
      private final EventMetadata metadata;
      private final CurrentEventMetadata metadataService;

      private FastEventWithMetadataPropagation(ResolvedObservers resolvedObserverMethods, EventMetadata metadata, CurrentEventMetadata metadataService) {
         super(resolvedObserverMethods, null);
         this.metadata = metadata;
         this.metadataService = metadataService;
      }

      public void fire(Object event) {
         ThreadLocalStack.ThreadLocalStackReference stack = this.metadataService.pushIfNotNull(this.metadata);

         try {
            Iterator var3 = this.resolvedObserverMethods.getImmediateSyncObservers().iterator();

            while(var3.hasNext()) {
               ObserverMethod observer = (ObserverMethod)var3.next();
               Observers.notify(observer, event, this.metadata);
            }
         } finally {
            stack.pop();
         }

      }

      // $FF: synthetic method
      FastEventWithMetadataPropagation(ResolvedObservers x0, EventMetadata x1, CurrentEventMetadata x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
