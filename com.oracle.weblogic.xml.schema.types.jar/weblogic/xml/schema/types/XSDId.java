package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.NameValidator;

public final class XSDId extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "ID");

   public static XSDId createFromXml(String xml) {
      return new XSDId(xml);
   }

   public static XSDId createFromJava(String xml) {
      return new XSDId(xml);
   }

   private XSDId(String xml) {
      super(xml);
      validateXml(xml);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_id) {
      if (xsd_id == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_id);
         return xsd_id;
      }
   }

   public static void validateXml(String xsd_id) {
      if (xsd_id == null) {
         throw new NullPointerException();
      } else if (!NameValidator.validNCName(xsd_id)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_id, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_id, XML_TYPE_NAME);
      }
   }

   public static String getXml(String xsd_id) {
      return getCanonicalXml(xsd_id);
   }

   public static String getCanonicalXml(String xsd_id) {
      validateXml(xsd_id);
      return xsd_id;
   }
}
