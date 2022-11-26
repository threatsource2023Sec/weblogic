package weblogic.xml.xpath.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public final class NamespaceNode implements Node {
   static final String XMLNS = "xmlns";
   private static final NodeList EMPTY_NODELIST = new NodeList() {
      public int getLength() {
         return 0;
      }

      public Node item(int index) {
         return null;
      }
   };
   public static final short TYPE = -4343;
   private Node mParent;
   private String mName;
   private String mValue;

   public NamespaceNode(Node parent, String name, String value) {
      this.mParent = parent;
      this.mName = name;
      this.mValue = value;
   }

   public String getNodeName() {
      return this.mName;
   }

   public String getNodeValue() {
      return this.mValue;
   }

   public short getNodeType() {
      return -4343;
   }

   public Node getParentNode() {
      return this.mParent;
   }

   public String getLocalName() {
      return this.mName;
   }

   public Document getOwnerDocument() {
      return this.mParent.getOwnerDocument();
   }

   public Node cloneNode(boolean deep) {
      return new NamespaceNode(this.mParent, this.mName, this.mValue);
   }

   public boolean hasAttributes() {
      return false;
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

   public boolean hasChildNodes() {
      return false;
   }

   public void normalize() {
   }

   public boolean isSupported(String f, String v) {
      return false;
   }

   public NodeList getChildNodes() {
      return EMPTY_NODELIST;
   }

   public String getNamespaceURI() {
      return null;
   }

   public String getPrefix() {
      return null;
   }

   public void setPrefix(String noway) throws DOMException {
      throw this.disallowed();
   }

   public void setNodeValue(String noway) throws DOMException {
      throw this.disallowed();
   }

   public Node replaceChild(Node nope, Node forgetit) throws DOMException {
      throw this.disallowed();
   }

   public Node insertBefore(Node nope, Node forgetit) throws DOMException {
      throw this.disallowed();
   }

   public Node appendChild(Node nope) throws DOMException {
      throw this.disallowed();
   }

   public Node removeChild(Node nope) throws DOMException {
      throw this.disallowed();
   }

   private DOMException disallowed() {
      return new DOMException((short)7, "weblogic.xml.xpath.adapters.dom.NamespaceNode cannot be modified");
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

   public String lookupPrefix(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public short compareDocumentPosition(Node node) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setIdAttributeNode(Attr attr, boolean flag) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setIdAttributeNS(String s, String s1, boolean flag) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setIdAttribute(String s, boolean flag) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public TypeInfo getSchemaTypeInfo() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean isEqualNode(Node node) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean isSameNode(Node node) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String lookupNamespaceURI(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean isDefaultNamespace(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public void setTextContent(String s) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getTextContent() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getBaseURI() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
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
}
