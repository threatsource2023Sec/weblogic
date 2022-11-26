package com.bea.core.repackaged.aspectj.weaver;

import java.io.IOException;

public class NewParentTypeMunger extends ResolvedTypeMunger {
   ResolvedType newParent;
   ResolvedType declaringType;
   private boolean isMixin;
   private volatile int hashCode = 0;

   public NewParentTypeMunger(ResolvedType newParent, ResolvedType declaringType) {
      super(Parent, (ResolvedMember)null);
      this.newParent = newParent;
      this.declaringType = declaringType;
      this.isMixin = false;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new RuntimeException("unimplemented");
   }

   public ResolvedType getNewParent() {
      return this.newParent;
   }

   public boolean equals(Object other) {
      if (!(other instanceof NewParentTypeMunger)) {
         return false;
      } else {
         NewParentTypeMunger o = (NewParentTypeMunger)other;
         return this.newParent.equals(o.newParent) && this.isMixin == o.isMixin;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + this.newParent.hashCode();
         result = 37 * result + (this.isMixin ? 0 : 1);
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public ResolvedType getDeclaringType() {
      return this.declaringType;
   }

   public void setIsMixin(boolean b) {
      this.isMixin = true;
   }

   public boolean isMixin() {
      return this.isMixin;
   }
}
