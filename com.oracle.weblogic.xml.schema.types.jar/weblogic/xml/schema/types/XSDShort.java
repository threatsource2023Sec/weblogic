package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDShort extends Number implements XSDBuiltinType, Comparable {
   final short javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "short");

   public static XSDShort createFromXml(String xml) {
      return new XSDShort(xml);
   }

   public static XSDShort createFromShort(short f) {
      return new XSDShort(f);
   }

   private XSDShort(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDShort(short f) {
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
      return new Short(this.javaValue);
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

   public int compareTo(XSDShort aShort) {
      return this.javaValue - aShort.javaValue;
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDShort)o);
   }

   public static short convertXml(String xsd_short) {
      if (xsd_short == null) {
         throw new NullPointerException();
      } else {
         try {
            return Short.parseShort(TypeUtils.trimInitialPlus(xsd_short));
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_short, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_short, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_short) {
      convertXml(xsd_short);
   }

   public static String getXml(short l) {
      return Short.toString(l);
   }

   public static String getCanonicalXml(short l) {
      return Short.toString(l);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDShort) {
         return ((XSDShort)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue;
   }
}
