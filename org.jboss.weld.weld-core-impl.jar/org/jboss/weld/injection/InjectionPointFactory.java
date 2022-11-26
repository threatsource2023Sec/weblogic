package org.jboss.weld.injection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedCallable;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.unbacked.UnbackedAnnotatedType;
import org.jboss.weld.injection.attributes.FieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.InferringFieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.InferringParameterInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ParameterInjectionPointAttributes;
import org.jboss.weld.injection.attributes.SpecialParameterInjectionPoint;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableSet;

public class InjectionPointFactory {
   private static final InjectionPointFactory INSTANCE = new InjectionPointFactory();
   private static final InjectionPointFactory SILENT_INSTANCE = new InjectionPointFactory() {
      protected FieldInjectionPointAttributes processInjectionPoint(FieldInjectionPointAttributes injectionPointAttributes, Class declaringComponentClass, BeanManagerImpl manager) {
         return injectionPointAttributes;
      }

      protected ParameterInjectionPointAttributes processInjectionPoint(ParameterInjectionPointAttributes injectionPointAttributes, Class declaringComponentClass, BeanManagerImpl manager) {
         return injectionPointAttributes;
      }
   };

   private InjectionPointFactory() {
   }

   public static InjectionPointFactory instance() {
      return INSTANCE;
   }

   public static InjectionPointFactory silentInstance() {
      return SILENT_INSTANCE;
   }

   protected FieldInjectionPointAttributes processInjectionPoint(FieldInjectionPointAttributes injectionPointAttributes, Class declaringComponentClass, BeanManagerImpl manager) {
      return manager.getContainerLifecycleEvents().fireProcessInjectionPoint(injectionPointAttributes, declaringComponentClass, manager);
   }

   protected ParameterInjectionPointAttributes processInjectionPoint(ParameterInjectionPointAttributes injectionPointAttributes, Class declaringComponentClass, BeanManagerImpl manager) {
      return manager.getContainerLifecycleEvents().fireProcessInjectionPoint(injectionPointAttributes, declaringComponentClass, manager);
   }

   public FieldInjectionPoint createFieldInjectionPoint(EnhancedAnnotatedField field, Bean declaringBean, Class declaringComponentClass, BeanManagerImpl manager) {
      FieldInjectionPointAttributes attributes = InferringFieldInjectionPointAttributes.of(field, declaringBean, declaringComponentClass, manager);
      FieldInjectionPointAttributes attributes = this.processInjectionPoint((FieldInjectionPointAttributes)attributes, declaringComponentClass, manager);
      return new FieldInjectionPoint(attributes);
   }

   public ParameterInjectionPoint createParameterInjectionPoint(EnhancedAnnotatedParameter parameter, Bean declaringBean, Class declaringComponentClass, BeanManagerImpl manager) {
      ParameterInjectionPointAttributes attributes = InferringParameterInjectionPointAttributes.of(parameter, declaringBean, declaringComponentClass, manager);
      ParameterInjectionPointAttributes attributes = this.processInjectionPoint((ParameterInjectionPointAttributes)attributes, declaringComponentClass, manager);
      return new ParameterInjectionPointImpl(attributes);
   }

   public ConstructorInjectionPoint createConstructorInjectionPoint(Bean declaringBean, EnhancedAnnotatedType type, BeanManagerImpl manager) {
      EnhancedAnnotatedConstructor constructor = Beans.getBeanConstructorStrict(type);
      return this.createConstructorInjectionPoint(declaringBean, type.getJavaClass(), constructor, manager);
   }

   public ConstructorInjectionPoint createConstructorInjectionPoint(Bean declaringBean, Class declaringComponentClass, EnhancedAnnotatedConstructor constructor, BeanManagerImpl manager) {
      return new ConstructorInjectionPoint(constructor, declaringBean, declaringComponentClass, this, manager);
   }

   public MethodInjectionPoint createMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType methodInjectionPointType, EnhancedAnnotatedMethod enhancedMethod, Bean declaringBean, Class declaringComponentClass, Set specialParameterMarkers, BeanManagerImpl manager) {
      return (MethodInjectionPoint)(enhancedMethod.isStatic() ? new StaticMethodInjectionPoint(methodInjectionPointType, enhancedMethod, declaringBean, declaringComponentClass, specialParameterMarkers, this, manager) : new VirtualMethodInjectionPoint(methodInjectionPointType, enhancedMethod, declaringBean, declaringComponentClass, specialParameterMarkers, this, manager));
   }

   public List getFieldInjectionPoints(Bean declaringBean, EnhancedAnnotatedType type, BeanManagerImpl manager) {
      List injectableFieldsList = new ArrayList();
      if (type.slim() instanceof UnbackedAnnotatedType) {
         Collection allFields = type.getEnhancedFields(Inject.class);

         for(Class clazz = type.getJavaClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            ImmutableSet.Builder fields = ImmutableSet.builder();
            Iterator var8 = allFields.iterator();

            while(var8.hasNext()) {
               EnhancedAnnotatedField field = (EnhancedAnnotatedField)var8.next();
               if (!field.isStatic() && field.getJavaMember().getDeclaringClass().equals(clazz)) {
                  this.addFieldInjectionPoint(field, fields, declaringBean, type.getJavaClass(), manager);
               }
            }

            injectableFieldsList.add(0, fields.build());
         }
      } else {
         for(EnhancedAnnotatedType t = type; t != null && !t.getJavaClass().equals(Object.class); t = t.getEnhancedSuperclass()) {
            ImmutableSet.Builder fields = ImmutableSet.builder();
            Iterator var12 = t.getDeclaredEnhancedFields(Inject.class).iterator();

            while(var12.hasNext()) {
               EnhancedAnnotatedField annotatedField = (EnhancedAnnotatedField)var12.next();
               if (!annotatedField.isStatic()) {
                  this.addFieldInjectionPoint(annotatedField, fields, declaringBean, t.getJavaClass(), manager);
               }
            }

            injectableFieldsList.add(0, fields.build());
         }
      }

      return ImmutableList.copyOf((Collection)injectableFieldsList);
   }

   private void addFieldInjectionPoint(EnhancedAnnotatedField annotatedField, ImmutableSet.Builder injectableFields, Bean declaringBean, Class declaringComponentClass, BeanManagerImpl manager) {
      if (annotatedField.isFinal()) {
         throw UtilLogger.LOG.qualifierOnFinalField(annotatedField);
      } else {
         injectableFields.add(this.createFieldInjectionPoint(annotatedField, declaringBean, declaringComponentClass, manager));
      }
   }

   public List getParameterInjectionPoints(EnhancedAnnotatedCallable callable, Bean declaringBean, Class declaringComponentClass, BeanManagerImpl manager, boolean observerOrDisposer) {
      List parameters = new ArrayList();
      Bean bean = null;
      if (!observerOrDisposer) {
         bean = declaringBean;
      }

      Iterator var8 = callable.getEnhancedParameters().iterator();

      while(var8.hasNext()) {
         EnhancedAnnotatedParameter parameter = (EnhancedAnnotatedParameter)var8.next();
         if (this.isSpecialParameter(parameter)) {
            parameters.add(SpecialParameterInjectionPoint.of(parameter, bean, declaringBean.getBeanClass(), manager));
         } else {
            parameters.add(this.createParameterInjectionPoint(parameter, bean, declaringComponentClass, manager));
         }
      }

      return ImmutableList.copyOf((Collection)parameters);
   }

   private boolean isSpecialParameter(EnhancedAnnotatedParameter parameter) {
      return parameter.isAnnotationPresent(Disposes.class) || parameter.isAnnotationPresent(Observes.class) || parameter.isAnnotationPresent(ObservesAsync.class);
   }

   // $FF: synthetic method
   InjectionPointFactory(Object x0) {
      this();
   }
}
