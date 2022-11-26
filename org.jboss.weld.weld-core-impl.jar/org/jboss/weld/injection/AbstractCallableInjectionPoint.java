package org.jboss.weld.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.TransientReference;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedCallable;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.InjectionPoints;
import org.jboss.weld.util.collections.ListToSet;
import org.jboss.weld.util.reflection.Reflections;

abstract class AbstractCallableInjectionPoint implements WeldInjectionPointAttributes {
   private final Bean declaringBean;
   private final List parameters;
   private final Set injectionPoints;
   protected final boolean hasTransientReferenceParameter;

   protected AbstractCallableInjectionPoint(EnhancedAnnotatedCallable callable, Bean declaringBean, Class declaringComponentClass, boolean observerOrDisposer, InjectionPointFactory factory, BeanManagerImpl manager) {
      this.declaringBean = declaringBean;
      this.parameters = factory.getParameterInjectionPoints(callable, declaringBean, declaringComponentClass, manager, observerOrDisposer);
      if (observerOrDisposer) {
         this.injectionPoints = InjectionPoints.filterOutSpecialParameterInjectionPoints(this.parameters);
      } else {
         this.injectionPoints = new ListToSet() {
            protected List delegate() {
               return (List)Reflections.cast(AbstractCallableInjectionPoint.this.getParameterInjectionPoints());
            }
         };
      }

      this.hasTransientReferenceParameter = initHasTransientReference(callable.getEnhancedParameters());
   }

   private static boolean initHasTransientReference(List parameters) {
      Iterator var1 = parameters.iterator();

      EnhancedAnnotatedParameter parameter;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         parameter = (EnhancedAnnotatedParameter)var1.next();
      } while(!parameter.isAnnotationPresent(TransientReference.class));

      return true;
   }

   public Type getType() {
      return this.getAnnotated().getBaseType();
   }

   public Set getQualifiers() {
      throw new UnsupportedOperationException();
   }

   public Bean getBean() {
      return this.declaringBean;
   }

   public boolean isDelegate() {
      return false;
   }

   public boolean isTransient() {
      return false;
   }

   public Annotation getQualifier(Class annotationType) {
      Annotation qualifier = this.getAnnotated().getAnnotation(annotationType);
      return this.getQualifiers().contains(qualifier) ? qualifier : null;
   }

   public Member getMember() {
      return this.getAnnotated().getJavaMember();
   }

   public abstract AnnotatedCallable getAnnotated();

   public List getParameterInjectionPoints() {
      return this.parameters;
   }

   public Set getInjectionPoints() {
      return this.injectionPoints;
   }

   public boolean equals(Object obj) {
      if (obj instanceof AbstractCallableInjectionPoint) {
         AbstractCallableInjectionPoint ip = (AbstractCallableInjectionPoint)obj;
         if (AnnotatedTypes.compareAnnotatedCallable(this.getAnnotated(), ip.getAnnotated())) {
            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      return this.getAnnotated().hashCode();
   }

   public String toString() {
      return this.getAnnotated().toString();
   }
}
