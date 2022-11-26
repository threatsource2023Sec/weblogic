package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class DefaultAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator implements BeanNameAware {
   public static final String SEPARATOR = ".";
   private boolean usePrefix = false;
   @Nullable
   private String advisorBeanNamePrefix;

   public void setUsePrefix(boolean usePrefix) {
      this.usePrefix = usePrefix;
   }

   public boolean isUsePrefix() {
      return this.usePrefix;
   }

   public void setAdvisorBeanNamePrefix(@Nullable String advisorBeanNamePrefix) {
      this.advisorBeanNamePrefix = advisorBeanNamePrefix;
   }

   @Nullable
   public String getAdvisorBeanNamePrefix() {
      return this.advisorBeanNamePrefix;
   }

   public void setBeanName(String name) {
      if (this.advisorBeanNamePrefix == null) {
         this.advisorBeanNamePrefix = name + ".";
      }

   }

   protected boolean isEligibleAdvisorBean(String beanName) {
      if (!this.isUsePrefix()) {
         return true;
      } else {
         String prefix = this.getAdvisorBeanNamePrefix();
         return prefix != null && beanName.startsWith(prefix);
      }
   }
}
