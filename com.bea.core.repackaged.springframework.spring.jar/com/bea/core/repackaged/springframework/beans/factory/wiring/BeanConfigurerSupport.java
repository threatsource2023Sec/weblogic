package com.bea.core.repackaged.springframework.beans.factory.wiring;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCurrentlyInCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class BeanConfigurerSupport implements BeanFactoryAware, InitializingBean, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private volatile BeanWiringInfoResolver beanWiringInfoResolver;
   @Nullable
   private volatile ConfigurableListableBeanFactory beanFactory;

   public void setBeanWiringInfoResolver(BeanWiringInfoResolver beanWiringInfoResolver) {
      Assert.notNull(beanWiringInfoResolver, (String)"BeanWiringInfoResolver must not be null");
      this.beanWiringInfoResolver = beanWiringInfoResolver;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
         throw new IllegalArgumentException("Bean configurer aspect needs to run in a ConfigurableListableBeanFactory: " + beanFactory);
      } else {
         this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
         if (this.beanWiringInfoResolver == null) {
            this.beanWiringInfoResolver = this.createDefaultBeanWiringInfoResolver();
         }

      }
   }

   @Nullable
   protected BeanWiringInfoResolver createDefaultBeanWiringInfoResolver() {
      return new ClassNameBeanWiringInfoResolver();
   }

   public void afterPropertiesSet() {
      Assert.notNull(this.beanFactory, (String)"BeanFactory must be set");
   }

   public void destroy() {
      this.beanFactory = null;
      this.beanWiringInfoResolver = null;
   }

   public void configureBean(Object beanInstance) {
      if (this.beanFactory == null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("BeanFactory has not been set on " + ClassUtils.getShortName(this.getClass()) + ": Make sure this configurer runs in a Spring container. Unable to configure bean of type [" + ClassUtils.getDescriptiveType(beanInstance) + "]. Proceeding without injection.");
         }

      } else {
         BeanWiringInfoResolver bwiResolver = this.beanWiringInfoResolver;
         Assert.state(bwiResolver != null, "No BeanWiringInfoResolver available");
         BeanWiringInfo bwi = bwiResolver.resolveWiringInfo(beanInstance);
         if (bwi != null) {
            ConfigurableListableBeanFactory beanFactory = this.beanFactory;
            Assert.state(beanFactory != null, "No BeanFactory available");

            try {
               String beanName = bwi.getBeanName();
               if (!bwi.indicatesAutowiring() && (!bwi.isDefaultBeanName() || beanName == null || beanFactory.containsBean(beanName))) {
                  beanFactory.configureBean(beanInstance, beanName != null ? beanName : "");
               } else {
                  beanFactory.autowireBeanProperties(beanInstance, bwi.getAutowireMode(), bwi.getDependencyCheck());
                  beanFactory.initializeBean(beanInstance, beanName != null ? beanName : "");
               }

            } catch (BeanCreationException var9) {
               Throwable rootCause = var9.getMostSpecificCause();
               if (rootCause instanceof BeanCurrentlyInCreationException) {
                  BeanCreationException bce = (BeanCreationException)rootCause;
                  String bceBeanName = bce.getBeanName();
                  if (bceBeanName != null && beanFactory.isCurrentlyInCreation(bceBeanName)) {
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Failed to create target bean '" + bce.getBeanName() + "' while configuring object of type [" + beanInstance.getClass().getName() + "] - probably due to a circular reference. This is a common startup situation and usually not fatal. Proceeding without injection. Original exception: " + var9);
                     }

                     return;
                  }
               }

               throw var9;
            }
         }
      }
   }
}
