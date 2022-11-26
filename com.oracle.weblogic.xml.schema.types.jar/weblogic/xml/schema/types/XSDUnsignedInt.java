package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDUnsignedInt extends Number implements XSDBuiltinType, Comparable {
   final long javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt");
   public static final long MAX_LONG_VALUE = 4294967295L;
   public static final XSDUnsignedInt MAX_VALUE = new XSDUnsignedInt(4294967295L);

   public static XSDUnsignedInt createFromXml(String xml) {
      return new XSDUnsignedInt(xml);
   }

   public static XSDUnsignedInt createFromLong(long f) {
      return new XSDUnsignedInt(f);
   }

   private XSDUnsignedInt(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDUnsignedInt(long f) {
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

   public int compareTo(XSDUnsignedInt aLong) {
      long thisVal = this.javaValue;
      long anotherVal = aLong.javaValue;
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDUnsignedInt)o);
   }

   public static long convertXml(String xsd_unsignedint) {
      if (xsd_unsignedint == null) {
         throw new NullPointerException();
      } else {
         try {
            long val = Long.parseLong(TypeUtils.trimInitialPlus(xsd_unsignedint));
            if (!isLegalJavaValue(val)) {
               String msg = TypeUtils.createInvalidArgMsg(xsd_unsignedint, XML_TYPE_NAME);
               throw new IllegalLexicalValueException(msg, xsd_unsignedint, XML_TYPE_NAME);
            } else {
               return val;
            }
         } catch (NumberFormatException var4) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_unsignedint, XML_TYPE_NAME, var4);
            throw new IllegalLexicalValueException(msg, xsd_unsignedint, XML_TYPE_NAME, var4);
         }
      }
   }

   public static void validateXml(String xsd_unsignedint) {
      convertXml(xsd_unsignedint);
   }

   public static String getXml(long l) {
      checkRange(l);
      return Long.toString(l);
   }

   public static String getCanonicalXml(long l) {
      checkRange(l);
      return getXml(l);
   }

   private static void checkRange(long l) {
      if (!isLegalJavaValue(l)) {
         Long val = new Long(l);
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
      } else if (obj instanceof XSDUnsignedInt) {
         return ((XSDUnsignedInt)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return (int)(this.javaValue ^ this.javaValue >>> 32);
   }

   private static boolean isLegalJavaValue(long val) {
      return val >= 0L && val <= 4294967295L;
   }
}
