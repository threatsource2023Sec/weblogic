package weblogic.xml.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public final class DocumentImpl extends NodeImpl implements Document {
   private String namespaceURI;
   private String localName;
   private ElementNode documentElement;

   public DocumentImpl() {
      this.setNodeType((short)9);
   }

   public DocumentImpl(String namespaceURI, String qualifiedName) {
      this.namespaceURI = namespaceURI;
      this.localName = Util.getLocalName(qualifiedName);
   }

   public String getNodeName() {
      return "#document";
   }

   public Document getOwnerDocument() {
      return this;
   }

   private void check(Node child) {
      if (child.getNodeType() == 1) {
         this.documentElement = (ElementNode)child;
      }

   }

   public Node appendChild(Node newChild) throws DOMException {
      this.check(newChild);
      return super.appendChild(newChild);
   }

   public Node removeChild(Node oldChild) throws DOMException {
      if (oldChild == this.documentElement) {
         this.documentElement = null;
      }

      return super.removeChild(oldChild);
   }

   public Node insertBefore(Node newChild, Node refChild) {
      this.check(newChild);
      return super.insertBefore(newChild, refChild);
   }

   public Node getPreviousSibling() {
      return null;
   }

   public Node getNextSibling() {
      return null;
   }

   public String getNamespaceURI(String namespaceURI) {
      return namespaceURI;
   }

   public void setNamespaceURI(String namespaceURI) {
      this.namespaceURI = namespaceURI;
   }

   public void setLocalName(String localName) {
      this.localName = localName;
   }

   public String getLocalName() {
      return this.localName;
   }

   public DOMImplementation getImplementation() {
      return ImplementationFactory.newImplementation();
   }

   public DocumentType getDoctype() {
      return null;
   }

   public Element getDocumentElement() {
      return this.documentElement;
   }

   public Element createElement(String localName) throws DOMException {
      ElementNode n = new ElementNode();
      n.setLocalName(localName);
      n.setOwnerDocument(this);
      return n;
   }

   public DocumentFragment createDocumentFragment() {
      return new DocumentFragmentNode();
   }

   public Text createTextNode(String text) {
      TextNode t = new TextNode(text);
      this.setOwnerDocument(this);
      return t;
   }

   public Comment createComment(String comment) {
      TextNode t = new TextNode(comment);
      t.setOwnerDocument(this);
      return t.asComment();
   }

   public CDATASection createCDATASection(String data) throws DOMException {
      TextNode t = new TextNode(data);
      t.setOwnerDocument(this);
      return t.asCDATA();
   }

   public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
      PINode p = new PINode(target, data);
      p.setOwnerDocument(this);
      return p;
   }

   public Attr createAttribute(String name) throws DOMException {
      AttributeImpl a = new AttributeImpl();
      a.setLocalName(name);
      return a;
   }

   public EntityReference createEntityReference(String name) throws DOMException {
      throw new UnsupportedOperationException("NYI");
   }

   public Node importNode(Node importedNode, boolean deep) throws DOMException {
      return importedNode;
   }

   public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
      ElementNode element = new ElementNode(namespaceURI, Util.getLocalName(qualifiedName), Util.getPrefix(qualifiedName));
      element.setOwnerDocument(this);
      return element;
   }

   public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
      AttributeImpl a = new AttributeImpl();
      a.setLocalName(Util.getLocalName(qualifiedName));
      a.setNamespaceURI(namespaceURI);
      a.setPrefix(Util.getPrefix(qualifiedName));
      return a;
   }

   public Element getElementById(String elementId) {
      return null;
   }

   public void print(StringBuffer b, int tab) {
      b.append("DOCUMENT[" + this.namespaceURI + "][" + this.localName + "][\n");
      NodeList children = this.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         NodeImpl child = (NodeImpl)children.item(i);
         if (child == null) {
            System.out.println(i + " is null");
         } else {
            if (child == this.documentElement) {
               b.append("ROOT(" + i + ").->[");
            } else {
               b.append("CHILD(" + i + ").->[");
            }

            child.print(b, tab + 1);
            b.append("]\n");
         }
      }

      b.append("]\n");
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      this.print(b, 0);
      return b.toString();
   }

   public static void main(String[] args) throws Exception {
      DOMImplementation i = ImplementationFactory.newImplementation();
      Document d = i.createDocument("http://myuri", "prefix:document", (DocumentType)null);
      Element e = d.createElement("doc");
      d.appendChild(e);
      d.insertBefore(d.createProcessingInstruction("pi1", "data1"), e);
      d.insertBefore(d.createTextNode("\n    \n"), e);
      d.appendChild(d.createProcessingInstruction("pi2", "data2"));
      d.appendChild(d.createTextNode("\n"));
      Element a = d.createElementNS("http://fruit", "a:apple");
      e.appendChild(a);
      Element b = d.createElementNS("http://animal", "b:bear");
      b.appendChild(d.createTextNode("some text about bears"));
      e.appendChild(b);
      e.setAttribute("root", "value");
      System.out.println(d);
      d.removeChild(e);
      System.out.println(d);
      d.appendChild(e);
      System.out.println(d);
   }
}
