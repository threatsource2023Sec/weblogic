package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.AutoProxyUtils;
import com.bea.core.repackaged.springframework.aop.scope.ScopedObject;
import com.bea.core.repackaged.springframework.aop.scope.ScopedProxyUtils;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import com.bea.core.repackaged.springframework.beans.factory.SmartInitializingSingleton;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.ApplicationContextAware;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.context.ConfigurableApplicationContext;
import com.bea.core.repackaged.springframework.core.MethodIntrospector;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.stereotype.Component;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EventListenerMethodProcessor implements SmartInitializingSingleton, ApplicationContextAware, BeanFactoryPostProcessor {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private ConfigurableApplicationContext applicationContext;
   @Nullable
   private ConfigurableListableBeanFactory beanFactory;
   @Nullable
   private List eventListenerFactories;
   private final EventExpressionEvaluator evaluator = new EventExpressionEvaluator();
   private final Set nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap(64));

   public void setApplicationContext(ApplicationContext applicationContext) {
      Assert.isTrue(applicationContext instanceof ConfigurableApplicationContext, "ApplicationContext does not implement ConfigurableApplicationContext");
      this.applicationContext = (ConfigurableApplicationContext)applicationContext;
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
      this.beanFactory = beanFactory;
      Map beans = beanFactory.getBeansOfType(EventListenerFactory.class, false, false);
      List factories = new ArrayList(beans.values());
      AnnotationAwareOrderComparator.sort((List)factories);
      this.eventListenerFactories = factories;
   }

   public void afterSingletonsInstantiated() {
      ConfigurableListableBeanFactory beanFactory = this.beanFactory;
      Assert.state(this.beanFactory != null, "No ConfigurableListableBeanFactory set");
      String[] beanNames = beanFactory.getBeanNamesForType(Object.class);
      String[] var3 = beanNames;
      int var4 = beanNames.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String beanName = var3[var5];
         if (!ScopedProxyUtils.isScopedTarget(beanName)) {
            Class type = null;

            try {
               type = AutoProxyUtils.determineTargetClass(beanFactory, beanName);
            } catch (Throwable var10) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Could not resolve target class for bean with name '" + beanName + "'", var10);
               }
            }

            if (type != null) {
               if (ScopedObject.class.isAssignableFrom(type)) {
                  try {
                     Class targetClass = AutoProxyUtils.determineTargetClass(beanFactory, ScopedProxyUtils.getTargetBeanName(beanName));
                     if (targetClass != null) {
                        type = targetClass;
                     }
                  } catch (Throwable var11) {
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Could not resolve target bean for scoped proxy '" + beanName + "'", var11);
                     }
                  }
               }

               try {
                  this.processBean(beanName, type);
               } catch (Throwable var9) {
                  throw new BeanInitializationException("Failed to process @EventListener annotation on bean with name '" + beanName + "'", var9);
               }
            }
         }
      }

   }

   private void processBean(String beanName, Class targetType) {
      if (!this.nonAnnotatedClasses.contains(targetType) && !targetType.getName().startsWith("java") && !isSpringContainerClass(targetType)) {
         Map annotatedMethods = null;

         try {
            annotatedMethods = MethodIntrospector.selectMethods(targetType, (methodx) -> {
               return (EventListener)AnnotatedElementUtils.findMergedAnnotation(methodx, EventListener.class);
            });
         } catch (Throwable var12) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Could not resolve methods for bean with name '" + beanName + "'", var12);
            }
         }

         if (CollectionUtils.isEmpty(annotatedMethods)) {
            this.nonAnnotatedClasses.add(targetType);
            if (this.logger.isTraceEnabled()) {
               this.logger.trace("No @EventListener annotations found on bean class: " + targetType.getName());
            }
         } else {
            ConfigurableApplicationContext context = this.applicationContext;
            Assert.state(context != null, "No ApplicationContext set");
            List factories = this.eventListenerFactories;
            Assert.state(factories != null, "EventListenerFactory List not initialized");
            Iterator var6 = annotatedMethods.keySet().iterator();

            while(true) {
               while(var6.hasNext()) {
                  Method method = (Method)var6.next();
                  Iterator var8 = factories.iterator();

                  while(var8.hasNext()) {
                     EventListenerFactory factory = (EventListenerFactory)var8.next();
                     if (factory.supportsMethod(method)) {
                        Method methodToUse = AopUtils.selectInvocableMethod(method, context.getType(beanName));
                        ApplicationListener applicationListener = factory.createApplicationListener(beanName, targetType, methodToUse);
                        if (applicationListener instanceof ApplicationListenerMethodAdapter) {
                           ((ApplicationListenerMethodAdapter)applicationListener).init(context, this.evaluator);
                        }

                        context.addApplicationListener(applicationListener);
                        break;
                     }
                  }
               }

               if (this.logger.isDebugEnabled()) {
                  this.logger.debug(annotatedMethods.size() + " @EventListener methods processed on bean '" + beanName + "': " + annotatedMethods);
               }
               break;
            }
         }
      }

   }

   private static boolean isSpringContainerClass(Class clazz) {
      return clazz.getName().startsWith("com.bea.core.repackaged.springframework.") && !AnnotatedElementUtils.isAnnotated(ClassUtils.getUserClass(clazz), (Class)Component.class);
   }
}
