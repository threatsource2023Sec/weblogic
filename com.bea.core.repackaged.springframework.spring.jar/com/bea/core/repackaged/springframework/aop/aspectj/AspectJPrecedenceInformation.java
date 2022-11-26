package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.core.Ordered;

public interface AspectJPrecedenceInformation extends Ordered {
   String getAspectName();

   int getDeclarationOrder();

   boolean isBeforeAdvice();

   boolean isAfterAdvice();
}
