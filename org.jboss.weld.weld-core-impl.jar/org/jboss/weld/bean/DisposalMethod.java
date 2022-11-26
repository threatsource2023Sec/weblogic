package org.jboss.weld.bean;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.enterprise.inject.Default.Literal;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.Interceptor;
import javax.inject.Inject;
import javax.inject.Qualifier;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.bootstrap.Validator;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.MethodInjectionPoint;
import org.jboss.weld.injection.MethodInvocationStrategy;
import org.jboss.weld.injection.ParameterInjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class DisposalMethod {
   private static final Set SPECIAL_PARAM_MARKERS = Collections.singleton(Disposes.class);
   private static final String DISPOSER_ANNOTATION = "@Disposes";
   private final BeanManagerImpl beanManager;
   private final AbstractClassBean declaringBean;
   private final MethodInjectionPoint disposalMethodInjectionPoint;
   private final AnnotatedParameter disposesParameter;
   private final Set requiredQualifiers;
   private final MethodInvocationStrategy invocationStrategy;

   public static DisposalMethod of(BeanManagerImpl manager, EnhancedAnnotatedMethod method, AbstractClassBean declaringBean) {
      return new DisposalMethod(manager, method, declaringBean);
   }

   protected DisposalMethod(BeanManagerImpl beanManager, EnhancedAnnotatedMethod enhancedAnnotatedMethod, AbstractClassBean declaringBean) {
      this.disposalMethodInjectionPoint = InjectionPointFactory.instance().createMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType.DISPOSER, enhancedAnnotatedMethod, declaringBean, declaringBean.getBeanClass(), SPECIAL_PARAM_MARKERS, beanManager);
      this.beanManager = beanManager;
      this.declaringBean = declaringBean;
      EnhancedAnnotatedParameter enhancedDisposesParameter = this.getEnhancedDisposesParameter(enhancedAnnotatedMethod);
      this.disposesParameter = enhancedDisposesParameter.slim();
      this.requiredQualifiers = this.getRequiredQualifiers(enhancedDisposesParameter);
      this.checkDisposalMethod(enhancedAnnotatedMethod, declaringBean);
      this.invocationStrategy = MethodInvocationStrategy.forDisposer(this.disposalMethodInjectionPoint, beanManager);
   }

   private EnhancedAnnotatedParameter getEnhancedDisposesParameter(EnhancedAnnotatedMethod enhancedAnnotatedMethod) {
      return (EnhancedAnnotatedParameter)enhancedAnnotatedMethod.getEnhancedParameters(Disposes.class).get(0);
   }

   public AnnotatedParameter getDisposesParameter() {
      return this.disposesParameter;
   }

   public AnnotatedMethod getAnnotated() {
      return this.disposalMethodInjectionPoint.getAnnotated();
   }

   public void invokeDisposeMethod(Object receiver, Object instance, CreationalContext creationalContext) {
      this.invocationStrategy.invoke(receiver, this.disposalMethodInjectionPoint, instance, this.beanManager, creationalContext);
   }

   private void checkDisposalMethod(EnhancedAnnotatedMethod enhancedAnnotatedMethod, AbstractClassBean declaringBean) {
      if (enhancedAnnotatedMethod.getEnhancedParameters(Disposes.class).size() > 1) {
         throw BeanLogger.LOG.multipleDisposeParams(this.disposalMethodInjectionPoint, Formats.formatAsStackTraceElement((Member)enhancedAnnotatedMethod.getJavaMember()));
      } else if (enhancedAnnotatedMethod.getEnhancedParameters(Observes.class).size() > 0) {
         throw BeanLogger.LOG.inconsistentAnnotationsOnMethod("@Observes", "@Disposes", this.disposalMethodInjectionPoint, Formats.formatAsStackTraceElement((Member)enhancedAnnotatedMethod.getJavaMember()));
      } else if (enhancedAnnotatedMethod.getAnnotation(Inject.class) != null) {
         throw BeanLogger.LOG.inconsistentAnnotationsOnMethod("@Inject", "@Disposes", this.disposalMethodInjectionPoint, Formats.formatAsStackTraceElement((Member)enhancedAnnotatedMethod.getJavaMember()));
      } else if (enhancedAnnotatedMethod.getAnnotation(Produces.class) != null) {
         throw BeanLogger.LOG.inconsistentAnnotationsOnMethod("@Produces", "@Disposes", this.disposalMethodInjectionPoint, Formats.formatAsStackTraceElement((Member)enhancedAnnotatedMethod.getJavaMember()));
      } else if (enhancedAnnotatedMethod.getAnnotation(Specializes.class) != null) {
         throw BeanLogger.LOG.inconsistentAnnotationsOnMethod("@Specialized", "@Disposes", this.disposalMethodInjectionPoint, Formats.formatAsStackTraceElement((Member)enhancedAnnotatedMethod.getJavaMember()));
      } else {
         if (declaringBean instanceof SessionBean) {
            SessionBean sessionBean = (SessionBean)declaringBean;
            Set localBusinessMethodSignatures = sessionBean.getLocalBusinessMethodSignatures();
            Set remoteBusinessMethodSignatures = sessionBean.getRemoteBusinessMethodSignatures();
            if (!localBusinessMethodSignatures.contains(enhancedAnnotatedMethod.getSignature()) || remoteBusinessMethodSignatures.contains(enhancedAnnotatedMethod.getSignature())) {
               throw BeanLogger.LOG.methodNotBusinessMethod("Disposer", enhancedAnnotatedMethod, declaringBean, Formats.formatAsStackTraceElement((Member)enhancedAnnotatedMethod.getJavaMember()));
            }
         }

         Iterator var6 = this.disposalMethodInjectionPoint.getParameterInjectionPoints().iterator();

         while(true) {
            ParameterInjectionPoint ip;
            Class rawType;
            do {
               if (!var6.hasNext()) {
                  return;
               }

               ip = (ParameterInjectionPoint)var6.next();
               rawType = Reflections.getRawType(ip.getType());
            } while(!Bean.class.equals(rawType) && !Interceptor.class.equals(rawType) && !Decorator.class.equals(rawType));

            Validator.checkBeanMetadataInjectionPoint(this, ip, this.getDisposesParameter().getBaseType());
         }
      }
   }

   public Type getGenericType() {
      return this.getDisposesParameter().getBaseType();
   }

   public Set getRequiredQualifiers() {
      return this.requiredQualifiers;
   }

   public AbstractClassBean getDeclaringBean() {
      return this.declaringBean;
   }

   public String toString() {
      return "Disposer method [" + this.getDisposesParameter().getDeclaringCallable() + "]";
   }

   public Set getInjectionPoints() {
      return this.disposalMethodInjectionPoint.getInjectionPoints();
   }

   private Set getRequiredQualifiers(EnhancedAnnotatedParameter enhancedDisposedParameter) {
      Set disposedParameterQualifiers = enhancedDisposedParameter.getMetaAnnotations(Qualifier.class);
      if (disposedParameterQualifiers.isEmpty()) {
         disposedParameterQualifiers = Collections.singleton(Literal.INSTANCE);
      }

      return ((MetaAnnotationStore)this.beanManager.getServices().get(MetaAnnotationStore.class)).getQualifierInstances(disposedParameterQualifiers);
   }
}
