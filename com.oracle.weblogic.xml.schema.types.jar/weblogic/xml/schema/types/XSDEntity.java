package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.NameValidator;

public final class XSDEntity extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "ENTITY");

   public static XSDEntity createFromXml(String xml) {
      return new XSDEntity(xml);
   }

   public static XSDEntity createFromJava(String xml) {
      return new XSDEntity(xml);
   }

   private XSDEntity(String xml) {
      super(xml);
      validateXml(xml);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_entity) {
      if (xsd_entity == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_entity);
         return xsd_entity;
      }
   }

   public static void validateXml(String xsd_entity) {
      if (xsd_entity == null) {
         throw new NullPointerException();
      } else if (!NameValidator.validNCName(xsd_entity)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_entity, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_entity, XML_TYPE_NAME);
      }
   }

   public static String getXml(String xsd_entity) {
      return getCanonicalXml(xsd_entity);
   }

   public static String getCanonicalXml(String xsd_entity) {
      validateXml(xsd_entity);
      return xsd_entity;
   }
}
