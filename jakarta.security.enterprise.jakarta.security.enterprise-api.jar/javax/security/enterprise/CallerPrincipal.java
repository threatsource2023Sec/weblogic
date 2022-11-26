package javax.security.enterprise;

import java.security.Principal;

public class CallerPrincipal implements Principal {
   private final String name;

   public CallerPrincipal(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }
}
