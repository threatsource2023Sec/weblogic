package org.jboss.weld.resolution;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.weld.bootstrap.events.ProcessAnnotatedTypeEventResolvable;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.event.ContainerLifecycleEventObserverMethod;
import org.jboss.weld.event.ResolvedObservers;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.reflection.Reflections;

public class TypeSafeObserverResolver extends TypeSafeResolver {
   private final MetaAnnotationStore metaAnnotationStore;
   private final AssignabilityRules rules;

   public TypeSafeObserverResolver(MetaAnnotationStore metaAnnotationStore, Iterable observers, WeldConfiguration configuration) {
      super(observers, configuration);
      this.metaAnnotationStore = metaAnnotationStore;
      this.rules = EventTypeAssignabilityRules.instance();
   }

   protected boolean matches(Resolvable resolvable, ObserverMethod observer) {
      if (!this.rules.matches(observer.getObservedType(), resolvable.getTypes())) {
         return false;
      } else if (!Beans.containsAllQualifiers(QualifierInstance.of(observer.getObservedQualifiers(), this.metaAnnotationStore), resolvable.getQualifiers())) {
         return false;
      } else if (observer instanceof ContainerLifecycleEventObserverMethod) {
         ContainerLifecycleEventObserverMethod lifecycleObserver = (ContainerLifecycleEventObserverMethod)observer;
         if (resolvable instanceof ProcessAnnotatedTypeEventResolvable && !lifecycleObserver.getRequiredAnnotations().isEmpty()) {
            ProcessAnnotatedTypeEventResolvable patResolvable = (ProcessAnnotatedTypeEventResolvable)resolvable;
            return patResolvable.containsRequiredAnnotations(lifecycleObserver.getRequiredAnnotations());
         } else {
            return true;
         }
      } else {
         return !this.isContainerLifecycleEvent(resolvable);
      }
   }

   protected boolean isContainerLifecycleEvent(Resolvable resolvable) {
      Iterator var2 = resolvable.getTypes().iterator();

      Type type;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         type = (Type)var2.next();
      } while(!Observers.CONTAINER_LIFECYCLE_EVENT_TYPES.contains(Reflections.getRawType(type)));

      return true;
   }

   protected Set filterResult(Set matched) {
      return matched;
   }

   protected List sortResult(Set matched) {
      List observers = new ArrayList(matched);
      Collections.sort(observers, TypeSafeObserverResolver.ObserverMethodComparator.INSTANCE);
      return observers;
   }

   protected ResolvedObservers makeResultImmutable(List result) {
      return ResolvedObservers.of((List)Reflections.cast(result));
   }

   public MetaAnnotationStore getMetaAnnotationStore() {
      return this.metaAnnotationStore;
   }

   private static class ObserverMethodComparator implements Comparator, Serializable {
      private static final long serialVersionUID = 1L;
      private static ObserverMethodComparator INSTANCE = new ObserverMethodComparator();

      public int compare(ObserverMethod o1, ObserverMethod o2) {
         return o1.getPriority() - o2.getPriority();
      }
   }
}
