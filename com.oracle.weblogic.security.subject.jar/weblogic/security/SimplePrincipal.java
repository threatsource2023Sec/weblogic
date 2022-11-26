package weblogic.security;

import java.security.Principal;

public class SimplePrincipal implements Principal {
   private String name;

   public SimplePrincipal(String name) {
      this.name = name;
   }

   public final String getName() {
      return this.name;
   }

   public String toString() {
      return "SimplePrincipal \"" + this.name + "\"";
   }

   public int hashCode() {
      return this.name == null ? 0 : this.name.hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof SimplePrincipal)) {
         return false;
      } else if (o == this) {
         return true;
      } else {
         SimplePrincipal s = (SimplePrincipal)o;
         if (this.name != null && s.name != null) {
            return this.name.equals(s.name);
         } else {
            return this.name == s.name;
         }
      }
   }
}
