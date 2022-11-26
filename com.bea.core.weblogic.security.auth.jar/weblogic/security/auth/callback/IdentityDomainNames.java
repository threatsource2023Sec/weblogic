package weblogic.security.auth.callback;

public class IdentityDomainNames {
   private final String name;
   private final String idd;

   public IdentityDomainNames(String name, String identityDomain) {
      validateNames(name, identityDomain);
      this.name = name != null && !name.isEmpty() ? name : null;
      this.idd = identityDomain != null && !identityDomain.isEmpty() ? identityDomain : null;
   }

   public String getName() {
      return this.name;
   }

   public String getIdentityDomain() {
      return this.idd;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.idd == null ? 0 : this.idd.hashCode());
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
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
         IdentityDomainNames otherIdd = (IdentityDomainNames)other;
         boolean isNameEqual = this.name == null ? otherIdd.name == null : this.name.equals(otherIdd.name);
         boolean isIddEqual = this.idd == null ? otherIdd.idd == null : this.idd.equals(otherIdd.idd);
         return isNameEqual && isIddEqual;
      }
   }

   public String toString() {
      return "" + this.name + " [" + this.idd + "]";
   }

   private static void validateNames(String name, String identityDomain) {
      if (name == null && identityDomain != null) {
         throw new IllegalArgumentException("identity domain must be null if name is null");
      }
   }
}
