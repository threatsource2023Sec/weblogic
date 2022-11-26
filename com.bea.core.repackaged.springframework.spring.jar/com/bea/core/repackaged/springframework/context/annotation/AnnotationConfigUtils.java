package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.context.event.DefaultEventListenerFactory;
import com.bea.core.repackaged.springframework.context.event.EventListenerMethodProcessor;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.core.type.AnnotatedTypeMetadata;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class AnnotationConfigUtils {
   public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME = "com.bea.core.repackaged.springframework.context.annotation.internalConfigurationAnnotationProcessor";
   public static final String CONFIGURATION_BEAN_NAME_GENERATOR = "com.bea.core.repackaged.springframework.context.annotation.internalConfigurationBeanNameGenerator";
   public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "com.bea.core.repackaged.springframework.context.annotation.internalAutowiredAnnotationProcessor";
   /** @deprecated */
   @Deprecated
   public static final String REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "com.bea.core.repackaged.springframework.context.annotation.internalRequiredAnnotationProcessor";
   public static final String COMMON_ANNOTATION_PROCESSOR_BEAN_NAME = "com.bea.core.repackaged.springframework.context.annotation.internalCommonAnnotationProcessor";
   public static final String PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME = "com.bea.core.repackaged.springframework.context.annotation.internalPersistenceAnnotationProcessor";
   private static final String PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME = "com.bea.core.repackaged.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor";
   public static final String EVENT_LISTENER_PROCESSOR_BEAN_NAME = "com.bea.core.repackaged.springframework.context.event.internalEventListenerProcessor";
   public static final String EVENT_LISTENER_FACTORY_BEAN_NAME = "com.bea.core.repackaged.springframework.context.event.internalEventListenerFactory";
   private static final boolean jsr250Present;
   private static final boolean jpaPresent;

   public static void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
      registerAnnotationConfigProcessors(registry, (Object)null);
   }

   public static Set registerAnnotationConfigProcessors(BeanDefinitionRegistry registry, @Nullable Object source) {
      DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
      if (beanFactory != null) {
         if (!(beanFactory.getDependencyComparator() instanceof AnnotationAwareOrderComparator)) {
            beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
         }

         if (!(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver)) {
            beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
         }
      }

      Set beanDefs = new LinkedHashSet(8);
      RootBeanDefinition def;
      if (!registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.annotation.internalConfigurationAnnotationProcessor")) {
         def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
         def.setSource(source);
         beanDefs.add(registerPostProcessor(registry, def, "com.bea.core.repackaged.springframework.context.annotation.internalConfigurationAnnotationProcessor"));
      }

      if (!registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.annotation.internalAutowiredAnnotationProcessor")) {
         def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
         def.setSource(source);
         beanDefs.add(registerPostProcessor(registry, def, "com.bea.core.repackaged.springframework.context.annotation.internalAutowiredAnnotationProcessor"));
      }

      if (jsr250Present && !registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.annotation.internalCommonAnnotationProcessor")) {
         def = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
         def.setSource(source);
         beanDefs.add(registerPostProcessor(registry, def, "com.bea.core.repackaged.springframework.context.annotation.internalCommonAnnotationProcessor"));
      }

      if (jpaPresent && !registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.annotation.internalPersistenceAnnotationProcessor")) {
         def = new RootBeanDefinition();

         try {
            def.setBeanClass(ClassUtils.forName("com.bea.core.repackaged.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor", AnnotationConfigUtils.class.getClassLoader()));
         } catch (ClassNotFoundException var6) {
            throw new IllegalStateException("Cannot load optional framework class: org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor", var6);
         }

         def.setSource(source);
         beanDefs.add(registerPostProcessor(registry, def, "com.bea.core.repackaged.springframework.context.annotation.internalPersistenceAnnotationProcessor"));
      }

      if (!registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.event.internalEventListenerProcessor")) {
         def = new RootBeanDefinition(EventListenerMethodProcessor.class);
         def.setSource(source);
         beanDefs.add(registerPostProcessor(registry, def, "com.bea.core.repackaged.springframework.context.event.internalEventListenerProcessor"));
      }

      if (!registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.event.internalEventListenerFactory")) {
         def = new RootBeanDefinition(DefaultEventListenerFactory.class);
         def.setSource(source);
         beanDefs.add(registerPostProcessor(registry, def, "com.bea.core.repackaged.springframework.context.event.internalEventListenerFactory"));
      }

      return beanDefs;
   }

   private static BeanDefinitionHolder registerPostProcessor(BeanDefinitionRegistry registry, RootBeanDefinition definition, String beanName) {
      definition.setRole(2);
      registry.registerBeanDefinition(beanName, definition);
      return new BeanDefinitionHolder(definition, beanName);
   }

   @Nullable
   private static DefaultListableBeanFactory unwrapDefaultListableBeanFactory(BeanDefinitionRegistry registry) {
      if (registry instanceof DefaultListableBeanFactory) {
         return (DefaultListableBeanFactory)registry;
      } else {
         return registry instanceof GenericApplicationContext ? ((GenericApplicationContext)registry).getDefaultListableBeanFactory() : null;
      }
   }

   public static void processCommonDefinitionAnnotations(AnnotatedBeanDefinition abd) {
      processCommonDefinitionAnnotations(abd, abd.getMetadata());
   }

   static void processCommonDefinitionAnnotations(AnnotatedBeanDefinition abd, AnnotatedTypeMetadata metadata) {
      AnnotationAttributes lazy = attributesFor(metadata, Lazy.class);
      if (lazy != null) {
         abd.setLazyInit(lazy.getBoolean("value"));
      } else if (abd.getMetadata() != metadata) {
         lazy = attributesFor(abd.getMetadata(), (Class)Lazy.class);
         if (lazy != null) {
            abd.setLazyInit(lazy.getBoolean("value"));
         }
      }

      if (metadata.isAnnotated(Primary.class.getName())) {
         abd.setPrimary(true);
      }

      AnnotationAttributes dependsOn = attributesFor(metadata, DependsOn.class);
      if (dependsOn != null) {
         abd.setDependsOn(dependsOn.getStringArray("value"));
      }

      AnnotationAttributes role = attributesFor(metadata, Role.class);
      if (role != null) {
         abd.setRole(role.getNumber("value").intValue());
      }

      AnnotationAttributes description = attributesFor(metadata, Description.class);
      if (description != null) {
         abd.setDescription(description.getString("value"));
      }

   }

   static BeanDefinitionHolder applyScopedProxyMode(ScopeMetadata metadata, BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {
      ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
      if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
         return definition;
      } else {
         boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
         return ScopedProxyCreator.createScopedProxy(definition, registry, proxyTargetClass);
      }
   }

   @Nullable
   static AnnotationAttributes attributesFor(AnnotatedTypeMetadata metadata, Class annotationClass) {
      return attributesFor(metadata, annotationClass.getName());
   }

   @Nullable
   static AnnotationAttributes attributesFor(AnnotatedTypeMetadata metadata, String annotationClassName) {
      return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationClassName, false));
   }

   static Set attributesForRepeatable(AnnotationMetadata metadata, Class containerClass, Class annotationClass) {
      return attributesForRepeatable(metadata, containerClass.getName(), annotationClass.getName());
   }

   static Set attributesForRepeatable(AnnotationMetadata metadata, String containerClassName, String annotationClassName) {
      Set result = new LinkedHashSet();
      addAttributesIfNotNull(result, metadata.getAnnotationAttributes(annotationClassName, false));
      Map container = metadata.getAnnotationAttributes(containerClassName, false);
      if (container != null && container.containsKey("value")) {
         Map[] var5 = (Map[])((Map[])container.get("value"));
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Map containedAttributes = var5[var7];
            addAttributesIfNotNull(result, containedAttributes);
         }
      }

      return Collections.unmodifiableSet(result);
   }

   private static void addAttributesIfNotNull(Set result, @Nullable Map attributes) {
      if (attributes != null) {
         result.add(AnnotationAttributes.fromMap(attributes));
      }

   }

   static {
      ClassLoader classLoader = AnnotationConfigUtils.class.getClassLoader();
      jsr250Present = ClassUtils.isPresent("javax.annotation.Resource", classLoader);
      jpaPresent = ClassUtils.isPresent("javax.persistence.EntityManagerFactory", classLoader) && ClassUtils.isPresent("com.bea.core.repackaged.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor", classLoader);
   }
}
