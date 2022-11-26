package weblogic.xml.schema.types;

import java.util.Arrays;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.HexBinary;

public final class XSDHexBinary implements XSDBuiltinType {
   final byte[] javaValue;
   final String xmlValue;
   int hash = 0;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "hexBinary");

   public static XSDHexBinary createFromXml(String xml) {
      return new XSDHexBinary(xml);
   }

   public static XSDHexBinary createFromBytes(byte[] bytes) {
      return new XSDHexBinary(bytes);
   }

   private XSDHexBinary(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDHexBinary(byte[] bytes) {
      this.javaValue = bytes;
      this.xmlValue = getCanonicalXml(bytes);
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

   public byte[] getBytes() {
      return this.javaValue;
   }

   public static byte[] convertXml(String s) {
      if (s == null) {
         throw new NullPointerException();
      } else {
         return HexBinary.decode(s);
      }
   }

   public static void validateXml(String xsd_hexBinary) {
      convertXml(xsd_hexBinary);
   }

   public static String getXml(byte[] bytes) {
      return getCanonicalXml(bytes);
   }

   public static String getCanonicalXml(byte[] bytes) {
      if (bytes == null) {
         throw new NullPointerException();
      } else {
         try {
            return HexBinary.encode(bytes);
         } catch (Exception var4) {
            var4.printStackTrace();
            String array_str = TypeUtils.arrayToString(bytes);
            String msg = TypeUtils.createInvalidJavaArgMsg(array_str, XML_TYPE_NAME, var4);
            throw new IllegalLexicalValueException(msg, array_str, XML_TYPE_NAME, var4);
         }
      }
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDHexBinary) {
         XSDHexBinary other = (XSDHexBinary)obj;
         int other_hash = other.hash;
         int this_hash = this.hash;
         return other_hash != this_hash && other_hash != 0 && this_hash != 0 ? false : Arrays.equals(other.javaValue, this.javaValue);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      int this_hash = this.hash;
      if (this_hash == 0) {
         this_hash = TypeUtils.byteArrayHashCode(this.javaValue);
         this.hash = this_hash;
      }

      return this_hash;
   }
}
