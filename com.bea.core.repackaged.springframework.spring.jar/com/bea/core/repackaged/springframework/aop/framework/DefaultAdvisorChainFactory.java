package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.intercept.Interceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.IntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.IntroductionAwareMethodMatcher;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.PointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultAdvisorChainFactory implements AdvisorChainFactory, Serializable {
   public List getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method, @Nullable Class targetClass) {
      AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance();
      Advisor[] advisors = config.getAdvisors();
      List interceptorList = new ArrayList(advisors.length);
      Class actualClass = targetClass != null ? targetClass : method.getDeclaringClass();
      Boolean hasIntroductions = null;
      Advisor[] var9 = advisors;
      int var10 = advisors.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         Advisor advisor = var9[var11];
         if (advisor instanceof PointcutAdvisor) {
            PointcutAdvisor pointcutAdvisor = (PointcutAdvisor)advisor;
            if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
               MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
               boolean match;
               if (mm instanceof IntroductionAwareMethodMatcher) {
                  if (hasIntroductions == null) {
                     hasIntroductions = hasMatchingIntroductions(advisors, actualClass);
                  }

                  match = ((IntroductionAwareMethodMatcher)mm).matches(method, actualClass, hasIntroductions);
               } else {
                  match = mm.matches(method, actualClass);
               }

               if (match) {
                  MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
                  if (mm.isRuntime()) {
                     MethodInterceptor[] var17 = interceptors;
                     int var18 = interceptors.length;

                     for(int var19 = 0; var19 < var18; ++var19) {
                        MethodInterceptor interceptor = var17[var19];
                        interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
                     }
                  } else {
                     interceptorList.addAll(Arrays.asList(interceptors));
                  }
               }
            }
         } else if (advisor instanceof IntroductionAdvisor) {
            IntroductionAdvisor ia = (IntroductionAdvisor)advisor;
            if (config.isPreFiltered() || ia.getClassFilter().matches(actualClass)) {
               Interceptor[] interceptors = registry.getInterceptors(advisor);
               interceptorList.addAll(Arrays.asList(interceptors));
            }
         } else {
            Interceptor[] interceptors = registry.getInterceptors(advisor);
            interceptorList.addAll(Arrays.asList(interceptors));
         }
      }

      return interceptorList;
   }

   private static boolean hasMatchingIntroductions(Advisor[] advisors, Class actualClass) {
      Advisor[] var2 = advisors;
      int var3 = advisors.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Advisor advisor = var2[var4];
         if (advisor instanceof IntroductionAdvisor) {
            IntroductionAdvisor ia = (IntroductionAdvisor)advisor;
            if (ia.getClassFilter().matches(actualClass)) {
               return true;
            }
         }
      }

      return false;
   }
}
