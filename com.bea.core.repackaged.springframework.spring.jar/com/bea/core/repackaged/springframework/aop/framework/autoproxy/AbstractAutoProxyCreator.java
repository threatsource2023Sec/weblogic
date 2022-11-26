package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.framework.AopInfrastructureBean;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.framework.ProxyProcessorSupport;
import com.bea.core.repackaged.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.aop.target.SingletonTargetSource;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractAutoProxyCreator extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {
   @Nullable
   protected static final Object[] DO_NOT_PROXY = null;
   protected static final Object[] PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS = new Object[0];
   protected final Log logger = LogFactory.getLog(this.getClass());
   private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
   private boolean freezeProxy = false;
   private String[] interceptorNames = new String[0];
   private boolean applyCommonInterceptorsFirst = true;
   @Nullable
   private TargetSourceCreator[] customTargetSourceCreators;
   @Nullable
   private BeanFactory beanFactory;
   private final Set targetSourcedBeans = Collections.newSetFromMap(new ConcurrentHashMap(16));
   private final Map earlyProxyReferences = new ConcurrentHashMap(16);
   private final Map proxyTypes = new ConcurrentHashMap(16);
   private final Map advisedBeans = new ConcurrentHashMap(256);

   public void setFrozen(boolean frozen) {
      this.freezeProxy = frozen;
   }

   public boolean isFrozen() {
      return this.freezeProxy;
   }

   public void setAdvisorAdapterRegistry(AdvisorAdapterRegistry advisorAdapterRegistry) {
      this.advisorAdapterRegistry = advisorAdapterRegistry;
   }

   public void setCustomTargetSourceCreators(TargetSourceCreator... targetSourceCreators) {
      this.customTargetSourceCreators = targetSourceCreators;
   }

   public void setInterceptorNames(String... interceptorNames) {
      this.interceptorNames = interceptorNames;
   }

   public void setApplyCommonInterceptorsFirst(boolean applyCommonInterceptorsFirst) {
      this.applyCommonInterceptorsFirst = applyCommonInterceptorsFirst;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   @Nullable
   protected BeanFactory getBeanFactory() {
      return this.beanFactory;
   }

   @Nullable
   public Class predictBeanType(Class beanClass, String beanName) {
      if (this.proxyTypes.isEmpty()) {
         return null;
      } else {
         Object cacheKey = this.getCacheKey(beanClass, beanName);
         return (Class)this.proxyTypes.get(cacheKey);
      }
   }

   @Nullable
   public Constructor[] determineCandidateConstructors(Class beanClass, String beanName) {
      return null;
   }

   public Object getEarlyBeanReference(Object bean, String beanName) {
      Object cacheKey = this.getCacheKey(bean.getClass(), beanName);
      this.earlyProxyReferences.put(cacheKey, bean);
      return this.wrapIfNecessary(bean, beanName, cacheKey);
   }

   public Object postProcessBeforeInstantiation(Class beanClass, String beanName) {
      Object cacheKey = this.getCacheKey(beanClass, beanName);
      if (!StringUtils.hasLength(beanName) || !this.targetSourcedBeans.contains(beanName)) {
         if (this.advisedBeans.containsKey(cacheKey)) {
            return null;
         }

         if (this.isInfrastructureClass(beanClass) || this.shouldSkip(beanClass, beanName)) {
            this.advisedBeans.put(cacheKey, Boolean.FALSE);
            return null;
         }
      }

      TargetSource targetSource = this.getCustomTargetSource(beanClass, beanName);
      if (targetSource != null) {
         if (StringUtils.hasLength(beanName)) {
            this.targetSourcedBeans.add(beanName);
         }

         Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
         Object proxy = this.createProxy(beanClass, beanName, specificInterceptors, targetSource);
         this.proxyTypes.put(cacheKey, proxy.getClass());
         return proxy;
      } else {
         return null;
      }
   }

   public boolean postProcessAfterInstantiation(Object bean, String beanName) {
      return true;
   }

   public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
      return pvs;
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) {
      return bean;
   }

   public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
      if (bean != null) {
         Object cacheKey = this.getCacheKey(bean.getClass(), beanName);
         if (this.earlyProxyReferences.remove(cacheKey) != bean) {
            return this.wrapIfNecessary(bean, beanName, cacheKey);
         }
      }

      return bean;
   }

   protected Object getCacheKey(Class beanClass, @Nullable String beanName) {
      if (StringUtils.hasLength(beanName)) {
         return FactoryBean.class.isAssignableFrom(beanClass) ? "&" + beanName : beanName;
      } else {
         return beanClass;
      }
   }

   protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
      if (StringUtils.hasLength(beanName) && this.targetSourcedBeans.contains(beanName)) {
         return bean;
      } else if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
         return bean;
      } else if (!this.isInfrastructureClass(bean.getClass()) && !this.shouldSkip(bean.getClass(), beanName)) {
         Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, (TargetSource)null);
         if (specificInterceptors != DO_NOT_PROXY) {
            this.advisedBeans.put(cacheKey, Boolean.TRUE);
            Object proxy = this.createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
            this.proxyTypes.put(cacheKey, proxy.getClass());
            return proxy;
         } else {
            this.advisedBeans.put(cacheKey, Boolean.FALSE);
            return bean;
         }
      } else {
         this.advisedBeans.put(cacheKey, Boolean.FALSE);
         return bean;
      }
   }

   protected boolean isInfrastructureClass(Class beanClass) {
      boolean retVal = Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass) || AopInfrastructureBean.class.isAssignableFrom(beanClass);
      if (retVal && this.logger.isTraceEnabled()) {
         this.logger.trace("Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
      }

      return retVal;
   }

   protected boolean shouldSkip(Class beanClass, String beanName) {
      return AutoProxyUtils.isOriginalInstance(beanName, beanClass);
   }

   @Nullable
   protected TargetSource getCustomTargetSource(Class beanClass, String beanName) {
      if (this.customTargetSourceCreators != null && this.beanFactory != null && this.beanFactory.containsBean(beanName)) {
         TargetSourceCreator[] var3 = this.customTargetSourceCreators;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetSourceCreator tsc = var3[var5];
            TargetSource ts = tsc.getTargetSource(beanClass, beanName);
            if (ts != null) {
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("TargetSourceCreator [" + tsc + "] found custom TargetSource for bean with name '" + beanName + "'");
               }

               return ts;
            }
         }
      }

      return null;
   }

   protected Object createProxy(Class beanClass, @Nullable String beanName, @Nullable Object[] specificInterceptors, TargetSource targetSource) {
      if (this.beanFactory instanceof ConfigurableListableBeanFactory) {
         AutoProxyUtils.exposeTargetClass((ConfigurableListableBeanFactory)this.beanFactory, beanName, beanClass);
      }

      ProxyFactory proxyFactory = new ProxyFactory();
      proxyFactory.copyFrom(this);
      if (!proxyFactory.isProxyTargetClass()) {
         if (this.shouldProxyTargetClass(beanClass, beanName)) {
            proxyFactory.setProxyTargetClass(true);
         } else {
            this.evaluateProxyInterfaces(beanClass, proxyFactory);
         }
      }

      Advisor[] advisors = this.buildAdvisors(beanName, specificInterceptors);
      proxyFactory.addAdvisors(advisors);
      proxyFactory.setTargetSource(targetSource);
      this.customizeProxyFactory(proxyFactory);
      proxyFactory.setFrozen(this.freezeProxy);
      if (this.advisorsPreFiltered()) {
         proxyFactory.setPreFiltered(true);
      }

      return proxyFactory.getProxy(this.getProxyClassLoader());
   }

   protected boolean shouldProxyTargetClass(Class beanClass, @Nullable String beanName) {
      return this.beanFactory instanceof ConfigurableListableBeanFactory && AutoProxyUtils.shouldProxyTargetClass((ConfigurableListableBeanFactory)this.beanFactory, beanName);
   }

   protected boolean advisorsPreFiltered() {
      return false;
   }

   protected Advisor[] buildAdvisors(@Nullable String beanName, @Nullable Object[] specificInterceptors) {
      Advisor[] commonInterceptors = this.resolveInterceptorNames();
      List allInterceptors = new ArrayList();
      if (specificInterceptors != null) {
         allInterceptors.addAll(Arrays.asList(specificInterceptors));
         if (commonInterceptors.length > 0) {
            if (this.applyCommonInterceptorsFirst) {
               allInterceptors.addAll(0, Arrays.asList(commonInterceptors));
            } else {
               allInterceptors.addAll(Arrays.asList(commonInterceptors));
            }
         }
      }

      int i;
      if (this.logger.isTraceEnabled()) {
         int nrOfCommonInterceptors = commonInterceptors.length;
         i = specificInterceptors != null ? specificInterceptors.length : 0;
         this.logger.trace("Creating implicit proxy for bean '" + beanName + "' with " + nrOfCommonInterceptors + " common interceptors and " + i + " specific interceptors");
      }

      Advisor[] advisors = new Advisor[allInterceptors.size()];

      for(i = 0; i < allInterceptors.size(); ++i) {
         advisors[i] = this.advisorAdapterRegistry.wrap(allInterceptors.get(i));
      }

      return advisors;
   }

   private Advisor[] resolveInterceptorNames() {
      BeanFactory bf = this.beanFactory;
      ConfigurableBeanFactory cbf = bf instanceof ConfigurableBeanFactory ? (ConfigurableBeanFactory)bf : null;
      List advisors = new ArrayList();
      String[] var4 = this.interceptorNames;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String beanName = var4[var6];
         if (cbf == null || !cbf.isCurrentlyInCreation(beanName)) {
            Assert.state(bf != null, "BeanFactory required for resolving interceptor names");
            Object next = bf.getBean(beanName);
            advisors.add(this.advisorAdapterRegistry.wrap(next));
         }
      }

      return (Advisor[])advisors.toArray(new Advisor[0]);
   }

   protected void customizeProxyFactory(ProxyFactory proxyFactory) {
   }

   @Nullable
   protected abstract Object[] getAdvicesAndAdvisorsForBean(Class var1, String var2, @Nullable TargetSource var3) throws BeansException;
}
