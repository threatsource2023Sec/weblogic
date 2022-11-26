package weblogic.xml.schema.types;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public final class XSDNonNegativeInteger extends XSDIntegerRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger");

   public static XSDNonNegativeInteger createFromXml(String xml) {
      return new XSDNonNegativeInteger(xml);
   }

   public static XSDNonNegativeInteger createFromBigInteger(BigInteger bd) {
      return new XSDNonNegativeInteger(bd);
   }

   private XSDNonNegativeInteger(String xml) {
      super(convertXml(xml), xml);
   }

   private XSDNonNegativeInteger(BigInteger f) {
      super(f, getXml(f));
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static BigInteger convertXml(String xsd_nonnegativeinteger) {
      BigInteger val = convertXml(xsd_nonnegativeinteger, XML_TYPE_NAME);
      if (!isLegalJavaValue(val)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_nonnegativeinteger, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_nonnegativeinteger, XML_TYPE_NAME);
      } else {
         return val;
      }
   }

   public static void validateXml(String xsd_nonnegativeinteger) {
      convertXml(xsd_nonnegativeinteger);
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
      return val.signum() >= 0;
   }
}
