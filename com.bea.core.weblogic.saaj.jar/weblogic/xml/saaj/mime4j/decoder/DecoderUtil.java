package weblogic.xml.saaj.mime4j.decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import weblogic.xml.saaj.mime4j.util.CharsetUtil;

public class DecoderUtil {
   public static byte[] decodeBaseQuotedPrintable(String s) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
         byte[] bytes = s.getBytes("US-ASCII");
         QuotedPrintableInputStream is = new QuotedPrintableInputStream(new ByteArrayInputStream(bytes));
         int b = false;

         int b;
         while((b = is.read()) != -1) {
            baos.write(b);
         }
      } catch (IOException var5) {
      }

      return baos.toByteArray();
   }

   public static byte[] decodeBase64(String s) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
         byte[] bytes = s.getBytes("US-ASCII");
         Base64InputStream is = new Base64InputStream(new ByteArrayInputStream(bytes));
         int b = false;

         int b;
         while((b = is.read()) != -1) {
            baos.write(b);
         }
      } catch (IOException var5) {
      }

      return baos.toByteArray();
   }

   public static String decodeB(String encodedWord, String charset) throws UnsupportedEncodingException {
      return new String(decodeBase64(encodedWord), charset);
   }

   public static String decodeQ(String encodedWord, String charset) throws UnsupportedEncodingException {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < encodedWord.length(); ++i) {
         char c = encodedWord.charAt(i);
         if (c == '_') {
            sb.append("=20");
         } else {
            sb.append(c);
         }
      }

      return new String(decodeBaseQuotedPrintable(sb.toString()), charset);
   }

   public static String decodeEncodedWords(String body) {
      StringBuffer sb = new StringBuffer();
      int p1 = false;
      int p2 = 0;

      try {
         while(p2 < body.length()) {
            int p1 = body.indexOf("=?", p2);
            if (p1 == -1) {
               sb.append(body.substring(p2));
               break;
            }

            if (p1 - p2 > 0) {
               sb.append(body.substring(p2, p1));
            }

            int t1 = body.indexOf(63, p1 + 2);
            int t2 = t1 != -1 ? body.indexOf(63, t1 + 1) : -1;
            p2 = t2 != -1 ? body.indexOf("?=", t2 + 1) : -1;
            if (p2 == -1) {
               if (t2 == -1 || body.charAt(t2 + 1) != '=') {
                  sb.append(body.substring(p1));
                  break;
               }

               p2 = t2;
            }

            String decodedWord = null;
            if (t2 == p2) {
               decodedWord = "";
            } else {
               String mimeCharset = body.substring(p1 + 2, t1);
               String enc = body.substring(t1 + 1, t2);
               String encodedWord = body.substring(t2 + 1, p2);
               String charset = CharsetUtil.toJavaCharset(mimeCharset);
               if (charset == null) {
                  body.substring(p1, p2 + 2);
                  decodedWord = body.substring(p1, p2 + 2);
               } else if (enc.equalsIgnoreCase("Q")) {
                  decodedWord = decodeQ(encodedWord, charset);
               } else if (enc.equalsIgnoreCase("B")) {
                  decodedWord = decodeB(encodedWord, charset);
               } else {
                  decodedWord = encodedWord;
               }
            }

            p2 += 2;
            sb.append(decodedWord);
         }
      } catch (Throwable var11) {
      }

      return sb.toString();
   }
}
