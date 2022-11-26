package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDToken extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "token");

   public static XSDToken createFromXml(String xml) {
      return new XSDToken(xml, false);
   }

   public static XSDToken createFromJava(String xml) {
      return new XSDToken(xml, true);
   }

   private XSDToken(String xml, boolean check_xml_chars) {
      super(xml);
      validateXml(xml, check_xml_chars);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_token) {
      if (xsd_token == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_token, false);
         return xsd_token;
      }
   }

   public static void validateXml(String xsd_token, boolean check_xml_char) {
      if (xsd_token == null) {
         throw new NullPointerException();
      } else {
         int length = xsd_token.length();
         char last = '0';

         for(int i = 0; i < length; ++i) {
            char ch = xsd_token.charAt(i);
            String msg;
            if (check_xml_char && !TypeUtils.isXmlChar(ch)) {
               msg = TypeUtils.createInvalidXmlCharMsg(ch, i);
               throw new IllegalLexicalValueException(msg, xsd_token, XML_TYPE_NAME);
            }

            if (ch == '\t' || ch == '\n' || last == ' ' && ch == ' ' || (i == 0 || i == length - 1) && ch == ' ') {
               msg = "invalid " + XML_TYPE_NAME + " character (" + ch + ") found at index " + i;
               throw new IllegalLexicalValueException(msg, xsd_token, XML_TYPE_NAME);
            }

            last = ch;
         }

      }
   }

   public static String getXml(String xsd_token, boolean check_xml_chars) {
      return getCanonicalXml(xsd_token, check_xml_chars);
   }

   public static String getCanonicalXml(String xsd_token, boolean check_xml_chars) {
      validateXml(xsd_token, check_xml_chars);
      return xsd_token;
   }
}
