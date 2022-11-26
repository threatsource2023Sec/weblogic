package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;

public abstract class ItemResourceMetaData extends ResourceMetaData {
   public TypeInfo entityType() throws Exception {
      return this.entityRefType(this.entityClassName());
   }

   public String className() throws Exception {
      return this.entityClassName();
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.path()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.path())});
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

   public TypeInfo GETRespType() throws Exception {
      return this.entityType();
   }

   public TypeInfo POSTReqType() throws Exception {
      return this.entityType();
   }

   public TypeInfo POSTRespType() throws Exception {
      return this.voidType();
   }
}
