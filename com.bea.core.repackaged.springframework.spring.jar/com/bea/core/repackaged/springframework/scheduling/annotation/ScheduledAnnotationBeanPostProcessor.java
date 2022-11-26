package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.framework.AopInfrastructureBean;
import com.bea.core.repackaged.springframework.aop.framework.AopProxyUtils;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.SmartInitializingSingleton;
import com.bea.core.repackaged.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.NamedBeanHolder;
import com.bea.core.repackaged.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.ApplicationContextAware;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.context.EmbeddedValueResolverAware;
import com.bea.core.repackaged.springframework.context.event.ContextRefreshedEvent;
import com.bea.core.repackaged.springframework.core.MethodIntrospector;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.TaskScheduler;
import com.bea.core.repackaged.springframework.scheduling.config.CronTask;
import com.bea.core.repackaged.springframework.scheduling.config.FixedDelayTask;
import com.bea.core.repackaged.springframework.scheduling.config.FixedRateTask;
import com.bea.core.repackaged.springframework.scheduling.config.ScheduledTask;
import com.bea.core.repackaged.springframework.scheduling.config.ScheduledTaskHolder;
import com.bea.core.repackaged.springframework.scheduling.config.ScheduledTaskRegistrar;
import com.bea.core.repackaged.springframework.scheduling.support.CronTrigger;
import com.bea.core.repackaged.springframework.scheduling.support.ScheduledMethodRunnable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduledAnnotationBeanPostProcessor implements ScheduledTaskHolder, MergedBeanDefinitionPostProcessor, DestructionAwareBeanPostProcessor, Ordered, EmbeddedValueResolverAware, BeanNameAware, BeanFactoryAware, ApplicationContextAware, SmartInitializingSingleton, ApplicationListener, DisposableBean {
   public static final String DEFAULT_TASK_SCHEDULER_BEAN_NAME = "taskScheduler";
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final ScheduledTaskRegistrar registrar;
   @Nullable
   private Object scheduler;
   @Nullable
   private StringValueResolver embeddedValueResolver;
   @Nullable
   private String beanName;
   @Nullable
   private BeanFactory beanFactory;
   @Nullable
   private ApplicationContext applicationContext;
   private final Set nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap(64));
   private final Map scheduledTasks = new IdentityHashMap(16);

   public ScheduledAnnotationBeanPostProcessor() {
      this.registrar = new ScheduledTaskRegistrar();
   }

   public ScheduledAnnotationBeanPostProcessor(ScheduledTaskRegistrar registrar) {
      Assert.notNull(registrar, (String)"ScheduledTaskRegistrar is required");
      this.registrar = registrar;
   }

   public int getOrder() {
      return Integer.MAX_VALUE;
   }

   public void setScheduler(Object scheduler) {
      this.scheduler = scheduler;
   }

   public void setEmbeddedValueResolver(StringValueResolver resolver) {
      this.embeddedValueResolver = resolver;
   }

   public void setBeanName(String beanName) {
      this.beanName = beanName;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   public void setApplicationContext(ApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
      if (this.beanFactory == null) {
         this.beanFactory = applicationContext;
      }

   }

   public void afterSingletonsInstantiated() {
      this.nonAnnotatedClasses.clear();
      if (this.applicationContext == null) {
         this.finishRegistration();
      }

   }

   public void onApplicationEvent(ContextRefreshedEvent event) {
      if (event.getApplicationContext() == this.applicationContext) {
         this.finishRegistration();
      }

   }

   private void finishRegistration() {
      if (this.scheduler != null) {
         this.registrar.setScheduler(this.scheduler);
      }

      if (this.beanFactory instanceof ListableBeanFactory) {
         Map beans = ((ListableBeanFactory)this.beanFactory).getBeansOfType(SchedulingConfigurer.class);
         List configurers = new ArrayList(beans.values());
         AnnotationAwareOrderComparator.sort((List)configurers);
         Iterator var3 = configurers.iterator();

         while(var3.hasNext()) {
            SchedulingConfigurer configurer = (SchedulingConfigurer)var3.next();
            configurer.configureTasks(this.registrar);
         }
      }

      if (this.registrar.hasTasks() && this.registrar.getScheduler() == null) {
         Assert.state(this.beanFactory != null, "BeanFactory must be set to find scheduler by type");

         try {
            this.registrar.setTaskScheduler((TaskScheduler)this.resolveSchedulerBean(this.beanFactory, TaskScheduler.class, false));
         } catch (NoUniqueBeanDefinitionException var9) {
            this.logger.trace("Could not find unique TaskScheduler bean", var9);

            try {
               this.registrar.setTaskScheduler((TaskScheduler)this.resolveSchedulerBean(this.beanFactory, TaskScheduler.class, true));
            } catch (NoSuchBeanDefinitionException var8) {
               if (this.logger.isInfoEnabled()) {
                  this.logger.info("More than one TaskScheduler bean exists within the context, and none is named 'taskScheduler'. Mark one of them as primary or name it 'taskScheduler' (possibly as an alias); or implement the SchedulingConfigurer interface and call ScheduledTaskRegistrar#setScheduler explicitly within the configureTasks() callback: " + var9.getBeanNamesFound());
               }
            }
         } catch (NoSuchBeanDefinitionException var10) {
            this.logger.trace("Could not find default TaskScheduler bean", var10);

            try {
               this.registrar.setScheduler(this.resolveSchedulerBean(this.beanFactory, ScheduledExecutorService.class, false));
            } catch (NoUniqueBeanDefinitionException var6) {
               this.logger.trace("Could not find unique ScheduledExecutorService bean", var6);

               try {
                  this.registrar.setScheduler(this.resolveSchedulerBean(this.beanFactory, ScheduledExecutorService.class, true));
               } catch (NoSuchBeanDefinitionException var5) {
                  if (this.logger.isInfoEnabled()) {
                     this.logger.info("More than one ScheduledExecutorService bean exists within the context, and none is named 'taskScheduler'. Mark one of them as primary or name it 'taskScheduler' (possibly as an alias); or implement the SchedulingConfigurer interface and call ScheduledTaskRegistrar#setScheduler explicitly within the configureTasks() callback: " + var6.getBeanNamesFound());
                  }
               }
            } catch (NoSuchBeanDefinitionException var7) {
               this.logger.trace("Could not find default ScheduledExecutorService bean", var7);
               this.logger.info("No TaskScheduler/ScheduledExecutorService bean found for scheduled processing");
            }
         }
      }

      this.registrar.afterPropertiesSet();
   }

   private Object resolveSchedulerBean(BeanFactory beanFactory, Class schedulerType, boolean byName) {
      if (byName) {
         Object scheduler = beanFactory.getBean("taskScheduler", schedulerType);
         if (this.beanName != null && this.beanFactory instanceof ConfigurableBeanFactory) {
            ((ConfigurableBeanFactory)this.beanFactory).registerDependentBean("taskScheduler", this.beanName);
         }

         return scheduler;
      } else if (beanFactory instanceof AutowireCapableBeanFactory) {
         NamedBeanHolder holder = ((AutowireCapableBeanFactory)beanFactory).resolveNamedBean(schedulerType);
         if (this.beanName != null && beanFactory instanceof ConfigurableBeanFactory) {
            ((ConfigurableBeanFactory)beanFactory).registerDependentBean(holder.getBeanName(), this.beanName);
         }

         return holder.getBeanInstance();
      } else {
         return beanFactory.getBean(schedulerType);
      }
   }

   public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class beanType, String beanName) {
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) {
      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) {
      if (!(bean instanceof AopInfrastructureBean) && !(bean instanceof TaskScheduler) && !(bean instanceof ScheduledExecutorService)) {
         Class targetClass = AopProxyUtils.ultimateTargetClass(bean);
         if (!this.nonAnnotatedClasses.contains(targetClass)) {
            Map annotatedMethods = MethodIntrospector.selectMethods(targetClass, (method) -> {
               Set scheduledMethods = AnnotatedElementUtils.getMergedRepeatableAnnotations(method, Scheduled.class, Schedules.class);
               return !scheduledMethods.isEmpty() ? scheduledMethods : null;
            });
            if (annotatedMethods.isEmpty()) {
               this.nonAnnotatedClasses.add(targetClass);
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("No @Scheduled annotations found on bean class: " + targetClass);
               }
            } else {
               annotatedMethods.forEach((method, scheduledMethods) -> {
                  scheduledMethods.forEach((scheduled) -> {
                     this.processScheduled(scheduled, method, bean);
                  });
               });
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace(annotatedMethods.size() + " @Scheduled methods processed on bean '" + beanName + "': " + annotatedMethods);
               }
            }
         }

         return bean;
      } else {
         return bean;
      }
   }

   protected void processScheduled(Scheduled scheduled, Method method, Object bean) {
      try {
         Runnable runnable = this.createRunnable(bean, method);
         boolean processedSchedule = false;
         String errorMessage = "Exactly one of the 'cron', 'fixedDelay(String)', or 'fixedRate(String)' attributes is required";
         Set tasks = new LinkedHashSet(4);
         long initialDelay = scheduled.initialDelay();
         String initialDelayString = scheduled.initialDelayString();
         if (StringUtils.hasText(initialDelayString)) {
            Assert.isTrue(initialDelay < 0L, "Specify 'initialDelay' or 'initialDelayString', not both");
            if (this.embeddedValueResolver != null) {
               initialDelayString = this.embeddedValueResolver.resolveStringValue(initialDelayString);
            }

            if (StringUtils.hasLength(initialDelayString)) {
               try {
                  initialDelay = parseDelayAsLong(initialDelayString);
               } catch (RuntimeException var24) {
                  throw new IllegalArgumentException("Invalid initialDelayString value \"" + initialDelayString + "\" - cannot parse into long");
               }
            }
         }

         String cron = scheduled.cron();
         if (StringUtils.hasText(cron)) {
            String zone = scheduled.zone();
            if (this.embeddedValueResolver != null) {
               cron = this.embeddedValueResolver.resolveStringValue(cron);
               zone = this.embeddedValueResolver.resolveStringValue(zone);
            }

            if (StringUtils.hasLength(cron)) {
               Assert.isTrue(initialDelay == -1L, "'initialDelay' not supported for cron triggers");
               processedSchedule = true;
               if (!"-".equals(cron)) {
                  TimeZone timeZone;
                  if (StringUtils.hasText(zone)) {
                     timeZone = StringUtils.parseTimeZoneString(zone);
                  } else {
                     timeZone = TimeZone.getDefault();
                  }

                  tasks.add(this.registrar.scheduleCronTask(new CronTask(runnable, new CronTrigger(cron, timeZone))));
               }
            }
         }

         if (initialDelay < 0L) {
            initialDelay = 0L;
         }

         long fixedDelay = scheduled.fixedDelay();
         if (fixedDelay >= 0L) {
            Assert.isTrue(!processedSchedule, errorMessage);
            processedSchedule = true;
            tasks.add(this.registrar.scheduleFixedDelayTask(new FixedDelayTask(runnable, fixedDelay, initialDelay)));
         }

         String fixedDelayString = scheduled.fixedDelayString();
         if (StringUtils.hasText(fixedDelayString)) {
            if (this.embeddedValueResolver != null) {
               fixedDelayString = this.embeddedValueResolver.resolveStringValue(fixedDelayString);
            }

            if (StringUtils.hasLength(fixedDelayString)) {
               Assert.isTrue(!processedSchedule, errorMessage);
               processedSchedule = true;

               try {
                  fixedDelay = parseDelayAsLong(fixedDelayString);
               } catch (RuntimeException var23) {
                  throw new IllegalArgumentException("Invalid fixedDelayString value \"" + fixedDelayString + "\" - cannot parse into long");
               }

               tasks.add(this.registrar.scheduleFixedDelayTask(new FixedDelayTask(runnable, fixedDelay, initialDelay)));
            }
         }

         long fixedRate = scheduled.fixedRate();
         if (fixedRate >= 0L) {
            Assert.isTrue(!processedSchedule, errorMessage);
            processedSchedule = true;
            tasks.add(this.registrar.scheduleFixedRateTask(new FixedRateTask(runnable, fixedRate, initialDelay)));
         }

         String fixedRateString = scheduled.fixedRateString();
         if (StringUtils.hasText(fixedRateString)) {
            if (this.embeddedValueResolver != null) {
               fixedRateString = this.embeddedValueResolver.resolveStringValue(fixedRateString);
            }

            if (StringUtils.hasLength(fixedRateString)) {
               Assert.isTrue(!processedSchedule, errorMessage);
               processedSchedule = true;

               try {
                  fixedRate = parseDelayAsLong(fixedRateString);
               } catch (RuntimeException var22) {
                  throw new IllegalArgumentException("Invalid fixedRateString value \"" + fixedRateString + "\" - cannot parse into long");
               }

               tasks.add(this.registrar.scheduleFixedRateTask(new FixedRateTask(runnable, fixedRate, initialDelay)));
            }
         }

         Assert.isTrue(processedSchedule, errorMessage);
         synchronized(this.scheduledTasks) {
            Set regTasks = (Set)this.scheduledTasks.computeIfAbsent(bean, (key) -> {
               return new LinkedHashSet(4);
            });
            regTasks.addAll(tasks);
         }
      } catch (IllegalArgumentException var25) {
         throw new IllegalStateException("Encountered invalid @Scheduled method '" + method.getName() + "': " + var25.getMessage());
      }
   }

   protected Runnable createRunnable(Object target, Method method) {
      Assert.isTrue(method.getParameterCount() == 0, "Only no-arg methods may be annotated with @Scheduled");
      Method invocableMethod = AopUtils.selectInvocableMethod(method, target.getClass());
      return new ScheduledMethodRunnable(target, invocableMethod);
   }

   private static long parseDelayAsLong(String value) throws RuntimeException {
      return value.length() <= 1 || !isP(value.charAt(0)) && !isP(value.charAt(1)) ? Long.parseLong(value) : Duration.parse(value).toMillis();
   }

   private static boolean isP(char ch) {
      return ch == 'P' || ch == 'p';
   }

   public Set getScheduledTasks() {
      Set result = new LinkedHashSet();
      synchronized(this.scheduledTasks) {
         Collection allTasks = this.scheduledTasks.values();
         Iterator var4 = allTasks.iterator();

         while(true) {
            if (!var4.hasNext()) {
               break;
            }

            Set tasks = (Set)var4.next();
            result.addAll(tasks);
         }
      }

      result.addAll(this.registrar.getScheduledTasks());
      return result;
   }

   public void postProcessBeforeDestruction(Object bean, String beanName) {
      Set tasks;
      synchronized(this.scheduledTasks) {
         tasks = (Set)this.scheduledTasks.remove(bean);
      }

      if (tasks != null) {
         Iterator var4 = tasks.iterator();

         while(var4.hasNext()) {
            ScheduledTask task = (ScheduledTask)var4.next();
            task.cancel();
         }
      }

   }

   public boolean requiresDestruction(Object bean) {
      synchronized(this.scheduledTasks) {
         return this.scheduledTasks.containsKey(bean);
      }
   }

   public void destroy() {
      synchronized(this.scheduledTasks) {
         Collection allTasks = this.scheduledTasks.values();
         Iterator var3 = allTasks.iterator();

         label25:
         while(true) {
            if (var3.hasNext()) {
               Set tasks = (Set)var3.next();
               Iterator var5 = tasks.iterator();

               while(true) {
                  if (!var5.hasNext()) {
                     continue label25;
                  }

                  ScheduledTask task = (ScheduledTask)var5.next();
                  task.cancel();
               }
            }

            this.scheduledTasks.clear();
            break;
         }
      }

      this.registrar.destroy();
   }
}
