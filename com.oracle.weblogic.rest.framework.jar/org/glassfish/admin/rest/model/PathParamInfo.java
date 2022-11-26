package org.glassfish.admin.rest.model;

public class PathParamInfo extends ParamInfo {
   PathParamInfo(String name, String description, TypeInfo type) throws Exception {
      super(name, description, type, true);
   }

   public String toString() {
      return this.toString("PathParamInfo");
   }
}
