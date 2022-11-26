package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.utils.StringUtil;

public abstract class CustomSingletonChildCreateFormResourceMetaData extends CustomGetResourceMetaData {
   protected String singleton() throws Exception {
      String path = this.path();
      if (path.endsWith("CreateForm")) {
         return path.substring(0, path.length() - "CreateForm".length());
      } else {
         throw new AssertionError("Create form name does not end with CreateForm");
      }
   }

   public String className() throws Exception {
      return this.entityClassName() + ".createForm";
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.singleton()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.singleton())});
   }

   protected String defaultGETSummary() throws Exception {
      return "itemCreateFormGETSummary";
   }

   protected String defaultGETDesc() throws Exception {
      return "itemCreateFormGETDesc";
   }

   protected String defaultExampleGETBaseKey() throws Exception {
      return "itemExampleCreateFormGET";
   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      this.addLink(m, "create", this.parent().subUri(this.singleton()), "itemCreateFormResItemLinkDesc");
   }

   protected String category() throws Exception {
      return this.singleton();
   }
}
