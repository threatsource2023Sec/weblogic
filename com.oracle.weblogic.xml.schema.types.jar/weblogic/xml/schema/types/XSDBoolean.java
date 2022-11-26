package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public final class XSDBoolean implements XSDBuiltinType {
   final boolean javaValue;
   final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "boolean");
   public static final XSDBoolean TRUE = new XSDBoolean(true);
   public static final XSDBoolean FALSE = new XSDBoolean(false);

   public static XSDBoolean createFromXml(String xml) {
      if ("true".equals(xml)) {
         return TRUE;
      } else {
         return "false".equals(xml) ? FALSE : new XSDBoolean(xml);
      }
   }

   public static XSDBoolean createFromBoolean(boolean b) {
      return b ? TRUE : FALSE;
   }

   private XSDBoolean(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDBoolean(boolean b) {
      this.javaValue = b;
      this.xmlValue = getXml(b);
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
      return this.getBoolean();
   }

   public Boolean getBoolean() {
      return this.javaValue ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean booleanValue() {
      return this.javaValue;
   }

   public static boolean convertXml(String xsd_boolean) {
      if (xsd_boolean == null) {
         throw new NullPointerException();
      } else if (!"true".equals(xsd_boolean) && !"1".equals(xsd_boolean)) {
         if (!"false".equals(xsd_boolean) && !"0".equals(xsd_boolean)) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_boolean, XML_TYPE_NAME);
            throw new IllegalLexicalValueException(msg, xsd_boolean, XML_TYPE_NAME);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public static void validateXml(String xsd_boolean) {
      convertXml(xsd_boolean);
   }

   public static String getXml(boolean bool) {
      return getCanonicalXml(bool);
   }

   public static String getCanonicalXml(boolean bool) {
      return bool ? "true" : "false";
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDBoolean) {
         return ((XSDBoolean)obj).javaValue == this.javaValue;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue ? 17 : 0;
   }
}
