package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDUnsignedShort extends Number implements XSDBuiltinType, Comparable {
   final int javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedShort");
   public static final int MAX_INT_VALUE = 65535;
   public static final XSDUnsignedShort MAX_VALUE = new XSDUnsignedShort(65535);

   public static XSDUnsignedShort createFromXml(String xml) {
      return new XSDUnsignedShort(xml);
   }

   public static XSDUnsignedShort createFromInt(int f) {
      return new XSDUnsignedShort(f);
   }

   private XSDUnsignedShort(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDUnsignedShort(int f) {
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

   public int compareTo(XSDUnsignedShort aInt) {
      int thisVal = this.javaValue;
      int anotherVal = aInt.javaValue;
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDUnsignedShort)o);
   }

   public static int convertXml(String xsd_unsignedshort) {
      if (xsd_unsignedshort == null) {
         throw new NullPointerException();
      } else {
         String msg;
         try {
            int val = Integer.parseInt(TypeUtils.trimInitialPlus(xsd_unsignedshort));
            if (!isLegalJavaValue(val)) {
               msg = TypeUtils.createInvalidArgMsg(xsd_unsignedshort, XML_TYPE_NAME);
               throw new IllegalLexicalValueException(msg, xsd_unsignedshort, XML_TYPE_NAME);
            } else {
               return val;
            }
         } catch (NumberFormatException var3) {
            msg = TypeUtils.createInvalidArgMsg(xsd_unsignedshort, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_unsignedshort, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_unsignedshort) {
      convertXml(xsd_unsignedshort);
   }

   public static String getXml(int l) {
      checkRange(l);
      return Integer.toString(l);
   }

   public static String getCanonicalXml(int l) {
      checkRange(l);
      return getXml(l);
   }

   private static void checkRange(int l) {
      if (!isLegalJavaValue(l)) {
         Integer val = new Integer(l);
         String msg = TypeUtils.createInvalidJavaArgMsg(val, XML_TYPE_NAME);
         throw new IllegalJavaValueException(msg, val, XML_TYPE_NAME);
      }
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDUnsignedShort) {
         return ((XSDUnsignedShort)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue;
   }

   private static boolean isLegalJavaValue(int val) {
      return val >= 0 && val <= 65535;
   }
}
