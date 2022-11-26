package weblogic.xml.domimpl;

abstract class NSAttrBase extends AttrBase {
   protected NSAttrBase(DocumentImpl ownerDocument) {
      super(ownerDocument);
   }

   protected NSAttrBase(DocumentImpl ownerDocument, String namespace_prefix) {
      super(ownerDocument);
   }

   public final boolean isNamespaceAttribute() {
      return true;
   }

   public abstract boolean definesNamespacePrefix(String var1);
}
