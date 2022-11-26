package weblogic.security.psm;

import java.security.Permission;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class PDP {
   public ProtectionDomain pd;
   public Permission p;

   public PDP(ProtectionDomain pd, Permission p) {
      if (p == null) {
         throw new IllegalArgumentException("Permission passed to PDP constructor can not be null");
      } else {
         this.pd = pd;
         this.p = p;
      }
   }

   public int hashCode() {
      return this.pd.hashCode() ^ this.p.hashCode();
   }

   public boolean equals(Object obj) {
      boolean retVal = false;
      if (obj == null) {
         retVal = false;
      } else if (this == obj) {
         retVal = true;
      } else if (obj instanceof PDP) {
         PDP other = (PDP)obj;
         if (this.pd.getCodeSource().equals(other.pd.getCodeSource()) && this.p.implies(other.p)) {
            if (this.pd.getPrincipals().length == 0 && other.pd.getPrincipals().length == 0) {
               retVal = true;
            } else if (Arrays.equals(this.pd.getPrincipals(), other.pd.getPrincipals())) {
               retVal = true;
            } else {
               retVal = false;
            }
         }
      }

      return retVal;
   }
}
