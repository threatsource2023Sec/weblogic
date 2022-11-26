package org.python.apache.xerces.dom;

import org.python.apache.xerces.xni.NamespaceContext;
import org.python.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.NamedNodeMap;

public class DeferredElementNSImpl extends ElementNSImpl implements DeferredNode {
   static final long serialVersionUID = -5001885145370927385L;
   protected transient int fNodeIndex;

   DeferredElementNSImpl(DeferredDocumentImpl var1, int var2) {
      super(var1, (String)null);
      this.fNodeIndex = var2;
      this.needsSyncChildren(true);
   }

   public final int getNodeIndex() {
      return this.fNodeIndex;
   }

   protected final void synchronizeData() {
      this.needsSyncData(false);
      DeferredDocumentImpl var1 = (DeferredDocumentImpl)this.ownerDocument;
      boolean var2 = var1.mutationEvents;
      var1.mutationEvents = false;
      this.name = var1.getNodeName(this.fNodeIndex);
      int var3 = this.name.indexOf(58);
      if (var3 < 0) {
         this.localName = this.name;
      } else {
         this.localName = this.name.substring(var3 + 1);
      }

      this.namespaceURI = var1.getNodeURI(this.fNodeIndex);
      this.type = (XSTypeDefinition)var1.getTypeInfo(this.fNodeIndex);
      this.setupDefaultAttributes();
      int var4 = var1.getNodeExtra(this.fNodeIndex);
      if (var4 != -1) {
         NamedNodeMap var5 = this.getAttributes();
         boolean var6 = false;

         do {
            AttrImpl var7 = (AttrImpl)var1.getNodeObject(var4);
            if (var7.getSpecified() || !var6 && (var7.getNamespaceURI() == null || var7.getNamespaceURI() == NamespaceContext.XMLNS_URI || var7.getName().indexOf(58) >= 0)) {
               var5.setNamedItem(var7);
            } else {
               var6 = true;
               var5.setNamedItemNS(var7);
            }

            var4 = var1.getPrevSibling(var4);
         } while(var4 != -1);
      }

      var1.mutationEvents = var2;
   }

   protected final void synchronizeChildren() {
      DeferredDocumentImpl var1 = (DeferredDocumentImpl)this.ownerDocument();
      var1.synchronizeChildren((ParentNode)this, this.fNodeIndex);
   }
}
