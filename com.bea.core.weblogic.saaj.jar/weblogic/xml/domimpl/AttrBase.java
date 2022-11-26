package weblogic.xml.domimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

public abstract class AttrBase extends NodeImpl implements Attr, NodeList {
   protected Object value = null;

   public AttrBase(DocumentImpl ownerDocument) {
      super(ownerDocument);
   }

   public final short getNodeType() {
      return 2;
   }

   public final Node getParentNode() {
      return null;
   }

   public abstract boolean isNamespaceAttribute();

   public Node getFirstChild() {
      this.makeChildNode();
      return (Node)this.value;
   }

   public Node getLastChild() {
      return this.lastChild();
   }

   final ChildNode lastChild() {
      this.makeChildNode();
      return this.value != null ? ((ChildNode)this.value).previousSibling : null;
   }

   final void lastChild(ChildNode node) {
      if (this.value != null) {
         ((ChildNode)this.value).previousSibling = node;
      }

   }

   public final Node getPreviousSibling() {
      return null;
   }

   public final Node getNextSibling() {
      return null;
   }

   public boolean getSpecified() {
      return true;
   }

   public String getNodeValue() {
      return this.getValue();
   }

   public void setNodeValue(String value) throws DOMException {
      this.setValue(value);
   }

   public NodeList getChildNodes() {
      return this;
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
      return this.internalInsertBefore(newChild, refChild, false);
   }

   public String getValue() {
      if (this.value == null) {
         return "";
      } else if (this.hasStringValue()) {
         return (String)this.value;
      } else {
         ChildNode firstChild = (ChildNode)this.value;
         String data = null;
         if (firstChild.getNodeType() == 5) {
            data = ((EntityReferenceImpl)firstChild).getEntityRefValue();
         } else {
            data = firstChild.getNodeValue();
         }

         ChildNode node = firstChild.nextSibling;
         if (node != null && data != null) {
            StringBuilder value;
            for(value = new StringBuilder(data); node != null; node = node.nextSibling) {
               if (node.getNodeType() == 5) {
                  data = ((EntityReferenceImpl)node).getEntityRefValue();
                  if (data == null) {
                     return "";
                  }

                  value.append(data);
               } else {
                  value.append(node.getNodeValue());
               }
            }

            return value.toString();
         } else {
            return data == null ? "" : data;
         }
      }
   }

   public Node cloneNode(boolean deep) {
      AttrBase clone = (AttrBase)super.cloneNode(deep);
      if (!clone.hasStringValue()) {
         clone.value = null;

         for(Node child = (Node)this.value; child != null; child = child.getNextSibling()) {
            clone.appendChild(child.cloneNode(true));
         }
      }

      return clone;
   }

   public void normalize() {
      if (!this.isNormalized() && !this.hasStringValue()) {
         ChildNode firstChild = (ChildNode)this.value;

         Object next;
         for(Node kid = firstChild; kid != null; kid = next) {
            next = ((Node)kid).getNextSibling();
            if (((Node)kid).getNodeType() == 3) {
               if (next != null && ((Node)next).getNodeType() == 3) {
                  ((Text)kid).appendData(((Node)next).getNodeValue());
                  this.internalRemoveChild((Node)next, true);
                  next = kid;
               } else if (((Node)kid).getNodeValue().length() == 0) {
                  this.internalRemoveChild((Node)kid, true);
               }
            }
         }

         this.isNormalized(true);
      }
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
      this.makeChildNode();
      DocumentImpl ownerDocument = this.ownerDocument();
      this.internalInsertBefore(newChild, oldChild, true);
      if (newChild != oldChild) {
         this.internalRemoveChild(oldChild, true);
      }

      return oldChild;
   }

   public void setValue(String newvalue) {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         DocumentImpl ownerDocument = this.ownerDocument();
         Element ownerElement = this.getOwnerElement();
         String oldvalue = "";
         if (this.value != null) {
            if (this.hasStringValue()) {
               oldvalue = (String)this.value;
            } else {
               oldvalue = this.getValue();
               ChildNode firstChild = (ChildNode)this.value;
               firstChild.previousSibling = null;
               firstChild.isFirstChild(false);
               firstChild.ownerNode = ownerDocument;
            }

            this.value = null;
            if (this.isIdAttribute() && ownerElement != null) {
               ownerDocument.removeIdentifier(oldvalue);
            }
         }

         this.value = newvalue;
         this.hasStringValue(true);
         if (this.isIdAttribute() && ownerElement != null) {
            ownerDocument.putIdentifier(newvalue, ownerElement);
         }

      }
   }

   protected void makeChildNode() {
      if (this.hasStringValue()) {
         if (this.value != null) {
            TextImpl text = (TextImpl)this.ownerDocument().createTextNode((String)this.value);
            this.value = text;
            text.isFirstChild(true);
            text.previousSibling = text;
            text.ownerNode = this;
            text.isOwned(true);
         }

         this.hasStringValue(false);
      }

   }

   public final Element getOwnerElement() {
      return (Element)((Element)(this.isOwned() ? this.ownerNode : null));
   }

   public final boolean hasChildNodes() {
      return this.value != null;
   }

   public final TypeInfo getSchemaTypeInfo() {
      return null;
   }

   public boolean isId() {
      return this.isIdAttribute();
   }

   public Node item(int index) {
      if (this.hasStringValue()) {
         if (index == 0 && this.value != null) {
            this.makeChildNode();
            return (Node)this.value;
         } else {
            return null;
         }
      } else {
         ChildNode node = (ChildNode)this.value;

         for(int i = 0; i < index && node != null; ++i) {
            node = node.nextSibling;
         }

         return node;
      }
   }

   public int getLength() {
      if (this.hasStringValue()) {
         return 1;
      } else {
         ChildNode node = (ChildNode)this.value;

         int length;
         for(length = 0; node != null; node = node.nextSibling) {
            ++length;
         }

         return length;
      }
   }

   Node internalInsertBefore(Node newChild, Node refChild, boolean replace) throws DOMException {
      DocumentImpl ownerDocument = this.ownerDocument();
      boolean errorChecking = ownerDocument.errorChecking;
      if (newChild.getNodeType() == 11) {
         if (errorChecking) {
            for(Node kid = newChild.getFirstChild(); kid != null; kid = kid.getNextSibling()) {
               if (!ownerDocument.isKidOK(this, kid)) {
                  throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
               }
            }
         }

         while(newChild.hasChildNodes()) {
            this.insertBefore(newChild.getFirstChild(), refChild);
         }

         return newChild;
      } else if (newChild == refChild) {
         refChild = refChild.getNextSibling();
         this.removeChild(newChild);
         this.insertBefore(newChild, refChild);
         return newChild;
      } else {
         if (errorChecking) {
            if (this.isReadOnly()) {
               throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            }

            if (newChild.getOwnerDocument() != ownerDocument) {
               throw new DOMException((short)4, "WRONG_DOCUMENT_ERR");
            }

            if (!ownerDocument.isKidOK(this, newChild)) {
               throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
            }

            if (refChild != null && refChild.getParentNode() != this) {
               throw new DOMException((short)8, "NOT_FOUND_ERR");
            }

            boolean treeSafe = true;

            for(NodeImpl a = this; treeSafe && a != null; a = ((NodeImpl)a).parentNode()) {
               treeSafe = newChild != a;
            }

            if (!treeSafe) {
               throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
            }
         }

         this.makeChildNode();
         ChildNode newInternal = (ChildNode)newChild;
         Node oldparent = newInternal.parentNode();
         if (oldparent != null) {
            oldparent.removeChild(newInternal);
         }

         ChildNode refInternal = (ChildNode)refChild;
         newInternal.ownerNode = this;
         newInternal.isOwned(true);
         ChildNode firstChild = (ChildNode)this.value;
         if (firstChild == null) {
            this.value = newInternal;
            newInternal.isFirstChild(true);
            newInternal.previousSibling = newInternal;
         } else {
            ChildNode prev;
            if (refInternal == null) {
               prev = firstChild.previousSibling;
               prev.nextSibling = newInternal;
               newInternal.previousSibling = prev;
               firstChild.previousSibling = newInternal;
            } else if (refChild == firstChild) {
               firstChild.isFirstChild(false);
               newInternal.nextSibling = firstChild;
               newInternal.previousSibling = firstChild.previousSibling;
               firstChild.previousSibling = newInternal;
               this.value = newInternal;
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
         ChildNode oldPreviousSibling;
         if (oldInternal == this.value) {
            oldInternal.isFirstChild(false);
            this.value = oldInternal.nextSibling;
            oldPreviousSibling = (ChildNode)this.value;
            if (oldPreviousSibling != null) {
               oldPreviousSibling.isFirstChild(true);
               oldPreviousSibling.previousSibling = oldInternal.previousSibling;
            }
         } else {
            oldPreviousSibling = oldInternal.previousSibling;
            ChildNode next = oldInternal.nextSibling;
            oldPreviousSibling.nextSibling = next;
            if (next == null) {
               ChildNode firstChild = (ChildNode)this.value;
               firstChild.previousSibling = oldPreviousSibling;
            } else {
               next.previousSibling = oldPreviousSibling;
            }
         }

         oldPreviousSibling = oldInternal.previousSibling();
         oldInternal.ownerNode = ownerDocument;
         oldInternal.isOwned(false);
         oldInternal.nextSibling = null;
         oldInternal.previousSibling = null;
         return oldInternal;
      }
   }
}
