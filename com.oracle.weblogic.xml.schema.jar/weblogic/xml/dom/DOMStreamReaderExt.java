package weblogic.xml.dom;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.common.XmlWhitespace;
import com.bea.xbean.richParser.XMLStreamReaderExt;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Node;
import weblogic.xml.util.WhitespaceUtils;

public class DOMStreamReaderExt extends DOMStreamReader implements XMLStreamReaderExt {
   private final List contents = new ArrayList();
   private String _defaultValue;

   public DOMStreamReaderExt(Node n) throws XMLStreamException {
      super(n);
   }

   public String getStringValue() throws XMLStreamException {
      return this.getCurrentPreserveCharSeq().toString();
   }

   public String getStringValue(int wsStyle) throws XMLStreamException {
      return XmlWhitespace.collapse(this.getCurrentPreserveCharSeq().toString(), wsStyle);
   }

   public boolean getBooleanValue() throws XMLStreamException, InvalidLexicalValueException {
      return XsTypeConverter.lexBoolean(this.getCurrentTrimCharSeq());
   }

   public byte getByteValue() throws XMLStreamException, InvalidLexicalValueException {
      return XsTypeConverter.lexByte(this.getCurrentTrimCharSeq());
   }

   public short getShortValue() throws XMLStreamException, InvalidLexicalValueException {
      return XsTypeConverter.lexShort(this.getCurrentTrimCharSeq());
   }

   public int getIntValue() throws XMLStreamException, InvalidLexicalValueException {
      return XsTypeConverter.lexInt(this.getCurrentTrimCharSeq());
   }

   public long getLongValue() throws XMLStreamException {
      return XsTypeConverter.lexLong(this.getCurrentTrimCharSeq());
   }

   public BigInteger getBigIntegerValue() throws XMLStreamException {
      CharSequence ret = this.getCurrentTrimCharSeq();
      return "".equals(ret.toString()) ? null : XsTypeConverter.lexInteger(ret);
   }

   public BigDecimal getBigDecimalValue() throws XMLStreamException {
      CharSequence ret = this.getCurrentTrimCharSeq();
      return "".equals(ret.toString()) ? null : XsTypeConverter.lexDecimal(ret);
   }

   public float getFloatValue() throws XMLStreamException {
      return XsTypeConverter.lexFloat(this.getCurrentTrimCharSeq());
   }

   public double getDoubleValue() throws XMLStreamException {
      return XsTypeConverter.lexDouble(this.getCurrentTrimCharSeq());
   }

   public InputStream getHexBinaryValue() throws XMLStreamException, InvalidLexicalValueException {
      String text = this.getCurrentTrimCharSeq().toString();
      byte[] buf = HexBin.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid hexBinary value");
      }
   }

   public InputStream getBase64Value() throws XMLStreamException, InvalidLexicalValueException {
      String text = this.getCurrentTrimCharSeq().toString();
      byte[] buf = Base64.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid base64Binary value");
      }
   }

   public XmlCalendar getCalendarValue() throws XMLStreamException, InvalidLexicalValueException {
      try {
         return (new GDateBuilder(this.getCurrentTrimCharSeq())).getCalendar();
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2);
      }
   }

   public Date getDateValue() throws XMLStreamException, InvalidLexicalValueException {
      try {
         return (new GDateBuilder(this.getCurrentTrimCharSeq())).getDate();
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2);
      }
   }

   public GDate getGDateValue() throws XMLStreamException, InvalidLexicalValueException {
      try {
         return XsTypeConverter.lexGDate(this.getCurrentTrimCharSeq());
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2);
      }
   }

   public GDuration getGDurationValue() throws XMLStreamException, InvalidLexicalValueException {
      try {
         return new GDuration(this.getCurrentTrimCharSeq());
      } catch (IllegalArgumentException var2) {
         throw new InvalidLexicalValueException(var2);
      }
   }

   public QName getQNameValue() throws XMLStreamException, InvalidLexicalValueException {
      try {
         return XsTypeConverter.lexQName(this.getCurrentTrimCharSeq(), this.getNamespaceContext());
      } catch (InvalidLexicalValueException var2) {
         throw new InvalidLexicalValueException(var2);
      }
   }

   public String getAttributeStringValue(int index) throws XMLStreamException {
      return this.getAttributeValue(index);
   }

   public String getAttributeStringValue(int index, int wsStyle) throws XMLStreamException {
      return XmlWhitespace.collapse(this.getAttributeValue(index), wsStyle);
   }

   public boolean getAttributeBooleanValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexBoolean(this.getCurrentAttributeTrimCharSeq(index));
      } catch (InvalidLexicalValueException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public byte getAttributeByteValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexByte(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public short getAttributeShortValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexShort(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public int getAttributeIntValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexInt(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public long getAttributeLongValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexLong(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public BigInteger getAttributeBigIntegerValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexInteger(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public BigDecimal getAttributeBigDecimalValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexDecimal(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public float getAttributeFloatValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexFloat(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public double getAttributeDoubleValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexDouble(this.getCurrentAttributeTrimCharSeq(index));
      } catch (NumberFormatException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public InputStream getAttributeHexBinaryValue(int index) throws XMLStreamException {
      String text = this.getCurrentAttributeTrimCharSeq(index).toString();
      byte[] buf = HexBin.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid hexBinary value");
      }
   }

   public InputStream getAttributeBase64Value(int index) throws XMLStreamException {
      String text = this.getCurrentAttributeTrimCharSeq(index).toString();
      byte[] buf = Base64.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid base64Binary value");
      }
   }

   public XmlCalendar getAttributeCalendarValue(int index) throws XMLStreamException {
      try {
         return (new GDateBuilder(this.getCurrentAttributeTrimCharSeq(index))).getCalendar();
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public Date getAttributeDateValue(int index) throws XMLStreamException {
      try {
         return (new GDateBuilder(this.getCurrentAttributeTrimCharSeq(index))).getDate();
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public GDate getAttributeGDateValue(int index) throws XMLStreamException {
      try {
         return new GDate(this.getCurrentAttributeTrimCharSeq(index));
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public GDuration getAttributeGDurationValue(int index) throws XMLStreamException {
      try {
         return new GDuration(this.getCurrentAttributeTrimCharSeq(index));
      } catch (IllegalArgumentException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public QName getAttributeQNameValue(int index) throws XMLStreamException {
      try {
         return XsTypeConverter.lexQName(this.getCurrentAttributeTrimCharSeq(index), this.getNamespaceContext());
      } catch (InvalidLexicalValueException var3) {
         throw new InvalidLexicalValueException(var3);
      }
   }

   public String getAttributeStringValue(String uri, String local) throws XMLStreamException {
      return this.getAttributeValue(uri, local);
   }

   public String getAttributeStringValue(String uri, String local, int wsStyle) throws XMLStreamException {
      return XmlWhitespace.collapse(this.getAttributeValue(uri, local), wsStyle);
   }

   public boolean getAttributeBooleanValue(String uri, String local) throws XMLStreamException {
      return XsTypeConverter.lexBoolean(this.getCurrentAttributeTrimCharSeq(uri, local));
   }

   public byte getAttributeByteValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexByte(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public short getAttributeShortValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexShort(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public int getAttributeIntValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexInt(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public long getAttributeLongValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexLong(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public BigInteger getAttributeBigIntegerValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexInteger(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public BigDecimal getAttributeBigDecimalValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexDecimal(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public float getAttributeFloatValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexFloat(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public double getAttributeDoubleValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexDouble(cs);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public InputStream getAttributeHexBinaryValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);
      String text = cs.toString();
      byte[] buf = HexBin.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid hexBinary value");
      }
   }

   public InputStream getAttributeBase64Value(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);
      String text = cs.toString();
      byte[] buf = Base64.decode(text.getBytes());
      if (buf != null) {
         return new ByteArrayInputStream(buf);
      } else {
         throw new InvalidLexicalValueException("invalid base64Binary value");
      }
   }

   public XmlCalendar getAttributeCalendarValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return (new GDateBuilder(cs)).getCalendar();
      } catch (IllegalArgumentException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public Date getAttributeDateValue(String uri, String local) throws XMLStreamException {
      try {
         CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);
         return (new GDateBuilder(cs)).getDate();
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4);
      }
   }

   public GDate getAttributeGDateValue(String uri, String local) throws XMLStreamException {
      try {
         CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);
         return new GDate(cs);
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4);
      }
   }

   public GDuration getAttributeGDurationValue(String uri, String local) throws XMLStreamException {
      try {
         return new GDuration(this.getCurrentAttributeTrimCharSeq(uri, local));
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4);
      }
   }

   public QName getAttributeQNameValue(String uri, String local) throws XMLStreamException {
      CharSequence cs = this.getCurrentAttributeTrimCharSeq(uri, local);

      try {
         return XsTypeConverter.lexQName(cs, this.getNamespaceContext());
      } catch (InvalidLexicalValueException var5) {
         throw new InvalidLexicalValueException(var5);
      }
   }

   public void setDefaultValue(String defaultValue) throws XMLStreamException {
      this._defaultValue = defaultValue;
   }

   private CharSequence getCurrentTrimCharSeq() throws XMLStreamException {
      assert this.contents.isEmpty();

      this.fillContents();
      CharSequence retval = this.getTrimCharSeq();
      retval = this.handleDefault(retval);
      this.contents.clear();
      return retval;
   }

   private CharSequence getCurrentPreserveCharSeq() throws XMLStreamException {
      assert this.contents.isEmpty();

      this.fillContents();
      CharSequence retval = this.getPreserveCharSeq();
      retval = this.handleDefault(retval);
      this.contents.clear();
      return retval;
   }

   private CharSequence handleDefault(CharSequence retval) {
      if (retval.length() == 0 && this._defaultValue != null) {
         retval = DOMStreamReaderExt.SingleStringTrimWsCharSeq.create(this._defaultValue);
      }

      this._defaultValue = null;
      return retval;
   }

   private CharSequence getTrimCharSeq() {
      int len = this.contents.size();
      if (len == 0) {
         return "";
      } else {
         return len == 1 ? DOMStreamReaderExt.SingleStringTrimWsCharSeq.create((CharSequence)this.contents.get(0)) : DOMStreamReaderExt.MultiStringTrimWsCharSeq.create(this.contents);
      }
   }

   private CharSequence getPreserveCharSeq() {
      int len = this.contents.size();
      if (len == 0) {
         return "";
      } else {
         return len == 1 ? (CharSequence)this.contents.get(0) : DOMStreamReaderExt.MultiStringCharSeq.create(this.contents);
      }
   }

   private void fillContents() throws XMLStreamException {
      if (this.getEventType() == 7) {
         this.next();
      }

      if (this.isStartElement()) {
         this.next();
      }

      int depth = 0;
      String error = null;
      int eventType = this.getEventType();

      label35:
      while(true) {
         switch (eventType) {
            case 1:
               ++depth;
               error = "Unexpected element '" + this.getName() + "' in text content.";
               break;
            case 2:
               --depth;
               if (depth < 0) {
                  break label35;
               }
            case 3:
            case 5:
            case 7:
            case 10:
            case 11:
            default:
               break;
            case 4:
            case 6:
            case 9:
            case 12:
               if (depth == 0) {
                  this.contents.add(this.getText());
               }
               break;
            case 8:
               break label35;
         }

         eventType = this.next();
      }

      if (error != null) {
         throw new XMLStreamException(error);
      }
   }

   private CharSequence getCurrentAttributeTrimCharSeq(int index) {
      return DOMStreamReaderExt.SingleStringTrimWsCharSeq.create(this.getAttributeValue(index));
   }

   private CharSequence getCurrentAttributeTrimCharSeq(String uri, String local) {
      return DOMStreamReaderExt.SingleStringTrimWsCharSeq.create(this.getAttributeValue(uri, local));
   }

   private static final class MultiStringCharSeq {
      public static CharSequence create(List contents) {
         if (contents.isEmpty()) {
            return "";
         } else {
            StringBuilder sb = new StringBuilder();
            int i = 0;

            for(int len = contents.size(); i < len; ++i) {
               sb.append(contents.get(i));
            }

            return sb;
         }
      }
   }

   private static final class MultiStringTrimWsCharSeq {
      public static CharSequence create(List contents) {
         assert !contents.isEmpty();

         StringBuilder sb = new StringBuilder();
         int i = 0;

         for(int len = contents.size(); i < len; ++i) {
            sb.append(contents.get(i));
         }

         return DOMStreamReaderExt.SingleStringTrimWsCharSeq.create(sb);
      }
   }

   private static final class SingleStringTrimWsCharSeq implements CharSequence {
      private final String str;
      private final int start;
      private final int length;

      static CharSequence create(CharSequence str) {
         int strlen = str.length();
         if (strlen == 0) {
            return "";
         } else {
            int start;
            for(start = 0; start < strlen && WhitespaceUtils.isWhitespace(str.charAt(start)); ++start) {
            }

            int end;
            for(end = strlen - 1; end >= start && WhitespaceUtils.isWhitespace(str.charAt(end)); --end) {
            }

            return (CharSequence)(start == end + 1 ? "" : str.subSequence(start, end + 1));
         }
      }

      private SingleStringTrimWsCharSeq(String str, int start, int length) {
         this.str = str;
         this.start = start;
         this.length = length;
      }

      public int length() {
         return this.length;
      }

      public char charAt(int index) {
         return this.str.charAt(this.start + index);
      }

      public CharSequence subSequence(int start, int end) {
         return this.str.subSequence(this.start + start, this.start + end);
      }
   }
}
