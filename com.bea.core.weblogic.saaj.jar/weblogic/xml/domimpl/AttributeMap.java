package weblogic.xml.domimpl;

import java.util.List;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

class AttributeMap extends NamedNodeMapImpl {
   static final long serialVersionUID = 8872606282138665383L;

   protected AttributeMap(ElementBase ownerNode) {
      super(ownerNode);
   }

   protected AttributeMap(ElementBase ownerNode, int expected_size) {
      this(ownerNode);
      if (expected_size > 0) {
         this.nodes = newNodeList(expected_size);
      }

   }

   public Node setNamedItem(Node arg) throws DOMException {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else if (arg.getOwnerDocument() != this.ownerNode.ownerDocument()) {
         throw new DOMException((short)4, "WRONG_DOCUMENT_ERR");
      } else if (arg.getNodeType() != 2) {
         throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
      } else {
         AttrBase argn = (AttrBase)arg;
         if (argn.isOwned()) {
            if (argn.getOwnerElement() != this.ownerNode) {
               throw new DOMException((short)10, "INUSE_ATTRIBUTE_ERR");
            } else {
               return arg;
            }
         } else {
            argn.ownerNode = this.ownerNode;
            argn.isOwned(true);
            int i = this.findNamePoint(arg.getNodeName());
            return this.setNamedItemNode(i, argn);
         }
      }
   }

   public Node setNamedItemNS(Node arg) throws DOMException {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else if (arg.getOwnerDocument() != this.ownerNode.ownerDocument()) {
         throw new DOMException((short)4, "WRONG_DOCUMENT_ERR");
      } else if (arg.getNodeType() != 2) {
         throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
      } else {
         AttrBase argn = (AttrBase)arg;
         if (argn.isOwned()) {
            if (argn.getOwnerElement() != this.ownerNode) {
               throw new DOMException((short)10, "INUSE_ATTRIBUTE_ERR");
            } else {
               return arg;
            }
         } else {
            argn.ownerNode = this.ownerNode;
            argn.isOwned(true);
            int i = this.findNamePoint(argn.getNamespaceURI(), argn.getLocalName());
            return this.setNamedItemNode(i, argn);
         }
      }
   }

   private Node setNamedItemNode(int idx, AttrBase argn) {
      AttrBase previous = null;
      if (idx >= 0) {
         previous = (AttrBase)this.nodes.get(idx);
         this.setNodeAt(argn, idx);
         previous.ownerNode = this.ownerNode.ownerDocument();
         previous.isOwned(false);
      } else {
         this.appendNode(argn);
      }

      if (!argn.isNormalized()) {
         this.ownerNode.isNormalized(false);
      }

      return previous;
   }

   public Node removeNamedItem(String name) throws DOMException {
      return this.internalRemoveNamedItem(name, true);
   }

   Node safeRemoveNamedItem(String name) {
      return this.internalRemoveNamedItem(name, false);
   }

   protected Node removeItem(Node item, boolean addDefault) throws DOMException {
      int index = -1;
      if (this.nodes != null) {
         int i = 0;

         for(int len = this.nodes.size(); i < len; ++i) {
            if (this.nodes.get(i) == item) {
               index = i;
               break;
            }
         }
      }

      if (index < 0) {
         throw new DOMException((short)8, "NOT_FOUND_ERR");
      } else {
         return this.remove((AttrBase)item, index, addDefault);
      }
   }

   private Node internalRemoveNamedItem(String name, boolean raiseEx) {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         int i = this.findNamePoint(name);
         if (i < 0) {
            if (raiseEx) {
               throw new DOMException((short)8, "NOT_FOUND_ERR");
            } else {
               return null;
            }
         } else {
            return this.remove((AttrBase)this.nodes.get(i), i, true);
         }
      }
   }

   private final Node remove(AttrBase attr, int index, boolean addDefault) {
      DocumentImpl ownerDocument = this.ownerNode.ownerDocument();
      String name = attr.getNodeName();
      if (attr.isIdAttribute()) {
         ownerDocument.removeIdentifier(attr.getValue());
      }

      this.removeNodeAt(index);
      attr.ownerNode = ownerDocument;
      attr.isOwned(false);
      attr.isIdAttribute(false);
      return attr;
   }

   public Node removeNamedItemNS(String namespaceURI, String name) throws DOMException {
      return this.internalRemoveNamedItemNS(namespaceURI, name, true);
   }

   Node safeRemoveNamedItemNS(String namespaceURI, String name) {
      return this.internalRemoveNamedItemNS(namespaceURI, name, false);
   }

   private Node internalRemoveNamedItemNS(String namespaceURI, String name, boolean raiseEx) {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         int i = this.findNamePoint(namespaceURI, name);
         if (i < 0) {
            if (raiseEx) {
               throw new DOMException((short)8, "NOT_FOUND_ERR");
            } else {
               return null;
            }
         } else {
            AttrBase n = (AttrBase)this.nodes.remove(i);
            DocumentImpl ownerDocument = this.ownerNode.ownerDocument();
            if (n.isIdAttribute()) {
               ownerDocument.removeIdentifier(n.getValue());
            }

            n.ownerNode = ownerDocument;
            n.isOwned(false);
            n.isIdAttribute(false);
            return n;
         }
      }
   }

   public AttributeMap cloneMap(NodeImpl ownerNode) {
      AttributeMap newmap = new AttributeMap((ElementBase)ownerNode);
      newmap.cloneContent(this);
      return newmap;
   }

   protected void cloneContent(NamedNodeMapImpl srcmap) {
      List srcnodes = srcmap.nodes;
      if (srcnodes != null) {
         int size = srcnodes.size();
         if (size != 0) {
            if (this.nodes == null) {
               this.nodes = newNodeList(size);
            } else {
               this.nodes.clear();
            }

            for(int i = 0; i < size; ++i) {
               NodeImpl n = (NodeImpl)srcnodes.get(i);
               NodeImpl clone = (NodeImpl)n.cloneNode(true);
               clone.ownerNode = this.ownerNode;
               clone.isOwned(true);
               this.nodes.add(clone);
            }
         }
      }

   }
}
