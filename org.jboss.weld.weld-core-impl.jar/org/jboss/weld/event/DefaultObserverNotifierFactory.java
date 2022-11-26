package org.jboss.weld.event;

import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.module.ObserverNotifierFactory;
import org.jboss.weld.resolution.TypeSafeObserverResolver;

public class DefaultObserverNotifierFactory implements ObserverNotifierFactory {
   public static final ObserverNotifierFactory INSTANCE = new DefaultObserverNotifierFactory();

   private DefaultObserverNotifierFactory() {
   }

   public ObserverNotifier create(String contextId, TypeSafeObserverResolver resolver, ServiceRegistry services, boolean strict) {
      return new ObserverNotifier(contextId, resolver, services, strict);
   }
}
