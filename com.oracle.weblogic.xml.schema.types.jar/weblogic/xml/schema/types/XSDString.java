package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDString extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "string");

   public static XSDString createFromXml(String xml) {
      return new XSDString(xml, false);
   }

   public static XSDString createFromJava(String xml) {
      return new XSDString(xml, true);
   }

   private XSDString(String xml, boolean check_xml_chars) {
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
      TypeUtils.validateXml(xsd_string, XML_TYPE_NAME, check_xml_char);
   }

   public static String getXml(String xsd_string, boolean check_xml_chars) {
      return getCanonicalXml(xsd_string, check_xml_chars);
   }

   public static String getCanonicalXml(String xsd_string, boolean check_xml_chars) {
      validateXml(xsd_string, check_xml_chars);
      return xsd_string;
   }
}
