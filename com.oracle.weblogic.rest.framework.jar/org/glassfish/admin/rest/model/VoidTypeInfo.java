package org.glassfish.admin.rest.model;

public class VoidTypeInfo implements TypeInfo {
   private static VoidTypeInfo INSTANCE = new VoidTypeInfo();

   public static VoidTypeInfo instance() {
      return INSTANCE;
   }

   private VoidTypeInfo() {
   }

   public String toString() {
      return "VoidTypeInfo";
   }
}
