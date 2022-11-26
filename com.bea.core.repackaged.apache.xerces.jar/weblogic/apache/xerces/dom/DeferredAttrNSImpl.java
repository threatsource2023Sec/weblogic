package weblogic.apache.xerces.dom;

public final class DeferredAttrNSImpl extends AttrNSImpl implements DeferredNode {
   static final long serialVersionUID = 6074924934945957154L;
   protected transient int fNodeIndex;

   DeferredAttrNSImpl(DeferredDocumentImpl var1, int var2) {
      super(var1, (String)null);
      this.fNodeIndex = var2;
      this.needsSyncData(true);
      this.needsSyncChildren(true);
   }

   public int getNodeIndex() {
      return this.fNodeIndex;
   }

   protected void synchronizeData() {
      this.needsSyncData(false);
      DeferredDocumentImpl var1 = (DeferredDocumentImpl)this.ownerDocument();
      this.name = var1.getNodeName(this.fNodeIndex);
      int var2 = this.name.indexOf(58);
      if (var2 < 0) {
         this.localName = this.name;
      } else {
         this.localName = this.name.substring(var2 + 1);
      }

      int var3 = var1.getNodeExtra(this.fNodeIndex);
      this.isSpecified((var3 & 32) != 0);
      this.isIdAttribute((var3 & 512) != 0);
      this.namespaceURI = var1.getNodeURI(this.fNodeIndex);
      int var4 = var1.getLastChild(this.fNodeIndex);
      this.type = var1.getTypeInfo(var4);
   }

   protected void synchronizeChildren() {
      DeferredDocumentImpl var1 = (DeferredDocumentImpl)this.ownerDocument();
      var1.synchronizeChildren((AttrImpl)this, this.fNodeIndex);
   }
}
