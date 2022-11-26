package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Java15GenericSignatureInformationProvider implements GenericSignatureInformationProvider {
   private final World world;

   public Java15GenericSignatureInformationProvider(World forWorld) {
      this.world = forWorld;
   }

   public UnresolvedType[] getGenericParameterTypes(ReflectionBasedResolvedMemberImpl resolvedMember) {
      JavaLangTypeToResolvedTypeConverter typeConverter = new JavaLangTypeToResolvedTypeConverter(this.world);
      Type[] pTypes = new Type[0];
      Member member = resolvedMember.getMember();
      if (member instanceof Method) {
         pTypes = ((Method)member).getGenericParameterTypes();
      } else if (member instanceof Constructor) {
         pTypes = ((Constructor)member).getGenericParameterTypes();
      }

      return typeConverter.fromTypes(pTypes);
   }

   public UnresolvedType getGenericReturnType(ReflectionBasedResolvedMemberImpl resolvedMember) {
      JavaLangTypeToResolvedTypeConverter typeConverter = new JavaLangTypeToResolvedTypeConverter(this.world);
      Member member = resolvedMember.getMember();
      if (member instanceof Field) {
         return typeConverter.fromType(((Field)member).getGenericType());
      } else if (member instanceof Method) {
         return typeConverter.fromType(((Method)member).getGenericReturnType());
      } else if (member instanceof Constructor) {
         return typeConverter.fromType(((Constructor)member).getDeclaringClass());
      } else {
         throw new IllegalStateException("unexpected member type: " + member);
      }
   }

   public boolean isBridge(ReflectionBasedResolvedMemberImpl resolvedMember) {
      Member member = resolvedMember.getMember();
      return member instanceof Method ? ((Method)member).isBridge() : false;
   }

   public boolean isVarArgs(ReflectionBasedResolvedMemberImpl resolvedMember) {
      Member member = resolvedMember.getMember();
      if (member instanceof Method) {
         return ((Method)member).isVarArgs();
      } else {
         return member instanceof Constructor ? ((Constructor)member).isVarArgs() : false;
      }
   }

   public boolean isSynthetic(ReflectionBasedResolvedMemberImpl resolvedMember) {
      Member member = resolvedMember.getMember();
      return member.isSynthetic();
   }
}
