package weblogic.xml.domimpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.ProcessingInstruction;
import weblogic.xml.stax.RecyclingFactory;
import weblogic.xml.util.WriteNamespaceHandler;

public final class Loader {
   private static final XMLInputFactory XML_INPUT_FACTORY = new RecyclingFactory();
   private static final String XML_VERSION_10 = "1.0";

   private Loader() {
   }

   public static Document load(InputStream is) throws IOException {
      try {
         XMLStreamReader reader = createXMLStreamReader(is);
         Document document = load(reader);
         reader.close();
         return document;
      } catch (XMLStreamException var3) {
         throw (IOException)((IOException)(new IOException(var3.getMessage())).initCause(var3));
      }
   }

   public static Document load(Reader rdr) throws IOException {
      try {
         XMLStreamReader reader = createXMLStreamReader(rdr);
         Document document = load(reader);
         reader.close();
         return document;
      } catch (XMLStreamException var3) {
         throw (IOException)((IOException)(new IOException(var3.getMessage())).initCause(var3));
      }
   }

   private static XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return XML_INPUT_FACTORY.createXMLStreamReader(is);
   }

   private static XMLStreamReader createXMLStreamReader(Reader rdr) throws XMLStreamException {
      return XML_INPUT_FACTORY.createXMLStreamReader(rdr);
   }

   public static Document load(XMLStreamReader reader) throws XMLStreamException, IOException {
      return load(reader, new DocumentImpl());
   }

   public static Document load(InputStream stream, DocumentImpl doc) throws IOException {
      try {
         XMLStreamReader reader = createXMLStreamReader(stream);
         Document newdoc = load(reader, doc);
         reader.close();

         assert newdoc == doc;

         return newdoc;
      } catch (XMLStreamException var4) {
         throw (IOException)((IOException)(new IOException()).initCause(var4));
      }
   }

   public static Document load(Reader rdr, DocumentImpl doc) throws IOException {
      try {
         XMLStreamReader reader = createXMLStreamReader(rdr);
         Document newdoc = load(reader, doc);
         reader.close();

         assert newdoc == doc;

         return newdoc;
      } catch (XMLStreamException var4) {
         throw (IOException)((IOException)(new IOException()).initCause(var4));
      }
   }

   public static Document load(XMLStreamReader reader, DocumentImpl doc) throws XMLStreamException, IOException {
      doc.errorChecking = false;
      NodeImpl curr = doc;
      StringBuffer concatenatedChar = null;
      int state = reader.getEventType();

      while(true) {
         if (state == 4) {
            if (curr != doc) {
               if (concatenatedChar == null) {
                  concatenatedChar = new StringBuffer();
               }

               concatenatedChar.append(reader.getText());
            }
         } else if (concatenatedChar != null) {
            ((NodeImpl)curr).appendChild(doc.createTextNode(concatenatedChar.toString()));
            concatenatedChar = null;
         }

         if (!reader.hasNext() && concatenatedChar != null) {
            ((NodeImpl)curr).appendChild(doc.createTextNode(concatenatedChar.toString()));
            concatenatedChar = null;
         }

         switch (state) {
            case 1:
               curr = addChildElement(reader, (NodeImpl)curr);
               break;
            case 2:
               curr = ((NodeImpl)curr).parentNode();
               break;
            case 3:
               ProcessingInstruction pi = doc.createProcessingInstruction(reader.getPITarget(), reader.getPIData());
               ((NodeImpl)curr).appendChild(pi);
            case 4:
            case 8:
            case 11:
               break;
            case 5:
               if (curr != doc) {
                  ((NodeImpl)curr).appendChild(doc.createComment(reader.getText()));
               }
               break;
            case 6:
               if (curr != doc) {
                  ((NodeImpl)curr).appendChild(doc.createTextNode(reader.getText()));
               }
               break;
            case 7:
               if (reader.standaloneSet()) {
                  doc.setXmlStandalone(reader.isStandalone());
               }

               String version = checkXmlVersion(reader);
               if (version != null) {
                  doc.setXmlVersion(reader.getVersion());
               }

               String enc = reader.getCharacterEncodingScheme();
               if (enc != null) {
                  doc.setXmlEncoding(enc);
               }
               break;
            case 9:
               throw new AssertionError("UNIMP  ENTITY_REFERENCE");
            case 10:
               throw new AssertionError("unexpected state: " + state);
            case 12:
               ((NodeImpl)curr).appendChild(doc.createCDATASection(reader.getText()));
               break;
            case 13:
               throw new AssertionError("unexpected state: " + state);
            case 14:
               throw new AssertionError("unexpected state: " + state);
            case 15:
               throw new AssertionError("unexpected state: " + state);
            default:
               throw new AssertionError("unexpected state: " + state);
         }

         if (!reader.hasNext()) {
            return doc;
         }

         state = reader.next();
      }
   }

   public static XMLStreamWriter createDOMWriter(ElementBase top_elem) {
      return new XMLDomWriter(top_elem);
   }

   public static XMLStreamWriter createDOMWriter(ElementBase top_elem, WriteNamespaceHandler handler) {
      return new XMLDomWriter(top_elem, handler);
   }

   private static String checkXmlVersion(XMLStreamReader reader) throws IOException {
      String version = reader.getVersion();
      if (version == null) {
         return null;
      } else if (!"1.0".equals(version.trim())) {
         throw new IOException("This Dom only supports xml version 1.0");
      } else {
         return "1.0";
      }
   }

   private static ElementNSImpl addChildElement(XMLStreamReader reader, NodeImpl curr) {
      int att_cnt = reader.getAttributeCount();
      int ns_cnt = reader.getNamespaceCount();
      String uri = reader.getNamespaceURI();
      String localName = reader.getLocalName();
      String prefix = reader.getPrefix();
      int total_atts = att_cnt + ns_cnt;
      DocumentImpl doc = curr.ownerDocument();
      ElementNSImpl el = curr.ownerDocument().createAndAppendElement(curr, uri, localName, prefix, total_atts);

      assert el.getParentNode() == curr;

      int i;
      for(i = 0; i < att_cnt; ++i) {
         Attr att = doc.createNonNSAttributeNS(reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributePrefix(i));
         att.setValue(reader.getAttributeValue(i));
         el.setAttributeNodeNS(att);
      }

      for(i = 0; i < ns_cnt; ++i) {
         String pfx = reader.getNamespacePrefix(i);
         Attr att;
         if (pfx == null) {
            att = doc.createDefaultNSAttribute();
         } else {
            att = doc.createNSAttribute(pfx);
         }

         att.setValue(reader.getNamespaceURI(i));
         el.setAttributeNodeNS(att);
      }

      return el;
   }
}
