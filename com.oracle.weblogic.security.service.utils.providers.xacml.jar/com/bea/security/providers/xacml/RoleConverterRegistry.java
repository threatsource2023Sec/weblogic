package com.bea.security.providers.xacml;

import java.util.Map;

public class RoleConverterRegistry {
   private RoleConverterFactory factory;

   public RoleConverterRegistry(RoleConverterFactory factory) {
      this.factory = factory;
   }

   public RoleConverter getConverter(Map r) {
      return this.factory != null ? this.factory.getConverter(r) : null;
   }
}
