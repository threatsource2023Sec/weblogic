package com.bea.core.repackaged.springframework.aop;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice {
   void before(Method var1, Object[] var2, @Nullable Object var3) throws Throwable;
}
