package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator {
   @Nullable
   private List includePatterns;
   @Nullable
   private AspectJAdvisorFactory aspectJAdvisorFactory;
   @Nullable
   private BeanFactoryAspectJAdvisorsBuilder aspectJAdvisorsBuilder;

   public void setIncludePatterns(List patterns) {
      this.includePatterns = new ArrayList(patterns.size());
      Iterator var2 = patterns.iterator();

      while(var2.hasNext()) {
         String patternText = (String)var2.next();
         this.includePatterns.add(Pattern.compile(patternText));
      }

   }

   public void setAspectJAdvisorFactory(AspectJAdvisorFactory aspectJAdvisorFactory) {
      Assert.notNull(aspectJAdvisorFactory, (String)"AspectJAdvisorFactory must not be null");
      this.aspectJAdvisorFactory = aspectJAdvisorFactory;
   }

   protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
      super.initBeanFactory(beanFactory);
      if (this.aspectJAdvisorFactory == null) {
         this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
      }

      this.aspectJAdvisorsBuilder = new BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
   }

   protected List findCandidateAdvisors() {
      List advisors = super.findCandidateAdvisors();
      if (this.aspectJAdvisorsBuilder != null) {
         advisors.addAll(this.aspectJAdvisorsBuilder.buildAspectJAdvisors());
      }

      return advisors;
   }

   protected boolean isInfrastructureClass(Class beanClass) {
      return super.isInfrastructureClass(beanClass) || this.aspectJAdvisorFactory != null && this.aspectJAdvisorFactory.isAspect(beanClass);
   }

   protected boolean isEligibleAspectBean(String beanName) {
      if (this.includePatterns == null) {
         return true;
      } else {
         Iterator var2 = this.includePatterns.iterator();

         Pattern pattern;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            pattern = (Pattern)var2.next();
         } while(!pattern.matcher(beanName).matches());

         return true;
      }
   }

   private class BeanFactoryAspectJAdvisorsBuilderAdapter extends BeanFactoryAspectJAdvisorsBuilder {
      public BeanFactoryAspectJAdvisorsBuilderAdapter(ListableBeanFactory beanFactory, AspectJAdvisorFactory advisorFactory) {
         super(beanFactory, advisorFactory);
      }

      protected boolean isEligibleBean(String beanName) {
         return AnnotationAwareAspectJAutoProxyCreator.this.isEligibleAspectBean(beanName);
      }
   }
}
