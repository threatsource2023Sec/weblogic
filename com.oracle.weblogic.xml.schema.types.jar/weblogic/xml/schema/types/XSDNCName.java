package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.NameValidator;

public final class XSDNCName extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "NCName");

   public static XSDNCName createFromXml(String xml) {
      return new XSDNCName(xml);
   }

   public static XSDNCName createFromJava(String xml) {
      return new XSDNCName(xml);
   }

   private XSDNCName(String xml) {
      super(xml);
      validateXml(xml);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_ncname) {
      if (xsd_ncname == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_ncname);
         return xsd_ncname;
      }
   }

   public static void validateXml(String xsd_ncname) {
      if (xsd_ncname == null) {
         throw new NullPointerException();
      } else if (!NameValidator.validNCName(xsd_ncname)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_ncname, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_ncname, XML_TYPE_NAME);
      }
   }

   public static String getXml(String xsd_ncname) {
      return getCanonicalXml(xsd_ncname);
   }

   public static String getCanonicalXml(String xsd_ncname) {
      validateXml(xsd_ncname);
      return xsd_ncname;
   }
}
