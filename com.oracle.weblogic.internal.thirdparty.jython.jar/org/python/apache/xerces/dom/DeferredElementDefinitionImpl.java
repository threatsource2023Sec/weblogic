package org.python.apache.xerces.dom;

public class DeferredElementDefinitionImpl extends ElementDefinitionImpl implements DeferredNode {
   static final long serialVersionUID = 6703238199538041591L;
   protected transient int fNodeIndex;

   DeferredElementDefinitionImpl(DeferredDocumentImpl var1, int var2) {
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
   }

   protected void synchronizeChildren() {
      boolean var1 = this.ownerDocument.getMutationEvents();
      this.ownerDocument.setMutationEvents(false);
      this.needsSyncChildren(false);
      DeferredDocumentImpl var2 = (DeferredDocumentImpl)this.ownerDocument;
      this.attributes = new NamedNodeMapImpl(var2);

      for(int var3 = var2.getLastChild(this.fNodeIndex); var3 != -1; var3 = var2.getPrevSibling(var3)) {
         DeferredNode var4 = var2.getNodeObject(var3);
         this.attributes.setNamedItem(var4);
      }

      var2.setMutationEvents(var1);
   }
}
