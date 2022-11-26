package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.NameValidator;

public final class XSDNMToken extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "NMTOKEN");

   public static XSDNMToken createFromXml(String xml) {
      return new XSDNMToken(xml);
   }

   public static XSDNMToken createFromJava(String xml) {
      return new XSDNMToken(xml);
   }

   private XSDNMToken(String xml) {
      super(xml);
      validateXml(xml);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_nmtoken) {
      if (xsd_nmtoken == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_nmtoken);
         return xsd_nmtoken;
      }
   }

   public static void validateXml(String xsd_nmtoken) {
      if (xsd_nmtoken == null) {
         throw new NullPointerException();
      } else if (!NameValidator.validNmtoken(xsd_nmtoken)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_nmtoken, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_nmtoken, XML_TYPE_NAME);
      }
   }

   public static String getXml(String xsd_nmtoken) {
      return getCanonicalXml(xsd_nmtoken);
   }

   public static String getCanonicalXml(String xsd_nmtoken) {
      validateXml(xsd_nmtoken);
      return xsd_nmtoken;
   }
}
