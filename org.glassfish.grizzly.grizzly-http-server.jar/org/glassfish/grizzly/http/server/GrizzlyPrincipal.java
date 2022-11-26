package org.glassfish.grizzly.http.server;

import java.io.Serializable;
import java.security.Principal;

public class GrizzlyPrincipal implements Principal, Serializable {
   private static final long serialVersionUID = 1L;
   protected String name = null;

   public GrizzlyPrincipal(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("GrizzlyPrincipal[");
      sb.append(this.name);
      sb.append("]");
      return sb.toString();
   }
}
