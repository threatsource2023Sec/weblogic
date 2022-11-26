package org.glassfish.admin.rest.model;

public class RequestHeaderInfo extends ParamInfo {
   RequestHeaderInfo(String name, String description, TypeInfo type, boolean isRequired) throws Exception {
      super(name, description, type, isRequired);
   }

   public String toString() {
      return this.toString("RequestHeaderInfo");
   }
}
