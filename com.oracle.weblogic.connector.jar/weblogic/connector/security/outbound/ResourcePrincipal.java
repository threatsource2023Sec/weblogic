package weblogic.connector.security.outbound;

import java.security.Principal;

public final class ResourcePrincipal implements Principal {
   private String password;
   private String userName;

   public ResourcePrincipal(String userName, String password) {
      this.userName = userName;
      this.password = password;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (!(other instanceof ResourcePrincipal)) {
         return false;
      } else {
         ResourcePrincipal inRP = (ResourcePrincipal)other;
         return this.userName.equals(inRP.getName()) && this.password.equals(inRP.getPassword());
      }
   }

   public int hashCode() {
      return this.userName.hashCode() ^ this.password.hashCode();
   }

   public String toString() {
      return "ResourcePrincipal(userName=" + this.userName + ",password=" + this.password + ")";
   }

   public String getName() {
      return this.userName;
   }

   public String getPassword() {
      return this.password;
   }
}
