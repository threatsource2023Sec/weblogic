package weblogic.xml.stax;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import weblogic.xml.stax.util.TypeNames;

public class EventScanner {
   protected Reader reader;
   protected char currentChar;
   protected int currentLine = 0;
   private boolean readEndDocument = false;

   public EventScanner() {
   }

   public EventScanner(Reader reader) throws IOException {
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
      int type = TypeNames.getType(typeName);
      this.read(']');
      return type;
   }

   public EventState readStartElement() throws XMLStreamException, IOException {
      EventState state = new EventState(1);
      this.read('[');
      state.setName(this.readName());
      if (this.getChar() == '[') {
         List atts = this.readAttributes();
         Iterator i = atts.iterator();

         while(i.hasNext()) {
            Object obj = i.next();
            if (obj instanceof Namespace) {
               state.addNamespace(obj);
            } else {
               state.addAttribute(obj);
            }
         }
      }

      this.read(']');
      return state;
   }

   public EventState readEndElement() throws XMLStreamException, IOException {
      EventState state = new EventState(2);
      this.read('[');
      state.setName(this.readName());
      this.read(']');
      return state;
   }

   public EventState readProcessingInstruction() throws XMLStreamException, IOException {
      EventState state = new EventState(3);
      this.read('[');
      String name = this.readString(']');
      this.read(']');
      String s = null;
      if (this.getChar() == ',') {
         this.read(",[");
         s = this.readString(']');
         this.read(']');
      }

      state.setData(name);
      state.setExtraData(s);
      return state;
   }

   public EventState readCharacterData() throws XMLStreamException, IOException {
      EventState state = new EventState(4);
      this.read('[');
      state.setData(this.readString(']'));
      this.read(']');
      return state;
   }

   public EventState readCDATA() throws XMLStreamException, IOException {
      EventState state = new EventState(12);
      this.read('[');
      this.readString(']');
      this.read(']');
      return state;
   }

   public EventState readStartDocument() throws XMLStreamException, IOException {
      EventState state = new EventState(7);
      if (this.getChar() != ';') {
         this.read('[');
         this.read('[');
         String version = this.readString(']');
         this.read(']');
         this.read(',');
         this.read('[');
         String encoding = this.readString(']');
         this.read(']');
         this.read(']');
         state.setData(version);
         state.setExtraData(encoding);
      }

      return state;
   }

   public EventState readDTD() throws XMLStreamException, IOException {
      EventState state = new EventState(11);
      this.read('[');
      String dtd = this.readString(']');
      this.read(']');
      state.setData(dtd);
      return state;
   }

   public EventState readEndDocument() throws XMLStreamException {
      EventState state = new EventState(8);
      return state;
   }

   public EventState readComment() throws XMLStreamException, IOException {
      EventState state = new EventState(5);
      this.read('[');
      state.setData(this.readString(']'));
      this.read(']');
      return state;
   }

   public String getPrefix(String name) {
      int index = name.indexOf(58);
      return index == -1 ? null : name.substring(0, index);
   }

   public String getName(String name) {
      int index = name.indexOf(58);
      return index == -1 ? name : name.substring(index + 1);
   }

   public QName readName() throws XMLStreamException, IOException {
      this.read('[');
      QName n = this.readName(']');
      this.read(']');
      return n;
   }

   public QName readName(char delim) throws XMLStreamException, IOException {
      String uri = "";
      String prefix = "";
      if (this.getChar() == '\'') {
         this.read('\'');
         uri = this.readString('\'');
         this.read('\'');
         this.read(':');
      }

      String name = this.readString(delim);
      prefix = this.getPrefix(name);
      if (prefix == null) {
         prefix = "";
      }

      String localName = this.getName(name);
      return new QName(uri, localName, prefix);
   }

   public List readAttributes() throws XMLStreamException, IOException {
      List attributes = new ArrayList();

      while(this.getChar() == '[') {
         attributes.add(this.readAttribute());
      }

      return attributes;
   }

   public Attribute readAttribute() throws XMLStreamException, IOException {
      this.read('[');
      this.read('[');
      String type = this.readString(']');
      this.read(']');
      QName n = this.readName();
      this.read("=[");
      String value = this.readString(']');
      this.read(']');
      this.read(']');
      if (type.equals("ATTRIBUTE")) {
         return new AttributeBase(n, value);
      } else if (type.equals("DEFAULT")) {
         return new NamespaceBase(value);
      } else if (type.equals("NAMESPACE")) {
         return new NamespaceBase(n.getLocalPart(), value);
      } else {
         throw new XMLStreamException("Parser Error expected (ATTRIBUTE||DEFAULT|NAMESPACE");
      }
   }

   public EventState readEntityReference() throws XMLStreamException, IOException {
      EventState state = new EventState(9);
      this.read('[');
      state.setData(this.readString(']'));
      this.read(']');
      return state;
   }

   public EventState readSpace() throws XMLStreamException, IOException {
      EventState state = new EventState(6);
      this.read('[');
      String content = this.readString(']');
      this.read(']');
      state.setData(content);
      return state;
   }

   public EventState readElement() throws XMLStreamException, IOException {
      int type = this.readType();
      EventState state;
      switch (type) {
         case 1:
            state = this.readStartElement();
            break;
         case 2:
            state = this.readEndElement();
            break;
         case 3:
            state = this.readProcessingInstruction();
            break;
         case 4:
            state = this.readCharacterData();
            break;
         case 5:
            state = this.readComment();
            break;
         case 6:
            state = this.readSpace();
            break;
         case 7:
            state = this.readStartDocument();
            break;
         case 8:
            this.readEndDocument = true;
            state = this.readEndDocument();
            break;
         case 9:
            state = this.readEntityReference();
            break;
         case 10:
         default:
            throw new XMLStreamException("Attempt to read unknown element [" + type + "]");
         case 11:
            state = this.readDTD();
            break;
         case 12:
            state = this.readCDATA();
      }

      this.read(';');
      this.skipSpace();
      return state;
   }

   public boolean endDocumentIsPresent() {
      return this.readEndDocument;
   }

   public boolean hasNext() throws IOException {
      return this.reader.ready() && !this.readEndDocument;
   }

   public static void main(String[] args) throws Exception {
      EventScanner reader = new EventScanner(new FileReader(args[0]));

      while(reader.hasNext()) {
         System.out.println(reader.readElement());
      }

   }
}
