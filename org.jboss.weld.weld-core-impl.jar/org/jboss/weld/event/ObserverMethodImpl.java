package org.jboss.weld.event;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Priority;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Inject;
import javax.inject.Qualifier;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.ContextualInstance;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.MethodInjectionPoint;
import org.jboss.weld.injection.MethodInvocationStrategy;
import org.jboss.weld.injection.ParameterInjectionPoint;
import org.jboss.weld.injection.attributes.SpecialParameterInjectionPoint;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.HierarchyDiscovery;

public class ObserverMethodImpl implements ObserverMethod, EventMetadataAwareObserverMethod {
   public static final String ID_PREFIX = ObserverMethodImpl.class.getPackage().getName();
   public static final String ID_SEPARATOR = "-";
   protected static final Set SPECIAL_PARAM_MARKERS = ImmutableSet.of(Observes.class, ObservesAsync.class);
   private static final Type EVENT_METADATA_INSTANCE_TYPE = (new TypeLiteral() {
   }).getType();
   private final Set bindings;
   private final Type eventType;
   protected final BeanManagerImpl beanManager;
   private final Reception reception;
   protected final RIBean declaringBean;
   protected final MethodInjectionPoint observerMethod;
   protected TransactionPhase transactionPhase;
   private final String id;
   private final Set injectionPoints;
   private final int priority;
   private final boolean isStatic;
   private final boolean eventMetadataRequired;
   private final MethodInvocationStrategy notificationStrategy;
   private final boolean isAsync;

   protected ObserverMethodImpl(EnhancedAnnotatedMethod observer, RIBean declaringBean, BeanManagerImpl manager, boolean isAsync) {
      this.beanManager = manager;
      this.declaringBean = declaringBean;
      this.observerMethod = this.initMethodInjectionPoint(observer, declaringBean, manager);
      this.id = this.createId(observer, declaringBean);
      this.isAsync = isAsync;
      EnhancedAnnotatedParameter eventParameter = this.getEventParameter(observer);
      if (isAsync) {
         this.reception = ((ObservesAsync)eventParameter.getAnnotation(ObservesAsync.class)).notifyObserver();
         this.transactionPhase = TransactionPhase.IN_PROGRESS;
      } else {
         this.reception = ((Observes)eventParameter.getAnnotation(Observes.class)).notifyObserver();
         this.transactionPhase = ObserverFactory.getTransactionalPhase(observer);
      }

      this.bindings = ((SharedObjectCache)manager.getServices().get(SharedObjectCache.class)).getSharedSet(eventParameter.getMetaAnnotations(Qualifier.class));
      this.eventType = (new HierarchyDiscovery(declaringBean.getBeanClass())).resolveType(eventParameter.getBaseType());
      ImmutableSet.Builder injectionPoints = ImmutableSet.builder();
      Iterator var7 = this.observerMethod.getParameterInjectionPoints().iterator();

      while(var7.hasNext()) {
         ParameterInjectionPoint injectionPoint = (ParameterInjectionPoint)var7.next();
         if (!(injectionPoint instanceof SpecialParameterInjectionPoint)) {
            injectionPoints.add(injectionPoint);
         }
      }

      this.injectionPoints = injectionPoints.build();
      Priority priority = (Priority)eventParameter.getAnnotation(Priority.class);
      if (priority == null) {
         this.priority = 2500;
      } else {
         this.priority = priority.value();
      }

      this.isStatic = observer.isStatic();
      this.eventMetadataRequired = initMetadataRequired(this.injectionPoints);
      this.notificationStrategy = MethodInvocationStrategy.forObserver(this.observerMethod, this.beanManager);
   }

   protected EnhancedAnnotatedParameter getEventParameter(EnhancedAnnotatedMethod observer) {
      return this.isAsync ? (EnhancedAnnotatedParameter)observer.getEnhancedParameters(ObservesAsync.class).get(0) : (EnhancedAnnotatedParameter)observer.getEnhancedParameters(Observes.class).get(0);
   }

   private static boolean initMetadataRequired(Set injectionPoints) {
      Iterator var1 = injectionPoints.iterator();

      Type type;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         WeldInjectionPointAttributes ip = (WeldInjectionPointAttributes)var1.next();
         type = ip.getType();
      } while(!EventMetadata.class.equals(type) && !EVENT_METADATA_INSTANCE_TYPE.equals(type));

      return true;
   }

   protected String createId(EnhancedAnnotatedMethod observer, RIBean declaringBean) {
      return ID_PREFIX + "-" + ObserverMethod.class.getSimpleName() + "-" + this.createTypeId(declaringBean) + "." + observer.getSignature();
   }

   protected String createTypeId(RIBean declaringBean) {
      String typeId = null;
      if (declaringBean instanceof AbstractClassBean) {
         AbstractClassBean classBean = (AbstractClassBean)declaringBean;
         typeId = ((AnnotatedTypeIdentifier)classBean.getAnnotated().getIdentifier()).asString();
      } else {
         typeId = declaringBean.getBeanClass().getName();
      }

      return typeId;
   }

   protected MethodInjectionPoint initMethodInjectionPoint(EnhancedAnnotatedMethod observer, RIBean declaringBean, BeanManagerImpl manager) {
      return InjectionPointFactory.instance().createMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType.OBSERVER, observer, declaringBean, declaringBean.getBeanClass(), SPECIAL_PARAM_MARKERS, manager);
   }

   public Set getInjectionPoints() {
      return this.injectionPoints;
   }

   private void checkObserverMethod(EnhancedAnnotatedMethod annotated) {
      List eventObjects = annotated.getEnhancedParameters(Observes.class);
      eventObjects.addAll(annotated.getEnhancedParameters(ObservesAsync.class));
      if (this.reception.equals(Reception.IF_EXISTS) && this.declaringBean.getScope().equals(Dependent.class)) {
         throw EventLogger.LOG.invalidScopedConditionalObserver(this, Formats.formatAsStackTraceElement((Member)annotated.getJavaMember()));
      } else if (eventObjects.size() > 1) {
         throw EventLogger.LOG.multipleEventParameters(this, Formats.formatAsStackTraceElement((Member)annotated.getJavaMember()));
      } else {
         EnhancedAnnotatedParameter eventParameter = (EnhancedAnnotatedParameter)eventObjects.iterator().next();
         this.checkRequiredTypeAnnotations(eventParameter);
         List disposeParams = annotated.getEnhancedParameters(Disposes.class);
         if (disposeParams.size() > 0) {
            throw EventLogger.LOG.invalidDisposesParameter(this, Formats.formatAsStackTraceElement((Member)annotated.getJavaMember()));
         } else if (this.observerMethod.getAnnotated().isAnnotationPresent(Produces.class)) {
            throw EventLogger.LOG.invalidProducer(this, Formats.formatAsStackTraceElement((Member)annotated.getJavaMember()));
         } else if (this.observerMethod.getAnnotated().isAnnotationPresent(Inject.class)) {
            throw EventLogger.LOG.invalidInitializer(this, Formats.formatAsStackTraceElement((Member)annotated.getJavaMember()));
         } else {
            boolean containerLifecycleObserverMethod = Observers.isContainerLifecycleObserverMethod(this);
            Iterator var6 = annotated.getEnhancedParameters().iterator();

            EnhancedAnnotatedParameter parameter;
            do {
               if (!var6.hasNext()) {
                  return;
               }

               parameter = (EnhancedAnnotatedParameter)var6.next();
            } while(!containerLifecycleObserverMethod || parameter.isAnnotationPresent(Observes.class) || parameter.isAnnotationPresent(ObservesAsync.class) || BeanManager.class.equals(parameter.getBaseType()));

            throw EventLogger.LOG.invalidInjectionPoint(this, Formats.formatAsStackTraceElement((Member)annotated.getJavaMember()));
         }
      }
   }

   protected void checkRequiredTypeAnnotations(EnhancedAnnotatedParameter eventParameter) {
      if (eventParameter.isAnnotationPresent(WithAnnotations.class)) {
         throw EventLogger.LOG.invalidWithAnnotations(this, Formats.formatAsStackTraceElement(eventParameter.getDeclaringEnhancedCallable().getJavaMember()));
      }
   }

   public Class getBeanClass() {
      return this.declaringBean.getType();
   }

   public RIBean getDeclaringBean() {
      return this.declaringBean;
   }

   public Reception getReception() {
      return this.reception;
   }

   public Set getObservedQualifiers() {
      return this.bindings;
   }

   public Type getObservedType() {
      return this.eventType;
   }

   public TransactionPhase getTransactionPhase() {
      return this.transactionPhase;
   }

   public MethodInjectionPoint getMethod() {
      return this.observerMethod;
   }

   public void initialize(EnhancedAnnotatedMethod annotated) {
      this.checkObserverMethod(annotated);
   }

   public void notify(Object event) {
      this.sendEvent(event);
   }

   protected void sendEvent(Object event) {
      if (this.isStatic) {
         this.sendEvent(event, (Object)null, (CreationalContext)null);
      } else {
         CreationalContext creationalContext = null;

         try {
            Object receiver = this.getReceiverIfExists((CreationalContext)null);
            if (receiver == null && this.reception != Reception.IF_EXISTS) {
               creationalContext = this.beanManager.createCreationalContext(this.declaringBean);
               receiver = this.getReceiverIfExists(creationalContext);
            }

            if (receiver != null) {
               this.sendEvent(event, receiver, creationalContext);
            }
         } finally {
            if (creationalContext != null) {
               creationalContext.release();
            }

         }
      }

   }

   protected void sendEvent(Object event, Object receiver, CreationalContext creationalContext) {
      try {
         this.preNotify(event, receiver);
         this.notificationStrategy.invoke(receiver, this.observerMethod, event, this.beanManager, creationalContext);
      } finally {
         this.postNotify(event, receiver);
      }

   }

   protected void preNotify(Object event, Object receiver) {
   }

   protected void postNotify(Object event, Object receiver) {
   }

   private Object getReceiverIfExists(CreationalContext creationalContext) {
      try {
         return this.getReceiver(creationalContext);
      } catch (ContextNotActiveException var3) {
         return null;
      }
   }

   protected Object getReceiver(CreationalContext creationalContext) {
      if (creationalContext != null) {
         if (creationalContext instanceof CreationalContextImpl) {
            creationalContext = ((CreationalContextImpl)creationalContext).getCreationalContext(this.declaringBean);
         }

         return ContextualInstance.get((RIBean)this.declaringBean, this.beanManager, (CreationalContext)creationalContext);
      } else {
         return ContextualInstance.getIfExists(this.declaringBean, this.beanManager);
      }
   }

   public String toString() {
      return this.observerMethod.toString();
   }

   public String getId() {
      return this.id;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ObserverMethodImpl that = (ObserverMethodImpl)obj;
         return this.getId().equals(that.getId());
      }
   }

   public int hashCode() {
      return this.getId().hashCode();
   }

   public int getPriority() {
      return this.priority;
   }

   public boolean isAsync() {
      return this.isAsync;
   }

   public boolean isEventMetadataRequired() {
      return this.eventMetadataRequired;
   }
}
