package javolution.xml.pull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import javax.realtime.MemoryArea;
import javolution.io.Utf8ByteBufferReader;
import javolution.io.Utf8StreamReader;
import javolution.lang.Reusable;
import javolution.lang.Text;
import javolution.lang.TypeFormat;
import javolution.util.FastTable;
import javolution.xml.sax.Attributes;

public final class XmlPullParserImpl implements XmlPullParser, Reusable {
   public static final String FEATURE_IGNORE_WHITESPACE = "http://javolution.org/xml/pull/ignore-whitespace";
   private static final int MERGED_TEXT = 16;
   private static final int READER_BUFFER_CAPACITY = 2048;
   private int _lineNumber;
   private int _columnOffset;
   private int _lineLength;
   private int _index;
   private boolean _ignoreWhitespace;
   private char[] _data = new char[4096];
   private int _length;
   private int _depth;
   private final Namespaces _namespaces = new Namespaces();
   private final AttributesImpl _attributes;
   private final FastTable _elemStack;
   private final char[] _chars;
   private final Utf8StreamReader _inputStreamReader;
   private final Utf8ByteBufferReader _byteBufferReader;
   private int _charsRead;
   private CharSequenceImpl _elemLocalName;
   private CharSequenceImpl _elemNamespace;
   private CharSequenceImpl _elemQName;
   private CharSequenceImpl _elemPrefix;
   private CharSequenceImpl _attrQName;
   private CharSequenceImpl _attrPrefix;
   private CharSequenceImpl _attrValue;
   private final CharSequenceImpl _num;
   private int _eventType;
   private String _inputEncoding;
   private boolean _isEmpty;
   private Reader _reader;
   private int _escStart;
   private int _savedState;
   private int _start;
   private int _state;
   private CharSequenceImpl _text;
   private CharSequenceImpl _mergedText;
   private boolean _hasNonWhitespace;
   private CharSequenceImpl[] _seqs;
   private int _seqsIndex;
   private int _seqsCapacity;
   private static final int CHAR_DATA = 16;
   private static final int MARKUP = 32;
   private static final int STATE_COMMENT = 48;
   private static final int PI = 64;
   private static final int CDATA = 80;
   private static final int OPEN_TAG = 96;
   private static final int CLOSE_TAG = 112;
   private static final int ESCAPE = 144;
   private static final int READ_ELEM_NAME = 1;
   private static final int ELEM_NAME_READ = 2;
   private static final int READ_ATTR_NAME = 3;
   private static final int ATTR_NAME_READ = 4;
   private static final int EQUAL_READ = 5;
   private static final int READ_ATTR_VALUE_SIMPLE_QUOTE = 6;
   private static final int READ_ATTR_VALUE_DOUBLE_QUOTE = 7;
   private static final int EMPTY_TAG = 8;

   public XmlPullParserImpl() {
      this._attributes = new AttributesImpl(this._namespaces);
      this._elemStack = new FastTable();
      this._chars = new char[2048];
      this._inputStreamReader = new Utf8StreamReader(2048);
      this._byteBufferReader = new Utf8ByteBufferReader();
      this._num = new CharSequenceImpl();
      this._eventType = 1;
      this._state = 16;
      this._seqs = new CharSequenceImpl[256];
   }

   public void setInput(ByteBuffer var1) {
      if (this._reader != null) {
         throw new IllegalStateException("Parser not reset.");
      } else {
         this._byteBufferReader.setByteBuffer(var1);
         this._inputEncoding = "UTF-8";
         this.setInput((Reader)this._byteBufferReader);
      }
   }

   public void setInput(InputStream var1) {
      if (this._reader != null) {
         throw new IllegalStateException("Parser not reset.");
      } else {
         this._inputStreamReader.setInputStream(var1);
         this._inputEncoding = "UTF-8";
         this.setInput((Reader)this._inputStreamReader);
      }
   }

   public void setInput(InputStream var1, String var2) throws XmlPullParserException {
      if (var2 != null && !var2.equals("utf-8") && !var2.equals("UTF-8")) {
         try {
            this._inputEncoding = var2;
            this.setInput((Reader)(new InputStreamReader(var1, var2)));
         } catch (UnsupportedEncodingException var4) {
            throw new XmlPullParserException(var4.getMessage());
         }
      } else {
         this.setInput(var1);
      }
   }

   public void setInput(Reader var1) {
      if (this._reader != null) {
         throw new IllegalStateException("Parser not reset.");
      } else {
         this._reader = var1;
         this._eventType = 0;
      }
   }

   public void defineEntityReplacementText(String var1, String var2) throws XmlPullParserException {
   }

   public Attributes getSaxAttributes() {
      return this._attributes;
   }

   public int getAttributeCount() {
      return this._eventType != 2 ? -1 : this._attributes.getLength();
   }

   public CharSequence getAttributeName(int var1) {
      return this._attributes.getLocalName(var1);
   }

   public CharSequence getAttributeNamespace(int var1) {
      return this._attributes.getURI(var1);
   }

   public CharSequence getAttributePrefix(int var1) {
      return this._attributes.getPrefix(var1);
   }

   public String getAttributeType(int var1) {
      return this._attributes.getType(var1);
   }

   public CharSequence getAttributeValue(String var1, String var2) {
      return var1 == null ? this._attributes.getValue("", var2) : this._attributes.getValue(var1, var2);
   }

   public CharSequence getAttributeValue(int var1) {
      return this._attributes.getValue(var1);
   }

   public int getDepth() {
      return this._depth;
   }

   public int getEventType() throws XmlPullParserException {
      return this._eventType;
   }

   public String getInputEncoding() {
      return this._inputEncoding;
   }

   public int getLineNumber() {
      int var1 = this._columnOffset + this._index;
      return var1 != 0 ? this._lineNumber : this._lineNumber - 1;
   }

   public int getColumnNumber() {
      int var1 = this._columnOffset + this._index;
      return var1 != 0 ? var1 : this._lineLength;
   }

   public CharSequence getName() {
      return this._elemLocalName;
   }

   public CharSequence getNamespace() {
      return this._elemNamespace;
   }

   public CharSequence getPrefix() {
      return this._elemPrefix;
   }

   public CharSequence getQName() {
      return this._elemQName;
   }

   public CharSequence getNamespace(String var1) {
      return this._namespaces.getNamespaceUri(var1);
   }

   public int getNamespaceCount(int var1) {
      return this._namespaces.getNamespaceCount(var1);
   }

   public CharSequence getNamespacePrefix(int var1) {
      return this._namespaces.getNamespacePrefix(var1);
   }

   public CharSequence getNamespaceUri(int var1) {
      return this._namespaces.getNamespaceUri(var1);
   }

   public CharSequence getPositionDescription() {
      return Text.valueOf((Object)"line ").concat(Text.valueOf(this.getLineNumber())).concat(Text.valueOf((Object)", column ")).concat(Text.valueOf(this.getColumnNumber()));
   }

   public CharSequence getText() {
      return this._text;
   }

   public char[] getTextCharacters(int[] var1) {
      if (this._text != null) {
         var1[0] = this._text.offset;
         var1[1] = this._text.length;
         return this._text.data;
      } else {
         var1[0] = var1[1] = -1;
         return null;
      }
   }

   public boolean isAttributeDefault(int var1) {
      return false;
   }

   public boolean isEmptyElementTag() throws XmlPullParserException {
      return this._isEmpty;
   }

   public boolean isWhitespace() throws XmlPullParserException {
      if (this._eventType != 4 && this._eventType != 5) {
         throw new IllegalStateException();
      } else {
         return !this._hasNonWhitespace;
      }
   }

   public int next() throws XmlPullParserException, IOException {
      while(true) {
         switch (this.nextToken()) {
            case 4:
            case 5:
               if (this._mergedText == null) {
                  this._mergedText = this._text;
               } else {
                  this._mergedText.data = this._text.data;
                  CharSequenceImpl var10000 = this._mergedText;
                  var10000.length += this._text.length;
               }

               this._length = this._text.offset + this._text.length;
               this._start = this._length;
               break;
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return this._eventType;
            case 8:
            case 9:
               break;
            case 16:
               this._text = this._mergedText;
               this._mergedText = null;
               return this._eventType = 4;
         }
      }
   }

   public int nextTag() throws XmlPullParserException, IOException {
      int var1 = this.next();
      if (var1 == 4 && this.isWhitespace()) {
         var1 = this.next();
      }

      if (var1 != 2 && var1 != 3) {
         throw this.error("expected start or end tag");
      } else {
         return var1;
      }
   }

   public CharSequence nextText() throws XmlPullParserException, IOException {
      if (this.getEventType() != 2) {
         throw this.error("parser must be on START_TAG to read next text");
      } else {
         int var1 = this.next();
         if (var1 == 4) {
            CharSequence var2 = this.getText();
            var1 = this.next();
            if (var1 != 3) {
               throw this.error("event TEXT must be immediately followed by END_TAG");
            } else {
               return var2;
            }
         } else if (var1 == 3) {
            return CharSequenceImpl.EMPTY;
         } else {
            throw this.error("parser must be on START_TAG or TEXT to read text");
         }
      }
   }

   public void setFeature(String var1, boolean var2) throws XmlPullParserException {
      if (var1.equals("http://javolution.org/xml/pull/ignore-whitespace")) {
         this._ignoreWhitespace = var2;
      }

   }

   public boolean getFeature(String var1) {
      return var1.equals("http://javolution.org/xml/pull/ignore-whitespace") ? this._ignoreWhitespace : false;
   }

   public void setProperty(String var1, Object var2) throws XmlPullParserException {
   }

   public Object getProperty(String var1) {
      return null;
   }

   public void require(int var1, String var2, String var3) throws XmlPullParserException, IOException {
      if (var1 != this.getEventType() || var2 != null && !this.getNamespace().equals(var2) || var3 != null && this.getName().equals(var3)) {
         throw this.error("Require " + TYPES[var1] + " failed");
      }
   }

   public int nextToken() throws XmlPullParserException, IOException {
      switch (this._eventType) {
         case 0:
            this._charsRead = this._reader.read(this._chars, 0, this._chars.length);
            break;
         case 1:
            throw this.error("End of document reached.");
         case 2:
            this._attributes.reset();
            if (this._isEmpty) {
               this._isEmpty = false;
               return this._eventType = 3;
            }

            this._elemPrefix = null;
            this._elemLocalName = null;
            this._elemNamespace = null;
            this._elemQName = null;
            break;
         case 3:
            --this._depth;
            this._length = this._elemQName.offset;
            this._start = this._length;

            while(this._seqs[--this._seqsIndex] != this._elemQName) {
            }

            this._elemPrefix = null;
            this._elemLocalName = null;
            this._elemNamespace = null;
            this._elemQName = null;
            break;
         case 4:
         case 5:
         case 8:
         case 9:
            this._text = null;
            this._hasNonWhitespace = false;
         case 6:
         case 7:
      }

      while(true) {
         char var1;
         while(true) {
            if (this._index >= this._charsRead) {
               if (this._depth != 0) {
                  throw this.error("Unexpected end of file");
               }

               this.reset();
               return 1;
            }

            var1 = this._chars[this._index];
            if (++this._index == this._charsRead) {
               this._columnOffset += this._index;
               this._index = 0;
               this._charsRead = this._reader.read(this._chars, 0, this._chars.length);

               while(this._length + this._charsRead >= this._data.length) {
                  this.increaseDataBuffer();
               }
            }

            if (var1 >= ' ') {
               break;
            }

            if (var1 == '\r') {
               if (this._index < this._charsRead && this._chars[this._index] == '\n') {
                  continue;
               }

               var1 = '\n';
            }

            if (var1 == '\n') {
               ++this._lineNumber;
               this._lineLength = this._columnOffset + this._index;
               this._columnOffset = -this._index;
            } else if (var1 != '\t') {
               throw this.error("Illegal XML character U+" + Integer.toHexString(var1));
            }
            break;
         }

         this._data[this._length++] = var1;
         if (var1 == '&' && this._state != 48 && this._state != 64 && this._state != 80 && this._state != 144) {
            this._savedState = this._state;
            this._escStart = this._length;
            this._state = 144;
         }

         int var2;
         switch (this._state) {
            case 16:
               if (var1 == '<') {
                  this._state = 32;
                  var2 = this._length - this._start - 1;
                  this._length = this._start;
                  if (!this._hasNonWhitespace && (this._ignoreWhitespace || var2 <= 0)) {
                     break;
                  }

                  this.setText(this._start, var2);
                  return this._eventType = 4;
               } else {
                  if (!this._hasNonWhitespace && var1 > ' ') {
                     this._hasNonWhitespace = true;
                  }
                  break;
               }
            case 32:
               if (this._length - this._start == 1) {
                  if (var1 == '/') {
                     this._state = 113;
                     this._length = this._start;
                     this._elemQName = this.newSeq();
                     this._elemQName.offset = this._start;
                     if (this._mergedText != null) {
                        return this._eventType = 16;
                     }
                  } else if (var1 == '?') {
                     this._state = 64;
                     this._length = this._start;
                  } else if (var1 != '!') {
                     this._state = 97;
                     this._elemQName = this.newSeq();
                     this._elemQName.offset = this._start;
                     if (this._mergedText != null) {
                        return this._eventType = 16;
                     }
                  }
               } else if (this._length - this._start == 3 && this._data[this._start] == '!' && this._data[this._start + 1] == '-' && this._data[this._start + 2] == '-') {
                  this._state = 48;
                  this._length = this._start;
               } else if (this._length - this._start == 8 && this._data[this._start] == '!' && this._data[this._start + 1] == '[' && this._data[this._start + 2] == 'C' && this._data[this._start + 3] == 'D' && this._data[this._start + 4] == 'A' && this._data[this._start + 5] == 'T' && this._data[this._start + 6] == 'A' && this._data[this._start + 7] == '[') {
                  this._state = 80;
                  this._length = this._start;
               } else if (var1 == '>') {
                  this._state = 16;
                  this._length = this._start;
               }
               break;
            case 48:
               if (var1 == '>' && this._length - this._start >= 3 && this._data[this._length - 2] == '-' && this._data[this._length - 3] == '-') {
                  this._state = 16;
                  var2 = this._length - this._start - 3;
                  this._length = this._start;
                  if (var2 > 0) {
                     this.setText(this._start, var2);
                     return this._eventType = 9;
                  }
               }
               break;
            case 64:
               if (var1 == '>' && this._length - this._start >= 2 && this._data[this._length - 2] == '?') {
                  this._state = 16;
                  var2 = this._length - this._start - 2;
                  this._length = this._start;
                  if (var2 > 0) {
                     this.setText(this._start, var2);
                     return this._eventType = 8;
                  }
               }
               break;
            case 80:
               if (var1 == '>' && this._length - this._start >= 3 && this._data[this._length - 2] == ']' && this._data[this._length - 3] == ']') {
                  this._state = 16;
                  var2 = this._length - this._start - 3;
                  this._length = this._start;
                  if (var2 > 0) {
                     this.setText(this._start, var2);
                     return this._eventType = 5;
                  }
               }

               if (!this._hasNonWhitespace && var1 > ' ') {
                  this._hasNonWhitespace = true;
               }
               break;
            case 97:
               if (var1 == '>') {
                  this._elemQName.length = this._length - this._elemQName.offset - 1;
                  this._elemQName.data = this._data;
                  this._state = 16;
                  this._start = this._length;
                  return this._eventType = this.processElement(96);
               }

               if (var1 == '/') {
                  this._elemQName.length = this._length - this._elemQName.offset - 1;
                  this._elemQName.data = this._data;
                  this._state = 104;
               } else if (var1 == ':' && this._elemPrefix == null) {
                  this._elemPrefix = this.newSeq();
                  this._elemPrefix.offset = this._elemQName.offset;
                  this._elemPrefix.length = this._length - this._elemQName.offset - 1;
                  this._elemPrefix.data = this._data;
               } else if (var1 <= ' ') {
                  this._elemQName.length = this._length - this._elemQName.offset - 1;
                  this._elemQName.data = this._data;
                  this._state = 98;
               }
               break;
            case 98:
               if (var1 == '>') {
                  this._state = 16;
                  this._start = this._length;
                  return this._eventType = this.processElement(96);
               }

               if (var1 == '/') {
                  this._state = 104;
               } else if (var1 > ' ') {
                  this._attrQName = this.newSeq();
                  this._attrQName.offset = this._length - 1;
                  this._state = 99;
               }
               break;
            case 99:
               if (var1 <= ' ') {
                  this._attrQName.length = this._length - this._attrQName.offset - 1;
                  this._attrQName.data = this._data;
                  this._state = 100;
               } else if (var1 == '=') {
                  this._attrQName.length = this._length - this._attrQName.offset - 1;
                  this._attrQName.data = this._data;
                  this._state = 101;
               } else if (var1 == ':' && this._attrPrefix == null) {
                  this._attrPrefix = this.newSeq();
                  this._attrPrefix.offset = this._attrQName.offset;
                  this._attrPrefix.length = this._length - this._attrQName.offset - 1;
                  this._attrPrefix.data = this._data;
               }
               break;
            case 100:
               if (var1 == '=') {
                  this._state = 101;
               } else if (var1 > ' ') {
                  throw this.error("'=' expected");
               }
               break;
            case 101:
               if (var1 == '\'') {
                  this._attrValue = this.newSeq();
                  this._attrValue.offset = this._length;
                  this._state = 102;
               } else if (var1 == '"') {
                  this._attrValue = this.newSeq();
                  this._attrValue.offset = this._length;
                  this._state = 103;
               } else if (var1 > ' ') {
                  throw this.error("Quotes expected");
               }
               break;
            case 102:
               if (var1 == '\'') {
                  this._attrValue.length = this._length - this._attrValue.offset - 1;
                  this._attrValue.data = this._data;
                  this.processAttribute();
                  this._state = 98;
               }
               break;
            case 103:
               if (var1 == '"') {
                  this._attrValue.length = this._length - this._attrValue.offset - 1;
                  this._attrValue.data = this._data;
                  this.processAttribute();
                  this._state = 98;
               }
               break;
            case 104:
               if (var1 == '>') {
                  this._state = 16;
                  this._start = this._length;
                  return this._eventType = this.processElement(104);
               }

               throw this.error("'>' expected");
            case 113:
               if (var1 == '>') {
                  this._elemQName.length = this._length - this._elemQName.offset - 1;
                  this._elemQName.data = this._data;
                  this._state = 16;
                  this._start = this._length;
                  return this._eventType = this.processElement(112);
               }

               if (var1 == ':' && this._elemPrefix == null) {
                  this._elemPrefix = this.newSeq();
                  this._elemPrefix.offset = this._elemQName.offset;
                  this._elemPrefix.length = this._length - this._elemQName.offset - 1;
                  this._elemPrefix.data = this._data;
               } else if (var1 <= ' ') {
                  this._elemQName.length = this._length - this._elemQName.offset - 1;
                  this._elemQName.data = this._data;
                  this._state = 114;
               }
               break;
            case 114:
               if (var1 == '>') {
                  this._state = 16;
                  this._start = this._length;
                  return this._eventType = this.processElement(112);
               }

               if (var1 > ' ') {
                  throw this.error("'>' expected");
               }
               break;
            case 144:
               if (var1 != ';') {
                  if (var1 <= ' ') {
                     throw this.error("';' expected");
                  }
               } else {
                  if (this._length - this._escStart == 3 && this._data[this._length - 3] == 'l' && this._data[this._length - 2] == 't') {
                     this._data[this._escStart - 1] = '<';
                  } else if (this._length - this._escStart == 3 && this._data[this._length - 3] == 'g' && this._data[this._length - 2] == 't') {
                     this._data[this._escStart - 1] = '>';
                  } else if (this._length - this._escStart == 5 && this._data[this._length - 5] == 'a' && this._data[this._length - 4] == 'p' && this._data[this._length - 3] == 'o' && this._data[this._length - 2] == 's') {
                     this._data[this._escStart - 1] = '\'';
                  } else if (this._length - this._escStart == 5 && this._data[this._length - 5] == 'q' && this._data[this._length - 4] == 'u' && this._data[this._length - 3] == 'o' && this._data[this._length - 2] == 't') {
                     this._data[this._escStart - 1] = '"';
                  } else if (this._length - this._escStart == 4 && this._data[this._length - 4] == 'a' && this._data[this._length - 3] == 'm' && this._data[this._length - 2] == 'p') {
                     this._data[this._escStart - 1] = '&';
                  } else {
                     if (this._length - this._escStart <= 1 || this._data[this._escStart] != '#') {
                        throw this.error("'#' expected");
                     }

                     try {
                        if (this._data[this._escStart + 1] == 'x') {
                           this._num.offset = this._escStart + 2;
                           this._num.length = this._length - this._escStart - 3;
                           this._num.data = this._data;
                           this._data[this._escStart - 1] = (char)TypeFormat.parseInt(this._num, 16);
                        } else {
                           this._num.offset = this._escStart + 1;
                           this._num.length = this._length - this._escStart - 2;
                           this._num.data = this._data;
                           this._data[this._escStart - 1] = (char)TypeFormat.parseInt(this._num);
                        }
                     } catch (NumberFormatException var3) {
                        throw this.error("Ill-formed character reference");
                     }
                  }

                  this._state = this._savedState;
                  this._length = this._escStart;
               }
               break;
            default:
               throw this.error("State unknown: " + this._state);
         }
      }
   }

   private void processAttribute() throws XmlPullParserException {
      if (this._attrPrefix == null) {
         if (isXmlns(this._attrQName)) {
            this._namespaces.map((CharSequenceImpl)null, this._attrValue);
         } else {
            this._attributes.addAttribute(this._attrQName, CharSequenceImpl.EMPTY, this._attrQName, this._attrValue);
         }
      } else {
         CharSequenceImpl var1 = this.newSeq();
         var1.offset = this._attrQName.offset + this._attrPrefix.length + 1;
         var1.length = this._attrQName.length - this._attrPrefix.length - 1;
         var1.data = this._attrQName.data;
         if (isXmlns(this._attrPrefix)) {
            this._namespaces.map(var1, this._attrValue);
         } else {
            this._attributes.addAttribute(var1, this._attrPrefix, this._attrQName, this._attrValue);
         }

         this._attrPrefix = null;
      }

   }

   private int processElement(int var1) throws XmlPullParserException {
      if (this._elemPrefix != null) {
         this._elemLocalName = this.newSeq();
         this._elemLocalName.offset = this._elemQName.offset + this._elemPrefix.length + 1;
         this._elemLocalName.length = this._elemQName.length - this._elemPrefix.length - 1;
         this._elemLocalName.data = this._elemQName.data;
         this._elemNamespace = this._namespaces.getNamespaceUri(this._elemPrefix);
         if (this._elemNamespace == null) {
            throw this.error("Namespace " + this._elemPrefix + " undefined");
         }
      } else {
         this._elemLocalName = this._elemQName;
         this._elemNamespace = this._namespaces.getDefault();
      }

      if (var1 == 104) {
         this._isEmpty = true;
         ++this._depth;
         this._namespaces.flush();
         return 2;
      } else if (var1 == 96) {
         ++this._depth;
         this._elemStack.addLast(this._elemQName);
         this._elemStack.addLast(this._elemNamespace);
         this._elemStack.addLast(this._elemLocalName);
         this._elemStack.addLast(this._elemPrefix);
         this._namespaces.push();
         return 2;
      } else if (var1 == 112) {
         this._elemPrefix = (CharSequenceImpl)this._elemStack.removeLast();
         this._elemLocalName = (CharSequenceImpl)this._elemStack.removeLast();
         this._elemNamespace = (CharSequenceImpl)this._elemStack.removeLast();
         CharSequenceImpl var2 = this._elemQName;
         this._elemQName = (CharSequenceImpl)this._elemStack.removeLast();
         if (!this._elemQName.equals(var2)) {
            throw this.error("Unexpected end tag for " + this._elemQName);
         } else {
            this._namespaces.pop();
            return 3;
         }
      } else {
         throw this.error("Unexpected state: " + var1);
      }
   }

   public void reset() {
      try {
         if (this._reader != null) {
            this._reader.close();
         }
      } catch (IOException var2) {
      }

      this._attributes.reset();
      this._attrPrefix = null;
      this._attrQName = null;
      this._attrValue = null;
      this._attrQName = null;
      this._charsRead = 0;
      this._columnOffset = 0;
      this._depth = 0;
      this._elemLocalName = null;
      this._elemNamespace = null;
      this._elemPrefix = null;
      this._elemQName = null;
      this._elemStack.reset();
      this._escStart = 0;
      this._eventType = 1;
      this._hasNonWhitespace = false;
      this._ignoreWhitespace = false;
      this._index = 0;
      this._inputEncoding = null;
      this._isEmpty = false;
      this._length = 0;
      this._lineLength = 0;
      this._lineNumber = 0;
      this._mergedText = null;
      this._namespaces.reset();
      this._reader = null;
      this._savedState = 0;
      this._seqsIndex = 0;
      this._start = 0;
      this._state = 16;
      this._text = null;
   }

   private void setText(int var1, int var2) {
      this._text = this.newSeq();
      this._text.data = this._data;
      this._text.offset = var1;
      this._text.length = var2;
   }

   private static boolean isXmlns(CharSequenceImpl var0) {
      return var0.length == 5 && var0.data[var0.offset] == 'x' && var0.data[var0.offset + 1] == 'm' && var0.data[var0.offset + 2] == 'l' && var0.data[var0.offset + 3] == 'n' && var0.data[var0.offset + 4] == 's';
   }

   private XmlPullParserException error(String var1) {
      XmlPullParserException var2 = new XmlPullParserException(var1, this, (Throwable)null);
      return var2;
   }

   private CharSequenceImpl newSeq() {
      return this._seqsIndex < this._seqsCapacity ? this._seqs[this._seqsIndex++] : this.newSeq2();
   }

   private CharSequenceImpl newSeq2() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            if (XmlPullParserImpl.access$008(XmlPullParserImpl.this) >= XmlPullParserImpl.access$100(XmlPullParserImpl.this).length) {
               CharSequenceImpl[] var1 = new CharSequenceImpl[XmlPullParserImpl.access$100(XmlPullParserImpl.this).length * 2];
               System.arraycopy(XmlPullParserImpl.access$100(XmlPullParserImpl.this), 0, var1, 0, XmlPullParserImpl.access$100(XmlPullParserImpl.this).length);
               XmlPullParserImpl.access$102(XmlPullParserImpl.this, var1);
            }

            XmlPullParserImpl.access$100(XmlPullParserImpl.this)[XmlPullParserImpl.access$200(XmlPullParserImpl.this)] = new CharSequenceImpl();
         }
      });
      return this._seqs[this._seqsIndex++];
   }

   private void increaseDataBuffer() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            char[] var1 = new char[XmlPullParserImpl.access$300(XmlPullParserImpl.this).length * 2];
            System.arraycopy(XmlPullParserImpl.access$300(XmlPullParserImpl.this), 0, var1, 0, XmlPullParserImpl.access$300(XmlPullParserImpl.this).length);
            XmlPullParserImpl.access$302(XmlPullParserImpl.this, var1);
         }
      });
   }

   static int access$008(XmlPullParserImpl var0) {
      return var0._seqsCapacity++;
   }

   static CharSequenceImpl[] access$100(XmlPullParserImpl var0) {
      return var0._seqs;
   }

   static CharSequenceImpl[] access$102(XmlPullParserImpl var0, CharSequenceImpl[] var1) {
      return var0._seqs = var1;
   }

   static int access$200(XmlPullParserImpl var0) {
      return var0._seqsIndex;
   }

   static char[] access$300(XmlPullParserImpl var0) {
      return var0._data;
   }

   static char[] access$302(XmlPullParserImpl var0, char[] var1) {
      return var0._data = var1;
   }
}
