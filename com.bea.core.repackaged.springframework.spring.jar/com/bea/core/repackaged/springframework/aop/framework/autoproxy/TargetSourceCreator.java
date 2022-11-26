package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;

@FunctionalInterface
public interface TargetSourceCreator {
   @Nullable
   TargetSource getTargetSource(Class var1, String var2);
}
