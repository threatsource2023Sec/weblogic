package org.glassfish.admin.rest.model;

public class QueryParamInfo extends ParamInfo {
   QueryParamInfo(String name, String description, TypeInfo type, boolean isRequired) throws Exception {
      super(name, description, type, isRequired);
   }

   public String toString() {
      return this.toString("QueryParamInfo");
   }
}
