package org.jboss.weld.util;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.MethodInjectionPoint;
import org.jboss.weld.interceptor.reader.InterceptorMetadataUtils;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.interceptor.util.InterceptionTypeRegistry;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.SetAccessibleAction;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;

public class BeanMethods {
   private BeanMethods() {
   }

   private static Object getMethods(EnhancedAnnotatedType type, MethodListBuilder builder) {
      Collection methods = filterMethods(builder.getAllMethods(type));

      for(Class clazz = type.getJavaClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
         builder.levelStart(clazz);
         Iterator var4 = methods.iterator();

         while(var4.hasNext()) {
            EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var4.next();
            if (method.getJavaMember().getDeclaringClass().equals(clazz)) {
               builder.processMethod(method);
            }
         }

         builder.levelFinish();
      }

      return builder.create();
   }

   public static List getPostConstructMethods(final EnhancedAnnotatedType type) {
      return (List)getMethods(type, new AbstractLifecycleEventCallbackMethodListBuilder() {
         public Collection getAllMethods(EnhancedAnnotatedType typex) {
            return typex.getEnhancedMethods(PostConstruct.class);
         }

         protected void duplicateMethod(EnhancedAnnotatedMethod method) {
            throw UtilLogger.LOG.tooManyPostConstructMethods(type);
         }

         protected EnhancedAnnotatedMethod processLevelResult(EnhancedAnnotatedMethod method) {
            BeanLogger.LOG.foundOnePostConstructMethod(method, type);
            return method;
         }
      });
   }

   public static List getPreDestroyMethods(final EnhancedAnnotatedType type) {
      return (List)getMethods(type, new AbstractLifecycleEventCallbackMethodListBuilder() {
         public Collection getAllMethods(EnhancedAnnotatedType typex) {
            return typex.getEnhancedMethods(PreDestroy.class);
         }

         protected void duplicateMethod(EnhancedAnnotatedMethod method) {
            throw UtilLogger.LOG.tooManyPreDestroyMethods(type);
         }

         protected EnhancedAnnotatedMethod processLevelResult(EnhancedAnnotatedMethod method) {
            BeanLogger.LOG.foundOnePreDestroyMethod(method, type);
            return method;
         }
      });
   }

   public static List getInitializerMethods(Bean declaringBean, EnhancedAnnotatedType type, BeanManagerImpl manager) {
      return (List)getMethods(type, new InitializerMethodListBuilder(type, declaringBean, manager));
   }

   public static Collection getObserverMethods(EnhancedAnnotatedType type) {
      return filterMethods(type.getEnhancedMethodsWithAnnotatedParameters(Observes.class));
   }

   public static Collection getAsyncObserverMethods(EnhancedAnnotatedType type) {
      return filterMethods(type.getEnhancedMethodsWithAnnotatedParameters(ObservesAsync.class));
   }

   public static Collection filterMethods(Collection methods) {
      return (Collection)methods.stream().filter((m) -> {
         return !m.getJavaMember().isBridge() && !m.getJavaMember().isSynthetic();
      }).collect(Collectors.toList());
   }

   public static List getInterceptorMethods(EnhancedAnnotatedType type, final InterceptionType interceptionType, final boolean targetClass) {
      return (List)getMethods(type, new MethodListBuilder() {
         List methodMetadata = null;

         public Collection getAllMethods(EnhancedAnnotatedType type) {
            return type.getEnhancedMethods(InterceptionTypeRegistry.getAnnotationClass(interceptionType));
         }

         public void levelStart(Class clazz) {
         }

         public void processMethod(EnhancedAnnotatedMethod method) {
            Method javaMethod = method.getJavaMember();
            if (InterceptorMetadataUtils.isInterceptorMethod(interceptionType, javaMethod, targetClass)) {
               if (this.methodMetadata == null) {
                  this.methodMetadata = new LinkedList();
               }

               if (System.getSecurityManager() == null) {
                  javaMethod.setAccessible(true);
               } else {
                  AccessController.doPrivileged(SetAccessibleAction.of(javaMethod));
               }

               this.methodMetadata.add(method.getJavaMember());
            }

         }

         public void levelFinish() {
         }

         public List create() {
            if (this.methodMetadata == null) {
               return Collections.emptyList();
            } else {
               Collections.reverse(this.methodMetadata);
               return ImmutableList.copyOf((Collection)this.methodMetadata);
            }
         }
      });
   }

   private static class InitializerMethodListBuilder implements MethodListBuilder {
      private final List result = new ArrayList();
      private ImmutableSet.Builder currentLevel = null;
      private final EnhancedAnnotatedType type;
      private final BeanManagerImpl manager;
      private final Bean declaringBean;

      public InitializerMethodListBuilder(EnhancedAnnotatedType type, Bean declaringBean, BeanManagerImpl manager) {
         this.type = type;
         this.manager = manager;
         this.declaringBean = declaringBean;
      }

      public Collection getAllMethods(EnhancedAnnotatedType type) {
         return type.getEnhancedMethods(Inject.class);
      }

      public void levelStart(Class clazz) {
         this.currentLevel = ImmutableSet.builder();
      }

      public void processMethod(EnhancedAnnotatedMethod method) {
         if (method.isAnnotationPresent(Inject.class)) {
            if (method.getAnnotation(Produces.class) != null) {
               throw UtilLogger.LOG.initializerCannotBeProducer(method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
            }

            if (method.getEnhancedParameters(Disposes.class).size() > 0) {
               throw UtilLogger.LOG.initializerCannotBeDisposalMethod(method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
            }

            if (method.getEnhancedParameters(Observes.class).size() > 0) {
               throw EventLogger.LOG.invalidInitializer(method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
            }

            if (method.isGeneric()) {
               throw UtilLogger.LOG.initializerMethodIsGeneric(method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
            }

            if (!method.isStatic()) {
               this.currentLevel.add(InjectionPointFactory.instance().createMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType.INITIALIZER, method, this.declaringBean, this.type.getJavaClass(), (Set)null, this.manager));
            }
         }

      }

      public void levelFinish() {
         this.result.add(this.currentLevel.build());
      }

      public List create() {
         Collections.reverse(this.result);
         return ImmutableList.copyOf((Collection)this.result);
      }
   }

   private abstract static class AbstractLifecycleEventCallbackMethodListBuilder implements MethodListBuilder {
      protected List result;
      protected EnhancedAnnotatedMethod foundMethod;

      private AbstractLifecycleEventCallbackMethodListBuilder() {
         this.result = new ArrayList();
         this.foundMethod = null;
      }

      public void levelStart(Class clazz) {
         this.foundMethod = null;
      }

      public void processMethod(EnhancedAnnotatedMethod method) {
         if (this.methodHasNoParameters(method)) {
            if (this.foundMethod != null) {
               this.duplicateMethod(method);
            }

            this.foundMethod = method;
         }

      }

      private boolean methodHasNoParameters(EnhancedAnnotatedMethod method) {
         return method.getParameterTypesAsArray().length == 0;
      }

      public void levelFinish() {
         if (this.foundMethod != null) {
            this.result.add(this.processLevelResult(this.foundMethod).slim());
         }

      }

      public List create() {
         Collections.reverse(this.result);
         return ImmutableList.copyOf((Collection)this.result);
      }

      protected abstract void duplicateMethod(EnhancedAnnotatedMethod var1);

      protected abstract EnhancedAnnotatedMethod processLevelResult(EnhancedAnnotatedMethod var1);

      // $FF: synthetic method
      AbstractLifecycleEventCallbackMethodListBuilder(Object x0) {
         this();
      }
   }

   private interface MethodListBuilder {
      Collection getAllMethods(EnhancedAnnotatedType var1);

      void levelStart(Class var1);

      void processMethod(EnhancedAnnotatedMethod var1);

      void levelFinish();

      Object create();
   }
}
