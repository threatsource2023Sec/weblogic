package org.apache.xml.security.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class XMLUtils {
   private static boolean ignoreLineBreaks = (Boolean)AccessController.doPrivileged(() -> {
      return Boolean.getBoolean("org.apache.xml.security.ignoreLineBreaks");
   });
   private static final WeakObjectPool[] pools = new WeakObjectPool[4];
   private static volatile String dsPrefix;
   private static volatile String ds11Prefix;
   private static volatile String xencPrefix;
   private static volatile String xenc11Prefix;
   private static final Logger LOG;

   private XMLUtils() {
   }

   public static void setDsPrefix(String prefix) {
      JavaUtils.checkRegisterPermission();
      dsPrefix = prefix;
   }

   public static void setDs11Prefix(String prefix) {
      JavaUtils.checkRegisterPermission();
      ds11Prefix = prefix;
   }

   public static void setXencPrefix(String prefix) {
      JavaUtils.checkRegisterPermission();
      xencPrefix = prefix;
   }

   public static void setXenc11Prefix(String prefix) {
      JavaUtils.checkRegisterPermission();
      xenc11Prefix = prefix;
   }

   public static Element getNextElement(Node el) {
      Node node;
      for(node = el; node != null && node.getNodeType() != 1; node = node.getNextSibling()) {
      }

      return (Element)node;
   }

   public static void getSet(Node rootNode, Set result, Node exclude, boolean com) {
      if (exclude == null || !isDescendantOrSelf(exclude, rootNode)) {
         getSetRec(rootNode, result, exclude, com);
      }
   }

   private static void getSetRec(Node rootNode, Set result, Node exclude, boolean com) {
      if (rootNode != exclude) {
         switch (rootNode.getNodeType()) {
            case 1:
               result.add(rootNode);
               Element el = (Element)rootNode;
               if (el.hasAttributes()) {
                  NamedNodeMap nl = el.getAttributes();
                  int length = nl.getLength();

                  for(int i = 0; i < length; ++i) {
                     result.add(nl.item(i));
                  }
               }
            case 9:
               for(Node r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
                  if (r.getNodeType() == 3) {
                     result.add(r);

                     while(r != null && r.getNodeType() == 3) {
                        r = r.getNextSibling();
                     }

                     if (r == null) {
                        return;
                     }
                  }

                  getSetRec(r, result, exclude, com);
               }

               return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            default:
               result.add(rootNode);
               return;
            case 8:
               if (com) {
                  result.add(rootNode);
               }

               return;
            case 10:
         }
      }
   }

   public static void outputDOM(Node contextNode, OutputStream os) {
      outputDOM(contextNode, os, false);
   }

   public static void outputDOM(Node contextNode, OutputStream os, boolean addPreamble) {
      try {
         if (addPreamble) {
            os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes(StandardCharsets.UTF_8));
         }

         os.write(Canonicalizer.getInstance("http://santuario.apache.org/c14n/physical").canonicalizeSubtree(contextNode));
      } catch (IOException var4) {
         LOG.debug(var4.getMessage(), var4);
      } catch (InvalidCanonicalizerException var5) {
         LOG.debug(var5.getMessage(), var5);
      } catch (CanonicalizationException var6) {
         LOG.debug(var6.getMessage(), var6);
      }

   }

   public static void outputDOMc14nWithComments(Node contextNode, OutputStream os) {
      try {
         os.write(Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments").canonicalizeSubtree(contextNode));
      } catch (IOException var3) {
         LOG.debug(var3.getMessage(), var3);
      } catch (InvalidCanonicalizerException var4) {
         LOG.debug(var4.getMessage(), var4);
      } catch (CanonicalizationException var5) {
         LOG.debug(var5.getMessage(), var5);
      }

   }

   /** @deprecated */
   @Deprecated
   public static String getFullTextChildrenFromElement(Element element) {
      return getFullTextChildrenFromNode(element);
   }

   public static String getFullTextChildrenFromNode(Node node) {
      StringBuilder sb = new StringBuilder();

      for(Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
         if (child.getNodeType() == 3) {
            sb.append(((Text)child).getData());
         }
      }

      return sb.toString();
   }

   public static Element createElementInSignatureSpace(Document doc, String elementName) {
      if (doc == null) {
         throw new RuntimeException("Document is null");
      } else {
         return dsPrefix != null && dsPrefix.length() != 0 ? doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", dsPrefix + ":" + elementName) : doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", elementName);
      }
   }

   public static Element createElementInSignature11Space(Document doc, String elementName) {
      if (doc == null) {
         throw new RuntimeException("Document is null");
      } else {
         return ds11Prefix != null && ds11Prefix.length() != 0 ? doc.createElementNS("http://www.w3.org/2009/xmldsig11#", ds11Prefix + ":" + elementName) : doc.createElementNS("http://www.w3.org/2009/xmldsig11#", elementName);
      }
   }

   public static Element createElementInEncryptionSpace(Document doc, String elementName) {
      if (doc == null) {
         throw new RuntimeException("Document is null");
      } else {
         return xencPrefix != null && xencPrefix.length() != 0 ? doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", xencPrefix + ":" + elementName) : doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", elementName);
      }
   }

   public static Element createElementInEncryption11Space(Document doc, String elementName) {
      if (doc == null) {
         throw new RuntimeException("Document is null");
      } else {
         return xenc11Prefix != null && xenc11Prefix.length() != 0 ? doc.createElementNS("http://www.w3.org/2009/xmlenc11#", xenc11Prefix + ":" + elementName) : doc.createElementNS("http://www.w3.org/2009/xmlenc11#", elementName);
      }
   }

   public static boolean elementIsInSignatureSpace(Element element, String localName) {
      if (element == null) {
         return false;
      } else {
         return "http://www.w3.org/2000/09/xmldsig#".equals(element.getNamespaceURI()) && element.getLocalName().equals(localName);
      }
   }

   public static boolean elementIsInSignature11Space(Element element, String localName) {
      if (element == null) {
         return false;
      } else {
         return "http://www.w3.org/2009/xmldsig11#".equals(element.getNamespaceURI()) && element.getLocalName().equals(localName);
      }
   }

   public static boolean elementIsInEncryptionSpace(Element element, String localName) {
      if (element == null) {
         return false;
      } else {
         return "http://www.w3.org/2001/04/xmlenc#".equals(element.getNamespaceURI()) && element.getLocalName().equals(localName);
      }
   }

   public static boolean elementIsInEncryption11Space(Element element, String localName) {
      if (element == null) {
         return false;
      } else {
         return "http://www.w3.org/2009/xmlenc11#".equals(element.getNamespaceURI()) && element.getLocalName().equals(localName);
      }
   }

   public static Document getOwnerDocument(Node node) {
      if (node.getNodeType() == 9) {
         return (Document)node;
      } else {
         try {
            return node.getOwnerDocument();
         } catch (NullPointerException var2) {
            throw new NullPointerException(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + var2.getMessage() + "\"");
         }
      }
   }

   public static Document getOwnerDocument(Set xpathNodeSet) {
      NullPointerException npe = null;
      Iterator var2 = xpathNodeSet.iterator();

      while(var2.hasNext()) {
         Node node = (Node)var2.next();
         int nodeType = node.getNodeType();
         if (nodeType == 9) {
            return (Document)node;
         }

         try {
            if (nodeType == 2) {
               return ((Attr)node).getOwnerElement().getOwnerDocument();
            }

            return node.getOwnerDocument();
         } catch (NullPointerException var6) {
            npe = var6;
         }
      }

      throw new NullPointerException(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + (npe == null ? "" : npe.getMessage()) + "\"");
   }

   public static Element createDSctx(Document doc, String prefix, String namespace) {
      if (prefix != null && prefix.trim().length() != 0) {
         Element ctx = doc.createElementNS((String)null, "namespaceContext");
         ctx.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix.trim(), namespace);
         return ctx;
      } else {
         throw new IllegalArgumentException("You must supply a prefix");
      }
   }

   public static void addReturnToElement(Element e) {
      if (!ignoreLineBreaks) {
         Document doc = e.getOwnerDocument();
         e.appendChild(doc.createTextNode("\n"));
      }

   }

   public static void addReturnToElement(Document doc, HelperNodeList nl) {
      if (!ignoreLineBreaks) {
         nl.appendChild(doc.createTextNode("\n"));
      }

   }

   public static void addReturnBeforeChild(Element e, Node child) {
      if (!ignoreLineBreaks) {
         Document doc = e.getOwnerDocument();
         e.insertBefore(doc.createTextNode("\n"), child);
      }

   }

   public static String encodeToString(byte[] bytes) {
      return ignoreLineBreaks ? java.util.Base64.getEncoder().encodeToString(bytes) : java.util.Base64.getMimeEncoder().encodeToString(bytes);
   }

   public static byte[] decode(String encodedString) {
      return java.util.Base64.getMimeDecoder().decode(encodedString);
   }

   public static byte[] decode(byte[] encodedBytes) {
      return java.util.Base64.getMimeDecoder().decode(encodedBytes);
   }

   public static boolean isIgnoreLineBreaks() {
      return ignoreLineBreaks;
   }

   public static Set convertNodelistToSet(NodeList xpathNodeSet) {
      if (xpathNodeSet == null) {
         return new HashSet();
      } else {
         int length = xpathNodeSet.getLength();
         Set set = new HashSet(length);

         for(int i = 0; i < length; ++i) {
            set.add(xpathNodeSet.item(i));
         }

         return set;
      }
   }

   public static void circumventBug2650(Document doc) {
      Element documentElement = doc.getDocumentElement();
      Attr xmlnsAttr = documentElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
      if (xmlnsAttr == null) {
         documentElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "");
      }

      circumventBug2650internal(doc);
   }

   private static void circumventBug2650internal(Node node) {
      Node parent = null;
      Node sibling = null;
      String namespaceNs = "http://www.w3.org/2000/xmlns/";

      while(true) {
         switch (node.getNodeType()) {
            case 1:
               Element element = (Element)node;
               if (!element.hasChildNodes()) {
                  break;
               }

               if (element.hasAttributes()) {
                  NamedNodeMap attributes = element.getAttributes();
                  int attributesLength = attributes.getLength();

                  for(Node child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
                     if (child.getNodeType() == 1) {
                        Element childElement = (Element)child;

                        for(int i = 0; i < attributesLength; ++i) {
                           Attr currentAttr = (Attr)attributes.item(i);
                           if ("http://www.w3.org/2000/xmlns/".equals(currentAttr.getNamespaceURI()) && !childElement.hasAttributeNS("http://www.w3.org/2000/xmlns/", currentAttr.getLocalName())) {
                              childElement.setAttributeNS("http://www.w3.org/2000/xmlns/", currentAttr.getName(), currentAttr.getNodeValue());
                           }
                        }
                     }
                  }
               }
            case 5:
            case 9:
               parent = node;
               sibling = node.getFirstChild();
         }

         while(sibling == null && parent != null) {
            sibling = parent.getNextSibling();
            parent = parent.getParentNode();
         }

         if (sibling == null) {
            return;
         }

         node = sibling;
         sibling = sibling.getNextSibling();
      }
   }

   public static Element selectDsNode(Node sibling, String nodeName, int number) {
      for(; sibling != null; sibling = sibling.getNextSibling()) {
         if ("http://www.w3.org/2000/09/xmldsig#".equals(sibling.getNamespaceURI()) && sibling.getLocalName().equals(nodeName)) {
            if (number == 0) {
               return (Element)sibling;
            }

            --number;
         }
      }

      return null;
   }

   public static Element selectDs11Node(Node sibling, String nodeName, int number) {
      for(; sibling != null; sibling = sibling.getNextSibling()) {
         if ("http://www.w3.org/2009/xmldsig11#".equals(sibling.getNamespaceURI()) && sibling.getLocalName().equals(nodeName)) {
            if (number == 0) {
               return (Element)sibling;
            }

            --number;
         }
      }

      return null;
   }

   public static Element selectXencNode(Node sibling, String nodeName, int number) {
      for(; sibling != null; sibling = sibling.getNextSibling()) {
         if ("http://www.w3.org/2001/04/xmlenc#".equals(sibling.getNamespaceURI()) && sibling.getLocalName().equals(nodeName)) {
            if (number == 0) {
               return (Element)sibling;
            }

            --number;
         }
      }

      return null;
   }

   public static Text selectDsNodeText(Node sibling, String nodeName, int number) {
      Node n = selectDsNode(sibling, nodeName, number);
      if (n == null) {
         return null;
      } else {
         Node n;
         for(n = n.getFirstChild(); n != null && n.getNodeType() != 3; n = n.getNextSibling()) {
         }

         return (Text)n;
      }
   }

   public static Text selectDs11NodeText(Node sibling, String nodeName, int number) {
      Node n = selectDs11Node(sibling, nodeName, number);
      if (n == null) {
         return null;
      } else {
         Node n;
         for(n = n.getFirstChild(); n != null && n.getNodeType() != 3; n = n.getNextSibling()) {
         }

         return (Text)n;
      }
   }

   public static Text selectNodeText(Node sibling, String uri, String nodeName, int number) {
      Node n = selectNode(sibling, uri, nodeName, number);
      if (n == null) {
         return null;
      } else {
         Node n;
         for(n = n.getFirstChild(); n != null && n.getNodeType() != 3; n = n.getNextSibling()) {
         }

         return (Text)n;
      }
   }

   public static Element selectNode(Node sibling, String uri, String nodeName, int number) {
      for(; sibling != null; sibling = sibling.getNextSibling()) {
         if (sibling.getNamespaceURI() != null && sibling.getNamespaceURI().equals(uri) && sibling.getLocalName().equals(nodeName)) {
            if (number == 0) {
               return (Element)sibling;
            }

            --number;
         }
      }

      return null;
   }

   public static Element[] selectDsNodes(Node sibling, String nodeName) {
      return selectNodes(sibling, "http://www.w3.org/2000/09/xmldsig#", nodeName);
   }

   public static Element[] selectDs11Nodes(Node sibling, String nodeName) {
      return selectNodes(sibling, "http://www.w3.org/2009/xmldsig11#", nodeName);
   }

   public static Element[] selectNodes(Node sibling, String uri, String nodeName) {
      ArrayList list;
      for(list = new ArrayList(); sibling != null; sibling = sibling.getNextSibling()) {
         if (sibling.getNamespaceURI() != null && sibling.getNamespaceURI().equals(uri) && sibling.getLocalName().equals(nodeName)) {
            list.add((Element)sibling);
         }
      }

      return (Element[])list.toArray(new Element[list.size()]);
   }

   public static Set excludeNodeFromSet(Node signatureElement, Set inputSet) {
      Set resultSet = new HashSet();
      Iterator iterator = inputSet.iterator();

      while(iterator.hasNext()) {
         Node inputNode = (Node)iterator.next();
         if (!isDescendantOrSelf(signatureElement, inputNode)) {
            resultSet.add(inputNode);
         }
      }

      return resultSet;
   }

   public static String getStrFromNode(Node xpathnode) {
      if (xpathnode.getNodeType() == 3) {
         StringBuilder sb = new StringBuilder();

         for(Node currentSibling = xpathnode.getParentNode().getFirstChild(); currentSibling != null; currentSibling = currentSibling.getNextSibling()) {
            if (currentSibling.getNodeType() == 3) {
               sb.append(((Text)currentSibling).getData());
            }
         }

         return sb.toString();
      } else if (xpathnode.getNodeType() == 2) {
         return xpathnode.getNodeValue();
      } else {
         return xpathnode.getNodeType() == 7 ? xpathnode.getNodeValue() : null;
      }
   }

   public static boolean isDescendantOrSelf(Node ctx, Node descendantOrSelf) {
      if (ctx == descendantOrSelf) {
         return true;
      } else {
         Node parent = descendantOrSelf;

         while(parent != null) {
            if (parent == ctx) {
               return true;
            }

            if (((Node)parent).getNodeType() == 2) {
               parent = ((Attr)parent).getOwnerElement();
            } else {
               parent = ((Node)parent).getParentNode();
            }
         }

         return false;
      }
   }

   public static boolean ignoreLineBreaks() {
      return ignoreLineBreaks;
   }

   public static String getAttributeValue(Element elem, String name) {
      Attr attr = elem.getAttributeNodeNS((String)null, name);
      return attr == null ? null : attr.getValue();
   }

   public static boolean protectAgainstWrappingAttack(Node startNode, String value) {
      String id = value.trim();
      if (!id.isEmpty() && id.charAt(0) == '#') {
         id = id.substring(1);
      }

      Node startParent = null;
      Node processedNode = null;
      Element foundElement = null;
      if (startNode != null) {
         startParent = startNode.getParentNode();
      }

      while(startNode != null) {
         if (startNode.getNodeType() == 1) {
            Element se = (Element)startNode;
            NamedNodeMap attributes = se.getAttributes();
            if (attributes != null) {
               int length = attributes.getLength();

               for(int i = 0; i < length; ++i) {
                  Attr attr = (Attr)attributes.item(i);
                  if (attr.isId() && id.equals(attr.getValue())) {
                     if (foundElement != null) {
                        LOG.debug("Multiple elements with the same 'Id' attribute value!");
                        return false;
                     }

                     foundElement = attr.getOwnerElement();
                  }
               }
            }
         }

         processedNode = startNode;
         startNode = startNode.getFirstChild();
         if (startNode == null) {
            startNode = processedNode.getNextSibling();
         }

         while(startNode == null) {
            processedNode = processedNode.getParentNode();
            if (processedNode == startParent) {
               return true;
            }

            startNode = processedNode.getNextSibling();
         }
      }

      return true;
   }

   public static boolean protectAgainstWrappingAttack(Node startNode, Element knownElement, String value) {
      String id = value.trim();
      if (!id.isEmpty() && id.charAt(0) == '#') {
         id = id.substring(1);
      }

      Node startParent = null;
      Node processedNode = null;
      if (startNode != null) {
         startParent = startNode.getParentNode();
      }

      while(startNode != null) {
         if (startNode.getNodeType() == 1) {
            Element se = (Element)startNode;
            NamedNodeMap attributes = se.getAttributes();
            if (attributes != null) {
               int length = attributes.getLength();

               for(int i = 0; i < length; ++i) {
                  Attr attr = (Attr)attributes.item(i);
                  if (attr.isId() && id.equals(attr.getValue()) && se != knownElement) {
                     LOG.debug("Multiple elements with the same 'Id' attribute value!");
                     return false;
                  }
               }
            }
         }

         processedNode = startNode;
         startNode = startNode.getFirstChild();
         if (startNode == null) {
            startNode = processedNode.getNextSibling();
         }

         while(startNode == null) {
            processedNode = processedNode.getParentNode();
            if (processedNode == startParent) {
               return true;
            }

            startNode = processedNode.getNextSibling();
         }
      }

      return true;
   }

   public static DocumentBuilder createDocumentBuilder(boolean validating) throws ParserConfigurationException {
      return createDocumentBuilder(validating, true);
   }

   public static DocumentBuilder createDocumentBuilder(boolean validating, boolean disAllowDocTypeDeclarations) throws ParserConfigurationException {
      int idx = getPoolsIndex(validating, disAllowDocTypeDeclarations);
      return (DocumentBuilder)pools[idx].getObject();
   }

   public static boolean repoolDocumentBuilder(DocumentBuilder db) {
      if (!(db instanceof DocumentBuilderProxy)) {
         return false;
      } else {
         db.reset();
         boolean disAllowDocTypeDeclarations = ((DocumentBuilderProxy)db).disAllowDocTypeDeclarations();
         int idx = getPoolsIndex(db.isValidating(), disAllowDocTypeDeclarations);
         return pools[idx].repool(db);
      }
   }

   public static byte[] getBytes(BigInteger big, int bitlen) {
      bitlen = bitlen + 7 >> 3 << 3;
      if (bitlen < big.bitLength()) {
         throw new IllegalArgumentException(I18n.translate("utils.Base64.IllegalBitlength"));
      } else {
         byte[] bigBytes = big.toByteArray();
         if (big.bitLength() % 8 != 0 && big.bitLength() / 8 + 1 == bitlen / 8) {
            return bigBytes;
         } else {
            int startSrc = 0;
            int bigLen = bigBytes.length;
            if (big.bitLength() % 8 == 0) {
               startSrc = 1;
               --bigLen;
            }

            int startDst = bitlen / 8 - bigLen;
            byte[] resizedBytes = new byte[bitlen / 8];
            System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, bigLen);
            return resizedBytes;
         }
      }
   }

   private static int getPoolsIndex(boolean validating, boolean disAllowDocTypeDeclarations) {
      return (validating ? 2 : 0) + (disAllowDocTypeDeclarations ? 1 : 0);
   }

   static {
      pools[0] = new DocumentBuilderPool(false, false);
      pools[1] = new DocumentBuilderPool(false, true);
      pools[2] = new DocumentBuilderPool(true, false);
      pools[3] = new DocumentBuilderPool(true, true);
      dsPrefix = "ds";
      ds11Prefix = "dsig11";
      xencPrefix = "xenc";
      xenc11Prefix = "xenc11";
      LOG = LoggerFactory.getLogger(XMLUtils.class);
   }

   private static final class DocumentBuilderPool extends WeakObjectPool {
      private final boolean validating;
      private final boolean disAllowDocTypeDeclarations;

      public DocumentBuilderPool(boolean validating, boolean disAllowDocTypeDeclarations) {
         this.validating = validating;
         this.disAllowDocTypeDeclarations = disAllowDocTypeDeclarations;
      }

      protected DocumentBuilder createObject() throws ParserConfigurationException {
         DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
         dfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
         if (this.disAllowDocTypeDeclarations) {
            dfactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
         }

         dfactory.setValidating(this.validating);
         dfactory.setNamespaceAware(true);
         return new DocumentBuilderProxy(dfactory.newDocumentBuilder(), this.disAllowDocTypeDeclarations);
      }
   }

   private static class DocumentBuilderProxy extends DocumentBuilder {
      private final DocumentBuilder delegate;
      private final boolean disAllowDocTypeDeclarations;

      private DocumentBuilderProxy(DocumentBuilder actual, boolean disAllowDocTypeDeclarations) {
         this.delegate = actual;
         this.disAllowDocTypeDeclarations = disAllowDocTypeDeclarations;
      }

      boolean disAllowDocTypeDeclarations() {
         return this.disAllowDocTypeDeclarations;
      }

      public void reset() {
         this.delegate.reset();
      }

      public Document parse(InputStream is) throws SAXException, IOException {
         return this.delegate.parse(is);
      }

      public Document parse(InputStream is, String systemId) throws SAXException, IOException {
         return this.delegate.parse(is, systemId);
      }

      public Document parse(String uri) throws SAXException, IOException {
         return this.delegate.parse(uri);
      }

      public Document parse(File f) throws SAXException, IOException {
         return this.delegate.parse(f);
      }

      public Schema getSchema() {
         return this.delegate.getSchema();
      }

      public boolean isXIncludeAware() {
         return this.delegate.isXIncludeAware();
      }

      public Document parse(InputSource is) throws SAXException, IOException {
         return this.delegate.parse(is);
      }

      public boolean isNamespaceAware() {
         return this.delegate.isNamespaceAware();
      }

      public boolean isValidating() {
         return this.delegate.isValidating();
      }

      public void setEntityResolver(EntityResolver er) {
         this.delegate.setEntityResolver(er);
      }

      public void setErrorHandler(ErrorHandler eh) {
         this.delegate.setErrorHandler(eh);
      }

      public Document newDocument() {
         return this.delegate.newDocument();
      }

      public DOMImplementation getDOMImplementation() {
         return this.delegate.getDOMImplementation();
      }

      // $FF: synthetic method
      DocumentBuilderProxy(DocumentBuilder x0, boolean x1, Object x2) {
         this(x0, x1);
      }
   }
}
