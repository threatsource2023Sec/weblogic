package weblogic.xml.schema.types;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import javax.xml.namespace.QName;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.xml.schema.binding.util.runtime.ByteList;

public final class XSDBase64Binary implements XSDBuiltinType {
   final byte[] javaValue;
   final String xmlValue;
   int hash = 0;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "base64Binary");

   public static XSDBase64Binary createFromXml(String xml) {
      return new XSDBase64Binary(xml);
   }

   public static XSDBase64Binary createFromBytes(byte[] bytes) {
      return new XSDBase64Binary(bytes);
   }

   private XSDBase64Binary(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDBase64Binary(byte[] bytes) {
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

   public static byte[] convertXml(String s) {
      if (s == null) {
         throw new NullPointerException();
      } else {
         String msg;
         try {
            InputStream is = new ByteArrayInputStream(s.getBytes());
            InputStream is = MimeUtility.decode(is, "base64");
            ByteList bytes = new ByteList(s.length());

            while(true) {
               int b = is.read();
               if (b == -1) {
                  return bytes.getMinSizedArray();
               }

               bytes.add((byte)b);
            }
         } catch (IOException var4) {
            msg = TypeUtils.createInvalidArgMsg(s, XML_TYPE_NAME, var4);
            throw new IllegalLexicalValueException(msg, s, XML_TYPE_NAME, var4);
         } catch (MessagingException var5) {
            msg = TypeUtils.createInvalidArgMsg(s, XML_TYPE_NAME, var5);
            throw new IllegalLexicalValueException(msg, s, XML_TYPE_NAME, var5);
         }
      }
   }

   public static void validateXml(String xsd_base64Binary) {
      convertXml(xsd_base64Binary);
   }

   public static String getXml(byte[] bytes) {
      return getCanonicalXml(bytes);
   }

   public static String getCanonicalXml(byte[] bytes) {
      BASE64Encoder enc = new BASE64Encoder();
      return enc.encodeBuffer(bytes);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDBase64Binary) {
         XSDBase64Binary other = (XSDBase64Binary)obj;
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
