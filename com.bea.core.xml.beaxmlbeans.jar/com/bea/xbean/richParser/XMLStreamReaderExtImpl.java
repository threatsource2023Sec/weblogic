package com.bea.xbean.richParser;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.common.XMLChar;
import com.bea.xbean.common.XmlWhitespace;
import com.bea.xbean.util.Base64;
import com.bea.xbean.util.HexBin;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.GDate;
import com.bea.xml.GDateBuilder;
import com.bea.xml.GDuration;
import com.bea.xml.XmlCalendar;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLStreamReaderExtImpl implements XMLStreamReaderExt {
   private final XMLStreamReader _xmlStream;
   private final CharSeqTrimWS _charSeq;
   private String _defaultValue;

   public XMLStreamReaderExtImpl(XMLStreamReader xmlStream) {
      if (xmlStream == null) {
         throw new IllegalArgumentException();
      } else {
         this._xmlStream = xmlStream;
         this._charSeq = new CharSeqTrimWS(this);
      }
   }

   public XMLStreamReader getUnderlyingXmlStream() {
      return this._xmlStream;
   }

   public String getStringValue() throws XMLStreamException {
      this._charSeq.reload(1);
      return this._charSeq.toString();
   }

   public String getStringValue(int wsStyle) throws XMLStreamException {
      this._charSeq.reload(1);
      return XmlWhitespace.collapse(this._charSeq.toString(), wsStyle);
   }

   public boolean getBooleanValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexBoolean(this._charSeq);
      } catch (InvalidLexicalValueException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public byte getByteValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexByte(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public short getShortValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexShort(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public int getIntValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexInt(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public long getLongValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexLong(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public BigInteger getBigIntegerValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexInteger(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public BigDecimal getBigDecimalValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexDecimal(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public float getFloatValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexFloat(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public double getDoubleValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexDouble(this._charSeq);
      } catch (NumberFormatException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public InputStream getHexBinaryValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);
      String text = this._charSeq.toString();
      byte[] buf = HexBin.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid hexBinary value", this._charSeq.getLocation());
      }
   }

   public InputStream getBase64Value() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);
      String text = this._charSeq.toString();
      byte[] buf = Base64.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid base64Binary value", this._charSeq.getLocation());
      }
   }

   public XmlCalendar getCalendarValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return (new GDateBuilder(this._charSeq)).getCalendar();
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public Date getDateValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return (new GDateBuilder(this._charSeq)).getDate();
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public GDate getGDateValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexGDate(this._charSeq);
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public GDuration getGDurationValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return new GDuration(this._charSeq);
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2, this._charSeq.getLocation());
      }
   }

   public QName getQNameValue() throws XMLStreamException, InvalidLexicalValueException {
      this._charSeq.reload(2);

      try {
         return XsTypeConverter.lexQName(this._charSeq, this._xmlStream.getNamespaceContext());
      } catch (InvalidLexicalValueException var2) {
         throw new InvalidLexicalValueException(var2.getMessage(), this._charSeq.getLocation());
      }
   }

   public String getAttributeStringValue(int index) throws XMLStreamException {
      return this._xmlStream.getAttributeValue(index);
   }

   public String getAttributeStringValue(int index, int wsStyle) throws XMLStreamException {
      return XmlWhitespace.collapse(this._xmlStream.getAttributeValue(index), wsStyle);
   }

   public boolean getAttributeBooleanValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexBoolean(this._charSeq.reloadAtt(index, 2));
      } catch (InvalidLexicalValueException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public byte getAttributeByteValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexByte(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public short getAttributeShortValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexShort(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public int getAttributeIntValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexInt(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public long getAttributeLongValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexLong(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public BigInteger getAttributeBigIntegerValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexInteger(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public BigDecimal getAttributeBigDecimalValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexDecimal(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public float getAttributeFloatValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexFloat(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public double getAttributeDoubleValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexDouble(this._charSeq.reloadAtt(index, 2));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public InputStream getAttributeHexBinaryValue(int index) throws XMLStreamException {
      String text = this._charSeq.reloadAtt(index, 2).toString();
      byte[] buf = HexBin.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid hexBinary value", this._charSeq.getLocation());
      }
   }

   public InputStream getAttributeBase64Value(int index) throws XMLStreamException {
      String text = this._charSeq.reloadAtt(index, 2).toString();
      byte[] buf = Base64.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid base64Binary value", this._charSeq.getLocation());
      }
   }

   public XmlCalendar getAttributeCalendarValue(int index) throws XMLStreamException {
      try {
         return (new GDateBuilder(this._charSeq.reloadAtt(index, 2))).getCalendar();
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public Date getAttributeDateValue(int index) throws XMLStreamException {
      try {
         return (new GDateBuilder(this._charSeq.reloadAtt(index, 2))).getDate();
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public GDate getAttributeGDateValue(int index) throws XMLStreamException {
      try {
         return new GDate(this._charSeq.reloadAtt(index, 2));
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public GDuration getAttributeGDurationValue(int index) throws XMLStreamException {
      try {
         return new GDuration(this._charSeq.reloadAtt(index, 2));
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3, this._charSeq.getLocation());
      }
   }

   public QName getAttributeQNameValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexQName(this._charSeq.reloadAtt(index, 2), this._xmlStream.getNamespaceContext());
      } catch (InvalidLexicalValueException var3) {
         throw new InvalidLexicalValueException(var3.getMessage(), this._charSeq.getLocation());
      }
   }

   public String getAttributeStringValue(String uri, String local) throws XMLStreamException {
      return this._charSeq.reloadAtt(uri, local, 1).toString();
   }

   public String getAttributeStringValue(String uri, String local, int wsStyle) throws XMLStreamException {
      return XmlWhitespace.collapse(this._xmlStream.getAttributeValue(uri, local), wsStyle);
   }

   public boolean getAttributeBooleanValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexBoolean(cs);
      } catch (InvalidLexicalValueException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public byte getAttributeByteValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexByte(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public short getAttributeShortValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexShort(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public int getAttributeIntValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexInt(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public long getAttributeLongValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexLong(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public BigInteger getAttributeBigIntegerValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexInteger(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public BigDecimal getAttributeBigDecimalValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexDecimal(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public float getAttributeFloatValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexFloat(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public double getAttributeDoubleValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexDouble(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public InputStream getAttributeHexBinaryValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);
      String text = cs.toString();
      byte[] buf = HexBin.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid hexBinary value", this._charSeq.getLocation());
      }
   }

   public InputStream getAttributeBase64Value(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);
      String text = cs.toString();
      byte[] buf = Base64.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid base64Binary value", this._charSeq.getLocation());
      }
   }

   public XmlCalendar getAttributeCalendarValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return (new GDateBuilder(cs)).getCalendar();
      } catch (IllegalArgumentException var5) {
         throw new InvalidLexicalValueException(var5, this._charSeq.getLocation());
      }
   }

   public Date getAttributeDateValue(String uri, String local) throws XMLStreamException {
      try {
         CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);
         return (new GDateBuilder(cs)).getDate();
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, this._charSeq.getLocation());
      }
   }

   public GDate getAttributeGDateValue(String uri, String local) throws XMLStreamException {
      try {
         CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);
         return new GDate(cs);
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, this._charSeq.getLocation());
      }
   }

   public GDuration getAttributeGDurationValue(String uri, String local) throws XMLStreamException {
      try {
         return new GDuration(this._charSeq.reloadAtt(uri, local, 2));
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, this._charSeq.getLocation());
      }
   }

   public QName getAttributeQNameValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this._charSeq.reloadAtt(uri, local, 2);

      try {
         return XsTypeConverter.lexQName(cs, this._xmlStream.getNamespaceContext());
      } catch (InvalidLexicalValueException var5) {
         throw new InvalidLexicalValueException(var5.getMessage(), this._charSeq.getLocation());
      }
   }

   public void setDefaultValue(String defaultValue) throws XMLStreamException {
      this._defaultValue = defaultValue;
   }

   public Object getProperty(String s) throws IllegalArgumentException {
      return this._xmlStream.getProperty(s);
   }

   public int next() throws XMLStreamException {
      return this._xmlStream.next();
   }

   public void require(int i, String s, String s1) throws XMLStreamException {
      this._xmlStream.require(i, s, s1);
   }

   public String getElementText() throws XMLStreamException {
      return this._xmlStream.getElementText();
   }

   public int nextTag() throws XMLStreamException {
      return this._xmlStream.nextTag();
   }

   public boolean hasNext() throws XMLStreamException {
      return this._xmlStream.hasNext();
   }

   public void close() throws XMLStreamException {
      this._xmlStream.close();
   }

   public String getNamespaceURI(String s) {
      return this._xmlStream.getNamespaceURI(s);
   }

   public boolean isStartElement() {
      return this._xmlStream.isStartElement();
   }

   public boolean isEndElement() {
      return this._xmlStream.isEndElement();
   }

   public boolean isCharacters() {
      return this._xmlStream.isCharacters();
   }

   public boolean isWhiteSpace() {
      return this._xmlStream.isWhiteSpace();
   }

   public String getAttributeValue(String s, String s1) {
      return this._xmlStream.getAttributeValue(s, s1);
   }

   public int getAttributeCount() {
      return this._xmlStream.getAttributeCount();
   }

   public QName getAttributeName(int i) {
      return this._xmlStream.getAttributeName(i);
   }

   public String getAttributeNamespace(int i) {
      return this._xmlStream.getAttributeNamespace(i);
   }

   public String getAttributeLocalName(int i) {
      return this._xmlStream.getAttributeLocalName(i);
   }

   public String getAttributePrefix(int i) {
      return this._xmlStream.getAttributePrefix(i);
   }

   public String getAttributeType(int i) {
      return this._xmlStream.getAttributeType(i);
   }

   public String getAttributeValue(int i) {
      return this._xmlStream.getAttributeValue(i);
   }

   public boolean isAttributeSpecified(int i) {
      return this._xmlStream.isAttributeSpecified(i);
   }

   public int getNamespaceCount() {
      return this._xmlStream.getNamespaceCount();
   }

   public String getNamespacePrefix(int i) {
      return this._xmlStream.getNamespacePrefix(i);
   }

   public String getNamespaceURI(int i) {
      return this._xmlStream.getNamespaceURI(i);
   }

   public NamespaceContext getNamespaceContext() {
      return this._xmlStream.getNamespaceContext();
   }

   public int getEventType() {
      return this._xmlStream.getEventType();
   }

   public String getText() {
      return this._xmlStream.getText();
   }

   public char[] getTextCharacters() {
      return this._xmlStream.getTextCharacters();
   }

   public int getTextCharacters(int i, char[] chars, int i1, int i2) throws XMLStreamException {
      return this._xmlStream.getTextCharacters(i, chars, i1, i2);
   }

   public int getTextStart() {
      return this._xmlStream.getTextStart();
   }

   public int getTextLength() {
      return this._xmlStream.getTextLength();
   }

   public String getEncoding() {
      return this._xmlStream.getEncoding();
   }

   public boolean hasText() {
      return this._xmlStream.hasText();
   }

   public Location getLocation() {
      return this._xmlStream.getLocation();
   }

   public QName getName() {
      return this._xmlStream.getName();
   }

   public String getLocalName() {
      return this._xmlStream.getLocalName();
   }

   public boolean hasName() {
      return this._xmlStream.hasName();
   }

   public String getNamespaceURI() {
      return this._xmlStream.getNamespaceURI();
   }

   public String getPrefix() {
      return this._xmlStream.getPrefix();
   }

   public String getVersion() {
      return this._xmlStream.getVersion();
   }

   public boolean isStandalone() {
      return this._xmlStream.isStandalone();
   }

   public boolean standaloneSet() {
      return this._xmlStream.standaloneSet();
   }

   public String getCharacterEncodingScheme() {
      return this._xmlStream.getCharacterEncodingScheme();
   }

   public String getPITarget() {
      return this._xmlStream.getPITarget();
   }

   public String getPIData() {
      return this._xmlStream.getPIData();
   }

   private static class CharSeqTrimWS implements CharSequence {
      static final int XMLWHITESPACE_PRESERVE = 1;
      static final int XMLWHITESPACE_TRIM = 2;
      private static int INITIAL_SIZE = 100;
      private char[] _buf;
      private int _start;
      private int _length;
      private int _nonWSStart;
      private int _nonWSEnd;
      private String _toStringValue;
      private XMLStreamReaderExtImpl _xmlSteam;
      private final ExtLocation _location;
      private boolean _hasText;

      CharSeqTrimWS(XMLStreamReaderExtImpl xmlSteam) {
         this._buf = new char[INITIAL_SIZE];
         this._length = 0;
         this._nonWSStart = 0;
         this._nonWSEnd = 0;
         this._xmlSteam = xmlSteam;
         this._location = new ExtLocation();
      }

      void reload(int style) throws XMLStreamException {
         this._toStringValue = null;
         this._location.reset();
         this._hasText = false;
         this.fillBuffer();
         if (style == 1) {
            this._nonWSStart = 0;
            this._nonWSEnd = this._length;
            if (!this._hasText && this._xmlSteam._defaultValue != null) {
               this._length = 0;
               this.fillBufferFromString(this._xmlSteam._defaultValue);
            }
         } else if (style == 2) {
            for(this._nonWSStart = 0; this._nonWSStart < this._length && XMLChar.isSpace(this._buf[this._nonWSStart]); ++this._nonWSStart) {
            }

            for(this._nonWSEnd = this._length; this._nonWSEnd > this._nonWSStart && XMLChar.isSpace(this._buf[this._nonWSEnd - 1]); --this._nonWSEnd) {
            }

            if (this.length() == 0 && this._xmlSteam._defaultValue != null) {
               this._length = 0;
               this.fillBufferFromString(this._xmlSteam._defaultValue);

               for(this._nonWSStart = 0; this._nonWSStart < this._length && XMLChar.isSpace(this._buf[this._nonWSStart]); ++this._nonWSStart) {
               }

               for(this._nonWSEnd = this._length; this._nonWSEnd > this._nonWSStart && XMLChar.isSpace(this._buf[this._nonWSEnd - 1]); --this._nonWSEnd) {
               }
            }
         }

         this._xmlSteam._defaultValue = null;
      }

      private void fillBuffer() throws XMLStreamException {
         this._length = 0;
         if (this._xmlSteam.getEventType() == 7) {
            this._xmlSteam.next();
         }

         if (this._xmlSteam.isStartElement()) {
            this._xmlSteam.next();
         }

         int depth = 0;
         String error = null;
         int eventType = this._xmlSteam.getEventType();

         label35:
         while(true) {
            switch (eventType) {
               case 1:
                  ++depth;
                  error = "Unexpected element '" + this._xmlSteam.getName() + "' in text content.";
                  this._location.set(this._xmlSteam.getLocation());
                  break;
               case 2:
                  this._location.set(this._xmlSteam.getLocation());
                  --depth;
                  if (depth < 0) {
                     break label35;
                  }
               case 3:
               case 5:
               case 7:
               case 10:
               case 11:
               case 13:
               case 14:
               case 15:
               default:
                  break;
               case 4:
               case 6:
               case 12:
                  this._location.set(this._xmlSteam.getLocation());
                  if (depth == 0) {
                     this.addTextToBuffer();
                  }
                  break;
               case 8:
                  this._location.set(this._xmlSteam.getLocation());
                  break label35;
               case 9:
                  this._location.set(this._xmlSteam.getLocation());
                  this.addEntityToBuffer();
            }

            eventType = this._xmlSteam.next();
         }

         if (error != null) {
            throw new XMLStreamException(error);
         }
      }

      private void ensureBufferLength(int lengthToAdd) {
         if (this._length + lengthToAdd > this._buf.length) {
            char[] newBuf = new char[this._length + lengthToAdd];
            if (this._length > 0) {
               System.arraycopy(this._buf, 0, newBuf, 0, this._length);
            }

            this._buf = newBuf;
         }

      }

      private void fillBufferFromString(CharSequence value) {
         int textLength = value.length();
         this.ensureBufferLength(textLength);

         for(int i = 0; i < textLength; ++i) {
            this._buf[i] = value.charAt(i);
         }

         this._length = textLength;
      }

      private void addTextToBuffer() {
         this._hasText = true;
         int textLength = this._xmlSteam.getTextLength();
         this.ensureBufferLength(textLength);
         System.arraycopy(this._xmlSteam.getTextCharacters(), this._xmlSteam.getTextStart(), this._buf, this._length, textLength);
         this._length += textLength;
      }

      private void addEntityToBuffer() {
         String text = this._xmlSteam.getText();
         int textLength = text.length();
         this.ensureBufferLength(textLength);
         text.getChars(0, text.length(), this._buf, this._length);
         this._length += text.length();
      }

      CharSequence reloadAtt(int index, int style) throws XMLStreamException {
         this._location.reset();
         this._location.set(this._xmlSteam.getLocation());
         String value = this._xmlSteam.getAttributeValue(index);
         if (value == null && this._xmlSteam._defaultValue != null) {
            value = this._xmlSteam._defaultValue;
         }

         this._xmlSteam._defaultValue = null;
         int length = value.length();
         if (style == 1) {
            return value;
         } else if (style != 2) {
            throw new IllegalStateException("unknown style");
         } else {
            int nonWSStart;
            for(nonWSStart = 0; nonWSStart < length && XMLChar.isSpace(value.charAt(nonWSStart)); ++nonWSStart) {
            }

            int nonWSEnd;
            for(nonWSEnd = length; nonWSEnd > nonWSStart && XMLChar.isSpace(value.charAt(nonWSEnd - 1)); --nonWSEnd) {
            }

            return (CharSequence)(nonWSStart == 0 && nonWSEnd == length ? value : value.subSequence(nonWSStart, nonWSEnd));
         }
      }

      CharSequence reloadAtt(String uri, String local, int style) throws XMLStreamException {
         this._location.reset();
         this._location.set(this._xmlSteam.getLocation());
         String value = this._xmlSteam.getAttributeValue(uri, local);
         if (value == null && this._xmlSteam._defaultValue != null) {
            value = this._xmlSteam._defaultValue;
         }

         this._xmlSteam._defaultValue = null;
         int length = value.length();
         if (style == 1) {
            return value;
         } else if (style != 2) {
            throw new IllegalStateException("unknown style");
         } else {
            for(this._nonWSStart = 0; this._nonWSStart < length && XMLChar.isSpace(value.charAt(this._nonWSStart)); ++this._nonWSStart) {
            }

            for(this._nonWSEnd = length; this._nonWSEnd > this._nonWSStart && XMLChar.isSpace(value.charAt(this._nonWSEnd - 1)); --this._nonWSEnd) {
            }

            return (CharSequence)(this._nonWSStart == 0 && this._nonWSEnd == length ? value : value.subSequence(this._nonWSStart, this._nonWSEnd));
         }
      }

      Location getLocation() {
         ExtLocation loc = new ExtLocation();
         loc.set(this._location);
         return loc;
      }

      public int length() {
         return this._nonWSEnd - this._nonWSStart;
      }

      public char charAt(int index) {
         assert index < this._nonWSEnd - this._nonWSStart && -1 < index : "Index " + index + " must be >-1 and <" + (this._nonWSEnd - this._nonWSStart);

         return this._buf[this._nonWSStart + index];
      }

      public CharSequence subSequence(int start, int end) {
         return new String(this._buf, this._nonWSStart + start, end - start);
      }

      public String toString() {
         if (this._toStringValue != null) {
            return this._toStringValue;
         } else {
            this._toStringValue = new String(this._buf, this._nonWSStart, this._nonWSEnd - this._nonWSStart);
            return this._toStringValue;
         }
      }

      private static class ExtLocation implements Location {
         private int _line;
         private int _col;
         private int _off;
         private String _pid;
         private String _sid;
         private boolean _isSet = false;

         ExtLocation() {
         }

         public int getLineNumber() {
            if (this._isSet) {
               return this._line;
            } else {
               throw new IllegalStateException();
            }
         }

         public int getColumnNumber() {
            if (this._isSet) {
               return this._col;
            } else {
               throw new IllegalStateException();
            }
         }

         public int getCharacterOffset() {
            if (this._isSet) {
               return this._off;
            } else {
               throw new IllegalStateException();
            }
         }

         public String getPublicId() {
            if (this._isSet) {
               return this._pid;
            } else {
               throw new IllegalStateException();
            }
         }

         public String getSystemId() {
            if (this._isSet) {
               return this._sid;
            } else {
               throw new IllegalStateException();
            }
         }

         void set(Location loc) {
            if (!this._isSet) {
               this._isSet = true;
               this._line = loc.getLineNumber();
               this._col = loc.getColumnNumber();
               this._off = loc.getCharacterOffset();
               this._pid = loc.getPublicId();
               this._sid = loc.getSystemId();
            }
         }

         void reset() {
            this._isSet = false;
         }
      }
   }
}
