package org.jboss.weld.module;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.event.ObserverNotifier;
import org.jboss.weld.resolution.TypeSafeObserverResolver;

public interface ObserverNotifierFactory extends Service {
   ObserverNotifier create(String var1, TypeSafeObserverResolver var2, ServiceRegistry var3, boolean var4);

   default void cleanup() {
   }
}
