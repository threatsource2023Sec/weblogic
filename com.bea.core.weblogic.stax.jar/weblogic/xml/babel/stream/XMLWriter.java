package weblogic.xml.babel.stream;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import weblogic.xml.babel.reader.XmlChars;
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
import weblogic.xml.stream.events.StartDocumentEvent;

public class XMLWriter {
   protected Writer writer;
   protected int elementLevel = 0;
   protected Map prefixMap = new HashMap();
   protected boolean writeElementNameSpaces = false;
   protected boolean writeAll = false;
   protected boolean showNamespaceBindings = false;
   protected boolean normalizeWhiteSpace = false;
   protected boolean writeHeader = true;

   public XMLWriter() {
   }

   public XMLWriter(Writer writer) {
      this.writer = writer;
   }

   public void setWriter(Writer writer) {
      this.writer = writer;
   }

   public void setWriteElementNameSpaces(boolean val) {
      this.writeElementNameSpaces = val;
   }

   public void setWriteAll(boolean val) {
      this.writeAll = val;
   }

   public void setNormalizeWhiteSpace(boolean val) {
      this.normalizeWhiteSpace = val;
   }

   public void setWriteHeader(boolean val) {
      this.writeHeader = val;
   }

   public void setShowNamespaceBindings(boolean val) {
      this.showNamespaceBindings = val;
   }

   protected void write(String s) throws XMLStreamException {
      try {
         this.writer.write(s);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   protected void write(char c) throws XMLStreamException {
      try {
         this.writer.write(c);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   private void write(char[] c) throws XMLStreamException {
      try {
         this.writer.write(c);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void flush() throws XMLStreamException {
      try {
         this.writer.flush();
      } catch (IOException var2) {
         throw new XMLStreamException(var2);
      }
   }

   public void write(StartElement element) throws XMLStreamException {
      ++this.elementLevel;
      this.write('<');
      this.write(element.getName());
      if (this.writeElementNameSpaces) {
         this.write(element.getAttributesAndNamespaces());
      } else {
         this.write(element.getAttributes());
      }

      this.write('>');
   }

   public void writeEmpty(StartElement element) throws XMLStreamException {
      this.write('<');
      this.write(element.getName());
      if (this.writeElementNameSpaces) {
         this.write(element.getAttributesAndNamespaces());
      } else {
         this.write(element.getAttributes());
      }

      this.write("/>");
   }

   public void write(EndElement element) throws XMLStreamException {
      --this.elementLevel;
      this.write("</");
      this.write(element.getName());
      this.write(">");
   }

   public void write(ProcessingInstruction event) throws XMLStreamException {
      this.write("<?");
      if (event.getTarget() != null) {
         this.write(event.getTarget());
      }

      if (event.getData() != null) {
         this.write(" " + event.getData());
      }

      this.write("?>");
   }

   public void write(CharacterData event) throws XMLStreamException {
      if (event.hasContent()) {
         this.writeCharacters(event.getContent(), false);
      }

   }

   public void write(StartDocument event) throws XMLStreamException {
      if (this.writeHeader) {
         if (event instanceof StartDocumentEvent) {
            StartDocumentEvent e = (StartDocumentEvent)event;
            this.write("<?xml version=\"" + e.getVersion() + "\"");
            if (e.getCharacterEncodingScheme() != null && e.encodingSet()) {
               this.write(" encoding=\"" + e.getCharacterEncodingScheme() + "\"");
            }

            if (e.standaloneSet()) {
               if (e.isStandalone()) {
                  this.write(" standalone=\"yes\"");
               } else {
                  this.write(" standalone=\"no\"");
               }
            }

            this.write("?>");
         } else {
            this.write("<?xml version=\"" + event.getVersion() + "\"");
            if (event.getCharacterEncodingScheme() != null) {
               this.write(" encoding=\"" + event.getCharacterEncodingScheme() + "\"");
            }

            if (event.isStandalone()) {
               this.write(" standalone=\"yes\"");
            } else {
               this.write(" standalone=\"no\"");
            }

            this.write("?>");
         }

      }
   }

   public void write(EndDocument event) throws XMLStreamException {
      if (this.writeAll) {
         this.write("<?END_DOCUMENT?>");
      }
   }

   public void write(Comment event) throws XMLStreamException {
      this.write("<!--");
      if (event.hasContent()) {
         this.writeCharacters(event.getContent(), false);
      }

      this.write("-->");
   }

   protected void writeNamespace(String prefix, String uri) throws XMLStreamException {
      this.write("['" + prefix + "']=['" + uri + "']");
   }

   public boolean inScope(String prefix) {
      return this.prefixMap.get(prefix) != null;
   }

   public void write(StartPrefixMapping mapping) throws XMLStreamException {
      this.prefixMap.put(mapping.getPrefix(), mapping.getNamespaceUri());
      if (this.writeAll) {
         this.write("<?START_PREFIX_MAPPING ");
         this.writeNamespace(mapping.getPrefix(), mapping.getNamespaceUri());
         this.write("?>");
      }
   }

   public void write(ChangePrefixMapping mapping) throws XMLStreamException {
      this.prefixMap.put(mapping.getPrefix(), mapping.getNewNamespaceUri());
      if (this.writeAll) {
         this.write("<?CHANGE_PREFIX_MAPPING ");
         this.writeNamespace(mapping.getPrefix(), mapping.getOldNamespaceUri());
         this.write(" ");
         this.writeNamespace(mapping.getPrefix(), mapping.getNewNamespaceUri());
         this.write(">?");
      }
   }

   public void write(EndPrefixMapping mapping) throws XMLStreamException {
      this.prefixMap.remove(mapping.getPrefix());
      if (this.writeAll) {
         this.write("<?END_PREFIX_MAPPING " + mapping.getPrefix() + "?>");
      }
   }

   public void write(XMLName name) throws XMLStreamException {
      if (name != null) {
         if (this.showNamespaceBindings && name.getNamespaceUri() != null) {
            this.write("['" + name.getNamespaceUri() + "']:" + name.getQualifiedName());
         } else {
            this.write(name.getQualifiedName());
         }

      }
   }

   public void write(AttributeIterator attributes) throws XMLStreamException {
      while(attributes.hasNext()) {
         this.write(" ");
         this.write(attributes.next());
      }

   }

   public void write(Attribute attribute) throws XMLStreamException {
      this.write(attribute.getName());
      this.write("=\"");
      this.writeCharacters(attribute.getValue(), true);
      this.write('"');
   }

   public void write(EntityReference reference) throws XMLStreamException {
      this.write("&" + reference.getName() + ";");
   }

   public void write(Space space) throws XMLStreamException {
      if (this.normalizeWhiteSpace) {
         this.write(' ');
      } else if (space.hasContent()) {
         this.write(space.getContent());
      }

   }

   protected void writeCharacters(String characters, boolean isAttributeValue) throws XMLStreamException {
      if (characters != null && characters.length() != 0) {
         boolean fastPath = true;
         int i = 0;

         for(int len = characters.length(); i < len; ++i) {
            switch (characters.charAt(i)) {
               case '"':
               case '&':
               case '<':
               case '>':
                  fastPath = false;
            }

            if (!XmlChars.isChar(characters.charAt(i))) {
               fastPath = false;
               break;
            }
         }

         if (fastPath) {
            this.write(characters);
         } else {
            this.slowWriteCharacters(characters, isAttributeValue);
         }

      }
   }

   private void slowWriteCharacters(String chars, boolean isAttributeValue) throws XMLStreamException {
      int i = 0;

      for(int len = chars.length(); i < len; ++i) {
         char c = chars.charAt(i);
         switch (c) {
            case '"':
               if (isAttributeValue) {
                  this.write("&quot;");
               } else {
                  this.write('"');
               }
               break;
            case '&':
               this.write("&amp;");
               break;
            case '<':
               this.write("&lt;");
               break;
            case '>':
               this.write("&gt;");
               break;
            default:
               if (!XmlChars.isChar(c)) {
                  this.write("&#");
                  this.write(Integer.toString(c));
                  this.write(';');
               } else {
                  this.write(c);
               }
         }
      }

   }

   public boolean write(XMLEvent e) throws XMLStreamException {
      switch (e.getType()) {
         case 2:
            this.write((StartElement)e);
            return true;
         case 4:
            this.write((EndElement)e);
            return true;
         case 8:
            this.write((ProcessingInstruction)e);
            return true;
         case 16:
            this.write((CharacterData)e);
            return true;
         case 32:
            this.write((Comment)e);
            return true;
         case 64:
            this.write((Space)e);
            return true;
         case 128:
            throw new XMLStreamException("Attempt to write a null element.");
         case 256:
            this.write((StartDocument)e);
            return true;
         case 512:
            this.write((EndDocument)e);
            return true;
         case 1024:
            this.write((StartPrefixMapping)e);
            return true;
         case 2048:
            this.write((EndPrefixMapping)e);
            return true;
         case 4096:
            this.write((ChangePrefixMapping)e);
            return true;
         case 8192:
            this.write((EntityReference)e);
            return true;
         default:
            throw new XMLStreamException("Attempt to write unknown element [" + e.getType() + "]");
      }
   }

   public static XMLWriter getDebugWriter(Writer writer) throws XMLStreamException {
      XMLWriter xmlWriter = new XMLWriter();
      xmlWriter.setWriter(writer);
      xmlWriter.setWriteElementNameSpaces(true);
      xmlWriter.setWriteAll(true);
      xmlWriter.setShowNamespaceBindings(true);
      xmlWriter.setNormalizeWhiteSpace(true);
      return xmlWriter;
   }

   public static XMLWriter getSymmetricWriter(Writer writer) throws XMLStreamException {
      XMLWriter xmlWriter = new XMLWriter();
      xmlWriter.setWriter(writer);
      xmlWriter.setWriteHeader(true);
      xmlWriter.setWriteElementNameSpaces(true);
      xmlWriter.setWriteAll(false);
      xmlWriter.setShowNamespaceBindings(false);
      xmlWriter.setNormalizeWhiteSpace(false);
      return xmlWriter;
   }
}
