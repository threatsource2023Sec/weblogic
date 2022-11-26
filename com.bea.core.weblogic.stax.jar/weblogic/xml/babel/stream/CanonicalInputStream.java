package weblogic.xml.babel.stream;

import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.SpaceEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.stream.util.XMLInputStreamFilterBase;

public class CanonicalInputStream extends XMLInputStreamFilterBase implements ElementFilter {
   private ScopeManager namespaces = new ScopeManager();
   private static final SpaceEvent eol = new SpaceEvent("\n");
   private boolean beforeDocumentElement = true;

   public CanonicalInputStream(XMLInputStream parent) throws XMLStreamException {
      this.setParent(parent);
      this.setFilter(this);
   }

   public boolean accept(XMLEvent element) {
      if (!this.namespaces.inRootElement() && (element.isSpace() || element.isCharacterData() || element.isStartDocument() || element.isEndDocument())) {
         return false;
      } else {
         return !element.isChangePrefixMapping() && !element.isStartPrefixMapping() && !element.isEndPrefixMapping();
      }
   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      return new CanonicalInputStream(this.getParent().getSubStream());
   }

   protected XMLEvent modify(XMLEvent e) throws XMLStreamException {
      if (!this.namespaces.inRootElement()) {
         switch (e.getType()) {
            case 8:
            case 32:
               if (!this.beforeDocumentElement) {
                  this.pushBack(e);
                  return eol;
               }

               this.pushBack(eol);
         }
      }

      switch (e.getType()) {
         case 2:
            this.beforeDocumentElement = false;
            return this.modify((StartElement)e);
         case 4:
            this.namespaces.closeScope();
            return e;
         case 16:
            return this.modify((CharacterData)e);
         default:
            return e;
      }
   }

   private String checkNull(String val) {
      return val == null ? "" : val;
   }

   private StartElement modify(StartElement startElement) {
      this.namespaces.openScope();
      StartElementEvent newElement = new StartElementEvent(startElement.getName());
      newElement.setNamespaceMap(startElement.getNamespaceMap());
      AttributeIterator i = CanonicalUtils.sortNamespaces(startElement.getNamespaces());

      Attribute att;
      while(i.hasNext()) {
         att = i.next();
         this.namespaces.checkPrefixMap(att.getName().getLocalName(), att.getValue());
         if (this.namespaces.needToWriteNS(att.getName().getLocalName())) {
            newElement.addNamespace(new AttributeImpl(att.getName(), this.checkNull(CanonicalUtils.normalizeCharacters(att.getValue(), true)), att.getType()));
            this.namespaces.wroteNS(att.getName().getLocalName());
         }
      }

      i = CanonicalUtils.sortAttributes(startElement.getAttributes());

      while(i.hasNext()) {
         att = i.next();
         newElement.addAttribute(new AttributeImpl(att.getName(), this.checkNull(CanonicalUtils.normalizeCharacters(att.getValue(), true)), att.getType()));
      }

      return newElement;
   }

   private CharacterData modify(CharacterData cdata) {
      return new CharacterDataEvent(this.checkNull(CanonicalUtils.normalizeCharacters(cdata.getContent(), false)));
   }

   public static void main(String[] args) throws Exception {
      XMLInputStreamBase base = new XMLInputStreamBase();
      base.openValidating(SAXElementFactory.createInputSource(args[0]));
      XMLInputStream canonicalStream = new CanonicalInputStream(base);

      while(canonicalStream.hasNext()) {
         XMLEvent elementEvent = canonicalStream.next();
         System.out.print(elementEvent);
      }

   }
}
