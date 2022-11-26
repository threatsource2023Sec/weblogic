package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.AfterAdvice;
import com.bea.core.repackaged.springframework.aop.BeforeAdvice;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class AspectJAopUtils {
   public static boolean isBeforeAdvice(Advisor anAdvisor) {
      AspectJPrecedenceInformation precedenceInfo = getAspectJPrecedenceInformationFor(anAdvisor);
      return precedenceInfo != null ? precedenceInfo.isBeforeAdvice() : anAdvisor.getAdvice() instanceof BeforeAdvice;
   }

   public static boolean isAfterAdvice(Advisor anAdvisor) {
      AspectJPrecedenceInformation precedenceInfo = getAspectJPrecedenceInformationFor(anAdvisor);
      return precedenceInfo != null ? precedenceInfo.isAfterAdvice() : anAdvisor.getAdvice() instanceof AfterAdvice;
   }

   @Nullable
   public static AspectJPrecedenceInformation getAspectJPrecedenceInformationFor(Advisor anAdvisor) {
      if (anAdvisor instanceof AspectJPrecedenceInformation) {
         return (AspectJPrecedenceInformation)anAdvisor;
      } else {
         Advice advice = anAdvisor.getAdvice();
         return advice instanceof AspectJPrecedenceInformation ? (AspectJPrecedenceInformation)advice : null;
      }
   }
}
