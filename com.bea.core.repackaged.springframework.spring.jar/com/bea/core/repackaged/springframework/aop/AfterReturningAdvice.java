package com.bea.core.repackaged.springframework.aop;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;

public interface AfterReturningAdvice extends AfterAdvice {
   void afterReturning(@Nullable Object var1, Method var2, Object[] var3, @Nullable Object var4) throws Throwable;
}
