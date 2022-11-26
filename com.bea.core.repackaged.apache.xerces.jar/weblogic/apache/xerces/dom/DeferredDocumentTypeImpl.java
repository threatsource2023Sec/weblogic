package weblogic.apache.xerces.dom;

public class DeferredDocumentTypeImpl extends DocumentTypeImpl implements DeferredNode {
   static final long serialVersionUID = -2172579663227313509L;
   protected transient int fNodeIndex;

   DeferredDocumentTypeImpl(DeferredDocumentImpl var1, int var2) {
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
      this.publicID = var1.getNodeValue(this.fNodeIndex);
      this.systemID = var1.getNodeURI(this.fNodeIndex);
      int var2 = var1.getNodeExtra(this.fNodeIndex);
      this.internalSubset = var1.getNodeValue(var2);
   }

   protected void synchronizeChildren() {
      boolean var1 = this.ownerDocument().getMutationEvents();
      this.ownerDocument().setMutationEvents(false);
      this.needsSyncChildren(false);
      DeferredDocumentImpl var2 = (DeferredDocumentImpl)this.ownerDocument;
      this.entities = new NamedNodeMapImpl(this);
      this.notations = new NamedNodeMapImpl(this);
      this.elements = new NamedNodeMapImpl(this);
      DeferredNode var3 = null;

      for(int var4 = var2.getLastChild(this.fNodeIndex); var4 != -1; var4 = var2.getPrevSibling(var4)) {
         DeferredNode var5 = var2.getNodeObject(var4);
         short var6 = var5.getNodeType();
         switch (var6) {
            case 1:
               if (((DocumentImpl)this.getOwnerDocument()).allowGrammarAccess) {
                  this.insertBefore(var5, var3);
                  var3 = var5;
                  break;
               }
            default:
               System.out.println("DeferredDocumentTypeImpl#synchronizeInfo: node.getNodeType() = " + var5.getNodeType() + ", class = " + var5.getClass().getName());
               break;
            case 6:
               this.entities.setNamedItem(var5);
               break;
            case 12:
               this.notations.setNamedItem(var5);
               break;
            case 21:
               this.elements.setNamedItem(var5);
         }
      }

      this.ownerDocument().setMutationEvents(var1);
      this.setReadOnly(true, false);
   }
}
