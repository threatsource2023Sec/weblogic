package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.PointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.interceptor.ExposeInvocationInterceptor;
import java.util.Iterator;
import java.util.List;

public abstract class AspectJProxyUtils {
   public static boolean makeAdvisorChainAspectJCapableIfNecessary(List advisors) {
      if (!advisors.isEmpty()) {
         boolean foundAspectJAdvice = false;
         Iterator var2 = advisors.iterator();

         while(var2.hasNext()) {
            Advisor advisor = (Advisor)var2.next();
            if (isAspectJAdvice(advisor)) {
               foundAspectJAdvice = true;
               break;
            }
         }

         if (foundAspectJAdvice && !advisors.contains(ExposeInvocationInterceptor.ADVISOR)) {
            advisors.add(0, ExposeInvocationInterceptor.ADVISOR);
            return true;
         }
      }

      return false;
   }

   private static boolean isAspectJAdvice(Advisor advisor) {
      return advisor instanceof InstantiationModelAwarePointcutAdvisor || advisor.getAdvice() instanceof AbstractAspectJAdvice || advisor instanceof PointcutAdvisor && ((PointcutAdvisor)advisor).getPointcut() instanceof AspectJExpressionPointcut;
   }
}
