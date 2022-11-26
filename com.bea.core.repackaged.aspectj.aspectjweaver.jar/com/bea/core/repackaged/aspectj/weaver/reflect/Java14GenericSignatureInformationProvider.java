package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class Java14GenericSignatureInformationProvider implements GenericSignatureInformationProvider {
   public UnresolvedType[] getGenericParameterTypes(ReflectionBasedResolvedMemberImpl resolvedMember) {
      return resolvedMember.getParameterTypes();
   }

   public UnresolvedType getGenericReturnType(ReflectionBasedResolvedMemberImpl resolvedMember) {
      return resolvedMember.getReturnType();
   }

   public boolean isBridge(ReflectionBasedResolvedMemberImpl resolvedMember) {
      return false;
   }

   public boolean isVarArgs(ReflectionBasedResolvedMemberImpl resolvedMember) {
      return false;
   }

   public boolean isSynthetic(ReflectionBasedResolvedMemberImpl resolvedMember) {
      return false;
   }
}
