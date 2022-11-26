package org.glassfish.soteria.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import javax.el.ELProcessor;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.glassfish.soteria.Utils;

public class CdiUtils {
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

            if (beanManager.isStereotype(annotation.annotationType())) {
               annotations.addAll(beanManager.getStereotypeDefinition(annotation.annotationType()));
            }
         }

         return Optional.empty();
      }
   }

   public static void addAnnotatedTypes(BeforeBeanDiscovery beforeBean, BeanManager beanManager, Class... types) {
      Class[] var3 = types;
      int var4 = types.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class type = var3[var5];
         beforeBean.addAnnotatedType(beanManager.createAnnotatedType(type), "Soteria " + type.getName());
      }

   }

   public static Optional getAnnotation(BeanManager beanManager, Class annotatedClass, Class annotationType) {
      if (annotatedClass.isAnnotationPresent(annotationType)) {
         return Optional.of(annotatedClass.getAnnotation(annotationType));
      } else {
         Queue annotations = new LinkedList(Arrays.asList(annotatedClass.getAnnotations()));

         while(!annotations.isEmpty()) {
            Annotation annotation = (Annotation)annotations.remove();
            if (annotation.annotationType().equals(annotationType)) {
               return Optional.of(annotationType.cast(annotation));
            }

            if (beanManager.isStereotype(annotation.annotationType())) {
               annotations.addAll(beanManager.getStereotypeDefinition(annotation.annotationType()));
            }
         }

         return Optional.empty();
      }
   }

   public static BeanManager getBeanManager() {
      return (BeanManager)jndiLookup("java:comp/BeanManager");
   }

   public static Object getBeanReference(Class type, Annotation... qualifiers) {
      return type.cast(getBeanReferenceByType(getBeanManager(), type, qualifiers));
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

   private static Object getContextualReference(Class type, BeanManager beanManager, Set beans) {
      Object beanReference = null;
      Bean bean = beanManager.resolve(beans);
      if (bean != null) {
         beanReference = beanManager.getReference(bean, type, beanManager.createCreationalContext(bean));
      }

      return beanReference;
   }

   public static List getBeanReferencesByType(Class type, boolean optional) {
      BeanManager beanManager = getBeanManager();
      Set beans = getBeanDefinitions(type, optional, beanManager);
      List result = new ArrayList(beans.size());
      Iterator var5 = beans.iterator();

      while(var5.hasNext()) {
         Bean bean = (Bean)var5.next();
         result.add(getContextualReference(type, beanManager, Collections.singleton(bean)));
      }

      return result;
   }

   public static ELProcessor getELProcessor(ELProcessor elProcessor) {
      return elProcessor != null ? elProcessor : getELProcessor();
   }

   public static ELProcessor getELProcessor() {
      ELProcessor elProcessor = new ELProcessor();
      elProcessor.getELManager().addELResolver(getBeanManager().getELResolver());
      return elProcessor;
   }

   private static Set getBeanDefinitions(Class type, boolean optional, BeanManager beanManager) {
      Set beans = beanManager.getBeans(type, new Annotation[]{new AnyAnnotationLiteral()});
      if (!Utils.isEmpty((Collection)beans)) {
         return beans;
      } else if (optional) {
         return Collections.emptySet();
      } else {
         throw new IllegalStateException("Could not find beans for Type=" + type);
      }
   }

   public static Object jndiLookup(String name) {
      InitialContext context = null;

      Object var3;
      try {
         context = new InitialContext();
         Object var2 = context.lookup(name);
         return var2;
      } catch (NamingException var7) {
         if (!is(var7, NameNotFoundException.class)) {
            throw new IllegalStateException(var7);
         }

         var3 = null;
      } finally {
         close(context);
      }

      return var3;
   }

   private static void close(InitialContext context) {
      try {
         if (context != null) {
            context.close();
         }

      } catch (NamingException var2) {
         throw new IllegalStateException(var2);
      }
   }

   public static boolean is(Throwable exception, Class type) {
      for(Throwable unwrappedException = exception; unwrappedException != null; unwrappedException = unwrappedException.getCause()) {
         if (type.isInstance(unwrappedException)) {
            return true;
         }
      }

      return false;
   }
}
