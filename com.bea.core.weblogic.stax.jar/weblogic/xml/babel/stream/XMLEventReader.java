package weblogic.xml.babel.stream;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.stream.Attribute;
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
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.ChangePrefixMappingEvent;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.CommentEvent;
import weblogic.xml.stream.events.ElementTypeNames;
import weblogic.xml.stream.events.EndDocumentEvent;
import weblogic.xml.stream.events.EndElementEvent;
import weblogic.xml.stream.events.EndPrefixMappingEvent;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.ProcessingInstructionEvent;
import weblogic.xml.stream.events.SpaceEvent;
import weblogic.xml.stream.events.StartDocumentEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.stream.events.StartPrefixMappingEvent;

public class XMLEventReader {
   protected Reader reader;
   protected char currentChar;
   protected int currentLine = 0;

   public XMLEventReader() {
   }

   public XMLEventReader(Reader reader) throws IOException {
      this.setReader(reader);
   }

   public void setReader(Reader reader) throws IOException {
      this.reader = reader;
      this.read();
      this.skipSpace();
   }

   protected String readString(char delim) throws IOException, XMLStreamException {
      StringBuffer buf = new StringBuffer();

      while(true) {
         while(this.getChar() != delim) {
            if (this.getChar() == '[' && delim == ']') {
               this.read();
               buf.append('[');
               if (this.getChar() != ']') {
                  buf.append(this.readString(']'));
               }

               buf.append(']');
               this.read(']');
            } else {
               buf.append(this.getChar());
               this.read();
            }
         }

         return buf.toString();
      }
   }

   protected char getChar() {
      return this.currentChar;
   }

   protected void skipSpace() throws IOException {
      while(this.currentChar == ' ' | this.currentChar == '\n' | this.currentChar == '\t' | this.currentChar == '\r') {
         this.read();
      }

   }

   protected char read() throws IOException {
      this.currentChar = (char)this.reader.read();
      if (this.currentChar == '\n') {
         ++this.currentLine;
      }

      return this.currentChar;
   }

   protected char read(char c) throws XMLStreamException, IOException {
      if (this.currentChar == c) {
         return this.read();
      } else {
         throw new XMLStreamException("Unexpected character '" + this.currentChar + "' , expected '" + c + "' at line " + this.currentLine);
      }
   }

   protected void read(String s) throws XMLStreamException, IOException {
      for(int i = 0; i < s.length(); ++i) {
         this.read(s.charAt(i));
      }

   }

   protected int readType() throws XMLStreamException, IOException {
      this.read('[');
      String typeName = this.readString(']');
      int type = ElementTypeNames.getType(typeName);
      this.read(']');
      return type;
   }

   public StartElement readStartElement() throws XMLStreamException, IOException {
      this.read('[');
      XMLName name = this.readName();
      StartElementEvent element = new StartElementEvent(name);
      this.read("][");
      List attributes = this.readAttributes();
      Iterator i = attributes.iterator();

      while(i.hasNext()) {
         element.addAttribute((Attribute)i.next());
      }

      this.read(']');
      return element;
   }

   public EndElement readEndElement() throws XMLStreamException, IOException {
      this.read('[');
      EndElement e = new EndElementEvent(this.readName());
      this.read(']');
      return e;
   }

   public ProcessingInstruction readProcessingInstruction() throws XMLStreamException, IOException {
      this.read('[');
      String name = this.readString(']');
      this.read(']');
      String s = null;
      if (this.getChar() == ',') {
         this.read(",[");
         s = this.readString(']');
         this.read(']');
      }

      return new ProcessingInstructionEvent(new Name(name), s);
   }

   public CharacterData readCharacterData() throws XMLStreamException, IOException {
      this.read('[');
      CharacterData cd = new CharacterDataEvent(this.readString(']'));
      this.read(']');
      return cd;
   }

   public StartDocument readStartDocument() throws XMLStreamException, IOException {
      if (this.getChar() != ';') {
         this.read('[');
         String version = this.readString(']');
         this.read(']');
         this.read(',');
         this.read('[');
         String encoding = this.readString(']');
         this.read(']');
         this.read(',');
         this.read('[');
         String standalone = this.readString(']');
         this.read(']');
         StartDocumentEvent e = new StartDocumentEvent();
         e.setVersion(version);
         e.setEncoding(encoding);
         e.setStandalone(standalone);
         return e;
      } else {
         return new StartDocumentEvent();
      }
   }

   public EndDocument readEndDocument() throws XMLStreamException {
      return new EndDocumentEvent();
   }

   public Comment readComment() throws XMLStreamException, IOException {
      this.read('[');
      CommentEvent e = new CommentEvent(this.readString(']'));
      this.read(']');
      return e;
   }

   public StartPrefixMapping readStartPrefixMapping() throws XMLStreamException, IOException {
      this.read('[');
      String prefix = this.readString(',');
      this.read(',');
      String uri = this.readString(']');
      this.read(']');
      return new StartPrefixMappingEvent(prefix, uri);
   }

   public ChangePrefixMapping readChangePrefixMapping() throws XMLStreamException, IOException {
      this.read('[');
      this.read('[');
      String prefix = this.readString(',');
      this.read(',');
      String olduri = this.readString(']');
      this.read(']');
      this.read(',');
      this.read('[');
      this.readString(',');
      this.read(',');
      String newuri = this.readString(']');
      this.read(']');
      this.read(']');
      return new ChangePrefixMappingEvent(olduri, newuri, prefix);
   }

   public EndPrefixMapping readEndPrefixMapping() throws XMLStreamException, IOException {
      this.read('[');
      String prefix = this.readString(']');
      this.read(']');
      return new EndPrefixMappingEvent(prefix);
   }

   public String getPrefix(String name) {
      int index = name.indexOf(58);
      return index == -1 ? null : name.substring(0, index);
   }

   public String getName(String name) {
      int index = name.indexOf(58);
      return index == -1 ? name : name.substring(index + 1);
   }

   public XMLName readName() throws XMLStreamException, IOException {
      return this.readName(']');
   }

   public XMLName readName(char delim) throws XMLStreamException, IOException {
      String uri = null;
      String prefix = null;
      if (this.getChar() == '[') {
         this.read();
         this.read('\'');
         uri = this.readString('\'');
         this.read('\'');
         this.read(']');
         this.read(':');
      }

      String name = this.readString(delim);
      prefix = this.getPrefix(name);
      String localName = this.getName(name);
      return new Name(uri, localName, prefix);
   }

   public List readAttributes() throws XMLStreamException, IOException {
      List attributes = new ArrayList();

      while(this.getChar() == ' ') {
         this.read();
         attributes.add(this.readAttribute());
      }

      return attributes;
   }

   public Attribute readAttribute() throws XMLStreamException, IOException {
      this.read('[');
      XMLName name = this.readName(',');
      this.read(",");
      String value = this.readString(']');
      this.read(']');
      return new AttributeImpl(name, value, "CDATA");
   }

   public EntityReference readEntityReference() throws XMLStreamException, IOException {
      this.read('[');
      this.readString(']');
      this.read(']');
      return null;
   }

   public Space readSpace() throws XMLStreamException, IOException {
      this.read('[');
      String content = this.readString(']');
      this.read(']');
      return new SpaceEvent(content);
   }

   public XMLEvent readElement() throws XMLStreamException, IOException {
      int type = this.readType();
      Object e;
      switch (type) {
         case 2:
            e = this.readStartElement();
            break;
         case 4:
            e = this.readEndElement();
            break;
         case 8:
            e = this.readProcessingInstruction();
            break;
         case 16:
            e = this.readCharacterData();
            break;
         case 32:
            e = this.readComment();
            break;
         case 64:
            e = this.readSpace();
            break;
         case 128:
            throw new XMLStreamException("Attempt to read a null element.");
         case 256:
            e = this.readStartDocument();
            break;
         case 512:
            e = this.readEndDocument();
            break;
         case 1024:
            e = this.readStartPrefixMapping();
            break;
         case 2048:
            e = this.readEndPrefixMapping();
            break;
         case 4096:
            e = this.readChangePrefixMapping();
            break;
         case 8192:
            e = this.readEntityReference();
            break;
         default:
            throw new XMLStreamException("Attempt to read unknown element [" + type + "]");
      }

      this.read(';');
      this.skipSpace();
      return (XMLEvent)e;
   }

   public boolean hasNext() throws IOException {
      return this.reader.ready();
   }

   public static void main(String[] args) throws Exception {
      XMLEventReader reader = new XMLEventReader(new FileReader(args[0]));

      while(reader.hasNext()) {
         System.out.print(reader.readElement());
      }

   }
}
