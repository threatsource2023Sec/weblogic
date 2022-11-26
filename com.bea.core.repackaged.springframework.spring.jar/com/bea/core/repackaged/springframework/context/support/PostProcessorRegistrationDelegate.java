package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import com.bea.core.repackaged.springframework.core.OrderComparator;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

final class PostProcessorRegistrationDelegate {
   private PostProcessorRegistrationDelegate() {
   }

   public static void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory, List beanFactoryPostProcessors) {
      Set processedBeans = new HashSet();
      ArrayList regularPostProcessors;
      ArrayList registryProcessors;
      int var9;
      ArrayList currentRegistryProcessors;
      String[] postProcessorNames;
      if (beanFactory instanceof BeanDefinitionRegistry) {
         BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
         regularPostProcessors = new ArrayList();
         registryProcessors = new ArrayList();
         Iterator var6 = beanFactoryPostProcessors.iterator();

         while(var6.hasNext()) {
            BeanFactoryPostProcessor postProcessor = (BeanFactoryPostProcessor)var6.next();
            if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
               BeanDefinitionRegistryPostProcessor registryProcessor = (BeanDefinitionRegistryPostProcessor)postProcessor;
               registryProcessor.postProcessBeanDefinitionRegistry(registry);
               registryProcessors.add(registryProcessor);
            } else {
               regularPostProcessors.add(postProcessor);
            }
         }

         currentRegistryProcessors = new ArrayList();
         postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
         String[] var16 = postProcessorNames;
         var9 = postProcessorNames.length;

         int var10;
         String ppName;
         for(var10 = 0; var10 < var9; ++var10) {
            ppName = var16[var10];
            if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
               currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
               processedBeans.add(ppName);
            }
         }

         sortPostProcessors(currentRegistryProcessors, beanFactory);
         registryProcessors.addAll(currentRegistryProcessors);
         invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
         currentRegistryProcessors.clear();
         postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
         var16 = postProcessorNames;
         var9 = postProcessorNames.length;

         for(var10 = 0; var10 < var9; ++var10) {
            ppName = var16[var10];
            if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
               currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
               processedBeans.add(ppName);
            }
         }

         sortPostProcessors(currentRegistryProcessors, beanFactory);
         registryProcessors.addAll(currentRegistryProcessors);
         invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
         currentRegistryProcessors.clear();
         boolean reiterate = true;

         while(reiterate) {
            reiterate = false;
            postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
            String[] var19 = postProcessorNames;
            var10 = postProcessorNames.length;

            for(int var26 = 0; var26 < var10; ++var26) {
               String ppName = var19[var26];
               if (!processedBeans.contains(ppName)) {
                  currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                  processedBeans.add(ppName);
                  reiterate = true;
               }
            }

            sortPostProcessors(currentRegistryProcessors, beanFactory);
            registryProcessors.addAll(currentRegistryProcessors);
            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
            currentRegistryProcessors.clear();
         }

         invokeBeanFactoryPostProcessors((Collection)registryProcessors, (ConfigurableListableBeanFactory)beanFactory);
         invokeBeanFactoryPostProcessors((Collection)regularPostProcessors, (ConfigurableListableBeanFactory)beanFactory);
      } else {
         invokeBeanFactoryPostProcessors((Collection)beanFactoryPostProcessors, (ConfigurableListableBeanFactory)beanFactory);
      }

      String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
      regularPostProcessors = new ArrayList();
      registryProcessors = new ArrayList();
      currentRegistryProcessors = new ArrayList();
      postProcessorNames = postProcessorNames;
      int var20 = postProcessorNames.length;

      String ppName;
      for(var9 = 0; var9 < var20; ++var9) {
         ppName = postProcessorNames[var9];
         if (!processedBeans.contains(ppName)) {
            if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
               regularPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
            } else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
               registryProcessors.add(ppName);
            } else {
               currentRegistryProcessors.add(ppName);
            }
         }
      }

      sortPostProcessors(regularPostProcessors, beanFactory);
      invokeBeanFactoryPostProcessors((Collection)regularPostProcessors, (ConfigurableListableBeanFactory)beanFactory);
      List orderedPostProcessors = new ArrayList();
      Iterator var21 = registryProcessors.iterator();

      while(var21.hasNext()) {
         String postProcessorName = (String)var21.next();
         orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
      }

      sortPostProcessors(orderedPostProcessors, beanFactory);
      invokeBeanFactoryPostProcessors((Collection)orderedPostProcessors, (ConfigurableListableBeanFactory)beanFactory);
      List nonOrderedPostProcessors = new ArrayList();
      Iterator var24 = currentRegistryProcessors.iterator();

      while(var24.hasNext()) {
         ppName = (String)var24.next();
         nonOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
      }

      invokeBeanFactoryPostProcessors((Collection)nonOrderedPostProcessors, (ConfigurableListableBeanFactory)beanFactory);
      beanFactory.clearMetadataCache();
   }

   public static void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {
      String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
      int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
      beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));
      List priorityOrderedPostProcessors = new ArrayList();
      List internalPostProcessors = new ArrayList();
      List orderedPostProcessorNames = new ArrayList();
      List nonOrderedPostProcessorNames = new ArrayList();
      String[] var8 = postProcessorNames;
      int var9 = postProcessorNames.length;

      String ppName;
      BeanPostProcessor pp;
      for(int var10 = 0; var10 < var9; ++var10) {
         ppName = var8[var10];
         if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
            pp = (BeanPostProcessor)beanFactory.getBean(ppName, BeanPostProcessor.class);
            priorityOrderedPostProcessors.add(pp);
            if (pp instanceof MergedBeanDefinitionPostProcessor) {
               internalPostProcessors.add(pp);
            }
         } else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
            orderedPostProcessorNames.add(ppName);
         } else {
            nonOrderedPostProcessorNames.add(ppName);
         }
      }

      sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
      registerBeanPostProcessors(beanFactory, (List)priorityOrderedPostProcessors);
      List orderedPostProcessors = new ArrayList();
      Iterator var14 = orderedPostProcessorNames.iterator();

      while(var14.hasNext()) {
         String ppName = (String)var14.next();
         BeanPostProcessor pp = (BeanPostProcessor)beanFactory.getBean(ppName, BeanPostProcessor.class);
         orderedPostProcessors.add(pp);
         if (pp instanceof MergedBeanDefinitionPostProcessor) {
            internalPostProcessors.add(pp);
         }
      }

      sortPostProcessors(orderedPostProcessors, beanFactory);
      registerBeanPostProcessors(beanFactory, (List)orderedPostProcessors);
      List nonOrderedPostProcessors = new ArrayList();
      Iterator var17 = nonOrderedPostProcessorNames.iterator();

      while(var17.hasNext()) {
         ppName = (String)var17.next();
         pp = (BeanPostProcessor)beanFactory.getBean(ppName, BeanPostProcessor.class);
         nonOrderedPostProcessors.add(pp);
         if (pp instanceof MergedBeanDefinitionPostProcessor) {
            internalPostProcessors.add(pp);
         }
      }

      registerBeanPostProcessors(beanFactory, (List)nonOrderedPostProcessors);
      sortPostProcessors(internalPostProcessors, beanFactory);
      registerBeanPostProcessors(beanFactory, (List)internalPostProcessors);
      beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
   }

   private static void sortPostProcessors(List postProcessors, ConfigurableListableBeanFactory beanFactory) {
      Comparator comparatorToUse = null;
      if (beanFactory instanceof DefaultListableBeanFactory) {
         comparatorToUse = ((DefaultListableBeanFactory)beanFactory).getDependencyComparator();
      }

      if (comparatorToUse == null) {
         comparatorToUse = OrderComparator.INSTANCE;
      }

      postProcessors.sort((Comparator)comparatorToUse);
   }

   private static void invokeBeanDefinitionRegistryPostProcessors(Collection postProcessors, BeanDefinitionRegistry registry) {
      Iterator var2 = postProcessors.iterator();

      while(var2.hasNext()) {
         BeanDefinitionRegistryPostProcessor postProcessor = (BeanDefinitionRegistryPostProcessor)var2.next();
         postProcessor.postProcessBeanDefinitionRegistry(registry);
      }

   }

   private static void invokeBeanFactoryPostProcessors(Collection postProcessors, ConfigurableListableBeanFactory beanFactory) {
      Iterator var2 = postProcessors.iterator();

      while(var2.hasNext()) {
         BeanFactoryPostProcessor postProcessor = (BeanFactoryPostProcessor)var2.next();
         postProcessor.postProcessBeanFactory(beanFactory);
      }

   }

   private static void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory, List postProcessors) {
      Iterator var2 = postProcessors.iterator();

      while(var2.hasNext()) {
         BeanPostProcessor postProcessor = (BeanPostProcessor)var2.next();
         beanFactory.addBeanPostProcessor(postProcessor);
      }

   }

   private static final class BeanPostProcessorChecker implements BeanPostProcessor {
      private static final Log logger = LogFactory.getLog(BeanPostProcessorChecker.class);
      private final ConfigurableListableBeanFactory beanFactory;
      private final int beanPostProcessorTargetCount;

      public BeanPostProcessorChecker(ConfigurableListableBeanFactory beanFactory, int beanPostProcessorTargetCount) {
         this.beanFactory = beanFactory;
         this.beanPostProcessorTargetCount = beanPostProcessorTargetCount;
      }

      public Object postProcessBeforeInitialization(Object bean, String beanName) {
         return bean;
      }

      public Object postProcessAfterInitialization(Object bean, String beanName) {
         if (!(bean instanceof BeanPostProcessor) && !this.isInfrastructureBean(beanName) && this.beanFactory.getBeanPostProcessorCount() < this.beanPostProcessorTargetCount && logger.isInfoEnabled()) {
            logger.info("Bean '" + beanName + "' of type [" + bean.getClass().getName() + "] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)");
         }

         return bean;
      }

      private boolean isInfrastructureBean(@Nullable String beanName) {
         if (beanName != null && this.beanFactory.containsBeanDefinition(beanName)) {
            BeanDefinition bd = this.beanFactory.getBeanDefinition(beanName);
            return bd.getRole() == 2;
         } else {
            return false;
         }
      }
   }
}
