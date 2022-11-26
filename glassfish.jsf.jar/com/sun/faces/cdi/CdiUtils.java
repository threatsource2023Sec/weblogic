package com.sun.faces.cdi;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.TypeLiteral;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.DataModel;
import javax.faces.validator.Validator;

public final class CdiUtils {
   private static final Logger LOGGER;
   private static final Type CONVERTER_TYPE;
   private static final Type VALIDATOR_TYPE;

   private CdiUtils() {
   }

   public static Converter createConverter(BeanManager beanManager, String value) {
      Converter managedConverter = createConverter(beanManager, (Annotation)(new FacesConverterAnnotationLiteral(value, Object.class)));
      return managedConverter != null ? new CdiConverter(value, Object.class, managedConverter) : null;
   }

   public static Converter createConverter(BeanManager beanManager, Class forClass) {
      Converter managedConverter = null;

      for(Class forClassOrSuperclass = forClass; managedConverter == null && forClassOrSuperclass != null && forClassOrSuperclass != Object.class; forClassOrSuperclass = forClassOrSuperclass.getSuperclass()) {
         managedConverter = createConverter(beanManager, (Annotation)(new FacesConverterAnnotationLiteral("", forClassOrSuperclass)));
      }

      return managedConverter != null ? new CdiConverter("", forClass, managedConverter) : null;
   }

   private static Converter createConverter(BeanManager beanManager, Annotation qualifier) {
      Converter managedConverter = (Converter)getBeanReferenceByType(beanManager, CONVERTER_TYPE, qualifier);
      if (managedConverter == null) {
         managedConverter = (Converter)getBeanReference(beanManager, Converter.class, qualifier);
      }

      return managedConverter;
   }

   public static Behavior createBehavior(BeanManager beanManager, String value) {
      Behavior delegatingBehavior = null;
      Behavior managedBehavior = (Behavior)getBeanReference(beanManager, Behavior.class, new FacesBehaviorAnnotationLiteral(value));
      if (managedBehavior != null) {
         delegatingBehavior = new CdiBehavior(value, managedBehavior);
      }

      return delegatingBehavior;
   }

   public static Validator createValidator(BeanManager beanManager, String value) {
      Annotation qualifier = new FacesValidatorAnnotationLiteral(value);
      Validator managedValidator = (Validator)getBeanReferenceByType(beanManager, VALIDATOR_TYPE, qualifier);
      if (managedValidator == null) {
         managedValidator = (Validator)getBeanReference(beanManager, Validator.class, qualifier);
      }

      return managedValidator != null ? new CdiValidator(value, managedValidator) : null;
   }

   public static Object getBeanReference(Class type, Annotation... qualifiers) {
      return type.cast(getBeanReferenceByType(Util.getCdiBeanManager(FacesContext.getCurrentInstance()), type, qualifiers));
   }

   public static Object getBeanReference(BeanManager beanManager, Class type, Annotation... qualifiers) {
      return type.cast(getBeanReferenceByType(beanManager, type, qualifiers));
   }

   public static Object getBeanReferenceByType(BeanManager beanManager, Type type, Annotation... qualifiers) {
      Object beanReference = null;
      Bean bean = beanManager.resolve(beanManager.getBeans(type, qualifiers));
      if (bean != null) {
         beanReference = beanManager.getReference(bean, type, beanManager.createCreationalContext(bean));
      }

      return beanReference;
   }

   public static Object getBeanInstance(Class type, boolean create) {
      BeanManager beanManager = Util.getCdiBeanManager(FacesContext.getCurrentInstance());
      Bean bean = beanManager.resolve(beanManager.getBeans(type, new Annotation[0]));
      if (bean != null) {
         Context context = beanManager.getContext(bean.getScope());
         return create ? context.get(bean, beanManager.createCreationalContext(bean)) : context.get(bean);
      } else {
         return null;
      }
   }

   public static Optional getAnnotation(BeanManager beanManager, Annotated annotated, Class annotationType) {
      annotated.getAnnotation(annotationType);
      if (annotated.getAnnotations().isEmpty()) {
         return Optional.empty();
      } else if (annotated.isAnnotationPresent(annotationType)) {
         return Optional.of(annotated.getAnnotation(annotationType));
      } else {
         Queue annotations = new LinkedList(annotated.getAnnotations());

         while(!annotations.isEmpty()) {
            Annotation annotation = (Annotation)annotations.remove();
            if (annotation.annotationType().equals(annotationType)) {
               return Optional.of(annotationType.cast(annotation));
            }

            try {
               if (beanManager.isStereotype(annotation.annotationType())) {
                  annotations.addAll(beanManager.getStereotypeDefinition(annotation.annotationType()));
               }
            } catch (Exception var6) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.warning("Exception happened when finding an annotation: " + var6);
               }
            }
         }

         return Optional.empty();
      }
   }

   public static DataModel createDataModel(Class forClass) {
      List dataModel = new ArrayList(1);
      CDI cdi = CDI.current();
      getDataModelClassesMap(cdi).entrySet().stream().filter((e) -> {
         return ((Class)e.getKey()).isAssignableFrom(forClass);
      }).findFirst().ifPresent((e) -> {
         dataModel.add(cdi.select((Class)e.getValue(), new Annotation[]{new FacesDataModelAnnotationLiteral((Class)e.getKey())}).get());
      });
      return dataModel.isEmpty() ? null : (DataModel)dataModel.get(0);
   }

   public static Map getDataModelClassesMap(CDI cdi) {
      BeanManager beanManager = cdi.getBeanManager();
      Bean bean = beanManager.resolve(beanManager.getBeans("comSunFacesDataModelClassesMap"));
      Object beanReference = beanManager.getReference(bean, Map.class, beanManager.createCreationalContext(bean));
      return (Map)beanReference;
   }

   public static InjectionPoint getCurrentInjectionPoint(BeanManager beanManager, CreationalContext creationalContext) {
      Bean bean = beanManager.resolve(beanManager.getBeans(InjectionPoint.class, new Annotation[0]));
      InjectionPoint injectionPoint = (InjectionPoint)beanManager.getReference(bean, InjectionPoint.class, creationalContext);
      if (injectionPoint == null) {
         bean = beanManager.resolve(beanManager.getBeans(InjectionPointGenerator.class, new Annotation[0]));
         injectionPoint = (InjectionPoint)beanManager.getInjectableReference((InjectionPoint)bean.getInjectionPoints().iterator().next(), creationalContext);
      }

      return injectionPoint;
   }

   public static Annotation getQualifier(InjectionPoint injectionPoint, Class qualifierClass) {
      Iterator var2 = injectionPoint.getQualifiers().iterator();

      Annotation annotation;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         annotation = (Annotation)var2.next();
      } while(!qualifierClass.isAssignableFrom(annotation.getClass()));

      return (Annotation)qualifierClass.cast(annotation);
   }

   public static boolean isScopeActive(Class scope) {
      BeanManager beanManager = Util.getCdiBeanManager(FacesContext.getCurrentInstance());

      try {
         Context context = beanManager.getContext(scope);
         return context.isActive();
      } catch (ContextNotActiveException var3) {
         return false;
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION_VIEW.getLogger();
      CONVERTER_TYPE = (new TypeLiteral() {
         private static final long serialVersionUID = 1L;
      }).getType();
      VALIDATOR_TYPE = (new TypeLiteral() {
         private static final long serialVersionUID = 1L;
      }).getType();
   }
}
