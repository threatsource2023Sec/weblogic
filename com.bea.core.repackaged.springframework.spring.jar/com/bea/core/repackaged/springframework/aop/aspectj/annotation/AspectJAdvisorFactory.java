package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.bea.core.repackaged.springframework.aop.framework.AopConfigException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.List;

public interface AspectJAdvisorFactory {
   boolean isAspect(Class var1);

   void validate(Class var1) throws AopConfigException;

   List getAdvisors(MetadataAwareAspectInstanceFactory var1);

   @Nullable
   Advisor getAdvisor(Method var1, MetadataAwareAspectInstanceFactory var2, int var3, String var4);

   @Nullable
   Advice getAdvice(Method var1, AspectJExpressionPointcut var2, MetadataAwareAspectInstanceFactory var3, int var4, String var5);
}
