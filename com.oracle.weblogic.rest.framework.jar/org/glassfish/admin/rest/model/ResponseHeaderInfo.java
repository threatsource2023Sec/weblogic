package org.glassfish.admin.rest.model;

public class ResponseHeaderInfo extends TypedNameInfo {
   ResponseHeaderInfo(String name, String description, TypeInfo type) throws Exception {
      super(name, description, type);
   }

   public String toString() {
      return this.toString("RequestHeaderInfo");
   }
}
