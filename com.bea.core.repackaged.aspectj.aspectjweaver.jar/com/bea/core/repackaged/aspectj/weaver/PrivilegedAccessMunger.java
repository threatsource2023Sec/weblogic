package com.bea.core.repackaged.aspectj.weaver;

import java.io.IOException;

public class PrivilegedAccessMunger extends ResolvedTypeMunger {
   public boolean shortSyntax = false;

   public PrivilegedAccessMunger(ResolvedMember member, boolean shortSyntax) {
      super(PrivilegedAccess, member);
      this.shortSyntax = shortSyntax;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new RuntimeException("should not be serialized");
   }

   public ResolvedMember getMember() {
      return this.getSignature();
   }

   public ResolvedMember getMatchingSyntheticMember(Member member, ResolvedType aspectType) {
      ResolvedMember ret;
      if (this.getSignature().getKind() == Member.FIELD) {
         ret = AjcMemberMaker.privilegedAccessMethodForFieldGet(aspectType, this.getSignature(), this.shortSyntax);
         if (ResolvedType.matches(ret, member)) {
            return this.getSignature();
         }

         ret = AjcMemberMaker.privilegedAccessMethodForFieldSet(aspectType, this.getSignature(), this.shortSyntax);
         if (ResolvedType.matches(ret, member)) {
            return this.getSignature();
         }
      } else {
         ret = AjcMemberMaker.privilegedAccessMethodForMethod(aspectType, this.getSignature());
         if (ResolvedType.matches(ret, member)) {
            return this.getSignature();
         }
      }

      return null;
   }

   public boolean equals(Object other) {
      if (!(other instanceof PrivilegedAccessMunger)) {
         return false;
      } else {
         boolean var10000;
         label52: {
            PrivilegedAccessMunger o = (PrivilegedAccessMunger)other;
            if (this.kind.equals(o.kind)) {
               label46: {
                  if (o.signature == null) {
                     if (this.signature != null) {
                        break label46;
                     }
                  } else if (!this.signature.equals(o.signature)) {
                     break label46;
                  }

                  if (o.declaredSignature == null) {
                     if (this.declaredSignature != null) {
                        break label46;
                     }
                  } else if (!this.declaredSignature.equals(o.declaredSignature)) {
                     break label46;
                  }

                  if (o.typeVariableAliases == null) {
                     if (this.typeVariableAliases == null) {
                        break label52;
                     }
                  } else if (this.typeVariableAliases.equals(o.typeVariableAliases)) {
                     break label52;
                  }
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.kind.hashCode();
      result = 37 * result + (this.signature == null ? 0 : this.signature.hashCode());
      result = 37 * result + (this.declaredSignature == null ? 0 : this.declaredSignature.hashCode());
      result = 37 * result + (this.typeVariableAliases == null ? 0 : this.typeVariableAliases.hashCode());
      return result;
   }

   public boolean existsToSupportShadowMunging() {
      return true;
   }
}
