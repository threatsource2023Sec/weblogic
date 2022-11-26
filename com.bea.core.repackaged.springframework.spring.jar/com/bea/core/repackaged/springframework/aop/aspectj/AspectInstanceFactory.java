package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface AspectInstanceFactory extends Ordered {
   Object getAspectInstance();

   @Nullable
   ClassLoader getAspectClassLoader();
}
