package org.python.google.common.eventbus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Throwables;
import org.python.google.common.cache.CacheBuilder;
import org.python.google.common.cache.CacheLoader;
import org.python.google.common.cache.LoadingCache;
import org.python.google.common.collect.HashMultimap;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Iterators;
import org.python.google.common.collect.Lists;
import org.python.google.common.collect.Maps;
import org.python.google.common.collect.Multimap;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.common.reflect.TypeToken;
import org.python.google.common.util.concurrent.UncheckedExecutionException;
import org.python.google.j2objc.annotations.Weak;

final class SubscriberRegistry {
   private final ConcurrentMap subscribers = Maps.newConcurrentMap();
   @Weak
   private final EventBus bus;
   private static final LoadingCache subscriberMethodsCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader() {
      public ImmutableList load(Class concreteClass) throws Exception {
         return SubscriberRegistry.getAnnotatedMethodsNotCached(concreteClass);
      }
   });
   private static final LoadingCache flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader() {
      public ImmutableSet load(Class concreteClass) {
         return ImmutableSet.copyOf((Collection)TypeToken.of(concreteClass).getTypes().rawTypes());
      }
   });

   SubscriberRegistry(EventBus bus) {
      this.bus = (EventBus)Preconditions.checkNotNull(bus);
   }

   void register(Object listener) {
      Multimap listenerMethods = this.findAllSubscribers(listener);

      Collection eventMethodsInListener;
      CopyOnWriteArraySet eventSubscribers;
      for(Iterator var3 = listenerMethods.asMap().entrySet().iterator(); var3.hasNext(); eventSubscribers.addAll(eventMethodsInListener)) {
         Map.Entry entry = (Map.Entry)var3.next();
         Class eventType = (Class)entry.getKey();
         eventMethodsInListener = (Collection)entry.getValue();
         eventSubscribers = (CopyOnWriteArraySet)this.subscribers.get(eventType);
         if (eventSubscribers == null) {
            CopyOnWriteArraySet newSet = new CopyOnWriteArraySet();
            eventSubscribers = (CopyOnWriteArraySet)MoreObjects.firstNonNull(this.subscribers.putIfAbsent(eventType, newSet), newSet);
         }
      }

   }

   void unregister(Object listener) {
      Multimap listenerMethods = this.findAllSubscribers(listener);
      Iterator var3 = listenerMethods.asMap().entrySet().iterator();

      Collection listenerMethodsForType;
      CopyOnWriteArraySet currentSubscribers;
      do {
         if (!var3.hasNext()) {
            return;
         }

         Map.Entry entry = (Map.Entry)var3.next();
         Class eventType = (Class)entry.getKey();
         listenerMethodsForType = (Collection)entry.getValue();
         currentSubscribers = (CopyOnWriteArraySet)this.subscribers.get(eventType);
      } while(currentSubscribers != null && currentSubscribers.removeAll(listenerMethodsForType));

      throw new IllegalArgumentException("missing event subscriber for an annotated method. Is " + listener + " registered?");
   }

   @VisibleForTesting
   Set getSubscribersForTesting(Class eventType) {
      return (Set)MoreObjects.firstNonNull(this.subscribers.get(eventType), ImmutableSet.of());
   }

   Iterator getSubscribers(Object event) {
      ImmutableSet eventTypes = flattenHierarchy(event.getClass());
      List subscriberIterators = Lists.newArrayListWithCapacity(eventTypes.size());
      UnmodifiableIterator var4 = eventTypes.iterator();

      while(var4.hasNext()) {
         Class eventType = (Class)var4.next();
         CopyOnWriteArraySet eventSubscribers = (CopyOnWriteArraySet)this.subscribers.get(eventType);
         if (eventSubscribers != null) {
            subscriberIterators.add(eventSubscribers.iterator());
         }
      }

      return Iterators.concat(subscriberIterators.iterator());
   }

   private Multimap findAllSubscribers(Object listener) {
      Multimap methodsInListener = HashMultimap.create();
      Class clazz = listener.getClass();
      UnmodifiableIterator var4 = getAnnotatedMethods(clazz).iterator();

      while(var4.hasNext()) {
         Method method = (Method)var4.next();
         Class[] parameterTypes = method.getParameterTypes();
         Class eventType = parameterTypes[0];
         methodsInListener.put(eventType, Subscriber.create(this.bus, listener, method));
      }

      return methodsInListener;
   }

   private static ImmutableList getAnnotatedMethods(Class clazz) {
      return (ImmutableList)subscriberMethodsCache.getUnchecked(clazz);
   }

   private static ImmutableList getAnnotatedMethodsNotCached(Class clazz) {
      Set supertypes = TypeToken.of(clazz).getTypes().rawTypes();
      Map identifiers = Maps.newHashMap();
      Iterator var3 = supertypes.iterator();

      while(var3.hasNext()) {
         Class supertype = (Class)var3.next();
         Method[] var5 = supertype.getDeclaredMethods();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            if (method.isAnnotationPresent(Subscribe.class) && !method.isSynthetic()) {
               Class[] parameterTypes = method.getParameterTypes();
               Preconditions.checkArgument(parameterTypes.length == 1, "Method %s has @Subscribe annotation but has %s parameters.Subscriber methods must have exactly 1 parameter.", method, (int)parameterTypes.length);
               MethodIdentifier ident = new MethodIdentifier(method);
               if (!identifiers.containsKey(ident)) {
                  identifiers.put(ident, method);
               }
            }
         }
      }

      return ImmutableList.copyOf(identifiers.values());
   }

   @VisibleForTesting
   static ImmutableSet flattenHierarchy(Class concreteClass) {
      try {
         return (ImmutableSet)flattenHierarchyCache.getUnchecked(concreteClass);
      } catch (UncheckedExecutionException var2) {
         throw Throwables.propagate(var2.getCause());
      }
   }

   private static final class MethodIdentifier {
      private final String name;
      private final List parameterTypes;

      MethodIdentifier(Method method) {
         this.name = method.getName();
         this.parameterTypes = Arrays.asList(method.getParameterTypes());
      }

      public int hashCode() {
         return Objects.hashCode(this.name, this.parameterTypes);
      }

      public boolean equals(@Nullable Object o) {
         if (!(o instanceof MethodIdentifier)) {
            return false;
         } else {
            MethodIdentifier ident = (MethodIdentifier)o;
            return this.name.equals(ident.name) && this.parameterTypes.equals(ident.parameterTypes);
         }
      }
   }
}
