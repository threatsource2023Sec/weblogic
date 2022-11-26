package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.MethodInfo;

public abstract class CustomSingletonChildResourceMetaData extends CustomItemResourceMetaData {
   protected boolean supportsCreate() throws Exception {
      return false;
   }

   protected boolean supportsCreateForm() throws Exception {
      return this.supportsCreate();
   }

   protected String createFormName() throws Exception {
      return this.path() + "CreateForm";
   }

   protected String defaultPOSTSummary() throws Exception {
      return this.supportsCreate() ? "itemPOSTCreateOrUpdateSummary" : super.defaultPOSTSummary();
   }

   protected String defaultPOSTDesc() throws Exception {
      return this.supportsCreate() ? "itemPOSTCreateOrUpdateDesc" : super.defaultPOSTDesc();
   }

   protected void addPOSTMethodExamples(MethodInfo m) throws Exception {
      if (this.supportsCreate()) {
         this.addMethodExamples(m, "-create", "itemExamplePOST");
         this.addMethodExamples(m, "-update", "itemExamplePOST");
      } else {
         super.addPOSTMethodExamples(m);
      }

   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      super.addGETLinks(m);
      if (this.supportsCreateForm()) {
         this.addLink(m, "create-form", this.parent().subUri(this.createFormName()), "itemResCreateFormLinkDesc");
      }

   }

   protected String category() throws Exception {
      return this.path();
   }
}
