package com.bea.core.repackaged.aspectj.weaver;

public class ExposeTypeMunger extends PrivilegedAccessMunger {
   public ExposeTypeMunger(UnresolvedType typeToExpose) {
      super(new ResolvedMemberImpl(Member.STATIC_INITIALIZATION, typeToExpose, 0, UnresolvedType.VOID, "<clinit>", UnresolvedType.NONE), false);
   }

   public String toString() {
      return "ExposeTypeMunger(" + this.getSignature().getDeclaringType().getName() + ")";
   }

   public String getExposedTypeSignature() {
      return this.getSignature().getDeclaringType().getSignature();
   }
}
