package weblogic.xml.schema.types;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public final class XSDInteger extends XSDIntegerRestriction implements XSDBuiltinType, Comparable {
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "integer");

   public static XSDInteger createFromXml(String xml) {
      return new XSDInteger(xml);
   }

   public static XSDInteger createFromBigInteger(BigInteger bd) {
      return new XSDInteger(bd);
   }

   private XSDInteger(String xml) {
      super(convertXml(xml), xml);
   }

   private XSDInteger(BigInteger f) {
      super(f, getXml(f));
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public static BigInteger convertXml(String xsd_nonpositiveinteger) {
      return convertXml(xsd_nonpositiveinteger, XML_TYPE_NAME);
   }

   public static void validateXml(String xsd_nonpositiveinteger) {
      convertXml(xsd_nonpositiveinteger);
   }

   public static String getXml(BigInteger bd) {
      return XSDIntegerRestriction.getXml(bd);
   }

   public static String getCanonicalXml(BigInteger bd) {
      return XSDIntegerRestriction.getCanonicalXml(bd);
   }
}
