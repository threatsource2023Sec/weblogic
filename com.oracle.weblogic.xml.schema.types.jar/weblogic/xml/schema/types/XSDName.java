package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.NameValidator;

public final class XSDName extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "Name");

   public static XSDName createFromXml(String xml) {
      return new XSDName(xml);
   }

   public static XSDName createFromJava(String xml) {
      return new XSDName(xml);
   }

   private XSDName(String xml) {
      super(xml);
      validateXml(xml);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_name) {
      if (xsd_name == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_name);
         return xsd_name;
      }
   }

   public static void validateXml(String xsd_name) {
      if (xsd_name == null) {
         throw new NullPointerException();
      } else if (!NameValidator.validName(xsd_name)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_name, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_name, XML_TYPE_NAME);
      }
   }

   public static String getXml(String xsd_name) {
      return getCanonicalXml(xsd_name);
   }

   public static String getCanonicalXml(String xsd_name) {
      validateXml(xsd_name);
      return xsd_name;
   }
}
