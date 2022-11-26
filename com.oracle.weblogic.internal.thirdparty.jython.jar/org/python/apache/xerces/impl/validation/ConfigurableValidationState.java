package org.python.apache.xerces.impl.validation;

public final class ConfigurableValidationState extends ValidationState {
   private boolean fIdIdrefChecking = true;
   private boolean fUnparsedEntityChecking = true;

   public void setIdIdrefChecking(boolean var1) {
      this.fIdIdrefChecking = var1;
   }

   public void setUnparsedEntityChecking(boolean var1) {
      this.fUnparsedEntityChecking = var1;
   }

   public String checkIDRefID() {
      return this.fIdIdrefChecking ? super.checkIDRefID() : null;
   }

   public boolean isIdDeclared(String var1) {
      return this.fIdIdrefChecking ? super.isIdDeclared(var1) : false;
   }

   public boolean isEntityDeclared(String var1) {
      return this.fUnparsedEntityChecking ? super.isEntityDeclared(var1) : true;
   }

   public boolean isEntityUnparsed(String var1) {
      return this.fUnparsedEntityChecking ? super.isEntityUnparsed(var1) : true;
   }

   public void addId(String var1) {
      if (this.fIdIdrefChecking) {
         super.addId(var1);
      }

   }

   public void addIdRef(String var1) {
      if (this.fIdIdrefChecking) {
         super.addIdRef(var1);
      }

   }
}
