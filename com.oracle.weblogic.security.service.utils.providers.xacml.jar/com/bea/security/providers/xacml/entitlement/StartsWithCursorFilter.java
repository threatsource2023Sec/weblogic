package com.bea.security.providers.xacml.entitlement;

public class StartsWithCursorFilter extends CursorFilter {
   private String startsWith;

   public StartsWithCursorFilter(String startsWith) {
      this.startsWith = startsWith;
   }

   public boolean isValidResource(String resource) {
      return resource != null && resource.startsWith(this.startsWith);
   }
}
