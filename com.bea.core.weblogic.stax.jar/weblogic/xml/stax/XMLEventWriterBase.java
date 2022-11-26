package weblogic.xml.stax;

import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventConsumer;
import weblogic.xml.stax.util.TypeNames;

public class XMLEventWriterBase implements XMLEventWriter, XMLEventConsumer {
   XMLStreamWriter writer;

   public XMLEventWriterBase(XMLStreamWriter writer) {
      this.writer = writer;
   }

   public void flush() throws XMLStreamException {
      this.writer.flush();
   }

   public void close() throws XMLStreamException {
      this.writer.close();
   }

   private void addStartElement(StartElement se) throws XMLStreamException {
      String prefix = se.getName().getPrefix();
      String namespace = se.getName().getNamespaceURI();
      String localName = se.getName().getLocalPart();
      this.writer.writeStartElement(prefix, localName, namespace);
      Iterator ni = se.getNamespaces();

      while(ni.hasNext()) {
         this.writeNamespace((Namespace)ni.next());
      }

      Iterator ai = se.getAttributes();

      while(ai.hasNext()) {
         this.writeAttribute((Attribute)ai.next());
      }

   }

   private void addEndElement(EndElement ee) throws XMLStreamException {
      String prefix = ee.getName().getPrefix();
      String namespace = ee.getName().getNamespaceURI();
      String localName = ee.getName().getLocalPart();
      this.writer.writeEndElement();
   }

   public void addCharacters(Characters cd) throws XMLStreamException {
      if (cd.isCData()) {
         this.writer.writeCData(cd.getData());
      } else {
         this.writer.writeCharacters(cd.getData());
      }

   }

   public void addEntityReference(EntityReference er) throws XMLStreamException {
      this.writer.writeEntityRef(er.getName());
   }

   public void addProcessingInstruction(ProcessingInstruction pi) throws XMLStreamException {
      this.writer.writeProcessingInstruction(pi.getTarget(), pi.getData());
   }

   public void addComment(Comment c) throws XMLStreamException {
      this.writer.writeComment(c.getText());
   }

   public void addStartDocument(StartDocument sd) throws XMLStreamException {
      String encoding = sd.getCharacterEncodingScheme();
      String version = sd.getVersion();
      boolean standalone = sd.isStandalone();
      this.writer.writeStartDocument(encoding, version);
   }

   public void addEndDocument(EndDocument ed) throws XMLStreamException {
   }

   private void writeAttribute(Attribute a) throws XMLStreamException {
      this.writer.writeAttribute(a.getName().getNamespaceURI(), a.getName().getLocalPart(), a.getValue());
   }

   public void addAttribute(Attribute a) throws XMLStreamException {
      this.writeAttribute(a);
   }

   public void writeNamespace(Namespace n) throws XMLStreamException {
      if (n.isDefaultNamespaceDeclaration()) {
         this.writer.writeDefaultNamespace(n.getNamespaceURI());
      } else {
         this.writer.writeNamespace(n.getPrefix(), n.getNamespaceURI());
      }

   }

   public void addNamespace(Namespace ns) throws XMLStreamException {
      this.writeNamespace(ns);
   }

   public void addDTD(DTD dtd) throws XMLStreamException {
      this.writer.writeDTD(dtd.getDocumentTypeDeclaration());
   }

   public void add(XMLEvent e) throws XMLStreamException {
      switch (e.getEventType()) {
         case 1:
            this.addStartElement((StartElement)e);
            break;
         case 2:
            this.addEndElement((EndElement)e);
            break;
         case 3:
            this.addProcessingInstruction((ProcessingInstruction)e);
            break;
         case 4:
            this.addCharacters((Characters)e);
            break;
         case 5:
            this.addComment((Comment)e);
            break;
         case 6:
         case 12:
         default:
            throw new XMLStreamException("Unable to add event[" + TypeNames.getName(e.getEventType()) + "]");
         case 7:
            this.addStartDocument((StartDocument)e);
            break;
         case 8:
            this.addEndDocument((EndDocument)e);
            break;
         case 9:
            this.addEntityReference((EntityReference)e);
            break;
         case 10:
            this.addAttribute((Attribute)e);
            break;
         case 11:
            this.addDTD((DTD)e);
            break;
         case 13:
            this.addNamespace((Namespace)e);
      }

   }

   public void add(XMLEventReader stream) throws XMLStreamException {
      while(stream.hasNext()) {
         this.add(stream.nextEvent());
      }

   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.writer.getPrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.writer.setPrefix(prefix, uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.writer.setDefaultNamespace(uri);
   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      this.writer.setNamespaceContext(context);
   }

   public NamespaceContext getNamespaceContext() {
      return this.writer.getNamespaceContext();
   }

   public static void main(String[] args) throws Exception {
      Writer w = new OutputStreamWriter(System.out);
      XMLEventWriterBase writer = new XMLEventWriterBase(new XMLWriterBase(w));
      XMLStreamReaderBase parser = new XMLStreamReaderBase();
      parser.setInput(new FileReader(args[0]));
      XMLEventReaderBase reader = new XMLEventReaderBase(parser);

      while(reader.hasNext()) {
         XMLEvent e = reader.nextEvent();
         System.out.println("about to add:[" + e + "];");
         writer.add(e);
      }

      writer.flush();
   }
}
