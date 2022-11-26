package weblogic.xml.xmlnode;

import java.io.IOException;
import weblogic.utils.AssertionError;
import weblogic.xml.babel.stream.XMLWriter;
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

public class XMLNodeWriter extends XMLWriter {
   private XMLNode root;
   private XMLNode current;

   public XMLNodeWriter(XMLNode root) {
      this.root = root;
      this.current = root;
      this.init();
   }

   public void reset() {
   }

   private XMLNode getCurrent() {
      if (this.current == null) {
         throw new AssertionError("Cant find current element, root=" + this.root);
      } else {
         return this.current;
      }
   }

   public void write(StartElement element) throws XMLStreamException {
      try {
         XMLName name = element.getName();
         XMLNode newNode = this.getCurrent().addChild(name.getLocalName(), name.getPrefix(), name.getNamespaceUri());
         if (this.writeElementNameSpaces) {
            newNode.readNamespace(element);
         }

         newNode.readAttributes(element);
         this.current = newNode;
      } catch (IOException var4) {
         throw new XMLStreamException(var4);
      }
   }

   public void write(EndElement element) throws XMLStreamException {
      this.current = this.getCurrent().getParent();
   }

   public void write(ProcessingInstruction event) throws XMLStreamException {
   }

   public void write(CharacterData event) throws XMLStreamException {
      this.getCurrent().addText(event.getContent());
   }

   public void write(StartDocument event) throws XMLStreamException {
      this.reset();
   }

   public void write(EndDocument event) throws XMLStreamException {
   }

   public void write(Comment event) throws XMLStreamException {
   }

   public void write(StartPrefixMapping mapping) throws XMLStreamException {
   }

   public void write(ChangePrefixMapping mapping) throws XMLStreamException {
   }

   public void write(EndPrefixMapping mapping) throws XMLStreamException {
   }

   public void write(EntityReference reference) throws XMLStreamException {
   }

   public void write(Space space) throws XMLStreamException {
      this.getCurrent().addText(space.getContent());
   }

   public void flush() throws XMLStreamException {
   }

   public boolean write(XMLEvent e) throws XMLStreamException {
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

      return true;
   }

   protected void init() {
      this.setWriteHeader(true);
      this.setWriteElementNameSpaces(true);
      this.setWriteAll(false);
      this.setShowNamespaceBindings(false);
      this.setNormalizeWhiteSpace(false);
      this.reset();
   }
}
