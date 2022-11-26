package weblogic.xml.schema.types;

import java.math.BigInteger;
import javax.xml.namespace.QName;

abstract class XSDIntegerRestriction extends Number implements XSDBuiltinType, Comparable {
   private final BigInteger javaValue;
   private final String xmlValue;

   protected XSDIntegerRestriction(BigInteger f, String xml) {
      this.javaValue = f;
      this.xmlValue = xml;
   }

   protected static BigInteger optimalBigInteger(BigInteger n) {
      if (BigInteger.ZERO.equals(n)) {
         return BigInteger.ZERO;
      } else {
         return BigInteger.ONE.equals(n) ? BigInteger.ONE : n;
      }
   }

   public String getXml() {
      return this.xmlValue;
   }

   public String getCanonicalXml() {
      return getCanonicalXml(this.javaValue);
   }

   public Object getJavaObject() {
      return this.javaValue;
   }

   public BigInteger getBigInteger() {
      return this.javaValue;
   }

   public int intValue() {
      return this.javaValue.intValue();
   }

   public long longValue() {
      return this.javaValue.longValue();
   }

   public float floatValue() {
      return this.javaValue.floatValue();
   }

   public double doubleValue() {
      return this.javaValue.doubleValue();
   }

   public int compareTo(XSDIntegerRestriction aInteger) {
      return this.javaValue.compareTo(aInteger.javaValue);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDIntegerRestriction)o);
   }

   protected static BigInteger convertXml(String xsd_integer, QName type_name) {
      if (xsd_integer == null) {
         throw new NullPointerException();
      } else {
         try {
            BigInteger bi = new BigInteger(TypeUtils.trimInitialPlus(xsd_integer));
            return optimalBigInteger(bi);
         } catch (NumberFormatException var4) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_integer, type_name, var4);
            throw new IllegalLexicalValueException(msg, xsd_integer, type_name, var4);
         }
      }
   }

   protected static void validateXml(String xsd_integer, QName type) {
      convertXml(xsd_integer, type);
   }

   protected static String getXml(BigInteger bd) {
      return bd.toString();
   }

   protected static String getCanonicalXml(BigInteger bd) {
      return bd.toString();
   }

   public String toString() {
      return this.getXml();
   }

   public final boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof XSDIntegerRestriction ? ((XSDIntegerRestriction)obj).javaValue.equals(this.javaValue) : false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}
