package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJPrecedenceInformation;
import com.bea.core.repackaged.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DynamicMethodMatcherPointcut;
import com.bea.core.repackaged.springframework.aop.support.Pointcuts;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

final class InstantiationModelAwarePointcutAdvisorImpl implements InstantiationModelAwarePointcutAdvisor, AspectJPrecedenceInformation, Serializable {
   private static final Advice EMPTY_ADVICE = new Advice() {
   };
   private final AspectJExpressionPointcut declaredPointcut;
   private final Class declaringClass;
   private final String methodName;
   private final Class[] parameterTypes;
   private transient Method aspectJAdviceMethod;
   private final AspectJAdvisorFactory aspectJAdvisorFactory;
   private final MetadataAwareAspectInstanceFactory aspectInstanceFactory;
   private final int declarationOrder;
   private final String aspectName;
   private final Pointcut pointcut;
   private final boolean lazy;
   @Nullable
   private Advice instantiatedAdvice;
   @Nullable
   private Boolean isBeforeAdvice;
   @Nullable
   private Boolean isAfterAdvice;

   public InstantiationModelAwarePointcutAdvisorImpl(AspectJExpressionPointcut declaredPointcut, Method aspectJAdviceMethod, AspectJAdvisorFactory aspectJAdvisorFactory, MetadataAwareAspectInstanceFactory aspectInstanceFactory, int declarationOrder, String aspectName) {
      this.declaredPointcut = declaredPointcut;
      this.declaringClass = aspectJAdviceMethod.getDeclaringClass();
      this.methodName = aspectJAdviceMethod.getName();
      this.parameterTypes = aspectJAdviceMethod.getParameterTypes();
      this.aspectJAdviceMethod = aspectJAdviceMethod;
      this.aspectJAdvisorFactory = aspectJAdvisorFactory;
      this.aspectInstanceFactory = aspectInstanceFactory;
      this.declarationOrder = declarationOrder;
      this.aspectName = aspectName;
      if (aspectInstanceFactory.getAspectMetadata().isLazilyInstantiated()) {
         Pointcut preInstantiationPointcut = Pointcuts.union(aspectInstanceFactory.getAspectMetadata().getPerClausePointcut(), this.declaredPointcut);
         this.pointcut = new PerTargetInstantiationModelPointcut(this.declaredPointcut, preInstantiationPointcut, aspectInstanceFactory);
         this.lazy = true;
      } else {
         this.pointcut = this.declaredPointcut;
         this.lazy = false;
         this.instantiatedAdvice = this.instantiateAdvice(this.declaredPointcut);
      }

   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   public boolean isLazy() {
      return this.lazy;
   }

   public synchronized boolean isAdviceInstantiated() {
      return this.instantiatedAdvice != null;
   }

   public synchronized Advice getAdvice() {
      if (this.instantiatedAdvice == null) {
         this.instantiatedAdvice = this.instantiateAdvice(this.declaredPointcut);
      }

      return this.instantiatedAdvice;
   }

   private Advice instantiateAdvice(AspectJExpressionPointcut pointcut) {
      Advice advice = this.aspectJAdvisorFactory.getAdvice(this.aspectJAdviceMethod, pointcut, this.aspectInstanceFactory, this.declarationOrder, this.aspectName);
      return advice != null ? advice : EMPTY_ADVICE;
   }

   public boolean isPerInstance() {
      return this.getAspectMetadata().getAjType().getPerClause().getKind() != PerClauseKind.SINGLETON;
   }

   public AspectMetadata getAspectMetadata() {
      return this.aspectInstanceFactory.getAspectMetadata();
   }

   public MetadataAwareAspectInstanceFactory getAspectInstanceFactory() {
      return this.aspectInstanceFactory;
   }

   public AspectJExpressionPointcut getDeclaredPointcut() {
      return this.declaredPointcut;
   }

   public int getOrder() {
      return this.aspectInstanceFactory.getOrder();
   }

   public String getAspectName() {
      return this.aspectName;
   }

   public int getDeclarationOrder() {
      return this.declarationOrder;
   }

   public boolean isBeforeAdvice() {
      if (this.isBeforeAdvice == null) {
         this.determineAdviceType();
      }

      return this.isBeforeAdvice;
   }

   public boolean isAfterAdvice() {
      if (this.isAfterAdvice == null) {
         this.determineAdviceType();
      }

      return this.isAfterAdvice;
   }

   private void determineAdviceType() {
      AbstractAspectJAdvisorFactory.AspectJAnnotation aspectJAnnotation = AbstractAspectJAdvisorFactory.findAspectJAnnotationOnMethod(this.aspectJAdviceMethod);
      if (aspectJAnnotation == null) {
         this.isBeforeAdvice = false;
         this.isAfterAdvice = false;
      } else {
         switch (aspectJAnnotation.getAnnotationType()) {
            case AtPointcut:
            case AtAround:
               this.isBeforeAdvice = false;
               this.isAfterAdvice = false;
               break;
            case AtBefore:
               this.isBeforeAdvice = true;
               this.isAfterAdvice = false;
               break;
            case AtAfter:
            case AtAfterReturning:
            case AtAfterThrowing:
               this.isBeforeAdvice = false;
               this.isAfterAdvice = true;
         }
      }

   }

   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
      inputStream.defaultReadObject();

      try {
         this.aspectJAdviceMethod = this.declaringClass.getMethod(this.methodName, this.parameterTypes);
      } catch (NoSuchMethodException var3) {
         throw new IllegalStateException("Failed to find advice method on deserialization", var3);
      }
   }

   public String toString() {
      return "InstantiationModelAwarePointcutAdvisor: expression [" + this.getDeclaredPointcut().getExpression() + "]; advice method [" + this.aspectJAdviceMethod + "]; perClauseKind=" + this.aspectInstanceFactory.getAspectMetadata().getAjType().getPerClause().getKind();
   }

   private final class PerTargetInstantiationModelPointcut extends DynamicMethodMatcherPointcut {
      private final AspectJExpressionPointcut declaredPointcut;
      private final Pointcut preInstantiationPointcut;
      @Nullable
      private LazySingletonAspectInstanceFactoryDecorator aspectInstanceFactory;

      public PerTargetInstantiationModelPointcut(AspectJExpressionPointcut declaredPointcut, Pointcut preInstantiationPointcut, MetadataAwareAspectInstanceFactory aspectInstanceFactory) {
         this.declaredPointcut = declaredPointcut;
         this.preInstantiationPointcut = preInstantiationPointcut;
         if (aspectInstanceFactory instanceof LazySingletonAspectInstanceFactoryDecorator) {
            this.aspectInstanceFactory = (LazySingletonAspectInstanceFactoryDecorator)aspectInstanceFactory;
         }

      }

      public boolean matches(Method method, Class targetClass) {
         return this.isAspectMaterialized() && this.declaredPointcut.matches(method, targetClass) || this.preInstantiationPointcut.getMethodMatcher().matches(method, targetClass);
      }

      public boolean matches(Method method, Class targetClass, Object... args) {
         return this.isAspectMaterialized() && this.declaredPointcut.matches(method, targetClass);
      }

      private boolean isAspectMaterialized() {
         return this.aspectInstanceFactory == null || this.aspectInstanceFactory.isMaterialized();
      }
   }
}
