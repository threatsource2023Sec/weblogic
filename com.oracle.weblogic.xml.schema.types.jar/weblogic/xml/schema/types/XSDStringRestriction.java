package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

abstract class XSDStringRestriction implements XSDBuiltinType, Comparable {
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "normalizedString");

   protected XSDStringRestriction(String xml) {
      this.xmlValue = xml;
   }

   public String getXml() {
      return this.xmlValue;
   }

   public String getCanonicalXml() {
      return this.xmlValue;
   }

   public Object getJavaObject() {
      return this.xmlValue;
   }

   public int compareTo(XSDStringRestriction aString) {
      return this.xmlValue.compareTo(aString.xmlValue);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDStringRestriction)o);
   }

   public final String toString() {
      return this.xmlValue;
   }

   public final boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof XSDStringRestriction ? ((XSDStringRestriction)obj).xmlValue.equals(this.xmlValue) : false;
      }
   }

   public final int hashCode() {
      return this.xmlValue.hashCode();
   }
}
