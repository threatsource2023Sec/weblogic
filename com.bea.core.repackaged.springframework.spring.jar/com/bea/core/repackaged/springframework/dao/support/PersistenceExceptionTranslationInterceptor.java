package com.bea.core.repackaged.springframework.dao.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.util.Iterator;
import java.util.Map;

public class PersistenceExceptionTranslationInterceptor implements MethodInterceptor, BeanFactoryAware, InitializingBean {
   @Nullable
   private volatile PersistenceExceptionTranslator persistenceExceptionTranslator;
   private boolean alwaysTranslate = false;
   @Nullable
   private ListableBeanFactory beanFactory;

   public PersistenceExceptionTranslationInterceptor() {
   }

   public PersistenceExceptionTranslationInterceptor(PersistenceExceptionTranslator pet) {
      Assert.notNull(pet, (String)"PersistenceExceptionTranslator must not be null");
      this.persistenceExceptionTranslator = pet;
   }

   public PersistenceExceptionTranslationInterceptor(ListableBeanFactory beanFactory) {
      Assert.notNull(beanFactory, (String)"ListableBeanFactory must not be null");
      this.beanFactory = beanFactory;
   }

   public void setPersistenceExceptionTranslator(PersistenceExceptionTranslator pet) {
      this.persistenceExceptionTranslator = pet;
   }

   public void setAlwaysTranslate(boolean alwaysTranslate) {
      this.alwaysTranslate = alwaysTranslate;
   }

   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      if (this.persistenceExceptionTranslator == null) {
         if (!(beanFactory instanceof ListableBeanFactory)) {
            throw new IllegalArgumentException("Cannot use PersistenceExceptionTranslator autodetection without ListableBeanFactory");
         }

         this.beanFactory = (ListableBeanFactory)beanFactory;
      }

   }

   public void afterPropertiesSet() {
      if (this.persistenceExceptionTranslator == null && this.beanFactory == null) {
         throw new IllegalArgumentException("Property 'persistenceExceptionTranslator' is required");
      }
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      try {
         return mi.proceed();
      } catch (RuntimeException var4) {
         if (!this.alwaysTranslate && ReflectionUtils.declaresException(mi.getMethod(), var4.getClass())) {
            throw var4;
         } else {
            PersistenceExceptionTranslator translator = this.persistenceExceptionTranslator;
            if (translator == null) {
               Assert.state(this.beanFactory != null, "No PersistenceExceptionTranslator set");
               translator = this.detectPersistenceExceptionTranslators(this.beanFactory);
               this.persistenceExceptionTranslator = translator;
            }

            throw DataAccessUtils.translateIfNecessary(var4, translator);
         }
      }
   }

   protected PersistenceExceptionTranslator detectPersistenceExceptionTranslators(ListableBeanFactory beanFactory) {
      Map pets = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, PersistenceExceptionTranslator.class, false, false);
      ChainedPersistenceExceptionTranslator cpet = new ChainedPersistenceExceptionTranslator();
      Iterator var4 = pets.values().iterator();

      while(var4.hasNext()) {
         PersistenceExceptionTranslator pet = (PersistenceExceptionTranslator)var4.next();
         cpet.addDelegate(pet);
      }

      return cpet;
   }
}
