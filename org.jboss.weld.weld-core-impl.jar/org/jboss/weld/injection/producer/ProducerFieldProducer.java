package org.jboss.weld.injection.producer;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.util.Collections;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.inject.Inject;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public abstract class ProducerFieldProducer extends AbstractMemberProducer {
   private final Field accessibleField;

   public ProducerFieldProducer(EnhancedAnnotatedField enhancedField, DisposalMethod disposalMethod) {
      super(enhancedField, disposalMethod);
      this.accessibleField = (Field)AccessController.doPrivileged(new GetAccessibleCopyOfMember(enhancedField.getJavaMember()));
      this.checkProducerField(enhancedField);
   }

   protected void checkProducerField(EnhancedAnnotatedField enhancedField) {
      if (this.getDeclaringBean() instanceof SessionBean && !enhancedField.isStatic()) {
         throw BeanLogger.LOG.producerFieldOnSessionBeanMustBeStatic(enhancedField, enhancedField.getDeclaringType());
      } else if (enhancedField.isAnnotationPresent(Inject.class)) {
         if (this.getDeclaringBean() != null) {
            throw BeanLogger.LOG.injectedFieldCannotBeProducer(enhancedField, this.getDeclaringBean());
         } else {
            throw BeanLogger.LOG.injectedFieldCannotBeProducer(enhancedField, enhancedField.getDeclaringType());
         }
      }
   }

   public Set getInjectionPoints() {
      return Collections.emptySet();
   }

   public abstract AnnotatedField getAnnotated();

   public Object produce(Object receiver, CreationalContext creationalContext) {
      if (receiver instanceof TargetInstanceProxy) {
         receiver = ((TargetInstanceProxy)Reflections.cast(receiver)).weld_getTargetInstance();
      }

      try {
         return Reflections.cast(this.accessibleField.get(receiver));
      } catch (IllegalAccessException var4) {
         throw UtilLogger.LOG.accessErrorOnField(this.accessibleField.getName(), this.accessibleField.getDeclaringClass(), var4);
      }
   }

   public String toString() {
      return this.getAnnotated().toString();
   }

   protected DefinitionException producerWithInvalidTypeVariable(AnnotatedMember member) {
      return BeanLogger.LOG.producerFieldTypeInvalidTypeVariable(member, Formats.formatAsStackTraceElement(member.getJavaMember()));
   }

   protected DefinitionException producerWithInvalidWildcard(AnnotatedMember member) {
      return BeanLogger.LOG.producerFieldCannotHaveAWildcardBeanType(member, Formats.formatAsStackTraceElement(member.getJavaMember()));
   }

   protected DefinitionException producerWithParameterizedTypeWithTypeVariableBeanTypeMustBeDependent(AnnotatedMember member) {
      return BeanLogger.LOG.producerFieldWithTypeVariableBeanTypeMustBeDependent(member, Formats.formatAsStackTraceElement(member.getJavaMember()));
   }
}
