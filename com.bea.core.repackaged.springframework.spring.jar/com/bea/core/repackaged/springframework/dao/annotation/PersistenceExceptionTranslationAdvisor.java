package com.bea.core.repackaged.springframework.dao.annotation;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.support.AbstractPointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.dao.support.PersistenceExceptionTranslationInterceptor;
import com.bea.core.repackaged.springframework.dao.support.PersistenceExceptionTranslator;

public class PersistenceExceptionTranslationAdvisor extends AbstractPointcutAdvisor {
   private final PersistenceExceptionTranslationInterceptor advice;
   private final AnnotationMatchingPointcut pointcut;

   public PersistenceExceptionTranslationAdvisor(PersistenceExceptionTranslator persistenceExceptionTranslator, Class repositoryAnnotationType) {
      this.advice = new PersistenceExceptionTranslationInterceptor(persistenceExceptionTranslator);
      this.pointcut = new AnnotationMatchingPointcut(repositoryAnnotationType, true);
   }

   PersistenceExceptionTranslationAdvisor(ListableBeanFactory beanFactory, Class repositoryAnnotationType) {
      this.advice = new PersistenceExceptionTranslationInterceptor(beanFactory);
      this.pointcut = new AnnotationMatchingPointcut(repositoryAnnotationType, true);
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }
}
