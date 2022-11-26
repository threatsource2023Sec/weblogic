package weblogic.jms.utils.xml.stax.bin;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.utils.xml.stax.utils.NamespaceContextImpl;

public final class BinaryXMLStreamWriter implements XMLStreamWriter {
   private static final String DEFAULT_NS = "";
   private static final int MAX_DICT_SIZE = 1024;
   private static final int MIN_DICT_ENTRY_SIZE = 8;
   final DataOutput dos;
   private final HashMap dict = new HashMap();
   private char[] charBuf;
   private boolean writingEmptyElement;
   private NamespaceContextImpl context = new NamespaceContextImpl();
   private HashSet needToWrite;
   private boolean startElementOpened;

   public BinaryXMLStreamWriter(OutputStream out) throws XMLStreamException {
      this.dos = (DataOutput)(out instanceof DataOutput ? (DataOutput)out : new DataOutputStream(out));

      try {
         this.dos.write(BinaryXMLConstants.HEADERV2_BYTES);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.openStartElement();
      int localNameID = this.abbrevString(localName);
      this.writeInt(1);
      this.writeInt(localNameID);
      this.writeInt(0);
   }

   public void writeStartElement(String uri, String localName) throws XMLStreamException {
      this.openStartElement();
      int localNameID = this.abbrevString(localName);
      int uriID = this.abbrevString(uri);
      this.writeInt(1);
      this.writeInt(localNameID);
      this.writeInt(uriID);
   }

   public void writeStartElement(String prefix, String localName, String uri) throws XMLStreamException {
      this.openStartElement();
      this.writeNamespace(prefix, uri);
      this.writeStartElement(uri, localName);
   }

   public void writeEmptyElement(String uri, String localName) throws XMLStreamException {
      this.writeStartElement(uri, localName);
      this.writingEmptyElement = true;
   }

   public void writeEmptyElement(String prefix, String localName, String uri) throws XMLStreamException {
      this.writeStartElement(prefix, localName, uri);
      this.writingEmptyElement = true;
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.writeStartElement(localName);
      this.writingEmptyElement = true;
   }

   public void writeEndElement() throws XMLStreamException {
      this.writingEmptyElement = false;
      this.context.closeScope(false);
      this.writeInt(4);
   }

   public void writeEndDocument() throws XMLStreamException {
      this.closeStartElement();
      this.writeInt(6);
   }

   public void close() throws XMLStreamException {
      this.writeInt(62);
      this.dict.clear();
   }

   public void flush() throws XMLStreamException {
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      int localNameID = this.abbrevString(localName);
      this.writeInt(2);
      this.writeInt(localNameID);
      this.writeInt(0);
      this.writePCDATA(value);
      this.writeInt(5);
   }

   public void writeAttribute(String prefix, String uri, String localName, String value) throws XMLStreamException {
      this.writeNamespace(prefix, uri);
      this.writeAttribute(uri, localName, value);
   }

   public void writeAttribute(String uri, String localName, String value) throws XMLStreamException {
      int localNameID = this.abbrevString(localName);
      int uriID = this.abbrevString(uri);
      this.writeInt(2);
      this.writeInt(localNameID);
      this.writeInt(uriID);
      this.writePCDATA(value);
      this.writeInt(5);
   }

   public void writeNamespace(String prefix, String uri) throws XMLStreamException {
      int prefixID = this.abbrevString(prefix);
      int uriID = this.abbrevString(uri);
      this.writeInt(8);
      this.writeInt(prefixID);
      this.writeInt(uriID);
   }

   public void writeDefaultNamespace(String uri) throws XMLStreamException {
      this.writeNamespace("", uri);
      this.setPrefix("", uri);
   }

   public void writeComment(String value) throws XMLStreamException {
      this.closeStartElement();
      this.writeInt(7);
      this.writeString(value);
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.writeProcessingInstruction(target, "");
   }

   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
      this.closeStartElement();
      int targetID = this.abbrevString(target);
      this.writeInt(9);
      this.writeInt(targetID);
      this.writeInt(0);
      this.writeString(data);
   }

   public void writeCData(String s) throws XMLStreamException {
      this.closeStartElement();
      this.writeCharacters(s);
   }

   public void writeDTD(String s) throws XMLStreamException {
      if (s.startsWith("<!DOCTYPE") && s.endsWith(">")) {
         s = s.substring(9, s.length() - 1).trim();
         int i1 = s.indexOf(" ");
         String name;
         if (i1 == -1) {
            name = s;
         } else {
            name = s.substring(0, i1);
         }

         this.writeInt(50);
         this.writeString(name);
         this.writeString("");
         this.writeString("");
      } else {
         throw new XMLStreamException("Invalid DTD");
      }
   }

   public void writeEntityRef(String s) throws XMLStreamException {
      this.closeStartElement();
      throw new AssertionError("NYI");
   }

   public void writeStartDocument() throws XMLStreamException {
      this.writeStartDocument("", "");
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      this.writeStartDocument("", version);
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      this.writeInt(3);
      this.writeString(version);
      this.writeString(encoding);
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.closeStartElement();
      this.writeInt(10);
      this.writeString(text);
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.closeStartElement();
      this.writeInt(10);
      this.writeChars(text, start, len);
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.context.getPrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.needToWrite(uri);
      this.context.bindDefaultNameSpace(uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.needToWrite(uri);
      this.context.bindDefaultNameSpace(uri);
   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      if (context == null) {
         throw new NullPointerException("The namespace context may not be null");
      } else {
         this.context = new NamespaceContextImpl(context);
      }
   }

   public NamespaceContext getNamespaceContext() {
      return this.context;
   }

   public Object getProperty(String s) throws IllegalArgumentException {
      return null;
   }

   private final void closeStartElement() throws XMLStreamException {
      if (this.startElementOpened) {
         this.flushNamespace();
         if (this.writingEmptyElement) {
            this.writeEndElement();
         }

         this.startElementOpened = false;
      }

   }

   private final void openStartElement() throws XMLStreamException {
      if (this.startElementOpened) {
         this.flushNamespace();
         if (this.writingEmptyElement) {
            this.writeEndElement();
         }
      }

      this.startElementOpened = true;
      this.context.openScope();
   }

   private final void needToWrite(String uri) {
      if (this.needToWrite == null) {
         this.needToWrite = new HashSet();
      }

      this.needToWrite.add(uri);
   }

   protected final void flushNamespace() throws XMLStreamException {
      if (this.needToWrite != null && this.needToWrite.size() != 0) {
         Iterator i = this.needToWrite.iterator();

         while(i.hasNext()) {
            String uri = (String)i.next();
            String prefix = this.context.getPrefix(uri);
            if (prefix == null) {
               throw new XMLStreamException("Unable to default prefix with uri:" + uri);
            }

            if (uri == null) {
               throw new XMLStreamException("Attempt to write a null uri.");
            }

            this.writeNamespace(prefix, uri);
         }

         this.needToWrite.clear();
      }
   }

   void writeString(String text) throws XMLStreamException {
      int len = text.length();
      if (this.charBuf == null || len > this.charBuf.length) {
         this.charBuf = new char[len * 2];
      }

      text.getChars(0, len, this.charBuf, 0);
      this.writeChars(this.charBuf, 0, len);
   }

   private void writeChars(char[] text, int start, int len) throws XMLStreamException {
      int utflen = 0;

      int c;
      for(int i = start; i < start + len; ++i) {
         c = text[i];
         if (c >= 1 && c <= 127) {
            ++utflen;
         } else if (c > 2047) {
            utflen += 3;
         } else {
            utflen += 2;
         }
      }

      this.writeInt(utflen);
      byte[] bytearr = new byte[utflen];
      c = 0;

      for(int i = start; i < start + len; ++i) {
         char c = text[i];
         if (c >= 1 && c <= 127) {
            bytearr[c++] = (byte)c;
         } else if (c > 2047) {
            bytearr[c++] = (byte)(224 | c >> 12 & 15);
            bytearr[c++] = (byte)(128 | c >> 6 & 63);
            bytearr[c++] = (byte)(128 | c >> 0 & 63);
         } else {
            bytearr[c++] = (byte)(192 | c >> 6 & 31);
            bytearr[c++] = (byte)(128 | c >> 0 & 63);
         }
      }

      try {
         this.dos.write(bytearr);
      } catch (IOException var9) {
         throw new XMLStreamException(var9);
      }
   }

   void writePCDATA(String value) throws XMLStreamException {
      int length = value.length();
      if (length > 8) {
         int pcdataID = this.abbrevString(value);
         this.writeInt(83);
         this.writeInt(pcdataID);
      } else {
         this.writeInt(19);
         this.writeString(value);
      }

   }

   void writeInt(int value) throws XMLStreamException {
      try {
         do {
            int val = value & 127;
            value >>>= 7;
            if (value != 0) {
               val |= 128;
            }

            this.dos.write(val);
         } while(value != 0);

      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   int abbrevString(String s) throws XMLStreamException {
      if (s == null) {
         return 0;
      } else {
         if (this.dict.size() >= 1024) {
            this.writeInt(61);
            this.dict.clear();
         }

         Integer i = (Integer)this.dict.get(s);
         if (i != null) {
            return i;
         } else {
            int iv = this.dict.size() + 1;
            this.dict.put(s, new Integer(iv));
            this.writeInt(60);
            this.writeString(s);
            return iv;
         }
      }
   }
}
