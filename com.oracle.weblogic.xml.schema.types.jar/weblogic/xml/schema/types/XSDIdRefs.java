package weblogic.xml.schema.types;

import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.utils.StringUtils;
import weblogic.xml.util.WhitespaceUtils;

public final class XSDIdRefs implements XSDBuiltinType {
   final String[] javaValue;
   final String xmlValue;
   int hash = 0;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "IDREFS");

   public static XSDIdRefs createFromXml(String xml) {
      return new XSDIdRefs(xml);
   }

   public static XSDIdRefs createFromStrings(String[] strings) {
      return new XSDIdRefs(strings);
   }

   private XSDIdRefs(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDIdRefs(String[] Strings) {
      this.javaValue = Strings;
      this.xmlValue = getCanonicalXml(Strings);
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
      return this.javaValue;
   }

   public String[] getStrings() {
      return this.javaValue;
   }

   public static String[] convertXml(String s) {
      if (s == null) {
         throw new NullPointerException();
      } else {
         List vals = WhitespaceUtils.splitOnXMLWhiteSpace(s);
         String[] strings = new String[vals.size()];
         vals.toArray(strings);
         validateIdrefs(strings);
         return strings;
      }
   }

   private static final void validateIdrefs(String[] idrefs) {
      int i = 0;

      for(int len = idrefs.length; i < len; ++i) {
         XSDIdRef.validateXml(idrefs[i]);
      }

   }

   public static void validateXml(String xsd_idrefs) {
      convertXml(xsd_idrefs);
   }

   public static String getXml(String[] Strings) {
      return getCanonicalXml(Strings);
   }

   public static String getCanonicalXml(String[] idrefs) {
      if (idrefs == null) {
         throw new NullPointerException();
      } else {
         validateIdrefs(idrefs);
         return StringUtils.join(idrefs, " ");
      }
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDIdRefs) {
         XSDIdRefs other = (XSDIdRefs)obj;
         return other.hash != this.hash && other.hash != 0 && this.hash != 0 ? false : Arrays.equals(other.javaValue, this.javaValue);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      if (this.hash == 0) {
         this.hash = TypeUtils.arrayHashCode(this.javaValue);
      }

      return this.hash;
   }
}
