package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.List;

public abstract class AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {
   @Nullable
   private BeanFactoryAdvisorRetrievalHelper advisorRetrievalHelper;

   public void setBeanFactory(BeanFactory beanFactory) {
      super.setBeanFactory(beanFactory);
      if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
         throw new IllegalArgumentException("AdvisorAutoProxyCreator requires a ConfigurableListableBeanFactory: " + beanFactory);
      } else {
         this.initBeanFactory((ConfigurableListableBeanFactory)beanFactory);
      }
   }

   protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
      this.advisorRetrievalHelper = new BeanFactoryAdvisorRetrievalHelperAdapter(beanFactory);
   }

   @Nullable
   protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, @Nullable TargetSource targetSource) {
      List advisors = this.findEligibleAdvisors(beanClass, beanName);
      return advisors.isEmpty() ? DO_NOT_PROXY : advisors.toArray();
   }

   protected List findEligibleAdvisors(Class beanClass, String beanName) {
      List candidateAdvisors = this.findCandidateAdvisors();
      List eligibleAdvisors = this.findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);
      this.extendAdvisors(eligibleAdvisors);
      if (!eligibleAdvisors.isEmpty()) {
         eligibleAdvisors = this.sortAdvisors(eligibleAdvisors);
      }

      return eligibleAdvisors;
   }

   protected List findCandidateAdvisors() {
      Assert.state(this.advisorRetrievalHelper != null, "No BeanFactoryAdvisorRetrievalHelper available");
      return this.advisorRetrievalHelper.findAdvisorBeans();
   }

   protected List findAdvisorsThatCanApply(List candidateAdvisors, Class beanClass, String beanName) {
      ProxyCreationContext.setCurrentProxiedBeanName(beanName);

      List var4;
      try {
         var4 = AopUtils.findAdvisorsThatCanApply(candidateAdvisors, beanClass);
      } finally {
         ProxyCreationContext.setCurrentProxiedBeanName((String)null);
      }

      return var4;
   }

   protected boolean isEligibleAdvisorBean(String beanName) {
      return true;
   }

   protected List sortAdvisors(List advisors) {
      AnnotationAwareOrderComparator.sort(advisors);
      return advisors;
   }

   protected void extendAdvisors(List candidateAdvisors) {
   }

   protected boolean advisorsPreFiltered() {
      return true;
   }

   private class BeanFactoryAdvisorRetrievalHelperAdapter extends BeanFactoryAdvisorRetrievalHelper {
      public BeanFactoryAdvisorRetrievalHelperAdapter(ConfigurableListableBeanFactory beanFactory) {
         super(beanFactory);
      }

      protected boolean isEligibleBean(String beanName) {
         return AbstractAdvisorAutoProxyCreator.this.isEligibleAdvisorBean(beanName);
      }
   }
}
