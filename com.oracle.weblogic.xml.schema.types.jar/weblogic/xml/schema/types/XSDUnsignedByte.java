package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDUnsignedByte extends Number implements XSDBuiltinType, Comparable {
   final short javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedByte");
   public static final short MAX_SHORT_VALUE = 255;
   public static final XSDUnsignedByte MAX_VALUE = new XSDUnsignedByte((short)255);

   public static XSDUnsignedByte createFromXml(String xml) {
      return new XSDUnsignedByte(xml);
   }

   public static XSDUnsignedByte createFromShort(short f) {
      return new XSDUnsignedByte(f);
   }

   private XSDUnsignedByte(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDUnsignedByte(short f) {
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

   public int compareTo(XSDUnsignedByte aShort) {
      short thisVal = this.javaValue;
      short anotherVal = aShort.javaValue;
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDUnsignedByte)o);
   }

   public static short convertXml(String xsd_unsignedbyte) {
      if (xsd_unsignedbyte == null) {
         throw new NullPointerException();
      } else {
         String msg;
         try {
            short val = Short.parseShort(TypeUtils.trimInitialPlus(xsd_unsignedbyte));
            if (!isLegalJavaValue(val)) {
               msg = TypeUtils.createInvalidArgMsg(xsd_unsignedbyte, XML_TYPE_NAME);
               throw new IllegalLexicalValueException(msg, xsd_unsignedbyte, XML_TYPE_NAME);
            } else {
               return val;
            }
         } catch (NumberFormatException var3) {
            msg = TypeUtils.createInvalidArgMsg(xsd_unsignedbyte, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_unsignedbyte, XML_TYPE_NAME);
         }
      }
   }

   public static void validateXml(String xsd_unsignedbyte) {
      convertXml(xsd_unsignedbyte);
   }

   public static String getXml(short l) {
      checkRange(l);
      return Short.toString(l);
   }

   public static String getCanonicalXml(short l) {
      return getXml(l);
   }

   private static void checkRange(short l) {
      if (!isLegalJavaValue(l)) {
         Short val = new Short(l);
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
      } else if (obj instanceof XSDUnsignedByte) {
         return ((XSDUnsignedByte)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue;
   }

   private static boolean isLegalJavaValue(short val) {
      return val >= 0 && val <= 255;
   }
}
