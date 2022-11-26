package org.jboss.weld.bootstrap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.event.DefaultObserverNotifierFactory;
import org.jboss.weld.event.EventMetadataImpl;
import org.jboss.weld.event.ObserverNotifier;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.resolution.TypeSafeObserverResolver;
import org.jboss.weld.util.collections.Iterables;

public final class BeanDeploymentModule {
   private final String id;
   private final boolean web;
   private final ObserverNotifier notifier;
   private final Set managers;

   BeanDeploymentModule(String moduleId, String contextId, boolean web, ServiceRegistry services) {
      this.id = moduleId;
      this.web = web;
      this.managers = new CopyOnWriteArraySet();
      Iterable observers = Iterables.flatMap(this.managers, BeanManagerImpl::getObservers);
      TypeSafeObserverResolver resolver = new TypeSafeObserverResolver((MetaAnnotationStore)services.get(MetaAnnotationStore.class), observers, (WeldConfiguration)services.get(WeldConfiguration.class));
      this.notifier = DefaultObserverNotifierFactory.INSTANCE.create(contextId, resolver, services, false);
   }

   public String getId() {
      return this.id;
   }

   public boolean isWebModule() {
      return this.web;
   }

   public void fireEvent(Type eventType, Object event, Annotation... qualifiers) {
      EventMetadata metadata = new EventMetadataImpl(eventType, (InjectionPoint)null, qualifiers);
      this.notifier.fireEvent(eventType, event, metadata, qualifiers);
   }

   void addManager(BeanManagerImpl manager) {
      if (this.managers.add(manager)) {
         this.notifier.clear();
      }

   }

   public String toString() {
      return "BeanDeploymentModule [id=" + this.id + ", web=" + this.web + ", managers=" + Iterables.transform(this.managers, BeanManagerImpl::getId) + "]";
   }
}
