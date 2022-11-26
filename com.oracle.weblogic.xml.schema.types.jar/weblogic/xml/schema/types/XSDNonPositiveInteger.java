package weblogic.xml.schema.types;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public final class XSDNonPositiveInteger extends XSDIntegerRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "nonPositiveInteger");

   public static XSDNonPositiveInteger createFromXml(String xml) {
      return new XSDNonPositiveInteger(xml);
   }

   public static XSDNonPositiveInteger createFromBigInteger(BigInteger bd) {
      return new XSDNonPositiveInteger(bd);
   }

   private XSDNonPositiveInteger(String xml) {
      super(convertXml(xml), xml);
   }

   private XSDNonPositiveInteger(BigInteger f) {
      super(f, getXml(f));
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static BigInteger convertXml(String xsd_nonpositiveinteger) {
      BigInteger val = convertXml(xsd_nonpositiveinteger, XML_TYPE_NAME);
      if (!isLegalJavaValue(val)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_nonpositiveinteger, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_nonpositiveinteger, XML_TYPE_NAME);
      } else {
         return val;
      }
   }

   public static void validateXml(String xsd_nonpositiveinteger) {
      convertXml(xsd_nonpositiveinteger);
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
      return val.signum() <= 0;
   }
}
