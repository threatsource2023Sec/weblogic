package weblogic.xml.domimpl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Iterator;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import weblogic.xml.dom.NamespaceUtils;
import weblogic.xml.domimpl.util.DOMNodeIterator;
import weblogic.xml.stax.XMLStreamOutputFactory;
import weblogic.xml.util.PrettyXMLStreamWriter;
import weblogic.xml.util.ScopedNamespaceContext;

public final class Saver {
   private final XMLStreamWriter writer;
   private final SaverOptions options;
   private final ScopedNamespaceContext namespaceContext;
   private int prefixCount;
   private boolean needNsDef;
   private static final String XML_VERSION = "1.0";
   private static final String XML_DEFAULT_NS_NAME = "xmlns:";
   private static final XMLOutputFactory XML_OUTPUT_FACTORY = createXMLOutputFactory();

   private Saver(XMLStreamWriter stream_writer, SaverOptions opts) {
      this.writer = stream_writer;
      this.options = opts;
      this.namespaceContext = new ScopedNamespaceContext();
   }

   public static void save(OutputStream os, Document doc, SaverOptions opts) throws IOException {
      OutputStream internal = os;
      if (opts == null) {
         opts = SaverOptions.getDefaults();
      }

      try {
         String encoding = getEncoding(opts, doc);
         XMLStreamWriter writer;
         if (encoding != null) {
            writer = XML_OUTPUT_FACTORY.createXMLStreamWriter(internal, encoding);
         } else {
            writer = XML_OUTPUT_FACTORY.createXMLStreamWriter(internal);
         }

         writer = prettyPrintWriter(writer, opts);
         save(writer, doc, opts);
         writer.close();
      } catch (XMLStreamException var6) {
         throw (IOException)((IOException)(new IOException(var6.getMessage())).initCause(var6));
      }
   }

   private static XMLStreamWriter prettyPrintWriter(XMLStreamWriter writer, SaverOptions opts) {
      assert writer != null;

      if (opts.isPrettyPrint()) {
         writer = new PrettyXMLStreamWriter((XMLStreamWriter)writer);
      }

      return (XMLStreamWriter)writer;
   }

   private static String getEncoding(SaverOptions opts, Document doc) {
      String opts_enc = opts.getEncoding();
      String doc_enc = safeGetXmlEncoding(doc);
      if (opts_enc == null) {
         return doc_enc;
      } else if (doc_enc == null) {
         return opts_enc;
      } else {
         assert opts_enc != null;

         assert doc_enc != null;

         if (equalCharsets(opts_enc, doc_enc)) {
            return doc_enc;
         } else {
            String msg = "encoding from options: " + opts_enc + " does not match encoding from document.getXmlEncoding(): " + doc_enc;
            throw new IllegalStateException(msg);
         }
      }
   }

   private static boolean equalCharsets(String opts_enc, String doc_enc) {
      Charset opts_cs = Charset.forName(opts_enc);
      Charset doc_cs = Charset.forName(doc_enc);
      return opts_cs.compareTo(doc_cs) == 0;
   }

   public static void save(OutputStream os, Document doc) throws IOException {
      save(os, doc, SaverOptions.getDefaults());
   }

   public static void save(Writer out, Document document) throws IOException {
      try {
         XMLStreamWriter writer = prettyPrintWriter(XML_OUTPUT_FACTORY.createXMLStreamWriter(out));
         save(writer, document, SaverOptions.getDefaults());
         writer.close();
      } catch (XMLStreamException var3) {
         throw (IOException)((IOException)(new IOException(var3.getMessage())).initCause(var3));
      }
   }

   private static XMLStreamWriter prettyPrintWriter(XMLStreamWriter writer) {
      return prettyPrintWriter(writer, SaverOptions.getDefaults());
   }

   public static void save(OutputStream os, Element element) throws IOException {
      try {
         XMLStreamWriter writer = prettyPrintWriter(XML_OUTPUT_FACTORY.createXMLStreamWriter(os));
         save((XMLStreamWriter)writer, (Node)element);
         writer.close();
      } catch (XMLStreamException var3) {
         throw (IOException)((IOException)(new IOException(var3.getMessage())).initCause(var3));
      }
   }

   public static void save(Writer out, Element element) throws IOException {
      try {
         XMLStreamWriter writer = prettyPrintWriter(XML_OUTPUT_FACTORY.createXMLStreamWriter(out));
         save((XMLStreamWriter)writer, (Node)element);
         writer.close();
      } catch (XMLStreamException var3) {
         throw (IOException)((IOException)(new IOException(var3.getMessage())).initCause(var3));
      }
   }

   private void saveInternal(Node curr) throws XMLStreamException {
      this.saveInternal(curr, false);
   }

   private void saveInternal(Node curr, boolean isSingleNode) throws XMLStreamException {
      XMLStreamWriter xw = this.writer;
      DOMNodeIterator itr = new DOMNodeIterator(curr);

      while(itr.hasNext()) {
         curr = itr.nextNode();
         switch (curr.getNodeType()) {
            case 1:
               if (itr.isOpen()) {
                  this.namespaceContext.openScope();
                  this.writeStartElementForNode((Element)curr);
               } else {
                  assert itr.isClosed();

                  if (curr.hasChildNodes()) {
                     xw.writeEndElement();
                  } else if (isSingleNode) {
                     xw.writeEndDocument();
                  }

                  this.namespaceContext.closeScope();
               }
               break;
            case 2:
               throw new AssertionError("unexpected node: " + curr.getNodeName());
            case 3:
               String nodeValue = curr.getNodeValue();
               if (nodeValue != null) {
                  xw.writeCharacters(nodeValue);
               }
               break;
            case 4:
               xw.writeCData(curr.getNodeValue());
               break;
            case 5:
               throw new AssertionError("unexpected node: " + curr.getNodeName());
            case 6:
               throw new AssertionError("unexpected node: " + curr.getNodeName());
            case 7:
               xw.writeCData(curr.getNodeValue());
               break;
            case 8:
               xw.writeComment(curr.getNodeValue());
               break;
            case 9:
               Document doc = (Document)curr;
               if (this.writeXmlDecl(doc)) {
                  String encoding = getEncoding(this.options, doc);
                  if (encoding != null) {
                     this.writer.writeStartDocument(encoding, "1.0");
                  } else {
                     this.writer.writeStartDocument("1.0");
                  }
               }

               this.saveInternal(doc.getDocumentElement());
               break;
            case 10:
               throw new AssertionError("unexpected node: " + curr.getNodeName());
            case 11:
               throw new AssertionError("unexpected node: " + curr.getNodeName());
            case 12:
               throw new AssertionError("unexpected node: " + curr.getNodeName());
            default:
               throw new AssertionError("unknown node type: " + curr.getNodeName());
         }
      }

   }

   private boolean writeXmlDecl(Document doc) {
      if (this.options.isSetWriteXmlDeclaration()) {
         return this.options.isWriteXmlDeclaration();
      } else {
         return safeGetXmlEncoding(doc) != null;
      }
   }

   private static String safeGetXmlEncoding(Document doc) {
      boolean doc_has_encoding = doc instanceof DocumentImpl || doc.getImplementation().hasFeature("Core", "3.0");
      return !doc_has_encoding ? null : doc.getXmlEncoding();
   }

   private void writeStartElementForNode(Element curr) throws XMLStreamException {
      String ln = curr.getLocalName();
      if (ln == null) {
         if (curr.hasChildNodes()) {
            this.writer.writeStartElement(curr.getNodeName());
         } else {
            this.writer.writeEmptyElement(curr.getNodeName());
         }
      } else {
         this.writeLvl2StartOrEmptyElem(curr);
      }

      if (curr.hasAttributes()) {
         this.writeAttributes(curr);
      }

   }

   private static XMLOutputFactory createXMLOutputFactory() {
      XMLOutputFactory factory = new XMLStreamOutputFactory();
      if (factory.isPropertySupported("weblogic.xml.stream.isEscapingCR")) {
         factory.setProperty("weblogic.xml.stream.isEscapingCR", Boolean.TRUE);
      }

      if (factory.isPropertySupported("weblogic.xml.stream.isEscapingCRLFTAB")) {
         factory.setProperty("weblogic.xml.stream.isEscapingCRLFTAB", Boolean.TRUE);
      }

      return factory;
   }

   private static void save(XMLStreamWriter stream_writer, Document doc, SaverOptions options) throws XMLStreamException {
      Saver saver = new Saver(stream_writer, options);
      saver.saveInternal(doc);
   }

   public static void save(XMLStreamWriter stream_writer, Node node) throws XMLStreamException {
      Saver saver = new Saver(stream_writer, SaverOptions.getDefaults());
      if (node.getNodeType() == 1 && !node.hasChildNodes()) {
         saver.saveInternal(node, true);
      } else {
         saver.saveInternal(node);
      }
   }

   private void writeLvl2StartOrEmptyElem(Element curr) throws XMLStreamException {
      String ln = curr.getLocalName();

      assert ln != null;

      String uri = curr.getNamespaceURI();
      String pfx;
      if (curr.hasChildNodes()) {
         if (noUri(uri)) {
            this.writer.writeStartElement(ln);
            this.declareDefaultNS(curr);
         } else {
            pfx = this.getElementPrefix(curr);
            this.writer.writeStartElement(pfx, ln, uri);
            if (this.needNsDef) {
               this.writeNamespaceForElement(pfx, uri);
            }
         }
      } else if (noUri(uri)) {
         this.writer.writeEmptyElement(ln);
         this.declareDefaultNS(curr);
      } else {
         pfx = this.getElementPrefix(curr);
         this.writer.writeEmptyElement(pfx, ln, uri);
         if (this.needNsDef) {
            this.writeNamespaceForElement(pfx, uri);
         }
      }

   }

   private void declareDefaultNS(Element curr) throws XMLStreamException {
      if (!noUri(this.namespaceContext.getNamespaceURI("")) && curr.getAttributeNode("xmlns") == null && curr instanceof ElementBase && ((ElementBase)curr).isImportedFromNSAwareElement()) {
         this.writeNamespaceForElement("", "");
      }

   }

   private void writeNamespaceForElement(String pfx, String uri) throws XMLStreamException {
      this.writeNamespace(pfx, uri);
      this.needNsDef = false;
   }

   private void writeNamespace(String pfx, String uri) throws XMLStreamException {
      this.writer.writeNamespace(pfx, uri);
      this.namespaceContext.bindNamespace(pfx, uri);
   }

   private String getElementPrefix(Element element_node) throws XMLStreamException {
      assert 1 == element_node.getNodeType();

      String pfx = element_node.getPrefix();
      String namespaceURI;
      if (pfx == null) {
         namespaceURI = element_node.getNamespaceURI();

         assert namespaceURI != null;

         assert namespaceURI.length() > 0;

         assert !this.needNsDef;

         String found_uri = this.lookupNamespaceOnElement(element_node, "");
         if (namespaceURI.equals(found_uri)) {
            return "";
         }

         pfx = this.lookupPrefixOnElement(element_node, namespaceURI, true);
         if (pfx != null) {
            return pfx;
         }

         for(pfx = this.getNewPrefix(namespaceURI); this.getNamespaceURI(pfx) != null; pfx = this.getNewPrefix(namespaceURI)) {
         }

         this.needNsDef = true;
      } else {
         namespaceURI = this.lookupNamespaceOnElement(element_node, pfx);
         if (namespaceURI == null) {
            this.needNsDef = true;
         } else if (!namespaceURI.equals(element_node.getNamespaceURI())) {
            this.needNsDef = true;
         }
      }

      assert pfx != null;

      return pfx;
   }

   private String lookupPrefixOnElement(Node element_node, String namespaceURI, boolean check_default_ns) {
      String pfx = NamespaceUtils.getPrefixOnElement(element_node, namespaceURI, check_default_ns);
      if (pfx != null) {
         return pfx;
      } else {
         pfx = this.getPrefix(namespaceURI);
         if ("".equals(pfx)) {
            pfx = null;
            Iterator prefixes = this.getPrefixes(namespaceURI);

            while(prefixes.hasNext()) {
               String p = (String)prefixes.next();
               if (!"".equals(p)) {
                  return p;
               }
            }
         }

         return pfx;
      }
   }

   private String getNamespaceURI(String pfx) {
      return this.namespaceContext.getNamespaceURI(pfx);
   }

   private Iterator getPrefixes(String namespaceURI) {
      return this.namespaceContext.getPrefixes(namespaceURI);
   }

   private String getPrefix(String namespaceURI) {
      return this.namespaceContext.getPrefix(namespaceURI);
   }

   private String lookupNamespaceOnElement(Element element_node, String prefix) {
      String uri = NamespaceUtils.getNamespaceOnElement(element_node, prefix);
      if (uri != null) {
         return uri;
      } else {
         uri = this.getNamespaceURI(prefix);
         return uri;
      }
   }

   private String getNewPrefix(String namespaceURI) {
      return "n" + ++this.prefixCount;
   }

   private void writeAttributes(Node curr) throws XMLStreamException {
      NamedNodeMap atts = curr.getAttributes();
      int i = 0;

      for(int len = atts.getLength(); i < len; ++i) {
         Attr att = (Attr)atts.item(i);

         assert att.getNodeType() == 2;

         String att_ln = att.getLocalName();
         if (att_ln == null) {
            String nodeName = att.getNodeName();
            if (!"xmlns".equals(nodeName) && !"xmlns:".equals(nodeName)) {
               this.writer.writeAttribute(nodeName, att.getNodeValue());
            } else {
               this.writeDefaultNamespaceAttribute(att);
            }
         } else {
            this.writeLvl2Attribute(att);
         }
      }

   }

   private void writeLvl2Attribute(Attr att) throws XMLStreamException {
      assert att.getLocalName() != null;

      String uri = att.getNamespaceURI();
      if (noUri(uri)) {
         if ("xmlns".equals(att.getLocalName())) {
            String msg = "invalid dom attribute node with no uri,  localName=" + att.getLocalName() + " and prefix=" + att.getPrefix();
            throw new XMLStreamException(msg);
         }

         this.writer.writeAttribute(att.getLocalName(), att.getNodeValue());
      } else if ("xmlns".equals(att.getPrefix())) {
         assert "http://www.w3.org/2000/xmlns/".equals(uri);

         this.writeNamespaceAttribute(att);
      } else if ("http://www.w3.org/2000/xmlns/".equals(uri)) {
         this.writeDefaultNamespaceAttribute(att);
      } else {
         this.writer.writeAttribute(this.getAttributePrefix(att), uri, att.getLocalName(), att.getNodeValue());
      }

   }

   private void writeDefaultNamespaceAttribute(Node att) throws XMLStreamException {
      String uri = att.getNodeValue();
      this.writer.writeDefaultNamespace(uri);
      this.namespaceContext.bindNamespace("", uri);
   }

   private void writeNamespaceAttribute(Node att) throws XMLStreamException {
      this.writeNamespace(att.getLocalName(), att.getNodeValue());
   }

   private static boolean noUri(String uri) {
      return uri == null || uri.length() == 0;
   }

   private static boolean noUri(Node n) {
      return noUri(n.getNamespaceURI());
   }

   private String getAttributePrefix(Attr att) throws XMLStreamException {
      assert !noUri((Node)att);

      assert 2 == att.getNodeType();

      String pfx = att.getPrefix();
      if (pfx == "") {
         pfx = null;
      }

      String att_uri = att.getNamespaceURI();
      if (pfx == null) {
         assert att_uri != null;

         assert att_uri.length() > 0;

         assert !this.needNsDef;

         pfx = this.lookupPrefixOnElement(att.getOwnerElement(), att_uri, false);
         if (pfx != null) {
            return pfx;
         }

         for(pfx = this.getNewPrefix(att_uri); this.getNamespaceURI(pfx) != null; pfx = this.getNewPrefix(att_uri)) {
         }

         assert pfx != null;

         this.writeNamespace(pfx, att_uri);
      } else {
         String found_uri = this.lookupNamespaceOnElement(att.getOwnerElement(), pfx);
         if (found_uri == null) {
            this.writeNamespace(pfx, att_uri);
         } else if (!found_uri.equals(att_uri)) {
            String e = "mismatched namespace and prefix on attribute.  prefix=" + pfx + " namespace=" + att_uri + " localname=" + att.getLocalName();
            throw new XMLStreamException(e);
         }
      }

      assert pfx != null;

      return pfx;
   }
}
