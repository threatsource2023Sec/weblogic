package weblogic.xml.domimpl;

import java.io.Serializable;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

public abstract class NodeImpl implements Node, Cloneable, Serializable {
   protected NodeImpl ownerNode;
   protected short flags;
   protected static final short READONLY = 1;
   protected static final short OWNED = 2;
   protected static final short FIRSTCHILD = 4;
   protected static final short SPECIFIED = 8;
   protected static final short IGNORABLEWS = 16;
   protected static final short HASSTRING = 32;
   protected static final short NORMALIZED = 64;
   protected static final short ID = 128;
   protected static final short LEVELONE = 256;
   protected static final short SAAJDIRTY = 512;
   static final long serialVersionUID = 5377485844753851224L;

   protected NodeImpl(DocumentImpl ownerDocument) {
      this.ownerNode = ownerDocument;
   }

   public NodeImpl() {
   }

   public String getNodeValue() throws DOMException {
      return null;
   }

   public void setNodeValue(String x) throws DOMException {
   }

   public Node appendChild(Node newChild) throws DOMException {
      return this.insertBefore(newChild, (Node)null);
   }

   public NamedNodeMap getAttributes() {
      return null;
   }

   public Node removeChild(Node oldChild) throws DOMException {
      throw new DOMException((short)8, "NOT_FOUND_ERR");
   }

   public Document getOwnerDocument() {
      return (Document)(this.isOwned() ? this.ownerNode.ownerDocument() : (Document)this.ownerNode);
   }

   protected DocumentImpl ownerDocument() {
      return this.isOwned() ? this.ownerNode.ownerDocument() : (DocumentImpl)this.ownerNode;
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
      throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
      throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
   }

   public Node cloneNode(boolean deep) {
      NodeImpl newnode;
      try {
         newnode = (NodeImpl)this.clone();
      } catch (CloneNotSupportedException var4) {
         throw new AssertionError(var4);
      }

      newnode.ownerNode = this.ownerDocument();
      newnode.isOwned(false);
      newnode.isReadOnly(false);
      return newnode;
   }

   public void normalize() {
   }

   public boolean isSupported(String feature, String version) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public String getNamespaceURI() {
      return null;
   }

   public String getPrefix() {
      return null;
   }

   public void setPrefix(String prefix) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public String getLocalName() {
      return null;
   }

   public boolean hasAttributes() {
      return false;
   }

   public String getBaseURI() {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public short compareDocumentPosition(Node other) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public String getTextContent() throws DOMException {
      StringBuffer valbuf = new StringBuffer();
      Node n = this.getFirstChild();
      if (n == null) {
         return null;
      } else {
         for(; n != null; n = n.getNextSibling()) {
            if (n.getNodeType() == 3 || n.getNodeType() == 4) {
               Text txtNode = (Text)n;
               valbuf.append(txtNode.getData());
            }
         }

         return new String(valbuf.toString());
      }
   }

   public void setTextContent(String textContent) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public boolean isSameNode(Node other) {
      return this == other;
   }

   public String lookupPrefix(String namespaceURI) {
      if (namespaceURI == null) {
         return null;
      } else {
         short type = this.getNodeType();
         switch (type) {
            case 1:
               String namespace = this.getNamespaceURI();
               return this.lookupNamespacePrefix(namespaceURI, (ElementBase)this);
            case 2:
               if (this.ownerNode.getNodeType() == 1) {
                  return this.ownerNode.lookupPrefix(namespaceURI);
               }

               return null;
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            default:
               NodeImpl ancestor = getElementAncestor(this);
               if (ancestor != null) {
                  return ancestor.lookupPrefix(namespaceURI);
               }

               return null;
            case 6:
            case 10:
            case 11:
            case 12:
               return null;
            case 9:
               return ((NodeImpl)((Document)this).getDocumentElement()).lookupPrefix(namespaceURI);
         }
      }
   }

   String lookupNamespacePrefix(String namespaceURI, ElementBase el) {
      String namespace = this.getNamespaceURI();
      String prefix = this.getPrefix();
      if (namespace != null && namespace.equals(namespaceURI) && prefix != null) {
         String foundNamespace = el.lookupNamespaceURI(prefix);
         if (foundNamespace != null && foundNamespace.equals(namespaceURI)) {
            return prefix;
         }
      }

      if (this.hasAttributes()) {
         NamedNodeMap map = this.getAttributes();
         int length = map.getLength();

         for(int i = 0; i < length; ++i) {
            Node attr = map.item(i);
            String attrPrefix = attr.getPrefix();
            String value = attr.getNodeValue();
            namespace = attr.getNamespaceURI();
            if (namespace != null && namespace.equals("http://www.w3.org/2000/xmlns/") && (attr.getNodeName().equals("xmlns") || attrPrefix != null && attrPrefix.equals("xmlns") && value.equals(namespaceURI))) {
               String localname = attr.getLocalName();
               String foundNamespace = el.lookupNamespaceURI(localname);
               if (foundNamespace != null && foundNamespace.equals(namespaceURI)) {
                  return localname;
               }
            }
         }
      }

      NodeImpl ancestor = getElementAncestor(this);
      return ancestor != null ? ancestor.lookupNamespacePrefix(namespaceURI, el) : null;
   }

   static ElementBase getElementAncestor(NodeImpl currentNode) {
      NodeImpl parent = currentNode.parentNode();
      if (parent != null) {
         short type = parent.getNodeType();
         return type == 1 ? (ElementBase)parent : getElementAncestor(parent);
      } else {
         return null;
      }
   }

   ElementBase getElementAncestor() {
      return getElementAncestor(this);
   }

   public boolean isDefaultNamespace(String namespaceURI) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public String lookupNamespaceURI(String specifiedPrefix) {
      return this.lookupNamespaceURIInternal(specifiedPrefix, true);
   }

   protected String lookupNamespaceURIInternal(String specifiedPrefix, boolean check_element_uri) {
      short type = this.getNodeType();
      switch (type) {
         case 1:
            String namespace = this.getNamespaceURI();
            String prefix = this.getPrefix();
            if (namespace != null && check_element_uri) {
               if (specifiedPrefix == null && prefix == specifiedPrefix) {
                  return namespace;
               }

               if (prefix != null && prefix.equals(specifiedPrefix)) {
                  return namespace;
               }
            }

            if (this.hasAttributes()) {
               NamedNodeMap map = this.getAttributes();
               int length = map.getLength();

               for(int i = 0; i < length; ++i) {
                  Node attr = map.item(i);
                  String attrPrefix = attr.getPrefix();
                  String value = attr.getNodeValue();
                  namespace = attr.getNamespaceURI();
                  if (namespace != null && namespace.equals("http://www.w3.org/2000/xmlns/")) {
                     if (specifiedPrefix == null && attr.getNodeName().equals("xmlns")) {
                        return value;
                     }

                     if (attrPrefix != null && attrPrefix.equals("xmlns") && attr.getLocalName().equals(specifiedPrefix)) {
                        return value;
                     }
                  }
               }
            }

            NodeImpl ancestor = getElementAncestor(this);
            if (ancestor != null) {
               return ancestor.lookupNamespaceURIInternal(specifiedPrefix, check_element_uri);
            }

            return null;
         case 2:
            if (this.ownerNode.getNodeType() == 1) {
               return this.ownerNode.lookupNamespaceURIInternal(specifiedPrefix, check_element_uri);
            }

            return null;
         case 3:
         case 4:
         case 5:
         case 7:
         case 8:
         default:
            NodeImpl ancestor = getElementAncestor(this);
            if (ancestor != null) {
               return ancestor.lookupNamespaceURIInternal(specifiedPrefix, check_element_uri);
            }

            return null;
         case 6:
         case 10:
         case 11:
         case 12:
            return null;
         case 9:
            return ((NodeImpl)((Document)this).getDocumentElement()).lookupNamespaceURIInternal(specifiedPrefix, check_element_uri);
      }
   }

   public boolean isEqualNode(Node arg) {
      if (arg == this) {
         return true;
      } else if (arg.getNodeType() != this.getNodeType()) {
         return false;
      } else if (!this.isEqual(this.getNodeName(), arg.getNodeName())) {
         return false;
      } else if (!this.isEqual(this.getLocalName(), arg.getLocalName())) {
         return false;
      } else if (!this.isEqual(this.getNamespaceURI(), arg.getNamespaceURI())) {
         return false;
      } else if (!this.isEqual(this.getPrefix(), arg.getPrefix())) {
         return false;
      } else {
         return this.isEqual(this.getNodeValue(), arg.getNodeValue());
      }
   }

   private boolean isEqual(String currentValue, String objectValue) {
      if (currentValue == null) {
         if (objectValue != null) {
            return false;
         }
      } else if (!currentValue.equals(objectValue)) {
         return false;
      }

      return true;
   }

   public Object getFeature(String feature, String version) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Object setUserData(String key, Object data, UserDataHandler handler) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Object getUserData(String key) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public NodeImpl parentNode() {
      return null;
   }

   protected final boolean isReadOnly() {
      return (this.flags & 1) != 0;
   }

   protected final void isReadOnly(boolean value) {
      this.flags = (short)(value ? this.flags | 1 : this.flags & -2);
   }

   protected final boolean isOwned() {
      return (this.flags & 2) != 0;
   }

   protected void isOwned(boolean value) {
      this.flags = (short)(value ? this.flags | 2 : this.flags & -3);
   }

   protected final boolean isFirstChild() {
      return (this.flags & 4) != 0;
   }

   protected final void isFirstChild(boolean value) {
      this.flags = (short)(value ? this.flags | 4 : this.flags & -5);
   }

   protected final boolean internalIsIgnorableWhitespace() {
      return (this.flags & 16) != 0;
   }

   protected final void isIgnorableWhitespace(boolean value) {
      this.flags = (short)(value ? this.flags | 16 : this.flags & -17);
   }

   protected final boolean hasStringValue() {
      return (this.flags & 32) != 0;
   }

   protected final void hasStringValue(boolean value) {
      this.flags = (short)(value ? this.flags | 32 : this.flags & -33);
   }

   protected final boolean isNormalized() {
      return (this.flags & 64) != 0;
   }

   protected final void isNormalized(boolean value) {
      if (!value && this.isNormalized() && this.ownerNode != null) {
         this.ownerNode.isNormalized(false);
      }

      this.flags = (short)(value ? this.flags | 64 : this.flags & -65);
   }

   protected final boolean isIdAttribute() {
      return (this.flags & 128) != 0;
   }

   protected final void isIdAttribute(boolean value) {
      this.flags = (short)(value ? this.flags | 128 : this.flags & -129);
   }

   protected final boolean isLevelOne() {
      return (this.flags & 256) != 0;
   }

   protected final void isLevelOne(boolean value) {
      this.flags = (short)(value ? this.flags | 256 : this.flags & -257);
   }

   public final boolean isSaajTyped() {
      return (this.flags & 512) != 0;
   }

   public final void isSaajTyped(boolean value) {
      this.flags = (short)(value ? this.flags | 512 : this.flags & -513);
   }

   protected void setReadOnly(boolean readOnly, boolean deep) {
      this.isReadOnly(readOnly);
   }

   void setOwnerDocument(DocumentImpl doc) {
      if (!this.isOwned()) {
         this.ownerNode = doc;
      }

   }

   public boolean hasUri() {
      String uri = this.getNamespaceURI();
      if (uri == null) {
         return false;
      } else {
         return uri.length() > 0;
      }
   }

   protected int changes() {
      return 0;
   }

   protected static String fixEmptyNamespaceURI(String namespaceURI) {
      if ("".equals(namespaceURI)) {
         namespaceURI = null;
      }

      return namespaceURI;
   }
}
