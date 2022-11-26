package weblogic.apache.xerces.dom;

import org.w3c.dom.Node;

public abstract class ChildNode extends NodeImpl {
   static final long serialVersionUID = -6112455738802414002L;
   protected ChildNode previousSibling;
   protected ChildNode nextSibling;

   protected ChildNode(CoreDocumentImpl var1) {
      super(var1);
   }

   public ChildNode() {
   }

   public Node cloneNode(boolean var1) {
      ChildNode var2 = (ChildNode)super.cloneNode(var1);
      var2.previousSibling = null;
      var2.nextSibling = null;
      var2.isFirstChild(false);
      return var2;
   }

   public Node getParentNode() {
      return this.isOwned() ? this.ownerNode : null;
   }

   final NodeImpl parentNode() {
      return this.isOwned() ? this.ownerNode : null;
   }

   public Node getNextSibling() {
      return this.nextSibling;
   }

   public Node getPreviousSibling() {
      return this.isFirstChild() ? null : this.previousSibling;
   }

   final ChildNode previousSibling() {
      return this.isFirstChild() ? null : this.previousSibling;
   }
}
