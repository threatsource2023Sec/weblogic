package weblogic.xml.babel.stream;

import java.io.Writer;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.ChangePrefixMapping;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.Comment;
import weblogic.xml.stream.EndDocument;
import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.EndPrefixMapping;
import weblogic.xml.stream.EntityReference;
import weblogic.xml.stream.ProcessingInstruction;
import weblogic.xml.stream.Space;
import weblogic.xml.stream.StartDocument;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.StartPrefixMapping;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;

public class XMLEventWriter extends XMLWriter {
   public XMLEventWriter() {
   }

   public XMLEventWriter(Writer writer) {
      super(writer);
   }

   protected void writeType(XMLEvent element) throws XMLStreamException {
      this.write('[');
      this.write((String)element.getTypeAsString());
      this.write(']');
   }

   public void write(StartElement element) throws XMLStreamException {
      this.write('[');
      this.write(element.getName());
      this.write((String)"][");
      if (this.writeElementNameSpaces) {
         this.write(element.getAttributesAndNamespaces());
      } else {
         this.write(element.getAttributes());
      }

      this.write(']');
   }

   public void write(EndElement element) throws XMLStreamException {
      this.write('[');
      this.write(element.getName());
      this.write(']');
   }

   public void write(ProcessingInstruction event) throws XMLStreamException {
      this.write('[');
      if (event.getTarget() != null) {
         this.write((String)event.getTarget());
      }

      if (event.getData() != null) {
         this.write((String)("],[" + event.getData()));
      }

      this.write(']');
   }

   public void write(CharacterData event) throws XMLStreamException {
      this.write('[');
      if (event.hasContent()) {
         this.write((String)event.getContent());
      }

      this.write(']');
   }

   public void write(StartDocument event) throws XMLStreamException {
      if (this.writeHeader) {
         this.write((String)("[" + event.getVersion() + "]"));
         if (event.getCharacterEncodingScheme() != null) {
            this.write((String)(",[" + event.getCharacterEncodingScheme() + "]"));
         } else {
            this.write((String)",[]");
         }

         if (event.isStandalone()) {
            this.write((String)",[yes]");
         } else {
            this.write((String)",[no]");
         }

      }
   }

   public void write(EndDocument event) throws XMLStreamException {
   }

   public void write(Comment event) throws XMLStreamException {
      this.write('[');
      if (event.hasContent()) {
         event.getContent();
      }

      this.write(']');
   }

   protected void writeNamespace(String prefix, String uri) throws XMLStreamException {
      this.write((String)("[" + prefix + "," + uri + "]"));
   }

   public void write(StartPrefixMapping mapping) throws XMLStreamException {
      this.prefixMap.put(mapping.getPrefix(), mapping.getNamespaceUri());
      if (this.writeAll) {
         this.writeNamespace(mapping.getPrefix(), mapping.getNamespaceUri());
      }
   }

   public void write(ChangePrefixMapping mapping) throws XMLStreamException {
      this.prefixMap.put(mapping.getPrefix(), mapping.getNewNamespaceUri());
      if (this.writeAll) {
         this.write((String)"[");
         this.writeNamespace(mapping.getPrefix(), mapping.getOldNamespaceUri());
         this.write((String)",");
         this.writeNamespace(mapping.getPrefix(), mapping.getNewNamespaceUri());
         this.write((String)"]");
      }
   }

   public void write(EndPrefixMapping mapping) throws XMLStreamException {
      this.prefixMap.remove(mapping.getPrefix());
      if (this.writeAll) {
         this.write((String)("[" + mapping.getPrefix() + "]"));
      }
   }

   public void write(XMLName name) throws XMLStreamException {
      if (this.showNamespaceBindings && name.getNamespaceUri() != null) {
         this.write((String)("['" + name.getNamespaceUri() + "']:" + name.getQualifiedName()));
      } else {
         this.write((String)name.getQualifiedName());
      }

   }

   public void write(AttributeIterator attributes) throws XMLStreamException {
      while(attributes.hasNext()) {
         this.write((String)" ");
         this.write(attributes.next());
      }

   }

   public void write(Attribute attribute) throws XMLStreamException {
      this.write('[');
      this.write(attribute.getName());
      this.write((String)",");
      this.write((String)attribute.getValue());
      this.write(']');
   }

   public void write(EntityReference reference) throws XMLStreamException {
      this.write('[');
      this.write(reference.getName());
      this.write(']');
   }

   public void write(Space space) throws XMLStreamException {
      this.write('[');
      if (space.hasContent()) {
         this.write((String)space.getContent());
      }

      this.write(']');
   }

   public boolean write(XMLEvent e) throws XMLStreamException {
      this.writeType(e);
      switch (e.getType()) {
         case 2:
            this.write((StartElement)e);
            break;
         case 4:
            this.write((EndElement)e);
            break;
         case 8:
            this.write((ProcessingInstruction)e);
            break;
         case 16:
            this.write((CharacterData)e);
            break;
         case 32:
            this.write((Comment)e);
            break;
         case 64:
            this.write((Space)e);
            break;
         case 128:
            throw new XMLStreamException("Attempt to write a null element.");
         case 256:
            this.write((StartDocument)e);
            break;
         case 512:
            this.write((EndDocument)e);
            break;
         case 1024:
            this.write((StartPrefixMapping)e);
            break;
         case 2048:
            this.write((EndPrefixMapping)e);
            break;
         case 4096:
            this.write((ChangePrefixMapping)e);
            break;
         case 8192:
            this.write((EntityReference)e);
            break;
         default:
            throw new XMLStreamException("Attempt to write unknown element [" + e.getType() + "]");
      }

      this.write((String)";\n");
      return true;
   }

   public static XMLWriter getWriter(Writer writer) throws XMLStreamException {
      XMLWriter xmlWriter = new XMLEventWriter();
      xmlWriter.setWriter(writer);
      xmlWriter.setWriteHeader(true);
      xmlWriter.setWriteElementNameSpaces(true);
      xmlWriter.setWriteAll(true);
      xmlWriter.setShowNamespaceBindings(true);
      xmlWriter.setNormalizeWhiteSpace(false);
      return xmlWriter;
   }
}
