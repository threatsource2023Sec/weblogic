package org.glassfish.soteria.servlet;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.Set;

public class AuthenticationData implements Serializable {
   private static final long serialVersionUID = 1L;
   private final Principal principal;
   private final Set groups;

   public AuthenticationData(Principal principal, Set groups) {
      this.principal = principal;
      this.groups = Collections.unmodifiableSet(groups);
   }

   public Principal getPrincipal() {
      return this.principal;
   }

   public Set getGroups() {
      return this.groups;
   }
}
