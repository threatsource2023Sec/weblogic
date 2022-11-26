package com.bea.xml.stream;

import com.bea.xml.stream.reader.XmlReader;
import com.bea.xml.stream.util.ElementTypeNames;
import com.bea.xml.stream.util.EmptyIterator;
import com.wutka.dtd.DTD;
import com.wutka.dtd.DTDAttlist;
import com.wutka.dtd.DTDAttribute;
import com.wutka.dtd.DTDEntity;
import com.wutka.dtd.DTDParser;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class MXParser implements XMLStreamReader, Location {
   protected static final String XML_URI = "http://www.w3.org/XML/1998/namespace";
   protected static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
   protected static final String FEATURE_XML_ROUNDTRIP = "http://xmlpull.org/v1/doc/features.html#xml-roundtrip";
   protected static final String FEATURE_NAMES_INTERNED = "http://xmlpull.org/v1/doc/features.html#names-interned";
   public static final String FEATURE_PROCESS_DOCDECL = "http://xmlpull.org/v1/doc/features.html#process-docdecl";
   public static final String[] TYPES = new String[]{"START_DOCUMENT", "END_DOCUMENT", "START_ELEMENT", "END_ELEMENT", "CHARACTERS", "CDATA", "ENTITY_REFERENCE", "SPACE", "PROCESSING_INSTRUCTION", "XML_DECLARATION", "COMMENT", "DOCDECL"};
   private static final int TEXT = 16384;
   private static final int DOCDECL = 32768;
   public static final String NO_NAMESPACE = "";
   protected boolean allStringsInterned;
   private static final boolean TRACE_SIZING = false;
   public static final String FEATURE_PROCESS_NAMESPACES = "http://xmlpull.org/v1/doc/features.html#process-namespaces";
   protected boolean processNamespaces = true;
   protected boolean roundtripSupported = true;
   protected int lineNumber;
   protected int columnNumber;
   protected boolean seenRoot;
   protected boolean reachedEnd;
   protected int eventType;
   protected boolean emptyElementTag;
   protected int depth;
   protected char[][] elRawName;
   protected int[] elRawNameEnd;
   protected String[] elName;
   protected String[] elPrefix;
   protected String[] elUri;
   protected int[] elNamespaceCount;
   protected String xmlVersion;
   protected boolean standalone = true;
   protected boolean standaloneSet = false;
   protected String charEncodingScheme;
   protected String piTarget;
   protected String piData;
   protected HashMap defaultAttributes;
   protected static final int LOOKUP_MAX = 1024;
   protected static final char LOOKUP_MAX_CHAR = 'Ѐ';
   protected static boolean[] lookupNameStartChar = new boolean[1024];
   protected static boolean[] lookupNameChar = new boolean[1024];
   protected int attributeCount;
   protected String[] attributeName;
   protected int[] attributeNameHash;
   protected String[] attributePrefix;
   protected String[] attributeUri;
   protected String[] attributeValue;
   protected int namespaceEnd;
   protected String[] namespacePrefix;
   protected int[] namespacePrefixHash;
   protected String[] namespaceUri;
   protected int localNamespaceEnd;
   protected String[] localNamespacePrefix;
   protected int[] localNamespacePrefixHash;
   protected String[] localNamespaceUri;
   protected int entityEnd;
   protected String[] entityName;
   protected char[][] entityNameBuf;
   protected int[] entityNameHash;
   protected char[][] entityReplacementBuf;
   protected String[] entityReplacement;
   protected static final int READ_CHUNK_SIZE = 8192;
   protected Reader reader;
   protected String inputEncoding;
   protected int bufLoadFactor = 95;
   protected char[] buf = new char[Runtime.getRuntime().freeMemory() > 1000000L ? 8192 : 256];
   protected int bufSoftLimit;
   protected int bufAbsoluteStart;
   protected int bufStart;
   protected int bufEnd;
   protected int pos;
   protected int posStart;
   protected int posEnd;
   protected char[] pc;
   protected int pcStart;
   protected int pcEnd;
   protected boolean usePC;
   protected boolean seenStartTag;
   protected boolean seenEndTag;
   protected boolean pastEndTag;
   protected boolean seenAmpersand;
   protected boolean seenMarkup;
   protected boolean seenDocdecl;
   protected boolean tokenize;
   protected String text;
   protected String entityRefName;
   protected char[] charRefOneCharBuf;
   protected static final char[] VERSION;
   protected static final char[] ENCODING;
   protected static final char[] STANDALONE;
   protected static final char[] YES;
   protected static final char[] NO;
   private ConfigurationContextBase configurationContext;

   protected void resetStringCache() {
   }

   protected String newString(char[] cbuf, int off, int len) {
      return new String(cbuf, off, len);
   }

   protected String newStringIntern(char[] cbuf, int off, int len) {
      return (new String(cbuf, off, len)).intern();
   }

   protected void ensureElementsCapacity() {
      int elStackSize = this.elName != null ? this.elName.length : 0;
      if (this.depth + 1 >= elStackSize) {
         int newSize = (this.depth >= 7 ? 2 * this.depth : 8) + 2;
         boolean needsCopying = elStackSize > 0;
         String[] arr = null;
         arr = new String[newSize];
         if (needsCopying) {
            System.arraycopy(this.elName, 0, arr, 0, elStackSize);
         }

         this.elName = arr;
         arr = new String[newSize];
         if (needsCopying) {
            System.arraycopy(this.elPrefix, 0, arr, 0, elStackSize);
         }

         this.elPrefix = arr;
         arr = new String[newSize];
         if (needsCopying) {
            System.arraycopy(this.elUri, 0, arr, 0, elStackSize);
         }

         this.elUri = arr;
         int[] iarr = new int[newSize];
         if (needsCopying) {
            System.arraycopy(this.elNamespaceCount, 0, iarr, 0, elStackSize);
         } else {
            iarr[0] = 0;
         }

         this.elNamespaceCount = iarr;
         iarr = new int[newSize];
         if (needsCopying) {
            System.arraycopy(this.elRawNameEnd, 0, iarr, 0, elStackSize);
         }

         this.elRawNameEnd = iarr;
         char[][] carr = new char[newSize][];
         if (needsCopying) {
            System.arraycopy(this.elRawName, 0, carr, 0, elStackSize);
         }

         this.elRawName = carr;
      }

   }

   private static final void setName(char ch) {
      lookupNameChar[ch] = true;
   }

   private static final void setNameStart(char ch) {
      lookupNameStartChar[ch] = true;
      setName(ch);
   }

   protected boolean isNameStartChar(char ch) {
      return ch < 1024 && lookupNameStartChar[ch] || ch >= 1024 && ch <= 8231 || ch >= 8234 && ch <= 8591 || ch >= 10240 && ch <= '\uffef';
   }

   protected boolean isNameChar(char ch) {
      return ch < 1024 && lookupNameChar[ch] || ch >= 1024 && ch <= 8231 || ch >= 8234 && ch <= 8591 || ch >= 10240 && ch <= '\uffef';
   }

   protected boolean isS(char ch) {
      return ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t';
   }

   protected void ensureAttributesCapacity(int size) {
      int attrPosSize = this.attributeName != null ? this.attributeName.length : 0;
      if (size >= attrPosSize) {
         int newSize = size > 7 ? 2 * size : 8;
         boolean needsCopying = attrPosSize > 0;
         String[] arr = null;
         arr = new String[newSize];
         if (needsCopying) {
            System.arraycopy(this.attributeName, 0, arr, 0, attrPosSize);
         }

         this.attributeName = arr;
         arr = new String[newSize];
         if (needsCopying) {
            System.arraycopy(this.attributePrefix, 0, arr, 0, attrPosSize);
         }

         this.attributePrefix = arr;
         arr = new String[newSize];
         if (needsCopying) {
            System.arraycopy(this.attributeUri, 0, arr, 0, attrPosSize);
         }

         this.attributeUri = arr;
         arr = new String[newSize];
         if (needsCopying) {
            System.arraycopy(this.attributeValue, 0, arr, 0, attrPosSize);
         }

         this.attributeValue = arr;
         if (!this.allStringsInterned) {
            int[] iarr = new int[newSize];
            if (needsCopying) {
               System.arraycopy(this.attributeNameHash, 0, iarr, 0, attrPosSize);
            }

            this.attributeNameHash = iarr;
         }

         arr = null;
      }

   }

   protected void ensureNamespacesCapacity(int size) {
      int namespaceSize = this.namespacePrefix != null ? this.namespacePrefix.length : 0;
      if (size >= namespaceSize) {
         int newSize = size > 7 ? 2 * size : 8;
         String[] newNamespacePrefix = new String[newSize];
         String[] newNamespaceUri = new String[newSize];
         if (this.namespacePrefix != null) {
            System.arraycopy(this.namespacePrefix, 0, newNamespacePrefix, 0, this.namespaceEnd);
            System.arraycopy(this.namespaceUri, 0, newNamespaceUri, 0, this.namespaceEnd);
         }

         this.namespacePrefix = newNamespacePrefix;
         this.namespaceUri = newNamespaceUri;
         if (!this.allStringsInterned) {
            int[] newNamespacePrefixHash = new int[newSize];
            if (this.namespacePrefixHash != null) {
               System.arraycopy(this.namespacePrefixHash, 0, newNamespacePrefixHash, 0, this.namespaceEnd);
            }

            this.namespacePrefixHash = newNamespacePrefixHash;
         }
      }

   }

   protected void ensureLocalNamespacesCapacity(int size) {
      int localNamespaceSize = this.localNamespacePrefix != null ? this.localNamespacePrefix.length : 0;
      if (size >= localNamespaceSize) {
         int newSize = size > 7 ? 2 * size : 8;
         String[] newLocalNamespacePrefix = new String[newSize];
         String[] newLocalNamespaceUri = new String[newSize];
         if (this.localNamespacePrefix != null) {
            System.arraycopy(this.localNamespacePrefix, 0, newLocalNamespacePrefix, 0, this.localNamespaceEnd);
            System.arraycopy(this.localNamespaceUri, 0, newLocalNamespaceUri, 0, this.localNamespaceEnd);
         }

         this.localNamespacePrefix = newLocalNamespacePrefix;
         this.localNamespaceUri = newLocalNamespaceUri;
         if (!this.allStringsInterned) {
            int[] newLocalNamespacePrefixHash = new int[newSize];
            if (this.localNamespacePrefixHash != null) {
               System.arraycopy(this.localNamespacePrefixHash, 0, newLocalNamespacePrefixHash, 0, this.localNamespaceEnd);
            }

            this.localNamespacePrefixHash = newLocalNamespacePrefixHash;
         }
      }

   }

   public int getLocalNamespaceCount() {
      int startNs = this.elNamespaceCount[this.depth - 1];
      return this.namespaceEnd - startNs;
   }

   private String getLocalNamespaceURI(int pos) {
      return this.namespaceUri[pos];
   }

   private String getLocalNamespacePrefix(int pos) {
      return this.namespacePrefix[pos];
   }

   protected static final int fastHash(char[] ch, int off, int len) {
      if (len == 0) {
         return 0;
      } else {
         int hash = ch[off];
         hash = (hash << 7) + ch[off + len - 1];
         if (len > 16) {
            hash = (hash << 7) + ch[off + len / 4];
         }

         if (len > 8) {
            hash = (hash << 7) + ch[off + len / 2];
         }

         return hash;
      }
   }

   protected void ensureEntityCapacity() {
      int entitySize = this.entityReplacementBuf != null ? this.entityReplacementBuf.length : 0;
      if (this.entityEnd >= entitySize) {
         int newSize = this.entityEnd > 7 ? 2 * this.entityEnd : 8;
         String[] newEntityName = new String[newSize];
         char[][] newEntityNameBuf = new char[newSize][];
         String[] newEntityReplacement = new String[newSize];
         char[][] newEntityReplacementBuf = new char[newSize][];
         if (this.entityName != null) {
            System.arraycopy(this.entityName, 0, newEntityName, 0, this.entityEnd);
            System.arraycopy(this.entityReplacementBuf, 0, newEntityReplacement, 0, this.entityEnd);
            System.arraycopy(this.entityReplacement, 0, newEntityReplacement, 0, this.entityEnd);
            System.arraycopy(this.entityReplacementBuf, 0, newEntityReplacementBuf, 0, this.entityEnd);
         }

         this.entityName = newEntityName;
         this.entityNameBuf = newEntityNameBuf;
         this.entityReplacement = newEntityReplacement;
         this.entityReplacementBuf = newEntityReplacementBuf;
         if (!this.allStringsInterned) {
            int[] newEntityNameHash = new int[newSize];
            if (this.entityNameHash != null) {
               System.arraycopy(this.entityNameHash, 0, newEntityNameHash, 0, this.entityEnd);
            }

            this.entityNameHash = newEntityNameHash;
         }
      }

   }

   private void reset() {
      this.lineNumber = 1;
      this.columnNumber = 0;
      this.seenRoot = false;
      this.reachedEnd = false;
      this.eventType = 7;
      this.emptyElementTag = false;
      this.depth = 0;
      this.attributeCount = 0;
      this.namespaceEnd = 0;
      this.localNamespaceEnd = 0;
      this.entityEnd = 0;
      this.reader = null;
      this.inputEncoding = null;
      this.bufAbsoluteStart = 0;
      this.bufEnd = this.bufStart = 0;
      this.pos = this.posStart = this.posEnd = 0;
      this.pcEnd = this.pcStart = 0;
      this.usePC = false;
      this.seenStartTag = false;
      this.seenEndTag = false;
      this.pastEndTag = false;
      this.seenAmpersand = false;
      this.seenMarkup = false;
      this.seenDocdecl = false;
      this.resetStringCache();
   }

   public MXParser() {
      this.bufSoftLimit = this.bufLoadFactor * this.buf.length / 100;
      this.pc = new char[Runtime.getRuntime().freeMemory() > 1000000L ? 8192 : 64];
      this.charRefOneCharBuf = new char[1];
   }

   public void setFeature(String name, boolean state) throws XMLStreamException {
      if (name == null) {
         throw new IllegalArgumentException("feature name should not be nulll");
      } else {
         if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(name)) {
            if (this.eventType != 7) {
               throw new XMLStreamException("namespace processing feature can only be changed before parsing", this.getLocation());
            }

            this.processNamespaces = state;
         } else if ("http://xmlpull.org/v1/doc/features.html#names-interned".equals(name)) {
            if (state) {
               throw new XMLStreamException("interning names in this implementation is not supported");
            }
         } else if ("http://xmlpull.org/v1/doc/features.html#process-docdecl".equals(name)) {
            if (state) {
               throw new XMLStreamException("processing DOCDECL is not supported");
            }
         } else {
            if (!"http://xmlpull.org/v1/doc/features.html#xml-roundtrip".equals(name)) {
               throw new XMLStreamException("unknown feature " + name);
            }

            if (!state) {
               throw new XMLStreamException("roundtrip feature can not be switched off");
            }
         }

      }
   }

   public boolean getFeature(String name) {
      if (name == null) {
         throw new IllegalArgumentException("feature name should not be nulll");
      } else if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(name)) {
         return this.processNamespaces;
      } else if ("http://xmlpull.org/v1/doc/features.html#names-interned".equals(name)) {
         return false;
      } else if ("http://xmlpull.org/v1/doc/features.html#process-docdecl".equals(name)) {
         return false;
      } else {
         return "http://xmlpull.org/v1/doc/features.html#xml-roundtrip".equals(name);
      }
   }

   public void setProperty(String name, Object value) throws XMLStreamException {
      throw new XMLStreamException("unsupported property: '" + name + "'");
   }

   public boolean checkForXMLDecl() throws XMLStreamException {
      try {
         BufferedReader breader = new BufferedReader(this.reader, 7);
         this.reader = breader;
         breader.mark(7);
         if (breader.read() == 60 && breader.read() == 63 && breader.read() == 120 && breader.read() == 109 && breader.read() == 108) {
            breader.reset();
            return true;
         } else {
            breader.reset();
            return false;
         }
      } catch (IOException var2) {
         throw new XMLStreamException(var2);
      }
   }

   public void setInput(Reader in) throws XMLStreamException {
      this.reset();
      this.reader = in;
      if (this.checkForXMLDecl()) {
         this.next();
      }

   }

   public void setInput(InputStream in) throws XMLStreamException {
      try {
         this.setInput(XmlReader.createReader(in));
      } catch (Exception var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void setInput(InputStream inputStream, String inputEncoding) throws XMLStreamException {
      if (inputStream == null) {
         throw new IllegalArgumentException("input stream can not be null");
      } else {
         InputStreamReader reader;
         if (inputEncoding != null) {
            try {
               if (inputEncoding != null) {
                  reader = new InputStreamReader(inputStream, inputEncoding);
               } else {
                  reader = new InputStreamReader(inputStream);
               }
            } catch (UnsupportedEncodingException var5) {
               throw new XMLStreamException("could not create reader for encoding " + inputEncoding + " : " + var5, this.getLocation(), var5);
            }
         } else {
            reader = new InputStreamReader(inputStream);
         }

         this.setInput((Reader)reader);
         this.inputEncoding = inputEncoding;
      }
   }

   public String getInputEncoding() {
      return this.inputEncoding;
   }

   public void defineEntityReplacementText(String entityName, String replacementText) throws XMLStreamException {
      this.ensureEntityCapacity();
      this.entityName[this.entityEnd] = this.newString(entityName.toCharArray(), 0, entityName.length());
      this.entityNameBuf[this.entityEnd] = entityName.toCharArray();
      this.entityReplacement[this.entityEnd] = replacementText;
      this.entityReplacementBuf[this.entityEnd] = replacementText.toCharArray();
      if (!this.allStringsInterned) {
         this.entityNameHash[this.entityEnd] = fastHash(this.entityNameBuf[this.entityEnd], 0, this.entityNameBuf[this.entityEnd].length);
      }

      ++this.entityEnd;
   }

   public int getNamespaceCount() {
      return this.getNamespaceCount(this.depth);
   }

   public int getNamespaceCount(int depth) {
      if (this.processNamespaces && depth != 0) {
         if (depth < 0) {
            throw new IllegalArgumentException("namespace count may be 0.." + this.depth + " not " + depth);
         } else {
            return this.elNamespaceCount[depth] - this.elNamespaceCount[depth - 1];
         }
      } else {
         return 0;
      }
   }

   public String getNamespacePrefix(int pos) {
      int currentDepth = this.depth;
      int end = this.getNamespaceCount(currentDepth);
      int newpos = pos + this.elNamespaceCount[currentDepth - 1];
      if (pos < end) {
         return this.namespacePrefix[newpos];
      } else {
         throw new ArrayIndexOutOfBoundsException("position " + pos + " exceeded number of available namespaces " + end);
      }
   }

   public String getNamespaceURI(int pos) {
      int currentDepth = this.depth;
      int end = this.getNamespaceCount(currentDepth);
      int newpos = pos + this.elNamespaceCount[currentDepth - 1];
      if (pos < end) {
         return this.namespaceUri[newpos];
      } else {
         throw new ArrayIndexOutOfBoundsException("position " + pos + " exceedded number of available namespaces " + end);
      }
   }

   public String getNamespaceURI(String prefix) {
      int i;
      if (prefix != null && !"".equals(prefix)) {
         for(i = this.namespaceEnd - 1; i >= 0; --i) {
            if (prefix.equals(this.namespacePrefix[i])) {
               return this.namespaceUri[i];
            }
         }

         if ("xml".equals(prefix)) {
            return "http://www.w3.org/XML/1998/namespace";
         }

         if ("xmlns".equals(prefix)) {
            return "http://www.w3.org/2000/xmlns/";
         }
      } else {
         for(i = this.namespaceEnd - 1; i >= 0; --i) {
            if (this.namespacePrefix[i] == null) {
               return this.namespaceUri[i];
            }
         }
      }

      return null;
   }

   public int getDepth() {
      return this.depth;
   }

   private static int findFragment(int bufMinPos, char[] b, int start, int end) {
      if (start < bufMinPos) {
         start = bufMinPos;
         if (bufMinPos > end) {
            start = end;
         }

         return start;
      } else {
         if (end - start > 65) {
            start = end - 10;
         }

         int i = start + 1;

         char c;
         do {
            --i;
            if (i <= bufMinPos || end - i > 65) {
               break;
            }

            c = b[i];
         } while(c != '<' || start - i <= 10);

         return i;
      }
   }

   public String getPositionDescription() {
      String fragment = null;
      if (this.posStart <= this.pos) {
         int start = findFragment(0, this.buf, this.posStart, this.pos);
         if (start < this.pos) {
            fragment = new String(this.buf, start, this.pos - start);
         }

         if (this.bufAbsoluteStart > 0 || start > 0) {
            fragment = "..." + fragment;
         }
      }

      return " " + (fragment != null ? " seen " + this.printable(fragment) + "..." : "") + " @" + this.getLineNumber() + ":" + this.getColumnNumber();
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public int getColumnNumber() {
      return this.columnNumber;
   }

   public String getLocationURI() {
      return null;
   }

   public boolean isWhiteSpace() {
      if (this.eventType != 4 && this.eventType != 12) {
         return this.eventType == 6;
      } else {
         int i;
         if (this.usePC) {
            for(i = this.pcStart; i < this.pcEnd; ++i) {
               if (!this.isS(this.pc[i])) {
                  return false;
               }
            }

            return true;
         } else {
            for(i = this.posStart; i < this.posEnd; ++i) {
               if (!this.isS(this.buf[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public String getText() {
      if (this.eventType != 7 && this.eventType != 8) {
         if (this.eventType == 9) {
            return this.text;
         } else {
            if (this.usePC) {
               this.text = new String(this.pc, this.pcStart, this.pcEnd - this.pcStart);
            } else {
               this.text = new String(this.buf, this.posStart, this.posEnd - this.posStart);
            }

            return this.text;
         }
      } else {
         return null;
      }
   }

   public String getNamespaceURI() {
      if (this.eventType != 1 && this.eventType != 2) {
         return null;
      } else {
         return this.processNamespaces ? this.elUri[this.depth] : "";
      }
   }

   public String getLocalName() {
      if (this.eventType == 1) {
         return this.elName[this.depth];
      } else if (this.eventType == 2) {
         return this.elName[this.depth];
      } else if (this.eventType == 9) {
         if (this.entityRefName == null) {
            this.entityRefName = this.newString(this.buf, this.posStart, this.posEnd - this.posStart);
         }

         return this.entityRefName;
      } else {
         return null;
      }
   }

   public String getPrefix() {
      return this.eventType != 1 && this.eventType != 2 ? null : this.elPrefix[this.depth];
   }

   public boolean isEmptyElementTag() throws XMLStreamException {
      if (this.eventType != 1) {
         throw new XMLStreamException("parser must be on XMLEvent.START_ELEMENT to check for empty element", this.getLocation());
      } else {
         return this.emptyElementTag;
      }
   }

   public int getAttributeCount() {
      return this.eventType != 1 ? -1 : this.attributeCount;
   }

   public String getAttributeNamespace(int index) {
      if (this.eventType != 1) {
         throw new IndexOutOfBoundsException("only XMLEvent.START_ELEMENT can have attributes");
      } else if (!this.processNamespaces) {
         return "";
      } else if (index >= 0 && index < this.attributeCount) {
         return this.attributeUri[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public String getAttributeLocalName(int index) {
      if (this.eventType != 1) {
         throw new IndexOutOfBoundsException("only XMLEvent.START_ELEMENT can have attributes");
      } else if (index >= 0 && index < this.attributeCount) {
         return this.attributeName[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public String getAttributePrefix(int index) {
      if (this.eventType != 1) {
         throw new IndexOutOfBoundsException("only XMLEvent.START_ELEMENT can have attributes");
      } else if (!this.processNamespaces) {
         return null;
      } else if (index >= 0 && index < this.attributeCount) {
         return this.attributePrefix[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public String getAttributeType(int index) {
      if (this.eventType != 1) {
         throw new IndexOutOfBoundsException("only XMLEvent.START_ELEMENT can have attributes");
      } else if (index >= 0 && index < this.attributeCount) {
         return "CDATA";
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public boolean isAttributeSpecified(int index) {
      if (this.eventType != 1) {
         throw new IndexOutOfBoundsException("only XMLEvent.START_ELEMENT can have attributes");
      } else if (index >= 0 && index < this.attributeCount) {
         return false;
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public String getAttributeValue(int index) {
      if (this.eventType != 1) {
         throw new IndexOutOfBoundsException("only XMLEvent.START_ELEMENT can have attributes");
      } else if (index >= 0 && index < this.attributeCount) {
         return this.attributeValue[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public String getAttributeValue(String namespace, String name) {
      if (this.eventType != 1) {
         throw new IndexOutOfBoundsException("only XMLEvent.START_ELEMENT can have attributes");
      } else if (name == null) {
         throw new IllegalArgumentException("attribute name can not be null");
      } else {
         int i;
         if (namespace != null) {
            for(i = 0; i < this.attributeCount; ++i) {
               if (namespace.equals(this.attributeUri[i]) && name.equals(this.attributeName[i])) {
                  return this.attributeValue[i];
               }
            }
         } else {
            for(i = 0; i < this.attributeCount; ++i) {
               if (name.equals(this.attributeName[i])) {
                  return this.attributeValue[i];
               }
            }
         }

         return null;
      }
   }

   public int getEventType() {
      return this.eventType;
   }

   public void require(int type, String namespace, String name) throws XMLStreamException {
      if (type != this.getEventType() || namespace != null && !namespace.equals(this.getNamespaceURI()) || name != null && !name.equals(this.getLocalName())) {
         throw new XMLStreamException("expected event " + ElementTypeNames.getEventTypeString(type) + (name != null ? " with name '" + name + "'" : "") + (namespace != null && name != null ? " and" : "") + (namespace != null ? " with namespace '" + namespace + "'" : "") + " but got" + (type != this.getEventType() ? " " + ElementTypeNames.getEventTypeString(this.getEventType()) : "") + (name != null && this.getLocalName() != null && !name.equals(this.getName()) ? " name '" + this.getLocalName() + "'" : "") + (namespace != null && name != null && this.getLocalName() != null && !name.equals(this.getName()) && this.getNamespaceURI() != null && !namespace.equals(this.getNamespaceURI()) ? " and" : "") + (namespace != null && this.getNamespaceURI() != null && !namespace.equals(this.getNamespaceURI()) ? " namespace '" + this.getNamespaceURI() + "'" : "") + " (postion:" + this.getPositionDescription() + ")");
      }
   }

   public String nextText() throws XMLStreamException {
      if (this.getEventType() != 1) {
         throw new XMLStreamException("parser must be on XMLEvent.START_ELEMENT to read next text", this.getLocation());
      } else {
         int eventType = this.next();
         if (eventType == 4) {
            String result = this.getText();
            eventType = this.next();
            if (eventType != 2) {
               throw new XMLStreamException("TEXT must be immediately followed by XMLEvent.END_ELEMENT and not " + ElementTypeNames.getEventTypeString(this.getEventType()), this.getLocation());
            } else {
               return result;
            }
         } else if (eventType == 2) {
            return "";
         } else {
            throw new XMLStreamException("parser must be on XMLEvent.START_ELEMENT or TEXT to read text", this.getLocation());
         }
      }
   }

   public int nextTag() throws XMLStreamException {
      this.next();
      if (this.eventType == 4 && this.isWhiteSpace() || this.eventType == 5) {
         this.next();
      }

      if (this.eventType != 1 && this.eventType != 2) {
         throw new XMLStreamException("expected XMLEvent.START_ELEMENT or XMLEvent.END_ELEMENT not " + ElementTypeNames.getEventTypeString(this.getEventType()), this.getLocation());
      } else {
         return this.eventType;
      }
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

   public int next() throws XMLStreamException {
      this.tokenize = true;
      this.pcEnd = this.pcStart = 0;
      this.usePC = false;
      return this.nextImpl();
   }

   public int nextToken() throws XMLStreamException {
      this.tokenize = true;
      return this.nextImpl();
   }

   public int nextElement() throws XMLStreamException {
      return this.nextTag();
   }

   public boolean hasNext() throws XMLStreamException {
      return this.eventType != 8;
   }

   public void skip() throws XMLStreamException {
      this.nextToken();
   }

   public void close() throws XMLStreamException {
   }

   public boolean isStartElement() {
      return this.eventType == 1;
   }

   public boolean isEndElement() {
      return this.eventType == 2;
   }

   public boolean isCharacters() {
      return this.eventType == 4;
   }

   public boolean isEOF() {
      return this.eventType == 8;
   }

   public boolean moveToStartElement() throws XMLStreamException {
      if (this.isStartElement()) {
         return true;
      } else {
         while(this.hasNext()) {
            if (this.isStartElement()) {
               return true;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToStartElement(String localName) throws XMLStreamException {
      if (localName == null) {
         return false;
      } else {
         while(this.moveToStartElement()) {
            if (localName.equals(this.getLocalName())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToStartElement(String localName, String namespaceUri) throws XMLStreamException {
      if (localName != null && namespaceUri != null) {
         while(this.moveToStartElement(localName)) {
            if (namespaceUri.equals(this.getNamespaceURI())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean moveToEndElement() throws XMLStreamException {
      if (this.isEndElement()) {
         return true;
      } else {
         while(this.hasNext()) {
            if (this.isEndElement()) {
               return true;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToEndElement(String localName) throws XMLStreamException {
      if (localName == null) {
         return false;
      } else {
         while(this.moveToEndElement()) {
            if (localName.equals(this.getLocalName())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToEndElement(String localName, String namespaceUri) throws XMLStreamException {
      if (localName != null && namespaceUri != null) {
         while(this.moveToEndElement(localName)) {
            if (namespaceUri.equals(this.getNamespaceURI())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean hasAttributes() {
      return this.getAttributeCount() > 0;
   }

   public boolean hasNamespaces() {
      return this.getNamespaceCount() > 0;
   }

   public Iterator getAttributes() {
      if (!this.hasAttributes()) {
         return EmptyIterator.emptyIterator;
      } else {
         int attributeCount = this.getAttributeCount();
         ArrayList atts = new ArrayList();

         for(int i = 0; i < attributeCount; ++i) {
            atts.add(new AttributeBase(this.getAttributePrefix(i), this.getAttributeNamespace(i), this.getAttributeLocalName(i), this.getAttributeValue(i), this.getAttributeType(i)));
         }

         return atts.iterator();
      }
   }

   public Iterator internalGetNamespaces(int depth, int namespaceCount) {
      ArrayList ns = new ArrayList();
      int startNs = this.elNamespaceCount[depth - 1];

      for(int i = 0; i < namespaceCount; ++i) {
         String prefix = this.getLocalNamespacePrefix(i + startNs);
         if (prefix == null) {
            ns.add(new NamespaceBase(this.getLocalNamespaceURI(i + startNs)));
         } else {
            ns.add(new NamespaceBase(prefix, this.getLocalNamespaceURI(i + startNs)));
         }
      }

      return ns.iterator();
   }

   public Iterator getNamespaces() {
      if (!this.hasNamespaces()) {
         return EmptyIterator.emptyIterator;
      } else {
         int namespaceCount = this.getLocalNamespaceCount();
         return this.internalGetNamespaces(this.depth, namespaceCount);
      }
   }

   public Iterator getOutOfScopeNamespaces() {
      int startNs = this.elNamespaceCount[this.depth - 1];
      int endNs = this.elNamespaceCount[this.depth];
      int namespaceCount = endNs - startNs;
      return this.internalGetNamespaces(this.depth, namespaceCount);
   }

   public XMLStreamReader subReader() throws XMLStreamException {
      return new SubReader(this);
   }

   public void recycle() throws XMLStreamException {
      this.reset();
   }

   public Reader getTextStream() {
      throw new UnsupportedOperationException();
   }

   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
      if (this.getTextStart() + sourceStart >= this.getTextLength()) {
         throw new ArrayIndexOutOfBoundsException();
      } else {
         int numCopy;
         if (this.getTextStart() + sourceStart + length < this.getTextLength()) {
            numCopy = length;
         } else {
            numCopy = this.getTextLength() - (this.getTextStart() + sourceStart);
         }

         System.arraycopy(this.getTextCharacters(), this.getTextStart() + sourceStart, target, targetStart, numCopy);
         return numCopy;
      }
   }

   public char[] getTextCharacters() {
      if (this.eventType == 4) {
         return this.usePC ? this.pc : this.buf;
      } else if (this.eventType != 1 && this.eventType != 2 && this.eventType != 12 && this.eventType != 5 && this.eventType != 9 && this.eventType != 3 && this.eventType != 6 && this.eventType != 11) {
         if (this.eventType != 7 && this.eventType != 8) {
            throw new IllegalArgumentException("unknown text eventType: " + this.eventType);
         } else {
            return null;
         }
      } else {
         return this.buf;
      }
   }

   public int getTextStart() {
      return this.usePC ? this.pcStart : this.posStart;
   }

   public int getTextLength() {
      return this.usePC ? this.pcEnd - this.pcStart : this.posEnd - this.posStart;
   }

   public boolean hasText() {
      return 0 != (this.eventType & 15);
   }

   public String getValue() {
      return this.getText();
   }

   public String getEncoding() {
      return this.getInputEncoding();
   }

   public int getCharacterOffset() {
      return this.posEnd;
   }

   public QName getAttributeName(int index) {
      return new QName(this.getAttributeNamespace(index), this.getAttributeLocalName(index), this.getAttributePrefix(index));
   }

   public QName getName() {
      return new QName(this.getNamespaceURI(), this.getLocalName(), this.getPrefix());
   }

   public boolean hasName() {
      return 0 != (this.eventType & 11);
   }

   public String getVersion() {
      return this.xmlVersion;
   }

   public boolean isStandalone() {
      return this.standalone;
   }

   public boolean standaloneSet() {
      return this.standaloneSet;
   }

   public String getCharacterEncodingScheme() {
      return this.charEncodingScheme;
   }

   protected int nextImpl() throws XMLStreamException {
      try {
         this.text = null;
         this.bufStart = this.posEnd;
         if (this.pastEndTag) {
            this.pastEndTag = false;
            --this.depth;
            this.namespaceEnd = this.elNamespaceCount[this.depth];
         }

         if (this.emptyElementTag) {
            this.emptyElementTag = false;
            this.pastEndTag = true;
            return this.eventType = 2;
         } else if (this.depth <= 0) {
            return this.seenRoot ? this.parseEpilog() : this.parseProlog();
         } else if (this.seenStartTag) {
            this.seenStartTag = false;
            return this.eventType = this.parseStartTag();
         } else if (this.seenEndTag) {
            this.seenEndTag = false;
            return this.eventType = this.parseEndTag();
         } else {
            char ch;
            if (this.seenMarkup) {
               this.seenMarkup = false;
               ch = '<';
            } else if (this.seenAmpersand) {
               this.seenAmpersand = false;
               ch = '&';
            } else {
               ch = this.more();
            }

            this.posStart = this.pos - 1;
            boolean hadCharData = false;
            boolean needsMerging = false;

            while(true) {
               label245:
               while(true) {
                  int oldEnd;
                  int i;
                  int oldStart;
                  if (ch == '<') {
                     if (hadCharData && this.tokenize) {
                        this.seenMarkup = true;
                        return this.eventType = 4;
                     }

                     ch = this.more();
                     if (ch == '/') {
                        if (!this.tokenize && hadCharData) {
                           this.seenEndTag = true;
                           return this.eventType = 4;
                        }

                        return this.eventType = this.parseEndTag();
                     }

                     if (ch == '!') {
                        ch = this.more();
                        if (ch == '-') {
                           this.parseComment();
                           if (this.tokenize) {
                              return this.eventType = 5;
                           }

                           if (!this.usePC && hadCharData) {
                              needsMerging = true;
                           }
                        } else {
                           if (ch != '[') {
                              throw new XMLStreamException("unexpected character in markup " + this.printable(ch), this.getLocation());
                           }

                           oldStart = this.posStart;
                           oldEnd = this.posEnd;
                           this.parseCDATA();
                           int cdStart = this.posStart;
                           i = this.posEnd;
                           this.posStart = oldStart;
                           this.posEnd = oldEnd;
                           int cdLen = i - cdStart;
                           if (cdLen > 0) {
                              if (hadCharData) {
                                 if (!this.usePC) {
                                    if (this.posEnd > this.posStart) {
                                       this.joinPC();
                                    } else {
                                       this.usePC = true;
                                       this.pcStart = this.pcEnd = 0;
                                    }
                                 }

                                 if (this.pcEnd + cdLen >= this.pc.length) {
                                    this.ensurePC(this.pcEnd + cdLen);
                                 }

                                 System.arraycopy(this.buf, cdStart, this.pc, this.pcEnd, cdLen);
                                 this.pcEnd += cdLen;
                              } else {
                                 needsMerging = true;
                                 this.posStart = cdStart;
                                 this.posEnd = i;
                              }

                              hadCharData = true;
                           } else if (!this.usePC && hadCharData) {
                              needsMerging = true;
                           }
                        }
                     } else {
                        if (ch != '?') {
                           if (this.isNameStartChar(ch)) {
                              if (!this.tokenize && hadCharData) {
                                 this.seenStartTag = true;
                                 return this.eventType = 4;
                              }

                              return this.eventType = this.parseStartTag();
                           }

                           throw new XMLStreamException("unexpected character in markup " + this.printable(ch), this.getLocation());
                        }

                        this.parsePI();
                        if (this.tokenize) {
                           return this.eventType = 3;
                        }

                        if (!this.usePC && hadCharData) {
                           needsMerging = true;
                        }
                     }
                     break;
                  }

                  if (ch == '&') {
                     if (this.tokenize && hadCharData) {
                        this.seenAmpersand = true;
                        return this.eventType = 4;
                     }

                     oldStart = this.posStart;
                     oldEnd = this.posEnd;
                     char[] resolvedEntity = this.parseEntityRef();
                     if (!this.getConfigurationContext().isReplacingEntities()) {
                        return this.eventType = 9;
                     }

                     this.eventType = 4;
                     if (resolvedEntity == null) {
                        if (this.entityRefName == null) {
                           this.entityRefName = this.newString(this.buf, this.posStart, this.posEnd - this.posStart);
                        }

                        throw new XMLStreamException("could not resolve entity named '" + this.printable(this.entityRefName) + "'", this.getLocation());
                     }

                     this.posStart = oldStart;
                     this.posEnd = oldEnd;
                     if (!this.usePC) {
                        if (hadCharData) {
                           this.joinPC();
                           needsMerging = false;
                        } else {
                           this.usePC = true;
                           this.pcStart = this.pcEnd = 0;
                        }
                     }

                     i = 0;

                     while(true) {
                        if (i >= resolvedEntity.length) {
                           break label245;
                        }

                        if (this.pcEnd >= this.pc.length) {
                           this.ensurePC(this.pcEnd);
                        }

                        this.pc[this.pcEnd++] = resolvedEntity[i];
                        ++i;
                     }
                  }

                  if (needsMerging) {
                     this.joinPC();
                     needsMerging = false;
                  }

                  hadCharData = true;
                  boolean normalizedCR = false;

                  do {
                     if (ch == '\r') {
                        normalizedCR = true;
                        this.posEnd = this.pos - 1;
                        if (!this.usePC) {
                           if (this.posEnd > this.posStart) {
                              this.joinPC();
                           } else {
                              this.usePC = true;
                              this.pcStart = this.pcEnd = 0;
                           }
                        }

                        if (this.pcEnd >= this.pc.length) {
                           this.ensurePC(this.pcEnd);
                        }

                        this.pc[this.pcEnd++] = '\n';
                     } else if (ch == '\n') {
                        if (!normalizedCR && this.usePC) {
                           if (this.pcEnd >= this.pc.length) {
                              this.ensurePC(this.pcEnd);
                           }

                           this.pc[this.pcEnd++] = '\n';
                        }

                        normalizedCR = false;
                     } else {
                        if (this.usePC) {
                           if (this.pcEnd >= this.pc.length) {
                              this.ensurePC(this.pcEnd);
                           }

                           this.pc[this.pcEnd++] = ch;
                        }

                        normalizedCR = false;
                     }

                     ch = this.more();
                  } while(ch != '<' && ch != '&');

                  this.posEnd = this.pos - 1;
               }

               ch = this.more();
            }
         }
      } catch (EOFException var9) {
         throw new XMLStreamException(var9);
      }
   }

   protected int parseProlog() throws XMLStreamException {
      try {
         char ch;
         if (this.seenMarkup) {
            ch = this.buf[this.pos - 1];
         } else {
            ch = this.more();
         }

         if (this.eventType == 7) {
            if (ch == '\ufffe') {
               throw new XMLStreamException("first character in input was UNICODE noncharacter (0xFFFE)- input requires int swapping", this.getLocation());
            }

            if (ch == '\ufeff') {
               ch = this.more();
            }
         }

         this.seenMarkup = false;
         boolean gotS = false;
         this.posStart = this.pos - 1;

         while(true) {
            if (ch == '<') {
               if (gotS && this.tokenize) {
                  this.posEnd = this.pos - 1;
                  this.seenMarkup = true;
                  return this.eventType = 6;
               }

               ch = this.more();
               if (ch == '?') {
                  boolean isXMLDecl = this.parsePI();
                  if (this.tokenize) {
                     if (isXMLDecl) {
                        return this.eventType = 7;
                     }

                     return this.eventType = 3;
                  }
               } else {
                  if (ch != '!') {
                     if (ch == '/') {
                        throw new XMLStreamException("expected start tag name and not " + this.printable(ch), this.getLocation());
                     }

                     if (this.isNameStartChar(ch)) {
                        this.seenRoot = true;
                        return this.parseStartTag();
                     }

                     throw new XMLStreamException("expected start tag name and not " + this.printable(ch), this.getLocation());
                  }

                  ch = this.more();
                  if (ch == 'D') {
                     if (this.seenDocdecl) {
                        throw new XMLStreamException("only one docdecl allowed in XML document", this.getLocation());
                     }

                     this.seenDocdecl = true;
                     this.parseDocdecl();
                     if (this.tokenize) {
                        return this.eventType = 11;
                     }
                  } else {
                     if (ch != '-') {
                        throw new XMLStreamException("unexpected markup <!" + this.printable(ch), this.getLocation());
                     }

                     this.parseComment();
                     if (this.tokenize) {
                        return this.eventType = 5;
                     }
                  }
               }
            } else {
               if (!this.isS(ch)) {
                  throw new XMLStreamException("only whitespace content allowed before start tag and not " + this.printable(ch), this.getLocation());
               }

               gotS = true;
            }

            ch = this.more();
         }
      } catch (EOFException var4) {
         throw new XMLStreamException(var4);
      }
   }

   protected int parseEpilog() throws XMLStreamException {
      if (this.eventType == 8) {
         throw new XMLStreamException("already reached end document", this.getLocation());
      } else if (this.reachedEnd) {
         return this.eventType = 8;
      } else {
         boolean gotS = false;

         try {
            char ch;
            if (this.seenMarkup) {
               ch = this.buf[this.pos - 1];
            } else {
               ch = this.more();
            }

            this.seenMarkup = false;
            this.posStart = this.pos - 1;

            while(true) {
               if (ch == '<') {
                  if (gotS && this.tokenize) {
                     this.posEnd = this.pos - 1;
                     this.seenMarkup = true;
                     return this.eventType = 6;
                  }

                  ch = this.more();
                  if (ch == '?') {
                     this.parsePI();
                     if (this.tokenize) {
                        return this.eventType = 3;
                     }
                  } else {
                     if (ch != '!') {
                        if (ch == '/') {
                           throw new XMLStreamException("end tag not allowed in epilog but got " + this.printable(ch), this.getLocation());
                        }

                        if (this.isNameStartChar(ch)) {
                           throw new XMLStreamException("start tag not allowed in epilog but got " + this.printable(ch), this.getLocation());
                        }

                        throw new XMLStreamException("in epilog expected ignorable content and not " + this.printable(ch), this.getLocation());
                     }

                     ch = this.more();
                     if (ch == 'D') {
                        this.parseDocdecl();
                        if (this.tokenize) {
                           return this.eventType = 11;
                        }
                     } else {
                        if (ch != '-') {
                           throw new XMLStreamException("unexpected markup <!" + this.printable(ch), this.getLocation());
                        }

                        this.parseComment();
                        if (this.tokenize) {
                           return this.eventType = 5;
                        }
                     }
                  }
               } else {
                  if (!this.isS(ch)) {
                     throw new XMLStreamException("in epilog non whitespace content is not allowed but got " + this.printable(ch), this.getLocation());
                  }

                  gotS = true;
               }

               ch = this.more();
            }
         } catch (EOFException var3) {
            this.reachedEnd = true;
            if (this.tokenize && gotS) {
               this.posEnd = this.pos;
               return this.eventType = 6;
            } else {
               return this.eventType = 8;
            }
         }
      }
   }

   public int parseEndTag() throws XMLStreamException {
      try {
         char ch = this.more();
         if (!this.isNameStartChar(ch)) {
            throw new XMLStreamException("expected name start and not " + this.printable(ch), this.getLocation());
         } else {
            this.posStart = this.pos - 3;
            int nameStart = this.pos - 1 + this.bufAbsoluteStart;

            do {
               ch = this.more();
            } while(this.isNameChar(ch));

            int last = this.pos - 1;
            int off = nameStart - this.bufAbsoluteStart;
            int len = last - off;
            char[] cbuf = this.elRawName[this.depth];
            String startname;
            if (this.elRawNameEnd[this.depth] != len) {
               String startname = new String(cbuf, 0, this.elRawNameEnd[this.depth]);
               startname = new String(this.buf, off, len);
               throw new XMLStreamException("end tag name '" + startname + "' must match start tag name '" + startname + "'", this.getLocation());
            } else {
               for(int i = 0; i < len; ++i) {
                  if (this.buf[off++] != cbuf[i]) {
                     startname = new String(cbuf, 0, len);
                     String endname = new String(this.buf, off - i - 1, len);
                     throw new XMLStreamException("end tag name '" + endname + "' must be the same as start tag '" + startname + "'", this.getLocation());
                  }
               }

               while(this.isS(ch)) {
                  ch = this.more();
               }

               if (ch != '>') {
                  throw new XMLStreamException("expected > to finsh end tag not " + this.printable(ch), this.getLocation());
               } else {
                  this.posEnd = this.pos;
                  this.pastEndTag = true;
                  return this.eventType = 2;
               }
            }
         }
      } catch (EOFException var10) {
         throw new XMLStreamException(var10);
      }
   }

   public int parseStartTag() throws XMLStreamException {
      try {
         ++this.depth;
         this.posStart = this.pos - 2;
         this.emptyElementTag = false;
         this.attributeCount = 0;
         this.localNamespaceEnd = 0;
         int nameStart = this.pos - 1 + this.bufAbsoluteStart;
         int colonPos = -1;
         char ch = this.buf[this.pos - 1];
         if (ch == ':' && this.processNamespaces) {
            throw new XMLStreamException("when namespaces processing enabled colon can not be at element name start", this.getLocation());
         } else {
            while(true) {
               ch = this.more();
               if (!this.isNameChar(ch)) {
                  this.ensureElementsCapacity();
                  int elLen = this.pos - 1 - (nameStart - this.bufAbsoluteStart);
                  if (this.elRawName[this.depth] == null || this.elRawName[this.depth].length < elLen) {
                     this.elRawName[this.depth] = new char[2 * elLen];
                  }

                  System.arraycopy(this.buf, nameStart - this.bufAbsoluteStart, this.elRawName[this.depth], 0, elLen);
                  this.elRawNameEnd[this.depth] = elLen;
                  String name = null;
                  String prefix = null;
                  if (this.processNamespaces) {
                     if (colonPos != -1) {
                        prefix = this.elPrefix[this.depth] = this.newString(this.buf, nameStart - this.bufAbsoluteStart, colonPos - nameStart);
                        name = this.elName[this.depth] = this.newString(this.buf, colonPos + 1 - this.bufAbsoluteStart, this.pos - 2 - (colonPos - this.bufAbsoluteStart));
                     } else {
                        prefix = this.elPrefix[this.depth] = null;
                        name = this.elName[this.depth] = this.newString(this.buf, nameStart - this.bufAbsoluteStart, elLen);
                     }
                  } else {
                     name = this.elName[this.depth] = this.newString(this.buf, nameStart - this.bufAbsoluteStart, elLen);
                  }

                  while(true) {
                     while(this.isS(ch)) {
                        ch = this.more();
                     }

                     if (ch == '>') {
                        break;
                     }

                     if (ch == '/') {
                        if (this.emptyElementTag) {
                           throw new XMLStreamException("repeated / in tag declaration", this.getLocation());
                        }

                        this.emptyElementTag = true;
                        ch = this.more();
                        if (ch != '>') {
                           throw new XMLStreamException("expected > to end empty tag not " + this.printable(ch), this.getLocation());
                        }
                        break;
                     }

                     if (!this.isNameStartChar(ch)) {
                        throw new XMLStreamException("start tag unexpected character " + this.printable(ch), this.getLocation());
                     }

                     ch = this.parseAttribute();
                     ch = this.more();
                  }

                  int i;
                  String attrPrefix;
                  String attrUri;
                  if (this.processNamespaces) {
                     String uri = this.getNamespaceURI(prefix);
                     if (uri == null) {
                        if (prefix != null) {
                           throw new XMLStreamException("could not determine namespace bound to element prefix " + prefix, this.getLocation());
                        }

                        uri = "";
                     }

                     this.elUri[this.depth] = uri;

                     for(i = 0; i < this.attributeCount; ++i) {
                        attrPrefix = this.attributePrefix[i];
                        if (attrPrefix != null) {
                           attrUri = this.getNamespaceURI(attrPrefix);
                           if (attrUri == null) {
                              throw new XMLStreamException("could not determine namespace bound to attribute prefix " + attrPrefix, this.getLocation());
                           }

                           this.attributeUri[i] = attrUri;
                        } else {
                           this.attributeUri[i] = "";
                        }
                     }

                     for(int i = 1; i < this.attributeCount; ++i) {
                        for(int j = 0; j < i; ++j) {
                           if (this.attributeUri[j] == this.attributeUri[i] && (this.allStringsInterned && this.attributeName[j].equals(this.attributeName[i]) || !this.allStringsInterned && this.attributeNameHash[j] == this.attributeNameHash[i] && this.attributeName[j].equals(this.attributeName[i]))) {
                              String attr1 = this.attributeName[j];
                              if (this.attributeUri[j] != null) {
                                 attr1 = this.attributeUri[j] + ":" + attr1;
                              }

                              String attr2 = this.attributeName[i];
                              if (this.attributeUri[i] != null) {
                                 attr2 = this.attributeUri[i] + ":" + attr2;
                              }

                              throw new XMLStreamException("duplicated attributes " + attr1 + " and " + attr2, this.getLocation());
                           }
                        }
                     }
                  } else {
                     for(int i = 1; i < this.attributeCount; ++i) {
                        for(i = 0; i < i; ++i) {
                           if (this.allStringsInterned && this.attributeName[i].equals(this.attributeName[i]) || !this.allStringsInterned && this.attributeNameHash[i] == this.attributeNameHash[i] && this.attributeName[i].equals(this.attributeName[i])) {
                              attrPrefix = this.attributeName[i];
                              attrUri = this.attributeName[i];
                              throw new XMLStreamException("duplicated attributes " + attrPrefix + " and " + attrUri, this.getLocation());
                           }
                        }
                     }
                  }

                  this.elNamespaceCount[this.depth] = this.namespaceEnd;
                  this.posEnd = this.pos;
                  if (this.defaultAttributes != null) {
                     if (prefix != null) {
                        this.addDefaultAttributes(prefix + ":" + name);
                     } else {
                        this.addDefaultAttributes(name);
                     }
                  }

                  return this.eventType = 1;
               }

               if (ch == ':' && this.processNamespaces) {
                  if (colonPos != -1) {
                     throw new XMLStreamException("only one colon is allowed in name of element when namespaces are enabled", this.getLocation());
                  }

                  colonPos = this.pos - 1 + this.bufAbsoluteStart;
               }
            }
         }
      } catch (EOFException var13) {
         throw new XMLStreamException(var13);
      }
   }

   protected void addDefaultAttributes(String elementName) throws XMLStreamException {
      if (this.defaultAttributes != null) {
         DTDAttlist attList = (DTDAttlist)this.defaultAttributes.get(elementName);
         if (elementName != null && attList != null) {
            DTDAttribute[] atts = attList.getAttribute();

            for(int i = 0; i < atts.length; ++i) {
               DTDAttribute att = atts[i];
               if (att.getDefaultValue() != null) {
                  boolean found = false;
                  int count = this.attributeCount;

                  for(int j = 0; j < count; ++j) {
                     if (this.attributeName[j].equals(att.getName())) {
                        found = true;
                        break;
                     }
                  }

                  if (!found) {
                     ++this.attributeCount;
                     this.ensureAttributesCapacity(this.attributeCount);
                     this.attributePrefix[this.attributeCount - 1] = "";
                     this.attributeUri[this.attributeCount - 1] = "";
                     this.attributeName[this.attributeCount - 1] = att.getName();
                     this.attributeValue[this.attributeCount - 1] = att.getDefaultValue();
                  }
               }
            }

         }
      }
   }

   protected char parseAttribute() throws XMLStreamException {
      try {
         int prevPosStart = this.posStart + this.bufAbsoluteStart;
         int nameStart = this.pos - 1 + this.bufAbsoluteStart;
         int colonPos = -1;
         char ch = this.buf[this.pos - 1];
         if (ch == ':' && this.processNamespaces) {
            throw new XMLStreamException("when namespaces processing enabled colon can not be at attribute name start", this.getLocation());
         } else {
            boolean startsWithXmlns = this.processNamespaces && ch == 'x';
            int xmlnsPos = 0;

            for(ch = this.more(); this.isNameChar(ch); ch = this.more()) {
               if (this.processNamespaces) {
                  if (startsWithXmlns && xmlnsPos < 5) {
                     ++xmlnsPos;
                     if (xmlnsPos == 1) {
                        if (ch != 'm') {
                           startsWithXmlns = false;
                        }
                     } else if (xmlnsPos == 2) {
                        if (ch != 'l') {
                           startsWithXmlns = false;
                        }
                     } else if (xmlnsPos == 3) {
                        if (ch != 'n') {
                           startsWithXmlns = false;
                        }
                     } else if (xmlnsPos == 4) {
                        if (ch != 's') {
                           startsWithXmlns = false;
                        }
                     } else if (xmlnsPos == 5 && ch != ':') {
                        throw new XMLStreamException("after xmlns in attribute name must be colonwhen namespaces are enabled", this.getLocation());
                     }
                  }

                  if (ch == ':') {
                     if (colonPos != -1) {
                        throw new XMLStreamException("only one colon is allowed in attribute name when namespaces are enabled", this.getLocation());
                     }

                     colonPos = this.pos - 1 + this.bufAbsoluteStart;
                  }
               }
            }

            this.ensureAttributesCapacity(this.attributeCount);
            String name = null;
            String prefix = null;
            if (this.processNamespaces) {
               if (xmlnsPos < 4) {
                  startsWithXmlns = false;
               }

               if (startsWithXmlns) {
                  if (colonPos != -1) {
                     name = this.newString(this.buf, colonPos - this.bufAbsoluteStart + 1, this.pos - 2 - (colonPos - this.bufAbsoluteStart));
                  }
               } else {
                  if (colonPos != -1) {
                     prefix = this.attributePrefix[this.attributeCount] = this.newString(this.buf, nameStart - this.bufAbsoluteStart, colonPos - nameStart);
                     name = this.attributeName[this.attributeCount] = this.newString(this.buf, colonPos - this.bufAbsoluteStart + 1, this.pos - 2 - (colonPos - this.bufAbsoluteStart));
                  } else {
                     prefix = this.attributePrefix[this.attributeCount] = null;
                     name = this.attributeName[this.attributeCount] = this.newString(this.buf, nameStart - this.bufAbsoluteStart, this.pos - 1 - (nameStart - this.bufAbsoluteStart));
                  }

                  if (!this.allStringsInterned) {
                     this.attributeNameHash[this.attributeCount] = name.hashCode();
                  }
               }
            } else {
               name = this.attributeName[this.attributeCount] = this.newString(this.buf, nameStart - this.bufAbsoluteStart, this.pos - 1 - (nameStart - this.bufAbsoluteStart));
               if (!this.allStringsInterned) {
                  this.attributeNameHash[this.attributeCount] = name.hashCode();
               }
            }

            while(this.isS(ch)) {
               ch = this.more();
            }

            if (ch != '=') {
               throw new XMLStreamException("expected = after attribute name", this.getLocation());
            } else {
               for(ch = this.more(); this.isS(ch); ch = this.more()) {
               }

               char delimit = ch;
               if (ch != '"' && ch != '\'') {
                  throw new XMLStreamException("attribute value must start with quotation or apostrophe not " + this.printable(ch), this.getLocation());
               } else {
                  boolean normalizedCR = false;
                  this.usePC = false;
                  this.pcStart = this.pcEnd;
                  this.posStart = this.pos;

                  while(true) {
                     ch = this.more();
                     int prefixHash;
                     if (ch == delimit) {
                        if (this.processNamespaces && startsWithXmlns) {
                           String ns = null;
                           if (!this.usePC) {
                              ns = this.newStringIntern(this.buf, this.posStart, this.pos - 1 - this.posStart);
                           } else {
                              ns = this.newStringIntern(this.pc, this.pcStart, this.pcEnd - this.pcStart);
                           }

                           this.ensureNamespacesCapacity(this.namespaceEnd);
                           prefixHash = -1;
                           if (colonPos != -1) {
                              if (ns.length() == 0) {
                                 throw new XMLStreamException("non-default namespace can not be declared to be empty string", this.getLocation());
                              }

                              this.namespacePrefix[this.namespaceEnd] = name;
                              if (!this.allStringsInterned) {
                                 prefixHash = this.namespacePrefixHash[this.namespaceEnd] = name.hashCode();
                              }
                           } else {
                              this.namespacePrefix[this.namespaceEnd] = null;
                              if (!this.allStringsInterned) {
                                 prefixHash = this.namespacePrefixHash[this.namespaceEnd] = -1;
                              }
                           }

                           this.namespaceUri[this.namespaceEnd] = ns;
                           int startNs = this.elNamespaceCount[this.depth - 1];
                           int i = this.namespaceEnd - 1;

                           while(true) {
                              if (i < startNs) {
                                 ++this.namespaceEnd;
                                 break;
                              }

                              if ((this.allStringsInterned || name == null) && this.namespacePrefix[i] == name || !this.allStringsInterned && name != null && this.namespacePrefixHash[i] == prefixHash && name.equals(this.namespacePrefix[i])) {
                                 String s = name == null ? "default" : "'" + name + "'";
                                 throw new XMLStreamException("duplicated namespace declaration for " + s + " prefix", this.getLocation());
                              }

                              --i;
                           }
                        } else {
                           if (!this.usePC) {
                              this.attributeValue[this.attributeCount] = new String(this.buf, this.posStart, this.pos - 1 - this.posStart);
                           } else {
                              this.attributeValue[this.attributeCount] = new String(this.pc, this.pcStart, this.pcEnd - this.pcStart);
                           }

                           ++this.attributeCount;
                        }

                        this.posStart = prevPosStart - this.bufAbsoluteStart;
                        return ch;
                     }

                     if (ch == '<') {
                        throw new XMLStreamException("markup not allowed inside attribute value - illegal < ", this.getLocation());
                     }

                     if (ch == '&') {
                        this.posEnd = this.pos - 1;
                        if (!this.usePC) {
                           boolean hadCharData = this.posEnd > this.posStart;
                           if (hadCharData) {
                              this.joinPC();
                           } else {
                              this.usePC = true;
                              this.pcStart = this.pcEnd = 0;
                           }
                        }

                        char[] resolvedEntity = this.parseEntityRef();
                        if (resolvedEntity == null) {
                           if (this.entityRefName == null) {
                              this.entityRefName = this.newString(this.buf, this.posStart, this.posEnd - this.posStart);
                           }

                           throw new XMLStreamException("could not resolve entity named '" + this.printable(this.entityRefName) + "'", this.getLocation());
                        }

                        for(prefixHash = 0; prefixHash < resolvedEntity.length; ++prefixHash) {
                           if (this.pcEnd >= this.pc.length) {
                              this.ensurePC(this.pcEnd);
                           }

                           this.pc[this.pcEnd++] = resolvedEntity[prefixHash];
                        }
                     } else if (ch != '\t' && ch != '\n' && ch != '\r') {
                        if (this.usePC) {
                           if (this.pcEnd >= this.pc.length) {
                              this.ensurePC(this.pcEnd);
                           }

                           this.pc[this.pcEnd++] = ch;
                        }
                     } else {
                        if (!this.usePC) {
                           this.posEnd = this.pos - 1;
                           if (this.posEnd > this.posStart) {
                              this.joinPC();
                           } else {
                              this.usePC = true;
                              this.pcEnd = this.pcStart = 0;
                           }
                        }

                        if (this.pcEnd >= this.pc.length) {
                           this.ensurePC(this.pcEnd);
                        }

                        if (ch != '\n' || !normalizedCR) {
                           this.pc[this.pcEnd++] = ' ';
                        }
                     }

                     normalizedCR = ch == '\r';
                  }
               }
            }
         }
      } catch (EOFException var16) {
         throw new XMLStreamException(var16);
      }
   }

   protected char[] parseEntityRef() throws XMLStreamException {
      boolean replace = this.getConfigurationContext().isReplacingEntities();

      try {
         this.entityRefName = null;
         this.posStart = this.pos;
         char ch = this.more();
         if (ch == '#') {
            char charRef = 0;
            ch = this.more();
            if (ch == 'x') {
               label152:
               while(true) {
                  while(true) {
                     while(true) {
                        ch = this.more();
                        if (ch < '0' || ch > '9') {
                           if (ch < 'a' || ch > 'f') {
                              if (ch < 'A' || ch > 'F') {
                                 if (ch != ';') {
                                    throw new XMLStreamException("character reference (with hex value) may not contain " + this.printable(ch), this.getLocation());
                                 }
                                 break label152;
                              }

                              charRef = (char)(charRef * 16 + (ch - 55));
                           } else {
                              charRef = (char)(charRef * 16 + (ch - 87));
                           }
                        } else {
                           charRef = (char)(charRef * 16 + (ch - 48));
                        }
                     }
                  }
               }
            } else {
               while(ch >= '0' && ch <= '9') {
                  charRef = (char)(charRef * 10 + (ch - 48));
                  ch = this.more();
               }

               if (ch != ';') {
                  throw new XMLStreamException("character reference (with decimal value) may not contain " + this.printable(ch), this.getLocation());
               }
            }

            this.posEnd = this.pos - 1;
            this.charRefOneCharBuf[0] = charRef;
            if (!replace) {
               this.text = this.newString(this.charRefOneCharBuf, 0, 1);
            }

            return this.charRefOneCharBuf;
         } else {
            do {
               ch = this.more();
            } while(ch != ';');

            this.posEnd = this.pos - 1;
            int len = this.posEnd - this.posStart;
            if (len == 2 && this.buf[this.posStart] == 'l' && this.buf[this.posStart + 1] == 't') {
               if (!replace) {
                  this.text = "<";
               }

               this.charRefOneCharBuf[0] = '<';
               return this.charRefOneCharBuf;
            } else if (len == 3 && this.buf[this.posStart] == 'a' && this.buf[this.posStart + 1] == 'm' && this.buf[this.posStart + 2] == 'p') {
               if (!replace) {
                  this.text = "&";
               }

               this.charRefOneCharBuf[0] = '&';
               return this.charRefOneCharBuf;
            } else if (len == 2 && this.buf[this.posStart] == 'g' && this.buf[this.posStart + 1] == 't') {
               if (!replace) {
                  this.text = ">";
               }

               this.charRefOneCharBuf[0] = '>';
               return this.charRefOneCharBuf;
            } else if (len == 4 && this.buf[this.posStart] == 'a' && this.buf[this.posStart + 1] == 'p' && this.buf[this.posStart + 2] == 'o' && this.buf[this.posStart + 3] == 's') {
               if (!replace) {
                  this.text = "'";
               }

               this.charRefOneCharBuf[0] = '\'';
               return this.charRefOneCharBuf;
            } else if (len == 4 && this.buf[this.posStart] == 'q' && this.buf[this.posStart + 1] == 'u' && this.buf[this.posStart + 2] == 'o' && this.buf[this.posStart + 3] == 't') {
               if (!replace) {
                  this.text = "\"";
               }

               this.charRefOneCharBuf[0] = '"';
               return this.charRefOneCharBuf;
            } else {
               char[] result = this.lookupEntityReplacement(len);
               if (result != null) {
                  return result;
               } else {
                  if (!replace) {
                     this.text = null;
                  }

                  return null;
               }
            }
         }
      } catch (EOFException var5) {
         throw new XMLStreamException(var5);
      }
   }

   protected char[] lookupEntityReplacement(int entitNameLen) throws XMLStreamException {
      int i;
      if (!this.allStringsInterned) {
         i = fastHash(this.buf, this.posStart, this.posEnd - this.posStart);

         label53:
         for(int i = this.entityEnd - 1; i >= 0; --i) {
            if (i == this.entityNameHash[i] && entitNameLen == this.entityNameBuf[i].length) {
               char[] entityBuf = this.entityNameBuf[i];

               for(int j = 0; j < entitNameLen; ++j) {
                  if (this.buf[this.posStart + j] != entityBuf[j]) {
                     continue label53;
                  }
               }

               if (this.tokenize) {
                  this.text = this.entityReplacement[i];
               }

               return this.entityReplacementBuf[i];
            }
         }
      } else {
         this.entityRefName = this.newString(this.buf, this.posStart, this.posEnd - this.posStart);

         for(i = this.entityEnd - 1; i >= 0; --i) {
            if (this.entityRefName == this.entityName[i]) {
               if (this.tokenize) {
                  this.text = this.entityReplacement[i];
               }

               return this.entityReplacementBuf[i];
            }
         }
      }

      return null;
   }

   protected void parseComment() throws XMLStreamException {
      try {
         char ch = this.more();
         if (ch != '-') {
            throw new XMLStreamException("expected <!-- for COMMENT start", this.getLocation());
         } else {
            if (this.tokenize) {
               this.posStart = this.pos;
            }

            int curLine = this.lineNumber;
            int curColumn = this.columnNumber;

            try {
               boolean seenDash = false;
               boolean seenDashDash = false;

               while(true) {
                  ch = this.more();
                  if (seenDashDash && ch != '>') {
                     throw new XMLStreamException("in COMMENT after two dashes (--) next character must be > not " + this.printable(ch), this.getLocation());
                  }

                  if (ch == '-') {
                     if (!seenDash) {
                        seenDash = true;
                     } else {
                        seenDashDash = true;
                        seenDash = false;
                     }
                  } else if (ch == '>') {
                     if (seenDashDash) {
                        break;
                     }

                     seenDashDash = false;
                     seenDash = false;
                  } else {
                     seenDash = false;
                  }
               }
            } catch (EOFException var6) {
               throw new XMLStreamException("COMMENT started on line " + curLine + " and column " + curColumn + " was not closed", this.getLocation(), var6);
            }

            if (this.tokenize) {
               this.posEnd = this.pos - 3;
            }

         }
      } catch (EOFException var7) {
         throw new XMLStreamException(var7);
      }
   }

   public String getPITarget() {
      return this.eventType != 3 ? null : this.piTarget;
   }

   public String getPIData() {
      return this.eventType != 3 ? null : this.piData;
   }

   public NamespaceContext getNamespaceContext() {
      return new ReadOnlyNamespaceContextBase(this.namespacePrefix, this.namespaceUri, this.namespaceEnd);
   }

   protected boolean parsePI() throws XMLStreamException {
      boolean isXMLDecl = false;
      this.piTarget = null;
      this.piData = null;
      if (this.tokenize) {
         this.posStart = this.pos;
      }

      int curLine = this.lineNumber;
      int curColumn = this.columnNumber;
      int piTargetStart = this.pos;
      int piTargetBegin = this.pos;
      int piTargetEnd = -1;

      try {
         boolean seenQ = false;

         label79:
         while(true) {
            while(true) {
               while(true) {
                  char ch = this.more();
                  if (ch != '?') {
                     if (ch != '>') {
                        if (piTargetEnd == -1 && this.isS(ch)) {
                           piTargetEnd = this.pos - 1;
                           piTargetBegin = piTargetEnd;
                           this.piTarget = new String(this.buf, piTargetStart, piTargetEnd - piTargetStart);
                           if (piTargetEnd - piTargetStart == 3 && (this.buf[piTargetStart] == 'x' || this.buf[piTargetStart] == 'X') && (this.buf[piTargetStart + 1] == 'm' || this.buf[piTargetStart + 1] == 'M') && (this.buf[piTargetStart + 2] == 'l' || this.buf[piTargetStart + 2] == 'L')) {
                              if (piTargetStart != 2) {
                                 throw new XMLStreamException("processing instruction can not have PITarget with reserveld xml name", this.getLocation());
                              }

                              if (this.buf[piTargetStart] != 'x' && this.buf[piTargetStart + 1] != 'm' && this.buf[piTargetStart + 2] != 'l') {
                                 throw new XMLStreamException("XMLDecl must have xml name in lowercase", this.getLocation());
                              }

                              this.parseXmlDecl(ch);
                              isXMLDecl = true;
                              break label79;
                           }
                        }

                        seenQ = false;
                     } else {
                        if (seenQ) {
                           break label79;
                        }

                        seenQ = false;
                     }
                  } else {
                     seenQ = true;
                  }
               }
            }
         }
      } catch (EOFException var9) {
         throw new XMLStreamException("processing instruction started on line " + curLine + " and column " + curColumn + " was not closed", this.getLocation(), var9);
      }

      if (piTargetEnd == -1) {
         throw new XMLStreamException("processing instruction must have PITarget name", this.getLocation());
      } else {
         if (this.tokenize) {
            this.posEnd = this.pos - 2;
         }

         this.piData = new String(this.buf, piTargetBegin, this.posEnd - piTargetBegin);
         return isXMLDecl;
      }
   }

   protected char requireInput(char ch, char[] input) throws XMLStreamException {
      for(int i = 0; i < input.length; ++i) {
         if (ch != input[i]) {
            throw new XMLStreamException("expected " + this.printable(input[i]) + " in " + new String(input) + " and not " + this.printable(ch), this.getLocation());
         }

         try {
            ch = this.more();
         } catch (EOFException var5) {
            throw new XMLStreamException(var5);
         }
      }

      return ch;
   }

   protected char requireNextS() throws XMLStreamException {
      char ch;
      try {
         ch = this.more();
      } catch (EOFException var3) {
         throw new XMLStreamException(var3);
      }

      if (!this.isS(ch)) {
         throw new XMLStreamException("white space is required and not " + this.printable(ch), this.getLocation());
      } else {
         return this.skipS(ch);
      }
   }

   protected char skipS(char ch) throws XMLStreamException {
      try {
         while(this.isS(ch)) {
            ch = this.more();
         }

         return ch;
      } catch (EOFException var3) {
         throw new XMLStreamException(var3);
      }
   }

   protected void parseXmlDecl(char ch) throws XMLStreamException {
      try {
         ch = this.skipS(ch);
         ch = this.requireInput(ch, VERSION);
         ch = this.skipS(ch);
         if (ch != '=') {
            throw new XMLStreamException("expected equals sign (=) after version and not " + this.printable(ch), this.getLocation());
         } else {
            ch = this.more();
            ch = this.skipS(ch);
            if (ch != '\'' && ch != '"') {
               throw new XMLStreamException("expected apostrophe (') or quotation mark (\") after version and not " + this.printable(ch), this.getLocation());
            } else {
               char quotChar = ch;
               int versionStart = this.pos;

               for(ch = this.more(); ch != quotChar; ch = this.more()) {
                  if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9') && ch != '_' && ch != '.' && ch != ':' && ch != '-') {
                     throw new XMLStreamException("<?xml version value expected to be in ([a-zA-Z0-9_.:] | '-') not " + this.printable(ch), this.getLocation());
                  }
               }

               int versionEnd = this.pos - 1;
               this.parseXmlDeclWithVersion(versionStart, versionEnd);
            }
         }
      } catch (EOFException var5) {
         throw new XMLStreamException(var5);
      }
   }

   protected void parseXmlDeclWithVersion(int versionStart, int versionEnd) throws XMLStreamException {
      try {
         if (versionEnd - versionStart == 3 && this.buf[versionStart] == '1' && this.buf[versionStart + 1] == '.' && this.buf[versionStart + 2] == '0') {
            this.xmlVersion = new String(this.buf, versionStart, versionEnd - versionStart);
            char ch = this.more();
            ch = this.skipS(ch);
            if (ch != '?') {
               ch = this.skipS(ch);
               ch = this.requireInput(ch, ENCODING);
               ch = this.skipS(ch);
               if (ch != '=') {
                  throw new XMLStreamException("expected equals sign (=) after encoding and not " + this.printable(ch), this.getLocation());
               }

               ch = this.more();
               ch = this.skipS(ch);
               if (ch != '\'' && ch != '"') {
                  throw new XMLStreamException("expected apostrophe (') or quotation mark (\") after encoding and not " + this.printable(ch), this.getLocation());
               }

               char quotChar = ch;
               int encodingStart = this.pos;
               ch = this.more();
               if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z')) {
                  throw new XMLStreamException("<?xml encoding name expected to start with [A-Za-z] not " + this.printable(ch), this.getLocation());
               }

               for(ch = this.more(); ch != quotChar; ch = this.more()) {
                  if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9') && ch != '.' && ch != '_' && ch != '-') {
                     throw new XMLStreamException("<?xml encoding value expected to be in ([A-Za-z0-9._] | '-') not " + this.printable(ch), this.getLocation());
                  }
               }

               int encodingEnd = this.pos - 1;
               this.charEncodingScheme = this.newString(this.buf, encodingStart, encodingEnd - encodingStart);
               ch = this.more();
               ch = this.skipS(ch);
               if (ch != '?') {
                  ch = this.skipS(ch);
                  ch = this.requireInput(ch, STANDALONE);
                  ch = this.skipS(ch);
                  if (ch != '=') {
                     throw new XMLStreamException("expected equals sign (=) after standalone and not " + this.printable(ch), this.getLocation());
                  }

                  ch = this.more();
                  ch = this.skipS(ch);
                  if (ch != '\'' && ch != '"') {
                     throw new XMLStreamException("expected apostrophe (') or quotation mark (\") after encoding and not " + this.printable(ch), this.getLocation());
                  }

                  quotChar = ch;
                  int standaloneStart = this.pos;
                  ch = this.more();
                  if (ch == 'y') {
                     ch = this.requireInput(ch, YES);
                     this.standalone = true;
                     this.standaloneSet = true;
                  } else {
                     if (ch != 'n') {
                        throw new XMLStreamException("expected 'yes' or 'no' after standalone and not " + this.printable(ch), this.getLocation());
                     }

                     ch = this.requireInput(ch, NO);
                     this.standalone = false;
                     this.standaloneSet = true;
                  }

                  if (ch != quotChar) {
                     throw new XMLStreamException("expected " + quotChar + " after standalone value not " + this.printable(ch), this.getLocation());
                  }

                  ch = this.more();
               }
            }

            ch = this.skipS(ch);
            if (ch != '?') {
               throw new XMLStreamException("expected ?> as last part of <?xml not " + this.printable(ch), this.getLocation());
            } else {
               ch = this.more();
               if (ch != '>') {
                  throw new XMLStreamException("expected ?> as last part of <?xml not " + this.printable(ch), this.getLocation());
               }
            }
         } else {
            throw new XMLStreamException("only 1.0 is supported as <?xml version not '" + this.printable(new String(this.buf, versionStart, versionEnd)) + "'", this.getLocation());
         }
      } catch (EOFException var8) {
         throw new XMLStreamException(var8);
      }
   }

   protected void parseDocdecl() throws XMLStreamException {
      this.posStart = this.pos - 3;

      try {
         char ch = this.more();
         if (ch != 'O') {
            throw new XMLStreamException("expected <!DOCTYPE", this.getLocation());
         } else {
            ch = this.more();
            if (ch != 'C') {
               throw new XMLStreamException("expected <!DOCTYPE", this.getLocation());
            } else {
               ch = this.more();
               if (ch != 'T') {
                  throw new XMLStreamException("expected <!DOCTYPE", this.getLocation());
               } else {
                  ch = this.more();
                  if (ch != 'Y') {
                     throw new XMLStreamException("expected <!DOCTYPE", this.getLocation());
                  } else {
                     ch = this.more();
                     if (ch != 'P') {
                        throw new XMLStreamException("expected <!DOCTYPE", this.getLocation());
                     } else {
                        ch = this.more();
                        if (ch != 'E') {
                           throw new XMLStreamException("expected <!DOCTYPE", this.getLocation());
                        } else {
                           int bracketLevel = 0;

                           do {
                              ch = this.more();
                              if (ch == '[') {
                                 ++bracketLevel;
                              }

                              if (ch == ']') {
                                 --bracketLevel;
                              }
                           } while(ch != '>' || bracketLevel != 0);

                           this.posEnd = this.pos;
                           this.processDTD();
                        }
                     }
                  }
               }
            }
         }
      } catch (EOFException var3) {
         throw new XMLStreamException(var3);
      }
   }

   protected void processDTD() throws XMLStreamException {
      try {
         String internalDTD = new String(this.buf, this.posStart, this.posEnd - this.posStart);
         int start = internalDTD.indexOf(91);
         int end = internalDTD.lastIndexOf(93);
         if (start != -1 && end != -1 && end > start) {
            String expectedDTD = internalDTD.substring(start + 1, end);
            DTDParser dtdParser = new DTDParser(new StringReader(expectedDTD));
            DTD dtd = dtdParser.parse();
            new StringWriter();
            Vector v = dtd.getItemsByType((new DTDEntity()).getClass());
            Enumeration e = v.elements();

            while(e.hasMoreElements()) {
               DTDEntity entity = (DTDEntity)e.nextElement();
               if (!entity.isParsed()) {
                  this.defineEntityReplacementText(entity.getName(), entity.getValue());
               }
            }

            v = dtd.getItemsByType((new DTDAttlist()).getClass());
            e = v.elements();

            while(e.hasMoreElements()) {
               DTDAttlist list = (DTDAttlist)e.nextElement();
               DTDAttribute[] atts = list.getAttribute();

               for(int i = 0; i < atts.length; ++i) {
                  DTDAttribute att = atts[i];
                  if (att.getDefaultValue() != null) {
                     if (this.defaultAttributes == null) {
                        this.defaultAttributes = new HashMap();
                     }

                     this.defaultAttributes.put(list.getName(), list);
                  }
               }
            }

         }
      } catch (IOException var14) {
         System.out.println(var14);
         var14.printStackTrace();
         throw new XMLStreamException(var14);
      }
   }

   protected void parseCDATA() throws XMLStreamException {
      char ch;
      try {
         ch = this.more();
         if (ch != 'C') {
            throw new XMLStreamException("expected <[CDATA[ for COMMENT start", this.getLocation());
         }

         ch = this.more();
         if (ch != 'D') {
            throw new XMLStreamException("expected <[CDATA[ for COMMENT start", this.getLocation());
         }

         ch = this.more();
         if (ch != 'A') {
            throw new XMLStreamException("expected <[CDATA[ for COMMENT start", this.getLocation());
         }

         ch = this.more();
         if (ch != 'T') {
            throw new XMLStreamException("expected <[CDATA[ for COMMENT start", this.getLocation());
         }

         ch = this.more();
         if (ch != 'A') {
            throw new XMLStreamException("expected <[CDATA[ for COMMENT start", this.getLocation());
         }

         ch = this.more();
         if (ch != '[') {
            throw new XMLStreamException("expected <[CDATA[ for COMMENT start", this.getLocation());
         }
      } catch (EOFException var7) {
         throw new XMLStreamException(var7);
      }

      this.posStart = this.pos;
      int curLine = this.lineNumber;
      int curColumn = this.columnNumber;

      try {
         boolean seenBracket = false;
         boolean seenBracketBracket = false;

         label48:
         while(true) {
            while(true) {
               ch = this.more();
               if (ch == ']') {
                  if (!seenBracket) {
                     seenBracket = true;
                  } else {
                     seenBracketBracket = true;
                     seenBracket = false;
                  }
               } else if (ch == '>') {
                  if (seenBracketBracket) {
                     break label48;
                  }

                  seenBracketBracket = false;
                  seenBracketBracket = false;
               } else {
                  seenBracketBracket = false;
               }
            }
         }
      } catch (EOFException var6) {
         throw new XMLStreamException("CDATA section on line " + curLine + " and column " + curColumn + " was not closed", this.getLocation(), var6);
      }

      this.posEnd = this.pos - 3;
   }

   protected void fillBuf() throws XMLStreamException, EOFException {
      if (this.reader == null) {
         throw new XMLStreamException("reader must be set before parsing is started");
      } else {
         if (this.bufEnd > this.bufSoftLimit) {
            boolean compact = this.bufStart > this.bufSoftLimit;
            boolean expand = false;
            if (!compact) {
               if (this.bufStart < this.buf.length / 2) {
                  expand = true;
               } else {
                  compact = true;
               }
            }

            if (compact) {
               System.arraycopy(this.buf, this.bufStart, this.buf, 0, this.bufEnd - this.bufStart);
            } else {
               if (!expand) {
                  throw new XMLStreamException("internal error in fillBuffer()");
               }

               int newSize = 2 * this.buf.length;
               char[] newBuf = new char[newSize];
               System.arraycopy(this.buf, this.bufStart, newBuf, 0, this.bufEnd - this.bufStart);
               this.buf = newBuf;
               if (this.bufLoadFactor > 0) {
                  this.bufSoftLimit = this.bufLoadFactor * this.buf.length / 100;
               }
            }

            this.bufEnd -= this.bufStart;
            this.pos -= this.bufStart;
            this.posStart -= this.bufStart;
            this.posEnd -= this.bufStart;
            this.bufAbsoluteStart += this.bufStart;
            this.bufStart = 0;
         }

         int len = this.buf.length - this.bufEnd > 8192 ? 8192 : this.buf.length - this.bufEnd;

         int ret;
         try {
            ret = this.reader.read(this.buf, this.bufEnd, len);
         } catch (IOException var5) {
            throw new XMLStreamException(var5);
         }

         if (ret > 0) {
            this.bufEnd += ret;
         } else if (ret == -1) {
            throw new EOFException("no more data available");
         } else {
            throw new XMLStreamException("error reading input, returned " + ret);
         }
      }
   }

   protected char more() throws XMLStreamException, EOFException {
      if (this.pos >= this.bufEnd) {
         this.fillBuf();
      }

      char ch = this.buf[this.pos++];
      if (ch == '\n') {
         ++this.lineNumber;
         this.columnNumber = 1;
      } else {
         ++this.columnNumber;
      }

      return ch;
   }

   protected String printable(char ch) {
      if (ch == '\n') {
         return "\\n";
      } else if (ch == '\r') {
         return "\\r";
      } else if (ch == '\t') {
         return "\\t";
      } else if (ch == '\'') {
         return "\\'";
      } else {
         return ch <= 127 && ch >= ' ' ? "" + ch : "\\u" + Integer.toHexString(ch);
      }
   }

   protected String printable(String s) {
      if (s == null) {
         return null;
      } else {
         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < s.length(); ++i) {
            buf.append(this.printable(s.charAt(i)));
         }

         s = buf.toString();
         return s;
      }
   }

   protected void ensurePC(int end) {
      int newSize = end > 8192 ? 2 * end : 16384;
      char[] newPC = new char[newSize];
      System.arraycopy(this.pc, 0, newPC, 0, this.pcEnd);
      this.pc = newPC;
   }

   protected void joinPC() {
      int len = this.posEnd - this.posStart;
      int newEnd = this.pcEnd + len + 1;
      if (newEnd >= this.pc.length) {
         this.ensurePC(newEnd);
      }

      System.arraycopy(this.buf, this.posStart, this.pc, this.pcEnd, len);
      this.pcEnd += len;
      this.usePC = true;
   }

   public Location getLocation() {
      return this;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return null;
   }

   public void setConfigurationContext(ConfigurationContextBase c) {
      this.configurationContext = c;
   }

   public ConfigurationContextBase getConfigurationContext() {
      return this.configurationContext;
   }

   public Object getProperty(String name) {
      return this.configurationContext.getProperty(name);
   }

   static {
      setNameStart(':');

      for(char ch = 'A'; ch <= 'Z'; ++ch) {
         setNameStart(ch);
      }

      setNameStart('_');

      for(char ch = 'a'; ch <= 'z'; ++ch) {
         setNameStart(ch);
      }

      for(char ch = 192; ch <= 767; ++ch) {
         setNameStart(ch);
      }

      for(char ch = 880; ch <= 893; ++ch) {
         setNameStart(ch);
      }

      for(char ch = 895; ch < 1024; ++ch) {
         setNameStart(ch);
      }

      setName('-');
      setName('.');

      for(char ch = '0'; ch <= '9'; ++ch) {
         setName(ch);
      }

      setName('·');

      for(char ch = 768; ch <= 879; ++ch) {
         setName(ch);
      }

      VERSION = new char[]{'v', 'e', 'r', 's', 'i', 'o', 'n'};
      ENCODING = new char[]{'e', 'n', 'c', 'o', 'd', 'i', 'n', 'g'};
      STANDALONE = new char[]{'s', 't', 'a', 'n', 'd', 'a', 'l', 'o', 'n', 'e'};
      YES = new char[]{'y', 'e', 's'};
      NO = new char[]{'n', 'o'};
   }
}
