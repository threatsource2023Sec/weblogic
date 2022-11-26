package org.jboss.weld.resolution;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InterceptionType;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class InterceptorResolvableBuilder extends ResolvableBuilder {
   private InterceptionType interceptionType;

   public InterceptorResolvableBuilder(BeanManagerImpl manager) {
      super(manager);
   }

   public InterceptorResolvableBuilder(Type type, BeanManagerImpl manager) {
      super(type, manager);
   }

   protected void checkQualifier(Annotation qualifier, QualifierInstance qualifierInstance, Class annotationType) {
      if (!this.getMetaAnnotationStore().getInterceptorBindingModel(annotationType).isValid()) {
         throw BeanManagerLogger.LOG.interceptorResolutionWithNonbindingType(qualifier);
      } else if (this.qualifierInstances.contains(qualifierInstance)) {
         throw BeanManagerLogger.LOG.duplicateInterceptorBinding(qualifierInstance);
      }
   }

   public InterceptorResolvableBuilder setInterceptionType(InterceptionType interceptionType) {
      this.interceptionType = interceptionType;
      return this;
   }

   public InterceptorResolvableBuilder addQualifier(Annotation qualifier) {
      super.addQualifier(qualifier);
      return this;
   }

   public InterceptorResolvableBuilder addQualifiers(Annotation[] qualifiers) {
      super.addQualifiers(qualifiers);
      return this;
   }

   public InterceptorResolvableBuilder addQualifiers(Collection qualifiers) {
      super.addQualifiers(qualifiers);
      return this;
   }

   public InterceptorResolvableBuilder addType(Type type) {
      super.addType(type);
      return this;
   }

   public InterceptorResolvableBuilder addTypes(Set types) {
      super.addTypes(types);
      return this;
   }

   public InterceptorResolvableBuilder setDeclaringBean(Bean declaringBean) {
      super.setDeclaringBean(declaringBean);
      return this;
   }

   public InterceptorResolvable create() {
      if (this.qualifierInstances.isEmpty()) {
         throw BeanManagerLogger.LOG.interceptorBindingsEmpty();
      } else {
         return new InterceptorResolvableImpl(this.rawType, this.types, this.declaringBean, this.interceptionType, this.qualifierInstances);
      }
   }

   private static class InterceptorResolvableImpl extends ResolvableBuilder.ResolvableImpl implements InterceptorResolvable {
      private final InterceptionType interceptionType;

      private InterceptorResolvableImpl(Class rawType, Set typeClosure, Bean declaringBean, InterceptionType interceptionType, Set instances) {
         super(rawType, typeClosure, declaringBean, instances, false);
         this.interceptionType = interceptionType;
      }

      public InterceptionType getInterceptionType() {
         return this.interceptionType;
      }

      public int hashCode() {
         return 31 * super.hashCode() + this.getInterceptionType().hashCode();
      }

      public boolean equals(Object o) {
         if (!(o instanceof Resolvable)) {
            return false;
         } else {
            Resolvable r = (Resolvable)o;
            return super.equals(r) && r instanceof InterceptorResolvable && this.getInterceptionType().equals(((InterceptorResolvable)r).getInterceptionType());
         }
      }

      // $FF: synthetic method
      InterceptorResolvableImpl(Class x0, Set x1, Bean x2, InterceptionType x3, Set x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
