package weblogic.xml.saaj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import weblogic.xml.babel.reader.XmlReader;
import weblogic.xml.domimpl.Loader;
import weblogic.xml.domimpl.Saver;

class SOAPPartImpl extends SOAPPart {
   private SaajDocument document;
   private final MimeHeaders headers;
   private String soapNS;

   SOAPPartImpl(String soapNS) {
      this(soapNS, new MimeHeaders());
   }

   SOAPPartImpl(String soapNS, MimeHeaders headers) {
      this.soapNS = soapNS;
      this.document = new SaajDocument(true, soapNS);
      this.headers = headers;
      this.headers.setHeader("Content-Type", SOAPConstants.getMimeType(soapNS));
   }

   SOAPPartImpl(SaajDocument document, MimeHeaders headers) {
      this.document = document;
      this.headers = headers;
      this.soapNS = "http://schemas.xmlsoap.org/soap/envelope/";
   }

   protected MimeHeaders getHeaders() {
      return this.headers;
   }

   public void removeAllMimeHeaders() {
      this.headers.removeAllHeaders();
   }

   public void removeMimeHeader(String s) {
      this.headers.removeHeader(s);
   }

   public Iterator getAllMimeHeaders() {
      return this.headers.getAllHeaders();
   }

   public SOAPEnvelope getEnvelope() throws SOAPException {
      return this.envelope();
   }

   SOAPEnvelopeImpl envelope() throws SOAPException {
      return this.document.getEnvelope();
   }

   public Source getContent() throws SOAPException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      try {
         Saver.save((OutputStream)bos, (Document)this.document);
         bos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
         return new StreamSource(bis);
      } catch (IOException var3) {
         throw new SOAPException(var3);
      }
   }

   public void setContent(Source source) throws SOAPException {
      try {
         if (source instanceof StreamSource) {
            InputStream is = ((StreamSource)source).getInputStream();
            Reader rdr = ((StreamSource)source).getReader();
            if (is != null) {
               this.document = this.createDocumentFromInputStream(is);
            } else {
               if (rdr == null) {
                  throw new SOAPException("Source does not have a valid Reader or InputStream");
               }

               this.document = this.createDocumentFromReader(rdr);
            }
         }

      } catch (IOException var4) {
         throw new SOAPException("Error setting the source for SOAPPart: " + var4.getMessage(), var4);
      }
   }

   protected SaajDocument createDocumentFromInputStream(InputStream is) throws IOException {
      SaajDocument sd = new SaajDocument(false, this.soapNS);
      Document doc = Loader.load((InputStream)is, sd);

      assert doc == sd;

      this.document = sd;
      return sd;
   }

   protected SaajDocument createDocumentFromInputStream(InputStream is, String encoding) throws IOException {
      assert encoding != null;

      new String();
      String newEncoding;
      if (encoding.equalsIgnoreCase("us-ascii")) {
         newEncoding = "UTF-8";
      } else {
         newEncoding = encoding;
      }

      Reader rdr = getReaderFromInputStream(is, newEncoding);
      SaajDocument sd = new SaajDocument(false, this.soapNS);
      Document doc = Loader.load((Reader)rdr, sd);

      assert doc == sd;

      this.document = sd;
      return sd;
   }

   private static Reader getReaderFromInputStream(InputStream is, String encoding) throws IOException {
      return XmlReader.createReader(is, encoding);
   }

   private SaajDocument createDocumentFromReader(Reader rdr) throws IOException {
      SaajDocument sd = new SaajDocument(false, this.soapNS);
      Document doc = Loader.load((Reader)rdr, sd);

      assert doc == sd;

      this.document = sd;
      return sd;
   }

   public String[] getMimeHeader(String s) {
      return this.headers.getHeader(s);
   }

   public void addMimeHeader(String name, String value) {
      this.headers.addHeader(name, value);
   }

   public void setMimeHeader(String name, String value) {
      this.headers.setHeader(name, value);
   }

   public Iterator getMatchingMimeHeaders(String[] strings) {
      return this.headers.getMatchingHeaders(strings);
   }

   public Iterator getNonMatchingMimeHeaders(String[] strings) {
      return this.headers.getNonMatchingHeaders(strings);
   }

   public DocumentType getDoctype() {
      return this.document.getDoctype();
   }

   public DOMImplementation getImplementation() {
      return this.document.getImplementation();
   }

   public Element getDocumentElement() {
      return this.document.getDocumentElement();
   }

   public Element createElement(String tagName) throws DOMException {
      return this.document.createElement(tagName);
   }

   public DocumentFragment createDocumentFragment() {
      return this.document.createDocumentFragment();
   }

   public Text createTextNode(String data) {
      return this.document.createTextNode(data);
   }

   public Comment createComment(String data) {
      return this.document.createComment(data);
   }

   public CDATASection createCDATASection(String data) throws DOMException {
      return this.document.createCDATASection(data);
   }

   public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
      return this.document.createProcessingInstruction(target, data);
   }

   public Attr createAttribute(String name) throws DOMException {
      return this.document.createAttribute(name);
   }

   public EntityReference createEntityReference(String name) throws DOMException {
      return this.document.createEntityReference(name);
   }

   public NodeList getElementsByTagName(String tagname) {
      return this.document.getElementsByTagName(tagname);
   }

   public Node importNode(Node importedNode, boolean deep) throws DOMException {
      return this.document.importNode(importedNode, deep);
   }

   public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
      return this.document.createElementNS(namespaceURI, qualifiedName);
   }

   public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
      return this.document.createAttributeNS(namespaceURI, qualifiedName);
   }

   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
      return this.document.getElementsByTagNameNS(namespaceURI, localName);
   }

   public Element getElementById(String elementId) {
      return this.document.getElementById(elementId);
   }

   public String getInputEncoding() {
      return this.document.getInputEncoding();
   }

   public String getXmlEncoding() {
      return this.document.getXmlEncoding();
   }

   public boolean getXmlStandalone() {
      return this.document.getXmlStandalone();
   }

   public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
      this.document.setXmlStandalone(xmlStandalone);
   }

   public String getXmlVersion() {
      return this.document.getXmlVersion();
   }

   public void setXmlVersion(String xmlVersion) throws DOMException {
      this.document.setXmlVersion(xmlVersion);
   }

   public boolean getStrictErrorChecking() {
      return this.document.getStrictErrorChecking();
   }

   public void setStrictErrorChecking(boolean strictErrorChecking) {
      this.document.setStrictErrorChecking(strictErrorChecking);
   }

   public String getDocumentURI() {
      return this.document.getDocumentURI();
   }

   public void setDocumentURI(String documentURI) {
      this.document.setDocumentURI(documentURI);
   }

   public Node adoptNode(Node source) throws DOMException {
      return this.document.adoptNode(source);
   }

   public DOMConfiguration getDomConfig() {
      return this.document.getDomConfig();
   }

   public void normalizeDocument() {
      this.document.normalizeDocument();
   }

   public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
      return this.document.renameNode(n, namespaceURI, qualifiedName);
   }

   public String getNodeName() {
      return this.document.getNodeName();
   }

   public String getNodeValue() throws DOMException {
      return this.document.getNodeValue();
   }

   public void setNodeValue(String nodeValue) throws DOMException {
      this.document.setNodeValue(nodeValue);
   }

   public short getNodeType() {
      return this.document.getNodeType();
   }

   public Node getParentNode() {
      return this.document.getParentNode();
   }

   public NodeList getChildNodes() {
      return this.document.getChildNodes();
   }

   public Node getFirstChild() {
      return this.document.getFirstChild();
   }

   public Node getLastChild() {
      return this.document.getLastChild();
   }

   public Node getPreviousSibling() {
      return this.document.getPreviousSibling();
   }

   public Node getNextSibling() {
      return this.document.getNextSibling();
   }

   public NamedNodeMap getAttributes() {
      return this.document.getAttributes();
   }

   public Document getOwnerDocument() {
      return this;
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
      return this.document.insertBefore(newChild, refChild);
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
      return this.document.replaceChild(newChild, oldChild);
   }

   public Node removeChild(Node oldChild) throws DOMException {
      return this.document.removeChild(oldChild);
   }

   public Node appendChild(Node newChild) throws DOMException {
      if (this.isSoapEnvelope(newChild) && this.isEmpty()) {
         this.document.removeChild(this.document.getDocumentElement());
      }

      return this.document.appendChild(newChild);
   }

   private boolean isEmpty() {
      try {
         SOAPEnvelope envelope = this.getEnvelope();
         SOAPHeader soapHeader = envelope.getHeader();
         if (soapHeader != null && soapHeader.getChildElements().hasNext()) {
            return false;
         } else {
            SOAPBody soapBody = envelope.getBody();
            return soapBody == null || !soapBody.getChildElements().hasNext();
         }
      } catch (SOAPException var4) {
         return false;
      }
   }

   private boolean isSoapEnvelope(Node node) {
      if (node.getLocalName() != null && node.getNamespaceURI() != null) {
         QName envelopeQName = new QName(this.soapNS, "Envelope");
         QName nodeQName = new QName(node.getNamespaceURI(), node.getLocalName());
         return envelopeQName.equals(nodeQName);
      } else {
         return false;
      }
   }

   public boolean hasChildNodes() {
      return this.document.hasChildNodes();
   }

   public Node cloneNode(boolean deep) {
      return this.document.cloneNode(deep);
   }

   public void normalize() {
      this.document.normalize();
   }

   public boolean isSupported(String feature, String version) {
      return this.document.isSupported(feature, version);
   }

   public String getNamespaceURI() {
      return this.document.getNamespaceURI();
   }

   public String getPrefix() {
      return this.document.getPrefix();
   }

   public void setPrefix(String prefix) throws DOMException {
      this.document.setPrefix(prefix);
   }

   public String getLocalName() {
      return this.document.getLocalName();
   }

   public boolean hasAttributes() {
      return this.document.hasAttributes();
   }

   public String getBaseURI() {
      return this.document.getBaseURI();
   }

   public short compareDocumentPosition(Node other) throws DOMException {
      return this.document.compareDocumentPosition(other);
   }

   public String getTextContent() throws DOMException {
      return this.document.getTextContent();
   }

   public void setTextContent(String textContent) throws DOMException {
      this.document.setTextContent(textContent);
   }

   public boolean isSameNode(Node other) {
      return this.document.isSameNode(other);
   }

   public String lookupPrefix(String namespaceURI) {
      return this.document.lookupPrefix(namespaceURI);
   }

   public boolean isDefaultNamespace(String namespaceURI) {
      return this.document.isDefaultNamespace(namespaceURI);
   }

   public String lookupNamespaceURI(String prefix) {
      return this.document.lookupNamespaceURI(prefix);
   }

   public boolean isEqualNode(Node arg) {
      return this.document.isEqualNode(arg);
   }

   public Object getFeature(String feature, String version) {
      return this.document.getFeature(feature, version);
   }

   public Object setUserData(String key, Object data, UserDataHandler handler) {
      return this.document.setUserData(key, data, handler);
   }

   public Object getUserData(String key) {
      return this.document.getUserData(key);
   }

   public void detachNode() {
      throw new UnsupportedOperationException("This class does not support JDK6");
   }

   public void recycleNode() {
      throw new UnsupportedOperationException("This class does not support JDK6");
   }

   public void setParentElement(SOAPElement parent) throws SOAPException {
      throw new UnsupportedOperationException("This class does not support JDK6");
   }

   public SOAPElement getParentElement() {
      throw new UnsupportedOperationException("This class does not support JDK6");
   }

   public String getValue() {
      throw new UnsupportedOperationException("This class does not support JDK6");
   }

   public void setValue(String s) {
      throw new UnsupportedOperationException("This class does not support JDK6");
   }
}
