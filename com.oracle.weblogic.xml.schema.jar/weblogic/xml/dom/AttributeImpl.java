package weblogic.xml.dom;

import javax.xml.soap.Name;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public final class AttributeImpl implements Name, Attr, Cloneable {
   private String uri;
   private String prefix;
   private String value;
   private String localName;
   private Element owner;

   public AttributeImpl() {
   }

   public AttributeImpl(Element owner, String uri, String prefix, String localName, String value) {
      this.owner = owner;
      this.uri = uri;
      this.prefix = prefix;
      this.localName = localName;
      this.value = value;
   }

   public String getName() {
      return this.getNodeName();
   }

   public Element getOwnerElement() {
      return this.owner;
   }

   public boolean getSpecified() {
      return true;
   }

   public String getValue() {
      return this.getNodeValue();
   }

   public void setValue(String value) {
      this.setNodeValue(value);
   }

   public String getURI() {
      return this.getNamespaceURI();
   }

   public String getQualifiedName() {
      return this.getPrefix() == null ? this.getLocalName() : this.getPrefix() + ":" + this.getLocalName();
   }

   public String getNodeName() {
      return this.prefix != null && !"".equals(this.prefix) ? this.prefix + ":" + this.localName : this.localName;
   }

   public String getNodeValue() throws DOMException {
      return this.value;
   }

   public void setNodeValue(String value) throws DOMException {
      this.value = value;
   }

   public short getNodeType() {
      return 2;
   }

   public Node getParentNode() {
      return null;
   }

   public NodeList getChildNodes() {
      return Util.EMPTY_NODELIST;
   }

   public Node getFirstChild() {
      return null;
   }

   public Node getLastChild() {
      return null;
   }

   public Node getPreviousSibling() {
      return null;
   }

   public Node getNextSibling() {
      return null;
   }

   public NamedNodeMap getAttributes() {
      return null;
   }

   public Document getOwnerDocument() {
      return null;
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
      return null;
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
      return null;
   }

   public Node removeChild(Node oldChild) throws DOMException {
      return null;
   }

   public Node appendChild(Node newChild) throws DOMException {
      return null;
   }

   public boolean hasChildNodes() {
      return false;
   }

   public void removeChildren() {
   }

   public Node cloneNode(boolean deep) {
      try {
         return (Node)this.clone();
      } catch (CloneNotSupportedException var3) {
         return null;
      }
   }

   public void normalize() {
   }

   public boolean isSupported(String feature, String version) {
      return false;
   }

   public String getNamespaceURI() {
      return this.uri;
   }

   public void setNamespaceURI(String namespaceURI) {
      this.uri = namespaceURI;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setPrefix(String prefix) throws DOMException {
      this.prefix = prefix;
   }

   public String getLocalName() {
      return this.localName;
   }

   public void setLocalName(String localName) {
      this.localName = localName;
   }

   public boolean hasAttributes() {
      return false;
   }

   public String toString() {
      if (this.value == null) {
         this.value = "";
      }

      StringBuffer b = new StringBuffer();
      if (this.uri != null) {
         b.append("['" + this.uri + "']");
      }

      if (this.prefix != null) {
         b.append(this.prefix + ":");
      }

      b.append(this.localName + "='" + this.value + "' ");
      return b.toString();
   }

   public boolean isId() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public TypeInfo getSchemaTypeInfo() throws DOMException {
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
