package org.jboss.weld.event;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jboss.weld.bootstrap.api.BootstrapService;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.module.ObserverNotifierFactory;
import org.jboss.weld.resolution.TypeSafeObserverResolver;
import org.jboss.weld.util.collections.Iterators;

public class GlobalObserverNotifierService implements BootstrapService {
   private final Set beanManagers = new CopyOnWriteArraySet();
   private final ObserverNotifier globalLenientObserverNotifier;
   private final ObserverNotifier globalStrictObserverNotifier;

   public GlobalObserverNotifierService(ServiceRegistry services, String contextId) {
      TypeSafeObserverResolver resolver = new TypeSafeObserverResolver((MetaAnnotationStore)services.get(MetaAnnotationStore.class), createGlobalObserverMethodIterable(this.beanManagers), (WeldConfiguration)services.get(WeldConfiguration.class));
      ObserverNotifierFactory factory = (ObserverNotifierFactory)services.get(ObserverNotifierFactory.class);
      this.globalLenientObserverNotifier = factory.create(contextId, resolver, services, false);
      this.globalStrictObserverNotifier = factory.create(contextId, resolver, services, true);
   }

   private static Iterable createGlobalObserverMethodIterable(final Set beanManagers) {
      return new Iterable() {
         public Iterator iterator() {
            Iterator observerMethodIterators = Iterators.transform(beanManagers.iterator(), (beanManager) -> {
               return beanManager.getObservers().iterator();
            });
            return Iterators.concat(observerMethodIterators);
         }
      };
   }

   public void registerBeanManager(BeanManagerImpl manager) {
      this.beanManagers.add(manager);
   }

   public ObserverNotifier getGlobalLenientObserverNotifier() {
      return this.globalLenientObserverNotifier;
   }

   public ObserverNotifier getGlobalStrictObserverNotifier() {
      return this.globalStrictObserverNotifier;
   }

   public Iterable getAllObserverMethods() {
      return createGlobalObserverMethodIterable(this.beanManagers);
   }

   public void cleanupAfterBoot() {
      this.globalStrictObserverNotifier.clear();
      this.globalLenientObserverNotifier.clear();
   }

   public void cleanup() {
      this.cleanupAfterBoot();
      this.beanManagers.clear();
   }
}
