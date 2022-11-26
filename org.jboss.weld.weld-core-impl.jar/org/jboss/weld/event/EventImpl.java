package org.jboss.weld.event;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Event;
import javax.enterprise.event.NotificationOptions;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.bean.builtin.AbstractFacade;
import org.jboss.weld.bean.builtin.FacadeInjectionPoint;
import org.jboss.weld.events.WeldEvent;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.EventObjectTypeResolverBuilder;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.TypeResolver;

@SuppressFBWarnings(
   value = {"SE_NO_SUITABLE_CONSTRUCTOR"},
   justification = "Uses SerializationProxy"
)
public class EventImpl extends AbstractFacade implements WeldEvent, Serializable {
   private static final String EVENT_ARGUMENT_NAME = "event";
   private static final String SUBTYPE_ARGUMENT_NAME = "subtype";
   private static final long serialVersionUID = 656782657242515455L;
   private static final int DEFAULT_CACHE_CAPACITY = 4;
   private static final NotificationOptions EMPTY_NOTIFICATION_OPTIONS = NotificationOptions.builder().build();
   private final transient HierarchyDiscovery injectionPointTypeHierarchy = new HierarchyDiscovery(this.getType());
   private transient volatile CachedObservers lastCachedObservers;
   private final transient Map cachedObservers = new ConcurrentHashMap(4);

   public static EventImpl of(InjectionPoint injectionPoint, BeanManagerImpl beanManager) {
      return new EventImpl(injectionPoint, beanManager);
   }

   private EventImpl(InjectionPoint injectionPoint, BeanManagerImpl beanManager) {
      super(injectionPoint, (CreationalContext)null, beanManager);
   }

   public String toString() {
      return Formats.formatAnnotations((Iterable)this.getQualifiers()) + " Event<" + Formats.formatType(this.getType()) + ">";
   }

   public void fire(Object event) {
      Preconditions.checkArgumentNotNull(event, "event");
      CachedObservers observers = this.getObservers(event);
      this.getBeanManager().getGlobalLenientObserverNotifier().notify(observers.observers, event, observers.metadata);
   }

   public CompletionStage fireAsync(Object event) {
      Preconditions.checkArgumentNotNull(event, "event");
      return this.fireAsyncInternal(event, EMPTY_NOTIFICATION_OPTIONS);
   }

   public CompletionStage fireAsync(Object event, NotificationOptions options) {
      Preconditions.checkArgumentNotNull(event, "event");
      Preconditions.checkArgumentNotNull(options, "options");
      return this.fireAsyncInternal(event, options);
   }

   private CompletionStage fireAsyncInternal(Object event, NotificationOptions options) {
      CachedObservers observers = this.getObservers(event);
      return this.getBeanManager().getGlobalLenientObserverNotifier().notifyAsync(observers.observers, event, observers.metadata, options);
   }

   private CachedObservers getObservers(Object event) {
      Class runtimeType = event.getClass();
      CachedObservers lastResolvedObservers = this.lastCachedObservers;
      if (lastResolvedObservers != null && lastResolvedObservers.rawType.equals(runtimeType)) {
         return lastResolvedObservers;
      } else {
         lastResolvedObservers = (CachedObservers)this.cachedObservers.get(runtimeType);
         if (lastResolvedObservers == null) {
            lastResolvedObservers = (CachedObservers)WeldCollections.putIfAbsent(this.cachedObservers, runtimeType, this.createCachedObservers(runtimeType));
         }

         return this.lastCachedObservers = lastResolvedObservers;
      }
   }

   private CachedObservers createCachedObservers(Class runtimeType) {
      Type eventType = this.getEventType(runtimeType);
      ResolvedObservers observers = this.getBeanManager().getGlobalStrictObserverNotifier().resolveObserverMethods(eventType, this.getQualifiers());
      EventMetadata metadata = new EventMetadataImpl(eventType, this.getInjectionPoint(), this.getQualifiers());
      return new CachedObservers(runtimeType, observers, metadata);
   }

   public WeldEvent select(Annotation... qualifiers) {
      return this.selectEvent(this.getType(), qualifiers);
   }

   public WeldEvent select(Class subtype, Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(subtype, "subtype");
      return this.selectEvent(subtype, qualifiers);
   }

   public WeldEvent select(TypeLiteral subtype, Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(subtype, "subtype");
      return this.selectEvent(subtype.getType(), qualifiers);
   }

   public WeldEvent select(Type type, Annotation... qualifiers) {
      if (!this.getType().equals(Object.class)) {
         throw EventLogger.LOG.selectByTypeOnlyWorksOnObject();
      } else {
         return this.selectEvent(type, qualifiers);
      }
   }

   private WeldEvent selectEvent(Type subtype, Annotation[] newQualifiers) {
      this.getBeanManager().getGlobalStrictObserverNotifier().checkEventObjectType(subtype);
      return new EventImpl(new FacadeInjectionPoint(this.getBeanManager(), this.getInjectionPoint(), Event.class, subtype, this.getQualifiers(), newQualifiers), this.getBeanManager());
   }

   protected Type getEventType(Class runtimeType) {
      Type resolvedType = runtimeType;
      if (Types.containsTypeVariable(runtimeType)) {
         resolvedType = this.injectionPointTypeHierarchy.resolveType(runtimeType);
      }

      if (Types.containsTypeVariable((Type)resolvedType)) {
         Type canonicalEventType = Types.getCanonicalType(runtimeType);
         TypeResolver objectTypeResolver = (new EventObjectTypeResolverBuilder(this.injectionPointTypeHierarchy.getResolver().getResolvedTypeVariables(), (new HierarchyDiscovery(canonicalEventType)).getResolver().getResolvedTypeVariables())).build();
         resolvedType = objectTypeResolver.resolveType(canonicalEventType);
      }

      return (Type)resolvedType;
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SerializationProxy(this);
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw EventLogger.LOG.serializationProxyRequired();
   }

   private class CachedObservers {
      private final Class rawType;
      private final ResolvedObservers observers;
      private final EventMetadata metadata;

      private CachedObservers(Class rawType, ResolvedObservers observers, EventMetadata metadata) {
         this.rawType = rawType;
         this.observers = observers;
         this.metadata = metadata;
      }

      // $FF: synthetic method
      CachedObservers(Class x1, ResolvedObservers x2, EventMetadata x3, Object x4) {
         this(x1, x2, x3);
      }
   }

   private static class SerializationProxy extends AbstractFacade.AbstractFacadeSerializationProxy {
      private static final long serialVersionUID = 9181171328831559650L;

      public SerializationProxy(EventImpl event) {
         super(event);
      }

      private Object readResolve() throws ObjectStreamException {
         return EventImpl.of(this.getInjectionPoint(), this.getBeanManager());
      }
   }
}
