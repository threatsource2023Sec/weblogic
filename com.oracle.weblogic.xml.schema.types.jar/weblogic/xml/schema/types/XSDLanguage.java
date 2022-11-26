package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.LanguageValidator;

public final class XSDLanguage extends XSDStringRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "language");

   public static XSDLanguage createFromXml(String xml) {
      return new XSDLanguage(xml);
   }

   public static XSDLanguage createFromJava(String xml) {
      return new XSDLanguage(xml);
   }

   private XSDLanguage(String xml) {
      super(xml);
      validateXml(xml);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static String convertXml(String xsd_language) {
      if (xsd_language == null) {
         throw new NullPointerException();
      } else {
         validateXml(xsd_language);
         return xsd_language;
      }
   }

   public static void validateXml(String xsd_language) {
      if (xsd_language == null) {
         throw new NullPointerException();
      } else if (!LanguageValidator.validLanguage(xsd_language)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_language, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_language, XML_TYPE_NAME);
      }
   }

   public static String getXml(String xsd_language) {
      return getCanonicalXml(xsd_language);
   }

   public static String getCanonicalXml(String xsd_language) {
      validateXml(xsd_language);
      return xsd_language;
   }
}
