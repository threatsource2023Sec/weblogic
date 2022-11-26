package org.glassfish.hk2.extras.events.internal;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.glassfish.hk2.api.AOPProxyCtl;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DynamicConfigurationListener;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.MethodParameter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Self;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Unqualified;
import org.glassfish.hk2.api.messaging.MessageReceiver;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.glassfish.hk2.api.messaging.Topic;
import org.glassfish.hk2.api.messaging.TopicDistributionService;
import org.glassfish.hk2.extras.events.DefaultTopicDistributionErrorService;
import org.glassfish.hk2.utilities.InjecteeImpl;
import org.glassfish.hk2.utilities.MethodParameterImpl;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.utilities.reflection.TypeChecker;
import org.glassfish.hk2.utilities.reflection.internal.ClassReflectionHelperImpl;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Optional;

@Singleton
@Named("HK2TopicDistributionService")
@ContractsProvided({TopicDistributionService.class, InstanceLifecycleListener.class, DynamicConfigurationListener.class})
public class DefaultTopicDistributionService implements TopicDistributionService, InstanceLifecycleListener, DynamicConfigurationListener {
   private static final Filter SUBSCRIBER_FILTER = new Filter() {
      public boolean matches(Descriptor d) {
         return d.getQualifiers().contains(MessageReceiver.class.getName());
      }
   };
   @Inject
   private ServiceLocator locator;
   @Inject
   private IterableProvider errorHandlers;
   @Inject
   @Self
   private ActiveDescriptor selfDescriptor;
   private final ClassReflectionHelper reflectionHelper = new ClassReflectionHelperImpl();
   private final HashMap descriptor2Classes = new HashMap();
   private final HashMap class2Subscribers = new HashMap();
   private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
   private final ReentrantReadWriteLock.WriteLock wLock;
   private final ReentrantReadWriteLock.ReadLock rLock;

   public DefaultTopicDistributionService() {
      this.wLock = this.readWriteLock.writeLock();
      this.rLock = this.readWriteLock.readLock();
   }

   private void fire(Object message, Method subscription, SubscriberInfo subscriptionInfo, Object target, ServiceLocator locator) throws Throwable {
      List mps = new ArrayList(subscriptionInfo.otherInjectees.length);

      for(int lcv = 0; lcv < subscriptionInfo.otherInjectees.length; ++lcv) {
         InjecteeImpl injectee = subscriptionInfo.otherInjectees[lcv];
         if (injectee == null) {
            mps.add(new MethodParameterImpl(lcv, message));
         } else if (injectee.isSelf()) {
            mps.add(new MethodParameterImpl(lcv, injectee.getInjecteeDescriptor()));
         }
      }

      ServiceHandle handle = locator.getServiceHandle(this.selfDescriptor);
      locator.assistedInject(target, subscription, handle, (MethodParameter[])mps.toArray(new MethodParameter[mps.size()]));
      List subHandles = handle.getSubHandles();
      Iterator var9 = subHandles.iterator();

      while(var9.hasNext()) {
         ServiceHandle subHandle = (ServiceHandle)var9.next();
         ActiveDescriptor ad = subHandle.getActiveDescriptor();
         if (ad != null && PerLookup.class.equals(ad.getScopeAnnotation())) {
            subHandle.destroy();
         }
      }

   }

   private List handleDescriptorToClass(ActiveDescriptor descriptor, Class clazz, Type eventType, Topic topic) {
      LinkedList retVal = new LinkedList();
      List subscribers = (List)this.class2Subscribers.get(new ActivatorClassKey(descriptor, clazz));
      if (subscribers == null) {
         subscribers = Collections.emptyList();
      }

      Iterator var7 = subscribers.iterator();

      while(true) {
         SubscriberInfo subscriberInfo;
         while(true) {
            Type subscriptionType;
            do {
               do {
                  if (!var7.hasNext()) {
                     return retVal;
                  }

                  subscriberInfo = (SubscriberInfo)var7.next();
                  subscriptionType = subscriberInfo.eventType;
               } while(!TypeChecker.isRawTypeSafe(subscriptionType, eventType));
            } while(!subscriberInfo.eventQualifiers.isEmpty() && !ReflectionHelper.annotationContainsAll(topic.getTopicQualifiers(), subscriberInfo.eventQualifiers));

            if (subscriberInfo.unqualified == null || topic.getTopicQualifiers().isEmpty()) {
               break;
            }

            if (subscriberInfo.unqualified.value().length != 0) {
               Set topicQualifierClasses = new HashSet();
               Iterator var11 = topic.getTopicQualifiers().iterator();

               while(var11.hasNext()) {
                  Annotation topicQualifier = (Annotation)var11.next();
                  topicQualifierClasses.add(topicQualifier.annotationType());
               }

               boolean found = false;
               Class[] var19 = subscriberInfo.unqualified.value();
               int var13 = var19.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Class verbotenQualifier = var19[var14];
                  if (topicQualifierClasses.contains(verbotenQualifier)) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  break;
               }
            }
         }

         Iterator var16 = subscriberInfo.targets.iterator();

         while(var16.hasNext()) {
            WeakReference targetReference = (WeakReference)var16.next();
            Object target = targetReference.get();
            retVal.add(new FireResults(subscriberInfo.method, subscriberInfo, target));
         }
      }
   }

   public void distributeMessage(Topic topic, Object message) {
      Type eventType = topic.getTopicType();
      LinkedList fireResults = new LinkedList();
      this.rLock.lock();

      Iterator var7;
      try {
         Iterator var5 = this.descriptor2Classes.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry d2cEntry = (Map.Entry)var5.next();
            var7 = ((Set)d2cEntry.getValue()).iterator();

            while(var7.hasNext()) {
               Class clazz = (Class)var7.next();
               fireResults.addAll(this.handleDescriptorToClass((ActiveDescriptor)d2cEntry.getKey(), clazz, eventType, topic));
            }
         }
      } finally {
         this.rLock.unlock();
      }

      HashSet hasDeadReferences = new HashSet();
      MultiException errors = null;
      var7 = fireResults.iterator();

      while(true) {
         while(var7.hasNext()) {
            FireResults fireResult = (FireResults)var7.next();
            if (fireResult.target == null) {
               hasDeadReferences.add(fireResult.subscriberInfo);
            } else {
               try {
                  this.fire(message, fireResult.subscriberMethod, fireResult.subscriberInfo, fireResult.target, this.locator);
               } catch (Throwable var22) {
                  if (errors == null) {
                     errors = new MultiException(var22);
                  } else {
                     errors.addError(var22);
                  }
               }
            }
         }

         if (errors != null) {
            var7 = this.errorHandlers.handleIterator().iterator();

            while(var7.hasNext()) {
               ServiceHandle handle = (ServiceHandle)var7.next();

               try {
                  ((DefaultTopicDistributionErrorService)handle.getService()).subscribersFailed(topic, message, errors);
                  if (handle.getActiveDescriptor().getScope().equals(PerLookup.class.getName())) {
                     handle.destroy();
                  }
               } catch (Throwable var20) {
               }
            }
         }

         if (!hasDeadReferences.isEmpty()) {
            this.wLock.lock();

            try {
               var7 = hasDeadReferences.iterator();

               while(var7.hasNext()) {
                  SubscriberInfo sInfo = (SubscriberInfo)var7.next();
                  Iterator iterator = sInfo.targets.iterator();

                  while(iterator.hasNext()) {
                     WeakReference ref = (WeakReference)iterator.next();
                     if (ref.get() == null) {
                        iterator.remove();
                     }
                  }
               }
            } finally {
               this.wLock.unlock();
            }
         }

         return;
      }
   }

   public Filter getFilter() {
      return SUBSCRIBER_FILTER;
   }

   private void postProduction(InstanceLifecycleEvent lifecycleEvent) {
      ActiveDescriptor descriptor = lifecycleEvent.getActiveDescriptor();
      Object target = lifecycleEvent.getLifecycleObject();
      if (target != null) {
         Class targetClass = target.getClass();
         Set descriptorClazzes = (Set)this.descriptor2Classes.get(descriptor);
         List existingMethods = null;
         if (descriptorClazzes != null) {
            if (descriptorClazzes.contains(targetClass)) {
               existingMethods = (List)this.class2Subscribers.get(new ActivatorClassKey(descriptor, targetClass));
               if (existingMethods != null) {
                  Iterator var7 = existingMethods.iterator();

                  while(var7.hasNext()) {
                     SubscriberInfo info = (SubscriberInfo)var7.next();
                     info.targets.add(new WeakReference(target));
                  }

                  return;
               }
            } else {
               descriptorClazzes.add(targetClass);
            }
         } else {
            Set descriptorClazzes = new HashSet();
            descriptorClazzes.add(targetClass);
            this.descriptor2Classes.put(descriptor, descriptorClazzes);
         }

         List existingMethods = new LinkedList();
         this.class2Subscribers.put(new ActivatorClassKey(descriptor, targetClass), existingMethods);
         Class resolvedClass;
         if (target instanceof AOPProxyCtl) {
            ActiveDescriptor underlyingDescriptor = ((AOPProxyCtl)target).__getUnderlyingDescriptor();
            if (underlyingDescriptor == null) {
               resolvedClass = targetClass;
            } else {
               resolvedClass = underlyingDescriptor.getImplementationClass();
            }
         } else {
            resolvedClass = targetClass;
         }

         Set allMethods = this.reflectionHelper.getAllMethods(resolvedClass);
         Iterator var9 = allMethods.iterator();

         while(true) {
            MethodWrapper methodWrapper;
            Annotation[][] paramAnnotations;
            int foundPosition;
            Method useMethod;
            do {
               do {
                  if (!var9.hasNext()) {
                     return;
                  }

                  methodWrapper = (MethodWrapper)var9.next();
                  paramAnnotations = methodWrapper.getMethod().getParameterAnnotations();
                  foundPosition = -1;

                  for(int position = 0; position < paramAnnotations.length; ++position) {
                     Annotation[] var14 = paramAnnotations[position];
                     int var15 = var14.length;

                     for(int var16 = 0; var16 < var15; ++var16) {
                        Annotation paramAnnotation = var14[var16];
                        if (SubscribeTo.class.equals(paramAnnotation.annotationType())) {
                           if (foundPosition != -1) {
                              throw new IllegalArgumentException("A method " + Pretty.method(methodWrapper.getMethod()) + " on class " + methodWrapper.getMethod().getDeclaringClass().getName() + " has more than one @SubscribeTo annotation on its parameters");
                           }

                           foundPosition = position;
                        }
                     }
                  }
               } while(foundPosition == -1);

               if (resolvedClass == targetClass) {
                  useMethod = methodWrapper.getMethod();
                  break;
               }

               useMethod = this.findMethodOnDifferentClass(targetClass, methodWrapper.getMethod());
            } while(useMethod == null);

            SubscriberInfo si = generateSubscriberInfo(descriptor, methodWrapper.getMethod(), useMethod, foundPosition, paramAnnotations);
            si.targets.add(new WeakReference(target));
            existingMethods.add(si);
         }
      }
   }

   private Method findMethodOnDifferentClass(Class findOnMe, Method method) {
      if ((method.getModifiers() & 2) != 0) {
         return method;
      } else if ((method.getModifiers() & 1) != 0) {
         try {
            return findOnMe.getMethod(method.getName(), method.getParameterTypes());
         } catch (Throwable var7) {
            return null;
         }
      } else {
         Set allMethods = this.reflectionHelper.getAllMethods(findOnMe);
         MethodWrapper findMe = this.reflectionHelper.createMethodWrapper(method);
         Iterator var5 = allMethods.iterator();

         MethodWrapper allMethod;
         do {
            if (!var5.hasNext()) {
               return null;
            }

            allMethod = (MethodWrapper)var5.next();
         } while(!allMethod.equals(findMe));

         return allMethod.getMethod();
      }
   }

   private static SubscriberInfo generateSubscriberInfo(ActiveDescriptor injecteeDescriptor, Method subscriber, Method useSubscriber, int subscribeToPosition, Annotation[][] paramAnnotations) {
      Type[] parameterTypes = subscriber.getGenericParameterTypes();
      Type eventType = parameterTypes[subscribeToPosition];
      Set eventQualifiers = new HashSet();
      Unqualified eventUnqualified = null;
      Annotation[] subscribeToAnnotations = paramAnnotations[subscribeToPosition];
      Annotation[] var10 = subscribeToAnnotations;
      int lcv = subscribeToAnnotations.length;

      for(int var12 = 0; var12 < lcv; ++var12) {
         Annotation possibleQualifier = var10[var12];
         if (ReflectionHelper.isAnnotationAQualifier(possibleQualifier)) {
            eventQualifiers.add(possibleQualifier);
         }

         if (Unqualified.class.equals(possibleQualifier.annotationType())) {
            eventUnqualified = (Unqualified)possibleQualifier;
         }
      }

      InjecteeImpl[] injectees = new InjecteeImpl[parameterTypes.length];

      for(lcv = 0; lcv < injectees.length; ++lcv) {
         if (lcv == subscribeToPosition) {
            injectees[lcv] = null;
         } else {
            InjecteeImpl ii = new InjecteeImpl();
            ii.setRequiredType(parameterTypes[lcv]);
            Set parameterQualifiers = new HashSet();
            Annotation[] parameterAnnotations = paramAnnotations[lcv];
            boolean isOptional = false;
            boolean isSelf = false;
            Unqualified unqualified = null;
            Annotation[] var18 = parameterAnnotations;
            int var19 = parameterAnnotations.length;

            for(int var20 = 0; var20 < var19; ++var20) {
               Annotation possibleQualifier = var18[var20];
               if (ReflectionHelper.isAnnotationAQualifier(possibleQualifier)) {
                  parameterQualifiers.add(possibleQualifier);
               }

               if (Optional.class.equals(possibleQualifier.annotationType())) {
                  isOptional = true;
               }

               if (Self.class.equals(possibleQualifier.annotationType())) {
                  isSelf = true;
               }

               if (Unqualified.class.equals(possibleQualifier.annotationType())) {
                  unqualified = (Unqualified)possibleQualifier;
               }
            }

            ii.setRequiredQualifiers(parameterQualifiers);
            ii.setPosition(lcv);
            ii.setParent(useSubscriber);
            ii.setOptional(isOptional);
            ii.setSelf(isSelf);
            ii.setUnqualified(unqualified);
            ii.setInjecteeDescriptor(injecteeDescriptor);
            injectees[lcv] = ii;
         }
      }

      return new SubscriberInfo(subscriber, eventType, eventQualifiers, eventUnqualified, injectees);
   }

   private void preDestruction(InstanceLifecycleEvent lifecycleEvent) {
      ActiveDescriptor descriptor = lifecycleEvent.getActiveDescriptor();
      Object target = lifecycleEvent.getLifecycleObject();
      if (target != null) {
         Set classes = (Set)this.descriptor2Classes.get(descriptor);
         Iterator var5 = classes.iterator();

         while(var5.hasNext()) {
            Class clazz = (Class)var5.next();
            List subscribers = (List)this.class2Subscribers.get(new ActivatorClassKey(descriptor, clazz));
            Iterator var8 = subscribers.iterator();

            while(var8.hasNext()) {
               SubscriberInfo subscriberInfo = (SubscriberInfo)var8.next();
               Iterator targetIterator = subscriberInfo.targets.iterator();

               while(targetIterator.hasNext()) {
                  WeakReference ref = (WeakReference)targetIterator.next();
                  Object subscriberTarget = ref.get();
                  if (subscriberTarget == null) {
                     targetIterator.remove();
                  } else if (subscriberTarget == target) {
                     targetIterator.remove();
                  }
               }
            }
         }

      }
   }

   public void lifecycleEvent(InstanceLifecycleEvent lifecycleEvent) {
      switch (lifecycleEvent.getEventType()) {
         case POST_PRODUCTION:
            this.wLock.lock();

            try {
               this.postProduction(lifecycleEvent);
               break;
            } finally {
               this.wLock.unlock();
            }
         case PRE_DESTRUCTION:
            this.wLock.lock();

            try {
               this.preDestruction(lifecycleEvent);
               break;
            } finally {
               this.wLock.unlock();
            }
         default:
            return;
      }

   }

   public void configurationChanged() {
      List allDescriptors = this.locator.getDescriptors(this.getFilter());
      this.wLock.lock();

      try {
         HashSet removeMe = new HashSet(this.descriptor2Classes.keySet());
         removeMe.removeAll(allDescriptors);
         Iterator var3 = removeMe.iterator();

         while(true) {
            ActiveDescriptor parent;
            Set clazzes;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               parent = (ActiveDescriptor)var3.next();
               clazzes = (Set)this.descriptor2Classes.remove(parent);
            } while(clazzes == null);

            Iterator var6 = clazzes.iterator();

            while(var6.hasNext()) {
               Class clazz = (Class)var6.next();
               this.class2Subscribers.remove(new ActivatorClassKey(parent, clazz));
            }
         }
      } finally {
         this.wLock.unlock();
      }
   }

   private static class ActivatorClassKey {
      private final ActiveDescriptor descriptor;
      private final Class clazz;
      private final int hashCode;

      private ActivatorClassKey(ActiveDescriptor descriptor, Class clazz) {
         this.descriptor = descriptor;
         this.clazz = clazz;
         this.hashCode = descriptor.hashCode() ^ clazz.hashCode();
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else if (!(o instanceof ActivatorClassKey)) {
            return false;
         } else {
            ActivatorClassKey other = (ActivatorClassKey)o;
            return this.descriptor.equals(other.descriptor) && this.clazz.equals(other.clazz);
         }
      }

      // $FF: synthetic method
      ActivatorClassKey(ActiveDescriptor x0, Class x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class FireResults {
      private final Method subscriberMethod;
      private final SubscriberInfo subscriberInfo;
      private final Object target;

      private FireResults(Method subscriberMethod, SubscriberInfo subscriberInfo, Object target) {
         this.subscriberMethod = subscriberMethod;
         this.subscriberInfo = subscriberInfo;
         this.target = target;
      }

      // $FF: synthetic method
      FireResults(Method x0, SubscriberInfo x1, Object x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class SubscriberInfo {
      private final Method method;
      private final LinkedList targets;
      private final Type eventType;
      private final Set eventQualifiers;
      private final Unqualified unqualified;
      private final InjecteeImpl[] otherInjectees;

      private SubscriberInfo(Method method, Type eventType, Set eventQualifiers, Unqualified unqualified, InjecteeImpl[] otherInjectees) {
         this.targets = new LinkedList();
         this.method = method;
         this.eventType = eventType;
         this.eventQualifiers = eventQualifiers;
         this.unqualified = unqualified;
         this.otherInjectees = otherInjectees;
      }

      // $FF: synthetic method
      SubscriberInfo(Method x0, Type x1, Set x2, Unqualified x3, InjecteeImpl[] x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
