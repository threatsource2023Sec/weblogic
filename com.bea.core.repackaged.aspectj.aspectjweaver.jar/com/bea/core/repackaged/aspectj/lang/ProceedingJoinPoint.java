package com.bea.core.repackaged.aspectj.lang;

import com.bea.core.repackaged.aspectj.runtime.internal.AroundClosure;

public interface ProceedingJoinPoint extends JoinPoint {
   void set$AroundClosure(AroundClosure var1);

   Object proceed() throws Throwable;

   Object proceed(Object[] var1) throws Throwable;
}
