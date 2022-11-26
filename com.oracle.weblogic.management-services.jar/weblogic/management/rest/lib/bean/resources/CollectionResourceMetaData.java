package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;

public abstract class CollectionResourceMetaData extends ResourceMetaData {
   protected boolean supportsDELETE() throws Exception {
      return false;
   }

   public TypeInfo entityType() throws Exception {
      return this.entityRefType(this.entityClassName());
   }

   public String className() throws Exception {
      return this.entityClassName() + ".collection";
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.path()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.path())});
   }

   protected String defaultGETSummary() throws Exception {
      return "collectionGETSummary";
   }

   protected String defaultGETDesc() throws Exception {
      return "collectionGETDesc";
   }

   protected String defaultExampleGETBaseKey() throws Exception {
      return "collectionExampleGET";
   }

   protected String defaultGETRespDesc() throws Exception {
      return "collectionRespDesc";
   }

   protected String defaultPOSTDesc() throws Exception {
      return "collectionPOSTCreateDesc";
   }

   protected String defaultPOSTSummary() throws Exception {
      return "collectionPOSTCreateSummary";
   }

   protected String defaultExamplePOSTBaseKey() throws Exception {
      return "collectionExamplePOSTCreate";
   }

   protected String defaultPOSTReqDesc() throws Exception {
      return "collectionPOSTReqDesc";
   }

   public TypeInfo GETRespType() throws Exception {
      return this.arrayType(this.entityType());
   }

   public TypeInfo POSTReqType() throws Exception {
      return this.entityType();
   }

   public TypeInfo POSTRespType() throws Exception {
      return this.voidType();
   }

   protected boolean POSTCreates() throws Exception {
      return true;
   }

   protected String category() throws Exception {
      return this.path();
   }
}
