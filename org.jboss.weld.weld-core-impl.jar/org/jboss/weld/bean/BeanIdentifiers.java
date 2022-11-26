package org.jboss.weld.bean;

import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.reflection.DeclaredMemberIndexer;

public class BeanIdentifiers {
   public static final String PREFIX = "WELD%";
   public static final String PREFIX_BUILDER = "BUILDER%";

   private BeanIdentifiers() {
   }

   public static StringBuilder getPrefix(Class beanType) {
      return (new StringBuilder("WELD%")).append(beanType.getSimpleName()).append("%");
   }

   public static String forManagedBean(EnhancedAnnotatedType type) {
      return forManagedBean((AnnotatedTypeIdentifier)type.slim().getIdentifier());
   }

   public static String forManagedBean(AnnotatedTypeIdentifier identifier) {
      return getPrefix(ManagedBean.class).append(identifier.asString()).toString();
   }

   public static String forDecorator(EnhancedAnnotatedType type) {
      return getPrefix(DecoratorImpl.class).append(((AnnotatedTypeIdentifier)type.slim().getIdentifier()).asString()).toString();
   }

   public static String forInterceptor(EnhancedAnnotatedType type) {
      return getPrefix(InterceptorImpl.class).append(((AnnotatedTypeIdentifier)type.slim().getIdentifier()).asString()).toString();
   }

   public static String forNewManagedBean(EnhancedAnnotatedType type) {
      return getPrefix(NewManagedBean.class).append(((AnnotatedTypeIdentifier)type.slim().getIdentifier()).asString()).toString();
   }

   public static String forProducerField(EnhancedAnnotatedField field, AbstractClassBean declaringBean) {
      StringBuilder sb = getPrefix(ProducerField.class).append(((AnnotatedTypeIdentifier)declaringBean.getAnnotated().getIdentifier()).asString()).append("%");
      if (declaringBean.getEnhancedAnnotated().isDiscovered()) {
         sb.append(field.getName());
      } else {
         sb.append(AnnotatedTypes.createFieldId(field));
      }

      return sb.toString();
   }

   public static String forProducerMethod(EnhancedAnnotatedMethod method, AbstractClassBean declaringBean) {
      return declaringBean.getEnhancedAnnotated().isDiscovered() ? forProducerMethod((AnnotatedTypeIdentifier)declaringBean.getAnnotated().getIdentifier(), DeclaredMemberIndexer.getIndexForMethod(method.getJavaMember())) : getPrefix(ProducerMethod.class).append(method.getDeclaringType().slim().getIdentifier()).append(AnnotatedTypes.createCallableId(method)).toString();
   }

   public static String forProducerMethod(AnnotatedTypeIdentifier identifier, int memberIndex) {
      return getPrefix(ProducerMethod.class).append(identifier.asString()).append("%").append(memberIndex).toString();
   }

   public static String forSyntheticBean(BeanAttributes attributes, Class beanClass) {
      return getPrefix(AbstractSyntheticBean.class).append(beanClass.getName()).append("%").append(Beans.createBeanAttributesId(attributes)).toString();
   }

   public static String forBuiltInBean(BeanManagerImpl manager, Class type, String suffix) {
      StringBuilder builder = getPrefix(AbstractBuiltInBean.class).append(manager.getId()).append("%").append(type.getSimpleName());
      if (suffix != null) {
         builder.append("%").append(suffix);
      }

      return builder.toString();
   }

   public static String forExtension(EnhancedAnnotatedType type) {
      return getPrefix(Extension.class).append(((AnnotatedTypeIdentifier)type.slim().getIdentifier()).asString()).toString();
   }

   public static String forBuilderBean(BeanAttributes attributes, Class beanClass) {
      return "BUILDER%" + beanClass.getName() + "%" + Beans.createBeanAttributesId(attributes);
   }
}
