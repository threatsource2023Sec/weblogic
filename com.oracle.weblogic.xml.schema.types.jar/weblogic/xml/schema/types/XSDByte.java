package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDByte extends Number implements XSDBuiltinType, Comparable {
   final byte javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "byte");

   public static XSDByte createFromXml(String xml) {
      return new XSDByte(xml);
   }

   public static XSDByte createFromByte(byte f) {
      return new XSDByte(f);
   }

   private XSDByte(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDByte(byte f) {
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
      return new Byte(this.javaValue);
   }

   public int intValue() {
      return this.javaValue;
   }

   public long longValue() {
      return (long)this.javaValue;
   }

   public float floatValue() {
      return (float)this.javaValue;
   }

   public double doubleValue() {
      return (double)this.javaValue;
   }

   public int compareTo(XSDByte aByte) {
      return this.javaValue - aByte.javaValue;
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDByte)o);
   }

   public static byte convertXml(String xsd_byte) {
      if (xsd_byte == null) {
         throw new NullPointerException();
      } else {
         try {
            return Byte.parseByte(TypeUtils.trimInitialPlus(xsd_byte));
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_byte, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_byte, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_byte) {
      convertXml(xsd_byte);
   }

   public static String getXml(byte l) {
      return Byte.toString(l);
   }

   public static String getCanonicalXml(byte l) {
      return Byte.toString(l);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDByte) {
         return ((XSDByte)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue;
   }
}
