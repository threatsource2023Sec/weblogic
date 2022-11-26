package org.jboss.weld.bootstrap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.bean.AbstractBean;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bean.ProducerMethod;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class SpecializationAndEnablementRegistry extends AbstractBootstrapService {
   private final ComputingCache specializedBeanResolvers;
   private final Map environmentByManager = new ConcurrentHashMap();
   private final ComputingCache specializedBeans;
   private final ConcurrentHashMap specializedBeansMap = new ConcurrentHashMap();

   public SpecializationAndEnablementRegistry() {
      ComputingCacheBuilder cacheBuilder = ComputingCacheBuilder.newBuilder();
      this.specializedBeanResolvers = cacheBuilder.build(new SpecializedBeanResolverForBeanManager());
      this.specializedBeans = ComputingCacheBuilder.newBuilder().build(new BeansSpecializedByBean());
   }

   public Set resolveSpecializedBeans(Bean specializingBean) {
      if (specializingBean instanceof AbstractClassBean) {
         AbstractClassBean abstractClassBean = (AbstractClassBean)specializingBean;
         if (abstractClassBean.isSpecializing()) {
            return (Set)this.specializedBeans.getValue(specializingBean);
         }
      }

      if (specializingBean instanceof ProducerMethod) {
         ProducerMethod producerMethod = (ProducerMethod)specializingBean;
         if (producerMethod.isSpecializing()) {
            return (Set)this.specializedBeans.getValue(specializingBean);
         }
      }

      return Collections.emptySet();
   }

   public void vetoSpecializingBean(Bean bean) {
      Set noLongerSpecializedBeans = (Set)this.specializedBeans.getValueIfPresent(bean);
      if (noLongerSpecializedBeans != null) {
         this.specializedBeans.invalidate(bean);
         Iterator var3 = noLongerSpecializedBeans.iterator();

         while(var3.hasNext()) {
            AbstractBean noLongerSpecializedBean = (AbstractBean)var3.next();
            LongAdder count = (LongAdder)this.specializedBeansMap.get(noLongerSpecializedBean);
            if (count != null) {
               count.decrement();
            }
         }
      }

   }

   public boolean isSpecializedInAnyBeanDeployment(Bean bean) {
      LongAdder count = (LongAdder)this.specializedBeansMap.get(bean);
      return count != null && count.longValue() > 0L;
   }

   public boolean isEnabledInAnyBeanDeployment(Bean bean) {
      Iterator var2 = this.environmentByManager.keySet().iterator();

      BeanManagerImpl manager;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         manager = (BeanManagerImpl)var2.next();
      } while(!manager.isBeanEnabled(bean));

      return true;
   }

   public boolean isCandidateForLifecycleEvent(Bean bean) {
      if (bean instanceof AbstractProducerBean) {
         AbstractProducerBean producer = (AbstractProducerBean)Reflections.cast(bean);
         if (!this.isCandidateForLifecycleEvent(producer.getDeclaringBean())) {
            return false;
         }
      }

      return this.isEnabledInAnyBeanDeployment(bean) && !this.isSpecializedInAnyBeanDeployment(bean);
   }

   public void registerEnvironment(BeanManagerImpl manager, BeanDeployerEnvironment environment, boolean additionalBeanArchive) {
      if (this.specializedBeanResolvers.size() > 0L && !additionalBeanArchive) {
         throw new IllegalStateException(this.getClass().getName() + ".registerEnvironment() must not be called after specialization resolution begins");
      } else if (environment == null) {
         throw new IllegalArgumentException("Environment must not be null");
      } else {
         this.environmentByManager.put(manager, environment);
      }
   }

   public void cleanupAfterBoot() {
      this.specializedBeanResolvers.clear();
      this.environmentByManager.clear();
      this.specializedBeans.clear();
      this.specializedBeansMap.clear();
   }

   public Set getBeansSpecializedInAnyDeployment() {
      return ImmutableSet.copyOf(this.specializedBeansMap.keySet());
   }

   public Map getBeansSpecializedInAnyDeploymentAsMap() {
      return (Map)this.specializedBeansMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (entry) -> {
         return ((LongAdder)entry.getValue()).longValue();
      }));
   }

   private class BeansSpecializedByBean implements Function {
      private BeansSpecializedByBean() {
      }

      public Set apply(Bean specializingBean) {
         Set result = null;
         if (specializingBean instanceof AbstractClassBean) {
            result = this.apply((AbstractClassBean)specializingBean);
         }

         if (specializingBean instanceof ProducerMethod) {
            result = this.apply((ProducerMethod)specializingBean);
         }

         if (result == null) {
            throw new IllegalArgumentException("Unsupported bean type " + specializingBean);
         } else {
            if (SpecializationAndEnablementRegistry.this.isEnabledInAnyBeanDeployment(specializingBean)) {
               Iterator var3 = result.iterator();

               while(var3.hasNext()) {
                  AbstractBean specializedBean = (AbstractBean)var3.next();
                  ((LongAdder)SpecializationAndEnablementRegistry.this.specializedBeansMap.computeIfAbsent(specializedBean, (key) -> {
                     return new LongAdder();
                  })).increment();
               }
            }

            return result;
         }
      }

      private Set apply(AbstractClassBean bean) {
         return this.getSpecializedBeanResolver(bean).resolveSpecializedBeans(bean);
      }

      private Set apply(ProducerMethod bean) {
         return this.getSpecializedBeanResolver(bean).resolveSpecializedBeans(bean);
      }

      private SpecializedBeanResolver getSpecializedBeanResolver(RIBean bean) {
         return (SpecializedBeanResolver)SpecializationAndEnablementRegistry.this.specializedBeanResolvers.getValue(bean.getBeanManager());
      }

      // $FF: synthetic method
      BeansSpecializedByBean(Object x1) {
         this();
      }
   }

   private class SpecializedBeanResolverForBeanManager implements Function {
      private SpecializedBeanResolverForBeanManager() {
      }

      public SpecializedBeanResolver apply(BeanManagerImpl manager) {
         return new SpecializedBeanResolver(this.buildAccessibleBeanDeployerEnvironments(manager));
      }

      private Set buildAccessibleBeanDeployerEnvironments(BeanManagerImpl manager) {
         Set result = new HashSet();
         result.add(SpecializationAndEnablementRegistry.this.environmentByManager.get(manager));
         this.buildAccessibleBeanDeployerEnvironments(manager, result);
         return result;
      }

      private void buildAccessibleBeanDeployerEnvironments(BeanManagerImpl manager, Collection result) {
         Iterator var3 = manager.getAccessibleManagers().iterator();

         while(var3.hasNext()) {
            BeanManagerImpl accessibleManager = (BeanManagerImpl)var3.next();
            BeanDeployerEnvironment environment = (BeanDeployerEnvironment)SpecializationAndEnablementRegistry.this.environmentByManager.get(accessibleManager);
            if (!result.contains(environment)) {
               result.add(environment);
               this.buildAccessibleBeanDeployerEnvironments(accessibleManager, result);
            }
         }

      }

      // $FF: synthetic method
      SpecializedBeanResolverForBeanManager(Object x1) {
         this();
      }
   }
}
