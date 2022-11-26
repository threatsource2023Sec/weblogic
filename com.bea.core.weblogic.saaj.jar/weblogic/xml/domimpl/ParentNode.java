package weblogic.xml.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract class ParentNode extends ChildNode implements NodeList {
   protected DocumentImpl ownerDocument;
   protected ChildNode firstChild = null;

   protected ParentNode(DocumentImpl ownerDocument) {
      super(ownerDocument);
      this.ownerDocument = ownerDocument;
   }

   public Document getOwnerDocument() {
      return this.ownerDocument;
   }

   protected DocumentImpl ownerDocument() {
      return this.ownerDocument;
   }

   public boolean hasChildNodes() {
      return this.firstChild != null;
   }

   public NodeList getChildNodes() {
      return this;
   }

   public Node getFirstChild() {
      return this.firstChild;
   }

   public ChildNode firstChild() {
      return this.firstChild;
   }

   public Node getLastChild() {
      return this.lastChild();
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
      return this.internalInsertBefore(newChild, refChild, false);
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
      this.internalInsertBefore(newChild, oldChild, true);
      if (newChild != oldChild) {
         this.internalRemoveChild(oldChild, true);
      }

      return oldChild;
   }

   public Node cloneNode(boolean deep) {
      ParentNode newnode = (ParentNode)super.cloneNode(deep);
      newnode.ownerDocument = this.ownerDocument;
      newnode.firstChild = null;
      if (deep) {
         for(ChildNode child = this.firstChild; child != null; child = child.nextSibling) {
            newnode.appendChild(child.cloneNode(true));
         }
      }

      return newnode;
   }

   final Node internalInsertBefore(Node newChild, Node refChild, boolean replace) throws DOMException {
      assert this.ownerDocument != null;

      if (11 == newChild.getNodeType()) {
         return this.internalInsertDocumentFragmentBefore(newChild, refChild);
      } else if (newChild == refChild) {
         refChild = refChild.getNextSibling();
         this.removeChild(newChild);
         this.insertBefore(newChild, refChild);
         return newChild;
      } else {
         if (this.ownerDocument.errorChecking) {
            this.inernalInsertErrorChecks(newChild, refChild);
         }

         ChildNode newInternal = (ChildNode)newChild;
         Node oldparent = newInternal.parentNode();
         if (oldparent != null) {
            oldparent.removeChild(newInternal);
         }

         ChildNode refInternal = (ChildNode)refChild;
         newInternal.ownerNode = this;
         newInternal.isOwned(true);
         if (this.firstChild == null) {
            this.firstChild = newInternal;
            newInternal.isFirstChild(true);
            newInternal.previousSibling = newInternal;
         } else {
            ChildNode prev;
            if (refInternal == null) {
               prev = this.firstChild.previousSibling;
               prev.nextSibling = newInternal;
               newInternal.previousSibling = prev;
               this.firstChild.previousSibling = newInternal;
            } else if (refChild == this.firstChild) {
               this.firstChild.isFirstChild(false);
               newInternal.nextSibling = this.firstChild;
               newInternal.previousSibling = this.firstChild.previousSibling;
               this.firstChild.previousSibling = newInternal;
               this.firstChild = newInternal;
               newInternal.isFirstChild(true);
            } else {
               prev = refInternal.previousSibling;
               newInternal.nextSibling = refInternal;
               prev.nextSibling = newInternal;
               refInternal.previousSibling = newInternal;
               newInternal.previousSibling = prev;
            }
         }

         return newChild;
      }
   }

   private void inernalInsertErrorChecks(Node newChild, Node refChild) {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else if (newChild.getOwnerDocument() != this.ownerDocument) {
         throw new DOMException((short)4, "WRONG_DOCUMENT_ERR");
      } else if (!this.ownerDocument.isKidOK(this, newChild)) {
         String msg = "HIERARCHY_REQUEST_ERR: " + newChild.getNodeName() + " not a valid child type for " + this.getNodeName();
         throw new DOMException((short)3, msg);
      } else if (refChild != null && refChild.getParentNode() != this) {
         throw new DOMException((short)8, "NOT_FOUND_ERR");
      } else {
         boolean treeSafe = true;

         for(NodeImpl a = this; treeSafe && a != null; a = ((NodeImpl)a).parentNode()) {
            treeSafe = newChild != a;
         }

         if (!treeSafe) {
            throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
         }
      }
   }

   private Node internalInsertDocumentFragmentBefore(Node newChild, Node refChild) {
      if (this.ownerDocument.errorChecking) {
         for(Node kid = newChild.getFirstChild(); kid != null; kid = kid.getNextSibling()) {
            if (!this.ownerDocument.isKidOK(this, kid)) {
               throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
            }
         }
      }

      while(newChild.hasChildNodes()) {
         this.insertBefore(newChild.getFirstChild(), refChild);
      }

      return newChild;
   }

   final ChildNode lastChild() {
      return this.firstChild != null ? this.firstChild.previousSibling : null;
   }

   final void lastChild(ChildNode node) {
      if (this.firstChild != null) {
         this.firstChild.previousSibling = node;
      }

   }

   public Node removeChild(Node oldChild) throws DOMException {
      return this.internalRemoveChild(oldChild, false);
   }

   Node internalRemoveChild(Node oldChild, boolean replace) throws DOMException {
      DocumentImpl ownerDocument = this.ownerDocument();
      if (ownerDocument.errorChecking) {
         if (this.isReadOnly()) {
            throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
         }

         if (oldChild != null && oldChild.getParentNode() != this) {
            throw new DOMException((short)8, "NOT_FOUND_ERR");
         }
      }

      if (oldChild == null) {
         return null;
      } else {
         ChildNode oldInternal = (ChildNode)oldChild;
         if (oldInternal == this.firstChild) {
            oldInternal.isFirstChild(false);
            this.firstChild = oldInternal.nextSibling;
            if (this.firstChild != null) {
               this.firstChild.isFirstChild(true);
               this.firstChild.previousSibling = oldInternal.previousSibling;
            }
         } else {
            ChildNode prev = oldInternal.previousSibling;
            ChildNode next = oldInternal.nextSibling;
            prev.nextSibling = next;
            if (next == null) {
               this.firstChild.previousSibling = prev;
            } else {
               next.previousSibling = prev;
            }
         }

         oldInternal.ownerNode = ownerDocument;
         oldInternal.isOwned(false);
         oldInternal.nextSibling = null;
         oldInternal.previousSibling = null;
         return oldInternal;
      }
   }

   protected void setReadOnly(boolean readOnly, boolean deep) {
      super.setReadOnly(readOnly, deep);
      if (deep) {
         for(ChildNode mykid = this.firstChild; mykid != null; mykid = mykid.nextSibling) {
            if (mykid.getNodeType() != 5) {
               mykid.setReadOnly(readOnly, true);
            }
         }
      }

   }

   public int getLength() {
      return this.nodeListGetLength();
   }

   private Node nodeListItem(int index) {
      int cnt = 0;

      Object curr;
      for(curr = this.firstChild; cnt != index; ++cnt) {
         if (curr == null) {
            return null;
         }

         curr = ((Node)curr).getNextSibling();
      }

      return (Node)curr;
   }

   public Node item(int index) {
      return this.nodeListItem(index);
   }

   public void normalize() {
      if (!this.isNormalized()) {
         for(ChildNode kid = this.firstChild; kid != null; kid = kid.nextSibling) {
            kid.normalize();
         }

         this.isNormalized(true);
      }
   }

   private int nodeListGetLength() {
      if (this.firstChild == null) {
         return 0;
      } else {
         int cnt = 0;

         for(Node curr = this.firstChild; curr != null; ++cnt) {
            curr = ((Node)curr).getNextSibling();
         }

         return cnt;
      }
   }
}
