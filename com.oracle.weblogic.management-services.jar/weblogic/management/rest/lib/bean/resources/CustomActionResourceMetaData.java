package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.TypeInfo;

public abstract class CustomActionResourceMetaData extends CustomResourceMetaData {
   protected boolean supportsGET() throws Exception {
      return false;
   }

   protected boolean supportsPOST() throws Exception {
      return true;
   }

   protected String defaultExamplePOSTBaseKey() throws Exception {
      return "actionExamplePOST";
   }

   public TypeInfo POSTReqType() throws Exception {
      return this.voidType();
   }

   public TypeInfo POSTRespType() throws Exception {
      return this.voidType();
   }

   protected String defaultPOSTSummary() throws Exception {
      return "actionPOSTSummary";
   }

   protected String defaultPOSTReqDesc() {
      return "actionReqDesc";
   }

   protected String defaultPOSTRespDesc() {
      return "actionRespDesc";
   }

   public boolean isActionsCategory() throws Exception {
      return true;
   }
}
