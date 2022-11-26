package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.utils.StringUtil;

public abstract class CustomCollectionChildCreateFormResourceMetaData extends CustomGetResourceMetaData {
   protected String collection() throws Exception {
      String path = this.path();
      if (path.endsWith("CreateForm")) {
         String singular = path.substring(0, path.length() - "CreateForm".length());
         return StringUtil.getPlural(singular);
      } else {
         throw new AssertionError("Create form name does not end with CreateForm");
      }
   }

   public String className() throws Exception {
      return this.entityClassName() + ".createForm";
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.collection()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.collection())});
   }

   protected String defaultGETSummary() throws Exception {
      return "collectionCreateFormGETSummary";
   }

   protected String defaultGETDesc() throws Exception {
      return "collectionCreateFormGETDesc";
   }

   protected String defaultExampleGETBaseKey() throws Exception {
      return "collectionExampleCreateFormGET";
   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      this.addLink(m, "create", this.parent().subUri(this.collection()), "collectionCreateFormResCollectionLinkDesc");
   }

   protected String category() throws Exception {
      return this.collection();
   }
}
