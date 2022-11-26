package org.jboss.weld.injection.attributes;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Set;
import javax.decorator.Delegate;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.serialization.BeanHolder;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractInferringInjectionPointAttributes implements WeldInjectionPointAttributes, Serializable {
   private static final long serialVersionUID = 7820718127728549436L;
   private final BeanHolder bean;
   private final Set qualifiers;
   private final TypeAttribute typeAttribute;
   private final boolean delegate;

   public AbstractInferringInjectionPointAttributes(EnhancedAnnotated annotatedElement, String contextId, Bean bean, Set qualifiers, Class declaringComponentClass) {
      this.bean = BeanHolder.of(contextId, bean);
      this.qualifiers = qualifiers;
      if (bean == null) {
         this.typeAttribute = new NonContextualInjectionPointTypeAttribute(declaringComponentClass);
      } else {
         this.typeAttribute = new BeanInjectionPointTypeAttribute();
      }

      this.delegate = annotatedElement.isAnnotationPresent(Delegate.class);
   }

   public Type getType() {
      return this.typeAttribute.getType();
   }

   public Set getQualifiers() {
      return this.qualifiers;
   }

   public Bean getBean() {
      return (Bean)this.bean.get();
   }

   public boolean isDelegate() {
      return this.delegate;
   }

   public boolean isTransient() {
      return Reflections.isTransient(this.getMember());
   }

   public abstract Member getMember();

   public String toString() {
      return this.getAnnotated().toString();
   }

   public Annotation getQualifier(Class annotationType) {
      Annotation qualifier = this.getAnnotated().getAnnotation(annotationType);
      return this.getQualifiers().contains(qualifier) ? qualifier : null;
   }

   @SuppressFBWarnings(
      value = {"SE_INNER_CLASS"},
      justification = "The outer class is always serialized along this inner class."
   )
   private class NonContextualInjectionPointTypeAttribute extends TypeAttribute {
      private static final long serialVersionUID = 1870361474843082321L;
      private Class componentClass;

      public NonContextualInjectionPointTypeAttribute(Class componentClass) {
         super(null);
         this.componentClass = componentClass;
      }

      protected Type resolveType() {
         return (new HierarchyDiscovery(this.componentClass)).resolveType(AbstractInferringInjectionPointAttributes.this.getAnnotated().getBaseType());
      }
   }

   @SuppressFBWarnings(
      value = {"SE_INNER_CLASS"},
      justification = "The outer class is always serialized along this inner class."
   )
   private class BeanInjectionPointTypeAttribute extends TypeAttribute {
      private static final long serialVersionUID = 6927120066961769765L;

      private BeanInjectionPointTypeAttribute() {
         super(null);
      }

      protected Type resolveType() {
         return (new HierarchyDiscovery(AbstractInferringInjectionPointAttributes.this.getBean().getBeanClass())).resolveType(AbstractInferringInjectionPointAttributes.this.getAnnotated().getBaseType());
      }

      // $FF: synthetic method
      BeanInjectionPointTypeAttribute(Object x1) {
         this();
      }
   }

   private abstract class TypeAttribute implements Serializable {
      private static final long serialVersionUID = -4558590047874880757L;
      private transient volatile Type type;

      private TypeAttribute() {
      }

      public Type getType() {
         if (this.type == null) {
            this.type = this.resolveType();
         }

         return this.type;
      }

      protected abstract Type resolveType();

      // $FF: synthetic method
      TypeAttribute(Object x1) {
         this();
      }
   }
}
