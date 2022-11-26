package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDNotation implements XSDBuiltinType, Comparable {
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "NOTATION");

   public static XSDNotation createFromXml(String xml) {
      return new XSDNotation(xml, false);
   }

   public static XSDNotation createFromJava(String xml) {
      return new XSDNotation(xml, true);
   }

   private XSDNotation(String xml, boolean check_xml_chars) {
      validateXml(xml, check_xml_chars);
      this.xmlValue = xml;
   }

   public String getXml() {
      return this.xmlValue;
   }

   public String getCanonicalXml() {
      return this.xmlValue;
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public Object getJavaObject() {
      return this.xmlValue;
   }

   public int compareTo(XSDNotation aString) {
      return this.xmlValue.compareTo(aString.xmlValue);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDNotation)o);
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

   public String toString() {
      return this.xmlValue;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof XSDNotation ? ((XSDNotation)obj).xmlValue.equals(this.xmlValue) : false;
      }
   }

   public final int hashCode() {
      return this.xmlValue.hashCode();
   }
}
