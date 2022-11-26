package weblogic.xml.stax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SAXHandlerContext implements ContentHandler {
   private Locator locator;
   private ArrayList states = new ArrayList();
   private ArrayList nameSpaceDeclarations;
   private static boolean debug = false;

   public List getStates() {
      return this.states;
   }

   public void setDocumentLocator(Locator locator) {
      this.locator = locator;
   }

   public void startDocument() throws SAXException {
      if (debug) {
         System.out.println("startDocument()");
      }

      this.states = new ArrayList();
      this.states.add(new EventState(7));
   }

   public void endDocument() throws SAXException {
      if (debug) {
         System.out.println("endDocument()");
      }

      this.states.add(new EventState(8));
   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      if (this.nameSpaceDeclarations == null) {
         this.nameSpaceDeclarations = new ArrayList();
      }

      this.nameSpaceDeclarations.add(new NamespaceBase(prefix, uri));
   }

   public void endPrefixMapping(String prefix) throws SAXException {
   }

   private static String getPrefix(String qName) {
      int index = qName.indexOf(58);
      return index == -1 ? null : qName.substring(0, index);
   }

   private static String getUri(String uri) {
      if (uri.equals("")) {
         uri = null;
      }

      return uri;
   }

   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
      if (debug) {
         System.out.println("startElement(" + localName + ")");
      }

      EventState state = new EventState(1);
      state.setName(new QName(namespaceURI, localName, getPrefix(qName)));

      for(int i = 0; i < atts.getLength(); ++i) {
         String attLocalName = atts.getLocalName(i);
         String attPrefix = getPrefix(atts.getQName(i));
         if (attLocalName.equals("xmlns")) {
            state.addNamespace(new NamespaceBase(atts.getValue(i)));
         } else if (attPrefix != null && attPrefix.equals("xmlns")) {
            state.addNamespace(new NamespaceBase(attPrefix, atts.getValue(i)));
         } else {
            state.addAttribute(new AttributeBase(new QName(getUri(atts.getURI(i)), atts.getLocalName(i), getPrefix(atts.getQName(i))), atts.getValue(i)));
         }
      }

      if (this.nameSpaceDeclarations != null) {
         Iterator i = this.nameSpaceDeclarations.iterator();

         while(i.hasNext()) {
            state.addNamespace((NamespaceBase)i.next());
         }

         this.nameSpaceDeclarations = null;
      }

      this.states.add(state);
   }

   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
      if (debug) {
         System.out.println("endElement(" + localName + ")");
      }

      EventState state = new EventState(2);
      state.setName(new QName(getUri(namespaceURI), localName, getPrefix(qName)));
      this.states.add(state);
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if (debug) {
         System.out.println("characters()");
      }

      EventState state = new EventState(4);
      state.setData(new String(ch, start, length));
      this.states.add(state);
   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      throw new UnsupportedOperationException("NYI");
   }

   public void processingInstruction(String target, String data) throws SAXException {
      EventState state = new EventState(3);
      state.setData(target);
      state.setExtraData(data);
      this.states.add(state);
   }

   public void skippedEntity(String name) throws SAXException {
   }

   public XMLStreamReader reader() {
      return new XMLStreamBuffer(this.getStates());
   }

   public String toString() {
      try {
         StringBuffer b = new StringBuffer();
         XMLStreamReader r = this.reader();

         int i;
         for(i = 0; r.hasNext(); ++i) {
            b.append("STATE[" + i + "]" + XMLStreamReaderBase.printEvent(r) + "\n");
            r.next();
         }

         b.append("STATE[" + i + "]" + XMLStreamReaderBase.printEvent(r) + "\n");
         return b.toString();
      } catch (XMLStreamException var4) {
         return "Error: " + var4.getMessage();
      }
   }
}
