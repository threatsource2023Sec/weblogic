package weblogic.xml.schema.types;

import java.lang.reflect.Array;
import javax.xml.namespace.QName;
import weblogic.xml.babel.reader.XmlChars;

final class TypeUtils {
   static final String SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";
   static final int XML_TOO_LONG = 30;

   static boolean isXmlChar(int ucs4char) {
      return XmlChars.isChar(ucs4char);
   }

   static String createInvalidXmlCharMsg(char c, int idx) {
      String msg = "invalid xml character (" + c + ") found at index " + idx;
      return msg;
   }

   static void validateXml(String xsd_string, QName xml_type, boolean check_xml_char) {
      if (xsd_string == null) {
         throw new IllegalArgumentException("argument cannot be null");
      } else if (check_xml_char) {
         int i = 0;

         for(int str_len = xsd_string.length(); i < str_len; ++i) {
            char c = xsd_string.charAt(i);
            if (!isXmlChar(c)) {
               String msg = createInvalidXmlCharMsg(c, i);
               throw new IllegalLexicalValueException(msg, xsd_string, xml_type);
            }
         }

      }
   }

   static String getTypeNamespace() {
      return "http://www.w3.org/2001/XMLSchema";
   }

   static String createInvalidArgMsg(String xml_val, QName xml_type) {
      if (xml_val.length() > 30) {
         xml_val = xml_val.substring(0, 30) + "...";
      }

      return "\"" + xml_val + "\" is not a valid lexical representation of " + xml_type;
   }

   static String createInvalidArgMsg(String xml_val, QName xml_type, Exception e) {
      return createInvalidArgMsg(xml_val, xml_type) + " - " + e;
   }

   static String createInvalidJavaArgMsg(Object arg, QName xml_type) {
      String arg_str = arg.toString();
      if (arg_str.length() > 30) {
         arg_str = arg_str.substring(0, 30) + "...";
      }

      return "\"" + arg_str + "\" is not a valid value for type " + xml_type;
   }

   static String createInvalidJavaArgMsg(Object arg, QName xml_type, Exception e) {
      return createInvalidJavaArgMsg(arg, xml_type) + " - " + e;
   }

   static String trimInitialPlus(String xml) {
      return xml.charAt(0) == '+' ? xml.substring(1) : xml;
   }

   static int byteArrayHashCode(byte[] b) {
      int h = 17;
      int i = 0;

      for(int len = b.length; i < len; ++i) {
         h = 37 * h + b[i];
      }

      return h;
   }

   static int arrayHashCode(Object[] b) {
      int h = 17;
      int i = 0;

      for(int len = b.length; i < len; ++i) {
         h = 37 * h + b[i].hashCode();
      }

      return h;
   }

   static String arrayToString(Object in) {
      if (in == null) {
         return "null";
      } else {
         StringBuffer buf = new StringBuffer();
         buf.append("[");
         int len = Array.getLength(in);

         for(int i = 0; i < len; ++i) {
            Object obj = Array.get(in, i);
            buf.append(obj == in ? "(this array)" : String.valueOf(obj));
            if (i < len - 1) {
               buf.append(", ");
            }
         }

         buf.append("]");
         return buf.toString();
      }
   }
}
