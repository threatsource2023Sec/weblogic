package com.bea.xbean.util;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xml.GDate;
import com.bea.xml.GDateBuilder;
import com.bea.xml.GDateSpecification;
import com.bea.xml.XmlCalendar;
import com.bea.xml.XmlError;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

public final class XsTypeConverter {
   private static final String POS_INF_LEX = "INF";
   private static final String NEG_INF_LEX = "-INF";
   private static final String NAN_LEX = "NaN";
   private static final char NAMESPACE_SEP = ':';
   private static final String EMPTY_PREFIX = "";
   private static final BigDecimal DECIMAL__ZERO = new BigDecimal(0.0);
   private static final String[] URI_CHARS_TO_BE_REPLACED = new String[]{" ", "{", "}", "|", "\\", "^", "[", "]", "`"};
   private static final String[] URI_CHARS_REPLACED_WITH = new String[]{"%20", "%7b", "%7d", "%7c", "%5c", "%5e", "%5b", "%5d", "%60"};
   private static final char[] CH_ZEROS = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};

   public static float lexFloat(CharSequence cs) throws NumberFormatException {
      String v = cs.toString();

      try {
         if (cs.length() > 0) {
            char ch = cs.charAt(cs.length() - 1);
            if ((ch == 'f' || ch == 'F') && cs.charAt(cs.length() - 2) != 'N') {
               throw new NumberFormatException("Invalid char '" + ch + "' in float.");
            }
         }

         return Float.parseFloat(v);
      } catch (NumberFormatException var3) {
         if (v.equals("INF")) {
            return Float.POSITIVE_INFINITY;
         } else if (v.equals("-INF")) {
            return Float.NEGATIVE_INFINITY;
         } else if (v.equals("NaN")) {
            return Float.NaN;
         } else {
            throw var3;
         }
      }
   }

   public static float lexFloat(CharSequence cs, Collection errors) {
      try {
         return lexFloat(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid float: " + cs;
         errors.add(XmlError.forMessage(msg));
         return Float.NaN;
      }
   }

   public static String printFloat(float value) {
      if (value == Float.POSITIVE_INFINITY) {
         return "INF";
      } else if (value == Float.NEGATIVE_INFINITY) {
         return "-INF";
      } else {
         return Float.isNaN(value) ? "NaN" : Float.toString(value);
      }
   }

   public static double lexDouble(CharSequence cs) throws NumberFormatException {
      String v = cs.toString();

      try {
         if (cs.length() > 0) {
            char ch = cs.charAt(cs.length() - 1);
            if (ch == 'd' || ch == 'D') {
               throw new NumberFormatException("Invalid char '" + ch + "' in double.");
            }
         }

         return Double.parseDouble(v);
      } catch (NumberFormatException var3) {
         if (v.equals("INF")) {
            return Double.POSITIVE_INFINITY;
         } else if (v.equals("-INF")) {
            return Double.NEGATIVE_INFINITY;
         } else if (v.equals("NaN")) {
            return Double.NaN;
         } else {
            throw var3;
         }
      }
   }

   public static double lexDouble(CharSequence cs, Collection errors) {
      try {
         return lexDouble(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid double: " + cs;
         errors.add(XmlError.forMessage(msg));
         return Double.NaN;
      }
   }

   public static String printDouble(double value) {
      if (value == Double.POSITIVE_INFINITY) {
         return "INF";
      } else if (value == Double.NEGATIVE_INFINITY) {
         return "-INF";
      } else {
         return Double.isNaN(value) ? "NaN" : Double.toString(value);
      }
   }

   public static BigDecimal lexDecimal(CharSequence cs) throws NumberFormatException {
      String v = cs.toString();
      return new BigDecimal(trimTrailingZeros(v));
   }

   public static BigDecimal lexDecimal(CharSequence cs, Collection errors) {
      try {
         return lexDecimal(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid long: " + cs;
         errors.add(XmlError.forMessage(msg));
         return DECIMAL__ZERO;
      }
   }

   public static String printDecimal(BigDecimal value) {
      String intStr = value.unscaledValue().toString();
      int scale = value.scale();
      if (scale != 0 && (value.longValue() != 0L || scale >= 0)) {
         int begin = value.signum() < 0 ? 1 : 0;
         int delta = scale;
         StringBuffer result = new StringBuffer(intStr.length() + 1 + Math.abs(scale));
         if (begin == 1) {
            result.append('-');
         }

         if (scale > 0) {
            delta = scale - (intStr.length() - begin);
            if (delta >= 0) {
               result.append("0.");

               while(delta > CH_ZEROS.length) {
                  result.append(CH_ZEROS);
                  delta -= CH_ZEROS.length;
               }

               result.append(CH_ZEROS, 0, delta);
               result.append(intStr.substring(begin));
            } else {
               delta = begin - delta;
               result.append(intStr.substring(begin, delta));
               result.append('.');
               result.append(intStr.substring(delta));
            }
         } else {
            result.append(intStr.substring(begin));

            while(delta < -CH_ZEROS.length) {
               result.append(CH_ZEROS);
               delta += CH_ZEROS.length;
            }

            result.append(CH_ZEROS, 0, -delta);
         }

         return result.toString();
      } else {
         return intStr;
      }
   }

   public static BigInteger lexInteger(CharSequence cs) throws NumberFormatException {
      if (cs.length() > 1 && cs.charAt(0) == '+' && cs.charAt(1) == '-') {
         throw new NumberFormatException("Illegal char sequence '+-'");
      } else {
         String v = cs.toString();
         return new BigInteger(trimInitialPlus(v));
      }
   }

   public static BigInteger lexInteger(CharSequence cs, Collection errors) {
      try {
         return lexInteger(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid long: " + cs;
         errors.add(XmlError.forMessage(msg));
         return BigInteger.ZERO;
      }
   }

   public static String printInteger(BigInteger value) {
      return value.toString();
   }

   public static long lexLong(CharSequence cs) throws NumberFormatException {
      String v = cs.toString();
      return Long.parseLong(trimInitialPlus(v));
   }

   public static long lexLong(CharSequence cs, Collection errors) {
      try {
         return lexLong(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid long: " + cs;
         errors.add(XmlError.forMessage(msg));
         return 0L;
      }
   }

   public static String printLong(long value) {
      return Long.toString(value);
   }

   public static short lexShort(CharSequence cs) throws NumberFormatException {
      return parseShort(cs);
   }

   public static short lexShort(CharSequence cs, Collection errors) {
      try {
         return lexShort(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid short: " + cs;
         errors.add(XmlError.forMessage(msg));
         return 0;
      }
   }

   public static String printShort(short value) {
      return Short.toString(value);
   }

   public static int lexInt(CharSequence cs) throws NumberFormatException {
      return parseInt(cs);
   }

   public static int lexInt(CharSequence cs, Collection errors) {
      try {
         return lexInt(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid int:" + cs;
         errors.add(XmlError.forMessage(msg));
         return 0;
      }
   }

   public static String printInt(int value) {
      return Integer.toString(value);
   }

   public static byte lexByte(CharSequence cs) throws NumberFormatException {
      return parseByte(cs);
   }

   public static byte lexByte(CharSequence cs, Collection errors) {
      try {
         return lexByte(cs);
      } catch (NumberFormatException var4) {
         String msg = "invalid byte: " + cs;
         errors.add(XmlError.forMessage(msg));
         return 0;
      }
   }

   public static String printByte(byte value) {
      return Byte.toString(value);
   }

   public static boolean lexBoolean(CharSequence v) {
      switch (v.length()) {
         case 1:
            char c = v.charAt(0);
            if ('0' == c) {
               return false;
            }

            if ('1' == c) {
               return true;
            }
         case 2:
         case 3:
         default:
            break;
         case 4:
            if ('t' == v.charAt(0) && 'r' == v.charAt(1) && 'u' == v.charAt(2) && 'e' == v.charAt(3)) {
               return true;
            }
            break;
         case 5:
            if ('f' == v.charAt(0) && 'a' == v.charAt(1) && 'l' == v.charAt(2) && 's' == v.charAt(3) && 'e' == v.charAt(4)) {
               return false;
            }
      }

      String msg = "invalid boolean: " + v;
      throw new InvalidLexicalValueException(msg);
   }

   public static boolean lexBoolean(CharSequence value, Collection errors) {
      try {
         return lexBoolean(value);
      } catch (InvalidLexicalValueException var3) {
         errors.add(XmlError.forMessage(var3.getMessage()));
         return false;
      }
   }

   public static String printBoolean(boolean value) {
      return value ? "true" : "false";
   }

   public static String lexString(CharSequence cs, Collection errors) {
      String v = cs.toString();
      return v;
   }

   public static String lexString(CharSequence lexical_value) {
      return lexical_value.toString();
   }

   public static String printString(String value) {
      return value;
   }

   public static QName lexQName(CharSequence charSeq, NamespaceContext nscontext) {
      boolean hasFirstCollon = false;

      int firstcolon;
      for(firstcolon = 0; firstcolon < charSeq.length(); ++firstcolon) {
         if (charSeq.charAt(firstcolon) == ':') {
            hasFirstCollon = true;
            break;
         }
      }

      String prefix;
      String localname;
      if (hasFirstCollon) {
         prefix = charSeq.subSequence(0, firstcolon).toString();
         localname = charSeq.subSequence(firstcolon + 1, charSeq.length()).toString();
         if (firstcolon == 0) {
            throw new InvalidLexicalValueException("invalid xsd:QName '" + charSeq.toString() + "'");
         }
      } else {
         prefix = "";
         localname = charSeq.toString();
      }

      String uri = nscontext.getNamespaceURI(prefix);
      if (uri == null) {
         if (prefix != null && prefix.length() > 0) {
            throw new InvalidLexicalValueException("Can't resolve prefix: " + prefix);
         }

         uri = "";
      }

      return new QName(uri, localname);
   }

   public static QName lexQName(String xsd_qname, Collection errors, NamespaceContext nscontext) {
      try {
         return lexQName(xsd_qname, nscontext);
      } catch (InvalidLexicalValueException var5) {
         errors.add(XmlError.forMessage(var5.getMessage()));
         int idx = xsd_qname.indexOf(58);
         return new QName((String)null, xsd_qname.substring(idx));
      }
   }

   public static String printQName(QName qname, NamespaceContext nsContext, Collection errors) {
      String uri = qname.getNamespaceURI();

      assert uri != null;

      String prefix;
      if (uri.length() > 0) {
         prefix = nsContext.getPrefix(uri);
         if (prefix == null) {
            String msg = "NamespaceContext does not provide prefix for namespaceURI " + uri;
            errors.add(XmlError.forMessage(msg));
         }
      } else {
         prefix = null;
      }

      return getQNameString(uri, qname.getLocalPart(), prefix);
   }

   public static String getQNameString(String uri, String localpart, String prefix) {
      return prefix != null && uri != null && uri.length() > 0 && prefix.length() > 0 ? prefix + ':' + localpart : localpart;
   }

   public static GDate lexGDate(CharSequence charSeq) {
      return new GDate(charSeq);
   }

   public static GDate lexGDate(String xsd_gdate, Collection errors) {
      try {
         return lexGDate(xsd_gdate);
      } catch (IllegalArgumentException var3) {
         errors.add(XmlError.forMessage(var3.getMessage()));
         return (new GDateBuilder()).toGDate();
      }
   }

   public static String printGDate(GDate gdate, Collection errors) {
      return gdate.toString();
   }

   public static XmlCalendar lexDateTime(CharSequence v) {
      GDateSpecification value = getGDateValue((CharSequence)v, 14);
      return value.getCalendar();
   }

   public static String printDateTime(Calendar c) {
      return printDateTime(c, 14);
   }

   public static String printTime(Calendar c) {
      return printDateTime(c, 15);
   }

   public static String printDate(Calendar c) {
      return printDateTime(c, 16);
   }

   public static String printDate(Date d) {
      GDateSpecification value = getGDateValue((Date)d, 16);
      return value.toString();
   }

   public static String printDateTime(Calendar c, int type_code) {
      GDateSpecification value = getGDateValue(c, type_code);
      return value.toString();
   }

   public static String printDateTime(Date c) {
      GDateSpecification value = getGDateValue((Date)c, 14);
      return value.toString();
   }

   public static CharSequence printHexBinary(byte[] val) {
      return HexBin.bytesToString(val);
   }

   public static byte[] lexHexBinary(CharSequence lexical_value) {
      byte[] buf = HexBin.decode(lexical_value.toString().getBytes());
      if (buf != null) {
         return buf;
      } else {
         throw new InvalidLexicalValueException("invalid hexBinary value");
      }
   }

   public static CharSequence printBase64Binary(byte[] val) {
      byte[] bytes = Base64.encode(val);
      return new String(bytes);
   }

   public static byte[] lexBase64Binary(CharSequence lexical_value) {
      byte[] buf = Base64.decode(lexical_value.toString().getBytes());
      if (buf != null) {
         return buf;
      } else {
         throw new InvalidLexicalValueException("invalid base64Binary value");
      }
   }

   public static GDateSpecification getGDateValue(Date d, int builtin_type_code) {
      GDateBuilder gDateBuilder = new GDateBuilder(d);
      gDateBuilder.setBuiltinTypeCode(builtin_type_code);
      GDate value = gDateBuilder.toGDate();
      return value;
   }

   public static GDateSpecification getGDateValue(Calendar c, int builtin_type_code) {
      GDateBuilder gDateBuilder = new GDateBuilder(c);
      gDateBuilder.setBuiltinTypeCode(builtin_type_code);
      GDate value = gDateBuilder.toGDate();
      return value;
   }

   public static GDateSpecification getGDateValue(CharSequence v, int builtin_type_code) {
      GDateBuilder gDateBuilder = new GDateBuilder(v);
      gDateBuilder.setBuiltinTypeCode(builtin_type_code);
      GDate value = gDateBuilder.toGDate();
      return value;
   }

   private static String trimInitialPlus(String xml) {
      return xml.length() > 0 && xml.charAt(0) == '+' ? xml.substring(1) : xml;
   }

   private static String trimTrailingZeros(String xsd_decimal) {
      int last_char_idx = xsd_decimal.length() - 1;
      if (xsd_decimal.charAt(last_char_idx) == '0') {
         int last_point = xsd_decimal.lastIndexOf(46);
         if (last_point >= 0) {
            for(int idx = last_char_idx; idx > last_point; --idx) {
               if (xsd_decimal.charAt(idx) != '0') {
                  return xsd_decimal.substring(0, idx + 1);
               }
            }

            return xsd_decimal.substring(0, last_point);
         }
      }

      return xsd_decimal;
   }

   private static int parseInt(CharSequence cs) {
      return parseIntXsdNumber(cs, Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   private static short parseShort(CharSequence cs) {
      return (short)parseIntXsdNumber(cs, -32768, 32767);
   }

   private static byte parseByte(CharSequence cs) {
      return (byte)parseIntXsdNumber(cs, -128, 127);
   }

   private static int parseIntXsdNumber(CharSequence ch, int min_value, int max_value) {
      int length = ch.length();
      if (length < 1) {
         throw new NumberFormatException("For input string: \"" + ch.toString() + "\"");
      } else {
         int sign = 1;
         int result = 0;
         int start = 0;
         char c = ch.charAt(0);
         int limit;
         int limit2;
         if (c == '-') {
            ++start;
            limit = min_value / 10;
            limit2 = -(min_value % 10);
         } else if (c == '+') {
            ++start;
            sign = -1;
            limit = -(max_value / 10);
            limit2 = max_value % 10;
         } else {
            sign = -1;
            limit = -(max_value / 10);
            limit2 = max_value % 10;
         }

         for(int i = 0; i < length - start; ++i) {
            c = ch.charAt(i + start);
            int v = Character.digit(c, 10);
            if (v < 0) {
               throw new NumberFormatException("For input string: \"" + ch.toString() + "\"");
            }

            if (result < limit || result == limit && v > limit2) {
               throw new NumberFormatException("For input string: \"" + ch.toString() + "\"");
            }

            result = result * 10 - v;
         }

         return sign * result;
      }
   }

   public static CharSequence printAnyURI(CharSequence val) {
      return val;
   }

   public static CharSequence lexAnyURI(CharSequence lexical_value) {
      StringBuffer s = new StringBuffer(lexical_value.toString());

      for(int ic = 0; ic < URI_CHARS_TO_BE_REPLACED.length; ++ic) {
         for(int i = 0; (i = s.indexOf(URI_CHARS_TO_BE_REPLACED[ic], i)) >= 0; i += 3) {
            s.replace(i, i + 1, URI_CHARS_REPLACED_WITH[ic]);
         }
      }

      try {
         URI.create(s.toString());
         return lexical_value;
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException("invalid anyURI value: " + lexical_value, var4);
      }
   }
}
