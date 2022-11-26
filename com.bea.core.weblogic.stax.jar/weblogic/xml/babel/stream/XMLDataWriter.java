package weblogic.xml.babel.stream;

import java.io.Writer;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.Space;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;

public class XMLDataWriter extends XMLWriter {
   private boolean begin = true;
   private boolean wasCharacterData = false;
   private boolean wasStartElement = true;

   public void write(StartElement element) throws XMLStreamException {
      this.write('<');
      this.write((XMLName)element.getName());
      if (this.writeElementNameSpaces) {
         this.write(element.getAttributesAndNamespaces());
      } else {
         this.write(element.getAttributes());
      }

      this.write('>');
   }

   public void write(EndElement element) throws XMLStreamException {
      this.write((String)"</");
      this.write((XMLName)element.getName());
      this.write((String)">");
   }

   public void tab(int numTab) throws XMLStreamException {
      for(int i = 0; i < numTab; ++i) {
         this.write(' ');
      }

   }

   public boolean write(XMLEvent e) throws XMLStreamException {
      switch (e.getType()) {
         case 2:
            if (!this.wasCharacterData && !this.begin || !this.wasStartElement) {
               this.write('\n');
               this.tab(this.elementLevel);
            }

            super.write(e);
            ++this.elementLevel;
            this.wasStartElement = true;
            this.wasCharacterData = false;
            this.begin = false;
            break;
         case 4:
            if (!this.wasCharacterData || !this.wasStartElement) {
               this.write('\n');
               this.tab(this.elementLevel - 1);
            }

            super.write(e);
            --this.elementLevel;
            this.wasStartElement = false;
            break;
         case 16:
            this.wasCharacterData = true;
            super.write((CharacterData)e);
            break;
         case 64:
            super.write(e);
            break;
         case 256:
            super.write(e);
            break;
         default:
            this.wasCharacterData = false;
            super.write(e);
      }

      return true;
   }

   public void writeEmpty(StartElement element) throws XMLStreamException {
      if (!this.wasCharacterData && !this.begin || !this.wasStartElement) {
         this.write('\n');
         this.tab(this.elementLevel);
      }

      super.writeEmpty(element);
      this.wasStartElement = true;
      this.wasCharacterData = false;
      this.begin = false;
   }

   public void write(Space space) throws XMLStreamException {
   }

   public void write(AttributeIterator attributes) throws XMLStreamException {
      while(attributes.hasNext()) {
         this.tab(this.elementLevel + 1);
         this.write((String)" ");
         this.write((Attribute)attributes.next());
         if (attributes.hasNext()) {
            this.write('\n');
         }
      }

   }

   public static XMLWriter getDebugWriter(Writer writer) throws XMLStreamException {
      XMLWriter xmlWriter = new XMLDataWriter();
      xmlWriter.setWriter(writer);
      xmlWriter.setWriteHeader(false);
      xmlWriter.setWriteElementNameSpaces(true);
      xmlWriter.setWriteAll(false);
      xmlWriter.setShowNamespaceBindings(false);
      xmlWriter.setNormalizeWhiteSpace(true);
      return xmlWriter;
   }
}
