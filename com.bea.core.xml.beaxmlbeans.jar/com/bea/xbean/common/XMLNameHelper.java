package com.bea.xbean.common;

import java.io.UnsupportedEncodingException;
import javax.xml.namespace.QName;
import weblogic.xml.stream.XMLName;

public class XMLNameHelper {
   private static final char[] hexdigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   public static QName getQName(XMLName xmlName) {
      return xmlName == null ? null : QNameHelper.forLNS(xmlName.getLocalName(), xmlName.getNamespaceUri());
   }

   public static XMLName forLNS(String localname, String uri) {
      if (uri == null) {
         uri = "";
      }

      return new XmlNameImpl(uri, localname);
   }

   public static XMLName forLN(String localname) {
      return new XmlNameImpl("", localname);
   }

   public static XMLName forPretty(String pretty, int offset) {
      int at = pretty.indexOf(64, offset);
      return at < 0 ? new XmlNameImpl("", pretty.substring(offset)) : new XmlNameImpl(pretty.substring(at + 1), pretty.substring(offset, at));
   }

   public static String pretty(XMLName name) {
      if (name == null) {
         return "null";
      } else {
         return name.getNamespaceUri() != null && name.getNamespaceUri().length() != 0 ? name.getLocalName() + "@" + name.getNamespaceUri() : name.getLocalName();
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

      for(int i = 0; i < s.length(); ++i) {
         char ch = s.charAt(i);
         if (isSafe(ch)) {
            result.append(ch);
         } else {
            byte[] utf8 = null;

            try {
               byte[] utf8 = s.substring(i, i + 1).getBytes("UTF-8");

               for(int j = 0; j < utf8.length; ++j) {
                  result.append('_');
                  result.append(hexdigits[utf8[j] >> 4 & 15]);
                  result.append(hexdigits[utf8[j] & 15]);
               }
            } catch (UnsupportedEncodingException var6) {
               result.append("_BAD_UTF8_CHAR");
            }
         }
      }

      return result.toString();
   }

   public static String hexsafedir(XMLName name) {
      return name.getNamespaceUri() != null && name.getNamespaceUri().length() != 0 ? hexsafe(name.getNamespaceUri()) + "/" + hexsafe(name.getLocalName()) : "_nons/" + hexsafe(name.getLocalName());
   }
}
