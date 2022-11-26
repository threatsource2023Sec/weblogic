package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDLong extends Number implements XSDBuiltinType, Comparable {
   final long javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "long");

   public static XSDLong createFromXml(String xml) {
      return new XSDLong(xml);
   }

   public static XSDLong createFromLong(long f) {
      return new XSDLong(f);
   }

   private XSDLong(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDLong(long f) {
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
      return new Long(this.javaValue);
   }

   public int intValue() {
      return (int)this.javaValue;
   }

   public long longValue() {
      return this.javaValue;
   }

   public float floatValue() {
      return (float)this.javaValue;
   }

   public double doubleValue() {
      return (double)this.javaValue;
   }

   public int compareTo(XSDLong aLong) {
      long thisVal = this.javaValue;
      long anotherVal = aLong.javaValue;
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDLong)o);
   }

   public static long convertXml(String xsd_long) {
      if (xsd_long == null) {
         throw new NullPointerException();
      } else {
         try {
            return Long.parseLong(TypeUtils.trimInitialPlus(xsd_long));
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_long, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_long, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_long) {
      convertXml(xsd_long);
   }

   public static String getXml(long l) {
      return Long.toString(l);
   }

   public static String getCanonicalXml(long l) {
      return Long.toString(l);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDLong) {
         return ((XSDLong)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return (int)(this.javaValue ^ this.javaValue >>> 32);
   }
}
