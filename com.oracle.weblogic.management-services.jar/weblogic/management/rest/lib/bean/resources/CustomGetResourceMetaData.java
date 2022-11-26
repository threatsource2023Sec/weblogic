package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.TypeInfo;

public abstract class CustomGetResourceMetaData extends CustomResourceMetaData {
   public TypeInfo entityType() throws Exception {
      return this.entityRefType(this.entityClassName());
   }

   public String className() throws Exception {
      return this.entityClassName();
   }

   protected String defaultGETSummary() throws Exception {
      return "itemGETSummary";
   }

   protected String defaultGETDesc() throws Exception {
      return "itemGETDesc";
   }

   protected String defaultExampleGETBaseKey() throws Exception {
      return "itemExampleGET";
   }

   protected String defaultGETRespDesc() throws Exception {
      return "itemRespDesc";
   }

   public TypeInfo GETRespType() throws Exception {
      return this.entityType();
   }
}
