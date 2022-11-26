package org.jboss.weld.util;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.decorator.Decorator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NormalScope;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.CreationException;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Typed;
import javax.enterprise.inject.Vetoed;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.AbstractBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.ForwardingBean;
import org.jboss.weld.bean.InterceptorImpl;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.enablement.ModuleEnablement;
import org.jboss.weld.injection.FieldInjectionPoint;
import org.jboss.weld.injection.MethodInjectionPoint;
import org.jboss.weld.injection.ResourceInjection;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.interceptor.util.InterceptionTypeRegistry;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MergedStereotypes;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.resolution.QualifierInstance;
import org.jboss.weld.resources.spi.ClassFileInfo;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class Beans {
   private Beans() {
   }

   public static boolean isPassivatingScope(Bean bean, BeanManagerImpl manager) {
      return bean == null ? false : ((MetaAnnotationStore)manager.getServices().get(MetaAnnotationStore.class)).getScopeModel(bean.getScope()).isPassivating();
   }

   public static boolean isPassivationCapableBean(Bean bean) {
      return bean instanceof RIBean ? ((RIBean)bean).isPassivationCapableBean() : bean instanceof PassivationCapable;
   }

   public static boolean isPassivationCapableDependency(Bean bean) {
      return bean instanceof RIBean ? ((RIBean)bean).isPassivationCapableDependency() : bean instanceof PassivationCapable;
   }

   public static boolean isBeanProxyable(Bean bean, BeanManagerImpl manager) {
      return bean instanceof RIBean ? ((RIBean)bean).isProxyable() : Proxies.isTypesProxyable((Iterable)bean.getTypes(), manager.getServices());
   }

   public static List getInterceptableMethods(EnhancedAnnotatedType type) {
      List annotatedMethods = new ArrayList();
      Iterator var2 = type.getEnhancedMethods().iterator();

      while(var2.hasNext()) {
         EnhancedAnnotatedMethod annotatedMethod = (EnhancedAnnotatedMethod)var2.next();
         boolean businessMethod = !annotatedMethod.isStatic() && !annotatedMethod.isAnnotationPresent(Inject.class) && !annotatedMethod.getJavaMember().isBridge();
         if (businessMethod && !isInterceptorMethod(annotatedMethod)) {
            annotatedMethods.add(annotatedMethod);
         }
      }

      return annotatedMethods;
   }

   private static boolean isInterceptorMethod(AnnotatedMethod annotatedMethod) {
      Iterator var1 = InterceptionTypeRegistry.getSupportedInterceptionTypes().iterator();

      InterceptionType interceptionType;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         interceptionType = (InterceptionType)var1.next();
      } while(!annotatedMethod.isAnnotationPresent(InterceptionTypeRegistry.getAnnotationClass(interceptionType)));

      return true;
   }

   public static boolean containsAllQualifiers(Set requiredQualifiers, Set qualifiers) {
      return qualifiers.containsAll(requiredQualifiers);
   }

   public static boolean containsAllInterceptionBindings(Set expectedBindings, Set existingBindings, BeanManagerImpl manager) {
      Set expected = manager.extractInterceptorBindingsForQualifierInstance(QualifierInstance.of(expectedBindings, (MetaAnnotationStore)manager.getServices().get(MetaAnnotationStore.class)));
      return expected.isEmpty() ? false : manager.extractInterceptorBindingsForQualifierInstance(existingBindings).containsAll(expected);
   }

   public static Set removeDisabledBeans(Set beans, BeanManagerImpl beanManager) {
      if (beans.isEmpty()) {
         return beans;
      } else {
         Iterator iterator = beans.iterator();

         while(iterator.hasNext()) {
            if (!isBeanEnabled((Bean)iterator.next(), beanManager.getEnabled())) {
               iterator.remove();
            }
         }

         return beans;
      }
   }

   public static boolean isBeanEnabled(Bean bean, ModuleEnablement enabled) {
      if (bean.isAlternative()) {
         if (enabled.isEnabledAlternativeClass(bean.getBeanClass())) {
            return true;
         } else {
            Iterator var4 = bean.getStereotypes().iterator();

            Class stereotype;
            do {
               if (!var4.hasNext()) {
                  return false;
               }

               stereotype = (Class)var4.next();
            } while(!enabled.isEnabledAlternativeStereotype(stereotype));

            return true;
         }
      } else if (bean instanceof AbstractProducerBean) {
         AbstractProducerBean receiverBean = (AbstractProducerBean)bean;
         return isBeanEnabled(receiverBean.getDeclaringBean(), enabled);
      } else if (bean instanceof DecoratorImpl) {
         return enabled.isDecoratorEnabled(bean.getBeanClass());
      } else {
         return bean instanceof InterceptorImpl ? enabled.isInterceptorEnabled(bean.getBeanClass()) : true;
      }
   }

   public static boolean isAlternative(EnhancedAnnotated annotated, MergedStereotypes mergedStereotypes) {
      return annotated.isAnnotationPresent(Alternative.class) || mergedStereotypes.isAlternative();
   }

   public static EnhancedAnnotatedConstructor getBeanConstructorStrict(EnhancedAnnotatedType type) {
      EnhancedAnnotatedConstructor constructor = getBeanConstructor(type);
      if (constructor == null) {
         throw UtilLogger.LOG.unableToFindConstructor(type);
      } else {
         return constructor;
      }
   }

   public static EnhancedAnnotatedConstructor getBeanConstructor(EnhancedAnnotatedType type) {
      Collection initializerAnnotatedConstructors = type.getEnhancedConstructors(Inject.class);
      BeanLogger.LOG.foundInjectableConstructors(initializerAnnotatedConstructors, type);
      EnhancedAnnotatedConstructor constructor = null;
      if (initializerAnnotatedConstructors.size() > 1) {
         throw UtilLogger.LOG.ambiguousConstructor(type, initializerAnnotatedConstructors);
      } else {
         if (initializerAnnotatedConstructors.size() == 1) {
            constructor = (EnhancedAnnotatedConstructor)initializerAnnotatedConstructors.iterator().next();
            BeanLogger.LOG.foundOneInjectableConstructor(constructor, type);
         } else if (type.getNoArgsEnhancedConstructor() != null) {
            constructor = type.getNoArgsEnhancedConstructor();
            BeanLogger.LOG.foundDefaultConstructor(constructor, type);
         }

         if (constructor != null) {
            if (!constructor.getEnhancedParameters(Disposes.class).isEmpty()) {
               throw BeanLogger.LOG.parameterAnnotationNotAllowedOnConstructor("@Disposes", constructor, Formats.formatAsStackTraceElement((Member)constructor.getJavaMember()));
            }

            if (!constructor.getEnhancedParameters(Observes.class).isEmpty()) {
               throw BeanLogger.LOG.parameterAnnotationNotAllowedOnConstructor("@Observes", constructor, Formats.formatAsStackTraceElement((Member)constructor.getJavaMember()));
            }

            if (!constructor.getEnhancedParameters(ObservesAsync.class).isEmpty()) {
               throw BeanLogger.LOG.parameterAnnotationNotAllowedOnConstructor("@ObservesAsync", constructor, Formats.formatAsStackTraceElement((Member)constructor.getJavaMember()));
            }
         }

         return constructor;
      }
   }

   public static void injectEEFields(Iterable resourceInjectionsHierarchy, Object beanInstance, CreationalContext ctx) {
      Iterator var3 = resourceInjectionsHierarchy.iterator();

      while(var3.hasNext()) {
         Set resourceInjections = (Set)var3.next();
         Iterator var5 = resourceInjections.iterator();

         while(var5.hasNext()) {
            ResourceInjection resourceInjection = (ResourceInjection)var5.next();
            resourceInjection.injectResourceReference(beanInstance, ctx);
         }
      }

   }

   public static Type getDeclaredBeanType(Class clazz) {
      Type[] actualTypeArguments = Reflections.getActualTypeArguments(clazz);
      return actualTypeArguments.length == 1 ? actualTypeArguments[0] : null;
   }

   public static void injectBoundFields(Object instance, CreationalContext creationalContext, BeanManagerImpl manager, Iterable injectableFields) {
      Iterator var4 = injectableFields.iterator();

      while(var4.hasNext()) {
         FieldInjectionPoint injectableField = (FieldInjectionPoint)var4.next();
         injectableField.inject(instance, manager, creationalContext);
      }

   }

   public static void injectFieldsAndInitializers(Object instance, CreationalContext ctx, BeanManagerImpl beanManager, List injectableFields, List initializerMethods) {
      if (injectableFields.size() != initializerMethods.size()) {
         throw UtilLogger.LOG.invalidQuantityInjectableFieldsAndInitializerMethods(injectableFields, initializerMethods);
      } else {
         for(int i = 0; i < injectableFields.size(); ++i) {
            injectBoundFields(instance, ctx, beanManager, (Iterable)injectableFields.get(i));
            callInitializers(instance, ctx, beanManager, (Iterable)initializerMethods.get(i));
         }

      }
   }

   public static void callInitializers(Object instance, CreationalContext creationalContext, BeanManagerImpl manager, Iterable initializerMethods) {
      Iterator var4 = initializerMethods.iterator();

      while(var4.hasNext()) {
         MethodInjectionPoint initializer = (MethodInjectionPoint)var4.next();
         initializer.invoke(instance, (Object)null, manager, creationalContext, CreationException.class);
      }

   }

   public static boolean isInterceptor(AnnotatedType annotatedItem) {
      return annotatedItem.isAnnotationPresent(Interceptor.class);
   }

   public static boolean isDecorator(EnhancedAnnotatedType annotatedItem) {
      return annotatedItem.isAnnotationPresent(Decorator.class);
   }

   public static Set mergeInQualifiers(BeanManagerImpl manager, Collection qualifiers, Annotation[] newQualifiers) {
      Set result = new HashSet();
      if (qualifiers != null && !qualifiers.isEmpty()) {
         result.addAll(qualifiers);
      }

      if (newQualifiers != null && newQualifiers.length > 0) {
         MetaAnnotationStore store = (MetaAnnotationStore)manager.getServices().get(MetaAnnotationStore.class);
         Set checkedNewQualifiers = new HashSet();
         Annotation[] var6 = newQualifiers;
         int var7 = newQualifiers.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Annotation qualifier = var6[var8];
            if (!store.getBindingTypeModel(qualifier.annotationType()).isValid()) {
               throw UtilLogger.LOG.annotationNotQualifier(qualifier);
            }

            Class annotationType = qualifier.annotationType();
            if (!annotationType.isAnnotationPresent(Repeatable.class)) {
               Iterator var11 = checkedNewQualifiers.iterator();

               while(var11.hasNext()) {
                  Annotation annotation = (Annotation)var11.next();
                  if (annotationType.equals(annotation.annotationType())) {
                     throw UtilLogger.LOG.redundantQualifier(qualifier, Arrays.toString(newQualifiers));
                  }
               }
            }

            checkedNewQualifiers.add(qualifier);
         }

         result.addAll(checkedNewQualifiers);
      }

      return result;
   }

   public static Set getTypes(EnhancedAnnotated annotated) {
      if (!annotated.getJavaClass().isArray() && !annotated.getJavaClass().isPrimitive()) {
         if (annotated.isAnnotationPresent(Typed.class)) {
            return ImmutableSet.builder().addAll((Iterable)getTypedTypes(Reflections.buildTypeMap(annotated.getTypeClosure()), annotated.getJavaClass(), (Typed)annotated.getAnnotation(Typed.class))).build();
         } else {
            return annotated.getJavaClass().isInterface() ? getLegalBeanTypes(annotated.getTypeClosure(), annotated, Object.class) : getLegalBeanTypes(annotated.getTypeClosure(), annotated);
         }
      } else {
         return ImmutableSet.builder().addAll((Object[])(annotated.getBaseType(), Object.class)).build();
      }
   }

   public static Set getTypedTypes(Map typeClosure, Class rawType, Typed typed) {
      Set types = new HashSet();
      Class[] var4 = typed.value();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class specifiedClass = var4[var6];
         Type tmp = (Type)typeClosure.get(specifiedClass);
         if (tmp == null) {
            throw BeanLogger.LOG.typedClassNotInHierarchy(specifiedClass.getName(), rawType, Formats.formatTypes(typeClosure.values()));
         }

         types.add(tmp);
      }

      types.add(Object.class);
      return types;
   }

   public static boolean isTypeManagedBeanOrDecoratorOrInterceptor(AnnotatedType annotatedType) {
      Class javaClass = annotatedType.getJavaClass();
      return !javaClass.isEnum() && !Extension.class.isAssignableFrom(javaClass) && Reflections.isTopLevelOrStaticNestedClass(javaClass) && !Reflections.isParameterizedTypeWithWildcard(javaClass) && hasSimpleCdiConstructor(annotatedType);
   }

   public static boolean isTypeManagedBeanOrDecoratorOrInterceptor(ClassFileInfo classFileInfo, boolean checkTypeModifiers) {
      boolean isTypeManagedBean = (classFileInfo.getModifiers() & 16384) == 0 && !classFileInfo.isAssignableTo(Extension.class) && classFileInfo.hasCdiConstructor() && (!Modifier.isAbstract(classFileInfo.getModifiers()) || classFileInfo.isAnnotationDeclared(Decorator.class));
      if (!checkTypeModifiers) {
         return isTypeManagedBean;
      } else {
         return isTypeManagedBean && (classFileInfo.isTopLevelClass() || Modifier.isStatic(classFileInfo.getModifiers()));
      }
   }

   public static boolean isDecoratorDeclaringInAppropriateConstructor(ClassFileInfo classFileInfo) {
      return !classFileInfo.hasCdiConstructor() && classFileInfo.isAnnotationDeclared(Decorator.class);
   }

   public static boolean isDecoratorDeclaringInAppropriateConstructor(AnnotatedType annotatedType) {
      return !hasSimpleCdiConstructor(annotatedType) && annotatedType.isAnnotationPresent(Decorator.class);
   }

   public static boolean hasSimpleCdiConstructor(AnnotatedType type) {
      Iterator var1 = type.getConstructors().iterator();

      AnnotatedConstructor constructor;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         constructor = (AnnotatedConstructor)var1.next();
         if (constructor.getParameters().isEmpty()) {
            return true;
         }
      } while(!constructor.isAnnotationPresent(Inject.class));

      return true;
   }

   public static boolean isVetoed(Class javaClass) {
      return javaClass.isAnnotationPresent(Vetoed.class) ? true : isPackageVetoed(javaClass.getPackage());
   }

   public static boolean isVetoed(AnnotatedType type) {
      return type.isAnnotationPresent(Vetoed.class) ? true : isPackageVetoed(type.getJavaClass().getPackage());
   }

   private static boolean isPackageVetoed(Package pkg) {
      return pkg != null && pkg.isAnnotationPresent(Vetoed.class);
   }

   public static String createBeanAttributesId(BeanAttributes attributes) {
      StringBuilder builder = new StringBuilder();
      builder.append(attributes.getName());
      builder.append(",");
      builder.append(attributes.getScope().getName());
      builder.append(",");
      builder.append(attributes.isAlternative());
      builder.append(AnnotatedTypes.createAnnotationCollectionId(attributes.getQualifiers()));
      builder.append(createTypeCollectionId(attributes.getStereotypes()));
      builder.append(createTypeCollectionId(attributes.getTypes()));
      return builder.toString();
   }

   public static String createTypeCollectionId(Collection types) {
      StringBuilder builder = new StringBuilder();
      List sortedTypes = new ArrayList(types);
      Collections.sort(sortedTypes, Beans.TypeComparator.INSTANCE);
      builder.append("[");
      Iterator iterator = sortedTypes.iterator();

      while(iterator.hasNext()) {
         builder.append(createTypeId((Type)iterator.next()));
         if (iterator.hasNext()) {
            builder.append(",");
         }
      }

      builder.append("]");
      return builder.toString();
   }

   private static String createTypeId(Type type) {
      if (type instanceof Class) {
         return ((Class)Reflections.cast(type)).getName();
      } else if (type instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)type;
         StringBuilder builder = new StringBuilder();
         builder.append(createTypeId(parameterizedType.getRawType()));
         builder.append("<");

         for(int i = 0; i < parameterizedType.getActualTypeArguments().length; ++i) {
            builder.append(createTypeId(parameterizedType.getActualTypeArguments()[i]));
            if (i != parameterizedType.getActualTypeArguments().length - 1) {
               builder.append(",");
            }
         }

         builder.append(">");
         return builder.toString();
      } else if (type instanceof TypeVariable) {
         return ((TypeVariable)Reflections.cast(type)).getName();
      } else if (type instanceof GenericArrayType) {
         return createTypeId(((GenericArrayType)Reflections.cast(type)).getGenericComponentType());
      } else {
         throw new IllegalArgumentException("Unknown type " + type);
      }
   }

   public static EnhancedAnnotated checkEnhancedAnnotatedAvailable(EnhancedAnnotated enhancedAnnotated) {
      if (enhancedAnnotated == null) {
         throw new IllegalStateException("Enhanced metadata should not be used at runtime.");
      } else {
         return enhancedAnnotated;
      }
   }

   public static boolean hasBuiltinScope(Bean bean) {
      return RequestScoped.class.equals(bean.getScope()) || SessionScoped.class.equals(bean.getScope()) || ApplicationScoped.class.equals(bean.getScope()) || ConversationScoped.class.equals(bean.getScope()) || Dependent.class.equals(bean.getScope());
   }

   public static Class getBeanDefiningAnnotationScope(AnnotatedType annotatedType) {
      Iterator var1 = annotatedType.getAnnotations().iterator();

      Annotation annotation;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         annotation = (Annotation)var1.next();
      } while(!annotation.annotationType().isAnnotationPresent(NormalScope.class) && !annotation.annotationType().equals(Dependent.class));

      return annotation.annotationType();
   }

   public static Set getLegalBeanTypes(Set types, Object baseType, Type... additionalTypes) {
      if (additionalTypes != null && additionalTypes.length > 0) {
         return omitIllegalBeanTypes(types, baseType).addAll((Object[])additionalTypes).build();
      } else {
         Iterator var3 = types.iterator();

         Type type;
         do {
            if (!var3.hasNext()) {
               return types;
            }

            type = (Type)var3.next();
         } while(!Types.isIllegalBeanType(type));

         return omitIllegalBeanTypes(types, baseType).build();
      }
   }

   static ImmutableSet.Builder omitIllegalBeanTypes(Set types, Object baseType) {
      ImmutableSet.Builder builder = ImmutableSet.builder();
      Iterator var3 = types.iterator();

      while(var3.hasNext()) {
         Type type = (Type)var3.next();
         if (Types.isIllegalBeanType(type)) {
            MetadataLogger.LOG.illegalBeanTypeIgnored(type, baseType);
         } else {
            builder.add(type);
         }
      }

      return builder;
   }

   public static BeanIdentifier getIdentifier(Contextual contextual, ContextualStore contextualStore) {
      return getIdentifier(contextual, contextualStore, (ServiceRegistry)null);
   }

   public static BeanIdentifier getIdentifier(Contextual contextual, ServiceRegistry serviceRegistry) {
      return getIdentifier(contextual, (ContextualStore)null, serviceRegistry);
   }

   public static boolean shouldIgnoreFinalMethods(Bean bean) {
      if (bean instanceof AbstractBean) {
         AbstractBean abstractBean = (AbstractBean)bean;
         return abstractBean.isIgnoreFinalMethods();
      } else {
         return false;
      }
   }

   public static Bean unwrap(Bean bean) {
      if (bean instanceof ForwardingBean) {
         ForwardingBean forwarding = (ForwardingBean)bean;
         return forwarding.delegate();
      } else {
         return bean;
      }
   }

   private static BeanIdentifier getIdentifier(Contextual contextual, ContextualStore contextualStore, ServiceRegistry serviceRegistry) {
      if (contextual instanceof RIBean) {
         return ((RIBean)contextual).getIdentifier();
      } else {
         if (contextualStore == null) {
            contextualStore = (ContextualStore)serviceRegistry.get(ContextualStore.class);
         }

         return contextualStore.putIfAbsent(contextual);
      }
   }

   private static class TypeComparator implements Comparator, Serializable {
      private static final long serialVersionUID = -2162735176891985078L;
      private static final TypeComparator INSTANCE = new TypeComparator();

      public int compare(Type o1, Type o2) {
         return Beans.createTypeId(o1).compareTo(Beans.createTypeId(o2));
      }
   }
}
