package weblogic.xml.stax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.stream.util.XMLEventConsumer;
import weblogic.utils.collections.Iterators;
import weblogic.xml.stax.util.TypeNames;

public class XMLEventAllocatorBase implements XMLEventAllocator {
   XMLEventFactory factory = new EventFactory();

   public XMLEventAllocator newInstance() {
      return this;
   }

   public static Iterator getAttributes(XMLStreamReader reader) {
      if (reader.getAttributeCount() == 0) {
         return Iterators.EMPTY_ITERATOR;
      } else {
         int attributeCount = reader.getAttributeCount();
         ArrayList atts = new ArrayList();

         for(int i = 0; i < attributeCount; ++i) {
            atts.add(new AttributeBase(reader.getAttributePrefix(i), reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributeValue(i), reader.getAttributeType(i)));
         }

         return atts.iterator();
      }
   }

   public static Iterator getNamespaces(XMLStreamReader reader) {
      if (reader.getNamespaceCount() == 0) {
         return Iterators.EMPTY_ITERATOR;
      } else {
         ArrayList ns = new ArrayList();

         for(int i = 0; i < reader.getNamespaceCount(); ++i) {
            String prefix = reader.getNamespacePrefix(i);
            if (prefix != null && !prefix.equals("")) {
               ns.add(new NamespaceBase(prefix, reader.getNamespaceURI(i)));
            } else {
               ns.add(new NamespaceBase(reader.getNamespaceURI(i)));
            }
         }

         return ns.iterator();
      }
   }

   public StartElement allocateStartElement(XMLStreamReader reader) throws XMLStreamException {
      String prefix = reader.getPrefix();
      String uri = reader.getNamespaceURI();
      if (prefix == null) {
         prefix = "";
      }

      if (uri == null) {
         uri = "";
      }

      NamespaceContext nsCtx = reader.getNamespaceContext();
      if (nsCtx instanceof ReadOnlyNamespaceContext) {
         ReadOnlyNamespaceContext roCtx = (ReadOnlyNamespaceContext)nsCtx;
         Map m = new HashMap();
         m.putAll(roCtx.internal);
         nsCtx = new ReadOnlyNamespaceContext(m);
      }

      return this.factory.createStartElement(prefix, uri, reader.getLocalName(), getAttributes(reader), getNamespaces(reader), (NamespaceContext)nsCtx);
   }

   public EndElement allocateEndElement(XMLStreamReader reader) throws XMLStreamException {
      String prefix = reader.getPrefix();
      String uri = reader.getNamespaceURI();
      if (prefix == null) {
         prefix = "";
      }

      if (uri == null) {
         uri = "";
      }

      return this.factory.createEndElement(prefix, uri, reader.getLocalName(), getNamespaces(reader));
   }

   public Characters allocateCharacters(XMLStreamReader reader) throws XMLStreamException {
      int start = reader.getTextStart();
      int length = reader.getTextLength();
      String result = new String(reader.getTextCharacters(), start, length);
      return reader.isWhiteSpace() ? this.factory.createSpace(result) : this.factory.createCharacters(result);
   }

   public Characters allocateCData(XMLStreamReader reader) throws XMLStreamException {
      return this.factory.createCData(reader.getText());
   }

   public Characters allocateSpace(XMLStreamReader reader) throws XMLStreamException {
      return this.factory.createSpace(reader.getText());
   }

   public EntityReference allocateEntityReference(XMLStreamReader reader) throws XMLStreamException {
      return this.factory.createEntityReference(reader.getLocalName(), (EntityDeclaration)null);
   }

   public ProcessingInstruction allocatePI(XMLStreamReader reader) throws XMLStreamException {
      return this.factory.createProcessingInstruction(reader.getPITarget(), reader.getPIData());
   }

   public Comment allocateComment(XMLStreamReader reader) throws XMLStreamException {
      return this.factory.createComment(reader.getText());
   }

   public StartDocument allocateStartDocument(XMLStreamReader reader) throws XMLStreamException {
      return this.allocateXMLDeclaration(reader);
   }

   public EndDocument allocateEndDocument(XMLStreamReader reader) throws XMLStreamException {
      return this.factory.createEndDocument();
   }

   public DTD allocateDTD(XMLStreamReader reader) throws XMLStreamException {
      return this.factory.createDTD(reader.getText());
   }

   public StartDocument allocateXMLDeclaration(XMLStreamReader reader) throws XMLStreamException {
      String encoding = reader.getCharacterEncodingScheme();
      String version = reader.getVersion();
      boolean standalone = reader.isStandalone();
      if (encoding != null && version != null && !standalone) {
         return this.factory.createStartDocument(encoding, version, standalone);
      } else if (version != null && encoding != null) {
         return this.factory.createStartDocument(encoding, version);
      } else {
         return encoding != null ? this.factory.createStartDocument(encoding) : this.factory.createStartDocument();
      }
   }

   public XMLEvent allocate(XMLStreamReader reader) throws XMLStreamException {
      switch (reader.getEventType()) {
         case 1:
            return this.allocateStartElement(reader);
         case 2:
            return this.allocateEndElement(reader);
         case 3:
            return this.allocatePI(reader);
         case 4:
            return this.allocateCharacters(reader);
         case 5:
            return this.allocateComment(reader);
         case 6:
            return this.allocateCharacters(reader);
         case 7:
            return this.allocateStartDocument(reader);
         case 8:
            return this.allocateEndDocument(reader);
         case 9:
            return this.allocateEntityReference(reader);
         case 10:
         default:
            throw new XMLStreamException("Unable to allocate event[" + reader.getEventType() + " , " + TypeNames.getName(reader.getEventType()) + "]");
         case 11:
            return this.allocateDTD(reader);
         case 12:
            return this.allocateCData(reader);
      }
   }

   public void allocate(XMLStreamReader reader, XMLEventConsumer consumer) throws XMLStreamException {
      consumer.add(this.allocate(reader));
   }

   public String toString() {
      return "weblogic.xml.stax.XMLEventAllocatorBase";
   }
}
