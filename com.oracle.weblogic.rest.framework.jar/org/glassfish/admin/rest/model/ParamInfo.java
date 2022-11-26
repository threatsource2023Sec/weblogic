package org.glassfish.admin.rest.model;

public class ParamInfo extends TypedNameInfo {
   private boolean isRequired;

   ParamInfo(String name, String description, TypeInfo type, boolean isRequired) throws Exception {
      super(name, description, type);
      this.isRequired = isRequired;
   }

   public boolean isRequired() throws Exception {
      return this.isRequired;
   }
}
