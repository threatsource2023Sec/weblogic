package com.bea.core.repackaged.aspectj.weaver;

public class MemberUtils {
   public static boolean isConstructor(ResolvedMember member) {
      return member.getName().equals("<init>");
   }
}
