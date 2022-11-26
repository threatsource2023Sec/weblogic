package weblogic.application;

public class SecurityRole {
   private final String[] principalNames;
   private final boolean isExternallyDefined;

   public SecurityRole(String[] principalNames) {
      this.principalNames = principalNames;
      this.isExternallyDefined = false;
   }

   public SecurityRole() {
      this.principalNames = null;
      this.isExternallyDefined = true;
   }

   public String toString() {
      if (this.isExternallyDefined()) {
         return "externally-defined";
      } else {
         StringBuffer sb = new StringBuffer("principals: ");
         if (this.principalNames != null) {
            for(int i = 0; i < this.principalNames.length; ++i) {
               sb.append(this.principalNames[i]).append(" ");
            }
         }

         return sb.toString();
      }
   }

   public String[] getPrincipalNames() {
      return this.principalNames;
   }

   public boolean isExternallyDefined() {
      return this.isExternallyDefined;
   }
}
