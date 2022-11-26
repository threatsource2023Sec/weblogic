package weblogic.xml.stax.bin;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.xml.stax.util.NamespaceContextImpl;

public final class BinaryXMLStreamReader implements XMLStreamReader {
   private static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
   private final boolean v1;
   private int currentKind;
   private int currentEvent;
   private int nextKind;
   private final DataInput dis;
   private String version;
   private String encoding;
   private final ArrayList dict = new ArrayList();
   private NamespaceContextImpl context;
   private String localName;
   private String namespaceURI;
   private String strVal;
   private final ArrayList attributes = new ArrayList();
   private int numAttrs;
   private final ArrayList namespaces = new ArrayList();
   private int numNamespaces;
   private char[] tmpCharBuf = new char[0];
   private byte[] tmpByteBuf = new byte[0];

   public BinaryXMLStreamReader(InputStream is) throws XMLStreamException {
      this.dis = (DataInput)(is instanceof DataInput ? (DataInput)is : new DataInputStream(is));
      byte[] arr = new byte[BinaryXMLConstants.HEADERV2_BYTES.length];

      try {
         is.read(arr);
      } catch (IOException var4) {
         throw new XMLStreamException(var4);
      }

      if (Arrays.equals(BinaryXMLConstants.HEADERV2_BYTES, arr)) {
         this.v1 = false;
      } else {
         if (!Arrays.equals(BinaryXMLConstants.HEADERV1_BYTES, arr)) {
            throw new XMLStreamException("Invalid token stream");
         }

         this.v1 = true;
      }

      this.advance();
   }

   public int next() throws XMLStreamException {
      this.consumeDictionaryOps();
      switch (this.nextKind) {
         case 1:
            if (this.context != null) {
               this.context.openScope();
            }

            this.readQName();
            this.advance();
            this.consumeAttributesAndNamespaces();
            return this.currentEvent = 1;
         case 3:
            this.version = this.readString();
            this.encoding = this.readString();
            this.advance();
            return this.currentEvent = 7;
         case 4:
            if (this.context != null) {
               this.context.closeScope(false);
            }

            this.advance();
            return this.currentEvent = 2;
         case 6:
            this.advance();
            return this.currentEvent = 8;
         case 7:
            this.strVal = this.readString();
            this.advance();
            return this.currentEvent = 5;
         case 9:
            this.readQName();
            this.strVal = this.readString();
            this.advance();
            return this.currentEvent = 3;
         case 10:
            this.strVal = this.readString();
            this.advance();
            return this.currentEvent = 4;
         case 50:
            this.strVal = this.readString();
            this.readString();
            this.readString();
            this.advance();
            return this.currentEvent = 11;
         case 62:
         case 1002:
            this.currentEvent = 8;
            return this.currentEvent = 8;
         default:
            throw new XMLStreamException("Unexpected token: " + this.nextKind);
      }
   }

   public Object getProperty(String s) throws IllegalArgumentException {
      return null;
   }

   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
      if (this.currentEvent != type || namespaceURI == null || !namespaceURI.equals(this.getNamespaceURI()) || localName == null || !localName.equals(this.getLocalName())) {
         throw new XMLStreamException();
      }
   }

   public String getElementText() throws XMLStreamException {
      if (!this.isStartElement()) {
         throw new XMLStreamException("getElementText can only be called for START_ELEMENT");
      } else {
         StringBuffer ret = new StringBuffer();

         while(this.isCharacters()) {
            ret.append(this.getText());
         }

         return ret.toString();
      }
   }

   public int nextTag() throws XMLStreamException {
      return convert(this.nextKind);
   }

   public boolean hasNext() throws XMLStreamException {
      return this.currentEvent != 8;
   }

   public String getNamespaceURI(String s) {
      return this.context != null ? this.context.getNamespaceURI(s) : null;
   }

   public boolean isStartElement() {
      return this.currentEvent == 1;
   }

   public boolean isEndElement() {
      return this.currentEvent == 2;
   }

   public boolean isCharacters() {
      return this.currentEvent == 4;
   }

   public boolean isWhiteSpace() {
      if (!this.isCharacters()) {
         return false;
      } else {
         int i = 0;

         while(i < this.strVal.length()) {
            switch (this.strVal.charAt(i)) {
               case '\t':
               case '\n':
               case '\r':
               case ' ':
                  ++i;
                  break;
               default:
                  return false;
            }
         }

         return true;
      }
   }

   public String getAttributeValue(String uri, String localName) {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attributes may only be accessed from START_ELEMENT event");
      } else {
         for(int i = 0; i < this.numAttrs; ++i) {
            Attribute attr = (Attribute)this.attributes.get(i);
            if ((attr.uri == null && uri == null || attr.uri != null && attr.uri.equals(uri)) && (attr.localName == null && localName == null || attr.localName != null && attr.localName.equals(localName))) {
               return attr.val;
            }
         }

         return "";
      }
   }

   public void close() throws XMLStreamException {
      this.dict.clear();
   }

   public int getAttributeCount() {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attributes may only be accessed from START_ELEMENT event");
      } else {
         return this.numAttrs;
      }
   }

   public QName getAttributeName(int index) {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attributes may only be accessed from START_ELEMENT event");
      } else {
         return new QName(this.getAttributeNamespace(index), this.getAttributeLocalName(index), this.getAttributePrefix(index));
      }
   }

   public String getAttributeNamespace(int index) {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attributes may only be accessed from START_ELEMENT event");
      } else {
         Attribute attr = (Attribute)this.attributes.get(index);
         return attr.uri;
      }
   }

   public String getAttributeLocalName(int index) {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attributes may only be accessed from START_ELEMENT event");
      } else {
         Attribute attr = (Attribute)this.attributes.get(index);
         return attr.localName;
      }
   }

   public String getAttributePrefix(int index) {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attributes may only be accessed from START_ELEMENT event");
      } else {
         return this.context == null ? null : this.context.getPrefix(this.getAttributeNamespace(index));
      }
   }

   public String getAttributeType(int index) {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attributes may only be accessed from START_ELEMENT event");
      } else {
         return "CDATA";
      }
   }

   public String getAttributeValue(int index) {
      Attribute attr = (Attribute)this.attributes.get(index);
      return attr.val;
   }

   public boolean isAttributeSpecified(int index) {
      return false;
   }

   public String getCharacterEncodingScheme() {
      return this.encoding;
   }

   public int getNamespaceCount() {
      return this.numNamespaces;
   }

   public String getNamespacePrefix(int index) {
      Namespace namespace = (Namespace)this.namespaces.get(index);
      return namespace.prefix;
   }

   public String getNamespaceURI(int index) {
      Namespace namespace = (Namespace)this.namespaces.get(index);
      return namespace.uri;
   }

   public NamespaceContext getNamespaceContext() {
      return this.context;
   }

   public int getEventType() {
      return this.currentEvent;
   }

   public String getText() {
      switch (this.currentEvent) {
         case 3:
         case 4:
         case 5:
            return this.strVal;
         default:
            throw new IllegalArgumentException();
      }
   }

   public char[] getTextCharacters() {
      switch (this.currentEvent) {
         case 3:
         case 4:
         case 5:
            return this.strVal.toCharArray();
         default:
            throw new IllegalStateException();
      }
   }

   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }

   public int getTextStart() {
      return 0;
   }

   public int getTextLength() {
      return this.getTextCharacters().length;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public boolean hasText() {
      switch (this.currentKind) {
         case 7:
         case 9:
         case 10:
            return true;
         case 8:
         default:
            return false;
      }
   }

   public Location getLocation() {
      return new Location() {
         public int getLineNumber() {
            return -1;
         }

         public int getColumnNumber() {
            return -1;
         }

         public int getCharacterOffset() {
            return -1;
         }

         public String getPublicId() {
            return null;
         }

         public String getSystemId() {
            return null;
         }
      };
   }

   public QName getName() {
      return new QName(this.getNamespaceURI(), this.getLocalName(), this.getPrefix());
   }

   public String getLocalName() {
      switch (this.currentKind) {
         case 1:
         case 2:
            return this.localName;
         default:
            return null;
      }
   }

   public boolean hasName() {
      switch (this.currentEvent) {
         case 1:
         case 2:
         case 3:
         case 9:
            return true;
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         default:
            return false;
      }
   }

   public String getNamespaceURI() {
      switch (this.currentKind) {
         case 1:
         case 4:
            return this.namespaceURI;
         default:
            return null;
      }
   }

   public String getPrefix() {
      return this.context == null ? null : this.context.getPrefix(this.getNamespaceURI());
   }

   public String getVersion() {
      return this.version;
   }

   public boolean isStandalone() {
      return true;
   }

   public boolean standaloneSet() {
      return false;
   }

   public String getPITarget() {
      if (this.currentEvent != 3) {
         throw new IllegalStateException("Current state must be XMLStreamConstants.PROCESSING_INSTRUCTION");
      } else {
         return this.localName;
      }
   }

   public String getPIData() {
      if (this.currentEvent != 3) {
         throw new IllegalStateException("Current state must be XMLStreamConstants.PROCESSING_INSTRUCTION");
      } else {
         return this.strVal;
      }
   }

   private void consumeDictionaryOps() throws XMLStreamException {
      while(true) {
         switch (this.nextKind) {
            case 60:
            case 1000:
               String str = this.readString();
               this.dict.add(str);
               break;
            case 61:
            case 1001:
               this.dict.clear();
               break;
            default:
               return;
         }

         this.nextKind = this.readInt();
      }
   }

   private void consumeAttributesAndNamespaces() throws XMLStreamException {
      this.numAttrs = 0;
      this.numNamespaces = 0;

      while(true) {
         this.consumeDictionaryOps();
         switch (this.nextKind) {
            case 2:
               this.consumeAttribute();
               break;
            case 8:
               this.consumeNamespace();
               break;
            default:
               return;
         }

         this.nextKind = this.readInt();
      }
   }

   private void consumeNamespace() throws XMLStreamException {
      Namespace namespace;
      if (this.numNamespaces + 1 > this.namespaces.size()) {
         namespace = new Namespace();
         this.namespaces.add(namespace);
      } else {
         namespace = (Namespace)this.namespaces.get(this.numNamespaces);
      }

      ++this.numNamespaces;
      this.readNamespace(namespace);
   }

   private void readNamespace(Namespace namespace) throws XMLStreamException {
      namespace.prefix = this.readAbbrevString();
      namespace.uri = this.readAbbrevString();
      if (this.context == null) {
         this.context = new NamespaceContextImpl();
         this.context.bindNamespace("xmlns", "http://www.w3.org/2000/xmlns/");
      }

      this.context.bindNamespace(namespace.prefix, namespace.uri);
   }

   private void consumeAttribute() throws XMLStreamException {
      Attribute attr;
      if (this.numAttrs + 1 > this.attributes.size()) {
         attr = new Attribute();
         this.attributes.add(attr);
      } else {
         attr = (Attribute)this.attributes.get(this.numAttrs);
      }

      ++this.numAttrs;
      this.readAttribute(attr);
   }

   private void readAttribute(Attribute attr) throws XMLStreamException {
      attr.localName = this.readAbbrevString();
      attr.uri = this.readAbbrevString();
      this.nextKind = this.readInt();
      this.consumeDictionaryOps();
      if (this.nextKind == 5) {
         attr.val = "";
         this.nextKind = this.readInt();
      } else {
         attr.val = this.readPCDataString();
         this.nextKind = this.readInt();
         this.consumeDictionaryOps();
         if (this.nextKind != 5) {
            throw new XMLStreamException("Expected END_ATTRIBUTE");
         }
      }
   }

   private static final int convert(int kind) throws XMLStreamException {
      switch (kind) {
         case 1:
            return 1;
         case 3:
            return 7;
         case 4:
            return 2;
         case 6:
            return 8;
         case 7:
            return 5;
         case 9:
            return 3;
         case 10:
            return 4;
         case 50:
            return 11;
         default:
            throw new XMLStreamException("Unexpected kind: " + kind);
      }
   }

   private void advance() throws XMLStreamException {
      this.currentKind = this.nextKind;
      this.nextKind = (short)this.readInt();
   }

   private String readPCDataString() throws XMLStreamException {
      switch (this.nextKind) {
         case 19:
            return this.readString();
         case 83:
            return this.readAbbrevString();
         default:
            throw new XMLStreamException("Expected PCDATA");
      }
   }

   private void readQName() throws XMLStreamException {
      this.localName = this.readAbbrevString();
      this.namespaceURI = this.readAbbrevString();
   }

   private String readAbbrevString() throws XMLStreamException {
      int abbrev = this.readInt();
      return abbrev == 0 ? null : (String)this.dict.get(abbrev - 1);
   }

   private String readString() throws XMLStreamException {
      int len;
      if (this.v1) {
         try {
            len = this.dis.readChar();
         } catch (IOException var8) {
            throw new XMLStreamException(var8);
         }
      } else {
         len = this.readInt();
      }

      if (len > this.tmpByteBuf.length) {
         this.tmpByteBuf = new byte[len];
      }

      try {
         this.dis.readFully(this.tmpByteBuf, 0, len);
      } catch (IOException var7) {
         throw new XMLStreamException(var7);
      }

      if (len > this.tmpCharBuf.length) {
         this.tmpCharBuf = new char[len];
      }

      int pos = 0;
      int outBufLen = 0;

      while(pos < len) {
         int c = this.tmpByteBuf[pos] & 255;
         byte c2;
         switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
               ++pos;
               this.tmpCharBuf[outBufLen++] = (char)c;
               break;
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               throw new XMLStreamException("Malformed UTF data");
            case 12:
            case 13:
               pos += 2;
               if (pos > len) {
                  throw new XMLStreamException("Malformed UTF data");
               }

               c2 = this.tmpByteBuf[pos - 1];
               if ((c2 & 192) != 128) {
                  throw new XMLStreamException("Malformed UTF data");
               }

               this.tmpCharBuf[outBufLen++] = (char)((c & 31) << 6 | c2 & 63);
               break;
            case 14:
               pos += 3;
               if (pos > len) {
                  throw new XMLStreamException("Malformed UTF data");
               }

               c2 = this.tmpByteBuf[pos - 2];
               int c3 = this.tmpByteBuf[pos - 1];
               if ((c2 & 192) != 128 || (c3 & 192) != 128) {
                  throw new XMLStreamException();
               }

               this.tmpCharBuf[outBufLen++] = (char)((c & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
         }
      }

      return new String(this.tmpCharBuf, 0, outBufLen);
   }

   private int readInt() throws XMLStreamException {
      if (this.v1) {
         try {
            return this.dis.readInt();
         } catch (IOException var4) {
            throw new XMLStreamException(var4);
         }
      } else {
         try {
            int sum = 0;
            int shift = 0;

            byte b;
            do {
               b = this.dis.readByte();
               sum += (b & 127) << shift;
               shift += 7;
            } while((b & 128) == 128);

            return sum;
         } catch (IOException var5) {
            throw new XMLStreamException(var5);
         }
      }
   }

   private static final class Attribute {
      String localName;
      String uri;
      String val;

      private Attribute() {
      }

      // $FF: synthetic method
      Attribute(Object x0) {
         this();
      }
   }

   private static final class Namespace {
      String uri;
      String prefix;

      private Namespace() {
      }

      // $FF: synthetic method
      Namespace(Object x0) {
         this();
      }
   }
}
