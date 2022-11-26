package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.context.ApplicationContextException;
import com.bea.core.repackaged.springframework.context.Lifecycle;
import com.bea.core.repackaged.springframework.context.LifecycleProcessor;
import com.bea.core.repackaged.springframework.context.Phased;
import com.bea.core.repackaged.springframework.context.SmartLifecycle;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DefaultLifecycleProcessor implements LifecycleProcessor, BeanFactoryAware {
   private final Log logger = LogFactory.getLog(this.getClass());
   private volatile long timeoutPerShutdownPhase = 30000L;
   private volatile boolean running;
   @Nullable
   private volatile ConfigurableListableBeanFactory beanFactory;

   public void setTimeoutPerShutdownPhase(long timeoutPerShutdownPhase) {
      this.timeoutPerShutdownPhase = timeoutPerShutdownPhase;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
         throw new IllegalArgumentException("DefaultLifecycleProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
      } else {
         this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
      }
   }

   private ConfigurableListableBeanFactory getBeanFactory() {
      ConfigurableListableBeanFactory beanFactory = this.beanFactory;
      Assert.state(beanFactory != null, "No BeanFactory available");
      return beanFactory;
   }

   public void start() {
      this.startBeans(false);
      this.running = true;
   }

   public void stop() {
      this.stopBeans();
      this.running = false;
   }

   public void onRefresh() {
      this.startBeans(true);
      this.running = true;
   }

   public void onClose() {
      this.stopBeans();
      this.running = false;
   }

   public boolean isRunning() {
      return this.running;
   }

   private void startBeans(boolean autoStartupOnly) {
      Map lifecycleBeans = this.getLifecycleBeans();
      Map phases = new HashMap();
      lifecycleBeans.forEach((beanName, bean) -> {
         if (!autoStartupOnly || bean instanceof SmartLifecycle && ((SmartLifecycle)bean).isAutoStartup()) {
            int phase = this.getPhase(bean);
            LifecycleGroup group = (LifecycleGroup)phases.get(phase);
            if (group == null) {
               group = new LifecycleGroup(phase, this.timeoutPerShutdownPhase, lifecycleBeans, autoStartupOnly);
               phases.put(phase, group);
            }

            group.add(beanName, bean);
         }

      });
      if (!phases.isEmpty()) {
         List keys = new ArrayList(phases.keySet());
         Collections.sort(keys);
         Iterator var5 = keys.iterator();

         while(var5.hasNext()) {
            Integer key = (Integer)var5.next();
            ((LifecycleGroup)phases.get(key)).start();
         }
      }

   }

   private void doStart(Map lifecycleBeans, String beanName, boolean autoStartupOnly) {
      Lifecycle bean = (Lifecycle)lifecycleBeans.remove(beanName);
      if (bean != null && bean != this) {
         String[] dependenciesForBean = this.getBeanFactory().getDependenciesForBean(beanName);
         String[] var6 = dependenciesForBean;
         int var7 = dependenciesForBean.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String dependency = var6[var8];
            this.doStart(lifecycleBeans, dependency, autoStartupOnly);
         }

         if (!bean.isRunning() && (!autoStartupOnly || !(bean instanceof SmartLifecycle) || ((SmartLifecycle)bean).isAutoStartup())) {
            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Starting bean '" + beanName + "' of type [" + bean.getClass().getName() + "]");
            }

            try {
               bean.start();
            } catch (Throwable var10) {
               throw new ApplicationContextException("Failed to start bean '" + beanName + "'", var10);
            }

            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Successfully started bean '" + beanName + "'");
            }
         }
      }

   }

   private void stopBeans() {
      Map lifecycleBeans = this.getLifecycleBeans();
      Map phases = new HashMap();
      lifecycleBeans.forEach((beanName, bean) -> {
         int shutdownPhase = this.getPhase(bean);
         LifecycleGroup group = (LifecycleGroup)phases.get(shutdownPhase);
         if (group == null) {
            group = new LifecycleGroup(shutdownPhase, this.timeoutPerShutdownPhase, lifecycleBeans, false);
            phases.put(shutdownPhase, group);
         }

         group.add(beanName, bean);
      });
      if (!phases.isEmpty()) {
         List keys = new ArrayList(phases.keySet());
         keys.sort(Collections.reverseOrder());
         Iterator var4 = keys.iterator();

         while(var4.hasNext()) {
            Integer key = (Integer)var4.next();
            ((LifecycleGroup)phases.get(key)).stop();
         }
      }

   }

   private void doStop(Map lifecycleBeans, String beanName, CountDownLatch latch, Set countDownBeanNames) {
      Lifecycle bean = (Lifecycle)lifecycleBeans.remove(beanName);
      if (bean != null) {
         String[] dependentBeans = this.getBeanFactory().getDependentBeans(beanName);
         String[] var7 = dependentBeans;
         int var8 = dependentBeans.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String dependentBean = var7[var9];
            this.doStop(lifecycleBeans, dependentBean, latch, countDownBeanNames);
         }

         try {
            if (bean.isRunning()) {
               if (bean instanceof SmartLifecycle) {
                  if (this.logger.isTraceEnabled()) {
                     this.logger.trace("Asking bean '" + beanName + "' of type [" + bean.getClass().getName() + "] to stop");
                  }

                  countDownBeanNames.add(beanName);
                  ((SmartLifecycle)bean).stop(() -> {
                     latch.countDown();
                     countDownBeanNames.remove(beanName);
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Bean '" + beanName + "' completed its stop procedure");
                     }

                  });
               } else {
                  if (this.logger.isTraceEnabled()) {
                     this.logger.trace("Stopping bean '" + beanName + "' of type [" + bean.getClass().getName() + "]");
                  }

                  bean.stop();
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("Successfully stopped bean '" + beanName + "'");
                  }
               }
            } else if (bean instanceof SmartLifecycle) {
               latch.countDown();
            }
         } catch (Throwable var11) {
            if (this.logger.isWarnEnabled()) {
               this.logger.warn("Failed to stop bean '" + beanName + "'", var11);
            }
         }
      }

   }

   protected Map getLifecycleBeans() {
      ConfigurableListableBeanFactory beanFactory = this.getBeanFactory();
      Map beans = new LinkedHashMap();
      String[] beanNames = beanFactory.getBeanNamesForType(Lifecycle.class, false, false);
      String[] var4 = beanNames;
      int var5 = beanNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String beanName = var4[var6];
         String beanNameToRegister = BeanFactoryUtils.transformedBeanName(beanName);
         boolean isFactoryBean = beanFactory.isFactoryBean(beanNameToRegister);
         String beanNameToCheck = isFactoryBean ? "&" + beanName : beanName;
         if (beanFactory.containsSingleton(beanNameToRegister) && (!isFactoryBean || this.matchesBeanType(Lifecycle.class, beanNameToCheck, beanFactory)) || this.matchesBeanType(SmartLifecycle.class, beanNameToCheck, beanFactory)) {
            Object bean = beanFactory.getBean(beanNameToCheck);
            if (bean != this && bean instanceof Lifecycle) {
               beans.put(beanNameToRegister, (Lifecycle)bean);
            }
         }
      }

      return beans;
   }

   private boolean matchesBeanType(Class targetType, String beanName, BeanFactory beanFactory) {
      Class beanType = beanFactory.getType(beanName);
      return beanType != null && targetType.isAssignableFrom(beanType);
   }

   protected int getPhase(Lifecycle bean) {
      return bean instanceof Phased ? ((Phased)bean).getPhase() : 0;
   }

   private class LifecycleGroupMember implements Comparable {
      private final String name;
      private final Lifecycle bean;

      LifecycleGroupMember(String name, Lifecycle bean) {
         this.name = name;
         this.bean = bean;
      }

      public int compareTo(LifecycleGroupMember other) {
         int thisPhase = DefaultLifecycleProcessor.this.getPhase(this.bean);
         int otherPhase = DefaultLifecycleProcessor.this.getPhase(other.bean);
         return Integer.compare(thisPhase, otherPhase);
      }
   }

   private class LifecycleGroup {
      private final int phase;
      private final long timeout;
      private final Map lifecycleBeans;
      private final boolean autoStartupOnly;
      private final List members = new ArrayList();
      private int smartMemberCount;

      public LifecycleGroup(int phase, long timeout, Map lifecycleBeans, boolean autoStartupOnly) {
         this.phase = phase;
         this.timeout = timeout;
         this.lifecycleBeans = lifecycleBeans;
         this.autoStartupOnly = autoStartupOnly;
      }

      public void add(String name, Lifecycle bean) {
         this.members.add(DefaultLifecycleProcessor.this.new LifecycleGroupMember(name, bean));
         if (bean instanceof SmartLifecycle) {
            ++this.smartMemberCount;
         }

      }

      public void start() {
         if (!this.members.isEmpty()) {
            if (DefaultLifecycleProcessor.this.logger.isDebugEnabled()) {
               DefaultLifecycleProcessor.this.logger.debug("Starting beans in phase " + this.phase);
            }

            Collections.sort(this.members);
            Iterator var1 = this.members.iterator();

            while(var1.hasNext()) {
               LifecycleGroupMember member = (LifecycleGroupMember)var1.next();
               DefaultLifecycleProcessor.this.doStart(this.lifecycleBeans, member.name, this.autoStartupOnly);
            }

         }
      }

      public void stop() {
         if (!this.members.isEmpty()) {
            if (DefaultLifecycleProcessor.this.logger.isDebugEnabled()) {
               DefaultLifecycleProcessor.this.logger.debug("Stopping beans in phase " + this.phase);
            }

            this.members.sort(Collections.reverseOrder());
            CountDownLatch latch = new CountDownLatch(this.smartMemberCount);
            Set countDownBeanNames = Collections.synchronizedSet(new LinkedHashSet());
            Set lifecycleBeanNames = new HashSet(this.lifecycleBeans.keySet());
            Iterator var4 = this.members.iterator();

            while(var4.hasNext()) {
               LifecycleGroupMember member = (LifecycleGroupMember)var4.next();
               if (lifecycleBeanNames.contains(member.name)) {
                  DefaultLifecycleProcessor.this.doStop(this.lifecycleBeans, member.name, latch, countDownBeanNames);
               } else if (member.bean instanceof SmartLifecycle) {
                  latch.countDown();
               }
            }

            try {
               latch.await(this.timeout, TimeUnit.MILLISECONDS);
               if (latch.getCount() > 0L && !countDownBeanNames.isEmpty() && DefaultLifecycleProcessor.this.logger.isInfoEnabled()) {
                  DefaultLifecycleProcessor.this.logger.info("Failed to shut down " + countDownBeanNames.size() + " bean" + (countDownBeanNames.size() > 1 ? "s" : "") + " with phase value " + this.phase + " within timeout of " + this.timeout + ": " + countDownBeanNames);
               }
            } catch (InterruptedException var6) {
               Thread.currentThread().interrupt();
            }

         }
      }
   }
}
