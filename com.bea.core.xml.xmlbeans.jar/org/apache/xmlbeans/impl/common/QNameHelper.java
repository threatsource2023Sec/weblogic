package org.apache.xmlbeans.impl.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.xml.stream.XMLName;

public class QNameHelper {
   private static final Map WELL_KNOWN_PREFIXES = buildWKP();
   private static final char[] hexdigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   public static final int MAX_NAME_LENGTH = 64;
   public static final String URI_SHA1_PREFIX = "URI_SHA_1_";

   public static XMLName getXMLName(QName qname) {
      return qname == null ? null : XMLNameHelper.forLNS(qname.getLocalPart(), qname.getNamespaceURI());
   }

   public static QName forLNS(String localname, String uri) {
      if (uri == null) {
         uri = "";
      }

      return new QName(uri, localname);
   }

   public static QName forLN(String localname) {
      return new QName("", localname);
   }

   public static QName forPretty(String pretty, int offset) {
      int at = pretty.indexOf(64, offset);
      return at < 0 ? new QName("", pretty.substring(offset)) : new QName(pretty.substring(at + 1), pretty.substring(offset, at));
   }

   public static String pretty(QName name) {
      if (name == null) {
         return "null";
      } else {
         return name.getNamespaceURI() != null && name.getNamespaceURI().length() != 0 ? name.getLocalPart() + "@" + name.getNamespaceURI() : name.getLocalPart();
      }
   }

   private static boolean isSafe(int c) {
      if (c >= 97 && c <= 122) {
         return true;
      } else if (c >= 65 && c <= 90) {
         return true;
      } else {
         return c >= 48 && c <= 57;
      }
   }

   public static String hexsafe(String s) {
      StringBuffer result = new StringBuffer();

      int j;
      byte[] digest;
      for(int i = 0; i < s.length(); ++i) {
         char ch = s.charAt(i);
         if (isSafe(ch)) {
            result.append(ch);
         } else {
            byte[] utf8 = null;

            try {
               digest = s.substring(i, i + 1).getBytes("UTF-8");

               for(j = 0; j < digest.length; ++j) {
                  result.append('_');
                  result.append(hexdigits[digest[j] >> 4 & 15]);
                  result.append(hexdigits[digest[j] & 15]);
               }
            } catch (UnsupportedEncodingException var8) {
               result.append("_BAD_UTF8_CHAR");
            }
         }
      }

      if (result.length() <= 64) {
         return result.toString();
      } else {
         try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] inputBytes = null;

            byte[] inputBytes;
            try {
               inputBytes = s.getBytes("UTF-8");
            } catch (UnsupportedEncodingException var6) {
               inputBytes = new byte[0];
            }

            digest = md.digest(inputBytes);

            assert digest.length == 20;

            result = new StringBuffer("URI_SHA_1_");

            for(j = 0; j < digest.length; ++j) {
               result.append(hexdigits[digest[j] >> 4 & 15]);
               result.append(hexdigits[digest[j] & 15]);
            }

            return result.toString();
         } catch (NoSuchAlgorithmException var7) {
            throw new IllegalStateException("Using in a JDK without an SHA implementation");
         }
      }
   }

   public static String hexsafedir(QName name) {
      return name.getNamespaceURI() != null && name.getNamespaceURI().length() != 0 ? hexsafe(name.getNamespaceURI()) + "/" + hexsafe(name.getLocalPart()) : "_nons/" + hexsafe(name.getLocalPart());
   }

   private static Map buildWKP() {
      Map result = new HashMap();
      result.put("http://www.w3.org/XML/1998/namespace", "xml");
      result.put("http://www.w3.org/2001/XMLSchema", "xs");
      result.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
      result.put("http://schemas.xmlsoap.org/wsdl/", "wsdl");
      result.put("http://schemas.xmlsoap.org/soap/encoding/", "soapenc");
      result.put("http://schemas.xmlsoap.org/soap/envelope/", "soapenv");
      return Collections.unmodifiableMap(result);
   }

   public static String readable(SchemaType sType) {
      return readable(sType, WELL_KNOWN_PREFIXES);
   }

   public static String readable(SchemaType sType, Map nsPrefix) {
      if (sType.getName() != null) {
         return readable(sType.getName(), nsPrefix);
      } else if (sType.isAttributeType()) {
         return "attribute type " + readable(sType.getAttributeTypeAttributeName(), nsPrefix);
      } else if (sType.isDocumentType()) {
         return "document type " + readable(sType.getDocumentElementName(), nsPrefix);
      } else if (!sType.isNoType() && sType.getOuterType() != null) {
         SchemaType outerType = sType.getOuterType();
         SchemaField container = sType.getContainerField();
         if (outerType.isAttributeType()) {
            return "type of attribute " + readable(container.getName(), nsPrefix);
         } else if (outerType.isDocumentType()) {
            return "type of element " + readable(container.getName(), nsPrefix);
         } else if (container != null) {
            return container.isAttribute() ? "type of " + container.getName().getLocalPart() + " attribute in " + readable(outerType, nsPrefix) : "type of " + container.getName().getLocalPart() + " element in " + readable(outerType, nsPrefix);
         } else if (outerType.getBaseType() == sType) {
            return "base type of " + readable(outerType, nsPrefix);
         } else if (outerType.getSimpleVariety() == 3) {
            return "item type of " + readable(outerType, nsPrefix);
         } else {
            return outerType.getSimpleVariety() == 2 ? "member type " + sType.getAnonymousUnionMemberOrdinal() + " of " + readable(outerType, nsPrefix) : "inner type in " + readable(outerType, nsPrefix);
         }
      } else {
         return "invalid type";
      }
   }

   public static String readable(QName name) {
      return readable(name, WELL_KNOWN_PREFIXES);
   }

   public static String readable(QName name, Map prefixes) {
      if (name.getNamespaceURI().length() == 0) {
         return name.getLocalPart();
      } else {
         String prefix = (String)prefixes.get(name.getNamespaceURI());
         return prefix != null ? prefix + ":" + name.getLocalPart() : name.getLocalPart() + " in namespace " + name.getNamespaceURI();
      }
   }

   public static String suggestPrefix(String namespace) {
      String result = (String)WELL_KNOWN_PREFIXES.get(namespace);
      if (result != null) {
         return result;
      } else {
         int len = namespace.length();
         int i = namespace.lastIndexOf(47);
         if (i > 0 && i == namespace.length() - 1) {
            len = i;
            i = namespace.lastIndexOf(47, i - 1);
         }

         ++i;
         if (namespace.startsWith("www.", i)) {
            i += 4;
         }

         while(i < len && !XMLChar.isNCNameStart(namespace.charAt(i))) {
            ++i;
         }

         for(int end = i + 1; end < len; ++end) {
            if (!XMLChar.isNCName(namespace.charAt(end)) || !Character.isLetterOrDigit(namespace.charAt(end))) {
               len = end;
               break;
            }
         }

         if (namespace.length() >= i + 3 && startsWithXml(namespace, i)) {
            return namespace.length() >= i + 4 ? "x" + Character.toLowerCase(namespace.charAt(i + 3)) : "ns";
         } else {
            if (len - i > 4) {
               if (isVowel(namespace.charAt(i + 2)) && !isVowel(namespace.charAt(i + 3))) {
                  len = i + 4;
               } else {
                  len = i + 3;
               }
            }

            return len - i == 0 ? "ns" : namespace.substring(i, len).toLowerCase();
         }
      }
   }

   private static boolean startsWithXml(String s, int i) {
      if (s.length() < i + 3) {
         return false;
      } else if (s.charAt(i) != 'X' && s.charAt(i) != 'x') {
         return false;
      } else if (s.charAt(i + 1) != 'M' && s.charAt(i + 1) != 'm') {
         return false;
      } else {
         return s.charAt(i + 2) == 'L' || s.charAt(i + 2) == 'l';
      }
   }

   private static boolean isVowel(char ch) {
      switch (ch) {
         case 'A':
         case 'E':
         case 'I':
         case 'O':
         case 'U':
         case 'a':
         case 'e':
         case 'i':
         case 'o':
         case 'u':
            return true;
         default:
            return false;
      }
   }

   public static String namespace(SchemaType sType) {
      while(sType != null) {
         if (sType.getName() != null) {
            return sType.getName().getNamespaceURI();
         }

         if (sType.getContainerField() != null && sType.getContainerField().getName().getNamespaceURI().length() > 0) {
            return sType.getContainerField().getName().getNamespaceURI();
         }

         sType = sType.getOuterType();
      }

      return "";
   }

   public static String getLocalPart(String qname) {
      int index = qname.indexOf(58);
      return index < 0 ? qname : qname.substring(index + 1);
   }

   public static String getPrefixPart(String qname) {
      int index = qname.indexOf(58);
      return index >= 0 ? qname.substring(0, index) : "";
   }
}
