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

public final class AttributeReference implements Name, Attr, Cloneable {
   private AttributeMap atts;
   private int index;

   public AttributeReference(AttributeMap m, int index) {
      this.atts = m;
      this.index = index;
   }

   public int getIndex() {
      return this.index;
   }

   public String getName() {
      return this.getNodeName();
   }

   public Element getOwnerElement() {
      return this.atts.getOwner();
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
      String prefix = this.atts.getPrefix(this.index);
      String localName = this.atts.getLocalName(this.index);
      return prefix != null && !"".equals(prefix) ? prefix + ":" + localName : localName;
   }

   public String getNodeValue() throws DOMException {
      return this.atts.getValue(this.index);
   }

   public void setNodeValue(String value) throws DOMException {
      this.atts.setValue(this.index, value);
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
      return new AttributeImpl(this.getOwnerElement(), this.getNamespaceURI(), this.getPrefix(), this.getLocalName(), this.getValue());
   }

   public void normalize() {
   }

   public boolean isSupported(String feature, String version) {
      return false;
   }

   public String getNamespaceURI() {
      return this.atts.getNamespaceURI(this.index);
   }

   public String getPrefix() {
      return this.atts.getPrefix(this.index);
   }

   public void setPrefix(String prefix) throws DOMException {
      this.atts.setPrefix(this.index, prefix);
   }

   public String getLocalName() {
      return this.atts.getLocalName(this.index);
   }

   public boolean hasAttributes() {
      return false;
   }

   public String toString() {
      String prefix = this.getPrefix();
      String localName = this.getLocalName();
      String uri = this.getNamespaceURI();
      String value = this.getValue();
      if (value == null) {
         value = "";
      }

      StringBuffer b = new StringBuffer();
      if (uri != null) {
         b.append("['" + uri + "']");
      }

      if (prefix != null) {
         b.append(prefix + ":");
      }

      b.append(localName + "='" + value + "' ");
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
