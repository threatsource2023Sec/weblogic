package weblogic.xml.domimpl;

class AttrImpl extends AttrBase {
   private String name;

   public AttrImpl(DocumentImpl ownerDocument, String name) {
      super(ownerDocument);
      this.name = name;
   }

   public String getNodeName() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public final boolean isNamespaceAttribute() {
      return false;
   }
}
