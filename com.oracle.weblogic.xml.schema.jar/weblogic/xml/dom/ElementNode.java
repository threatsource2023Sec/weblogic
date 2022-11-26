package weblogic.xml.dom;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import weblogic.utils.XXEUtils;

public class ElementNode extends NodeImpl implements Element {
   private String tagName;
   private String namespaceURI;
   private String localName;
   private String prefix;
   private AttributeMap attributes;
   public static final String XMLNS = "xmlns";
   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";

   public ElementNode() {
      this.setNodeType((short)1);
   }

   public ElementNode(int numAttributes) {
      this();
      this.attributes = new AttributeMap(numAttributes);
   }

   public ElementNode(String namespaceURI, String localName, String prefix) {
      this();
      this.namespaceURI = namespaceURI;
      this.localName = localName;
      this.prefix = prefix;
   }

   public void setAttributes(AttributeMap map) {
      this.attributes = map;
   }

   public String getNamespaceURI() {
      return this.namespaceURI;
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

   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getNodeName() {
      return this.prefix != null ? this.prefix + ":" + this.localName : this.localName;
   }

   public AttributeMap getAttributeMap() {
      return this.attributes;
   }

   public NamedNodeMap getAttributes() {
      return (NamedNodeMap)(this.attributes == null ? Util.NAMED_NODE_MAP : this.attributes);
   }

   public String getAttribute(String name) {
      String attribute = "";
      if (this.attributes != null) {
         String value = this.attributes.getValue(name);
         if (value != null) {
            attribute = value;
         }
      }

      return attribute;
   }

   public Attr getAttributeNode(String name) {
      if (this.attributes == null) {
         return null;
      } else {
         int index = this.attributes.getAttributeIndex(name);
         return index == -1 ? null : this.attributes.getAttribute(index);
      }
   }

   public String getNamespaceURI(String prefix) {
      if (this.attributes != null) {
         int index = this.attributes.getAttributeIndex("http://www.w3.org/2000/xmlns/", prefix);
         if (index != -1) {
            return this.attributes.getValue(index);
         }
      }

      Node n = this.getParentNode();
      if (n == null) {
         return null;
      } else if (n.getNodeType() != 1) {
         return null;
      } else {
         ElementNode p = (ElementNode)n;
         return p.getNamespaceURI(prefix);
      }
   }

   public String getPrefix(String namespaceURI) {
      if (this.attributes != null) {
         for(int i = 0; i < this.attributes.length(); ++i) {
            if (namespaceURI.equals(this.attributes.getValue(i)) && "http://www.w3.org/2000/xmlns/".equals(this.attributes.getNamespaceURI(i))) {
               return this.attributes.getPrefix(i);
            }
         }
      }

      Node n = this.getParentNode();
      if (n == null) {
         return null;
      } else if (n.getNodeType() != 1) {
         return null;
      } else {
         ElementNode p = (ElementNode)n;
         return p.getPrefix(namespaceURI);
      }
   }

   public String getDefaultNamespaceURI() {
      if (this.attributes != null) {
         int index = this.attributes.getAttributeIndex("xmlns");
         if (index != -1) {
            return this.attributes.getValue(index);
         }
      }

      Node n = this.getParentNode();
      if (n == null) {
         return null;
      } else if (n.getNodeType() != 1) {
         return null;
      } else {
         ElementNode p = (ElementNode)n;
         return p.getDefaultNamespaceURI();
      }
   }

   public int setNamespaceURI(String prefix, String uri) {
      int index = this.attributes.getAttributeIndex("http://www.w3.org/2000/xmlns/", prefix);
      if (index == -1) {
         return this.attributes.addAttribute("http://www.w3.org/2000/xmlns/", prefix, "xmlns", uri);
      } else {
         this.attributes.setAttribute(index, "http://www.w3.org/2000/xmlns/", prefix, "xmlns", uri);
         return index;
      }
   }

   public Attr setNamespaceURI(Attr newAttr) {
      int index = this.setNamespaceURI(newAttr.getLocalName(), newAttr.getValue());
      return this.attributes.getAttribute(index);
   }

   public Attr getAttributeNodeNS(String namespaceURI, String localName) {
      if (this.attributes == null) {
         return null;
      } else {
         int index = this.attributes.getAttributeIndex(namespaceURI, localName);
         return index == -1 ? null : this.attributes.getAttribute(index);
      }
   }

   public String getAttributeNS(String namespaceURI, String localName) {
      String attribute = "";
      if (this.attributes != null) {
         String value = this.attributes.getValue(namespaceURI, localName);
         if (value != null) {
            attribute = value;
         }
      }

      return attribute;
   }

   public String getTagName() {
      return this.localName;
   }

   public boolean hasAttribute(String name) {
      if (this.attributes == null) {
         return false;
      } else {
         return this.attributes.getAttributeIndex(name) != -1;
      }
   }

   public boolean hasAttributeNS(String namespaceURI, String localName) {
      if (this.attributes == null) {
         return false;
      } else {
         return this.attributes.getAttributeIndex(namespaceURI, localName) != -1;
      }
   }

   public void removeAttribute(String name) {
      if (this.attributes != null) {
         this.attributes.removeAttribute(this.attributes.getAttributeIndex(name));
      }
   }

   public Attr removeAttributeNode(Attr oldAttr) {
      if (this.attributes == null) {
         throw new DOMException((short)8, "The attribute provided is not a child of this Element");
      } else {
         Attr retVal = (Attr)oldAttr.cloneNode(false);
         AttributeReference r = (AttributeReference)oldAttr;
         if (r.getOwnerElement() != this) {
            throw new DOMException((short)8, "The attribute provided is not a child of this Element");
         } else {
            int index = r.getIndex();
            this.attributes.removeAttribute(index);
            return retVal;
         }
      }
   }

   public void removeAttributeNS(String namespaceURI, String localName) {
      if (this.attributes != null) {
         int index = this.attributes.getAttributeIndex(namespaceURI, localName);
         if (index != -1) {
            this.attributes.removeAttribute(index);
         }
      }
   }

   public void setAttribute(String name, String value) {
      if (this.attributes == null) {
         this.attributes = new AttributeMap();
      }

      int index = this.attributes.getAttributeIndex(this.localName);
      if (index == -1) {
         this.attributes.addAttribute((String)null, name, (String)null, value);
      } else {
         this.attributes.setValue(index, value);
      }

   }

   public Attr setAttributeNode(Attr newAttr) {
      if (this.attributes == null) {
         this.attributes = new AttributeMap();
      }

      if ("xmlns".equals(newAttr.getPrefix())) {
         this.setNamespaceURI(newAttr);
      }

      int index = this.attributes.getAttributeIndex(newAttr.getNamespaceURI(), newAttr.getLocalName());
      if (index == -1) {
         int a = this.attributes.addAttribute(newAttr.getNamespaceURI(), newAttr.getLocalName(), newAttr.getPrefix(), newAttr.getValue());
         return this.attributes.getAttribute(a);
      } else {
         this.attributes.setAttribute(index, newAttr.getNamespaceURI(), newAttr.getLocalName(), newAttr.getPrefix(), newAttr.getValue());
         return this.attributes.getAttribute(index);
      }
   }

   public Attr setAttributeNodeNS(Attr newAttr) {
      return this.setAttributeNode(newAttr);
   }

   public boolean hasAttributes() {
      if (this.attributes == null) {
         return false;
      } else {
         return this.attributes.getLength() > 0;
      }
   }

   public void setAttributeNS(String namespaceURI, String prefix, String localName, String value) {
      if (this.attributes == null) {
         this.attributes = new AttributeMap();
      }

      if ("xmlns".equals(prefix)) {
         this.setNamespaceURI(localName, value);
      } else {
         int index = this.attributes.getAttributeIndex(namespaceURI, localName);
         if (index == -1) {
            this.attributes.addAttribute(namespaceURI, localName, prefix, value);
         } else {
            this.attributes.setPrefix(index, prefix);
            this.attributes.setValue(index, value);
         }

      }
   }

   public void setAttributeNS(String namespaceURI, String qualifiedName, String value) {
      String prefix = Util.getPrefix(qualifiedName);
      String localName = Util.getLocalName(qualifiedName);
      this.setAttributeNS(namespaceURI, prefix, localName, value);
   }

   public void printName(StringBuffer b) {
      String ns = this.getNamespaceURI();
      String p = this.getPrefix();
      if (ns != null && !"".equals(ns)) {
         b.append("['" + ns + "']:");
      }

      if (this.prefix != null) {
         b.append(p + ":");
      }

      b.append(this.getLocalName());
   }

   public void print(StringBuffer b, int tab) {
      b.append("<");
      this.printName(b);
      if (this.attributes != null) {
         b.append(this.attributes.toString());
      }

      if (!this.hasChildNodes()) {
         b.append("/>");
      } else {
         b.append(">");
         NodeList children = this.getChildNodes();

         for(int i = 0; i < children.getLength(); ++i) {
            NodeImpl child = (NodeImpl)children.item(i);
            child.print(b, tab + 1);
         }

         b.append("</");
         this.printName(b);
         b.append(">");
      }
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      this.print(b, 0);
      return b.toString();
   }

   public ElementNode read(XMLStreamReader in) throws IOException {
      try {
         return Builder.read(this, in);
      } catch (Exception var3) {
         throw new IOException(var3.getMessage());
      }
   }

   public ElementNode read(InputStream in) throws IOException {
      try {
         return this.read(XXEUtils.createXMLInputFactoryInstance().createXMLStreamReader(in));
      } catch (Exception var3) {
         throw new IOException(var3.getMessage());
      }
   }

   public static void main(String[] args) throws Exception {
      ElementNode node = new ElementNode();
      node.setLocalName("test_node");
      node.setAttribute("a", "apple");
      node.setAttribute("b", "banana");
      node.setAttribute("c", "cherry");
      System.out.println("Node at Start=" + node);
      node.getAttributeNode("a").setValue("apples");
      node.getAttributeNode("b").setValue("bananas");
      node.getAttributeNode("c").setValue("cherrys");
      System.out.println("Node after mod=" + node);
      node.removeAttribute("a");
      System.out.println("Node after removal=" + node);
      System.out.println("isnull=" + node.getAttributeNode("a"));
      Attr ref = node.setAttributeNode(new AttributeImpl(node, (String)null, (String)null, "d", "donut"));
      System.out.println("Node after mod=" + node);
      ref.setValue("donuts");
      ref.setPrefix("d");
      System.out.println("Node after mod=" + node);
      NamedNodeMap m = node.getAttributes();
      Attr a = new AttributeImpl(node, (String)null, (String)null, "e", "elephant");
      Attr a = (Attr)m.setNamedItem(a);
      System.out.println("Node after att map mod=" + node);
      a.setValue("elephants");
      System.out.println("Node after att map mod=" + node);
      ElementNode n1 = new ElementNode();
      n1.setLocalName("n1");
      n1.setAttributeNS((String)null, "xmlns:a", "http://a");
      n1.setAttributeNS((String)null, "xmlns:b", "http://b");
      n1.setAttributeNS((String)null, "xmlns", "http://default");
      n1.setAttributeNS("http://b", "b:banana", "food");
      n1.setAttributeNS("http://a", "a:apple", "fruit");
      ElementNode n2 = new ElementNode();
      n2.setLocalName("n2");
      n1.appendChild(n2);
      System.out.println(n1);
      System.out.println("parent:" + n2.getNamespaceURI("b"));
      System.out.println("default:" + n2.getDefaultNamespaceURI());
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
}
