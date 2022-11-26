package com.bea.core.repackaged.springframework.aop.framework.autoproxy.target;

import com.bea.core.repackaged.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;
import com.bea.core.repackaged.springframework.aop.target.CommonsPool2TargetSource;
import com.bea.core.repackaged.springframework.aop.target.PrototypeTargetSource;
import com.bea.core.repackaged.springframework.aop.target.ThreadLocalTargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class QuickTargetSourceCreator extends AbstractBeanFactoryBasedTargetSourceCreator {
   public static final String PREFIX_COMMONS_POOL = ":";
   public static final String PREFIX_THREAD_LOCAL = "%";
   public static final String PREFIX_PROTOTYPE = "!";

   @Nullable
   protected final AbstractBeanFactoryBasedTargetSource createBeanFactoryBasedTargetSource(Class beanClass, String beanName) {
      if (beanName.startsWith(":")) {
         CommonsPool2TargetSource cpts = new CommonsPool2TargetSource();
         cpts.setMaxSize(25);
         return cpts;
      } else if (beanName.startsWith("%")) {
         return new ThreadLocalTargetSource();
      } else {
         return beanName.startsWith("!") ? new PrototypeTargetSource() : null;
      }
   }
}
