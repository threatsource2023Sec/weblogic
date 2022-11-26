package weblogic.xml.schema.types;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public final class XSDPositiveInteger extends XSDIntegerRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "positiveInteger");

   public static XSDPositiveInteger createFromXml(String xml) {
      return new XSDPositiveInteger(xml);
   }

   public static XSDPositiveInteger createFromBigInteger(BigInteger bd) {
      return new XSDPositiveInteger(bd);
   }

   private XSDPositiveInteger(String xml) {
      super(convertXml(xml), xml);
   }

   private XSDPositiveInteger(BigInteger f) {
      super(f, getXml(f));
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static BigInteger convertXml(String xsd_positiveinteger) {
      BigInteger val = convertXml(xsd_positiveinteger, XML_TYPE_NAME);
      if (!isLegalJavaValue(val)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_positiveinteger, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_positiveinteger, XML_TYPE_NAME);
      } else {
         return val;
      }
   }

   public static void validateXml(String xsd_positiveinteger) {
      convertXml(xsd_positiveinteger);
   }

   public static String getXml(BigInteger bd) {
      if (!isLegalJavaValue(bd)) {
         String msg = TypeUtils.createInvalidJavaArgMsg(bd, XML_TYPE_NAME);
         throw new IllegalJavaValueException(msg, bd, XML_TYPE_NAME);
      } else {
         return XSDIntegerRestriction.getXml(bd);
      }
   }

   public static String getCanonicalXml(BigInteger bd) {
      return XSDIntegerRestriction.getCanonicalXml(bd);
   }

   private static boolean isLegalJavaValue(BigInteger val) {
      return val.signum() > 0;
   }
}
