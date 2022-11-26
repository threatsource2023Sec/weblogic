package weblogic.security.principal;

public class PrincipalCacheKey {
   private final String principalName;
   private final String idd;

   public PrincipalCacheKey(WLSAbstractPrincipal wlsPrincipal, boolean caseInsensitiveName) {
      if (null == wlsPrincipal) {
         throw new IllegalArgumentException("Principal cannot be null.");
      } else {
         this.principalName = checkEmptyAndCaseInsensitive(wlsPrincipal.getName(), caseInsensitiveName);
         String iddValue = null;
         if (!(wlsPrincipal instanceof IDCSAppRole)) {
            iddValue = wlsPrincipal.getIdentityDomain();
         } else {
            iddValue = ((IDCSAppRole)wlsPrincipal).getAppName();
         }

         this.idd = checkEmptyAndCaseInsensitive(iddValue, false);
      }
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.idd == null ? 0 : this.idd.hashCode());
      result = 31 * result + (this.principalName == null ? 0 : this.principalName.hashCode());
      return result;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other == null) {
         return false;
      } else if (this.getClass() != other.getClass()) {
         return false;
      } else {
         PrincipalCacheKey otherKey = (PrincipalCacheKey)other;
         boolean isNameEqual = this.principalName == null ? otherKey.principalName == null : this.principalName.equals(otherKey.principalName);
         boolean isIddEqual = this.idd == null ? otherKey.idd == null : this.idd.equals(otherKey.idd);
         return isNameEqual && isIddEqual;
      }
   }

   public String toString() {
      return "key principal name: " + this.principalName + ", key identity domain: " + this.idd;
   }

   private static String checkEmptyAndCaseInsensitive(String input, boolean caseInsensitive) {
      if (null != input) {
         if (0 == input.length()) {
            input = null;
         } else if (caseInsensitive) {
            input = input.toLowerCase();
         }
      }

      return input;
   }
}
