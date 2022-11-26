package weblogic.xml.schema.types;

import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.utils.StringUtils;
import weblogic.xml.util.WhitespaceUtils;

public final class XSDNMTokens implements XSDBuiltinType {
   final String[] javaValue;
   final String xmlValue;
   int hash = 0;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "NMTOKENS");

   public static XSDNMTokens createFromXml(String xml) {
      return new XSDNMTokens(xml);
   }

   public static XSDNMTokens createFromStrings(String[] strings) {
      return new XSDNMTokens(strings);
   }

   private XSDNMTokens(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDNMTokens(String[] Strings) {
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
      return convertXml(s, (String)null);
   }

   public static String[] convertXml(String s, String ignore) {
      if (s == null) {
         throw new NullPointerException();
      } else {
         List vals = WhitespaceUtils.splitOnXMLWhiteSpace(s);
         String[] strings = new String[vals.size()];
         vals.toArray(strings);
         validateTokens(strings, ignore);
         return strings;
      }
   }

   private static final void validateTokens(String[] tokens, String ignore) {
      int i = 0;

      for(int len = tokens.length; i < len; ++i) {
         if (ignore == null || !ignore.equals(tokens[i])) {
            XSDNMToken.validateXml(tokens[i]);
         }
      }

   }

   public static void validateXml(String xsd_nmtokens) {
      convertXml(xsd_nmtokens);
   }

   public static String getXml(String[] Strings) {
      return getCanonicalXml(Strings);
   }

   public static String getCanonicalXml(String[] tokens) {
      if (tokens == null) {
         throw new NullPointerException();
      } else {
         validateTokens(tokens, (String)null);
         return StringUtils.join(tokens, " ");
      }
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDNMTokens) {
         XSDNMTokens other = (XSDNMTokens)obj;
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
