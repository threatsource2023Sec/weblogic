package org.jboss.weld.event;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessSyntheticAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import org.jboss.weld.Container;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.builtin.ExtensionBean;
import org.jboss.weld.bootstrap.events.NotificationListener;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.MethodInjectionPoint;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class ExtensionObserverMethodImpl extends ObserverMethodImpl implements ContainerLifecycleEventObserverMethod {
   private final Container containerLifecycleEventDeliveryLock;
   private final Set requiredTypeAnnotations;
   private volatile Set requiredScopeTypeAnnotations;

   protected ExtensionObserverMethodImpl(EnhancedAnnotatedMethod observer, RIBean declaringBean, BeanManagerImpl manager, boolean isAsync) {
      super(observer, declaringBean, manager, isAsync);
      this.containerLifecycleEventDeliveryLock = Container.instance(manager);
      this.requiredTypeAnnotations = this.initRequiredTypeAnnotations(observer);
   }

   protected Set initRequiredTypeAnnotations(EnhancedAnnotatedMethod observer) {
      EnhancedAnnotatedParameter eventParameter = this.getEventParameter(observer);
      WithAnnotations annotation = (WithAnnotations)eventParameter.getAnnotation(WithAnnotations.class);
      return annotation != null ? ImmutableSet.of(annotation.value()) : Collections.emptySet();
   }

   protected void checkRequiredTypeAnnotations(EnhancedAnnotatedParameter eventParameter) {
      Class rawObserverType = Reflections.getRawType(this.getObservedType());
      boolean isProcessAnnotatedType = rawObserverType.equals(ProcessAnnotatedType.class) || rawObserverType.equals(ProcessSyntheticAnnotatedType.class);
      if (!isProcessAnnotatedType && !this.requiredTypeAnnotations.isEmpty()) {
         throw EventLogger.LOG.invalidWithAnnotations(this, Formats.formatAsStackTraceElement(eventParameter.getDeclaringEnhancedCallable().getJavaMember()));
      } else {
         if (isProcessAnnotatedType && this.requiredTypeAnnotations.isEmpty()) {
            Type[] typeArguments = eventParameter.getActualTypeArguments();
            if (typeArguments.length == 0 || Reflections.isUnboundedWildcard(typeArguments[0]) || Reflections.isUnboundedTypeVariable(typeArguments[0])) {
               EventLogger.LOG.unrestrictedProcessAnnotatedTypes(this);
            }
         }

      }
   }

   protected MethodInjectionPoint initMethodInjectionPoint(EnhancedAnnotatedMethod observer, RIBean declaringBean, BeanManagerImpl manager) {
      return InjectionPointFactory.silentInstance().createMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType.OBSERVER, observer, declaringBean, declaringBean.getBeanClass(), SPECIAL_PARAM_MARKERS, manager);
   }

   protected void preNotify(Object event, Object receiver) {
      if (event instanceof NotificationListener) {
         ((NotificationListener)NotificationListener.class.cast(event)).preNotify((Extension)receiver);
      }

   }

   protected void postNotify(Object event, Object receiver) {
      if (event instanceof NotificationListener) {
         ((NotificationListener)NotificationListener.class.cast(event)).postNotify((Extension)receiver);
      }

   }

   protected Object getReceiver(CreationalContext ctx) {
      return this.getDeclaringBean().create((CreationalContext)null);
   }

   protected void sendEvent(Object event, Object receiver, CreationalContext creationalContext) {
      synchronized(this.containerLifecycleEventDeliveryLock) {
         super.sendEvent(event, receiver, creationalContext);
      }
   }

   protected String createTypeId(RIBean declaringBean) {
      if (declaringBean instanceof ExtensionBean) {
         ExtensionBean extensionBean = (ExtensionBean)declaringBean;
         return ((AnnotatedTypeIdentifier)extensionBean.getAnnotatedType().getIdentifier()).asString();
      } else {
         return super.createTypeId(declaringBean);
      }
   }

   public Collection getRequiredAnnotations() {
      return this.requiredTypeAnnotations;
   }

   public Collection getRequiredScopeAnnotations() {
      if (this.requiredScopeTypeAnnotations == null) {
         ImmutableSet.Builder builder = ImmutableSet.builder();
         Iterator var2 = this.requiredTypeAnnotations.iterator();

         while(var2.hasNext()) {
            Class annotation = (Class)var2.next();
            if (this.beanManager.isScope(annotation)) {
               builder.add(annotation);
            }
         }

         this.requiredScopeTypeAnnotations = builder.build();
      }

      return this.requiredScopeTypeAnnotations;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ExtensionObserverMethodImpl that = (ExtensionObserverMethodImpl)obj;
         return super.equals(that);
      }
   }

   public int hashCode() {
      return super.hashCode();
   }
}
