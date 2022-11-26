package com.oracle.pitchfork.inject;

import com.oracle.pitchfork.interfaces.inject.InjectionInfo;

public class BasicInjectionInfo implements InjectionInfo {
   private static final String INJECTION_NAME = "generic";
   protected String name;
   protected Class type;

   protected BasicInjectionInfo() {
   }

   public BasicInjectionInfo(String name, Class type) {
      this.name = name;
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public Class getType() {
      return this.type;
   }

   public String toString() {
      return this.getClass().getSimpleName() + ": name='" + this.name + "'; type='" + this.type + "'";
   }

   public String getInjectionName() {
      return "generic";
   }
}
