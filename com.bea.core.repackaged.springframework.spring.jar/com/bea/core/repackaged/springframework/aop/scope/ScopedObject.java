package com.bea.core.repackaged.springframework.aop.scope;

import com.bea.core.repackaged.springframework.aop.RawTargetAccess;

public interface ScopedObject extends RawTargetAccess {
   Object getTargetObject();

   void removeFromScope();
}
