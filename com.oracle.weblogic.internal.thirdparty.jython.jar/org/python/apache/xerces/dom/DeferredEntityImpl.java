package org.python.apache.xerces.dom;

public class DeferredEntityImpl extends EntityImpl implements DeferredNode {
   static final long serialVersionUID = 4760180431078941638L;
   protected transient int fNodeIndex;

   DeferredEntityImpl(DeferredDocumentImpl var1, int var2) {
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
      DeferredDocumentImpl var1 = (DeferredDocumentImpl)this.ownerDocument;
      this.name = var1.getNodeName(this.fNodeIndex);
      this.publicId = var1.getNodeValue(this.fNodeIndex);
      this.systemId = var1.getNodeURI(this.fNodeIndex);
      int var2 = var1.getNodeExtra(this.fNodeIndex);
      var1.getNodeType(var2);
      this.notationName = var1.getNodeName(var2);
      this.version = var1.getNodeValue(var2);
      this.encoding = var1.getNodeURI(var2);
      int var3 = var1.getNodeExtra(var2);
      this.baseURI = var1.getNodeName(var3);
      this.inputEncoding = var1.getNodeValue(var3);
   }

   protected void synchronizeChildren() {
      this.needsSyncChildren(false);
      this.isReadOnly(false);
      DeferredDocumentImpl var1 = (DeferredDocumentImpl)this.ownerDocument();
      var1.synchronizeChildren((ParentNode)this, this.fNodeIndex);
      this.setReadOnly(true, true);
   }
}
