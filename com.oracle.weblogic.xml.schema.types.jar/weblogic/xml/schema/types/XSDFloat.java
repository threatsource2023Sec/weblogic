package weblogic.xml.schema.types;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.xml.namespace.QName;

public final class XSDFloat extends Number implements XSDBuiltinType, Comparable {
   final float javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "float");
   private static final String NaN_LEX = "NaN";
   private static final String POS_INF_LEX = "INF";
   private static final String NEG_INF_LEX = "-INF";
   private static final String POS_ZERO_LEX = "0";
   private static final String NEG_ZERO_LEX = "-0";
   public static final XSDFloat NaN = new XSDFloat(Float.NaN, "NaN");
   public static final XSDFloat POSITIVE_INFINITY = new XSDFloat(Float.POSITIVE_INFINITY, "INF");
   public static final XSDFloat NEGATIVE_INFINITY = new XSDFloat(Float.NEGATIVE_INFINITY, "-INF");
   private static final NumberFormat formatter = new DecimalFormat("0.########E0");

   public static XSDFloat createFromXml(String xml) {
      return new XSDFloat(xml);
   }

   public static XSDFloat createFromFloat(float f) {
      return new XSDFloat(f);
   }

   private XSDFloat(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDFloat(float f) {
      this.javaValue = f;
      this.xmlValue = getXml(f);
   }

   private XSDFloat(float f, String xml) {
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
      return new Float(this.javaValue);
   }

   public int intValue() {
      return (int)this.javaValue;
   }

   public long longValue() {
      return (long)this.javaValue;
   }

   public float floatValue() {
      return this.javaValue;
   }

   public double doubleValue() {
      return (double)this.javaValue;
   }

   public int compareTo(XSDFloat aFloat) {
      Float other = new Float(aFloat.javaValue);
      Float mine = new Float(this.javaValue);
      return mine.compareTo(other);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDFloat)o);
   }

   public static float convertXml(String xsd_float) {
      if (xsd_float == null) {
         throw new NullPointerException();
      } else if (Double.valueOf(xsd_float).isNaN()) {
         return Float.NaN;
      } else if ("INF".equals(xsd_float)) {
         return Float.POSITIVE_INFINITY;
      } else {
         return "-INF".equals(xsd_float) ? Float.NEGATIVE_INFINITY : naiveConvertXml(xsd_float);
      }
   }

   public static void validateXml(String xsd_float) {
      convertXml(xsd_float);
   }

   private static float naiveConvertXml(String xsd_float) {
      String msg;
      try {
         float f = Float.parseFloat(xsd_float);
         if (f != Float.POSITIVE_INFINITY && f != Float.NEGATIVE_INFINITY) {
            return f;
         } else {
            msg = TypeUtils.createInvalidArgMsg(xsd_float, XML_TYPE_NAME);
            throw new IllegalLexicalValueException(msg, xsd_float, XML_TYPE_NAME);
         }
      } catch (NumberFormatException var3) {
         msg = TypeUtils.createInvalidArgMsg(xsd_float, XML_TYPE_NAME, var3);
         throw new IllegalLexicalValueException(msg, xsd_float, XML_TYPE_NAME, var3);
      }
   }

   public static String getXml(float f) {
      if (f == Float.POSITIVE_INFINITY) {
         return "INF";
      } else {
         return f == Float.NEGATIVE_INFINITY ? "-INF" : Float.toString(f);
      }
   }

   public static String getCanonicalXml(float f) {
      if (f == 0.0F) {
         return "0";
      } else if (f == -0.0F) {
         return "-0";
      } else if (f == Float.POSITIVE_INFINITY) {
         return "INF";
      } else if (f == Float.NEGATIVE_INFINITY) {
         return "-INF";
      } else {
         return Float.isNaN(f) ? "NaN" : formatter.format((double)f);
      }
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDFloat) {
         return ((XSDFloat)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return Float.floatToIntBits(this.javaValue);
   }
}
