package com.bea.core.repackaged.springframework.aop;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface TargetSource extends TargetClassAware {
   @Nullable
   Class getTargetClass();

   boolean isStatic();

   @Nullable
   Object getTarget() throws Exception;

   void releaseTarget(Object var1) throws Exception;
}
