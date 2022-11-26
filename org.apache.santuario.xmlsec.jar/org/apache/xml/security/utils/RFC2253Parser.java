package org.apache.xml.security.utils;

import java.io.IOException;
import java.io.StringReader;

public class RFC2253Parser {
   public static String rfc2253toXMLdsig(String dn) {
      String normalized = normalize(dn, true);
      return rfctoXML(normalized);
   }

   public static String xmldsigtoRFC2253(String dn) {
      String normalized = normalize(dn, false);
      return xmltoRFC(normalized);
   }

   public static String normalize(String dn) {
      return normalize(dn, true);
   }

   public static String normalize(String dn, boolean toXml) {
      if (dn != null && !dn.equals("")) {
         try {
            String DN = semicolonToComma(dn);
            StringBuilder sb = new StringBuilder();
            int i = 0;
            int l = 0;

            int k;
            for(int j = 0; (k = DN.indexOf(44, j)) >= 0; j = k + 1) {
               l += countQuotes(DN, j, k);
               if (k > 0 && DN.charAt(k - 1) != '\\' && l % 2 == 0) {
                  sb.append(parseRDN(DN.substring(i, k).trim(), toXml)).append(",");
                  i = k + 1;
                  l = 0;
               }
            }

            sb.append(parseRDN(trim(DN.substring(i)), toXml));
            return sb.toString();
         } catch (IOException var8) {
            return dn;
         }
      } else {
         return "";
      }
   }

   static String parseRDN(String str, boolean toXml) throws IOException {
      StringBuilder sb = new StringBuilder();
      int i = 0;
      int l = 0;

      int k;
      for(int j = 0; (k = str.indexOf(43, j)) >= 0; j = k + 1) {
         l += countQuotes(str, j, k);
         if (k > 0 && str.charAt(k - 1) != '\\' && l % 2 == 0) {
            sb.append(parseATAV(trim(str.substring(i, k)), toXml)).append("+");
            i = k + 1;
            l = 0;
         }
      }

      sb.append(parseATAV(trim(str.substring(i)), toXml));
      return sb.toString();
   }

   static String parseATAV(String str, boolean toXml) throws IOException {
      int i = str.indexOf(61);
      if (i == -1 || i > 0 && str.charAt(i - 1) == '\\') {
         return str;
      } else {
         String attrType = normalizeAT(str.substring(0, i));
         String attrValue = null;
         if (attrType.charAt(0) >= '0' && attrType.charAt(0) <= '9') {
            attrValue = str.substring(i + 1);
         } else {
            attrValue = normalizeV(str.substring(i + 1), toXml);
         }

         return attrType + "=" + attrValue;
      }
   }

   static String normalizeAT(String str) {
      String at = str.toUpperCase().trim();
      if (at.startsWith("OID")) {
         at = at.substring(3);
      }

      return at;
   }

   static String normalizeV(String str, boolean toXml) throws IOException {
      String value = trim(str);
      if (value.startsWith("\"")) {
         StringBuilder sb = new StringBuilder();
         StringReader sr = new StringReader(value.substring(1, value.length() - 1));

         char c;
         int i;
         for(int i = false; (i = sr.read()) > -1; sb.append(c)) {
            c = (char)i;
            if (c == ',' || c == '=' || c == '+' || c == '<' || c == '>' || c == '#' || c == ';') {
               sb.append('\\');
            }
         }

         value = trim(sb.toString());
      }

      if (toXml) {
         if (value.startsWith("#")) {
            value = '\\' + value;
         }
      } else if (value.startsWith("\\#")) {
         value = value.substring(1);
      }

      return value;
   }

   static String rfctoXML(String string) {
      try {
         String s = changeLess32toXML(string);
         return changeWStoXML(s);
      } catch (Exception var2) {
         return string;
      }
   }

   static String xmltoRFC(String string) {
      try {
         String s = changeLess32toRFC(string);
         return changeWStoRFC(s);
      } catch (Exception var2) {
         return string;
      }
   }

   static String changeLess32toRFC(String string) throws IOException {
      StringBuilder sb = new StringBuilder();
      StringReader sr = new StringReader(string);
      int i = false;

      while(true) {
         while(true) {
            int i;
            while((i = sr.read()) > -1) {
               char c = (char)i;
               if (c == '\\') {
                  sb.append(c);
                  char c1 = (char)sr.read();
                  char c2 = (char)sr.read();
                  if ((c1 >= '0' && c1 <= '9' || c1 >= 'A' && c1 <= 'F' || c1 >= 'a' && c1 <= 'f') && (c2 >= '0' && c2 <= '9' || c2 >= 'A' && c2 <= 'F' || c2 >= 'a' && c2 <= 'f')) {
                     char ch = (char)Byte.parseByte("" + c1 + c2, 16);
                     sb.append(ch);
                  } else {
                     sb.append(c1);
                     sb.append(c2);
                  }
               } else {
                  sb.append(c);
               }
            }

            return sb.toString();
         }
      }
   }

   static String changeLess32toXML(String string) throws IOException {
      StringBuilder sb = new StringBuilder();
      StringReader sr = new StringReader(string);
      int i = false;

      int i;
      while((i = sr.read()) > -1) {
         if (i < 32) {
            sb.append('\\');
            sb.append(Integer.toHexString(i));
         } else {
            sb.append((char)i);
         }
      }

      return sb.toString();
   }

   static String changeWStoXML(String string) throws IOException {
      StringBuilder sb = new StringBuilder();
      StringReader sr = new StringReader(string);
      int i = false;

      int i;
      while((i = sr.read()) > -1) {
         char c = (char)i;
         if (c == '\\') {
            char c1 = (char)sr.read();
            if (c1 == ' ') {
               sb.append('\\');
               String s = "20";
               sb.append(s);
            } else {
               sb.append('\\');
               sb.append(c1);
            }
         } else {
            sb.append(c);
         }
      }

      return sb.toString();
   }

   static String changeWStoRFC(String string) {
      StringBuilder sb = new StringBuilder();
      int i = 0;

      int k;
      for(int j = 0; (k = string.indexOf("\\20", j)) >= 0; j = k + 3) {
         sb.append(trim(string.substring(i, k))).append("\\ ");
         i = k + 3;
      }

      sb.append(string.substring(i));
      return sb.toString();
   }

   static String semicolonToComma(String str) {
      return removeWSandReplace(str, ";", ",");
   }

   static String removeWhiteSpace(String str, String symbol) {
      return removeWSandReplace(str, symbol, symbol);
   }

   static String removeWSandReplace(String str, String symbol, String replace) {
      StringBuilder sb = new StringBuilder();
      int i = 0;
      int l = 0;

      int k;
      for(int j = 0; (k = str.indexOf(symbol, j)) >= 0; j = k + 1) {
         l += countQuotes(str, j, k);
         if (k > 0 && str.charAt(k - 1) != '\\' && l % 2 == 0) {
            sb.append(trim(str.substring(i, k))).append(replace);
            i = k + 1;
            l = 0;
         }
      }

      sb.append(trim(str.substring(i)));
      return sb.toString();
   }

   private static int countQuotes(String s, int i, int j) {
      int k = 0;

      for(int l = i; l < j; ++l) {
         if (s.charAt(l) == '"') {
            ++k;
         }
      }

      return k;
   }

   static String trim(String str) {
      String trimed = str.trim();
      int i = str.indexOf(trimed) + trimed.length();
      if (str.length() > i && trimed.endsWith("\\") && !trimed.endsWith("\\\\") && str.charAt(i) == ' ') {
         trimed = trimed + " ";
      }

      return trimed;
   }
}
