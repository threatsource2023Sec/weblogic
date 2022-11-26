package org.jboss.weld.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.interceptor.InterceptorBinding;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.util.collections.Multimap;
import org.jboss.weld.util.collections.SetMultimap;

public class Interceptors {
   private Interceptors() {
   }

   public static Set filterInterceptorBindings(BeanManagerImpl beanManager, Collection annotations) {
      Set interceptorBindings = new InterceptorBindingSet(beanManager);
      Iterator var3 = annotations.iterator();

      while(var3.hasNext()) {
         Annotation annotation = (Annotation)var3.next();
         if (beanManager.isInterceptorBinding(annotation.annotationType())) {
            interceptorBindings.add(annotation);
         }
      }

      return interceptorBindings;
   }

   public static Set flattenInterceptorBindings(EnhancedAnnotatedType clazz, BeanManagerImpl beanManager, Collection annotations, boolean addTopLevelInterceptorBindings, boolean addInheritedInterceptorBindings) {
      Set flattenInterceptorBindings = new InterceptorBindingSet(beanManager);
      MetaAnnotationStore metaAnnotationStore = (MetaAnnotationStore)beanManager.getServices().get(MetaAnnotationStore.class);
      if (addTopLevelInterceptorBindings) {
         addInterceptorBindings(clazz, annotations, flattenInterceptorBindings, metaAnnotationStore);
      }

      if (addInheritedInterceptorBindings) {
         Iterator var7 = annotations.iterator();

         while(var7.hasNext()) {
            Annotation annotation = (Annotation)var7.next();
            addInheritedInterceptorBindings(clazz, annotation.annotationType(), metaAnnotationStore, flattenInterceptorBindings);
         }
      }

      return flattenInterceptorBindings;
   }

   private static void addInheritedInterceptorBindings(EnhancedAnnotatedType clazz, Class bindingType, MetaAnnotationStore metaAnnotationStore, Set flattenInterceptorBindings) {
      Set metaBindings = metaAnnotationStore.getInterceptorBindingModel(bindingType).getInheritedInterceptionBindingTypes();
      addInterceptorBindings(clazz, metaBindings, flattenInterceptorBindings, metaAnnotationStore);
      Iterator var5 = metaBindings.iterator();

      while(var5.hasNext()) {
         Annotation metaBinding = (Annotation)var5.next();
         addInheritedInterceptorBindings(clazz, metaBinding.annotationType(), metaAnnotationStore, flattenInterceptorBindings);
      }

   }

   private static void addInterceptorBindings(EnhancedAnnotatedType clazz, Collection interceptorBindings, Set flattenInterceptorBindings, MetaAnnotationStore metaAnnotationStore) {
      Annotation annotation;
      for(Iterator var4 = interceptorBindings.iterator(); var4.hasNext(); flattenInterceptorBindings.add(annotation)) {
         annotation = (Annotation)var4.next();
         Class annotationType = annotation.annotationType();
         if (!annotation.annotationType().isAnnotationPresent(Repeatable.class)) {
            Iterator var7 = flattenInterceptorBindings.iterator();

            while(var7.hasNext()) {
               Annotation binding = (Annotation)var7.next();
               if (binding.annotationType().equals(annotationType) && !metaAnnotationStore.getInterceptorBindingModel(annotationType).isEqual(annotation, binding, false)) {
                  if (clazz != null) {
                     throw new DefinitionException(BeanLogger.LOG.conflictingInterceptorBindings(clazz));
                  }

                  throw BeanManagerLogger.LOG.duplicateInterceptorBinding(annotation);
               }
            }
         }
      }

   }

   public static Multimap mergeBeanInterceptorBindings(BeanManagerImpl beanManager, EnhancedAnnotatedType clazz, Collection stereotypes) {
      Set rawBindings = clazz.getMetaAnnotations(InterceptorBinding.class);
      Set classBindingAnnotations = flattenInterceptorBindings(clazz, beanManager, filterInterceptorBindings(beanManager, rawBindings), true, false);
      Set inheritedBindingAnnotations = new HashSet();
      inheritedBindingAnnotations.addAll(flattenInterceptorBindings(clazz, beanManager, filterInterceptorBindings(beanManager, rawBindings), false, true));
      Iterator var6 = stereotypes.iterator();

      while(var6.hasNext()) {
         Class annotation = (Class)var6.next();
         inheritedBindingAnnotations.addAll(flattenInterceptorBindings(clazz, beanManager, filterInterceptorBindings(beanManager, beanManager.getStereotypeDefinition(annotation)), true, true));
      }

      try {
         return mergeBeanInterceptorBindings(beanManager, clazz, classBindingAnnotations, inheritedBindingAnnotations);
      } catch (DeploymentException var8) {
         throw new DefinitionException(BeanLogger.LOG.conflictingInterceptorBindings(clazz.getJavaClass()));
      }
   }

   public static Multimap mergeBeanInterceptorBindings(BeanManagerImpl beanManager, AnnotatedType clazz, Collection classBindingAnnotations, Collection inheritedBindingAnnotations) {
      SetMultimap mergedBeanBindings = SetMultimap.newSetMultimap();
      SetMultimap acceptedInheritedBindings = SetMultimap.newSetMultimap();
      MetaAnnotationStore metaAnnotationStore = (MetaAnnotationStore)beanManager.getServices().get(MetaAnnotationStore.class);

      Iterator var7;
      Annotation binding;
      Class annotationType;
      for(var7 = classBindingAnnotations.iterator(); var7.hasNext(); mergedBeanBindings.put(binding.annotationType(), binding)) {
         binding = (Annotation)var7.next();
         annotationType = binding.annotationType();
         if (!annotationType.isAnnotationPresent(Repeatable.class)) {
            Iterator var10 = ((Set)mergedBeanBindings.get(annotationType)).iterator();

            while(var10.hasNext()) {
               Annotation mergedBinding = (Annotation)var10.next();
               if (!metaAnnotationStore.getInterceptorBindingModel(annotationType).isEqual(binding, mergedBinding, false)) {
                  throw new DeploymentException(BeanLogger.LOG.conflictingInterceptorBindings(clazz.getJavaClass()));
               }
            }
         }
      }

      var7 = inheritedBindingAnnotations.iterator();

      while(true) {
         while(var7.hasNext()) {
            binding = (Annotation)var7.next();
            annotationType = binding.annotationType();
            if (mergedBeanBindings.containsKey(annotationType) && !annotationType.isAnnotationPresent(Repeatable.class)) {
               Set inheritedBindings = (Set)acceptedInheritedBindings.get(annotationType);
               Iterator var14 = inheritedBindings.iterator();

               while(var14.hasNext()) {
                  Annotation inheritedBinding = (Annotation)var14.next();
                  if (!metaAnnotationStore.getInterceptorBindingModel(annotationType).isEqual(binding, inheritedBinding, false)) {
                     throw new DeploymentException(BeanLogger.LOG.conflictingInterceptorBindings(clazz.getJavaClass()));
                  }
               }
            } else {
               mergedBeanBindings.put(annotationType, binding);
               acceptedInheritedBindings.put(annotationType, binding);
            }
         }

         return mergedBeanBindings;
      }
   }
}
