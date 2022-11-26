package com.googlecode.cqengine.codegen;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class MemberFilters {
   public static final MemberFilter ALL_MEMBERS = new MemberFilter() {
      public boolean accept(Member member) {
         return true;
      }
   };
   public static final MemberFilter FIELDS_ONLY = new MemberFilter() {
      public boolean accept(Member member) {
         return member instanceof Field;
      }
   };
   public static final MemberFilter METHODS_ONLY = new MemberFilter() {
      public boolean accept(Member member) {
         return member instanceof Method;
      }
   };
   public static final MemberFilter GETTER_METHODS_ONLY = new MemberFilter() {
      public boolean accept(Member member) {
         return member instanceof Method && (MemberFilters.hasGetterPrefix(member.getName(), "get") || MemberFilters.hasGetterPrefix(member.getName(), "is") || MemberFilters.hasGetterPrefix(member.getName(), "has"));
      }
   };

   static boolean hasGetterPrefix(String memberName, String prefix) {
      int prefixLength = prefix.length();
      return memberName.length() > prefixLength && memberName.startsWith(prefix) && Character.isUpperCase(memberName.charAt(prefixLength));
   }

   MemberFilters() {
   }
}
