package weblogic.xml.schema.types;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.xml.namespace.QName;

public final class XSDDouble extends Number implements XSDBuiltinType, Comparable {
   final double javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "double");
   private static final String NaN_LEX = "NaN";
   private static final String POS_INF_LEX = "INF";
   private static final String NEG_INF_LEX = "-INF";
   private static final String POS_ZERO_LEX = "0";
   private static final String NEG_ZERO_LEX = "-0";
   public static final XSDDouble NaN = new XSDDouble(Double.NaN, "NaN");
   public static final XSDDouble POSITIVE_INFINITY = new XSDDouble(Double.POSITIVE_INFINITY, "INF");
   public static final XSDDouble NEGATIVE_INFINITY = new XSDDouble(Double.NEGATIVE_INFINITY, "-INF");
   private static final NumberFormat formatter = new DecimalFormat("0.########E0");

   public static XSDDouble createFromXml(String xml) {
      return new XSDDouble(xml);
   }

   public static XSDDouble createFromDouble(double f) {
      return new XSDDouble(f);
   }

   private XSDDouble(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDDouble(double f) {
      this.javaValue = f;
      this.xmlValue = getXml(f);
   }

   private XSDDouble(double f, String xml) {
      this.javaValue = f;
      this.xmlValue = xml;
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
      return new Double(this.javaValue);
   }

   public int intValue() {
      return (int)this.javaValue;
   }

   public long longValue() {
      return (long)this.javaValue;
   }

   public double doubleValue() {
      return this.javaValue;
   }

   public float floatValue() {
      return (float)this.javaValue;
   }

   public int compareTo(XSDDouble aDouble) {
      Double other = new Double(aDouble.javaValue);
      Double mine = new Double(this.javaValue);
      return mine.compareTo(other);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDDouble)o);
   }

   public static double convertXml(String xsd_double) {
      if (xsd_double == null) {
         throw new NullPointerException();
      } else if (Double.valueOf(xsd_double).isNaN()) {
         return Double.NaN;
      } else if ("INF".equals(xsd_double)) {
         return Double.POSITIVE_INFINITY;
      } else {
         return "-INF".equals(xsd_double) ? Double.NEGATIVE_INFINITY : naiveConvertXml(xsd_double);
      }
   }

   public static void validateXml(String xsd_double) {
      convertXml(xsd_double);
   }

   private static double naiveConvertXml(String xsd_double) {
      try {
         double f = Double.parseDouble(xsd_double);
         if (f != Double.POSITIVE_INFINITY && f != Double.NEGATIVE_INFINITY) {
            return f;
         } else {
            String msg = TypeUtils.createInvalidArgMsg(xsd_double, XML_TYPE_NAME);
            throw new IllegalLexicalValueException(msg, xsd_double, XML_TYPE_NAME);
         }
      } catch (NumberFormatException var4) {
         String msg = TypeUtils.createInvalidArgMsg(xsd_double, XML_TYPE_NAME, var4);
         throw new IllegalLexicalValueException(msg, xsd_double, XML_TYPE_NAME, var4);
      }
   }

   public static String getXml(double f) {
      if (f == Double.POSITIVE_INFINITY) {
         return "INF";
      } else {
         return f == Double.NEGATIVE_INFINITY ? "-INF" : Double.toString(f);
      }
   }

   public static String getCanonicalXml(double f) {
      if (f == 0.0) {
         return "0";
      } else if (f == -0.0) {
         return "-0";
      } else if (f == Double.POSITIVE_INFINITY) {
         return "INF";
      } else if (f == Double.NEGATIVE_INFINITY) {
         return "-INF";
      } else {
         return Double.isNaN(f) ? "NaN" : formatter.format(f);
      }
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDDouble) {
         return ((XSDDouble)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      long bits = Double.doubleToLongBits(this.javaValue);
      return (int)(bits ^ bits >>> 32);
   }
}
