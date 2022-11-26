package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.TypeInfo;

public abstract class CustomItemResourceMetaData extends CustomGetResourceMetaData {
   protected String defaultPOSTSummary() throws Exception {
      return "itemPOSTUpdateSummary";
   }

   protected String defaultPOSTDesc() throws Exception {
      return "itemPOSTUpdateDesc";
   }

   protected String defaultExamplePOSTBaseKey() throws Exception {
      return "itemExamplePOSTUpdate";
   }

   protected String defaultPOSTReqDesc() throws Exception {
      return "itemReqDesc";
   }

   protected String defaultDELETESummary() throws Exception {
      return "itemDELETESummary";
   }

   protected String defaultDELETEDesc() throws Exception {
      return "itemDELETEDesc";
   }

   protected String defaultExampleDELETEBaseKey() throws Exception {
      return "itemExampleDELETE";
   }

   public TypeInfo POSTReqType() throws Exception {
      return this.entityType();
   }
}
