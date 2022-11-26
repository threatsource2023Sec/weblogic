package weblogic.management.rest.lib.bean.resources;

public abstract class CustomCollectionChildResourceMetaData extends CustomItemResourceMetaData {
   public String path() throws Exception {
      return "{" + this.pathParamName() + "}";
   }

   protected String identityProperty() {
      return "name";
   }

   protected String pathParamName() throws Exception {
      return this.identityProperty();
   }
}
