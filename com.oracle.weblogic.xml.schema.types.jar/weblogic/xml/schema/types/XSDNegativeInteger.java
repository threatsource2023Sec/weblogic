package weblogic.xml.schema.types;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public final class XSDNegativeInteger extends XSDIntegerRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "negativeInteger");

   public static XSDNegativeInteger createFromXml(String xml) {
      return new XSDNegativeInteger(xml);
   }

   public static XSDNegativeInteger createFromBigInteger(BigInteger bd) {
      return new XSDNegativeInteger(bd);
   }

   private XSDNegativeInteger(String xml) {
      super(convertXml(xml), xml);
   }

   private XSDNegativeInteger(BigInteger f) {
      super(f, getXml(f));
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static BigInteger convertXml(String xsd_negativeinteger) {
      BigInteger val = convertXml(xsd_negativeinteger, XML_TYPE_NAME);
      if (!isLegalJavaValue(val)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_negativeinteger, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_negativeinteger, XML_TYPE_NAME);
      } else {
         return val;
      }
   }

   public static void validateXml(String xsd_negativeinteger) {
      convertXml(xsd_negativeinteger);
   }

   public static String getXml(BigInteger bd) {
      checkRange(bd);
      return XSDIntegerRestriction.getXml(bd);
   }

   public static String getCanonicalXml(BigInteger bd) {
      checkRange(bd);
      return XSDIntegerRestriction.getCanonicalXml(bd);
   }

   private static void checkRange(BigInteger bd) {
      if (!isLegalJavaValue(bd)) {
         String msg = TypeUtils.createInvalidJavaArgMsg(bd, XML_TYPE_NAME);
         throw new IllegalJavaValueException(msg, bd, XML_TYPE_NAME);
      }
   }

   private static boolean isLegalJavaValue(BigInteger val) {
      return val.signum() < 0;
   }
}
