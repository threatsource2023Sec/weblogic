package com.oracle.pitchfork.inject;

public class ResourceInfo extends BasicInjectionInfo {
   private static final String INJECTION_NAME = "@Resource";

   public ResourceInfo(String name, Class type) {
      super(name, type);
   }

   public String getInjectionName() {
      return "@Resource";
   }
}
