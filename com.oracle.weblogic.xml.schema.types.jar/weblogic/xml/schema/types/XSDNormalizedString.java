package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDNormalizedString extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "normalizedString");

   public static XSDNormalizedString createFromXml(String xml) {
      return new XSDNormalizedString(xml, false);
   }

   public static XSDNormalizedString createFromJava(String xml) {
      return new XSDNormalizedString(xml, true);
   }

   private XSDNormalizedString(String xml, boolean check_xml_chars) {
      super(xml);
      validateXml(xml, check_xml_chars);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_string) {
      if (xsd_string == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_string, false);
         return xsd_string;
      }
   }

   public static void validateXml(String xsd_string, boolean check_xml_char) {
      if (xsd_string == null) {
         throw new NullPointerException();
      } else {
         int i = 0;

         for(int str_len = xsd_string.length(); i < str_len; ++i) {
            char c = xsd_string.charAt(i);
            String msg;
            if (check_xml_char && !TypeUtils.isXmlChar(c)) {
               msg = TypeUtils.createInvalidXmlCharMsg(c, i);
               throw new IllegalLexicalValueException(msg, xsd_string, XML_TYPE_NAME);
            }

            if (c == '\t' || c == '\n' || c == '\r') {
               msg = "invalid " + XML_TYPE_NAME + " character (" + xsd_string.charAt(i) + ") found at index " + i;
               throw new IllegalLexicalValueException(msg, xsd_string, XML_TYPE_NAME);
            }
         }

      }
   }

   public static String getXml(String xsd_string, boolean check_xml_chars) {
      return getCanonicalXml(xsd_string, check_xml_chars);
   }

   public static String getCanonicalXml(String xsd_string, boolean check_xml_chars) {
      validateXml(xsd_string, check_xml_chars);
      return xsd_string;
   }
}
