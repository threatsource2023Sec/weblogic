package org.jboss.weld.injection.attributes;

import java.lang.reflect.Field;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.reflection.Reflections;

public class InferringFieldInjectionPointAttributes extends AbstractInferringInjectionPointAttributes implements FieldInjectionPointAttributes {
   private static final long serialVersionUID = -3099189770772787108L;
   private final AnnotatedField field;

   public static InferringFieldInjectionPointAttributes of(EnhancedAnnotatedField field, Bean bean, Class declaringComponentClass, BeanManagerImpl manager) {
      return new InferringFieldInjectionPointAttributes(field, bean, declaringComponentClass, manager);
   }

   protected InferringFieldInjectionPointAttributes(EnhancedAnnotatedField field, Bean bean, Class declaringComponentClass, BeanManagerImpl manager) {
      super(field, manager.getContextId(), bean, SharedObjectCache.instance(manager).getSharedSet(field.getQualifiers()), declaringComponentClass);
      this.field = field.slim();
   }

   public Field getMember() {
      return this.getAnnotated().getJavaMember();
   }

   public AnnotatedField getAnnotated() {
      return this.field;
   }

   public int hashCode() {
      return this.getAnnotated().hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof InferringFieldInjectionPointAttributes) {
         AnnotatedField field = ((InferringFieldInjectionPointAttributes)Reflections.cast(obj)).getAnnotated();
         return AnnotatedTypes.compareAnnotatedField(this.getAnnotated(), field);
      } else {
         return false;
      }
   }
}
