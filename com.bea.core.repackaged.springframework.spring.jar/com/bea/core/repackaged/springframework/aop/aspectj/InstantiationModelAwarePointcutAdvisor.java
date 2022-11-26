package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.aop.PointcutAdvisor;

public interface InstantiationModelAwarePointcutAdvisor extends PointcutAdvisor {
   boolean isLazy();

   boolean isAdviceInstantiated();
}
