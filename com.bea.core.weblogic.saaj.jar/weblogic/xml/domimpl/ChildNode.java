package weblogic.xml.domimpl;

import org.w3c.dom.Node;

public abstract class ChildNode extends NodeImpl {
   protected ChildNode previousSibling;
   protected ChildNode nextSibling;

   protected ChildNode(DocumentImpl ownerDocument) {
      super(ownerDocument);
   }

   public ChildNode() {
   }

   public Node cloneNode(boolean deep) {
      ChildNode newnode = (ChildNode)super.cloneNode(deep);
      newnode.previousSibling = null;
      newnode.nextSibling = null;
      newnode.isFirstChild(false);
      return newnode;
   }

   public Node getParentNode() {
      return this.isOwned() ? this.ownerNode : null;
   }

   public final NodeImpl parentNode() {
      return this.isOwned() ? this.ownerNode : null;
   }

   public final ChildNode nextSibling() {
      return this.nextSibling;
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
