package weblogic.xml.schema.types;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public final class XSDUnsignedLong extends XSDIntegerRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedLong");
   private static final BigInteger MAX_INTEGER_VALUE = new BigInteger("18446744073709551615");
   public static final XSDUnsignedLong MAX_VALUE;

   public static XSDUnsignedLong createFromXml(String xml) {
      return new XSDUnsignedLong(xml);
   }

   public static XSDUnsignedLong createFromBigInteger(BigInteger bd) {
      return new XSDUnsignedLong(bd);
   }

   private XSDUnsignedLong(String xml) {
      super(convertXml(xml), xml);
   }

   private XSDUnsignedLong(BigInteger f) {
      super(f, getXml(f));
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static BigInteger convertXml(String xsd_unsignedlong) {
      BigInteger val = convertXml(xsd_unsignedlong, XML_TYPE_NAME);
      if (!isLegalJavaValue(val)) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_unsignedlong, XML_TYPE_NAME);
         throw new IllegalLexicalValueException(msg, xsd_unsignedlong, XML_TYPE_NAME);
      } else {
         return val;
      }
   }

   public static void validateXml(String xsd_unsignedlong) {
      convertXml(xsd_unsignedlong);
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
      if (val.signum() < 0) {
         return false;
      } else {
         return val.compareTo(MAX_INTEGER_VALUE) <= 0;
      }
   }

   static {
      MAX_VALUE = new XSDUnsignedLong(MAX_INTEGER_VALUE);
   }
}
