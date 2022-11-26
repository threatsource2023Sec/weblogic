package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aspectj.lang.annotation.After;
import com.bea.core.repackaged.aspectj.lang.annotation.AfterReturning;
import com.bea.core.repackaged.aspectj.lang.annotation.AfterThrowing;
import com.bea.core.repackaged.aspectj.lang.annotation.Around;
import com.bea.core.repackaged.aspectj.lang.annotation.Before;
import com.bea.core.repackaged.aspectj.lang.annotation.DeclareParents;
import com.bea.core.repackaged.aspectj.lang.annotation.Pointcut;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.aspectj.AbstractAspectJAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAfterAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAfterReturningAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAfterThrowingAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAroundAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.DeclareParentsAdvisor;
import com.bea.core.repackaged.springframework.aop.framework.AopConfigException;
import com.bea.core.repackaged.springframework.aop.support.DefaultPointcutAdvisor;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.core.convert.converter.ConvertingComparator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.comparator.InstanceComparator;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ReflectiveAspectJAdvisorFactory extends AbstractAspectJAdvisorFactory implements Serializable {
   private static final Comparator METHOD_COMPARATOR;
   @Nullable
   private final BeanFactory beanFactory;

   public ReflectiveAspectJAdvisorFactory() {
      this((BeanFactory)null);
   }

   public ReflectiveAspectJAdvisorFactory(@Nullable BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   public List getAdvisors(MetadataAwareAspectInstanceFactory aspectInstanceFactory) {
      Class aspectClass = aspectInstanceFactory.getAspectMetadata().getAspectClass();
      String aspectName = aspectInstanceFactory.getAspectMetadata().getAspectName();
      this.validate(aspectClass);
      MetadataAwareAspectInstanceFactory lazySingletonAspectInstanceFactory = new LazySingletonAspectInstanceFactoryDecorator(aspectInstanceFactory);
      List advisors = new ArrayList();
      Iterator var6 = this.getAdvisorMethods(aspectClass).iterator();

      while(var6.hasNext()) {
         Method method = (Method)var6.next();
         Advisor advisor = this.getAdvisor(method, lazySingletonAspectInstanceFactory, advisors.size(), aspectName);
         if (advisor != null) {
            advisors.add(advisor);
         }
      }

      if (!advisors.isEmpty() && lazySingletonAspectInstanceFactory.getAspectMetadata().isLazilyInstantiated()) {
         Advisor instantiationAdvisor = new SyntheticInstantiationAdvisor(lazySingletonAspectInstanceFactory);
         advisors.add(0, instantiationAdvisor);
      }

      Field[] var12 = aspectClass.getDeclaredFields();
      int var13 = var12.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         Field field = var12[var14];
         Advisor advisor = this.getDeclareParentsAdvisor(field);
         if (advisor != null) {
            advisors.add(advisor);
         }
      }

      return advisors;
   }

   private List getAdvisorMethods(Class aspectClass) {
      List methods = new ArrayList();
      ReflectionUtils.doWithMethods(aspectClass, (method) -> {
         if (AnnotationUtils.getAnnotation(method, Pointcut.class) == null) {
            methods.add(method);
         }

      });
      methods.sort(METHOD_COMPARATOR);
      return methods;
   }

   @Nullable
   private Advisor getDeclareParentsAdvisor(Field introductionField) {
      DeclareParents declareParents = (DeclareParents)introductionField.getAnnotation(DeclareParents.class);
      if (declareParents == null) {
         return null;
      } else if (DeclareParents.class == declareParents.defaultImpl()) {
         throw new IllegalStateException("'defaultImpl' attribute must be set on DeclareParents");
      } else {
         return new DeclareParentsAdvisor(introductionField.getType(), declareParents.value(), declareParents.defaultImpl());
      }
   }

   @Nullable
   public Advisor getAdvisor(Method candidateAdviceMethod, MetadataAwareAspectInstanceFactory aspectInstanceFactory, int declarationOrderInAspect, String aspectName) {
      this.validate(aspectInstanceFactory.getAspectMetadata().getAspectClass());
      AspectJExpressionPointcut expressionPointcut = this.getPointcut(candidateAdviceMethod, aspectInstanceFactory.getAspectMetadata().getAspectClass());
      return expressionPointcut == null ? null : new InstantiationModelAwarePointcutAdvisorImpl(expressionPointcut, candidateAdviceMethod, this, aspectInstanceFactory, declarationOrderInAspect, aspectName);
   }

   @Nullable
   private AspectJExpressionPointcut getPointcut(Method candidateAdviceMethod, Class candidateAspectClass) {
      AbstractAspectJAdvisorFactory.AspectJAnnotation aspectJAnnotation = AbstractAspectJAdvisorFactory.findAspectJAnnotationOnMethod(candidateAdviceMethod);
      if (aspectJAnnotation == null) {
         return null;
      } else {
         AspectJExpressionPointcut ajexp = new AspectJExpressionPointcut(candidateAspectClass, new String[0], new Class[0]);
         ajexp.setExpression(aspectJAnnotation.getPointcutExpression());
         if (this.beanFactory != null) {
            ajexp.setBeanFactory(this.beanFactory);
         }

         return ajexp;
      }
   }

   @Nullable
   public Advice getAdvice(Method candidateAdviceMethod, AspectJExpressionPointcut expressionPointcut, MetadataAwareAspectInstanceFactory aspectInstanceFactory, int declarationOrder, String aspectName) {
      Class candidateAspectClass = aspectInstanceFactory.getAspectMetadata().getAspectClass();
      this.validate(candidateAspectClass);
      AbstractAspectJAdvisorFactory.AspectJAnnotation aspectJAnnotation = AbstractAspectJAdvisorFactory.findAspectJAnnotationOnMethod(candidateAdviceMethod);
      if (aspectJAnnotation == null) {
         return null;
      } else if (!this.isAspect(candidateAspectClass)) {
         throw new AopConfigException("Advice must be declared inside an aspect type: Offending method '" + candidateAdviceMethod + "' in class [" + candidateAspectClass.getName() + "]");
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Found AspectJ method: " + candidateAdviceMethod);
         }

         Object springAdvice;
         switch (aspectJAnnotation.getAnnotationType()) {
            case AtPointcut:
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Processing pointcut '" + candidateAdviceMethod.getName() + "'");
               }

               return null;
            case AtAround:
               springAdvice = new AspectJAroundAdvice(candidateAdviceMethod, expressionPointcut, aspectInstanceFactory);
               break;
            case AtBefore:
               springAdvice = new AspectJMethodBeforeAdvice(candidateAdviceMethod, expressionPointcut, aspectInstanceFactory);
               break;
            case AtAfter:
               springAdvice = new AspectJAfterAdvice(candidateAdviceMethod, expressionPointcut, aspectInstanceFactory);
               break;
            case AtAfterReturning:
               springAdvice = new AspectJAfterReturningAdvice(candidateAdviceMethod, expressionPointcut, aspectInstanceFactory);
               AfterReturning afterReturningAnnotation = (AfterReturning)aspectJAnnotation.getAnnotation();
               if (StringUtils.hasText(afterReturningAnnotation.returning())) {
                  ((AbstractAspectJAdvice)springAdvice).setReturningName(afterReturningAnnotation.returning());
               }
               break;
            case AtAfterThrowing:
               springAdvice = new AspectJAfterThrowingAdvice(candidateAdviceMethod, expressionPointcut, aspectInstanceFactory);
               AfterThrowing afterThrowingAnnotation = (AfterThrowing)aspectJAnnotation.getAnnotation();
               if (StringUtils.hasText(afterThrowingAnnotation.throwing())) {
                  ((AbstractAspectJAdvice)springAdvice).setThrowingName(afterThrowingAnnotation.throwing());
               }
               break;
            default:
               throw new UnsupportedOperationException("Unsupported advice type on method: " + candidateAdviceMethod);
         }

         ((AbstractAspectJAdvice)springAdvice).setAspectName(aspectName);
         ((AbstractAspectJAdvice)springAdvice).setDeclarationOrder(declarationOrder);
         String[] argNames = this.parameterNameDiscoverer.getParameterNames(candidateAdviceMethod);
         if (argNames != null) {
            ((AbstractAspectJAdvice)springAdvice).setArgumentNamesFromStringArray(argNames);
         }

         ((AbstractAspectJAdvice)springAdvice).calculateArgumentBindings();
         return (Advice)springAdvice;
      }
   }

   static {
      Comparator adviceKindComparator = new ConvertingComparator(new InstanceComparator(new Class[]{Around.class, Before.class, After.class, AfterReturning.class, AfterThrowing.class}), (method) -> {
         AbstractAspectJAdvisorFactory.AspectJAnnotation annotation = AbstractAspectJAdvisorFactory.findAspectJAnnotationOnMethod(method);
         return annotation != null ? annotation.getAnnotation() : null;
      });
      Comparator methodNameComparator = new ConvertingComparator(Method::getName);
      METHOD_COMPARATOR = adviceKindComparator.thenComparing(methodNameComparator);
   }

   protected static class SyntheticInstantiationAdvisor extends DefaultPointcutAdvisor {
      public SyntheticInstantiationAdvisor(MetadataAwareAspectInstanceFactory aif) {
         super(aif.getAspectMetadata().getPerClausePointcut(), (method, args, target) -> {
            aif.getAspectInstance();
         });
      }
   }
}
