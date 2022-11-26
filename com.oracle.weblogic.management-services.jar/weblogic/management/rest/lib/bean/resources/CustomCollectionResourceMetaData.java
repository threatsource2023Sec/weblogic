package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;

public abstract class CustomCollectionResourceMetaData extends CustomGetResourceMetaData {
   public String className() throws Exception {
      return this.entityClassName() + ".collection";
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.path()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.path())});
   }

   protected boolean supportsCreateForm() throws Exception {
      return this.supportsPOST();
   }

   protected String createFormName() throws Exception {
      return StringUtil.getSingular(this.path()) + "CreateForm";
   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      super.addGETLinks(m);
      if (this.supportsCreateForm()) {
         this.addLink(m, this.createFormName(), this.parent().subUri(this.createFormName()), "collectionResCreateFormLinkDesc");
      }

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

   protected String defaultPOSTSummary() throws Exception {
      return "collectionPOSTCreateSummary";
   }

   protected String defaultPOSTDesc() throws Exception {
      return "collectionPOSTCreateDesc";
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

   protected String category() throws Exception {
      return this.path();
   }
}
