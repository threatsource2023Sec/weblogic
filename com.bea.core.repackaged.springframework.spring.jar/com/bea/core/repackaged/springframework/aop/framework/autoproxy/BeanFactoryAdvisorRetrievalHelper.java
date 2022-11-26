package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCurrentlyInCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.List;

public class BeanFactoryAdvisorRetrievalHelper {
   private static final Log logger = LogFactory.getLog(BeanFactoryAdvisorRetrievalHelper.class);
   private final ConfigurableListableBeanFactory beanFactory;
   @Nullable
   private volatile String[] cachedAdvisorBeanNames;

   public BeanFactoryAdvisorRetrievalHelper(ConfigurableListableBeanFactory beanFactory) {
      Assert.notNull(beanFactory, (String)"ListableBeanFactory must not be null");
      this.beanFactory = beanFactory;
   }

   public List findAdvisorBeans() {
      String[] advisorNames = this.cachedAdvisorBeanNames;
      if (advisorNames == null) {
         advisorNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this.beanFactory, Advisor.class, true, false);
         this.cachedAdvisorBeanNames = advisorNames;
      }

      if (advisorNames.length == 0) {
         return new ArrayList();
      } else {
         List advisors = new ArrayList();
         String[] var3 = advisorNames;
         int var4 = advisorNames.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String name = var3[var5];
            if (this.isEligibleBean(name)) {
               if (this.beanFactory.isCurrentlyInCreation(name)) {
                  if (logger.isTraceEnabled()) {
                     logger.trace("Skipping currently created advisor '" + name + "'");
                  }
               } else {
                  try {
                     advisors.add(this.beanFactory.getBean(name, Advisor.class));
                  } catch (BeanCreationException var11) {
                     Throwable rootCause = var11.getMostSpecificCause();
                     if (rootCause instanceof BeanCurrentlyInCreationException) {
                        BeanCreationException bce = (BeanCreationException)rootCause;
                        String bceBeanName = bce.getBeanName();
                        if (bceBeanName != null && this.beanFactory.isCurrentlyInCreation(bceBeanName)) {
                           if (logger.isTraceEnabled()) {
                              logger.trace("Skipping advisor '" + name + "' with dependency on currently created bean: " + var11.getMessage());
                           }
                           continue;
                        }
                     }

                     throw var11;
                  }
               }
            }
         }

         return advisors;
      }
   }

   protected boolean isEligibleBean(String beanName) {
      return true;
   }
}
