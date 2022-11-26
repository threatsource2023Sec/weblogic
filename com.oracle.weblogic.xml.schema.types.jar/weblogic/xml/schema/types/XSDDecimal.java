package weblogic.xml.schema.types;

import java.math.BigDecimal;
import javax.xml.namespace.QName;

public final class XSDDecimal extends Number implements XSDBuiltinType, Comparable {
   final BigDecimal javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "decimal");

   public static XSDDecimal createFromXml(String xml) {
      return new XSDDecimal(xml);
   }

   public static XSDDecimal createFromBigDecimal(BigDecimal bd) {
      return new XSDDecimal(bd);
   }

   private XSDDecimal(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDDecimal(BigDecimal f) {
      this.javaValue = f;
      this.xmlValue = getXml(f);
   }

   public String getXml() {
      return this.xmlValue;
   }

   public String getCanonicalXml() {
      return getCanonicalXml(this.javaValue);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public Object getJavaObject() {
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

   public int compareTo(XSDDecimal aDecimal) {
      return this.javaValue.compareTo(aDecimal.javaValue);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDDecimal)o);
   }

   public static BigDecimal convertXml(String xsd_decimal) {
      if (xsd_decimal == null) {
         throw new NullPointerException();
      } else {
         try {
            return new BigDecimal(trimTrailingZeros(xsd_decimal));
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_decimal, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_decimal, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_decimal) {
      convertXml(xsd_decimal);
   }

   private static String trimTrailingZeros(String xsd_decimal) {
      int last_char_idx = xsd_decimal.length() - 1;
      if (xsd_decimal.charAt(last_char_idx) == '0') {
         int last_point = xsd_decimal.lastIndexOf(46);
         if (last_point >= 0) {
            for(int idx = last_char_idx; idx > last_point; --idx) {
               if (xsd_decimal.charAt(idx) != '0') {
                  return xsd_decimal.substring(0, idx + 1);
               }
            }

            return xsd_decimal.substring(0, last_point);
         }
      }

      return xsd_decimal;
   }

   public static String getXml(BigDecimal bd) {
      return bd.toString();
   }

   public static String getCanonicalXml(BigDecimal bd) {
      String base = bd.toString();
      return bd.scale() < 1 ? base + ".0" : base;
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof XSDDecimal ? ((XSDDecimal)obj).javaValue.equals(this.javaValue) : false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}
