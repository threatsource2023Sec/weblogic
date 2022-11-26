package weblogic.xml.domimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamedNodeMapImpl implements NamedNodeMap, Serializable {
   static final long serialVersionUID = -7039242451046758020L;
   private short flags;
   private static final short READONLY = 1;
   protected List nodes;
   protected final NodeImpl ownerNode;
   private static final int DEFAULT_NODE_LIST_SIZE = 5;

   protected NamedNodeMapImpl(NodeImpl ownerNode) {
      this.ownerNode = ownerNode;
   }

   protected NamedNodeMapImpl(NodeImpl ownerNode, int expected_size) {
      this(ownerNode);
      this.nodes = newNodeList(expected_size);
   }

   public int getLength() {
      return this.nodes != null ? this.nodes.size() : 0;
   }

   public Node item(int index) {
      return this.nodes != null && index < this.nodes.size() ? (Node)((Node)this.nodes.get(index)) : null;
   }

   public Node getNamedItem(String name) {
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else if (this.nodes == null) {
         return null;
      } else {
         int i = 0;

         for(int len = this.nodes.size(); i < len; ++i) {
            NodeImpl a = (NodeImpl)this.nodes.get(i);
            if (name.equals(a.getNodeName())) {
               return a;
            }
         }

         return null;
      }
   }

   public Node getNamedItemNS(String namespaceURI, String localName) {
      int i = this.findNamePoint(namespaceURI, localName);
      return i < 0 ? null : (Node)((Node)this.nodes.get(i));
   }

   public Node setNamedItem(Node arg) throws DOMException {
      String msg;
      if (this.isReadOnly()) {
         msg = "NO_MODIFICATION_ALLOWED_ERR";
         throw new DOMException((short)7, msg);
      } else if (arg.getOwnerDocument() != this.ownerNode.ownerDocument()) {
         msg = "WRONG_DOCUMENT_ERR";
         throw new DOMException((short)4, msg);
      } else {
         int i = this.findNamePoint(arg.getNodeName());
         NodeImpl previous = null;
         if (i >= 0) {
            previous = (NodeImpl)this.nodes.get(i);
            this.setNodeAt(arg, i);
         } else {
            this.appendNode(arg);
         }

         return previous;
      }
   }

   public Node setNamedItemNS(Node arg) throws DOMException {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else if (arg.getOwnerDocument() != this.ownerNode.ownerDocument()) {
         throw new DOMException((short)4, "WRONG_DOCUMENT_ERR");
      } else {
         int i = this.findNamePoint(arg.getNamespaceURI(), arg.getLocalName());
         if (i >= 0) {
            Node previous = (Node)this.nodes.get(i);
            this.setNodeAt(arg, i);
            return previous;
         } else {
            this.appendNode(arg);
            return null;
         }
      }
   }

   public Node removeNamedItem(String name) throws DOMException {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         int len = this.nodes == null ? 0 : this.nodes.size();

         for(int i = 0; i < len; ++i) {
            NodeImpl a = (NodeImpl)this.nodes.get(i);
            if (name.equals(a.getNodeName())) {
               return (Node)this.nodes.remove(i);
            }
         }

         throw new DOMException((short)8, "NOT_FOUND_ERR");
      }
   }

   public Node removeNamedItemNS(String namespaceURI, String name) throws DOMException {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         int i = this.findNamePoint(namespaceURI, name);
         if (i < 0) {
            throw new DOMException((short)8, "NOT_FOUND_ERR");
         } else {
            NodeImpl n = (NodeImpl)this.nodes.get(i);
            this.removeNodeAt(i);
            return n;
         }
      }
   }

   public NamedNodeMapImpl cloneMap(NodeImpl ownerNode) {
      NamedNodeMapImpl newmap = new NamedNodeMapImpl(ownerNode);
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
               NodeImpl n = (NodeImpl)srcmap.nodes.get(i);
               NodeImpl clone = (NodeImpl)n.cloneNode(true);
               this.setNodeAt(clone, i);
            }
         }
      }

   }

   void setReadOnly(boolean readOnly, boolean deep) {
      this.isReadOnly(readOnly);
      if (deep && this.nodes != null) {
         for(int i = this.nodes.size() - 1; i >= 0; --i) {
            ((NodeImpl)this.nodes.get(i)).setReadOnly(readOnly, deep);
         }
      }

   }

   boolean getReadOnly() {
      return this.isReadOnly();
   }

   void setOwnerDocument(DocumentImpl doc) {
      if (this.nodes != null) {
         for(int i = 0; i < this.nodes.size(); ++i) {
            ((NodeImpl)this.item(i)).setOwnerDocument(doc);
         }
      }

   }

   final boolean isReadOnly() {
      return (this.flags & 1) != 0;
   }

   private void isReadOnly(boolean value) {
      this.flags = (short)(value ? this.flags | 1 : this.flags & -2);
   }

   protected int findNamePoint(String name) {
      if (this.nodes == null) {
         return -1;
      } else if (name == null) {
         return -1;
      } else {
         int i = 0;

         for(int len = this.nodes.size(); i < len; ++i) {
            NodeImpl a = (NodeImpl)this.nodes.get(i);
            if (name.equals(a.getNodeName())) {
               return i;
            }
         }

         return -1;
      }
   }

   protected final int findNamePoint(String namespaceURI, String name) {
      if (this.nodes == null) {
         return -1;
      } else if (name == null) {
         return -1;
      } else {
         int i = 0;

         for(int len = this.nodes.size(); i < len; ++i) {
            NodeImpl a = (NodeImpl)this.nodes.get(i);
            if (namespaceURI == null) {
               String aLocalName = a.getLocalName();
               if (a.getNamespaceURI() == null && (name.equals(aLocalName) || aLocalName == null && name.equals(a.getNodeName()))) {
                  return i;
               }
            } else if (namespaceURI.equals(a.getNamespaceURI()) && name.equals(a.getLocalName())) {
               return i;
            }
         }

         return -1;
      }
   }

   protected void removeItem(int index) {
      if (this.nodes != null && index < this.nodes.size()) {
         this.removeNodeAt(index);
      }

   }

   protected Object getItem(int index) {
      return this.nodes != null ? this.nodes.get(index) : null;
   }

   protected final void setNodeAt(Node arg, int i) {
      this.nodes.set(i, arg);
   }

   protected final void appendNode(Node arg) {
      if (this.nodes == null) {
         this.nodes = newNodeList();
      }

      this.nodes.add(arg);
   }

   protected static List newNodeList() {
      return newNodeList(5);
   }

   protected static List newNodeList(int size) {
      assert size > 0;

      return new ArrayList(size);
   }

   protected int getNamedItemIndex(String namespaceURI, String localName) {
      return this.findNamePoint(namespaceURI, localName);
   }

   public void removeAll() {
      if (this.nodes != null) {
         this.nodes.clear();
      }

   }

   protected void removeNodeAt(int index) {
      if (this.nodes != null) {
         this.nodes.remove(index);
      }

   }
}
