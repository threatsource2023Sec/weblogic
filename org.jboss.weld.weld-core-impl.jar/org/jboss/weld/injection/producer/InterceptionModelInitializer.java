package org.jboss.weld.injection.producer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Interceptor;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.InterceptorBinding;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.interceptor.builder.InterceptionModelBuilder;
import org.jboss.weld.interceptor.builder.InterceptorsApiAbstraction;
import org.jboss.weld.interceptor.reader.InterceptorMetadataReader;
import org.jboss.weld.interceptor.reader.TargetClassInterceptorMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Interceptors;
import org.jboss.weld.util.collections.Multimap;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptionModelInitializer {
   private final BeanManagerImpl manager;
   private final InterceptorMetadataReader reader;
   private final EnhancedAnnotatedType annotatedType;
   private final Set stereotypes;
   private final EnhancedAnnotatedConstructor constructor;
   private final InterceptorsApiAbstraction interceptorsApi;
   private final Class timeoutAnnotation;
   private List businessMethods;
   private final InterceptionModelBuilder builder;
   private boolean hasSerializationOrInvocationInterceptorMethods;
   private final WeldConfiguration configuration;

   public static InterceptionModelInitializer of(BeanManagerImpl manager, EnhancedAnnotatedType annotatedType, Bean bean) {
      return new InterceptionModelInitializer(manager, annotatedType, Beans.getBeanConstructorStrict(annotatedType), bean);
   }

   public InterceptionModelInitializer(BeanManagerImpl manager, EnhancedAnnotatedType annotatedType, EnhancedAnnotatedConstructor constructor, Bean bean) {
      this.constructor = constructor;
      this.manager = manager;
      this.reader = manager.getInterceptorMetadataReader();
      this.annotatedType = annotatedType;
      this.builder = new InterceptionModelBuilder();
      if (bean == null) {
         this.stereotypes = Collections.emptySet();
      } else {
         this.stereotypes = bean.getStereotypes();
      }

      this.interceptorsApi = (InterceptorsApiAbstraction)manager.getServices().get(InterceptorsApiAbstraction.class);
      this.timeoutAnnotation = ((EjbSupport)manager.getServices().get(EjbSupport.class)).getTimeoutAnnotation();
      this.configuration = (WeldConfiguration)manager.getServices().get(WeldConfiguration.class);
   }

   public void init() {
      this.initTargetClassInterceptors();
      this.businessMethods = Beans.getInterceptableMethods(this.annotatedType);
      this.initEjbInterceptors();
      this.initCdiInterceptors();
      InterceptionModel interceptionModel = this.builder.build();
      if (interceptionModel.getAllInterceptors().size() > 0 || this.hasSerializationOrInvocationInterceptorMethods) {
         if (this.annotatedType.isFinal()) {
            throw BeanLogger.LOG.finalBeanClassWithInterceptorsNotAllowed(this.annotatedType.getJavaClass());
         }

         if (this.constructor != null && Reflections.isPrivate(this.constructor.getJavaMember())) {
            throw new DeploymentException(ValidatorLogger.LOG.notProxyablePrivateConstructor(this.annotatedType.getJavaClass().getName(), this.constructor, this.annotatedType.getJavaClass()));
         }

         this.manager.getInterceptorModelRegistry().put(this.annotatedType.slim(), interceptionModel);
      }

   }

   private void initTargetClassInterceptors() {
      if (!Beans.isInterceptor(this.annotatedType)) {
         TargetClassInterceptorMetadata interceptorClassMetadata = this.reader.getTargetClassInterceptorMetadata(this.annotatedType);
         this.builder.setTargetClassInterceptorMetadata(interceptorClassMetadata);
         this.hasSerializationOrInvocationInterceptorMethods = interceptorClassMetadata.isEligible(InterceptionType.AROUND_INVOKE) || interceptorClassMetadata.isEligible(InterceptionType.AROUND_TIMEOUT) || interceptorClassMetadata.isEligible(InterceptionType.PRE_PASSIVATE) || interceptorClassMetadata.isEligible(InterceptionType.POST_ACTIVATE);
      } else {
         this.hasSerializationOrInvocationInterceptorMethods = false;
      }

   }

   private void initCdiInterceptors() {
      Multimap classBindingAnnotations = this.getClassInterceptorBindings();
      Set bindings = classBindingAnnotations.uniqueValues();
      this.builder.setClassInterceptorBindings(bindings);
      this.initCdiLifecycleInterceptors(bindings);
      if (this.constructor != null) {
         this.initCdiConstructorInterceptors(classBindingAnnotations);
      }

      this.initCdiBusinessMethodInterceptors(classBindingAnnotations);
   }

   private Multimap getClassInterceptorBindings() {
      return Interceptors.mergeBeanInterceptorBindings(this.manager, this.annotatedType, this.stereotypes);
   }

   private void initCdiLifecycleInterceptors(Set qualifiers) {
      if (!qualifiers.isEmpty()) {
         this.initLifeCycleInterceptor(javax.enterprise.inject.spi.InterceptionType.POST_CONSTRUCT, (AnnotatedConstructor)null, qualifiers);
         this.initLifeCycleInterceptor(javax.enterprise.inject.spi.InterceptionType.PRE_DESTROY, (AnnotatedConstructor)null, qualifiers);
         this.initLifeCycleInterceptor(javax.enterprise.inject.spi.InterceptionType.PRE_PASSIVATE, (AnnotatedConstructor)null, qualifiers);
         this.initLifeCycleInterceptor(javax.enterprise.inject.spi.InterceptionType.POST_ACTIVATE, (AnnotatedConstructor)null, qualifiers);
      }
   }

   private void initLifeCycleInterceptor(javax.enterprise.inject.spi.InterceptionType interceptionType, AnnotatedConstructor constructor, Set annotations) {
      List resolvedInterceptors = this.manager.resolveInterceptors(interceptionType, (Collection)annotations);
      if (!resolvedInterceptors.isEmpty()) {
         if (constructor != null) {
            this.builder.interceptGlobal(interceptionType, constructor.getJavaMember(), this.asInterceptorMetadata(resolvedInterceptors), annotations);
         } else {
            this.builder.interceptGlobal(interceptionType, (Constructor)null, this.asInterceptorMetadata(resolvedInterceptors), (Set)null);
         }
      }

   }

   private void initCdiBusinessMethodInterceptors(Multimap classBindingAnnotations) {
      Iterator var2 = this.businessMethods.iterator();

      while(var2.hasNext()) {
         EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var2.next();
         this.initCdiBusinessMethodInterceptor(method, this.getMemberBindingAnnotations(classBindingAnnotations, method.getMetaAnnotations(InterceptorBinding.class)));
      }

   }

   private void initCdiBusinessMethodInterceptor(AnnotatedMethod method, Set methodBindingAnnotations) {
      if (methodBindingAnnotations.size() != 0) {
         this.initInterceptor(javax.enterprise.inject.spi.InterceptionType.AROUND_INVOKE, method, methodBindingAnnotations);
         this.initInterceptor(javax.enterprise.inject.spi.InterceptionType.AROUND_TIMEOUT, method, methodBindingAnnotations);
      }
   }

   private void initInterceptor(javax.enterprise.inject.spi.InterceptionType interceptionType, AnnotatedMethod method, Set methodBindingAnnotations) {
      List methodBoundInterceptors = this.manager.resolveInterceptors(interceptionType, (Collection)methodBindingAnnotations);
      if (methodBoundInterceptors != null && methodBoundInterceptors.size() > 0) {
         Method javaMethod = method.getJavaMember();
         if (Reflections.isFinal((Member)javaMethod)) {
            if (!this.configuration.isFinalMethodIgnored(javaMethod.getDeclaringClass().getName())) {
               throw BeanLogger.LOG.finalInterceptedBeanMethodNotAllowed(method, ((Interceptor)methodBoundInterceptors.get(0)).getBeanClass().getName());
            }

            BeanLogger.LOG.finalMethodNotIntercepted(javaMethod, ((Interceptor)methodBoundInterceptors.get(0)).getBeanClass().getName());
         } else {
            this.builder.interceptMethod(interceptionType, javaMethod, this.asInterceptorMetadata(methodBoundInterceptors), methodBindingAnnotations);
         }
      }

   }

   private void initCdiConstructorInterceptors(Multimap classBindingAnnotations) {
      Set constructorBindings = this.getMemberBindingAnnotations(classBindingAnnotations, this.constructor.getMetaAnnotations(InterceptorBinding.class));
      if (!constructorBindings.isEmpty()) {
         this.initLifeCycleInterceptor(javax.enterprise.inject.spi.InterceptionType.AROUND_CONSTRUCT, this.constructor, constructorBindings);
      }
   }

   private Set getMemberBindingAnnotations(Multimap classBindingAnnotations, Set memberAnnotations) {
      Set methodBindingAnnotations = Interceptors.flattenInterceptorBindings((EnhancedAnnotatedType)null, this.manager, Interceptors.filterInterceptorBindings(this.manager, memberAnnotations), true, true);
      return this.mergeMemberInterceptorBindings(classBindingAnnotations, methodBindingAnnotations).uniqueValues();
   }

   private void initEjbInterceptors() {
      this.initClassDeclaredEjbInterceptors();
      if (this.constructor != null) {
         this.initConstructorDeclaredEjbInterceptors();
      }

      Iterator var1 = this.businessMethods.iterator();

      while(var1.hasNext()) {
         AnnotatedMethod method = (AnnotatedMethod)var1.next();
         this.initMethodDeclaredEjbInterceptors(method);
      }

   }

   private void initClassDeclaredEjbInterceptors() {
      Class[] classDeclaredInterceptors = this.interceptorsApi.extractInterceptorClasses(this.annotatedType);
      boolean excludeClassLevelAroundConstructInterceptors = this.constructor != null && this.constructor.isAnnotationPresent(ExcludeClassInterceptors.class);
      if (classDeclaredInterceptors != null) {
         Class[] var3 = classDeclaredInterceptors;
         int var4 = classDeclaredInterceptors.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class clazz = var3[var5];
            InterceptorClassMetadata interceptor = this.reader.getPlainInterceptorMetadata(clazz);
            javax.enterprise.inject.spi.InterceptionType[] var8 = javax.enterprise.inject.spi.InterceptionType.values();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               javax.enterprise.inject.spi.InterceptionType interceptionType = var8[var10];
               if ((!excludeClassLevelAroundConstructInterceptors || !interceptionType.equals(javax.enterprise.inject.spi.InterceptionType.AROUND_CONSTRUCT)) && interceptor.isEligible(InterceptionType.valueOf(interceptionType))) {
                  this.builder.interceptGlobal(interceptionType, (Constructor)null, Collections.singleton(interceptor), (Set)null);
               }
            }
         }
      }

   }

   public void initConstructorDeclaredEjbInterceptors() {
      Class[] constructorDeclaredInterceptors = this.interceptorsApi.extractInterceptorClasses(this.constructor);
      if (constructorDeclaredInterceptors != null) {
         Class[] var2 = constructorDeclaredInterceptors;
         int var3 = constructorDeclaredInterceptors.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class clazz = var2[var4];
            this.builder.interceptGlobal(javax.enterprise.inject.spi.InterceptionType.AROUND_CONSTRUCT, (Constructor)null, Collections.singleton(this.reader.getPlainInterceptorMetadata(clazz)), (Set)null);
         }
      }

   }

   private void initMethodDeclaredEjbInterceptors(AnnotatedMethod method) {
      Method javaMethod = method.getJavaMember();
      boolean excludeClassInterceptors = method.isAnnotationPresent(this.interceptorsApi.getExcludeClassInterceptorsAnnotationClass());
      if (excludeClassInterceptors) {
         this.builder.addMethodIgnoringGlobalInterceptors(javaMethod);
      }

      Class[] methodDeclaredInterceptors = this.interceptorsApi.extractInterceptorClasses(method);
      if (methodDeclaredInterceptors != null && methodDeclaredInterceptors.length > 0) {
         if (Reflections.isFinal((Member)method.getJavaMember())) {
            throw new DeploymentException(BeanLogger.LOG.finalInterceptedBeanMethodNotAllowed(method, methodDeclaredInterceptors[0].getName()));
         }

         javax.enterprise.inject.spi.InterceptionType interceptionType = this.isTimeoutAnnotationPresentOn(method) ? javax.enterprise.inject.spi.InterceptionType.AROUND_TIMEOUT : javax.enterprise.inject.spi.InterceptionType.AROUND_INVOKE;
         this.builder.interceptMethod(interceptionType, javaMethod, this.getMethodDeclaredInterceptorMetadatas(methodDeclaredInterceptors), (Set)null);
      }

   }

   private List getMethodDeclaredInterceptorMetadatas(Class[] methodDeclaredInterceptors) {
      List list = new LinkedList();
      Class[] var3 = methodDeclaredInterceptors;
      int var4 = methodDeclaredInterceptors.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class clazz = var3[var5];
         list.add(this.reader.getPlainInterceptorMetadata(clazz));
      }

      return list;
   }

   private boolean isTimeoutAnnotationPresentOn(AnnotatedMethod method) {
      return this.timeoutAnnotation != null && method.isAnnotationPresent(this.timeoutAnnotation);
   }

   protected Multimap mergeMemberInterceptorBindings(Multimap beanBindings, Set methodBindingAnnotations) {
      Multimap mergedBeanBindings = SetMultimap.newSetMultimap(beanBindings);
      Multimap methodBindings = SetMultimap.newSetMultimap();
      Iterator var5 = methodBindingAnnotations.iterator();

      while(var5.hasNext()) {
         Annotation methodBinding = (Annotation)var5.next();
         methodBindings.put(methodBinding.annotationType(), methodBinding);
      }

      var5 = methodBindings.keySet().iterator();

      while(var5.hasNext()) {
         Class key = (Class)var5.next();
         mergedBeanBindings.replaceValues(key, methodBindings.get(key));
      }

      return mergedBeanBindings;
   }

   private List asInterceptorMetadata(List interceptors) {
      return (List)interceptors.stream().map((i) -> {
         return this.reader.getCdiInterceptorMetadata(i);
      }).collect(Collectors.toList());
   }

   public String toString() {
      return "InterceptionModelInitializer for " + this.annotatedType.getJavaClass();
   }
}
