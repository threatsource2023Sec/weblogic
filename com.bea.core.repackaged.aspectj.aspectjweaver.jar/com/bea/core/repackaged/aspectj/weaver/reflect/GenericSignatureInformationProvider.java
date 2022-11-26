package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public interface GenericSignatureInformationProvider {
   UnresolvedType[] getGenericParameterTypes(ReflectionBasedResolvedMemberImpl var1);

   UnresolvedType getGenericReturnType(ReflectionBasedResolvedMemberImpl var1);

   boolean isBridge(ReflectionBasedResolvedMemberImpl var1);

   boolean isVarArgs(ReflectionBasedResolvedMemberImpl var1);

   boolean isSynthetic(ReflectionBasedResolvedMemberImpl var1);
}
