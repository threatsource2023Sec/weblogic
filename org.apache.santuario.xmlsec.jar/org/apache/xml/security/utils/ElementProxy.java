package org.apache.xml.security.utils;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public abstract class ElementProxy {
   protected static final Logger LOG = LoggerFactory.getLogger(ElementProxy.class);
   private Element wrappedElement;
   protected String baseURI;
   private Document wrappedDoc;
   private static Map prefixMappings = new ConcurrentHashMap();

   public ElementProxy() {
   }

   public ElementProxy(Document doc) {
      if (doc == null) {
         throw new RuntimeException("Document is null");
      } else {
         this.wrappedDoc = doc;
         this.wrappedElement = this.createElementForFamilyLocal(this.getBaseNamespace(), this.getBaseLocalName());
      }
   }

   public ElementProxy(Element element, String baseURI) throws XMLSecurityException {
      if (element == null) {
         throw new XMLSecurityException("ElementProxy.nullElement");
      } else {
         LOG.debug("setElement(\"{}\", \"{}\")", element.getTagName(), baseURI);
         this.setElement(element);
         this.baseURI = baseURI;
         this.guaranteeThatElementInCorrectSpace();
      }
   }

   public abstract String getBaseNamespace();

   public abstract String getBaseLocalName();

   protected Element createElementForFamilyLocal(String namespace, String localName) {
      Document doc = this.getDocument();
      Element result = null;
      if (namespace == null) {
         result = doc.createElementNS((String)null, localName);
      } else {
         String baseName = this.getBaseNamespace();
         String prefix = getDefaultPrefix(baseName);
         if (prefix != null && prefix.length() != 0) {
            result = doc.createElementNS(namespace, prefix + ":" + localName);
            result.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespace);
         } else {
            result = doc.createElementNS(namespace, localName);
            result.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespace);
         }
      }

      return result;
   }

   public static Element createElementForFamily(Document doc, String namespace, String localName) {
      Element result = null;
      String prefix = getDefaultPrefix(namespace);
      if (namespace == null) {
         result = doc.createElementNS((String)null, localName);
      } else if (prefix != null && prefix.length() != 0) {
         result = doc.createElementNS(namespace, prefix + ":" + localName);
         result.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespace);
      } else {
         result = doc.createElementNS(namespace, localName);
         result.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespace);
      }

      return result;
   }

   public void setElement(Element element, String baseURI) throws XMLSecurityException {
      if (element == null) {
         throw new XMLSecurityException("ElementProxy.nullElement");
      } else {
         LOG.debug("setElement({}, \"{}\")", element.getTagName(), baseURI);
         this.setElement(element);
         this.baseURI = baseURI;
      }
   }

   public final Element getElement() {
      return this.wrappedElement;
   }

   public final NodeList getElementPlusReturns() {
      HelperNodeList nl = new HelperNodeList();
      nl.appendChild(this.createText("\n"));
      nl.appendChild(this.getElement());
      nl.appendChild(this.createText("\n"));
      return nl;
   }

   protected Text createText(String text) {
      return this.wrappedDoc.createTextNode(text);
   }

   public Document getDocument() {
      if (this.wrappedDoc == null) {
         this.wrappedDoc = XMLUtils.getOwnerDocument((Node)this.wrappedElement);
      }

      return this.wrappedDoc;
   }

   public String getBaseURI() {
      return this.baseURI;
   }

   void guaranteeThatElementInCorrectSpace() throws XMLSecurityException {
      String expectedLocalName = this.getBaseLocalName();
      String expectedNamespaceUri = this.getBaseNamespace();
      String actualLocalName = this.getElement().getLocalName();
      String actualNamespaceUri = this.getElement().getNamespaceURI();
      if (!expectedNamespaceUri.equals(actualNamespaceUri) && !expectedLocalName.equals(actualLocalName)) {
         Object[] exArgs = new Object[]{actualNamespaceUri + ":" + actualLocalName, expectedNamespaceUri + ":" + expectedLocalName};
         throw new XMLSecurityException("xml.WrongElement", exArgs);
      }
   }

   public void addBigIntegerElement(BigInteger bi, String localname) {
      if (bi != null) {
         Element e = XMLUtils.createElementInSignatureSpace(this.getDocument(), localname);
         byte[] bytes = XMLUtils.getBytes(bi, bi.bitLength());
         String encodedInt = XMLUtils.encodeToString(bytes);
         Document doc = e.getOwnerDocument();
         Text text = doc.createTextNode(encodedInt);
         e.appendChild(text);
         this.appendSelf((Node)e);
         this.addReturnToSelf();
      }

   }

   protected void addReturnToSelf() {
      XMLUtils.addReturnToElement(this.getElement());
   }

   public void addBase64Element(byte[] bytes, String localname) {
      if (bytes != null) {
         Element el = XMLUtils.createElementInSignatureSpace(this.getDocument(), localname);
         Text text = this.getDocument().createTextNode(XMLUtils.encodeToString(bytes));
         el.appendChild(text);
         this.appendSelf((Node)el);
         if (!XMLUtils.ignoreLineBreaks()) {
            this.appendSelf((Node)this.createText("\n"));
         }
      }

   }

   public void addTextElement(String text, String localname) {
      Element e = XMLUtils.createElementInSignatureSpace(this.getDocument(), localname);
      Text t = this.createText(text);
      this.appendOther(e, t);
      this.appendSelf((Node)e);
      this.addReturnToSelf();
   }

   public void addBase64Text(byte[] bytes) {
      if (bytes != null) {
         Text t = XMLUtils.ignoreLineBreaks() ? this.createText(XMLUtils.encodeToString(bytes)) : this.createText("\n" + XMLUtils.encodeToString(bytes) + "\n");
         this.appendSelf((Node)t);
      }

   }

   protected void appendSelf(ElementProxy toAppend) {
      this.getElement().appendChild(toAppend.getElement());
   }

   protected void appendSelf(Node toAppend) {
      this.getElement().appendChild(toAppend);
   }

   protected void appendOther(Element parent, Node toAppend) {
      parent.appendChild(toAppend);
   }

   public void addText(String text) {
      if (text != null) {
         Text t = this.createText(text);
         this.appendSelf((Node)t);
      }

   }

   public BigInteger getBigIntegerFromChildElement(String localname, String namespace) {
      Node n = XMLUtils.selectNode(this.getFirstChild(), namespace, localname, 0);
      return n != null ? new BigInteger(1, XMLUtils.decode(XMLUtils.getFullTextChildrenFromNode(n))) : null;
   }

   public String getTextFromChildElement(String localname, String namespace) {
      return XMLUtils.selectNode(this.getFirstChild(), namespace, localname, 0).getTextContent();
   }

   public byte[] getBytesFromTextChild() throws XMLSecurityException {
      return XMLUtils.decode(this.getTextFromTextChild());
   }

   public String getTextFromTextChild() {
      return XMLUtils.getFullTextChildrenFromNode(this.getElement());
   }

   public int length(String namespace, String localname) {
      int number = 0;

      for(Node sibling = this.getFirstChild(); sibling != null; sibling = sibling.getNextSibling()) {
         if (localname.equals(sibling.getLocalName()) && namespace.equals(sibling.getNamespaceURI())) {
            ++number;
         }
      }

      return number;
   }

   public void setXPathNamespaceContext(String prefix, String uri) throws XMLSecurityException {
      if (prefix != null && prefix.length() != 0) {
         if ("xmlns".equals(prefix)) {
            throw new XMLSecurityException("defaultNamespaceCannotBeSetHere");
         } else {
            String ns;
            if (prefix.startsWith("xmlns:")) {
               ns = prefix;
            } else {
               ns = "xmlns:" + prefix;
            }

            Attr a = this.getElement().getAttributeNodeNS("http://www.w3.org/2000/xmlns/", ns);
            if (a != null) {
               if (!a.getNodeValue().equals(uri)) {
                  Object[] exArgs = new Object[]{ns, this.getElement().getAttributeNS((String)null, ns)};
                  throw new XMLSecurityException("namespacePrefixAlreadyUsedByOtherURI", exArgs);
               }
            } else {
               this.getElement().setAttributeNS("http://www.w3.org/2000/xmlns/", ns, uri);
            }
         }
      } else {
         throw new XMLSecurityException("defaultNamespaceCannotBeSetHere");
      }
   }

   public static void setDefaultPrefix(String namespace, String prefix) throws XMLSecurityException {
      JavaUtils.checkRegisterPermission();
      setNamespacePrefix(namespace, prefix);
   }

   private static void setNamespacePrefix(String namespace, String prefix) throws XMLSecurityException {
      if (prefixMappings.containsValue(prefix)) {
         String storedPrefix = (String)prefixMappings.get(namespace);
         if (!storedPrefix.equals(prefix)) {
            Object[] exArgs = new Object[]{prefix, namespace, storedPrefix};
            throw new XMLSecurityException("prefix.AlreadyAssigned", exArgs);
         }
      }

      if ("http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
         XMLUtils.setDsPrefix(prefix);
      } else if ("http://www.w3.org/2009/xmldsig11#".equals(namespace)) {
         XMLUtils.setDs11Prefix(prefix);
      } else if ("http://www.w3.org/2001/04/xmlenc#".equals(namespace)) {
         XMLUtils.setXencPrefix(prefix);
      }

      prefixMappings.put(namespace, prefix);
   }

   public static void registerDefaultPrefixes() throws XMLSecurityException {
      setNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
      setNamespacePrefix("http://www.w3.org/2001/04/xmlenc#", "xenc");
      setNamespacePrefix("http://www.w3.org/2009/xmlenc11#", "xenc11");
      setNamespacePrefix("http://www.xmlsecurity.org/experimental#", "experimental");
      setNamespacePrefix("http://www.w3.org/2002/04/xmldsig-filter2", "dsig-xpath-old");
      setNamespacePrefix("http://www.w3.org/2002/06/xmldsig-filter2", "dsig-xpath");
      setNamespacePrefix("http://www.w3.org/2001/10/xml-exc-c14n#", "ec");
      setNamespacePrefix("http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter", "xx");
      setNamespacePrefix("http://www.w3.org/2009/xmldsig11#", "dsig11");
   }

   public static String getDefaultPrefix(String namespace) {
      return (String)prefixMappings.get(namespace);
   }

   protected void setElement(Element elem) {
      this.wrappedElement = elem;
   }

   protected void setDocument(Document doc) {
      this.wrappedDoc = doc;
   }

   protected String getLocalAttribute(String attrName) {
      return this.getElement().getAttributeNS((String)null, attrName);
   }

   protected void setLocalAttribute(String attrName, String value) {
      this.getElement().setAttributeNS((String)null, attrName, value);
   }

   protected void setLocalIdAttribute(String attrName, String value) {
      if (value != null) {
         Attr attr = this.getDocument().createAttributeNS((String)null, attrName);
         attr.setValue(value);
         this.getElement().setAttributeNodeNS(attr);
         this.getElement().setIdAttributeNode(attr, true);
      } else {
         this.getElement().removeAttributeNS((String)null, attrName);
      }

   }

   protected Node getFirstChild() {
      return this.getElement().getFirstChild();
   }
}
