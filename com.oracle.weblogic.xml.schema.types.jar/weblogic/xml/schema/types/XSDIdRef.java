package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.NameValidator;

public final class XSDIdRef extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "IDREF");

   public static XSDIdRef createFromXml(String xml) {
      return new XSDIdRef(xml);
   }

   public static XSDIdRef createFromJava(String xml) {
      return new XSDIdRef(xml);
   }

   private XSDIdRef(String xml) {
      super(xml);
      validateXml(xml);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_idref) {
      if (xsd_idref == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_idref);
         return xsd_idref;
      }
   }

   public static void validateXml(String xsd_idref) {
      if (xsd_idref == null) {
         throw new NullPointerException();
      } else if (!NameValidator.validNCName(xsd_idref)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_idref, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_idref, XML_TYPE_NAME);
      }
   }

   public static String getXml(String xsd_idref) {
      return getCanonicalXml(xsd_idref);
   }

   public static String getCanonicalXml(String xsd_idref) {
      validateXml(xsd_idref);
      return xsd_idref;
   }
}
