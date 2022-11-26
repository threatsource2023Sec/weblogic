package com.bea.core.repackaged.springframework.dao.annotation;

import com.bea.core.repackaged.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.stereotype.Repository;
import com.bea.core.repackaged.springframework.util.Assert;

public class PersistenceExceptionTranslationPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
   private Class repositoryAnnotationType = Repository.class;

   public void setRepositoryAnnotationType(Class repositoryAnnotationType) {
      Assert.notNull(repositoryAnnotationType, (String)"'repositoryAnnotationType' must not be null");
      this.repositoryAnnotationType = repositoryAnnotationType;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      super.setBeanFactory(beanFactory);
      if (!(beanFactory instanceof ListableBeanFactory)) {
         throw new IllegalArgumentException("Cannot use PersistenceExceptionTranslator autodetection without ListableBeanFactory");
      } else {
         this.advisor = new PersistenceExceptionTranslationAdvisor((ListableBeanFactory)beanFactory, this.repositoryAnnotationType);
      }
   }
}
