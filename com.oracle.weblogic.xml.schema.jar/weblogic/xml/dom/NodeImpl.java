package weblogic.xml.dom;

import java.util.Iterator;
import javax.xml.soap.Name;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import weblogic.xml.stax.ReaderToWriter;

public class NodeImpl implements Node, NodeList, Cloneable {
   private Document ownerDocument;
   private NodeImpl parent;
   private NodeImpl firstChild;
   private NodeImpl lastChild;
   private NodeImpl previousSibling;
   private NodeImpl nextSibling;
   private int numChildren = 0;
   private short nodeType;
   private String nodeName;
   private String nodeValue;
   private String localName;
   private transient NodeImpl currentNode;
   private transient int currentNodeCount;
   private static final boolean debug = false;

   public NodeImpl() {
      this.currentNode = this.firstChild;
      this.currentNodeCount = 0;
   }

   public String getNodeName() {
      return this.nodeName;
   }

   public void setNodeName(String name) {
      this.nodeName = name;
   }

   public String getNodeValue() throws DOMException {
      return this.nodeValue;
   }

   public void setNodeValue(String value) throws DOMException {
      this.nodeValue = value;
   }

   public void setNodeType(short type) {
      this.nodeType = type;
   }

   public short getNodeType() {
      return this.nodeType;
   }

   public Node getParentNode() {
      return this.parent;
   }

   public NodeList getChildNodes() {
      return (NodeList)(this.firstChild == null ? Util.EMPTY_NODELIST : this);
   }

   public Node getFirstChild() {
      return this.firstChild;
   }

   public Node getLastChild() {
      return this.lastChild;
   }

   public Node getPreviousSibling() {
      return this.previousSibling;
   }

   public Node getNextSibling() {
      return this.nextSibling;
   }

   public NamedNodeMap getAttributes() {
      return null;
   }

   public void setOwnerDocument(Document doc) {
      this.ownerDocument = doc;
   }

   public Document getOwnerDocument() {
      if (this.ownerDocument == null && this.parent != null) {
         this.ownerDocument = this.parent.getOwnerDocument();
      }

      return this.ownerDocument;
   }

   public void setParentNode(Node newParent) {
      this.parent = (NodeImpl)newParent;
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
      if (newChild == null) {
         throw new NullPointerException("newChild may not be null");
      } else if (refChild == null) {
         return null;
      } else if (newChild == refChild) {
         return newChild;
      } else if (newChild.getNodeType() != 11) {
         NodeImpl internalRefChild = (NodeImpl)refChild;
         if (internalRefChild.parent != this) {
            throw new DOMException((short)8, "Unable to insert before the refChild because it is not a child of this node");
         } else {
            NodeImpl internalNewChild = (NodeImpl)newChild;
            if (internalNewChild.parent != null) {
               internalNewChild.parent.removeChild(internalNewChild);
            }

            ++this.numChildren;
            internalNewChild.parent = this;
            if (this.firstChild == refChild) {
               this.firstChild = internalNewChild;
            } else {
               internalRefChild.previousSibling.nextSibling = internalNewChild;
            }

            internalNewChild.nextSibling = internalRefChild;
            internalRefChild.previousSibling = internalNewChild;
            this.resetCache();
            return internalNewChild;
         }
      } else {
         while(newChild.hasChildNodes()) {
            this.insertBefore(newChild.getFirstChild(), refChild);
         }

         return newChild;
      }
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
      if (newChild == null) {
         throw new NullPointerException("newChild may not be null");
      } else {
         this.insertBefore(newChild, oldChild);
         this.removeChild(oldChild);
         this.resetCache();
         return oldChild;
      }
   }

   public Node removeChild(Node oldChild) throws DOMException {
      if (oldChild == null) {
         return null;
      } else {
         NodeImpl c = (NodeImpl)oldChild;
         if (c.parent != this) {
            throw new DOMException((short)8, "Attempt to removeChild that is not a child of this Node");
         } else {
            c.parent = null;
            --this.numChildren;
            if (this.firstChild == c) {
               this.firstChild = this.firstChild.nextSibling;
               if (this.lastChild == c) {
                  this.lastChild = this.firstChild;
               }

               this.resetCache();
               return c;
            } else if (this.lastChild == c) {
               this.lastChild = this.lastChild.previousSibling;
               this.resetCache();
               return c;
            } else {
               c.previousSibling.nextSibling = c.nextSibling;
               this.resetCache();
               return c;
            }
         }
      }
   }

   public Node appendChild(Node newChild) throws DOMException {
      if (newChild == this) {
         throw new DOMException((short)3, "A Node may not be its own child");
      } else if (newChild == null) {
         throw new NullPointerException("newChild may not be null");
      } else if (newChild.getNodeType() != 11) {
         NodeImpl internalChild = (NodeImpl)newChild;
         if (internalChild.parent != null) {
            internalChild.parent.removeChild(internalChild);
         }

         internalChild.parent = this;
         if (this.firstChild == null) {
            this.numChildren = 1;
            this.firstChild = internalChild;
            this.lastChild = internalChild;
            this.resetCache();
            return internalChild;
         } else {
            ++this.numChildren;
            this.lastChild.nextSibling = internalChild;
            internalChild.previousSibling = this.lastChild;
            this.lastChild = internalChild;
            this.resetCache();
            return internalChild;
         }
      } else {
         while(newChild.hasChildNodes()) {
            this.appendChild(newChild.getFirstChild());
         }

         return newChild;
      }
   }

   public boolean hasChildNodes() {
      return this.numChildren != 0;
   }

   public void removeChildren() {
      this.firstChild = null;
      this.lastChild = null;
      this.numChildren = 0;
      this.resetCache();
   }

   public Node cloneNode(boolean deep) {
      NodeImpl newnode;
      try {
         newnode = (NodeImpl)this.clone();
      } catch (CloneNotSupportedException var4) {
         throw new RuntimeException(var4.toString());
      }

      newnode.removeChildren();
      newnode.parent = null;
      if (deep) {
         for(NodeImpl child = this.firstChild; child != null; child = child.nextSibling) {
            newnode.appendChild(child.cloneNode(true));
         }
      }

      return newnode;
   }

   public void normalize() {
   }

   public boolean isSupported(String feature, String version) {
      return false;
   }

   public String getNamespaceURI() {
      return null;
   }

   public String getPrefix() {
      return null;
   }

   public void setPrefix(String prefix) throws DOMException {
   }

   public String getLocalName() {
      return null;
   }

   public boolean hasAttributes() {
      return false;
   }

   public Iterator getChildren() {
      return new ChildIterator(this);
   }

   private void resetCache() {
      this.currentNode = this.firstChild;
      this.currentNodeCount = 0;
   }

   public int getLength() {
      return this.numChildren;
   }

   public Node item(int index) {
      if (index >= this.numChildren) {
         throw new IndexOutOfBoundsException(index + " greater than  the number of chilren " + this.numChildren);
      } else if (this.currentNodeCount == index) {
         if (this.currentNode == null) {
            throw new NullPointerException("Internal Error: null child");
         } else {
            return this.currentNode;
         }
      } else {
         switch (index) {
            case 0:
               this.currentNode = this.firstChild;
               this.currentNodeCount = 0;
               if (this.currentNode == null) {
                  throw new NullPointerException("Internal Error: null child");
               }

               return this.currentNode;
            case 1:
               this.currentNode = this.firstChild.nextSibling;
               this.currentNodeCount = 1;
               if (this.currentNode == null) {
                  throw new NullPointerException("Internal Error: null child");
               }

               return this.currentNode;
            case 2:
               this.currentNode = this.firstChild.nextSibling.nextSibling;
               this.currentNodeCount = 2;
               if (this.currentNode == null) {
                  throw new NullPointerException("Internal Error: null child");
               }

               return this.currentNode;
            default:
               if (this.currentNodeCount + 1 == index) {
                  this.currentNode = this.currentNode.nextSibling;
                  ++this.currentNodeCount;
                  if (this.currentNode == null) {
                     throw new NullPointerException("Internal Error: null child");
                  } else {
                     return this.currentNode;
                  }
               } else if (this.currentNodeCount - 1 == index) {
                  this.currentNode = this.currentNode.previousSibling;
                  --this.currentNodeCount;
                  if (this.currentNode == null) {
                     throw new NullPointerException("Internal Error: null child");
                  } else {
                     return this.currentNode;
                  }
               } else if (this.numChildren - 1 == index) {
                  this.currentNode = this.lastChild;
                  if (this.currentNode == null) {
                     throw new NullPointerException("Internal Error: null child");
                  } else {
                     this.currentNodeCount = this.numChildren - 1;
                     return this.currentNode;
                  }
               } else {
                  this.currentNode = this.firstChild.nextSibling.nextSibling.nextSibling;
                  this.currentNodeCount = 3;

                  for(int i = 3; i < this.numChildren; ++i) {
                     if (i == index) {
                        if (this.currentNode == null) {
                           throw new NullPointerException("Internal Error: null child");
                        }

                        return this.currentNode;
                     }

                     this.currentNode = this.currentNode.nextSibling;
                     ++this.currentNodeCount;
                  }

                  throw new DOMException((short)8, "child " + index + " not found");
               }
         }
      }
   }

   public NodeIterator iterator() {
      return new NodeIterator(this);
   }

   public void print(StringBuffer b, int tab) {
      b.append("NoData");
   }

   public void write(XMLStreamWriter out) throws XMLStreamException {
      ReaderToWriter w = new ReaderToWriter(out);
      XMLStreamReader reader = this.reader();

      while(reader.hasNext()) {
         w.write(reader);
         reader.next();
      }

   }

   public NodeList getElementsByTagName(String name) {
      NodeListImpl l = new NodeListImpl();
      NodeIterator i = this.iterator();
      i.next();

      while(i.hasNext()) {
         Node n = i.nextNode();
         if (n.getNodeType() == 1 && name.equals(((Element)n).getTagName())) {
            l.add(n);
         }
      }

      return l;
   }

   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
      NodeListImpl l = new NodeListImpl();
      NodeIterator i = this.iterator();
      i.next();

      while(true) {
         Node n;
         do {
            do {
               do {
                  if (!i.hasNext()) {
                     return l;
                  }

                  n = i.nextNode();
               } while(n.getNodeType() != 1);
            } while(!localName.equals(n.getLocalName()));
         } while(namespaceURI != null && !"*".equals(namespaceURI) && !namespaceURI.equals(n.getNamespaceURI()));

         l.add(n);
      }
   }

   public XMLStreamReader reader() throws XMLStreamException {
      return new DOMStreamReader(this);
   }

   public Node createChild(Name name) {
      throw new IllegalStateException("createChild is abstract");
   }

   public Node renameNode(Node node, String s, String s1) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void normalizeDocument() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public DOMConfiguration getDomConfig() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setDocumentURI(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getDocumentURI() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public Node adoptNode(Node a) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setStrictErrorChecking(boolean a) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean getStrictErrorChecking() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setXmlVersion(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getXmlVersion() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setXmlStandalone(boolean flag) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean getXmlStandalone() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getXmlEncoding() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getInputEncoding() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public Object getUserData(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public Object setUserData(String s, Object obj, UserDataHandler userdatahandler) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public Object getFeature(String s, String s1) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean isEqualNode(Node node) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String lookupNamespaceURI(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean isDefaultNamespace(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String lookupPrefix(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean isSameNode(Node node) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setTextContent(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getTextContent() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public short compareDocumentPosition(Node node) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getBaseURI() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }
}
