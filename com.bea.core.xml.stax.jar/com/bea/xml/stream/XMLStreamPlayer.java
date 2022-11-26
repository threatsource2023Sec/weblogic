package com.bea.xml.stream;

import com.bea.xml.stream.util.NamespaceContextImpl;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;

public class XMLStreamPlayer implements XMLStreamReader {
   EventState state;
   EventScanner scanner;
   NamespaceContextImpl context = new NamespaceContextImpl();

   public XMLStreamPlayer() {
   }

   public XMLStreamPlayer(InputStream stream) {
      try {
         this.scanner = new EventScanner(new InputStreamReader(stream));
         this.next();
         if (this.getEventType() == 7) {
            String encoding = this.getCharacterEncodingScheme();
            this.scanner = new EventScanner(new InputStreamReader(stream, encoding));
         }

      } catch (Exception var3) {
         throw new IllegalArgumentException("Unable to instantiate the XMLStreamPlayer" + var3.getMessage());
      }
   }

   public XMLStreamPlayer(Reader reader) {
      try {
         this.scanner = new EventScanner(reader);
         this.next();
      } catch (Exception var3) {
         System.out.println(var3);
      }

   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return null;
   }

   public int next() throws XMLStreamException {
      try {
         if (!this.scanner.hasNext()) {
            this.state = null;
            return -1;
         } else {
            this.state = this.scanner.readElement();
            if (this.isStartElement()) {
               this.context.openScope();

               for(int i = 0; i < this.getNamespaceCount(); ++i) {
                  this.context.bindNamespace(this.getNamespacePrefix(i), this.getNamespaceURI(i));
               }
            } else if (this.isEndElement() && this.context.getDepth() > 0) {
               this.context.closeScope();
            }

            return this.state.getType();
         }
      } catch (Exception var2) {
         System.out.println(var2);
         var2.printStackTrace();
         throw new XMLStreamException(var2.getMessage(), var2);
      }
   }

   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
   }

   public String getElementText() throws XMLStreamException {
      StringBuffer buf = new StringBuffer();
      if (this.getEventType() != 1) {
         throw new XMLStreamException("Precondition for readText is getEventType() == START_ELEMENT");
      } else {
         while(this.next() != 8) {
            if (this.isStartElement()) {
               throw new XMLStreamException("Unexpected Element start");
            }

            if (this.isCharacters()) {
               buf.append(this.getText());
            }

            if (this.isEndElement()) {
               return buf.toString();
            }
         }

         throw new XMLStreamException("Unexpected end of Document");
      }
   }

   public int nextTag() throws XMLStreamException {
      do {
         if (this.next() == 8) {
            throw new XMLStreamException("Unexpected end of Document");
         }

         if (this.isCharacters() && !this.isWhiteSpace()) {
            throw new XMLStreamException("Unexpected text");
         }
      } while(!this.isStartElement() && !this.isEndElement());

      return this.getEventType();
   }

   public boolean hasNext() throws XMLStreamException {
      try {
         return this.state != null && this.state.getType() != 8;
      } catch (Exception var2) {
         throw new XMLStreamException(var2);
      }
   }

   public void close() throws XMLStreamException {
   }

   public String getNamespaceURI(String prefix) {
      return this.context.getNamespaceURI(prefix);
   }

   private Attribute getAttributeInternal(int index) {
      return (Attribute)this.state.getAttributes().get(index);
   }

   private Attribute getNamespaceInternal(int index) {
      return (Attribute)this.state.getNamespaces().get(index);
   }

   public boolean isStartElement() {
      return (this.getEventType() & 1) != 0;
   }

   public boolean isEndElement() {
      return (this.getEventType() & 2) != 0;
   }

   public boolean isCharacters() {
      return (this.getEventType() & 4) != 0;
   }

   public boolean isWhiteSpace() {
      return false;
   }

   public String getAttributeValue(String namespaceUri, String localName) {
      for(int i = 0; i < this.getAttributeCount(); ++i) {
         Attribute a = this.getAttributeInternal(i);
         if (localName.equals(a.getName().getLocalPart())) {
            if (namespaceUri == null) {
               return a.getValue();
            }

            if (namespaceUri.equals(a.getName().getNamespaceURI())) {
               return a.getValue();
            }
         }
      }

      return null;
   }

   public int getAttributeCount() {
      return this.isStartElement() ? this.state.getAttributes().size() : 0;
   }

   public QName getAttributeName(int index) {
      return new QName(this.getAttributeNamespace(index), this.getAttributeLocalName(index), this.getAttributePrefix(index));
   }

   public String getAttributeNamespace(int index) {
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getName().getNamespaceURI();
   }

   public String getAttributeLocalName(int index) {
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getName().getLocalPart();
   }

   public String getAttributePrefix(int index) {
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getName().getPrefix();
   }

   public String getAttributeType(int index) {
      return "CDATA";
   }

   public String getAttributeValue(int index) {
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getValue();
   }

   public boolean isAttributeSpecified(int index) {
      return false;
   }

   public int getNamespaceCount() {
      return this.isStartElement() ? this.state.getNamespaces().size() : 0;
   }

   public String getNamespacePrefix(int index) {
      Attribute a = this.getNamespaceInternal(index);
      return a == null ? null : a.getName().getLocalPart();
   }

   public String getNamespaceURI(int index) {
      Attribute a = this.getNamespaceInternal(index);
      return a == null ? null : a.getValue();
   }

   public NamespaceContext getNamespaceContext() {
      return this.context;
   }

   public XMLStreamReader subReader() throws XMLStreamException {
      return null;
   }

   public int getEventType() {
      return this.state == null ? 8 : this.state.getType();
   }

   public String getText() {
      return this.state.getData();
   }

   public Reader getTextStream() {
      throw new UnsupportedOperationException();
   }

   public char[] getTextCharacters() {
      return this.state.getData().toCharArray();
   }

   public int getTextCharacters(int src, char[] target, int targetStart, int length) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }

   public int getTextStart() {
      return 0;
   }

   public int getTextLength() {
      return this.state.getData().length();
   }

   public String getEncoding() {
      return this.state.getData();
   }

   public boolean hasText() {
      return 0 != (this.getEventType() & 15);
   }

   public Location getLocation() {
      return null;
   }

   public QName getName() {
      return new QName(this.getNamespaceURI(), this.getLocalName(), this.getPrefix());
   }

   public String getLocalName() {
      return this.state.getLocalName();
   }

   public boolean hasName() {
      return 0 != (this.getEventType() & 11);
   }

   public String getNamespaceURI() {
      return this.state.getNamespaceURI();
   }

   public String getPrefix() {
      return this.state.getPrefix();
   }

   public String getVersion() {
      return "1.0";
   }

   public boolean isStandalone() {
      return true;
   }

   public boolean standaloneSet() {
      return false;
   }

   public String getCharacterEncodingScheme() {
      return null;
   }

   public String getPITarget() {
      return this.state.getData();
   }

   public String getPIData() {
      return this.state.getExtraData();
   }

   public boolean endDocumentIsPresent() {
      return this.scanner.endDocumentIsPresent();
   }

   public static void main(String[] args) throws Exception {
      XMLStreamReader reader = new XMLStreamPlayer(new FileReader(args[0]));
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
      XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(System.out);
      ReaderToWriter rtow = new ReaderToWriter(xmlw);

      while(reader.hasNext()) {
         rtow.write(reader);
         reader.next();
      }

      xmlw.flush();
   }
}
