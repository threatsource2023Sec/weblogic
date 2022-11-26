package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;

public class DeferredResolvedPointcutDefinition extends ResolvedPointcutDefinition {
   public DeferredResolvedPointcutDefinition(UnresolvedType declaringType, int modifiers, String name, UnresolvedType[] parameterTypes) {
      super(declaringType, modifiers, name, parameterTypes, UnresolvedType.VOID, (Pointcut)null);
   }
}
