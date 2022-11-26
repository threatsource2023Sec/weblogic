package weblogic.management.patching.model;

public enum NodeManagerRelation {
   REGISTERED("Registered"),
   COLLOCATED("Collocated"),
   ORPHANED("Orphaned");

   String relationString;

   public String getRelationString() {
      return this.relationString;
   }

   private NodeManagerRelation(String relationString) {
      this.relationString = relationString;
   }
}
