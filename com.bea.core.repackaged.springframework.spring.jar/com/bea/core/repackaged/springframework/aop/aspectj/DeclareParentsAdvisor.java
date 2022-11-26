package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.IntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.IntroductionInterceptor;
import com.bea.core.repackaged.springframework.aop.support.ClassFilters;
import com.bea.core.repackaged.springframework.aop.support.DelegatePerTargetObjectIntroductionInterceptor;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;

public class DeclareParentsAdvisor implements IntroductionAdvisor {
   private final Advice advice;
   private final Class introducedInterface;
   private final ClassFilter typePatternClassFilter;

   public DeclareParentsAdvisor(Class interfaceType, String typePattern, Class defaultImpl) {
      this(interfaceType, typePattern, (IntroductionInterceptor)(new DelegatePerTargetObjectIntroductionInterceptor(defaultImpl, interfaceType)));
   }

   public DeclareParentsAdvisor(Class interfaceType, String typePattern, Object delegateRef) {
      this(interfaceType, typePattern, (IntroductionInterceptor)(new DelegatingIntroductionInterceptor(delegateRef)));
   }

   private DeclareParentsAdvisor(Class interfaceType, String typePattern, IntroductionInterceptor interceptor) {
      this.advice = interceptor;
      this.introducedInterface = interfaceType;
      ClassFilter typePatternFilter = new TypePatternClassFilter(typePattern);
      ClassFilter exclusion = (clazz) -> {
         return !this.introducedInterface.isAssignableFrom(clazz);
      };
      this.typePatternClassFilter = ClassFilters.intersection(typePatternFilter, exclusion);
   }

   public ClassFilter getClassFilter() {
      return this.typePatternClassFilter;
   }

   public void validateInterfaces() throws IllegalArgumentException {
   }

   public boolean isPerInstance() {
      return true;
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public Class[] getInterfaces() {
      return new Class[]{this.introducedInterface};
   }
}
