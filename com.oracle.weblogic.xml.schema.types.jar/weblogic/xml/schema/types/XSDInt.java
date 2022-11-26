package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDInt extends Number implements XSDBuiltinType, Comparable {
   final int javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "int");

   public static XSDInt createFromXml(String xml) {
      return new XSDInt(xml);
   }

   public static XSDInt createFromInt(int f) {
      return new XSDInt(f);
   }

   private XSDInt(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDInt(int f) {
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
      return new Integer(this.javaValue);
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

   public int compareTo(XSDInt aInt) {
      int thisVal = this.javaValue;
      int anotherVal = aInt.javaValue;
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDInt)o);
   }

   public static int convertXml(String xsd_int) {
      if (xsd_int == null) {
         throw new NullPointerException();
      } else {
         try {
            return Integer.parseInt(TypeUtils.trimInitialPlus(xsd_int));
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_int, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_int, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_int) {
      convertXml(xsd_int);
   }

   public static String getXml(int l) {
      return Integer.toString(l);
   }

   public static String getCanonicalXml(int l) {
      return Integer.toString(l);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDInt) {
         return ((XSDInt)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue;
   }
}
