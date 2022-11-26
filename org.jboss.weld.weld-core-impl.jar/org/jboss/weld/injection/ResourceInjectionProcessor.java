package org.jboss.weld.injection;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public abstract class ResourceInjectionProcessor {
   protected ResourceInjection createStaticProducerFieldResourceInjection(FieldInjectionPoint fieldInjectionPoint, BeanManagerImpl beanManager) {
      Service injectionServices = this.getInjectionServices(beanManager);
      Object processorContext = this.getProcessorContext(beanManager);
      return injectionServices != null && fieldInjectionPoint.getAnnotated().isAnnotationPresent(this.getMarkerAnnotation(processorContext)) && this.accept(fieldInjectionPoint.getAnnotated(), processorContext) ? this.createFieldResourceInjection(fieldInjectionPoint, injectionServices, processorContext) : null;
   }

   protected Set createResourceInjections(Bean declaringBean, EnhancedAnnotatedType type, BeanManagerImpl manager) {
      Service injectionServices = this.getInjectionServices(manager);
      Object processorContext = this.getProcessorContext(manager);
      if (injectionServices == null) {
         return Collections.emptySet();
      } else {
         Class marker = this.getMarkerAnnotation(processorContext);
         Collection fields = type.getDeclaredEnhancedFields(marker);
         Collection methods = type.getDeclaredEnhancedMethods(marker);
         return this.createResourceInjections(fields, methods, declaringBean, type.getJavaClass(), manager);
      }
   }

   private ResourceInjection createFieldResourceInjection(FieldInjectionPoint fieldInjectionPoint, Service injectionServices, Object processorContext) {
      return new FieldResourceInjection(fieldInjectionPoint, (ResourceReferenceFactory)Reflections.cast(this.getResourceReferenceFactory(fieldInjectionPoint, injectionServices, processorContext)));
   }

   private ResourceInjection createSetterResourceInjection(ParameterInjectionPoint parameterInjectionPoint, Service injectionServices, Object processorContext) {
      return new SetterResourceInjection(parameterInjectionPoint, (ResourceReferenceFactory)Reflections.cast(this.getResourceReferenceFactory(parameterInjectionPoint, injectionServices, processorContext)));
   }

   public Class getMarkerAnnotation(BeanManagerImpl manager) {
      return this.getMarkerAnnotation(this.getProcessorContext(manager));
   }

   protected abstract ResourceReferenceFactory getResourceReferenceFactory(InjectionPoint var1, Service var2, Object var3);

   protected abstract Class getMarkerAnnotation(Object var1);

   protected abstract Object getProcessorContext(BeanManagerImpl var1);

   protected abstract Service getInjectionServices(BeanManagerImpl var1);

   protected Set createResourceInjections(Iterable fields, Iterable methods, Bean declaringBean, Class declaringClass, BeanManagerImpl manager) {
      ImmutableSet.Builder resourceInjections = ImmutableSet.builder();
      Service injectionServices = this.getInjectionServices(manager);
      Object processorContext = this.getProcessorContext(manager);
      Iterator var9 = fields.iterator();

      while(var9.hasNext()) {
         EnhancedAnnotatedField field = (EnhancedAnnotatedField)var9.next();
         if (this.accept(field, processorContext)) {
            resourceInjections.add(this.createFieldResourceInjection(InjectionPointFactory.silentInstance().createFieldInjectionPoint(field, declaringBean, declaringClass, manager), injectionServices, processorContext));
         }
      }

      var9 = methods.iterator();

      while(var9.hasNext()) {
         EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var9.next();
         if (method.getParameters().size() != 1) {
            throw UtilLogger.LOG.resourceSetterInjectionNotAJavabean(method);
         }

         if (this.accept(method, processorContext)) {
            EnhancedAnnotatedParameter parameter = (EnhancedAnnotatedParameter)method.getEnhancedParameters().get(0);
            resourceInjections.add(this.createSetterResourceInjection(InjectionPointFactory.silentInstance().createParameterInjectionPoint(parameter, declaringBean, declaringClass, manager), injectionServices, processorContext));
         }
      }

      return resourceInjections.build();
   }

   protected boolean accept(AnnotatedMember member, Object processorContext) {
      return true;
   }

   protected Type getResourceInjectionPointType(AnnotatedMember member) {
      if (member instanceof AnnotatedField) {
         return member.getBaseType();
      } else if (member instanceof AnnotatedMethod) {
         AnnotatedMethod method = (AnnotatedMethod)member;
         if (method.getParameters().size() != 1) {
            throw UtilLogger.LOG.resourceSetterInjectionNotAJavabean(method);
         } else {
            return ((AnnotatedParameter)method.getParameters().get(0)).getBaseType();
         }
      } else {
         throw new IllegalArgumentException("Unknown member " + member);
      }
   }
}
