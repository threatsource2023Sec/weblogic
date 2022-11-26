package com.bea.security.providers.xacml.entitlement;

public abstract class CursorFilter {
   public boolean isValidResource(String resource) {
      return true;
   }

   public boolean isValidRole(String resource, String role) {
      return this.isValidResource(resource);
   }
}
